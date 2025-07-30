var JMODULE_NAME = "inventory";

var JCONTROLLER = {
    ITEM_MASTER: "item-master",
    INVENTORY_MOVEMENT: "inventory-movement",
    INVENTORY_MOVEMENT_ISSUE: "inventory-movement-issue",
    INVENTORY_MOVEMENT_TRANSFER: "inventory-movement-transfer",
    INVENTORY_MOVEMENT_POSTING: "inventory-movement-posting",
    INVENTORY_REPORT: "report"
};

var JCOMPONENT = {
    item_master_add_view: "item_master_add_view",
    item_master_update_view: "item_master_update_view",
    item_master_list_view: "item_master_list_view",

    inventory_movement_add_view: "inventory_movement_add_view",
    inventory_movement_update_view: "inventory_movement_update_view",
    inventory_movement_list_view: "inventory_movement_list_view",

    inventory_movement_issue_add_view: "inventory_movement_issue_add_view",
    inventory_movement_issue_update_view: "inventory_movement_issue_update_view",
    inventory_movement_issue_list_view: "inventory_movement_issue_list_view",

    inventory_movement_transfer_add_view: "inventory_movement_transfer_add_view",
    inventory_movement_transfer_update_view: "inventory_movement_transfer_update_view",
    inventory_movement_transfer_list_view: "inventory_movement_transfer_list_view",

    inventory_movement_posting_list_view: "inventory_movement_posting_list_view",

    inventory_movement_item_ledger_form_view: "inventory_movement_item_ledger_form_view"
};

var API = {
    ITEM_MASTER_SAVE: _baseurl_ + JMODULE_NAME + "/" + JCONTROLLER.ITEM_MASTER + "/save",
    ITEM_MASTER_UPDATE: _baseurl_ + JMODULE_NAME + "/" + JCONTROLLER.ITEM_MASTER + "/update",
    ITEM_MASTER_GET: _baseurl_ + JMODULE_NAME + "/" + JCONTROLLER.ITEM_MASTER + "/get",
    ITEM_MASTER_FILTER: _baseurl_ + JMODULE_NAME + "/" + JCONTROLLER.ITEM_MASTER + "/filter",
    ITEM_MASTER_LIST: _baseurl_ + JMODULE_NAME + "/" + JCONTROLLER.ITEM_MASTER + "/get/list",
    ITEM_MASTER_ITEMS_DROP_DOWN: _baseurl_ + JMODULE_NAME + "/" + JCONTROLLER.ITEM_MASTER + "/items",

    INVENTORY_MOVEMENT_SAVE: _baseurl_ + JMODULE_NAME + "/" + JCONTROLLER.INVENTORY_MOVEMENT + "/save",
    INVENTORY_MOVEMENT_UPDATE: _baseurl_ + JMODULE_NAME + "/" + JCONTROLLER.INVENTORY_MOVEMENT + "/update",
    INVENTORY_MOVEMENT_GET: _baseurl_ + JMODULE_NAME + "/" + JCONTROLLER.INVENTORY_MOVEMENT + "/get",
    INVENTORY_MOVEMENT_FILTER: _baseurl_ + JMODULE_NAME + "/" + JCONTROLLER.INVENTORY_MOVEMENT + "/filter",
    INVENTORY_MOVEMENT_TRNS_ID_AUTOCOMPLETE: _baseurl_ + JMODULE_NAME + "/" + JCONTROLLER.INVENTORY_MOVEMENT + "/search-transaction-id",

    INVENTORY_MOVEMENT_POSTING: _baseurl_ + JMODULE_NAME + "/" + JCONTROLLER.INVENTORY_MOVEMENT + "/posting",

    INVENTORY_REPORT_ITEM_LEDGER: _baseurl_ + JMODULE_NAME + "/" + JCONTROLLER.INVENTORY_REPORT + "/item-ledger",
    INVENTORY_MOVEMENT_REPORT_VIEW: _baseurl_ + JMODULE_NAME + "/" + JCONTROLLER.INVENTORY_REPORT

};

var INVENTORY_KEY = {
    ACTION: {
        RECEIPT: "RECEIPT",
        ISSUE: "ISSUE",
        TRANSFER: "TRANSFER"
    },
    STATUS: {
        OPEN: "OPEN",
        APPROVED: "APPROVED",
        ON_TRANSFER: "ON_TRANSFER",
        DELIVERED: "DELIVERED",
        PARTLY_DELIVERED: "PARTLY_DELIVERED"
    }
};