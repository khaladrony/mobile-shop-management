package com.rony.erpsoft.sales.service;

import com.rony.erpsoft.application_common.service.GeneralInfoCommonService;
import com.rony.erpsoft.application_common.service.TransactionNumberConfigService;
import com.rony.erpsoft.configuration.AppResponse;
import com.rony.erpsoft.sales.dto.OrderItemRequestDTO;
import com.rony.erpsoft.sales.dto.OrderRequestDTO;
import com.rony.erpsoft.sales.dto.OrderResponseDTO;
import com.rony.erpsoft.sales.dto.OrderSearchDTO;
import com.rony.erpsoft.sales.dto.SalesByItemReportDTO;
import com.rony.erpsoft.sales.mapper.OrderMapper;
import com.rony.erpsoft.sales.model.Order;
import com.rony.erpsoft.sales.model.enums.OrderStatus;
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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
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

    /*public List<OrderResponseDTO> getByStatus(OrderStatus status) {
        return orderRepository.findByStatusOrderByTimestampDesc(status);
    }*/

/*    public OrderResponseDTO updateStatus(Long id, OrderStatus status) {
        Order order = orderMapper.dtoToEntity(getById(id));
        order.setStatus(status);
        return orderMapper.entityToDto(orderRepository.save(order));
    }*/

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

    public List<SalesByItemReportDTO> getSalesByItem(String startDate, String endDate) {
        LocalDateTime from = LocalDate.parse(startDate).atStartOfDay();
        LocalDateTime to = LocalDate.parse(endDate).atTime(23, 59, 59);
        return orderRepository.getSalesByItemReport(from, to);
    }
}
