
app.factory('CommunicationService', function($http) {
    return {
        getRequest: function(type, url, reqData) {
            
            return returnObj = {
                method: type.toUpperCase(),
                url: url,
                headers: {'Content-Type': 'application/json', 'x-aip-token':_shskr_},
                dataType: 'json',
                data: JSON.stringify(reqData)
            };
        }

    };
});

app.factory('Communication', function ($http, $q, $timeout, CommunicationService) {
    return {
        request: function (type, url, reqData) {
            
            var deferred = $q.defer();
            
            $timeout(function () {
                var req = CommunicationService.getRequest(type, url, reqData);
                log("URL", url);

                $http(req).then(function (msg) {
                    if( JSON.stringify(msg).includes("Please Enter Your Credential") && JSON.stringify(msg).includes("DOCTYPE html") ){ // this text is exist in login page
                        window.location.href = COMMON_API.login_url;
                    }
                    deferred.resolve(msg.data);

                }, function (err) {
                    if( JSON.stringify(msg).includes("Please Enter Your Credential") && JSON.stringify(msg).includes("DOCTYPE html") ){ // this text is exist in login page
                        window.location.href = COMMON_API.login_url;
                    }
                    deferred.reject(err);
                });
            });
            
            return deferred.promise;
        },
        
        globalReqData:function(){
            var topping = 0;
            var from_date = "1970-01-01";
            var to_date = "1970-01-31";
            
            if (arguments.length === 0 || arguments.length === 1) { 
                topping = arguments[0];
                var _DATE_ = new Date();
                _DATE_ = addDays(_DATE_, 1);
                
                var _year = _DATE_.getFullYear();
                var _month = _DATE_.getMonth() + 1;
                var _dayOfMonth = _DATE_.getDate();
                
                _month = (_month<10) ? "0" +_month : _month;
                
                to_date = _year + "-" + _month + "-" + _dayOfMonth;
                
            } else if( arguments.length > 1 ){ // requires two parameter first one is topping, and second one is selected date
                topping = arguments[0];
                var selected_date = arguments[1];
                
                
                var dateArray = selected_date.split(" ");
                var _d = new Date(dateArray[1], parseInt(getMonthIndex( dateArray[0] ), 10), 0);
                
                
                from_date = dateArray[1] + "-" + getMonthIndex( dateArray[0] ) + "-01";
                to_date = dateArray[1] + "-" + getMonthIndex( dateArray[0] )  + "-" + _d.getDate();
                
            }
            
            
            var req = {"FROM_DATE":from_date, "TO_DATE":to_date, "TOPPING": topping, "month_selected":"2017-11-01"};
            return req;
        }
        
        
    };
});

app.service('ImageService', ['$http', function($http) {

    // Upload image file
    this.upload = function(moduleName, file, uploadUrl) {
        var formData = new FormData();
        formData.append("file", file);
        formData.append("moduleName", moduleName);

        return $http.post(uploadUrl, formData, {
            transformRequest: angular.identity,
            headers: { 'Content-Type': undefined }
        });
    };

    // Build full image URL
    this.getImageUrl = function(moduleName, fileName, baseUrl) {
        return baseUrl + moduleName + '/' + fileName;
    };

}]);

app.factory('autoCompleteDataService', [function () {
    return {
        getSource: function () {
            //this is where you'd set up your source... could be an external source, I suppose. 'something.php'
            //return ['apples', 'oranges', 'bananas'];
            /*return [
                {name:"Shahadat", mobile:"01824880161", age:"28", dob:"", gender:"Male"},
                {name:"Imtiaz", mobile:"01824880162", age:"28", dob:"", gender:"Male"},
                {name:"Jubayer", mobile:"01824880163", age:"28", dob:"", gender:"Male"},
                {name:"Sharif", mobile:"01824880164", age:"28", dob:"", gender:"Male"},
                {name:"Awlad Hossain", mobile:"01824880165", age:"28", dob:"", gender:"Male"},
                {name:"Tonmoy", mobile:"01824880165", age:"28", dob:"", gender:"Male"}
            ];*/
            return [
                {
                  value: "jquery",
                  label: "jQuery",
                  desc: "the write less, do more, JavaScript library",
                  icon: "jquery_32x32.png"
                },
                {
                  value: "jquery-ui",
                  label: "jQuery UI",
                  desc: "the official user interface library for jQuery",
                  icon: "jqueryui_32x32.png"
                },
                {
                  value: "sizzlejs",
                  label: "Sizzle JS",
                  desc: "a pure-JavaScript CSS selector engine",
                  icon: "sizzlejs_32x32.png"
                }
              ];
        }
    };
}]);

