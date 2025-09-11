app.service('TransactionService', function() {
    let selectedTransaction = null;
    return {
        set: obj => selectedTransaction = obj,
        get: () => selectedTransaction
    };
});
