
var app = angular.module('UserAuthManagementApp', ['rt.select2', 'ngPatternRestrict', '720kb.datepicker', 'ngDialog', 'ngToast', 'ui.router','ui.bootstrap', 'ngMaterial', 'ngMessages', 'ngSanitize', 'ngAnimate', 'angular-growl'])
    
    .config(function($stateProvider, $urlRouterProvider, ngToastProvider) {
        ngToastProvider.configure({
            animation: 'slide'
        });

        $stateProvider

        .state( JCOMPONENT.role_list_view, {
            url: '/role_list_view',
            cache: false,
            templateUrl: _NG_SRC_ + '/' + JMODULE_NAME +'/role/role_list.html',
            controller: 'RoleListCtrl'
        }).state(JCOMPONENT.role_add_view, {
            url: '/role_add_view',
            cache: false,
            templateUrl: _NG_SRC_ + '/' + JMODULE_NAME +'/role/role_form.html',
            controller: 'RoleFormCtrl'
            
        }).state(JCOMPONENT.role_update, {
            url: '/role_edit_view/:role_id',
            cache: false,
            templateUrl: _NG_SRC_ + '/' + JMODULE_NAME +'/role/role_form.html',
            controller: 'RoleFormCtrl'
        })
        .state(JCOMPONENT.role_feature_view, {
            url: '/role_feature_view',
            cache: false,
            templateUrl: _NG_SRC_ + '/' + JMODULE_NAME +'/role/role_feature.html',
            controller: 'RoleFeatureCtrl'
        })
        
        .state(JCOMPONENT.feature_list_view, {
            url: '/feature_list_view',
            cache: false,
            templateUrl: _NG_SRC_ + '/' + JMODULE_NAME +'/feature/feature_list.html',
            controller: 'FeatureListCtrl'
        })
        .state(JCOMPONENT.feature_add_view, {
            url: '/feature_add_view',
            cache: false,
            templateUrl: _NG_SRC_ + '/' + JMODULE_NAME +'/feature/feature_form.html',
            controller: 'FeatureFormCtrl'
            
        })
        .state(JCOMPONENT.feature_update, {
            url: '/feature_edit_view/:feature_id',
            cache: false,
            templateUrl: _NG_SRC_ + '/' + JMODULE_NAME +'/feature/feature_form.html',
            controller: 'FeatureFormCtrl'
        })
        .state("app_settings_view", {
            url: '/app_settings_view',
            cache: false,
            templateUrl: _NG_SRC_ + '/' + JMODULE_NAME +'/feature/app_settings.html',
            controller: 'AppSettingsCtrl'
        })
        .state("app_audit_view", {
            url: '/app_audit_view',
            cache: false,
            templateUrl: _NG_SRC_ + '/' + JMODULE_NAME +'/feature/app_audit_view.html',
            controller: 'AppAuditCtrl'
        })
        
        
        
        .state(JCOMPONENT.user_list_view, {
            url: '/user_list_view',
            cache: false,
            templateUrl: _NG_SRC_ + '/' + JMODULE_NAME +'/user/user_list.html',
            controller: 'UserListCtrl'
        })
        .state(JCOMPONENT.user_add_view, {
            url: '/user_add_view',
            cache: false,
            templateUrl: _NG_SRC_ + '/' + JMODULE_NAME +'/user/user_form.html',
            controller: 'UserFormCtrl'
            
        })
        .state(JCOMPONENT.user_edit_view, {
            url: '/user_edit_view/:user_id',
            cache: false,
            templateUrl: _NG_SRC_ + '/' + JMODULE_NAME +'/user/user_form.html',
            controller: 'UserFormCtrl'
        }).state(JCOMPONENT.user_profile_view, {
            url: '/user_profile_view',
            cache: false,
            templateUrl: _NG_SRC_ + '/' + JMODULE_NAME + '/user/user_profile.html',
            controller: 'UserProfileCtrl'
        }).state(JCOMPONENT.change_pwd_view, {
            url: '/change_pwd_view',
            cache: false,
            templateUrl: _NG_SRC_ + '/' + JMODULE_NAME + '/user/user_profile.html',
            controller: 'UserProfileCtrl'
        });
        
        $urlRouterProvider.otherwise('/' + JCOMPONENT.user_profile_view);
        
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