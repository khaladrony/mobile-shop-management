
var app = angular.module('PosApp', ['rt.select2', 'ngPatternRestrict',
                        '720kb.datepicker', 'ngDialog', 'ngToast', 'ui.router','ui.bootstrap',
                         'ngMaterial', 'ngMessages', 'ngSanitize', 'ngAnimate', 'angular-growl'])

    .config(function($stateProvider, $urlRouterProvider, ngToastProvider, growlProvider) {
        growlProvider.globalTimeToLive(5000);

        ngToastProvider.configure({
            animation: 'slide'
        });

        $stateProvider

        .state(JCOMPONENT.pos_add_view, {
            url: '/pos_add_view',
            cache: false,
            templateUrl: _NG_SRC_ + '/' + JMODULE_NAME + '/' + JCONTROLLER.POS + '/' + 'pos_form.html',
            controller: 'PosFormCtrl'
        }).state(JCOMPONENT.pos_update_view, {
            url: '/pos_update_view/:id',
            cache: false,
            templateUrl: _NG_SRC_ + '/' + JMODULE_NAME + '/' + JCONTROLLER.POS + '/' + 'pos_form.html',
            controller: 'PosFormCtrl'
        }).state(JCOMPONENT.pos_list_view, {
            url: '/pos_list_view',
            cache: false,
            templateUrl: _NG_SRC_ + '/' + JMODULE_NAME + '/' + JCONTROLLER.POS + '/' + 'pos_list.html',
            controller: 'PosFormCtrl'
        })

        .state(JCOMPONENT.pos_dashboard_view, {
            url: '/pos_dashboard_view',
            cache: false,
            templateUrl: _NG_SRC_ + '/' + JMODULE_NAME + '/' + JCONTROLLER.POS_DASHBOARD + '/' + 'pos_dashboard_form.html',
            controller: 'PosDashboardFormCtrl'
        })

        .state(JCOMPONENT.pos_daily_sales_report_view, {
            url: '/pos_daily_sales_report_view',
            cache: false,
            templateUrl: _NG_SRC_ + '/' + JMODULE_NAME + '/' + JCONTROLLER.POS_REPORT + '/' + 'daily_sales_report.form.html',
            controller: 'PosReportFormCtrl'
        });

        $urlRouterProvider.otherwise('/' + JCOMPONENT.pos_list_view);
    });

app.run(function($transitions, $state, $timeout, growl, $rootScope) {

    $transitions.onStart({}, function(transition) {
        // If user is not logged in
        if (!$rootScope._USER_ID_ || $rootScope._USER_ID_ === '') {
            $timeout(function () {
                window.location.href = COMMON_API.login_url;
            }, 0);
            return false; // cancel transition
        }

        // Permission check
        const toState = transition.to();
        const module = toState.data?.module || JMODULE_NAME;
        const component = toState.name;

        if (typeof $rootScope.hasPermission === 'function') {
            const hasAccess = $rootScope.hasPermission(module, component);

            if (!hasAccess) {
                growl.error('You do not have permission to access this page.', { title: 'Access Denied' });

                $timeout(function () {
                    window.location.href = COMMON_API.login_url;
                }, 1500);
            }
        }
    });
});