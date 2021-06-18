package com.rony.erpsoft.accounts.model.enums;

import java.util.Arrays;
import java.util.List;

public enum ChequeStatus {
    CLEARED("Cleared", "CLEARED"),
    NOT_CLEARED("Not Cleared", "NOT_CLEARED"),
    BOUNCED("Bounced", "BOUNCED");

    private String displayName;
    private String code;

    ChequeStatus(String displayName, String code) {
        this.displayName = displayName;
        this.code = code;
    }

    public String displayName() {
        return displayName;
    }

    public String code() {
        return code;
    }

    static public List<VoucherStatus> list() {
        return Arrays.asList(VoucherStatus.values());
    }

    @Override
    public String toString() {
        return code;
    }
}
