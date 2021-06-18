package com.rony.erpsoft.accounts.model.enums;

import java.util.Arrays;
import java.util.List;

public enum VoucherType {
    DEBIT_VOUCHER("Debit Voucher", "DEBIT_VOUCHER", "DV"),
    CREDIT_VOUCHER("Credit Voucher", "CREDIT_VOUCHER", "CV"),
    JOURNAL_VOUCHER("Journal Voucher", "JOURNAL_VOUCHER", "JV"),
    TRANSFER_VOUCHER("Transfer Voucher", "TRANSFER_VOUCHER", "TV"),
    OPENING_BALANCE("Opening Balance", "OPENING_BALANCE", "OB");

    private String displayName;
    private String code;
    private String prefix;

    VoucherType(String displayName, String code, String prefix) {
        this.displayName = displayName;
        this.code = code;
        this.prefix = prefix;
    }

    public String displayName() {
        return displayName;
    }

    public String code() {
        return code;
    }

    public String prefix() {
        return prefix;
    }

    static public List<VoucherType> list() {
        return Arrays.asList(VoucherType.values());
    }

    @Override
    public String toString() {
        return code;
    }
}
