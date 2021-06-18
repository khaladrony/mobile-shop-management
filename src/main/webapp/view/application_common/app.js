
var app = angular.module('ApplicationCommonApp', ['rt.select2', 'ngPatternRestrict', '720kb.datepicker', 'ngDialog', 'ngToast', 'ui.router','ui.bootstrap', 'ngMaterial', 'ngMessages', 'ngSanitize', 'ngAnimate', 'angular-growl'])

    .config(function($stateProvider, $urlRouterProvider, ngToastProvider, growlProvider) {
        growlProvider.globalTimeToLive(5000);

        ngToastProvider.configure({
            animation: 'slide'
        });

        $stateProvider

        .state( JCOMPONENT.bank_info_add_view, {
            url: '/bank_info_add_view',
            cache: false,
            templateUrl: _NG_SRC_ + '/' + JMODULE_NAME +'/bank_info/bank_info_form.html',
            controller: 'BankInfoFormCtrl'
        }).state(JCOMPONENT.bank_info_update_view, {
            url: '/bank_info_update_view/:id',
            cache: false,
            templateUrl: _NG_SRC_ + '/' + JMODULE_NAME + '/bank_info/bank_info_form.html',
            controller: 'BankInfoFormCtrl'
        }).state(JCOMPONENT.bank_info_list_view, {
            url: '/bank_info_list_view',
            cache: false,
            templateUrl: _NG_SRC_ + '/' + JMODULE_NAME + '/bank_info/bank_info_list.html',
            controller: 'BankInfoListCtrl'
        })

        .state( JCOMPONENT.supplier_info_add_view, {
            url: '/supplier_info_add_view',
            cache: false,
            templateUrl: _NG_SRC_ + '/' + JMODULE_NAME +'/supplier_info/supplier_info_form.html',
            controller: 'SupplierInfoFormCtrl'
        }).state(JCOMPONENT.supplier_info_update_view, {
            url: '/supplier_info_update_view/:id',
            cache: false,
            templateUrl: _NG_SRC_ + '/' + JMODULE_NAME + '/supplier_info/supplier_info_form.html',
            controller: 'SupplierInfoFormCtrl'
        }).state(JCOMPONENT.supplier_info_list_view, {
            url: '/supplier_info_list_view',
            cache: false,
            templateUrl: _NG_SRC_ + '/' + JMODULE_NAME + '/supplier_info/supplier_info_list.html',
            controller: 'SupplierInfoListCtrl'
        })

        .state( JCOMPONENT.customer_info_add_view, {
            url: '/customer_info_add_view',
            cache: false,
            templateUrl: _NG_SRC_ + '/' + JMODULE_NAME +'/customer_info/customer_info_form.html',
            controller: 'CustomerInfoFormCtrl'
        }).state(JCOMPONENT.customer_info_update_view, {
            url: '/customer_info_update_view/:id',
            cache: false,
            templateUrl: _NG_SRC_ + '/' + JMODULE_NAME + '/customer_info/customer_info_form.html',
            controller: 'CustomerInfoFormCtrl'
        }).state(JCOMPONENT.customer_info_list_view, {
            url: '/customer_info_list_view',
            cache: false,
            templateUrl: _NG_SRC_ + '/' + JMODULE_NAME + '/customer_info/customer_info_list.html',
            controller: 'CustomerInfoListCtrl'
        })

        .state( JCOMPONENT.employee_info_add_view, {
            url: '/employee_info_add_view',
            cache: false,
            templateUrl: _NG_SRC_ + '/' + JMODULE_NAME +'/employee_info/employee_info_form.html',
            controller: 'EmployeeInfoFormCtrl'
        }).state(JCOMPONENT.employee_info_update_view, {
            url: '/employee_info_update_view/:id',
            cache: false,
            templateUrl: _NG_SRC_ + '/' + JMODULE_NAME + '/employee_info/employee_info_form.html',
            controller: 'EmployeeInfoFormCtrl'
        }).state(JCOMPONENT.employee_info_list_view, {
            url: '/employee_info_list_view',
            cache: false,
            templateUrl: _NG_SRC_ + '/' + JMODULE_NAME + '/employee_info/employee_info_list.html',
            controller: 'EmployeeInfoListCtrl'
        });
        
        $urlRouterProvider.otherwise('/' + JCOMPONENT.bank_info_list_view);
        
    });
    