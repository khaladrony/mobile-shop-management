
var app = angular.module('InventoryManagementApp', ['rt.select2', 'ngPatternRestrict', '720kb.datepicker', 'ngDialog', 'ngToast', 'ui.router','ui.bootstrap', 'ngMaterial', 'ngMessages', 'ngSanitize', 'ngAnimate', 'angular-growl'])

    .config(function($stateProvider, $urlRouterProvider, ngToastProvider, growlProvider) {
        growlProvider.globalTimeToLive(5000);

        ngToastProvider.configure({
            animation: 'slide'
        });

        $stateProvider

        .state(JCOMPONENT.item_master_add_view, {
            url: '/item_master_add_view',
            cache: false,
            templateUrl: _NG_SRC_ + '/' + JMODULE_NAME + '/item_master/item_master_form.html',
            controller: 'ItemMasterFormCtrl'
        }).state(JCOMPONENT.item_master_update_view, {
            url: '/item_master_update_view/:id',
            cache: false,
            templateUrl: _NG_SRC_ + '/' + JMODULE_NAME + '/item_master/item_master_form.html',
            controller: 'ItemMasterFormCtrl'
        }).state(JCOMPONENT.item_master_list_view, {
            url: '/item_master_list_view',
            cache: false,
            templateUrl: _NG_SRC_ + '/' + JMODULE_NAME + '/item_master/item_master_list.html',
            controller: 'ItemMasterListCtrl'
        })

        $urlRouterProvider.otherwise('/' + JCOMPONENT.item_master_list_view);
        
    });
    