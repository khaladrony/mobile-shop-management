// bank-details.directive.js
app.directive('bankDetails', function () {
    return {
        restrict: 'E',
        scope: {
            module: '=',         // two-way binding for ng-model
            bankAccountList: '=', // input list
            isBankHide: '='      // hide/show the section
        },
        template: `
            <div ng-hide="isBankHide">
                <div class="col-xs-12 col-md-3">
                    <label>Bank A/C </label>
                    <select class="chosen-select form-control" id="bankAccountId" ng-model="module.bankAccountId">
                        <option value="">Choose Bank A/C</option>
                        <option ng-repeat="obj in bankAccountList track by $index" ng-value="obj.id">
                            {{obj.name}}
                        </option>
                    </select>
                </div>

                <div class="col-xs-12 col-md-3">
                    <label>Cheque No </label>
                    <input class="form-control" type="text" id="chequeNo" ng-model="module.chequeNo" placeholder="Cheque No" />
                </div>

                <div class="col-xs-12 col-md-3">
                    <label>Cheque Date </label>
                    <div class="input-group date" id="chequeDate">
                        <input type="text"
                               class="form-control"
                               placeholder="Cheque Date"
                               ng-model="module.chequeDate"
                               formatted-date-picker
                               ng-keydown="$event.preventDefault()" />
                        <span class="input-group-addon">
                            <i class="fa fa-calendar-check-o" style="color:#3C8DBC;"></i>
                        </span>
                    </div>
                </div>
            </div>
        `
    };
});

// payment-type-selector.directive.js
app.directive('paymentTypeSelector', function () {
    return {
        restrict: 'E',
        scope: {
            ngModel: '=',          // two-way binding for selected value
            onChange: '&'          // function binding
        },
        template: `
            <div>
                <label>Payment Type <span class="required">*</span></label>
                <select class="chosen-select form-control"
                        id="paymentType"
                        ng-model="ngModel"
                        ng-change="handleChange()">
                    <option value="">Choose Payment Type</option>
                    <option value="Bank">Bank</option>
                    <option value="Cash">Cash</option>
                </select>
            </div>
        `,
        link: function (scope, element) {
            scope.handleChange = function () {
                if (scope.onChange) {
                    scope.onChange({ paymentType: scope.ngModel });
                }
            };
        }
    };
});

//bank-info-section
app.directive('bankInfoSection', function () {
    return {
        restrict: 'E',
        scope: {
            selectedBankAccount: '=',
            module: '=',
            isBankHide: '=',
            fetchBankAccount: '&',
            onBankAccountChange: '&'
        },
        template: `
            <div class="col-xs-12 col-md-5" ng-hide="isBankHide">
                <label>Bank A/C</label>
                <lazy-dropdown
                    selected-item="selectedBankAccount"
                    display-property="name"
                    display-subtext="subLabel"
                    search-property="name"
                    placeholder="Search bank account..."
                    load-items="fetchBankAccount({params: params})"
                    on-select="onBankAccountChange({item: item})">
                </lazy-dropdown>
            </div>

            <div class="col-xs-12 col-md-3" ng-hide="isBankHide">
                <label>Cheque No</label>
                <input class="form-control"
                       type="text"
                       ng-model="module.chequeNo"
                       placeholder="Cheque No" />
            </div>

            <div class="col-xs-12 col-md-3" ng-hide="isBankHide">
                <label>Cheque Date</label>
                <div class="input-group date">
                    <input type="text"
                           class="form-control"
                           placeholder="Cheque Date"
                           ng-model="module.chequeDate"
                           formatted-date-picker
                           ng-keydown="$event.preventDefault()" />
                    <span class="input-group-addon">
                        <i class="fa fa-calendar-check-o" style="color:#3C8DBC;"></i>
                    </span>
                </div>
            </div>
        `
    };
});

//voucher-details-form
app.directive('voucherDetailsForm', function () {
    return {
        restrict: 'E',
        scope: {
            selectedCoa: '=',
            selectedSubCoa: '=',
            voucherDetails: '=',
            particularsDivClassVar: '=',
            isSubAccHide: '=',
            detailsAddBtnHide: '=',
            detailsEditBtnHide: '=',
            fetchChartOfAccounts: '&',
            fetchSubAccounts: '&',
            onChartOfAccountsChange: '&',
            onSubAccountChange: '&',
            addToGrid: '&',
            editToGrid: '&'
        },
        template: `
            <div class="widget-main">
                <div class="widget-header widget-header-flat">
                    <h4 class="widget-title" style="font-size:17px;">Voucher Details</h4>
                    <span class="widget-toolbar"> </span>
                </div>

                <div class="row">
                    <div class="col-xs-12 col-md-5">
                        <label>Chart of Accounts <span class="required">*</span></label>
                        <lazy-dropdown
                            selected-item="selectedCoa"
                            display-property="coa_code_name"
                            display-subtext="accounts_type"
                            search-property="coa_code_name"
                            placeholder="Search chart of accounts..."
                            load-items="fetchChartOfAccounts({params: params})"
                            on-select="onChartOfAccountsChange({item: item})">
                        </lazy-dropdown>
                    </div>

                    <div ng-class="particularsDivClassVar">
                        <label>Particulars <span class="required">*</span></label>
                        <input class="form-control"
                               type="text"
                               id="particulars"
                               ng-model="voucherDetails.particulars"
                               placeholder="Particulars" />
                    </div>

                    <div class="col-xs-12 col-md-2">
                        <label>Amount <span class="required">*</span></label>
                        <input class="form-control pastedString"
                               type="text"
                               id="amount"
                               ng-model="voucherDetails.amount"
                               placeholder="Amount"
                               maxlength="17"
                               onkeypress="return validateFloatKeyPress(this, event);" />
                    </div>

                    <br>

                    <div class="col-xs-12 col-md-1">
                        <action-button
                            label="Add"
                            icon="fa-check-circle"
                            type="btn-primary"
                            visible="!detailsAddBtnHide"
                            on-click="addToGrid()">
                        </action-button>

                        <action-button
                            label="Edit"
                            icon="fa-check-circle"
                            type="btn-yellow"
                            visible="!detailsEditBtnHide"
                            on-click="editToGrid()">
                        </action-button>
                    </div>
                </div>

                <div class="row">
                    <div class="col-xs-12 col-md-5" ng-hide="isSubAccHide">
                        <label>{{ voucherDetails.chartOfAccountsSource || 'Sub-account' }}<span class="required">*</span></label>
                        <lazy-dropdown
                            selected-item="selectedSubCoa"
                            display-property="name"
                            display-subtext="label"
                            search-property="name"
                            placeholder="Search {{ voucherDetails.chartOfAccountsSource || 'Sub Accounts' }}..."
                            load-items="fetchSubAccounts({params: params})"
                            on-select="onSubAccountChange({item: item})">
                        </lazy-dropdown>
                    </div>
                </div>

            </div>
        `
    };
});

