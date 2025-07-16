var JMODULE_NAME = "inventory";

var JCONTROLLER = {
    ITEM_MASTER: "item-master",
    INVENTORY_MOVEMENT: "inventory-movement"
};

var JCOMPONENT = {
    item_master_add_view: "item_master_add_view",
    item_master_update_view: "item_master_update_view",
    item_master_list_view: "item_master_list_view",

    inventory_movement_add_view: "inventory_movement_add_view",
    inventory_movement_update_view: "inventory_movement_update_view",
    inventory_movement_list_view: "inventory_movement_list_view"
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
    INVENTORY_MOVEMENT_TRNS_ID_AUTOCOMPLETE: _baseurl_ + JMODULE_NAME + "/" + JCONTROLLER.INVENTORY_MOVEMENT + "/search-transaction-id"
};

var INVENTORY_KEY = {
    ACTION: {
        RECEIPT: "RECEIPT",
        ISSUE: "ISSUE"
    },
    STATUS: {
        OPEN: "OPEN",
        APPROVED: "APPROVED",
        ON_TRANSFER: "ON_TRANSFER",
        DELIVERED: "DELIVERED",
        PARTLY_DELIVERED: "PARTLY_DELIVERED"
    }
};