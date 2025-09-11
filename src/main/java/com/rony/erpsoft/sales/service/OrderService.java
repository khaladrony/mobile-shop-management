package com.rony.erpsoft.sales.service;

import com.rony.erpsoft.application_common.service.GeneralInfoCommonService;
import com.rony.erpsoft.application_common.service.TransactionNumberConfigService;
import com.rony.erpsoft.configuration.AppResponse;
import com.rony.erpsoft.inventory.inventorymovement.service.SalesPostingService;
import com.rony.erpsoft.sales.dto.OrderItemDetailsDTO;
import com.rony.erpsoft.sales.dto.OrderItemRequestDTO;
import com.rony.erpsoft.sales.dto.OrderItemResponseDTO;
import com.rony.erpsoft.sales.dto.OrderItemSummaryDTO;
import com.rony.erpsoft.sales.dto.OrderRequestDTO;
import com.rony.erpsoft.sales.dto.OrderResponseDTO;
import com.rony.erpsoft.sales.dto.OrderSearchDTO;
import com.rony.erpsoft.sales.dto.SalesProfitReportDTO;
import com.rony.erpsoft.sales.mapper.OrderMapper;
import com.rony.erpsoft.sales.model.Order;
import com.rony.erpsoft.sales.model.enums.OrderStatus;
import com.rony.erpsoft.sales.model.enums.POSTransactionType;
import com.rony.erpsoft.sales.repository.OrderRepository;
import com.rony.erpsoft.utils.AppUtil;
import com.rony.erpsoft.utils.ModelValidator;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.rony.erpsoft.utils.ApplicationConstants.MODULE_SALES;

@Service
@AllArgsConstructor
public class OrderService {
    private final ModelValidator modelValidator;
    private final GeneralInfoCommonService generalInfoCommonService;
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final TransactionNumberConfigService transactionNumberConfigService;
    private final TransactionService transactionService;
    private final SalesPostingService salesPostingService;

    public AppResponse createAndUpdate(OrderRequestDTO orderRequest) {
        try {

            clientDataValidate(orderRequest);

            prepareEntityForSave(orderRequest);

            Order order = orderMapper.requestDTOToEntity(orderRequest);

            if (!modelValidator.isValid(order)) {
                String message = modelValidator.validationMessage(order);
                return AppResponse.build(HttpStatus.NOT_ACCEPTABLE).message(message);
            }

            Order saved = orderRepository.save(order);

            if (saved.getId() != null && saved.getId() > 0) {
                OrderResponseDTO responseDTO = orderMapper.entityToDto(saved);
                salesPostingService.salesPostingToInventory(responseDTO);

                return AppResponse.build(HttpStatus.OK).body(responseDTO);
            }

            return AppResponse.build(HttpStatus.EXPECTATION_FAILED).message("Not created");

        } catch (Exception ex) {
            return AppResponse.build(HttpStatus.INTERNAL_SERVER_ERROR).message(ex.getMessage());
        }
    }

    public AppResponse clientDataValidate(OrderRequestDTO orderRequest) {

        try {

            if (orderRequest.getItems().isEmpty()) {
                return AppResponse.build(HttpStatus.NOT_ACCEPTABLE).message("Order items not found!");
            }

            return AppResponse.build(HttpStatus.NOT_ACCEPTABLE).message(modelValidator.validationMessage(orderRequest));
        } catch (Exception ex) {
            return AppResponse.build(HttpStatus.INTERNAL_SERVER_ERROR).message(ex.getMessage());
        }
    }

    private void prepareEntityForSave(OrderRequestDTO orderRequest) {
        if (orderRequest.getId() == null) {
            orderRequest.setTransactionId(
                    transactionIdGeneration(
                            orderRequest.getTransactionType().toString(),
                            AppUtil.getLocalDateTimeToDate(orderRequest.getTransactionDate())
                    )
            );
        }

        orderRequest.setTransactionDate(LocalDateTime.now());
        List<OrderItemRequestDTO> details = orderRequest.getItems();
        if (details != null) {
            for (int i = 0; i < details.size(); i++) {
                OrderItemRequestDTO item = details.get(i);
                item.setLineNumber(i + 1);
            }
        }
    }

    private String transactionIdGeneration(String transactionType, Date transactionDate) {
        return transactionNumberConfigService.generateTransactionNumber(MODULE_SALES, transactionType, transactionDate);
    }

    public Page<OrderResponseDTO> findAll(OrderSearchDTO searchDTO, Pageable pageable) {
        Specification<Order> spec = OrderSpecification.build(searchDTO);

        return orderRepository.findAll(spec, pageable)
                .map(item -> orderMapper.entityToDto(item));
    }

    public List<String> searchTransactionIds(String query) {
        Specification<Order> spec = OrderSpecification.transactionIdMatches(query);
        return orderRepository.findAll(spec)
                .stream()
                .map(Order::getTransactionId)
                .distinct()
                .collect(Collectors.toList());
    }

