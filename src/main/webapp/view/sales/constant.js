var JMODULE_NAME = "sales";

var JCONTROLLER = {
    POS: "pos",
    POS_REPORT: "report"
};

var JCOMPONENT = {
    pos_add_view: "pos_add_view",
    pos_update_view: "pos_update_view",
    pos_list_view: "pos_list_view"
};

var API = {
    POS_SAVE: _baseurl_ + JMODULE_NAME + "/" + JCONTROLLER.POS + "/save",
    POS_UPDATE: _baseurl_ + JMODULE_NAME + "/" + JCONTROLLER.POS + "/update",
    POS_GET_BY_ID: _baseurl_ + JMODULE_NAME + "/" + JCONTROLLER.POS + "/orders/id",
    POS_GET_BY_TRANSACTION_ID: _baseurl_ + JMODULE_NAME + "/" + JCONTROLLER.POS + "/orders/transaction",
    POS_FILTER: _baseurl_ + JMODULE_NAME + "/" + JCONTROLLER.POS + "/filter",
    POS_LIST: _baseurl_ + JMODULE_NAME + "/" + JCONTROLLER.POS + "/get/list",
    ITEM_MASTER_ITEMS_DROP_DOWN: _baseurl_ + JMODULE_NAME + "/" + JCONTROLLER.ITEM_MASTER + "/items"

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
        BANK: "BANK"
    }
};