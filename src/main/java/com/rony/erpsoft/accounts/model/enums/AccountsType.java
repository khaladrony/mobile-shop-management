package com.rony.erpsoft.accounts.model.enums;

import java.util.Arrays;
import java.util.List;

public enum AccountsType {

    ASSET("Asset", "1"),
    LIABILITY("Liability", "2"),
    INCOME("Income", "3"),
    EXPENDITURE("Expenditure", "4");

    private String displayName;
    private String code;

    AccountsType(String displayName, String code) {
        this.displayName = displayName;
        this.code = code;
    }

    public String displayName() { return displayName; }

    public String code() { return code; }

    static public List<AccountsType> list() {
        return Arrays.asList(AccountsType.values());
    }

    @Override
    public String toString() { return code; }
}
