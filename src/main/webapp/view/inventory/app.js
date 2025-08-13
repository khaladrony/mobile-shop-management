
var app = angular.module('InventoryManagementApp', ['rt.select2', 'ngPatternRestrict',
                        '720kb.datepicker', 'ngDialog', 'ngToast', 'ui.router','ui.bootstrap',
                         'ngMaterial', 'ngMessages', 'ngSanitize', 'ngAnimate', 'angular-growl'])

    .config(function($stateProvider, $urlRouterProvider, ngToastProvider, growlProvider) {
        growlProvider.globalTimeToLive(5000);

        ngToastProvider.configure({
            animation: 'slide'
        });

        $stateProvider

        .state(JCOMPONENT.item_master_add_view, {
            url: '/item_master_add_view',
            cache: false,
            templateUrl: _NG_SRC_ + '/' + JMODULE_NAME + '/' + JCONTROLLER.ITEM_MASTER + '/' + 'item_master_form.html',
            controller: 'ItemMasterFormCtrl'
        }).state(JCOMPONENT.item_master_update_view, {
            url: '/item_master_update_view/:id',
            cache: false,
            templateUrl: _NG_SRC_ + '/' + JMODULE_NAME + '/' + JCONTROLLER.ITEM_MASTER + '/' + 'item_master_form.html',
            controller: 'ItemMasterFormCtrl'
        }).state(JCOMPONENT.item_master_list_view, {
            url: '/item_master_list_view',
            cache: false,
            templateUrl: _NG_SRC_ + '/' + JMODULE_NAME + '/' + JCONTROLLER.ITEM_MASTER + '/' + 'item_master_list.html',
            controller: 'ItemMasterListCtrl'
        })

        .state(JCOMPONENT.inventory_movement_add_view, {
            url: '/inventory_movement_add_view',
            cache: false,
            templateUrl: _NG_SRC_ + '/' + JMODULE_NAME + '/' + JCONTROLLER.INVENTORY_MOVEMENT + '/' + 'inventory_movement_form.html',
            controller: 'InventoryMovementFormCtrl'
        }).state(JCOMPONENT.inventory_movement_update_view, {
            url: '/inventory_movement_update_view/:id',
            cache: false,
            templateUrl: _NG_SRC_ + '/' + JMODULE_NAME + '/' + JCONTROLLER.INVENTORY_MOVEMENT + '/' + 'inventory_movement_form.html',
            controller: 'InventoryMovementFormCtrl'
        }).state(JCOMPONENT.inventory_movement_list_view, {
            url: '/inventory_movement_list_view',
            cache: false,
            templateUrl: _NG_SRC_ + '/' + JMODULE_NAME + '/' + JCONTROLLER.INVENTORY_MOVEMENT + '/' + 'inventory_movement_list.html',
            controller: 'InventoryMovementListCtrl'
        })

        .state(JCOMPONENT.inventory_movement_issue_add_view, {
            url: '/inventory_movement_issue_add_view',
            cache: false,
            templateUrl: _NG_SRC_ + '/' + JMODULE_NAME + '/' + JCONTROLLER.INVENTORY_MOVEMENT_ISSUE + '/' + 'inventory_movement_issue_form.html',
            controller: 'InventoryMovementIssueFormCtrl'
        }).state(JCOMPONENT.inventory_movement_issue_update_view, {
            url: '/inventory_movement_issue_update_view/:id',
            cache: false,
            templateUrl: _NG_SRC_ + '/' + JMODULE_NAME + '/' + JCONTROLLER.INVENTORY_MOVEMENT_ISSUE + '/' + 'inventory_movement_issue_form.html',
            controller: 'InventoryMovementIssueFormCtrl'
        }).state(JCOMPONENT.inventory_movement_issue_list_view, {
            url: '/inventory_movement_issue_list_view',
            cache: false,
            templateUrl: _NG_SRC_ + '/' + JMODULE_NAME + '/' + JCONTROLLER.INVENTORY_MOVEMENT_ISSUE + '/' + 'inventory_movement_issue_list.html',
            controller: 'InventoryMovementIssueListCtrl'
        })

        .state(JCOMPONENT.inventory_movement_transfer_add_view, {
            url: '/inventory_movement_transfer_add_view',
            cache: false,
            templateUrl: _NG_SRC_ + '/' + JMODULE_NAME + '/' + JCONTROLLER.INVENTORY_MOVEMENT_TRANSFER + '/' + 'inventory_movement_transfer_form.html',
            controller: 'InventoryMovementTransferFormCtrl'
        }).state(JCOMPONENT.inventory_movement_transfer_update_view, {
            url: '/inventory_movement_transfer_update_view/:id',
            cache: false,
            templateUrl: _NG_SRC_ + '/' + JMODULE_NAME + '/' + JCONTROLLER.INVENTORY_MOVEMENT_TRANSFER + '/' + 'inventory_movement_transfer_form.html',
            controller: 'InventoryMovementTransferFormCtrl'
        }).state(JCOMPONENT.inventory_movement_transfer_list_view, {
            url: '/inventory_movement_transfer_list_view',
            cache: false,
            templateUrl: _NG_SRC_ + '/' + JMODULE_NAME + '/' + JCONTROLLER.INVENTORY_MOVEMENT_TRANSFER + '/' + 'inventory_movement_transfer_list.html',
            controller: 'InventoryMovementTransferListCtrl'
        })

        .state(JCOMPONENT.inventory_movement_posting_list_view, {
            url: '/inventory_movement_posting_list_view',
            cache: false,
            templateUrl: _NG_SRC_ + '/' + JMODULE_NAME + '/' + JCONTROLLER.INVENTORY_MOVEMENT_POSTING + '/' +  'inventory_movement_posting_list.html',
            controller: 'InventoryMovementPostingListCtrl'
        })

         .state(JCOMPONENT.inventory_movement_item_ledger_form_view, {
            url: '/inventory_movement_item_ledger_form_view',
            cache: false,
            templateUrl: _NG_SRC_ + '/' + JMODULE_NAME + '/' + JCONTROLLER.INVENTORY_REPORT + '/' + 'item_ledger_report.form.html',
            controller: 'InventoryReportItemLedgerFormCtrl'
        });

        $urlRouterProvider.otherwise('/' + JCOMPONENT.item_master_list_view);
    });

app.run(function($transitions, $state, $timeout, growl, $rootScope) {

    $transitions.onStart({}, function(transition) {
        const toState = transition.to();
        const module = toState.data?.module || JMODULE_NAME;
        const component = toState.name;

        if (typeof $rootScope.hasPermission === 'function') {
            const hasAccess = $rootScope.hasPermission(module, component);

            if (!hasAccess) {
                growl.error('You do not have permission to access this page.', { title: 'Access Denied' });

                $timeout(function () {
                    window.location.href = _baseurl_ + 'auth/login';
                }, 1500);
            }
        }
    });
});