app.factory("AutoCompleteService", ["$http", function ($http) {
    return {
        search: function (term) {
            /*return $http.get("http://YourServiceUrl.com/" + term).then(function (response) {
                return response.data;
            });*/
            
            return [
                {"name":"Shahadat", "mobile":"01824880161", "age":"28", "dob":"", "gender":"Male"},
                {"name":"Imtiaz", "mobile":"01824880162", "age":"28", "dob":"", "gender":"Male"},
                {"name":"Jubayer", "mobile":"01824880163", "age":"28", "dob":"", "gender":"Male"},
                {"name":"Sharif", "mobile":"01824880164", "age":"28", "dob":"", "gender":"Male"},
                {"name":"Awlad Hossain", "mobile":"01824880165", "age":"28", "dob":"", "gender":"Male"},
                {"name":"Tonmoy", "mobile":"01824880165", "age":"28", "dob":"", "gender":"Male"}
            ];
            
        }
    };
}]);

app.factory('ClientService', function ($q, $filter){
    return {
        setLocalStorage: function (_key, _stringValue) {
            window.localStorage[_key] = _stringValue;
        },
        getLocalStorage: function (_key) {
            if (window.localStorage[_key]) {
                var _resp = window.localStorage[_key];
            } else {
                // var _resp = {};
                var _resp = "";
            }
            return _resp;

        },
        getLocalStorageJSON: function (_key) {
            if (window.localStorage[_key]) {
                var _resp = window.localStorage[_key];
            } else {
                var _resp = "{}";
            }
            return _resp;

        },
        clearLocalStorage: function () {
            window.localStorage.clear();
        },
        clearLocalStorageByKey: function (param) {
            window.localStorage.removeItem(param);
        },
        isStrongPassword: function (_str){
            var upr = "ABCDEFGHIJKLMNOPQRSTUVWXYZ", isUpr = false;
            var lwr = "abcdefghijklmnopqrstuvwxyz", isLwr = false;
            var nmr = "0123456789", isNmr = false;
            var spl = "!@#$%^&()_<>[]{}.+", isSpl = false;
            
            if( _str.length < 8 ) return "Password length at least 8 characters";
            for( var i=0 ; i<_str.length ; i++ ){
                if( upr.includes(_str[i]) ) isUpr = true;
                if( lwr.includes(_str[i]) ) isLwr = true;
                if( nmr.includes(_str[i]) ) isNmr = true;
                if( spl.includes(_str[i]) ) isSpl = true;
            }
            
            if( !isUpr ) return "Password should contain at least one (A-Z) characters";
            if( !isLwr ) return "Password should contain at least one (a-z) characters";
            if( !isNmr ) return "Password should contain at least one (0-9) characters";
            if( !isSpl ) return "Password should contain at least one Special characters";
            
            return "OK";
        },
        setUser: function (user) {
            window.localStorage['uSrLoggDIfo'] = JSON.stringify(user);
        },
        
        getUser: function () {
            var _resp = window.localStorage['uSrLoggDIfo'];
            return JSON.parse(_resp);
        },
        
        isLoggedIn: function () {
            var _resp = window.localStorage['uSrLoggDIfo'];
            var usr = JSON.parse(_resp);
            
            if (usr.user_id !== "") {
                return true;
            } else {
                return false;
            }
        },

        isRegistered: function () {
            if (window.localStorage['UserLGOIN']) {
                return true;
            } else {
                return false;
            }
        },

        hasPermission: function (module, controller, component) {
            var features = JSON.parse( window.localStorage[KEY.LOCAL.features] );
            var feature=features[module+'##'+component];
            if(feature===undefined){
                return false;
            }
            else return true;
        },
        featureName: function (module,component) {
            
            try{
                var features = JSON.parse( window.localStorage[KEY.LOCAL.features] );
                return features[module+'##'+component].feature_name;
            } catch (e){
                return "";
            }
            
        },
        log: function (tag,messaage) {
             log(tag,messaage);
        }
    };
});

