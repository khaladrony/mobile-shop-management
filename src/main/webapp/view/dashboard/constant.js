var JMODULE_NAME = "dashboard";
var JCONTROLLER = {
    statics:"statics",
    component:"component",

};
var JCOMPONENT = {
    dashboard_static_view:"dashboard_static_view",
    
    atm_list_detail_component:"atm_list_detail_component",
    system_statistics_component:"system_statistics_component",
    atm_file_stats_component:"atm_file_stats_component",
};

var API = {
    ATM_AREA_LIST: _baseurl_ + "atm_management/area/get/all",
    ATM_LIST: _baseurl_ + "atm_management/atm/area_wise",
    
    
    COMP_ATM_LIST_DETAIL: _baseurl_ + JMODULE_NAME +  "/" + JCONTROLLER.component + "/atm_list_detail",
    COMP_SYSTEM_STATISTICS: _baseurl_ + JMODULE_NAME +  "/" + JCONTROLLER.component + "/system_statistics",
    COMP_ATM_FILE_STATISTICS: _baseurl_ + JMODULE_NAME +  "/" + JCONTROLLER.component + "/atm_file_stats",
    
};
