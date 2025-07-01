<%-- 
    Document   : login_page
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html lang="en" ng-app="LauncherScreenApp">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta charset="utf-8" />
        <link rel="icon" type="image/x-icon" href="${STATIC_RES}/images/favicon.ico" />
        <title>Loading Home - <spring:message code="app_name" text="ERP"/></title>

        <meta name="description" content="User login page" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0" />

        <script type="text/javascript">
            var _baseurl_ = "${BASE_URL}";
            var _angular_ = "${ANGULAR}";
            var _NG_SRC_ = "${NG_SRC}";
            var isLoadingShown = false; 

        </script>
        <script src="${STATIC_RES}/angular/app.constant.js?v=${SCRIPT_VERSION}"></script>
        <!-- bootstrap & fontawesome -->
        <link rel="stylesheet" href="${STATIC_RES}/css/bootstrap.min.css?v=${SCRIPT_VERSION}" />
        <link rel="stylesheet" href="${STATIC_RES}/font-awesome/4.5.0/css/font-awesome.min.css?v=${SCRIPT_VERSION}" />

        <link rel="stylesheet" href="${STATIC_RES}/angular/angular-material.min.css?v=${SCRIPT_VERSION}" />
        <link rel="stylesheet" href="${STATIC_RES}/css/ngToast.css?v=${SCRIPT_VERSION}" />

        <!-- page specific plugin styles -->

        <!-- text fonts -->
        <link rel="stylesheet" href="${STATIC_RES}/css/fonts.googleapis.com.css?v=${SCRIPT_VERSION}" />

        <!-- ace styles -->
        <link rel="stylesheet" href="${STATIC_RES}/css/ace.min.css?v=${SCRIPT_VERSION}" class="ace-main-stylesheet" id="main-ace-style" />


        <link rel="stylesheet" href="${STATIC_RES}/css/ngDialog.css?v=${SCRIPT_VERSION}">
        <link rel="stylesheet" href="${STATIC_RES}/css/ngDialog-theme-default.css?v=${SCRIPT_VERSION}">
        <!--[if lte IE 9]>
          <link rel="stylesheet" href="${STATIC_RES}/css/ace-ie.min.css?v=${SCRIPT_VERSION}" />
        <![endif]-->

        <!-- inline styles related to this page -->

    </head>

    <body class="login-layout light-login" ng-controller="LauncherScreenCtrl" style="background: #C1CDCD">
        <div class="main-container" ng-init="configUserData()">
            <div class="main-content">
                <center><h2 style="color:#4683ea;">Loading "<b>${fn:escapeXml(USER.first_name)} ${fn:escapeXml(USER.last_name)}</b>" as ${fn:escapeXml(USER.role_name)}</h2></center>
            </div><!-- /.main-content -->
        </div><!-- /.main-container -->

        <!-- basic scripts -->

        <!--[if lte IE 8]>
        <script src="${STATIC_RES}/js/excanvas.min.js?v=${SCRIPT_VERSION}"></script>
        <![endif]-->
        
        <!-- AngularJS 1.6.10 -->
        <script src="${STATIC_RES}/angular/angular.min.js?v=${SCRIPT_VERSION}"></script>

        <script src="${STATIC_RES}/angular/angular-ui-router.min.js?v=${SCRIPT_VERSION}"></script>
        <script src="${STATIC_RES}/angular/angular-animate.min.js?v=${SCRIPT_VERSION}"></script>
        <script src="${STATIC_RES}/angular/angular-aria.min.js?v=${SCRIPT_VERSION}"></script>
        <script src="${STATIC_RES}/angular/angular-messages.min.js?v=${SCRIPT_VERSION}"></script>
        <script src="${STATIC_RES}/angular/angular-sanitize.js?v=${SCRIPT_VERSION}"></script>

        <script src="${STATIC_RES}/angular/angular-material.min.js?v=${SCRIPT_VERSION}"></script>
        <script src="${STATIC_RES}/angular/ngDialog.js?v=${SCRIPT_VERSION}"></script>
        <script src="${STATIC_RES}/angular/ngToast.js?v=${SCRIPT_VERSION}"></script>

        <script src="${STATIC_RES}/angular/angular-growl.js?v=${SCRIPT_VERSION}"></script>
        <script src="${STATIC_RES}/angular/angular-growl.min.js?v=${SCRIPT_VERSION}"></script>

        
        <script type="text/javascript">
            var app = angular.module('LauncherScreenApp', ['ngDialog', 'ngToast', 'ui.router','ngMaterial', 'ngMessages', 'ngSanitize', 'ngAnimate']);                                                                              var _shskr_ = "${XATKN}";
        </script>
        <script src="${ANGULAR}/app.services.js?v=${SCRIPT_VERSION}"></script>
        
        <script type="text/javascript">
            app.controller('LauncherScreenCtrl', function($scope, $rootScope, $http, $timeout, $rootScope, $sce, $mdDialog, $interval, Communication, DialogBox, ClientService) {
                
                $scope.configUserData = function(){
                    DialogBox.showProgress();
                    
                    var g = Communication.request("GET", _baseurl_ + "auth/mydata", {});

                    g.then(function (resp) {
                        //console.log("my data", JSON.stringify(resp));
                        //extract user info from response
                        var usrObj = {"user_id":resp.body.user_id, "last_name":resp.body.first_name, "last_name":resp.body.last_name, "user_code":resp.body.user_code, "email":resp.body.email, "phone":resp.body.phone, "address":resp.body.address, "country":resp.body.country};
                        ClientService.setUser(usrObj);
                        ClientService.setLocalStorage(KEY.LOCAL.menu, JSON.stringify(resp.body.menu));
                        ClientService.setLocalStorage(KEY.LOCAL.features, JSON.stringify(resp.body.features));
                        
                        
                        if( resp.body.default_feature !== null && resp.body.default_feature !== "null" ){
                            var url = _baseurl_ + resp.body.default_feature.module + "/" + resp.body.default_feature.controller + "/" + resp.body.default_feature.action + "#!/" + resp.body.default_feature.component;
                            window.location.replace(url);
                        } else{
                            DialogBox.alert("Configuration warning", "Sorry, you have no default feature");
                            DialogBox.hideProgress();
                        }
                        
                        
                    }, function (err) {
                        console.log("mydata error", JSON.stringify(err));
                    });
                    
                };
            });
        </script>
        
        
    </body>
</html>
