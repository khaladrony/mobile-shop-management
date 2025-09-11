var JMODULE_NAME = "sales";

var JCONTROLLER = {
    POS: "pos",
    POS_REPORT: "pos-report",
    POS_DASHBOARD: "pos-dashboard",
    POS_TRANSACTION: "pos-transaction",
    POS_DEFAULT: "pos-default"
};

var JCOMPONENT = {
    pos_add_view: "pos_add_view",
    pos_update_view: "pos_update_view",
    pos_list_view: "pos_list_view",
    pos_dashboard_view: "pos_dashboard_view",
    pos_daily_sales_report_view: "pos_daily_sales_report_view",
    pos_sales_by_item_report_view: "pos_sales_by_item_report_view",
    pos_transaction_view: "pos_transaction_view",
    pos_transaction_add_view: "pos_transaction_add_view",
    pos_transaction_update_view: "pos_transaction_update_view",
    pos_receipt_view: "pos_receipt_view",
    invoice_view: "invoice_view"
};

var API = {
    POS_SAVE: _baseurl_ + JMODULE_NAME + "/" + JCONTROLLER.POS + "/save",
    POS_UPDATE: _baseurl_ + JMODULE_NAME + "/" + JCONTROLLER.POS + "/update",
    POS_GET_BY_ID: _baseurl_ + JMODULE_NAME + "/" + JCONTROLLER.POS + "/orders/id",
    POS_GET_BY_TRANSACTION_ID: _baseurl_ + JMODULE_NAME + "/" + JCONTROLLER.POS + "/orders/transaction",
    POS_FILTER: _baseurl_ + JMODULE_NAME + "/" + JCONTROLLER.POS + "/filter",
    POS_LIST: _baseurl_ + JMODULE_NAME + "/" + JCONTROLLER.POS + "/get/list",
    ITEM_MASTER_ITEMS_DROP_DOWN: _baseurl_ + JMODULE_NAME + "/" + JCONTROLLER.ITEM_MASTER + "/items",
    REPORT_DAILY_SALES: _baseurl_ + JMODULE_NAME + "/" + JCONTROLLER.POS_REPORT + "/daily-sales",
    REPORT_SALES_BY_ITEM: _baseurl_ + JMODULE_NAME + "/" + JCONTROLLER.POS_REPORT + "/sales-by-item",
    GET_ALL_BY_DATE: _baseurl_ + JMODULE_NAME + "/" + JCONTROLLER.POS_REPORT + "/all-items",
    REPORT_SALES_PROFIT: _baseurl_ + JMODULE_NAME + "/" + JCONTROLLER.POS_REPORT + "/sales-profit",
    REPORT_STOCK_SUMMARY: _baseurl_ + JMODULE_NAME + "/" + JCONTROLLER.POS_REPORT + "/stock-summary",
    POS_TRANSACTION_SAVE: _baseurl_ + JMODULE_NAME + "/" + JCONTROLLER.POS_TRANSACTION + "/save",
    POS_TRANSACTION_UPDATE: _baseurl_ + JMODULE_NAME + "/" + JCONTROLLER.POS_TRANSACTION + "/update",
    POS_TRANSACTION_FILTER: _baseurl_ + JMODULE_NAME + "/" + JCONTROLLER.POS_TRANSACTION + "/filter",
    POS_TRANSACTION_GET: _baseurl_ + JMODULE_NAME + "/" + JCONTROLLER.POS_TRANSACTION + "/transactions/id",
    DAILY_SUMMARY: _baseurl_ + JMODULE_NAME + "/" + JCONTROLLER.POS_DASHBOARD + "/daily",
    MONTHLY_SUMMARY: _baseurl_ + JMODULE_NAME + "/" + JCONTROLLER.POS_DASHBOARD + "/monthly",
    POS_RECEIPT: _baseurl_ + JMODULE_NAME + "/" + JCONTROLLER.POS_REPORT + "/receipt",
    POS_DEFAULT_FETCH: _baseurl_ + JMODULE_NAME + "/" + JCONTROLLER.POS_DEFAULT + "/branchCode"

};

var SALES_KEY = {
    TRANSACTION_TYPE: {
        POS: "POS",
        NORMAL: "NORMAL"
    },
    STATUS: {
        PENDING: "PENDING",
        HELD: "HELD",
        COMPLETED: "COMPLETED",
        CANCELLED: "CANCELLED"
    },
    DISCOUNT_TYPE: {
        PERCENT: "PERCENT",
        AMOUNT: "AMOUNT"
    },
    PAYMENT_TYPE: {
        CASH: "CASH",
        CARD: "CARD",
        MOBILE: "MOBILE"
    }
};