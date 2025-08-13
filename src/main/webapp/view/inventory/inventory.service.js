app.factory('InventoryService', function ($http) {
    return {
        getTransactionId: function (action, query) {
            return $http.get('/inventory/inventory-movement/search-transaction-id', {
                params: { action: action, query: query }
            }).then(function (resp) {
               return resp.data.body;
            });
        }
    };
});