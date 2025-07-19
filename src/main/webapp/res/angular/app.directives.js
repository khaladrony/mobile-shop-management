app.run(function ($rootScope, $window, ClientService, $timeout, $sce, $q, $compile) {
    $rootScope.JMODULE_NAME = JMODULE_NAME;
    $rootScope.JCONTROLLER = JCONTROLLER;
    $rootScope.JCOMPONENT = JCOMPONENT;
    $rootScope._ROLE_NAME_ = _ROLE_NAME_;
    $rootScope._USER_ID_ = _USER_ID_;
    $rootScope._STATIC_RES_ = _STATIC_RES_;
    
    $rootScope.LOG_STATUS = [
        {"val":-1, "name": "In-queue"},
        {"val":0, "name": "Downloaded"},
        {"val":1, "name": "Partial"},
        {"val":2, "name": "Failed"}
    ];
    $rootScope.getLogStatus = function(status) {
        if( status === -1 ) return "In-queue";
        else if( status === 0 ) return "Downloaded";
        else if( status === 1 ) return "Partial";
        else if( status === 2 ) return "Failed";
        else return "Unknown";
    };
    
    $rootScope.hasPermission = function (module, component) {
        return ClientService.hasPermission(module, null, component);
    };
    $rootScope.featureName = function (module, component) {
        return ClientService.featureName(module, component);
    };
    $rootScope.setPageName = function (module, component) {
        $rootScope.page_name = ClientService.featureName(module, component);
        angular.element('#page_name').html($rootScope.page_name);
    };
    $rootScope.toastSuccess = function (messaage) {
        $rootScope.toast(1, messaage);
    };
    $rootScope.toastError = function (messaage) {
        $rootScope.toast(0, messaage);
    };
    $rootScope.toastWarning = function (messaage) {
        $rootScope.toast(2, messaage);
    };
    $rootScope.toast = function (type, messaage) {
        // angular.element(type === 1 ? '#operation_scs_alert' : '#operation_dng_alert').removeClass('hide');
        // angular.element(type === 1 ? '#operation_scs_msg' : '#operation_dng_msg').html(messaage);
        // angular.element(type === 1 ? '#operation_scs_alert' : '#operation_dng_alert').addClass('hide');

        angular.element(type ? '.page-content #operation_scs_alert' : '.page-content #operation_dng_alert').removeClass('hide');
        angular.element(type ? '.page-content #operation_scs_msg'   : '.page-content #operation_dng_msg').html(messaage);

        $timeout(function () {
            angular.element('div.alert').addClass('hide');
        }, TOAST_TIMEOUT);
    };

    $rootScope.log = function (tag, messaage) {
        ClientService.log(tag, messaage);
    };
    
    $timeout(function(){
        $window.location.href = _baseurl_ + '/auth/login';
    },_SESSION_TIMEOUT_);

});

