/* 
 * Created on : 24 May, 2018, 10:19:44 PM
 * Author     : sarker
 */

var app = angular.module(
            'DashboardViewApp',
            ['rt.select2','720kb.datepicker', 'ngDialog', 'ngToast', 'ui.router',
            'ui.bootstrap', 'ngMaterial', 'ngMessages', 'ngSanitize', 'ngAnimate', 'angular-growl'])
    
    .config(function($stateProvider, $urlRouterProvider, ngToastProvider) {
        ngToastProvider.configure({
            animation: 'slide'
        });

        $stateProvider

        .state( JCOMPONENT.dashboard_static_view, {
            url: '/dashboard_static_view',
            cache: false,
            templateUrl: _NG_SRC_ + '/' + JMODULE_NAME +'/static_view/dashboard_static_view.html',
            controller: 'DashboardStaticViewCtrl'
        });
        
        $urlRouterProvider.otherwise('/' + JCOMPONENT.dashboard_static_view);
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

    