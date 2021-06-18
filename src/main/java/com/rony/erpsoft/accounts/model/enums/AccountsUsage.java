package com.rony.erpsoft.accounts.model.enums;

import java.util.Arrays;
import java.util.List;

public enum AccountsUsage {
    LEDGER("Ledger", "LEDGER"),
    CASH("Cash", "CASH"),
    BANK("Bank", "BANK"),
    AP("AP", "AP"),
    AR("AR", "AR");

    private String displayName;
    private String code;

    AccountsUsage(String displayName, String code) {
        this.displayName = displayName;
        this.code = code;
    }

    public String displayName() { return displayName; }

    public String code() { return code; }

    static public List<AccountsUsage> list() {
        return Arrays.asList(AccountsUsage.values());
    }

    @Override
    public String toString() { return code; }
}
