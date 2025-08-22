package com.rony.erpsoft.utils;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ApplicationConstants {

    public static final String VIEW_PAGE = "inventory/view";
    public static final String INVENTORY_MOVEMENT_BASE_URL = "/inventory/inventory-movement";
    public static final String INVENTORY_REPORT_BASE_URL = "/inventory/report";
    public static final String INVENTORY_MASTER_BASE_URL = "/inventory/item-master";

    public static final String SALES_VIEW_PAGE = "sales/view";
    public static final String POS_BASE_URL = "/sales/pos";

    public static final String VIEW = "/view";
    public static final String FILTER = "/filter";
    public static final String SORT_BY_ID = "id";

    public static final String ACCOUNTS_SUB_TYPE_BALANCE_SHEET = "Balance Sheet";
    public static final String ACCOUNTS_SUB_TYPE_REVENUE = "Revenue";

    public static final String MODULE_ACCOUNTS = "Accounts";
    public static final String MODULE_INVENTORY = "Inventory";
    public static final String MODULE_SALES = "Sales";
}
