package com.rony.erpsoft.sales.service;

import com.rony.erpsoft.sales.dto.OrderItemResponseDTO;
import com.rony.erpsoft.sales.dto.OrderResponseDTO;
import com.rony.erpsoft.sales.dto.PosReceiptDTO;
import com.rony.erpsoft.sales.dto.PosReceiptItemDTO;
import com.rony.erpsoft.user_auth.model.Branch;
import com.rony.erpsoft.user_auth.service.SessionService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PosReceiptService {

    private final OrderService orderService;
    private final SessionService sessionService;

    public PosReceiptDTO getPosReceipt(Long id){

        OrderResponseDTO dto = orderService.getById(id);
        Branch branch = sessionService.getBranch();

        return PosReceiptDTO.builder()
                .date(dto.getTransactionDate())
                .cashier(sessionService.getUser().getFirst_name()) // TODO: map actual cashier/user
                .txnNo(dto.getTransactionId())
                .items(dto.getItems().stream()
                        .map(this::mapItem)
                        .collect(Collectors.toList()))
                .discount(dto.getDiscountValue())
                .vatRate(BigDecimal.valueOf(5)) // TODO: map from config if needed
                .serviceCharge(BigDecimal.ZERO) // placeholder
                .paymentMethod(dto.getPaymentType().name())
                .paid(dto.getGivenAmount())
                .grossAmount(dto.getGrossAmount())
                .netAmount(dto.getNetAmount())
                .vatAmount(dto.getVatAmount())
                .totalAmount(dto.getTotalAmount())
                .returnAmount(dto.getReturnAmount())
                .customerName(dto.getCustomerName())
                .mobileNumber(dto.getMobileNumber())
                .branch(branch)
                .footerNote("")
                .build();
    }

    private PosReceiptItemDTO mapItem(OrderItemResponseDTO item) {
        return PosReceiptItemDTO.builder()
                .name(item.getItemName())
                .qty(item.getQuantity())
                .price(item.getPrice())
                .total(item.getTotal())
                .discount(BigDecimal.ZERO) // map if discount per item available
                .note(null) // map if remarks/promo available
                .build();
    }
}