app.factory('DialogBox', function ($q, $mdDialog, $timeout, ngDialog, ngToast){
    return {
        
        toast: function (_title, _message) {
            ngDialog.open({
                template: '\
                <h4>' + _title + '</h4>\
                <p>' + _message + '</p>\
                <div class="ngdialog-buttons">\
                    <button type="button" class="ngdialog-button ngdialog-button-primary" ng-click="closeThisDialog(0)">Ok</button>\
                </div>',
                plain: true,
                className: 'ngdialog-theme-default'
            });
        },
        alert: function (_title, _message) {
            var _head = '';
            if( _title.toUpperCase() === 'SUCCESS' ){
                _head = '<h4 class="text-success">' + _title + '</h4>';
                
            } else if( _title.toUpperCase() === 'WARNING' ){
                _head = '<h4 class="text-warning">' + _title + '</h4>';
                
            } else if( _title.toUpperCase() === 'ERROR' ){
                _head = '<h4 class="text-danger">' + _title + '</h4>';
                
            } else{
                _head = '<h4>' + _title + '</h4>';
            }
                
            ngDialog.open({
                template: _head + '\
                <p style="overflow:auto;">' + _message + '</p>\
                <div class="ngdialog-buttons">\
                    <button type="button" class="ngdialog-button ngdialog-button-primary" ng-click="closeThisDialog(0)">Ok</button>\
                </div>',
                plain: true,
                className: 'ngdialog-theme-default'
            });
        },
        confirm: function (_message) {
            var nestedConfirmDialog = ngDialog.openConfirm({
                template:'\
                    <h4>Performing a serious action</h4>\
                    <p>' + _message + '</p>\
                    <div class="ngdialog-buttons">\
                        <button type="button" class="ngdialog-button ngdialog-button-primary" ng-click="confirm(1)">Yes</button>\
                        <button type="button" class="ngdialog-button ngdialog-button-secondary" ng-click="closeThisDialog(0)">No</button>\
                    </div>',
                plain: true
            });
 
            // NOTE: return the promise from openConfirm
            return nestedConfirmDialog;
        },
        show: function(content, title){
            var _title = ( typeof title === 'undefined' ) ? 'eHospital Dialog':title;
            ngDialog.open({
                template: '<h4>' + _title + '</h4>' + content,
                plain: true,
                width: 550,
                className: 'ngdialog-theme-default'
            });
        },
        showProgress:function(){
            
            if( !isLoadingShown ){
                
                log("dialog", "showing");
                isLoadingShown = true;
                
                $mdDialog.show({
                    template:'<md-dialog style="background-color:transparent;box-shadow:none;overflow: hidden !important;">' +
                                '<div layout="row" layout-sm="column" layout-align="center center" aria-label="wait">' +
                                    '<md-progress-circular class="md-hue-2" md-diameter="50px"></md-progress-circular>' +
                                '</div>' +
                            '</md-dialog>',
                    parent: angular.element(document.body),
                    clickOutsideToClose:false,
                    escapeToClose: false,
                    fullscreen: false
                });
                
                log("dialog", "shown");
            }
        },
        hideProgress:function(){
            $mdDialog.cancel();
            log("dialog", "closed");
            isLoadingShown = false;
        }
    };
});


