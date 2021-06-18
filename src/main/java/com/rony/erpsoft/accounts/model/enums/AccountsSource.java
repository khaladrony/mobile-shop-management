package com.rony.erpsoft.accounts.model.enums;

import java.util.Arrays;
import java.util.List;

public enum AccountsSource {
    NONE("None", "NONE"),
    SUBACCOUNT("Subaccount", "SUBACCOUNT"),
    CUSTOMER("Customer", "CUSTOMER"),
    SUPPLIER("Supplier", "SUPPLIER"),
    EMPLOYEE("Employee", "EMPLOYEE");

    private String displayName;
    private String code;

    AccountsSource(String displayName, String code) {
        this.displayName = displayName;
        this.code = code;
    }

    public String displayName() { return displayName; }

    public String code() { return code; }

    static public List<AccountsSource> list() {
        return Arrays.asList(AccountsSource.values());
    }

    @Override
    public String toString() { return code; }
}