    public OrderResponseDTO getById(Long id) {
        return orderRepository.findById(id)
                .map(orderMapper::entityToDto)
                .orElseThrow(() -> new EntityNotFoundException("Order not found with id: " + id));
    }

    public OrderResponseDTO getByTransactionId(String transactionId) {
        return orderRepository.findByTransactionId(transactionId)
                .map(orderMapper::entityToDto)
                .orElseThrow(() -> new EntityNotFoundException("Order not found with id: " + transactionId));
    }

    public AppResponse<OrderResponseDTO> updateStatus(Long id, OrderStatus status) {
        try {
            Order order = orderMapper.dtoToEntity(getById(id));
            if (order == null) {
                return AppResponse.<OrderResponseDTO>build(HttpStatus.NOT_FOUND)
                        .message("Order not found with id: " + id);
            }

            order.setStatus(status);

            Order saved = orderRepository.save(order);
            return AppResponse.<OrderResponseDTO>build(HttpStatus.OK)
                    .body(orderMapper.entityToDto(saved))
                    .message("Status updated successfully");

        } catch (Exception ex) {
            return AppResponse.<OrderResponseDTO>build(HttpStatus.INTERNAL_SERVER_ERROR)
                    .message(ex.getMessage());
        }
    }


    /*public void cancelOrder(Long id) {
        Order order = orderMapper.dtoToEntity(getById(id));
        order.setStatus(OrderStatus.CANCELLED);
        orderRepository.save(order);
    }*/

    /* Report */

    public List<OrderItemDetailsDTO> getAllOrder(String startDate, String endDate) {
        LocalDateTime fromDate = LocalDate.parse(startDate).atStartOfDay();
        LocalDateTime toDate = LocalDate.parse(endDate).atTime(23, 59, 59);
        List<OrderItemDetailsDTO> itemDetailsDTOS = new ArrayList<>();

        List<OrderResponseDTO> orderDTOs = orderRepository.findByTransactionDateBetweenAndStatusInOrderByIdDesc(
                        fromDate, toDate,
                        Arrays.asList(OrderStatus.COMPLETED, OrderStatus.RETURNED))
                .stream()
                .map(orderMapper::entityToDto)
                .toList();
        for (OrderResponseDTO orderDTO : orderDTOs) {
            itemDetailsDTOS.addAll(getOrderItemDetails(orderDTO));
        }

        return itemDetailsDTOS;
    }

    public List<OrderItemDetailsDTO> getOrderItemDetails(OrderResponseDTO orderDTO) {

        List<OrderItemDetailsDTO> itemDetailsDTOS = new ArrayList<>();
        int i = 0;
        BigDecimal runningTotalDiscount = BigDecimal.ZERO;
        BigDecimal runningTotalVat = BigDecimal.ZERO;

        for (OrderItemResponseDTO item : orderDTO.getItems()) {
            BigDecimal itemDiscount = BigDecimal.ZERO;
            BigDecimal itemVat = BigDecimal.ZERO;
            OrderItemDetailsDTO itemDetailsDTO = new OrderItemDetailsDTO();

            itemDetailsDTO.setId(item.getId());
            itemDetailsDTO.setOrganizationId(orderDTO.getOrganizationId());
            itemDetailsDTO.setTransactionId(orderDTO.getTransactionId());
            itemDetailsDTO.setTransactionDate(orderDTO.getTransactionDate());
            itemDetailsDTO.setStatus(orderDTO.getStatus());
            itemDetailsDTO.setItemCode(item.getItemCode());
            itemDetailsDTO.setItemName(item.getItemName());
            itemDetailsDTO.setCostPrice(item.getCostPrice());
            itemDetailsDTO.setQuantity(item.getQuantity());
            itemDetailsDTO.setPrice(item.getPrice());
            itemDetailsDTO.setCostPrice(item.getCostPrice());

            if (orderDTO.getVatAmount().compareTo(BigDecimal.ZERO) > 0
                    || orderDTO.getDiscountValue().compareTo(BigDecimal.ZERO) > 0) {
                if (i < orderDTO.getItems().size() - 1) {
                    // ratio = itemTotal / grossAmount
                    BigDecimal ratio = item.getTotal()
                            .divide(orderDTO.getGrossAmount(), 6, RoundingMode.HALF_UP);

                    // proportional discount = discountValue * ratio
                    if (orderDTO.getDiscountValue().compareTo(BigDecimal.ZERO) > 0) {
                        itemDiscount = orderDTO.getDiscountValue()
                                .multiply(ratio)
                                .setScale(2, RoundingMode.HALF_UP); // round to 2 decimals
                    }
                    if (orderDTO.getVatAmount().compareTo(BigDecimal.ZERO) > 0) {
                        itemVat = orderDTO.getVatAmount()
                                .multiply(ratio)
                                .setScale(2, RoundingMode.HALF_UP); // round to 2 decimals
                    }
                    runningTotalDiscount = runningTotalDiscount.add(itemDiscount);
                    runningTotalVat = runningTotalVat.add(itemVat);

                } else {
                    // last item gets the remainder
                    if (orderDTO.getDiscountValue().compareTo(BigDecimal.ZERO) > 0) {
                        itemDiscount = orderDTO.getDiscountValue()
                                .subtract(runningTotalDiscount)
                                .setScale(2, RoundingMode.HALF_UP);
                    }
                    if (orderDTO.getVatAmount().compareTo(BigDecimal.ZERO) > 0) {
                        itemVat = orderDTO.getVatAmount()
                                .subtract(runningTotalVat)
                                .setScale(2, RoundingMode.HALF_UP);
                    }
                }
            }

            itemDetailsDTO.setGrossAmount(item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())));
            itemDetailsDTO.setDiscountAmount(itemDiscount);
            itemDetailsDTO.setNetAmount(itemDetailsDTO.getGrossAmount().subtract(itemDiscount));
            itemDetailsDTO.setVatAmount(itemVat);
            itemDetailsDTO.setTotalAmount(itemDetailsDTO.getNetAmount().add(itemVat));
            itemDetailsDTOS.add(itemDetailsDTO);