app.factory("encrypt", function () {
    return {
        SHA256: function (data) {

            var rotateRight = function (n, x) {
                return ((x >>> n) | (x << (32 - n)));
            }
            var choice = function (x, y, z) {
                return ((x & y) ^ (~x & z));
            }
            function majority(x, y, z) {
                return ((x & y) ^ (x & z) ^ (y & z));
            }
            function sha256_Sigma0(x) {
                return (rotateRight(2, x) ^ rotateRight(13, x) ^ rotateRight(22, x));
            }
            function sha256_Sigma1(x) {
                return (rotateRight(6, x) ^ rotateRight(11, x) ^ rotateRight(25, x));
            }
            function sha256_sigma0(x) {
                return (rotateRight(7, x) ^ rotateRight(18, x) ^ (x >>> 3));
            }
            function sha256_sigma1(x) {
                return (rotateRight(17, x) ^ rotateRight(19, x) ^ (x >>> 10));
            }
            function sha256_expand(W, j) {
                return (W[j & 0x0f] += sha256_sigma1(W[(j + 14) & 0x0f]) + W[(j + 9) & 0x0f] +
                        sha256_sigma0(W[(j + 1) & 0x0f]));
            }

            /* Hash constant words K: */
            var K256 = new Array(
                    0x428a2f98, 0x71374491, 0xb5c0fbcf, 0xe9b5dba5,
                    0x3956c25b, 0x59f111f1, 0x923f82a4, 0xab1c5ed5,
                    0xd807aa98, 0x12835b01, 0x243185be, 0x550c7dc3,
                    0x72be5d74, 0x80deb1fe, 0x9bdc06a7, 0xc19bf174,
                    0xe49b69c1, 0xefbe4786, 0x0fc19dc6, 0x240ca1cc,
                    0x2de92c6f, 0x4a7484aa, 0x5cb0a9dc, 0x76f988da,
                    0x983e5152, 0xa831c66d, 0xb00327c8, 0xbf597fc7,
                    0xc6e00bf3, 0xd5a79147, 0x06ca6351, 0x14292967,
                    0x27b70a85, 0x2e1b2138, 0x4d2c6dfc, 0x53380d13,
                    0x650a7354, 0x766a0abb, 0x81c2c92e, 0x92722c85,
                    0xa2bfe8a1, 0xa81a664b, 0xc24b8b70, 0xc76c51a3,
                    0xd192e819, 0xd6990624, 0xf40e3585, 0x106aa070,
                    0x19a4c116, 0x1e376c08, 0x2748774c, 0x34b0bcb5,
                    0x391c0cb3, 0x4ed8aa4a, 0x5b9cca4f, 0x682e6ff3,
                    0x748f82ee, 0x78a5636f, 0x84c87814, 0x8cc70208,
                    0x90befffa, 0xa4506ceb, 0xbef9a3f7, 0xc67178f2
                    );

            /* global arrays */
            var ihash, count, buffer;
            var sha256_hex_digits = "0123456789abcdef";

            /* Add 32-bit integers with 16-bit operations (bug in some JS-interpreters: 
             overflow) */
            function safe_add(x, y)
            {
                var lsw = (x & 0xffff) + (y & 0xffff);
                var msw = (x >> 16) + (y >> 16) + (lsw >> 16);
                return (msw << 16) | (lsw & 0xffff);
            }

            /* Initialise the SHA256 computation */
            function sha256_init() {
                ihash = new Array(8);
                count = new Array(2);
                buffer = new Array(64);
                count[0] = count[1] = 0;
                ihash[0] = 0x6a09e667;
                ihash[1] = 0xbb67ae85;
                ihash[2] = 0x3c6ef372;
                ihash[3] = 0xa54ff53a;
                ihash[4] = 0x510e527f;
                ihash[5] = 0x9b05688c;
                ihash[6] = 0x1f83d9ab;
                ihash[7] = 0x5be0cd19;
            }

            /* Transform a 512-bit message block */
            function sha256_transform() {
                var a, b, c, d, e, f, g, h, T1, T2;
                var W = new Array(16);

                /* Initialize registers with the previous intermediate value */
                a = ihash[0];
                b = ihash[1];
                c = ihash[2];
                d = ihash[3];
                e = ihash[4];
                f = ihash[5];
                g = ihash[6];
                h = ihash[7];

                /* make 32-bit words */
                for (var i = 0; i < 16; i++)
                    W[i] = ((buffer[(i << 2) + 3]) | (buffer[(i << 2) + 2] << 8) | (buffer[(i << 2) + 1]
                            << 16) | (buffer[i << 2] << 24));

                for (var j = 0; j < 64; j++) {
                    T1 = h + sha256_Sigma1(e) + choice(e, f, g) + K256[j];
                    if (j < 16)
                        T1 += W[j];
                    else
                        T1 += sha256_expand(W, j);
                    T2 = sha256_Sigma0(a) + majority(a, b, c);
                    h = g;
                    g = f;
                    f = e;
                    e = safe_add(d, T1);
                    d = c;
                    c = b;
                    b = a;
                    a = safe_add(T1, T2);
                }

                /* Compute the current intermediate hash value */
                ihash[0] += a;
                ihash[1] += b;
                ihash[2] += c;
                ihash[3] += d;
                ihash[4] += e;
                ihash[5] += f;
                ihash[6] += g;
                ihash[7] += h;
            }

            /* Read the next chunk of data and update the SHA256 computation */
            function sha256_update(data, inputLen) {
                var i, index, curpos = 0;
                /* Compute number of bytes mod 64 */
                index = ((count[0] >> 3) & 0x3f);
                var remainder = (inputLen & 0x3f);

                /* Update number of bits */
                if ((count[0] += (inputLen << 3)) < (inputLen << 3))
                    count[1]++;
                count[1] += (inputLen >> 29);

                /* Transform as many times as possible */
                for (i = 0; i + 63 < inputLen; i += 64) {
                    for (var j = index; j < 64; j++)
                        buffer[j] = data.charCodeAt(curpos++);
                    sha256_transform();
                    index = 0;
                }

                /* Buffer remaining input */
                for (var j = 0; j < remainder; j++)
                    buffer[j] = data.charCodeAt(curpos++);
            }

            /* Finish the computation by operations such as padding */
            function sha256_final() {
                var index = ((count[0] >> 3) & 0x3f);
                buffer[index++] = 0x80;
                if (index <= 56) {
                    for (var i = index; i < 56; i++)
                        buffer[i] = 0;
                } else {
                    for (var i = index; i < 64; i++)
                        buffer[i] = 0;
                    sha256_transform();
                    for (var i = 0; i < 56; i++)
                        buffer[i] = 0;
                }
                buffer[56] = (count[1] >>> 24) & 0xff;
                buffer[57] = (count[1] >>> 16) & 0xff;
                buffer[58] = (count[1] >>> 8) & 0xff;
                buffer[59] = count[1] & 0xff;
                buffer[60] = (count[0] >>> 24) & 0xff;
                buffer[61] = (count[0] >>> 16) & 0xff;
                buffer[62] = (count[0] >>> 8) & 0xff;
                buffer[63] = count[0] & 0xff;
                sha256_transform();
            }

            /* Split the internal hash values into an array of bytes */
            function sha256_encode_bytes() {
                var j = 0;
                var output = new Array(32);
                for (var i = 0; i < 8; i++) {
                    output[j++] = ((ihash[i] >>> 24) & 0xff);
                    output[j++] = ((ihash[i] >>> 16) & 0xff);
                    output[j++] = ((ihash[i] >>> 8) & 0xff);
                    output[j++] = (ihash[i] & 0xff);
                }
                return output;
            }

            /* Get the internal hash as a hex string */
            function sha256_encode_hex() {
                var output = new String();
                for (var i = 0; i < 8; i++) {
                    for (var j = 28; j >= 0; j -= 4)
                        output += sha256_hex_digits.charAt((ihash[i] >>> j) & 0x0f);
                }
                return output;
            }



            /* test if the JS-interpreter is working properly */
            function sha256_self_test()
            {
                return sha256_digest("message digest") ==
                        "f7846f55cf23e14eebeab5b4e1550cad5b509e3348fbc4efa3a1413d393cb650";
            }

            sha256_init();
            sha256_update(data, data.length);
            sha256_final();
            return sha256_encode_hex();

        }

    };

});

