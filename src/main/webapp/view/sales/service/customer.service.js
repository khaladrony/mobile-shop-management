app.service("CustomerService", function($q, Communication) {
    var service = this;

    service.customer = {};
    service.search = { string: "" };
    service.searchResults = [];
    service.showAll = false;
    service.order = {};

    // Clear customer form
    service.clearCustomerForm = function(form) {
        service.customer = {};
        service.search.string = "";
        service.searchResults = [];
        service.showAll = false;

        if (form) {
            service.customer.mobileNumber = "";
            form.$setPristine();
            form.$setUntouched();
        }
    };

    // Save new customer
    service.saveCustomer = function(customer, form) {
        var deferred = $q.defer();

        Communication.request("POST", COMMON_API.save_customer, customer)
            .then(function(res) {
                if (res.code === 200) {
                    service.order.customer = res.body;
                    service.customer = {};
                    if (form) {
                        form.$setPristine();
                        form.$setUntouched();
                    }
                    deferred.resolve(res.body);
                } else {
                    deferred.reject(res.message || "Error saving customer");
                }
            }, function(err) {
                deferred.reject(err.message || "Server error while saving customer");
            });

        return deferred.promise;
    };

    // Search customer
    service.searchCustomer = function(query) {
        if (!query) return $q.resolve([]);
        return Communication.request("GET", COMMON_API.search_customer + "?query=" + query)
            .then(function(res) {
                service.searchResults = res.body || [];
                service.showAll = false;
                return service.searchResults;
            });
    };

    // Toggle show all results
    service.toggleShowAll = function() {
        service.showAll = !service.showAll;
        return service.showAll;
    };

    // Select customer
    service.selectCustomer = function(cust) {
        service.customer = angular.copy(cust);
        service.order.customer = service.customer;
        service.searchResults = [];
        return service.customer;
    };

    // Default customer
    service.defaultCustomer = function() {
        service.order.customer = {
            customerName: "Walk-In Customer",
            mobileNumber: ""
        };
        return service.order.customer;
    };
});