            i++;
        }
        return itemDetailsDTOS;
    }

    public List<OrderItemSummaryDTO> getSalesByItem(String startDate, String endDate) {
        return getAllOrder(startDate, endDate).stream()
                .filter(o -> o.getStatus().equals(OrderStatus.COMPLETED))
                .collect(Collectors.groupingBy(OrderItemDetailsDTO::getItemName))
                .entrySet().stream()
                .map(entry -> {
                    String itemName = entry.getKey();

                    List<OrderItemDetailsDTO> items = entry.getValue();

                    int totalQuantity = items.stream().mapToInt(OrderItemDetailsDTO::getQuantity).sum();
                    BigDecimal price = items.stream()
                            .map(OrderItemDetailsDTO::getPrice)
                            .findFirst()
                            .orElse(BigDecimal.ZERO);
                    BigDecimal totalGrossAmount = items.stream()
                            .map(OrderItemDetailsDTO::getGrossAmount)
                            .reduce(BigDecimal.ZERO, BigDecimal::add);
                    BigDecimal totalDiscountAmount = items.stream()
                            .map(OrderItemDetailsDTO::getDiscountAmount)
                            .reduce(BigDecimal.ZERO, BigDecimal::add);
                    BigDecimal totalNetAmount = items.stream()
                            .map(OrderItemDetailsDTO::getNetAmount)
                            .reduce(BigDecimal.ZERO, BigDecimal::add);
                    BigDecimal totalVatAmount = items.stream()
                            .map(OrderItemDetailsDTO::getVatAmount)
                            .reduce(BigDecimal.ZERO, BigDecimal::add);
                    BigDecimal totalAmount = items.stream()
                            .map(OrderItemDetailsDTO::getTotalAmount)
                            .reduce(BigDecimal.ZERO, BigDecimal::add);

                    return new OrderItemSummaryDTO(itemName, totalQuantity, price, totalGrossAmount,
                            totalDiscountAmount, totalNetAmount, totalVatAmount, totalAmount);
                })
                .collect(Collectors.toList());
    }

    public SalesProfitReportDTO calculateProfitReport(String startDate, String endDate) {

        List<OrderItemDetailsDTO> items = getAllOrder(startDate, endDate);
        Map<POSTransactionType, BigDecimal> summary = transactionService.transactionSummary(startDate, endDate);

        BigDecimal income = summary.getOrDefault(POSTransactionType.INCOME, BigDecimal.ZERO);

        // total sales = sum of totalAmount
        BigDecimal totalSales = items.stream()
                .filter(o -> o.getStatus().equals(OrderStatus.COMPLETED))
                .map(OrderItemDetailsDTO::getTotalAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        totalSales = totalSales.add(income);    //Income from transaction

        // purchase cost = sum of (costPrice × quantity)
        BigDecimal purchaseCost = items.stream()
                .filter(o -> o.getStatus().equals(OrderStatus.COMPLETED))
                .map(i -> i.getCostPrice().multiply(BigDecimal.valueOf(i.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        //gross profit = totalSales - purchaseCost
        BigDecimal grossProfit = totalSales.subtract(purchaseCost);

        // sales return = sum of totalAmount which status is 'RETURNED'
        BigDecimal salesReturns = items.stream()
                .filter(o -> o.getStatus().equals(OrderStatus.RETURNED))
                .map(OrderItemDetailsDTO::getTotalAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal expenses = summary.getOrDefault(POSTransactionType.EXPENSES, BigDecimal.ZERO);

        // profit = totalSales - purchaseCost - expenses - salesReturn
        BigDecimal profit = totalSales
                .subtract(purchaseCost)
                .subtract(expenses)
                .subtract(salesReturns);

        return new SalesProfitReportDTO(totalSales, purchaseCost, grossProfit, expenses, salesReturns, profit);
    }
}
