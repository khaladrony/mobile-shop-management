var JMODULE_NAME = "user_auth";
var JCONTROLLER = {
    role:"role",
    user:"user",
    feature:"feature",
    PROFILE:"profile"
};
var JCOMPONENT = {
    role_add_view:"role_add_view",
    role_active_inactive:"role_active_inactive",
    role_show:"role_show",
    role_update:"role_update",
    role_list_view:"role_list_view",
    role_feature_view:"role_feature_view",
    
    clear_user_session:"clear_user_session",
    user_add_view:"user_add_view",
    user_list_view:"user_list_view",
    user_show:"user_show",
    user_edit_view:"user_edit_view",
    user_active_inactive:"user_active_inactive",
    user_profile_view:"user_profile_view",
    change_pwd_view:"change_pwd_view",
    
    feature_list_view:"feature_list_view",
    feature_show:"feature_show",
    feature_update:"feature_update",
    feature_active_inactive:"feature_active_inactive",
    feature_add_view:"feature_add_view"
    
};

var API = {
    ROLE_GET: _baseurl_ + JMODULE_NAME + "/" + JCONTROLLER.role + "/get",
    ROLE_SAVE: _baseurl_ + JMODULE_NAME + "/" + JCONTROLLER.role + "/save",
    ROLE_UPDATE: _baseurl_ + JMODULE_NAME + "/" + JCONTROLLER.role + "/update",
    ROLE_LIST: _baseurl_ + JMODULE_NAME + "/" + JCONTROLLER.role + "/get/all",
    ROLE_FEATURE_LIST: _baseurl_ + JMODULE_NAME + "/" + JCONTROLLER.role + "/features",
    ROLE_FEATURE_MAP: _baseurl_ + JMODULE_NAME + "/" + JCONTROLLER.role + "/mapRoleFeatures",
    ROLE_PERMISSION_XLS: _baseurl_ + JMODULE_NAME + "/" + JCONTROLLER.role + "/roleWisePermission",
    ROLE_USERLIST_XLS: _baseurl_ + JMODULE_NAME + "/" + JCONTROLLER.role + "/roleWiseUser",
    
    APP_AUDIT_GET: _baseurl_ + JMODULE_NAME + "/" + JCONTROLLER.feature + "/getAppAudit",
    APP_SETTS_GET: _baseurl_ + JMODULE_NAME + "/" + JCONTROLLER.feature + "/getAppSetts",
    APP_SETTS_SAVE: _baseurl_ + JMODULE_NAME + "/" + JCONTROLLER.feature + "/saveAppSetts",
    FEATURE_GET: _baseurl_ + JMODULE_NAME + "/" + JCONTROLLER.feature + "/get",
    FEATURE_SAVE: _baseurl_ + JMODULE_NAME + "/" + JCONTROLLER.feature + "/save",
    FEATURE_UPDATE: _baseurl_ + JMODULE_NAME + "/" + JCONTROLLER.feature + "/update",
    FEATURE_LIST: _baseurl_ + JMODULE_NAME + "/" + JCONTROLLER.feature + "/get/all",
    USER_AUDIT_XLS: _baseurl_ + JMODULE_NAME + "/" + JCONTROLLER.feature + "/audit_download",
    
    USER_GET: _baseurl_ + JMODULE_NAME + "/" + JCONTROLLER.user + "/get",
    USER_SAVE: _baseurl_ + JMODULE_NAME + "/" + JCONTROLLER.user + "/save",
    CHECK_EMAIL: _baseurl_ + JMODULE_NAME + "/" + JCONTROLLER.user + "/check-email",
    CHECK_LAN_ID: _baseurl_ + JMODULE_NAME + "/" + JCONTROLLER.user + "/check-lanId",
    USER_UPDATE: _baseurl_ + JMODULE_NAME + "/" + JCONTROLLER.user + "/update",
    USER_UNLOCK: _baseurl_ + JMODULE_NAME + "/" + JCONTROLLER.user + "/unlock_user",
    CLEAR_SESSION: _baseurl_ + JMODULE_NAME + "/" + JCONTROLLER.user + "/clear-session",
    USER_LIST: _baseurl_ + JMODULE_NAME + "/" + JCONTROLLER.user + "/get/all",
    USER_ACTIVE_INACTIVE: _baseurl_ + JMODULE_NAME + "/" + JCONTROLLER.user + "/active_inactive",
    USER_FILTER: _baseurl_ + JMODULE_NAME + "/" + JCONTROLLER.user + "/filter",
    ALL_USER_FILTER: _baseurl_ + JMODULE_NAME + "/" + JCONTROLLER.user + "/downloadAllUserList",
    SHOW_PROFILE: _baseurl_ + JMODULE_NAME + "/" + JCONTROLLER.PROFILE + "/show",
    CHANGE_UPAWRD: _baseurl_ + JMODULE_NAME + "/" + JCONTROLLER.PROFILE + "/change-urpawrd",
    RESET_UPAWRD: _baseurl_ + JMODULE_NAME + "/" + JCONTROLLER.user + "/reset-urpawrd"
};
