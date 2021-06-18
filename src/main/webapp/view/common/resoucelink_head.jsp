<%-- 
    Document   : resoucelink_head
    Created on : May 22, 2018, 11:21:32 AM
    Author     : sarker
--%>
    <%@page contentType="text/html" pageEncoding="UTF-8"%>
    <%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <meta charset="utf-8" />
    <title>${fn:escapeXml(APP_NAME)}</title>
    <% response.addHeader("x-frame-options","DENY"); %>
    
    <link rel="icon" type="image/x-icon" href="${fn:escapeXml(STATIC_RES)}/images/favicon.ico" />
    <link rel="icon" type="image/png" sizes="32x32" href="${fn:escapeXml(STATIC_RES)}/images/icon.png">
    <meta name="description" content="overview &amp; stats" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0" />

    <script type="text/javascript">
        var _baseurl_ = "${fn:escapeXml(BASE_URL)}";
        var APP = "${fn:escapeXml(APP)}";
        var _angular_ = "${fn:escapeXml(ANGULAR)}";
        var _NG_SRC_ = "${fn:escapeXml(NG_SRC)}";
        var _ROLE_NAME_ = "${fn:escapeXml(ROLE_NAME)}";
        var _ROLE_CODE_ = "${fn:escapeXml(ROLE_CODE)}";
        var _STATIC_RES_ = "${fn:escapeXml(STATIC_RES)}";
        var _USER_ID_ = "${fn:escapeXml(USER_ID)}";
        var _SHOW_ALERT_ = "${fn:escapeXml(SHOW_ALERT)}";
        var _FORCE_CHANGE_ = "${fn:escapeXml(FORCE_CHANGE)}";
        var _FORCE_CHANGE_DEFAULT_PASSWORD_ = "${fn:escapeXml(FORCE_CHANGE_DEFAULT_PASSWORD)}";
        var _SESSION_TIMEOUT_ = "${fn:escapeXml(SESSION_TIMEOUT)}";
        
        var _BRANCH_ID_ = "${fn:escapeXml(BRANCH_ID)}";
        var _COMPANY_ID_ = "${fn:escapeXml(COMPANY_ID)}";
        var isLoadingShown = false;
        var profile_pic_uri = null;
        var TOAST_TIMEOUT = 5500;
        var DASHBOARD_REFRESH = 60000; // 1 minute
        
    </script>
    <script src="${fn:escapeXml(STATIC_RES)}/js/custom.js"></script>
    <script src="${fn:escapeXml(STATIC_RES)}/angular/app.constant.js"></script>
    <!-- bootstrap & fontawesome -->
    <link rel="stylesheet" href="${fn:escapeXml(STATIC_RES)}/css/bootstrap.min.css?v=${fn:escapeXml(SCRIPT_VERSION)}" />
    <link rel="stylesheet" href="${fn:escapeXml(STATIC_RES)}/css/bootstrap-datetimepicker.min.css?v=${fn:escapeXml(SCRIPT_VERSION)}" />
    <link rel="stylesheet" href="${fn:escapeXml(STATIC_RES)}/font-awesome/4.5.0/css/font-awesome.min.css?v=${fn:escapeXml(SCRIPT_VERSION)}" />
    
    <link rel="stylesheet" href="${fn:escapeXml(STATIC_RES)}/angular/angular-material.min.css?v=${fn:escapeXml(SCRIPT_VERSION)}" />
    <link rel="stylesheet" href="${fn:escapeXml(STATIC_RES)}/angular/angular-growl.css?v=${fn:escapeXml(SCRIPT_VERSION)}" />
    <link rel="stylesheet" href="${fn:escapeXml(STATIC_RES)}/angular/angular-growl.min.css?v=${fn:escapeXml(SCRIPT_VERSION)}" />

    <link rel="stylesheet" href="${fn:escapeXml(STATIC_RES)}/css/ngToast.css?v=${fn:escapeXml(SCRIPT_VERSION)}" />
    <link rel="stylesheet" href="${fn:escapeXml(STATIC_RES)}/css/jquery-ui.min.css?v=${fn:escapeXml(SCRIPT_VERSION)}" />
    <script src="${fn:escapeXml(STATIC_RES)}/js/q.js"></script>
    
    <script type="text/javascript" src="${fn:escapeXml(STATIC_RES)}/js/gstatic_loader.js"></script>
    <script type="text/javascript">
        google.charts.load('current', {packages: ['gauge','corechart','wordtree']});
    </script>
    <script type="text/javascript" src="${fn:escapeXml(STATIC_RES)}/js/jquery.m.s.s.js"></script>
    <!-- page specific plugin styles -->

    <!-- text fonts -->
    <link rel="stylesheet" href="${fn:escapeXml(STATIC_RES)}/css/fonts.googleapis.com.css?v=${fn:escapeXml(SCRIPT_VERSION)}" />

    <!-- ace styles -->
    <link rel="stylesheet" href="${fn:escapeXml(STATIC_RES)}/css/ace.min.css?v=${fn:escapeXml(SCRIPT_VERSION)}" class="ace-main-stylesheet" id="main-ace-style" />

    <!--[if lte IE 9]>
            <link rel="stylesheet" href="${fn:escapeXml(STATIC_RES)}/css/ace-part2.min.css?v=${fn:escapeXml(SCRIPT_VERSION)}" class="ace-main-stylesheet" />
    <![endif]-->
    <link rel="stylesheet" href="${fn:escapeXml(STATIC_RES)}/css/ace-skins.min.css?v=${fn:escapeXml(SCRIPT_VERSION)}" />
    <link rel="stylesheet" href="${fn:escapeXml(STATIC_RES)}/css/ace-rtl.min.css?v=${fn:escapeXml(SCRIPT_VERSION)}" />
    <link rel="stylesheet" href="${fn:escapeXml(STATIC_RES)}/css/select2.min.css?v=${fn:escapeXml(SCRIPT_VERSION)}" />

    <link rel="stylesheet" href="${fn:escapeXml(STATIC_RES)}/css/ui.jqgrid.min.css?v=${fn:escapeXml(SCRIPT_VERSION)}" />
    <link rel="stylesheet" href="${fn:escapeXml(STATIC_RES)}/css/angular-datepicker.css?v=${fn:escapeXml(SCRIPT_VERSION)}" />
    
    <link rel="stylesheet" href="${fn:escapeXml(STATIC_RES)}/css/ngDialog.css?v=${fn:escapeXml(SCRIPT_VERSION)}">
    <link rel="stylesheet" href="${fn:escapeXml(STATIC_RES)}/css/ngDialog-theme-default.css?v=${fn:escapeXml(SCRIPT_VERSION)}">
    
    <!--[if lte IE 9]>
      <link rel="stylesheet" href="${fn:escapeXml(STATIC_RES)}/css/ace-ie.min.css?v=${fn:escapeXml(SCRIPT_VERSION)}" />
    <![endif]-->

    <!-- inline styles related to this page -->

    <!-- ace settings handler -->
    <script src="${fn:escapeXml(STATIC_RES)}/js/ace-extra.min.js?v=${fn:escapeXml(SCRIPT_VERSION)}"></script>

    <!-- HTML5shiv and Respond.js for IE8 to support HTML5 elements and media queries -->

    <!--[if lte IE 8]>
    <script src="${fn:escapeXml(STATIC_RES)}/js/html5shiv.min.js?v=${fn:escapeXml(SCRIPT_VERSION)}"></script>
    <script src="${fn:escapeXml(STATIC_RES)}/js/respond.min.js?v=${fn:escapeXml(SCRIPT_VERSION)}"></script>
    <![endif]-->
    
    <link rel="stylesheet" href="${fn:escapeXml(STATIC_RES)}/css/custom.css?v=${fn:escapeXml(SCRIPT_VERSION)}" />