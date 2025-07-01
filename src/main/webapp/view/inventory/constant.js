var JMODULE_NAME = "inventory";

var JCONTROLLER = {
    ITEM_MASTER: "item-master"
};

var JCOMPONENT = {
    item_master_add_view: "item_master_add_view",
    item_master_update_view: "item_master_update_view",
    item_master_list_view: "item_master_list_view"
};

var API = {
    ITEM_MASTER_SAVE: _baseurl_ + JMODULE_NAME + "/" + JCONTROLLER.ITEM_MASTER + "/save",
    ITEM_MASTER_UPDATE: _baseurl_ + JMODULE_NAME + "/" + JCONTROLLER.ITEM_MASTER + "/update",
    ITEM_MASTER_GET: _baseurl_ + JMODULE_NAME + "/" + JCONTROLLER.ITEM_MASTER + "/get",
    ITEM_MASTER_FILTER: _baseurl_ + JMODULE_NAME + "/" + JCONTROLLER.ITEM_MASTER + "/filter",
    ITEM_MASTER_LIST: _baseurl_ + JMODULE_NAME + "/" + JCONTROLLER.ITEM_MASTER + "/get/list"
};