//window.open(url, "print", "directories=no,titlebar=no,toolbar=no,location=no,status=no,menubar=no,scrollbars=yes,resizable=yes,width=500,height=450,top=100,left=500");

function printPage(fullHtml) {
    var newWin = window.open('', 'Print-Window');
    newWin.document.open();
    newWin.document.write(fullHtml);
    newWin.document.close();
    setTimeout(function() {
      newWin.close();
    }, 300);
}

function printElement(content) {
    var newWin = window.open('', 'Print-Window');
    newWin.document.open();
    newWin.document.write('<html><body onload="window.print()">' + content + '</body></html>');
    newWin.document.close();
    setTimeout(function() {
      newWin.close();
    }, 300);
}

function log(tag,messaage) {
    console.log(tag,messaage);
}

//Cache itemList[]
app.factory('ItemService', function ($http, $q, CacheUtils) {
    var cache = null;
    var menuCache = null;

    return {
        getItemList: function () {
            if (cache) {
                return $q.resolve(cache); // return cached data
            }

            return $http.get(COMMON_API.items).then(function (resp) {
                if (resp.data.code === 200) {
                    cache = resp.data.body || [];
                    console.log("ITEM :: Fetched from API, cache size:", CacheUtils.getObjectSizeFormatted(cache));
                    return cache;
                } else {
                    return $q.reject("Failed to load items");
                }
            });
        },

        // fetch menu items
        getMenuItems: function () {
            if (menuCache) {
                return $q.resolve(menuCache); // return cached
            }

            return $http.get(COMMON_API.menu_items).then(function (resp) {
                if (resp.data.code === 200) {
                    menuCache = resp.data.body || [];
                    console.log("MENU ITEM:: fetched, cache size:", CacheUtils.getObjectSizeFormatted(menuCache));
                    return menuCache;
                } else {
                    return $q.reject("Failed to load menu items");
                }
            });
        },

        clearCache: function () {
            cache = null;
            menuCache = null;
            console.log("Item cache cleared");
        }
    };
});

