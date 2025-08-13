package com.rony.erpsoft.application_common.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "transaction_number_config",
        uniqueConstraints = {
                @UniqueConstraint(name = "uq_module_entity_name_transaction_type", columnNames = {"module", "entity_name", "transaction_type"})
        })
public class TransactionNumberConfig extends BaseEntity {

    @Column(name = "module", nullable = false, length = 20)
    private String module;          // e.g. VOUCHER, INVENTORY, SALES

    @Column(name = "entity_name", nullable = false, length = 30)
    private String entityName;      // e.g. "AccJournalMaster", "InventoryMovement"

    @Column(name = "number_field", nullable = false, length = 20)
    private String numberField;     // e.g. "voucherNo", "transactionId"

    @Column(name = "type_field", length = 50)
    private String typeField; // e.g. "transactionType", "voucherType", null if not applicable

    @Column(name = "transaction_type", nullable = false, length = 20)
    private String transactionType; // e.g. DEBIT_VOUCHER, CREDIT_VOUCHER, RECEIPT, ISSUE

    @Column(name = "prefix", nullable = false, length = 10)
    private String prefix;          // e.g. DV--, CV--, RE--, IS--

    @Column(name = "format_pattern", nullable = false, length = 50)
    private String formatPattern;   // e.g. {PREFIX}{YYYY}{MM}{SEQ}

    @Column(name = "seq_length")
    private int seqLength;          // number of digits for sequence (e.g. 4 => 0001)

    @Column(name = "last_sequence")
    private int lastSequence;       // last generated sequence

    @Column(name = "reset_frequency")
    private String resetFrequency;  // NONE, MONTHLY, YEARLY
}