app.directive('sarkerPagination', function () {
    return {
        restrict: 'EA',
        scope: {
            itemCount: '=',
            itemPerPage: '=',
            selectedPage: '=',
            linkCount: '=',
            getItems: '&'
        },
        controller: function ($scope) {
            $scope.showCurrentPageItems = function (pageNum) {
                $scope.getItems({pageNumber: pageNum, itemPerPage: $scope.itemPerPage});
            };

            $scope.isShowalble = function (selectedPage, buttonSL, totalLink) {
                if (selectedPage === buttonSL)
                    return true;

                var showing = 7 / 2, start, end; // button links

                start = selectedPage - showing;
                end = selectedPage + showing;

                // calculate link button presence
                if (start < 1) {
                    if (start === 0)
                        start = -1;
                    end += (start * -1);
                    start = 1;
                }
                if (end > totalLink) {
                    start -= (end - totalLink);
                    end = totalLink;
                }


                // validate link button presence
                if (start < 1) {
                    start = 1;
                }
                if (end > totalLink) {
                    end = totalLink;
                }

                if (buttonSL >= start && buttonSL <= end) {
                    return true;
                }

                /*if( selectedPage - 1 === buttonSL || selectedPage + 1 === buttonSL ) return true;
                 if( selectedPage - 2 === buttonSL || selectedPage + 2 === buttonSL ) return true;
                 if( selectedPage - 3 === buttonSL || selectedPage + 3 === buttonSL ) return true;*/

                return false;
            };
        },
        template: '<ul class="pagination pagination-md" style="float:left;margin: 10px 5px;" >\n\
                        <li><a href ng-click="showCurrentPageItems(1)"><i class="fa fa-step-backward"></i></a></li>\n\
                        <li ng-if="selectedPage===1" class="disabled" ><a><i class="fa fa-caret-left"></i></a></li>\n\
                        <li ng-if="selectedPage>1" ><a href ng-click="showCurrentPageItems( selectedPage-1 )"><i class="fa fa-caret-left"></i></a></li>\n\
                        <li ng-repeat="n in [].constructor( (itemCount/itemPerPage) | ceil ) track by $index" ng-class="{\'active\':selectedPage===($index+1)}" ng-if="isShowalble( selectedPage, ($index+1), ((itemCount/itemPerPage) | ceil) ) ">\n\
                            <a href ng-click="showCurrentPageItems($index+1)">{{ $index+1 }}</a>\n\
                        </li>\n\
                        <li ng-if="selectedPage===((itemCount/itemPerPage) | ceil)" class="disabled"><a><i class="fa fa-caret-right"></i></a></li>\n\
                        <li ng-if="selectedPage<((itemCount/itemPerPage) | ceil)"><a href ng-click="showCurrentPageItems( selectedPage+1 )"><i class="fa fa-caret-right"></i></a></li>\n\
                        <li><a href ng-click="showCurrentPageItems( (itemCount/itemPerPage) | ceil )"><i class="fa fa-step-forward"></i></a></li>\n\
                    </ul><span style="float:left;margin: 10px 5px;line-height: 35px;">Showing {{itemPerPage*(selectedPage-1) + 1}} - {{itemPerPage*(selectedPage-1) + itemPerPage}} of {{itemCount}} records<span>'
    };
});

app.filter('ceil', function () {
    return function (input) {
        return Math.ceil(input);
    };
});

app.directive('onFinishRender', function ($timeout) {
    return {
        restrict: 'A',
        link: function (scope, element, attr) {
            if (scope.$last === true) {
                $timeout(function () {
                    scope.$emit(attr.onFinishRender);
                });
            }
        }
    };
});


app.directive('pressEnter', function () {
    return function (scope, element, attrs) {
        element.bind("keypress", function (event) { //keydown keypress
            if (event.which === 13) {
                scope.$apply(function () {
                    scope.$eval(attrs.pressEnter);
                });

                event.preventDefault();
            }
        });
    };
});


app.directive('dateTimePicker', function () {
    return {
        restrict: "EA",
        require: "ng-model",
        link: function (scope, element, attrs, ngModelCtrl) {
            var parent = $(element).parent();
            var dtp = parent.datetimepicker({
                format: "YYYY-MM-DD HH:mm",
                showTodayButton: false,
                sideBySide: true,
                stepping: 5,
                useStrict:true,
                //date: moment(new Date()).format("YYYY-MM-DD HH:mm"),
                useCurrent: false
            });
            dtp.on("dp.change", function (e) {
                if (e.oldDate === null) {
                    var d = moment(e.date).format("YYYY-MM-DD") + ' ' + moment(new Date()).format("HH:mm");
                    $(this).data('DateTimePicker').date(moment(d).format("YYYY-MM-DD HH:mm"));
                    ngModelCtrl.$setViewValue(moment(d).format("YYYY-MM-DD HH:mm"));
                } else {
                    ngModelCtrl.$setViewValue(moment(e.date).format("YYYY-MM-DD HH:mm"));
                }
                scope.$apply();
            });
        }
    };
});
app.directive('datePicker', function () {
    return {
        restrict: "EA",
        require: "ng-model",
        link: function (scope, element, attrs, ngModelCtrl) {
            var parent = $(element).parent();
            var dtp = parent.datetimepicker({
                format: "YYYY-MM-DD",
                showTodayButton: false,
                sideBySide: false,
                 useStrict:true,
                useCurrent: false
            });
            dtp.on("dp.change", function (e) {
                ngModelCtrl.$setViewValue(moment(e.date).format("YYYY-MM-DD"));
                scope.$apply();
            });
        }
    };
});