//Toaster message queue
app.factory('ToasterMessageQueueService', function () {
    var messages = [];

    return {
        addMessage: function (type, message, title) {
            messages.push({ type: type, message: message, title: title });
        },
        getMessages: function () {
            var temp = angular.copy(messages);
            messages = []; // Clear after read
            return temp;
        }
    };
});

app.factory('ToastService', function(growl, ToasterMessageQueueService) {
    return {
        showMessages: function () {
            const messages = ToasterMessageQueueService.getMessages();
            messages.forEach(msg => {
                if (msg.type === 'success') {
                    growl.success(msg.message, { title: msg.title });
                } else if (msg.type === 'error') {
                    growl.error(msg.message, { title: msg.title });
                } else if (msg.type === 'warning') {
                    growl.warning(msg.message, { title: msg.title });
                } else if (msg.type === 'info') {
                    growl.info(msg.message, { title: msg.title });
                }
            });
        }
    };
});

app.factory('DateHelperService', function(growl) {
    function pad(number) {
        return String(number).padStart(2, '0');
    }

    function formatToLocalDateTimeString(date) {
        if (!(date instanceof Date)) return "";
        const yyyy = date.getFullYear();
        const mm = pad(date.getMonth() + 1);
        const dd = pad(date.getDate());
        return `${yyyy}-${mm}-${dd}T00:00:00`;
    }

    function formatMultipleFields (obj, fields) {
        fields.forEach(function (field) {
            obj[field] = this.formatToLocalDateTimeString(obj[field]);
        }, this);
        return obj;
    }

    function formatDateLocal(date) {
        if (!date) return '';
        const d = new Date(date);
        const day = pad(d.getDate());
        const month = pad(d.getMonth() + 1);
        const year = d.getFullYear();
        return `${year}-${month}-${day}`;
    }

    function validateAndFormat(fromDate, toDate) {
        const formattedFrom = formatToLocalDateTimeString(fromDate);
        const formattedTo = formatToLocalDateTimeString(toDate);

        if (formattedFrom && formattedTo && formattedFrom > formattedTo) {
            growl.error('To date should be greater than or equal to From date!', { title: 'Error!' });
            return null;
        }

        return {
            fromDate: formattedFrom,
            toDate: formattedTo
        };
    }

    return {
        formatToLocalDateTimeString,
        formatMultipleFields,
        formatDateLocal,
        validateAndFormat
    };
});

