var JMODULE_NAME = "application_common";
var JCONTROLLER = {
    BANK_INFO:"bank_info",
    SUPPLIER_INFO:"supplier_info",
    CUSTOMER_INFO:"customer_info",
    EMPLOYEE_INFO:"employee_info"

};
var JCOMPONENT = {
    bank_info_add_view: "bank_info_add_view",
    bank_info_update_view: "bank_info_update_view",
    bank_info_list_view: "bank_info_list_view",

    supplier_info_add_view: "supplier_info_add_view",
    supplier_info_update_view: "supplier_info_update_view",
    supplier_info_list_view: "supplier_info_list_view",

    customer_info_add_view: "customer_info_add_view",
    customer_info_update_view: "customer_info_update_view",
    customer_info_list_view: "customer_info_list_view",

    employee_info_add_view: "employee_info_add_view",
    employee_info_update_view: "employee_info_update_view",
    employee_info_list_view: "employee_info_list_view"
};

var API = {
    BANK_INFO_SAVE: _baseurl_ + JMODULE_NAME + "/" + JCONTROLLER.BANK_INFO + "/save",
    BANK_INFO_UPDATE: _baseurl_ + JMODULE_NAME + "/" + JCONTROLLER.BANK_INFO + "/update",
    BANK_INFO_GET: _baseurl_ + JMODULE_NAME + "/" + JCONTROLLER.BANK_INFO + "/get",
    BANK_INFO_FILTER: _baseurl_ + JMODULE_NAME + "/" + JCONTROLLER.BANK_INFO + "/filter",
    BANK_ACCOUNT_LIST: _baseurl_ + JMODULE_NAME + "/" + JCONTROLLER.BANK_INFO + "/get/list",

    SUPPLIER_INFO_SAVE: _baseurl_ + JMODULE_NAME + "/" + JCONTROLLER.SUPPLIER_INFO + "/save",
    SUPPLIER_INFO_UPDATE: _baseurl_ + JMODULE_NAME + "/" + JCONTROLLER.SUPPLIER_INFO + "/update",
    SUPPLIER_INFO_GET: _baseurl_ + JMODULE_NAME + "/" + JCONTROLLER.SUPPLIER_INFO + "/get",
    SUPPLIER_INFO_FILTER: _baseurl_ + JMODULE_NAME + "/" + JCONTROLLER.SUPPLIER_INFO + "/filter",
    SUPPLIER_LIST: _baseurl_ + JMODULE_NAME + "/" + JCONTROLLER.SUPPLIER_INFO + "/get/list",

    CUSTOMER_INFO_SAVE: _baseurl_ + JMODULE_NAME + "/" + JCONTROLLER.CUSTOMER_INFO + "/save",
    CUSTOMER_INFO_UPDATE: _baseurl_ + JMODULE_NAME + "/" + JCONTROLLER.CUSTOMER_INFO + "/update",
    CUSTOMER_INFO_GET: _baseurl_ + JMODULE_NAME + "/" + JCONTROLLER.CUSTOMER_INFO + "/get",
    CUSTOMER_INFO_FILTER: _baseurl_ + JMODULE_NAME + "/" + JCONTROLLER.CUSTOMER_INFO + "/filter",
    CUSTOMER_LIST: _baseurl_ + JMODULE_NAME + "/" + JCONTROLLER.CUSTOMER_INFO + "/get/list",

    EMPLOYEE_INFO_SAVE: _baseurl_ + JMODULE_NAME + "/" + JCONTROLLER.EMPLOYEE_INFO + "/save",
    EMPLOYEE_INFO_UPDATE: _baseurl_ + JMODULE_NAME + "/" + JCONTROLLER.EMPLOYEE_INFO + "/update",
    EMPLOYEE_INFO_GET: _baseurl_ + JMODULE_NAME + "/" + JCONTROLLER.EMPLOYEE_INFO + "/get",
    EMPLOYEE_INFO_FILTER: _baseurl_ + JMODULE_NAME + "/" + JCONTROLLER.EMPLOYEE_INFO + "/filter",
    EMPLOYEE_LIST: _baseurl_ + JMODULE_NAME + "/" + JCONTROLLER.EMPLOYEE_INFO + "/get/list",

};
