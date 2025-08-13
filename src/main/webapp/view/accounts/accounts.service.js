app.factory('AccountsService', function (
        $http, $q, DialogBox, ToastService, Communication, $timeout
        ) {
    var commonSetupCache = null;

    return {

        getFilteredVouchers: function (api, searchCriteria, currentPage, itemPerPage) {
            var deferred = $q.defer();

            searchCriteria.from_date = formatToYMD(searchCriteria.from_date);
            searchCriteria.to_date = formatToYMD(searchCriteria.to_date);

            var fromDate = new Date(searchCriteria.from_date);
            var toDate = new Date(searchCriteria.to_date);

            if (fromDate > toDate) {
                deferred.reject("To date should be greater than or equal to from date!");
                return deferred.promise;
            }

            DialogBox.showProgress();

            $http.post(api + "/" + currentPage + "/" + itemPerPage, searchCriteria)
                .then(function (resp) {
                    DialogBox.hideProgress();

                    if (resp.data.code === 200) {
                        resp.data.body.items.forEach(function (master) {
                            master.showDetails = false;
                        });

                        ToastService.showMessages();
                        deferred.resolve(resp.data.body);
                    } else {
                        deferred.resolve({ items: [], itemCount: 0 });
                    }

                }, function (err) {
                    DialogBox.hideProgress();
                    deferred.reject(err);
                });

            return deferred.promise;
        },

        getVoucherNo: function (voucherType, query) {
            return $http.get('/accounts/journal_voucher/search-vouchers', {
                params: { voucherType: voucherType, query: query }
            }).then(function (resp) {
               return resp.data.body;
            });
        },

        getVoucherNoWithStatus: function (voucherType, voucherStatus, query) {
            return $http.get('/accounts/journal_voucher/search-vouchers', {
                params: {
                        voucherType: voucherType,
                        voucherStatus: voucherStatus,
                        query: query
                        }
            }).then(function (resp) {
               return resp.data.body;
            });
        },

        previewVoucher: function (id) {
            var xhttp = new XMLHttpRequest();
            xhttp.open("GET", API.ACC_VOUCHER_REPORT_VIEW + '/' + id, true);
            xhttp.setRequestHeader('x-aip-token', _shskr_);
            xhttp.responseType = 'blob';

            xhttp.onload = function () {
                if (xhttp.status === 200) {
                    var pdfResponse = new Blob([xhttp.response], { type: 'application/pdf' });
                    var fileURL = URL.createObjectURL(pdfResponse);
                    var link = document.createElement('a');
                    link.href = fileURL;
                    link.target = '_blank';
                    link.click();
                }
            };

            xhttp.send();
        },

        getVoucherDetails: function (journalMasterId) {
            var deferred = $q.defer();

            DialogBox.showProgress();
            $http.get(API.ACC_VOUCHER_DETAILS + "/" + journalMasterId)
                .then(function (resp) {
                    DialogBox.hideProgress();

                    if (resp.data.code === 200) {
                        deferred.resolve(resp.data.body);
                    } else {
                        deferred.resolve([]);
                    }
                }, function (err) {
                    DialogBox.hideProgress();
                    log("voucher details error", JSON.stringify(err));
                    deferred.resolve([]);
                });

            return deferred.promise;
        },

        calculateVoucherTotals: function (detailsList) {
            var totals = { debitTotal: 0, creditTotal: 0 };

            angular.forEach(detailsList, function (details) {
                if (details.primeAmount > 0) {
                    totals.debitTotal += details.amount;
                } else if (details.primeAmount < 0) {
                    totals.creditTotal += details.amount;
                }
            });

            return totals;
        },

//        form controller function
        getCoaList : function () {
            return Communication.request("GET", API.ACC_CHART_OF_ACCOUNTS_LIST_DROP_DOWN, {});
        },

        getCashBankCoa : function (usageType) {
            return Communication.request("GET", API.ACC_CHART_OF_ACCOUNTS_BY_USAGES_TYPE + '/' + usageType);
        },

        getSubAccountList : function (accountsSource, chartOfAccountsId) {
            return Communication.request("GET", API.ACC_GET_SUB_ACCOUNTS_LIST + '/' + accountsSource + '/' + chartOfAccountsId, {});
        },

        getBankAccountList : function () {
            return Communication.request("GET", API.BANK_ACCOUNT_LIST, {});
        },

        fetchPaginatedData : function (list, params, field = 'name') {
            const offset = params.page * params.limit;
            const search = params.search.toLowerCase();
            const limit = params.limit;

            return $q(function (resolve) {
                $timeout(function () {
                    let filtered = list;
                    if (search) {
                        filtered = filtered.filter(item =>
                            item[field].toLowerCase().includes(search)
                        );
                    }
                    const items = filtered.slice(offset, offset + limit);
                    resolve({
                        items: items,
                        hasMore: offset + limit < filtered.length
                    });
                }, 300);
            });
        },

//        voucher object generation

        createEntryObject: function (isCashBank, data) {
            const { paymentType, cash_bank_coa_obj, selectedCoa, selectedSubCoa, selectedBankAccount, voucher_details } = data;
            const isBank = paymentType === VOUCHER_KEY.PAYMENT_TYPE.BANK;
            const baseAmount = voucher_details.amount;
            const primeAmount = baseAmount * voucher_details.currencyRate;

            const obj = {
                chartOfAccountsId: isCashBank ? cash_bank_coa_obj.id : selectedCoa.id,
                chartOfAccountsCodeName: isCashBank ? cash_bank_coa_obj.coaNameAndCode : selectedCoa.coa_code_name,
                particulars: voucher_details.particulars,
                amount: baseAmount,
                primeAmount: isCashBank ? -primeAmount : primeAmount,
                baseAmount: isCashBank ? -baseAmount : baseAmount,
            };

            if (isCashBank && isBank) {
                obj.subAccountsId = selectedBankAccount.id;
                obj.subAccountsCodeName = selectedBankAccount.name;
            } else if (!isCashBank && selectedSubCoa) {
                obj.subAccountsId = selectedSubCoa.id;
                obj.subAccountsCodeName = selectedSubCoa.name;
            }

            return obj;
        },

        createVoucherEntryObject: function (isCashBank, data) {
            const { paymentType, cash_bank_coa_obj, selectedCoa, selectedSubCoa, selectedBankAccount, voucher_details, module } = data;
            const isBank = paymentType === VOUCHER_KEY.PAYMENT_TYPE.BANK;
            const baseAmount = voucher_details.amount;
            const primeAmount = baseAmount * voucher_details.currencyRate;

            const obj = {
                chartOfAccountsId: isCashBank ? cash_bank_coa_obj.id : selectedCoa.id,
                chartOfAccountsCodeName: isCashBank ? cash_bank_coa_obj.coaNameAndCode : selectedCoa.coa_code_name,
                particulars: voucher_details.particulars,
                amount: baseAmount,
                primeAmount: isCashBank ? -primeAmount : primeAmount,
                baseAmount: isCashBank ? -baseAmount : baseAmount,
                currencyType: "BDT",
                currencyRate: voucher_details.currencyRate || 1,
            };

            if (isCashBank && isBank) {
                obj.subAccountsId = selectedBankAccount.id;
                obj.subAccountsCodeName = selectedBankAccount.name;
                module.chequeStatus = VOUCHER_KEY.CHEQUE_STATUS.NOT_CLEARED;
            } else if (!isCashBank && selectedSubCoa) {
                obj.subAccountsId = selectedSubCoa.id;
                obj.subAccountsCodeName = selectedSubCoa.name;
            }

            return obj;
        },

        voucherAmount: function (voucher_details_list, currencyRate, table_debit_index, table_credit_index, module) {
            const total = voucher_details_list.reduce((sum, item) => {
                return item.primeAmount > 0 ? sum + item.primeAmount : sum;
            }, 0);

            const creditEntry = voucher_details_list[0];
            creditEntry.amount = total;
            creditEntry.primeAmount = -total * currencyRate;
            creditEntry.baseAmount = -total;
            module.amount = total;

            if (typeof $ !== 'undefined') {
                $("#amountInWords").val(amountToTextWithDecimal(total));
                $('table tfoot td').eq(table_debit_index).text(total);
                $('table tfoot td').eq(table_credit_index).text(total);
            }

            return total;
        },

        gridDataValidation : function (voucherDetails, selectedCoa, selectedSubCoa, growl) {
            if (!voucherDetails.chartOfAccountsId) {
                growl.error('Please select chart of accounts', { title: 'Error!' });
                return false;
            }

            if (selectedCoa?.accounts_source !== 'None' && !selectedSubCoa) {
                growl.error('Please select sub-accounts', { title: 'Error!' });
                return false;
            }

            if (!voucherDetails.particulars && commonSetupCache.body.detailParticularRequired) {
                growl.error('Please enter details particulars', { title: 'Error!' });
                return false;
            }

            if (!voucherDetails.amount) {
                growl.error('Please enter amount', { title: 'Error!' });
                return false;
            }

            return true;
        },

        clearVoucherInputFields : function (scope) {
            scope.selectedCoa = null;
            scope.selectedSubCoa = null;
            scope.voucher_details.particulars = "";
            scope.voucher_details.amount = "";
            scope.isSubAccHide = true;
            scope.particularsDivClassVar = "col-xs-12 col-md-4";
        },

        validateVoucherBeforeSave : function (module, selectedBankAccount, growl, VOUCHER_KEY) {
            if (!module.paymentType) {
                growl.error('Please select payment type', { title: 'Error!' });
                return false;
            }

            if (!module.particulars && commonSetupCache.body.masterParticularRequired) {
                growl.error('Please enter master particulars', { title: 'Error!' });
                return false;
            }

            if (module.paymentType === VOUCHER_KEY.PAYMENT_TYPE.BANK) {
                if (!selectedBankAccount) {
                    growl.error('Please select bank a/c', { title: 'Error!' });
                    return false;
                }
                if (!module.chequeNo) {
                    growl.error('Please enter cheque no', { title: 'Error!' });
                    return false;
                }
                if (!module.chequeDate) {
                    growl.error('Please enter cheque date', { title: 'Error!' });
                    return false;
                }
            }

            if (!module.details || module.details.length === 0) {
                growl.error('Please enter voucher details', { title: 'Error!' });
                return false;
            }

            const voucherAmount = module.details.reduce((sum, item) => {
                return sum + Number(item.baseAmount);
            }, 0);

            if (voucherAmount !== 0) {
                growl.error('Debit credit amount not equal', { title: 'Error!' });
                return false;
            }

            return true;
        },

//      update voucher

        buildDetailsObject: function (data) {
            return {
                id: data.id,
                chartOfAccountsId: data.chartOfAccountsId,
                chartOfAccountsCodeName: data.chartOfAccountsCodeName,
                subAccountsId: data.subAccountsId || '',
                subAccountsCodeName: data.subAccountsCodeName || '',
                particulars: data.particulars,
                amount: data.amount,
                primeAmount: data.primeAmount,
                baseAmount: data.baseAmount
            };
        },

        loadVoucherForEdit : function ($scope, $stateParams, Communication, API, log) {
            const self = this;
            return Communication.request("GET", API.ACC_DEBIT_VOUCHER_GET + '/' + $stateParams.id)
                .then(function (resp) {
                    log("voucher edit: " + JSON.stringify(resp));

                    if (resp.code === 200) {
                        $scope.module = resp.body;
                        $scope.module.chequeDate = new Date(resp.body.chequeDate);
                        $scope.module.voucherDate = new Date(resp.body.voucherDate);
                        $scope.voucher_details_list = resp.body.details.map(self.buildDetailsObject);
                        $scope.updateAmountInWords();

                        if ($scope.module.paymentType === VOUCHER_KEY.PAYMENT_TYPE.BANK) {
                            $scope.isBankHide = false;
                            return self.getBankAccountList().then(function () {
                                $scope.selectedBankAccount = $scope.bankAccountList.find(obj =>
                                    obj.id === $scope.module.bankAccountId);
                            });
                        } else {
                            $scope.isBankHide = true;
                        }
                    }
                })
                .catch(function (err) {
                    log("voucher edit error", JSON.stringify(err));
                });
        },

        populateRowData : function ($scope, rowData, rowIndex) {
            $scope.rowIndex = rowIndex;
            $scope.detailsAddBtnHide = true;
            $scope.detailsEditBtnHide = false;

            $scope.selectedCoa = $scope.coaList.find(obj => obj.id === rowData.chartOfAccountsId);

            Object.assign($scope.voucher_details, {
                id: rowData.id,
                particulars: rowData.particulars,
                amount: rowData.amount,
                chartOfAccountsId: rowData.chartOfAccountsId,
                chartOfAccountsSource: $scope.selectedCoa.accounts_source
            });

            if ($scope.selectedCoa.accounts_source === 'None' && $scope.selectedCoa.accounts_usage != 'Bank') {
                $scope.isSubAccHide = true;
                $scope.selectedSubCoa = null;
                $scope.voucher_details.subAccountsId = "";
                $scope.particularsDivClassVar = "col-xs-12 col-md-4";
            } else {
                return $scope.getSubAccountList($scope.selectedCoa.accounts_source, rowData.chartOfAccountsId)
                    .then(function () {
                        $scope.selectedSubCoa = $scope.subaccountList.find(obj => obj.id === rowData.subAccountsId);
                        $scope.voucher_details.subAccountsId = $scope.selectedSubCoa?.id;
                        $scope.isSubAccHide = false;
                        $scope.particularsDivClassVar = "col-xs-12 col-md-4";
                    });
            }
        },

        updateRow : function ($scope) {
            $scope.detailsAddBtnHide = false;
            $scope.detailsEditBtnHide = true;

            const updatedDetails = this.buildDetailsObject({
                id: $scope.voucher_details.id,
                chartOfAccountsId: $scope.selectedCoa.id,
                chartOfAccountsCodeName: $scope.selectedCoa.coa_code_name,
                subAccountsId: $scope.selectedSubCoa?.id,
                subAccountsCodeName: $scope.selectedSubCoa?.name,
                particulars: $scope.voucher_details.particulars,
                amount: $scope.voucher_details.amount,
                primeAmount: $scope.voucher_details.amount * $scope.voucher_details.currencyRate,
                baseAmount: $scope.voucher_details.amount,
            });

            $scope.voucher_details_list[$scope.rowIndex] = updatedDetails;
            this.syncModuleDetails($scope, updatedDetails);
            this.updateFirstRowAmount($scope);
            $scope.updateAmountInWords();
            $scope.clearVoucherInputFields();
        },

        syncModuleDetails : function ($scope, details) {
            let detailsObj = (details.id === undefined)
                ? $scope.module.details[$scope.rowIndex]
                : $scope.module.details.find(v => v.id === details.id);

            if (!detailsObj) return;

            Object.assign(detailsObj, {
                chartOfAccountsId: details.chartOfAccountsId,
                chartOfAccountsCodeName: details.chartOfAccountsCodeName,
                particulars: details.particulars,
                subAccountsId: details.subAccountsId || '',
                subAccountsCodeName: details.subAccountsCodeName || '',
                amount: details.amount,
                primeAmount: details.primeAmount,
                baseAmount: details.baseAmount
            });

            $scope.module.details[$scope.rowIndex] = detailsObj;
        },

        updateFirstRowAmount : function ($scope) {
            const voucherAmount = $scope.voucherAmount();
            const firstRow = $scope.module.details[0];

            firstRow.amount = voucherAmount;
            firstRow.primeAmount = (-1) * voucherAmount * $scope.voucher_details.currencyRate;
            firstRow.baseAmount = (-1) * voucherAmount;
        },

        deleteRow : function ($scope, index) {
            $scope.voucher_details_list.splice(index, 1);
            $scope.module.details.splice(index, 1);
            this.updateFirstRowAmount($scope);
            $scope.updateAmountInWords();
        },

//        Chart of accounts data
        getAccountsTypeList: function () {
            return [
                { id: 'Asset', value: 'Asset' },
                { id: 'Expenditure', value: 'Expenditure' },
                { id: 'Equity', value: 'Equity' },
                { id: 'Income', value: 'Income' },
                { id: 'Liability', value: 'Liability' }
            ];
        },
        getAccountsUsageList: function () {
            return [
                { id: 'Ledger', value: 'Ledger' },
                { id: 'Bank', value: 'Bank' },
                { id: 'Cash', value: 'Cash' },
                { id: 'AP', value: 'AP' },
                { id: 'AR', value: 'AR' }
            ];
        },
        getAccountsSourceList: function () {
            return [
                { id: 'None', value: 'None' },
                { id: 'Subaccount', value: 'Subaccount' },
                { id: 'Bank', value: 'Bank' },
                { id: 'Customer', value: 'Customer' },
                { id: 'Employee', value: 'Employee' },
                { id: 'Supplier', value: 'Supplier' }
            ];
        },

         getCommonSetup: function() {
            if (commonSetupCache) {
                return $q.resolve(commonSetupCache); // return cached data as resolved promise
            }
            return $http.get(API.ACC_DEFAULT_SETUP_GET)
                .then(function(resp) {
                    commonSetupCache = resp.data;
                    return commonSetupCache;
                });
        },

        parseDatesIfString: function (obj, fields) {
            fields.forEach(function (field) {
                if (typeof obj[field] === 'string' && obj[field].trim() !== '') {
                    let parsedDate = new Date(obj[field]);
                    if (!isNaN(parsedDate.getTime())) { // valid date
                        obj[field] = parsedDate;
                    }
                }
            });
        }
    };
});