app.factory('ReportPreviewService', function () {
    function previewPdf(apiUrl, token) {
        return new Promise(function (resolve, reject) {
            var xhttp = new XMLHttpRequest();
            xhttp.open("GET", apiUrl, true);
            xhttp.setRequestHeader('x-aip-token', token);
            xhttp.responseType = 'blob';

            xhttp.onload = function () {
                if (this.status === 200) {
                    var pdfResponse = new Blob([this.response], { type: 'application/pdf' });
                    var fileURL = URL.createObjectURL(pdfResponse);
                    var link = document.createElement('a');
                    link.href = fileURL;
                    link.target = '_blank';
                    link.click();
                    resolve();
                } else {
                    reject("Failed to preview report. Status: " + this.status);
                }
            };

            xhttp.onerror = function () {
                reject("Network error occurred while previewing report.");
            };

            xhttp.send();
        });
    }

    return {
        previewPdf
    };
});

app.factory('CustomerService', function($http) {
    return {
        search: function(query) {
            return $http.get(COMMON_API.search_customer, { params: { query: query } });
        },
        saveCustomer: function(customerData) {
            return $http.post(COMMON_API.save_customer, customerData);
        }
    };
});

app.factory('NotificationService', function($timeout) {
    var messages = [];

    return {
        messages: messages,

        // Show a new message
        showMessage: function(type, text, duration = 4000) {
            const msg = { type, text, duration };
            messages.push(msg);

            // auto-close after duration
            const index = messages.length - 1;
            $timeout(function() {
                if (messages[index]) {
                    this.closeMessage(index);
                }
            }.bind(this), duration);
        },

        // Close a message
        closeMessage: function(i) {
            const els = document.querySelectorAll('.notification-card');
            const el = els[i];
            if (el) {
                el.classList.add('fade-out');
                $timeout(function() {
                    messages.splice(i, 1);
                }, 350);
            } else {
                messages.splice(i, 1);
            }
        }
    };
});

app.factory('CacheUtils', function() {
    return {
        getSizeInBytes: function(obj) {
            return new Blob([JSON.stringify(obj)]).size;
        },
        formatSize: function(bytes) {
            if (bytes < 1024) return bytes + " B";
            else if (bytes < 1024 * 1024) return (bytes / 1024).toFixed(2) + " KB";
            else return (bytes / (1024 * 1024)).toFixed(2) + " MB";
        },
        getObjectSizeFormatted: function(obj) {
            return this.formatSize(this.getSizeInBytes(obj));
        }
    };
});

app.factory('SupplierService', function($http, $q, CacheUtils) {
    var cache = null;

    return {
        getSupplierList: function () {
            if (cache) {
                return $q.resolve(cache); // return cached data
            }

            return $http.get(COMMON_API.suppliers).then(function (resp) {
                if (resp.data.code === 200) {
                    cache = resp.data.body || [];
                    console.log("SUPPLIER :: Fetched from API, cache size:", CacheUtils.getObjectSizeFormatted(cache));
                    return cache;
                } else {
                    return $q.reject("Failed to load supplier");
                }
            });
        },

        clearCache: function () {
            cache = null;
            console.log("Supplier cache cleared");
        }
    };
});

