app.controller('DashboardStaticViewCtrl', function ($scope, $http, $state, $timeout, $rootScope, $mdDialog, $interval, DialogBox, Communication) {
    $rootScope.setPageName(JMODULE_NAME,$state.current.name);
    $scope.areaList = [];
    $scope.atmList = [];
    $scope.sysStatistics = [];
    
    $scope.stat = {
        atmListMode:0,
        sysStatMode:0,
        fileStatMode:0
    };
    
    $interval(function(){
        log("interval--called");
        $scope.stat = {
            atmListMode:0,
            sysStatMode:0,
            fileStatMode:0
        };
        // $scope.reloadDashboardComponent();
    }, DASHBOARD_REFRESH);
    
    $scope.reloadDashboardComponent = function(){
        $timeout(function(){
            $scope.getSystemStatistics();
        }, 500);

        $timeout(function(){
            $scope.getAtmFilesStats();
        }, 700);
        
        $timeout(function(){
            $scope.showAtmStatListPie();
        }, 1000);
        
    };
    
    $scope.getAreaList = function () {
        var req = Communication.request("GET", API.ATM_AREA_LIST, {});
        req.then(function (resp) {
            log("area list: " + JSON.stringify(resp));
            if (resp.code === 200) {
                $scope.areaList = resp.body;
            }
        }, function (err) {
            log("area list error", JSON.stringify(err));
        });
    };
    
    $scope.getAtmList = function (area_id) {
        $scope.search.area_id = area_id;
        
        var req = Communication.request("GET", API.ATM_LIST + "/" + area_id, {});
        req.then(function (resp) {
            log("atm list: " + JSON.stringify(resp));
            if (resp.code === 200) {
                $scope.atmList = resp.body;
            }
        }, function (err) {
            log("atm list error", JSON.stringify(err));
        });
    };
    
    $scope.showAtmStatListPie = function(){
        var req = Communication.request("PUT", API.COMP_ATM_LIST_DETAIL, {});
        req.then(function (resp) {
            $scope.stat.atmListMode = 1;
            log("atm stat pie resp: " + JSON.stringify(resp));
            if (resp.code === 200) {
                
                /*------------------Load Chart Data--------------------*/
                var _chartData = "[['Atm','Current status']";
                var len = resp.body.length;
                for( var i = 0 ; i < len ; i++ ){
                    var obj = resp.body[i];

                    var _tmp = ",['" + obj.stat_name + "', " + obj.cnt + "]";
                    _chartData += _tmp;
                }
                if( len < 1 ) _chartData += ",['0', 0]";
                _chartData += "]";
                
                var options = {
                    title:'ATM machine status',
                    pieSliceText: 'value', //value-and-percentage
                    animation: {duration: 1000,easing: 'inAndOut',startup: true},
                    colors: ['#D15B47', '#82AF6F', '#F89406'],
                    chartArea: {
                        left:60,
                        right:50
                    }
                };
                
                if( resp.body.length > 0 ){
                    google.charts.setOnLoadCallback(
                    function(){
                        drawPieChart(_chartData, options, "atmStatPieChart");
                    });
                }
                /*---------------------Load chart Data-------------------------*/
            }
        }, function (err) {
            $scope.stat.atmListMode = 1;
            log("atm stat pie error", JSON.stringify(err));
        });
    };
    
    $scope.getSystemStatistics = function(){
        var req = Communication.request("PUT", API.COMP_SYSTEM_STATISTICS, {});
        req.then(function (resp) {
            $scope.stat.sysStatMode = 1;
            log("Statistics list: " + JSON.stringify(resp));
            if (resp.code === 200) {
                $scope.sysStatistics = resp.body;
            }
        }, function (err) {
            $scope.stat.sysStatMode = 1;
            log("Statistics list error", JSON.stringify(err));
        });
    };
    
    
    $scope.getAtmFilesStats = function(){
        
        var date = new Date();
        var toDateStr = date.toISOString().split('T')[0];
        date.setDate(date.getDate() - 30);
        var fromDateStr = date.toISOString().split('T')[0];
        
        var req = Communication.request("PUT", API.COMP_ATM_FILE_STATISTICS, {"from_date":fromDateStr, "to_date":toDateStr});
        req.then(function (resp) {
            $scope.stat.fileStatMode = 1;
            //log("atm file stats: " + JSON.stringify(resp));
            if (resp.code === 200) {
                
                
                /*------------------Load Chart Data--------------------*/
                var _chartData = "[['Genre','In-Queue', 'Downloaded', 'Partial', 'Failed']";
                var len = resp.body.length;
                for( var i = 0 ; i < len ; i++ ){
                    var obj = resp.body[i];

                    var _tmp = ",['" + obj.short_name + "', " + obj.InQueue + ", " + obj.Downloaded + ", " + obj.Partialy +  ", " + obj.Failured + "]";
                    _chartData += _tmp;
                }
                if( len < 1 ) _chartData += ",['0', 0, 0, 0, 0]";
                _chartData += "]";
                
                log("chartData: " + JSON.stringify(_chartData));
                
                var options = {
                    title:'Last 30 days image pulling status',
                    height: 400,
                    legend: { position: 'top', maxLines: 3 },
                    bar: { groupWidth: '75%' },
                    isStacked: true,
                    chartArea: {
                        left:60,
                        right:50
                    },
                    series: {
                        0:{color:'#5bc0de'},
                        1:{color:'#5cb85c'},
                        2:{color:'#f0ad4e'},
                        3:{color:'#d9534f'},
                    }
                };
                
                if( resp.body.length > 0 ){
                    google.charts.setOnLoadCallback(
                    function(){
                        drawStackedChart(_chartData, options, "atmFileStatChart", "COLUMN");
                    });
                }
                /*---------------------Load chart Data-------------------------*/
                
            }
        }, function (err) {
            $scope.stat.fileStatMode = 1;
            log("atm file stats error", JSON.stringify(err));
        });
        
    };
    
});