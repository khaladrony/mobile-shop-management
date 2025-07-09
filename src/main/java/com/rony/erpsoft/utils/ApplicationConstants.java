package com.rony.erpsoft.utils;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ApplicationConstants {

    public static final String INVENTORY_MOVEMENT_BASE_URL = "/inventory/inventory-movement";
    public static final String INVENTORY_MASTER_BASE_URL = "/inventory/item-master";
    public static final String VIEW = "/view";
    public static final String VIEW_PAGE = "inventory/view";
    public static final String FILTER = "/filter";
    public static final String SORT_BY_ID = "id";

//    Inventory
    public static final int RECEIPT_SIGN = 1;
    public static final int ISSUE_SIGN = -1;
}
