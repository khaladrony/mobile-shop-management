/* 
 * Created on : 24 May, 2018, 10:19:44 PM
 * Author     : sarker
 */

var app = angular.module('DashboardViewApp', ['rt.select2','720kb.datepicker', 'ngDialog', 'ngToast', 'ui.router','ui.bootstrap', 'ngMaterial', 'ngMessages', 'ngSanitize', 'ngAnimate'])
    
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
        })
        
        
        ;
        
        $urlRouterProvider.otherwise('/' + JCOMPONENT.dashboard_static_view);
        
    });
    