app.directive('formattedDatePicker', function($timeout, $filter) {
    return {
        restrict: "EA",
        require: "ng-model",
        link: function (scope, element, attrs, ngModelCtrl) {
            var parent = $(element).parent();
            var dtp = parent.datetimepicker({
                format: "DD-MM-YYYY",
                showTodayButton: false,
                sideBySide: false,
                useStrict:true,
                useCurrent: false
            });

            // Set model to real Date
            dtp.on("dp.change", function (e) {
                if (e.date && e.date.isValid()) {
                    // Convert Moment object to JS Date
                    const jsDate = e.date.toDate();
                    ngModelCtrl.$setViewValue(jsDate);
                } else {
                    ngModelCtrl.$setViewValue(null);
                }
                scope.$apply();
            });

            // Format view value for display
            ngModelCtrl.$formatters.push(function (modelValue) {
                if (modelValue) {
                    return $filter('date')(modelValue, 'dd-MM-yyyy');
                }
                return '';
            });
        }
    };
});

app.directive('fileModel', ['$parse', function ($parse) {
        return {
            restrict: 'A',
            link: function (scope, element, attrs) {
                var model = $parse(attrs.fileModel);
                var modelSetter = model.assign;

                element.bind('change', function () {
                    scope.$apply(function () {
                        modelSetter(scope, element[0].files[0]);
                    });
                });
            }
        };
    }]);

app.directive('appcodeDropdown', function () {
    return {
        restrict: 'E',
        scope: {
            type: '@',
            model: '=',
            placeholder: '@',
            exclude: '=?'
        },
        template: `
            <select class="form-control" ng-model="model"
                    ng-options="option for option in filteredOptions">
                <option value="">
                    -- {{ placeholder ? placeholder : 'Select ' + type }} --
                </option>
            </select>
        `,
        controller: function ($scope, $http) {
            const url = _baseurl_ + "application_common/app_codes";

            $http.get(url).then(function (resp) {
                if (resp.data.code === 200) {
                    const list = resp.data.body || [];

                    $scope.options = [...new Set(
                        list
                            .filter(item => item.xtype === $scope.type)
                            .map(item => item.xcode)
                    )];
                }
            }, function (err) {
                console.error("App codes error", err);
            });

            function updateFiltered() {
                if ($scope.exclude) {
                    $scope.filteredOptions = $scope.options.filter(opt => opt !== $scope.exclude);
                } else {
                    $scope.filteredOptions = $scope.options.slice();
                }
            }
            $scope.$watchGroup(['options', 'exclude'], updateFiltered);
        }
    };
});

app.directive('autocomplete', function($timeout, $sce) {
    return {
        restrict: 'E',
        scope: {
            ngModel: '=',
            fetchSuggestions: '&',
            placeholder: '@'
        },
        template: `
            <div>
                <input type="text" class="form-control"
                       ng-model="ngModel"
                       ng-change="onInputChange()"
                       ng-blur="hideDropdown()"
                       ng-focus="onInputChange()"
                       placeholder="{{ placeholder }}" />

                <div class="autocomplete-dropdown" ng-show="suggestions.length && dropdownVisible">
                    <div class="autocomplete-item"
                         ng-repeat="suggestion in suggestions"
                         ng-click="selectSuggestion(suggestion)">
                        <span ng-bind-html="highlightMatch(suggestion, ngModel)"></span>
                    </div>

                </div>
            </div>
        `,
        link: function(scope, element, attrs) {
            scope.suggestions = [];
            scope.dropdownVisible = false;

            scope.onInputChange = function() {
                if (!scope.ngModel) {
                    scope.suggestions = [];
                    scope.dropdownVisible = false;
                    return;
                }

                var result = scope.fetchSuggestions({ query: scope.ngModel });
                if (result && angular.isFunction(result.then)) {
                    result.then(function(results) {
                        scope.suggestions = results || [];
                        scope.dropdownVisible = true;
                    });
                } else {
                    console.error("fetchSuggestions must return a Promise.");
                }
            };

            scope.selectSuggestion = function(suggestion) {
                scope.ngModel = suggestion;
                scope.suggestions = [];
                scope.dropdownVisible = false;
            };

            scope.hideDropdown = function() {
                setTimeout(function () {
                    scope.dropdownVisible = false;
                    scope.$apply();
                }, 200);
            };

            scope.highlightMatch = function(text, query) {
                if (!text) return $sce.trustAsHtml('');
                if (!query) return $sce.trustAsHtml(text);

                var safeQuery = query.toString().replace(/[.*+?^${}()|[\]\\]/g, '\\$&');
                var regex = new RegExp(safeQuery, 'gi');
                var highlighted = text.toString().replace(regex, '<strong>$&</strong>');

                return $sce.trustAsHtml(highlighted);
            };
        }
    };
});

