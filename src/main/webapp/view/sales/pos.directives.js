app.directive('modalBase', function ($timeout) {
    return {
        restrict: 'E',
        transclude: true,
        scope: {
            modalId: '@',
            modalSize: '@?',
            onHide: '&?' // callback when modal closes
        },
        template: `
          <div class="modal fade" id="{{modalId}}" tabindex="-1" role="dialog">
            <div class="modal-dialog {{modalSize || ''}}" role="document">
              <div class="modal-content" ng-transclude></div>
            </div>
          </div>
        `,
        link: function (scope, element, attrs) {
            $(element).on('hidden.bs.modal', function () {
                if (scope.onHide) {
                    $timeout(function () {
                        scope.onHide();
                    }, 0);
                }
            });
        }
    };
});

/*Payment Modal*/
app.directive('paymentModal', function () {
    return {
        restrict: 'E',
        templateUrl: _NG_SRC_ +  '/' + JMODULE_NAME + '/'  + 'templates/payment-modal.html',
        scope: false   // shares parent controller scope
    };
});

/*Transaction Modal*/
app.directive('transactionModal', function () {
    return {
        restrict: 'E',
        templateUrl: _NG_SRC_ +  '/' + JMODULE_NAME + '/'  + 'templates/transaction-modal.html',
        scope: false   // shares parent controller scope
    };
});

/*Customer Modal*/
app.directive('customerModal', function () {
    return {
        restrict: 'E',
        templateUrl: _NG_SRC_ +  '/' + JMODULE_NAME + '/'  + 'templates/customer-modal.html',
        scope: false   // shares parent controller scope
    };
});

/*POS Return Modal*/
app.directive('posReturnModal', function () {
    return {
        restrict: 'E',
        templateUrl: _NG_SRC_ +  '/' + JMODULE_NAME + '/'  + 'templates/pos-return-modal.html',
        scope: false   // shares parent controller scope
    };
});


