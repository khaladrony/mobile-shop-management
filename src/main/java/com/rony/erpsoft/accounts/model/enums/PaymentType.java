package com.rony.erpsoft.accounts.model.enums;

import java.util.Arrays;
import java.util.List;

public enum PaymentType {
    CASH("Cash", "CASH"),
    BANK("Bank", "BANK");

    private String displayName;
    private String code;

    PaymentType(String displayName, String code) {
        this.displayName = displayName;
        this.code = code;
    }

    public String displayName() { return displayName; }

    public String code() { return code; }

    static public List<PaymentType> list() {
        return Arrays.asList(PaymentType.values());
    }

    @Override
    public String toString() { return code; }

}