app.factory('PosService', function($http, $q, CacheUtils) {
    var cache = null;

    return {
        getPosDefault: function () {
            if (cache) {
                return $q.resolve(cache);
            }

            return $http.get(COMMON_API.pos_default).then(function (resp) {
                if (resp.data.code === 200) {
                    cache = resp.data.body || [];
                    console.log("POS DEFAULT :: Fetched from API, cache size:", CacheUtils.getObjectSizeFormatted(cache));
                    return cache;
                } else {
                    return $q.reject("Failed to load pos default");
                }
            });
        },
        clearCache: function() {
            cache = null;
            console.log("POS default cache cleared");
        }
    };
});

/*
Reusable CacheService:

In-memory cache (super fast, per session).
LocalStorage (persistent across reloads).
TTL (time-to-live) → so stale data auto-expires.
Clear by key or clear all.
*/
app.factory('CacheService', function($q) {
    var memoryCache = {}; // in-memory cache
    var DEFAULT_TTL = 60 * 60 * 1000; // 60 minutes

    function now() {
        return new Date().getTime();
    }

    return {
        set: function(key, value, ttlMs) {
            var expiry = now() + (ttlMs || DEFAULT_TTL);

            // Store in memory
            memoryCache[key] = { value: value, expiry: expiry };

            // Store in localStorage
            var obj = { value: value, expiry: expiry };
            localStorage.setItem(key, JSON.stringify(obj));
        },

        get: function(key) {
            // 1. Check in-memory cache
            if (memoryCache[key] && memoryCache[key].expiry > now()) {
                return $q.resolve(memoryCache[key].value);
            }

            // 2. Check localStorage
            var str = localStorage.getItem(key);
            if (str) {
                try {
                    var obj = JSON.parse(str);
                    if (obj.expiry > now()) {
                        memoryCache[key] = obj; // refresh memory
                        return $q.resolve(obj.value);
                    } else {
                        // expired
                        this.remove(key);
                    }
                } catch (e) {
                    console.warn("Invalid JSON in localStorage for", key);
                    this.remove(key);
                }
            }

            // 3. Nothing found
            return $q.resolve(null);
        },

        remove: function(key) {
            delete memoryCache[key];
            localStorage.removeItem(key);
        },

        clearAll: function() {
            memoryCache = {};
            localStorage.clear();
        }
    };
});

/*
Fully reusable, lazy-load defaults service:

POS default, Inventory default, or any other defaults
Lazy-load on demand (no need to fetch at login)
Memory cache + localStorage (fast + persistent)
TTL / expiry
Force refresh if needed
One-line fetch per default in controllers
*/

app.factory('DefaultSetupService', function($http, $q, CacheService) {

    // Map keys to API endpoints
    var DEFAULT_APIS = {
        posDefault: COMMON_API.pos_default,
        inventoryDefault: COMMON_API.inventory_default
        // add more defaults here
    };

    return {

        /**
         * Fetch default setup by key
         * @param {string} key - 'posDefault', 'inventoryDefault', etc.
         * @param {boolean} forceRefresh - bypass cache/localStorage if true
         */
        get: function(key, forceRefresh) {
            if (!DEFAULT_APIS[key]) {
                return $q.reject("No API configured for key: " + key);
            }

            if (forceRefresh) {
                CacheService.remove(key);
            }

            return CacheService.get(key).then(function(cachedData) {
                if (cachedData) {
                    return cachedData;
                } else {
                    // Fetch from API
                    return $http.get(DEFAULT_APIS[key]).then(function(resp) {
                        if (resp.data.code === 200) {
                            var setup = resp.data.body || [];
                            CacheService.set(key, setup); // save to cache + localStorage
                            return setup;
                        } else {
                            return $q.reject("Failed to load " + key);
                        }
                    });
                }
            });
        },

        /**
         * Clear a specific default
         */
        clear: function(key) {
            CacheService.remove(key);
        },

        /**
         * Clear all defaults
         */
        clearAll: function() {
            CacheService.clearAll();
        }
    };
});

/* Generic Key–Value Store object Service */
app.factory('StoreService', function() {
    let store = {};

    return {
        /**
         * Save any object by key
         */
        set: function(key, value) {
            store[key] = value;
        },

        /**
         * Get object by key
         */
        get: function(key) {
            return store[key] || null;
        },
    };
});
