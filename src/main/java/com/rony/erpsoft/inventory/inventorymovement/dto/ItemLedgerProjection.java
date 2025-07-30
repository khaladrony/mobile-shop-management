package com.rony.erpsoft.inventory.inventorymovement.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public interface ItemLedgerProjection {
    String getItemNameCode();
    LocalDateTime getTransactionDate();
    String getDocumentNo();
    BigDecimal getOpeningQty();
    BigDecimal getReceiveQty();
    BigDecimal getIssueQty();
    BigDecimal getClosingQty();
}