// voucher-details-table.directive.js
app.directive('voucherDetailsTable', function () {
    return {
        restrict: 'E',
        scope: {
            voucherDetailsList: '=',
            onRowEdit: '&',
            onRowDelete: '&'
        },
        template: `
            <div class="widget-main">
                <div class="row">
                    <div class="col-md-12">
                        <table id="voucher-details-table" class="table table-bordered table-hover">
                            <thead>
                                <tr>
                                    <th>SL</th>
                                    <th ng-hide="true">ID</th>
                                    <th ng-hide="true">COA Id</th>
                                    <th ng-hide="true"></th>
                                    <th>A/C Code & Name</th>
                                    <th ng-hide="true">Accounts Source</th>
                                    <th>Particulars</th>
                                    <th>Debit</th>
                                    <th>Credit</th>
                                    <th>Actions</th>
                                </tr>
                            </thead>
                            <tbody>
                                <tr ng-repeat="details in voucherDetailsList">
                                    <td><label>{{$index + 1}}</label></td>
                                    <td ng-hide="true"><label>{{details.id}}</label></td>
                                    <td ng-hide="true"><label>{{details.chartOfAccountsId}}</label></td>
                                    <td ng-hide="true"><label>{{details.chartOfAccountsCodeName}}</label></td>
                                    <td>
                                        <label>
                                            {{details.subAccountsId > 0
                                                ? details.chartOfAccountsCodeName + " -> " + details.subAccountsCodeName
                                                : details.chartOfAccountsCodeName}}
                                        </label>
                                    </td>
                                    <td ng-hide="true"><label>{{details.chartOfAccountsSource}}</label></td>
                                    <td><label>{{details.particulars}}</label></td>
                                    <td align="right"><label>{{details.primeAmount > 0 ? details.amount : 0}}</label></td>
                                    <td align="right"><label>{{details.primeAmount < 0 ? details.amount : 0}}</label></td>
                                    <td style="width:130px">
                                        <input type="button"
                                               style="font-size:12px"
                                               class="btn btn-no-border btn-yellow btn-round"
                                               value="Edit"
                                               ng-if="$index > 0"
                                               ng-click="onRowEdit({details: details, index: $index})" />
                                        <input type="button"
                                               style="font-size:12px"
                                               class="btn btn-no-border btn-danger btn-round"
                                               value="Delete"
                                               ng-if="$index > 0"
                                               ng-click="onRowDelete({index: $index})" />
                                    </td>
                                </tr>
                            </tbody>
                            <tfoot>
                                <tr style="font-weight:bold">
                                    <td></td>
                                    <td></td>
                                    <td>Total</td>
                                    <td align="right"></td>
                                    <td align="right"></td>
                                    <td></td>
                                </tr>
                            </tfoot>
                        </table>
                    </div>
                </div>
            </div>
        `
    };
});

//  radio-group for char of accounts
app.directive('radioGroup', function () {
    return {
        restrict: 'E',
        scope: {
            label: '@',
            required: '@',
            options: '=',
            model: '=',
            onChange: '&',
            name: '@'
        },
        template: `
            <label>{{label}} <span ng-if="required === 'true'" class="required">*</span></label><br>
            <div class="form-inline">
                <label ng-repeat="opt in options" class="radio-inline">
                    <input class="ace"
                           type="radio"
                           name="{{name}}"
                           value="{{opt.value}}"
                           ng-model="model"
                           ng-change="onChange({value: opt.value})" />
                    <span class="lbl">{{opt.value}}</span>
                </label>
            </div>
        `
    };
});

app.directive('coaTree', function () {
  return {
    restrict: 'E',
    scope: {
      data: '=' // Bind the tree data
    },
    template: `
      <ul class="coa-tree">
        <li ng-repeat="node in data" ng-include="'/view/accounts/directives/tree_item_renderer.html'"></li>
      </ul>
    `,
    controller: function ($scope) {
      $scope.toggle = function (node) {
        node.collapsed = !node.collapsed;
      };
    }
  };
});