app.directive('lazyDropdown', function($timeout, $compile) {
    return {
        restrict: 'E',
        scope: {
            items: '=?',
            selectedItem: '=',
            placeholder: '@',
            displayProperty: '@',
            searchProperty: '@',
            itemTemplate: '@',
            enableSearch: '=?',
            loadItems: '&?',
            onSelect: '&?'
        },
        template: `
            <div class="lazy-dropdown">
                <button type="button"
                        class="btn btn-default btn-dropdown form-control"
                        ng-click="toggleDropdown()"
                        ng-class="{'btn-primary': isOpen}">
                    <span ng-if="!selectedItem" class="placeholder">{{placeholder || 'Select...'}}</span>
                    <span ng-if="selectedItem">{{getDisplayText(selectedItem)}}</span>
                    <span class="caret"></span>
                </button>

                <div class="dropdown-menu" ng-show="isOpen">
                    <div ng-if="enableSearch !== false" class="search-input">
                        <div class="input-group">
                            <span class="input-group-addon">
                                <i class="glyphicon glyphicon-search"></i>
                            </span>
                            <input type="text"
                                   class="form-control input-sm"
                                   placeholder="Search..."
                                   ng-model="$parent.searchText"
                                   ng-change="onSearch()"
                                   ng-click="$event.stopPropagation()" />
                        </div>
                    </div>

                    <div ng-if="!itemTemplate">
                        <div class="dropdown-item"
                             ng-repeat="item in displayedItems track by $index"
                             ng-click="selectItem(item)">
                            {{getDisplayText(item)}}
                        </div>
                    </div>

                    <div ng-if="itemTemplate" id="custom-items-container">
                        <!-- Custom template items will be compiled here -->
                    </div>

                    <div class="loading-item" ng-show="isLoading">
                        <i class="glyphicon glyphicon-refresh glyphicon-refresh-animate"></i>
                        Loading more items...
                    </div>

                    <div class="no-results" ng-show="displayedItems.length === 0 && !isLoading">
                        <i class="glyphicon glyphicon-info-sign"></i>
                        No items found
                    </div>
                </div>
            </div>
        `,
        link: function(scope, element, attrs) {
            // Initialize
            scope.isOpen = false;
            scope.displayedItems = [];
            scope.filteredItems = [];
            scope.searchText = '';
            scope.isLoading = false;
            scope.enableSearch = scope.enableSearch !== false;

            // Pagination settings
            const itemsPerPage = 10;
            let currentPage = 0;
            let hasMoreItems = true;

            // Get display text for item
            scope.getDisplayText = function(item) {
                if (!item) return '';
                return scope.displayProperty ? item[scope.displayProperty] : item;
            };

            // Reset dropdown state
            scope.resetDropdown = function() {
                scope.displayedItems = [];
                currentPage = 0;
                hasMoreItems = true;

                if (scope.loadItems) {
                    scope.loadMoreItems();
                } else {
                    scope.filterItems();
                    scope.loadMoreItems();
                }
            };

            // Filter items locally
            scope.filterItems = function() {
                if (!scope.items) return;

                if (scope.searchText) {
                    const searchProp = scope.searchProperty || scope.displayProperty;
                    scope.filteredItems = scope.items.filter(item => {
                        const text = searchProp ? item[searchProp] : item;
                        return text && text.toString().toLowerCase().includes(scope.searchText.toLowerCase());
                    });
                } else {
                    scope.filteredItems = scope.items.slice();
                }
            };

            // Load more items
            scope.loadMoreItems = function() {
                if (!hasMoreItems || scope.isLoading) return;

                scope.isLoading = true;

                if (scope.loadItems) {
                    // API-based loading
                    scope.loadItems({
                        params: {
                           page: currentPage,
                           search: scope.searchText,
                           limit: itemsPerPage
                       }
                   }).then(function(result) {
                        if (result && result.items && result.items.length > 0) {
                            if (currentPage === 0) {
                                scope.displayedItems = result.items;
                            } else {
                                scope.displayedItems = scope.displayedItems.concat(result.items);
                            }
                            currentPage++;
                            hasMoreItems = result.hasMore;
                        } else {
                            hasMoreItems = false;
                        }
                        scope.isLoading = false;
                    });
                } else {
                    // Local array loading
                    $timeout(function() {
                        const startIndex = currentPage * itemsPerPage;
                        const endIndex = startIndex + itemsPerPage;
                        const newItems = scope.filteredItems.slice(startIndex, endIndex);

                        if (newItems.length > 0) {
                            scope.displayedItems = scope.displayedItems.concat(newItems);
                            currentPage++;
                            hasMoreItems = endIndex < scope.filteredItems.length;
                        } else {
                            hasMoreItems = false;
                        }

                        scope.isLoading = false;
                    }, 200);
                }
            };

            // Toggle dropdown
            scope.toggleDropdown = function() {
                scope.isOpen = !scope.isOpen;
                if (scope.isOpen) {
                    scope.resetDropdown();
                    $timeout(function() {
                        scope.setupScrollListener();
                    }, 0);
                }
            };

            // Select item
            scope.selectItem = function(item) {
                scope.selectedItem = item;
                scope.isOpen = false;
                if (scope.onSelect) {
                    scope.onSelect({item: item});
                }
            };

            // Search functionality
            scope.onSearch = function() {
                console.log("Search Text:", scope.searchText);
                scope.resetDropdown();
            };

            // Setup scroll listener
            scope.setupScrollListener = function() {
                const dropdownMenu = element.find('.dropdown-menu')[0];
                if (!dropdownMenu) return;

                dropdownMenu.addEventListener('scroll', function() {
                    const scrollTop = dropdownMenu.scrollTop;
                    const scrollHeight = dropdownMenu.scrollHeight;
                    const clientHeight = dropdownMenu.clientHeight;

                    if (scrollTop + clientHeight >= scrollHeight - 5) {
                        scope.$apply(function() {
                            scope.loadMoreItems();
                        });
                    }
                });
            };

            // Close dropdown when clicking outside
            function handleOutsideClick(event) {
                if (!element[0].contains(event.target)) {
                    scope.$apply(function() {
                        scope.isOpen = false;
                    });
                }
            }

            document.addEventListener('click', handleOutsideClick);

            // Cleanup
            scope.$on('$destroy', function() {
                document.removeEventListener('click', handleOutsideClick);
            });
        }
    };
})

app.directive('actionButton', function () {
    return {
        restrict: 'E',
        scope: {
            label: '@',           // Button label: 'Add', 'Edit', etc.
            icon: '@',            // Font Awesome icon class
            type: '@',            // btn-primary, btn-yellow, etc.
            visible: '=',         // Boolean flag to show/hide button
            onClick: '&'          // Function to execute
        },
        template: `
            <button type="button"
                    class="btn-sm btn-round transition-fade"
                    ng-class="'btn ' + type"
                    ng-show="visible"
                    ng-click="onClick()"
                    style="padding: 5px 5px; position: absolute; top: 0; left: 0;">
                <i class="ace-icon fa" ng-class="icon"></i> {{label}}
            </button>
        `
    };
});




