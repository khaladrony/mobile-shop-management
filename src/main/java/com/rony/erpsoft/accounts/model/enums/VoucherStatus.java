package com.rony.erpsoft.accounts.model.enums;

import java.util.Arrays;
import java.util.List;

public enum VoucherStatus {
    DRAFT("Draft", "DRAFT"),
    SUBMITTED("Submitted", "SUBMITTED"),
    APPROVED("Approved", "APPROVED"),
    POSTED("Posted", "POSTED"),
    CANCELLED("Cancelled", "CANCELLED");

    private String displayName;
    private String code;

    VoucherStatus(String displayName, String code) {
        this.displayName = displayName;
        this.code = code;
    }

    public String displayName() { return displayName; }

    public String code() { return code; }

    static public List<VoucherStatus> list() {
        return Arrays.asList(VoucherStatus.values());
    }

    @Override
    public String toString() { return code; }
}
