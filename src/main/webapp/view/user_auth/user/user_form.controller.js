app.controller('UserFormCtrl', function ($scope, $http, $state, $timeout, $stateParams, $rootScope, $sce, $mdDialog, $interval, ClientService, DialogBox, encrypt, Communication) {

    $rootScope.setPageName(JMODULE_NAME,$state.current.name);
    $scope.current_state = $state.current.name;

    $scope.form_reset_radio = 1;
    $scope.reset_npwrd="";
    $scope.reset_cpwrd="";
    $scope.isReadonly = false;

    $scope.roleList = [];
    $scope.selected_role_id = "";
    $scope.email_cnt = -1;
    $scope.valid_email = -1;
    $scope.valid_lan_id = -1;
    $scope.lan_id_cnt = -1;
    $scope.module = {
        user_id: "",
        user_code: "",
        first_name: "",
        last_name: "",
        usremail: "",
        lan_id:"",
        phone: "",
        address: "",
        country_id: 1,
        role_id:"",
        role_name:""
    };


    $scope.getRoleList = function () {
        var req = Communication.request("GET", API.ROLE_LIST, {});
        req.then(function (resp) {
            log("role list: " + JSON.stringify(resp));
            if (resp.code === 200) {
                $scope.roleList = resp.body.filter((it) => it.active);
            }
        }, function (err) {
            log("role list error", JSON.stringify(err));
        });
    };

    if($state.current.name === JCOMPONENT.user_edit_view){
        var req = Communication.request("GET", API.USER_GET + '/' + $stateParams.user_id, $scope.module);
        req.then(function (resp) {
            log("user edit: " + JSON.stringify(resp));

            if (resp.code === 200) {
                $scope.module = resp.body;
                $scope.module.password = "";
                $scope.isReadonly = true;
            }
        }, function (err) {
            log("user edit error", JSON.stringify(err));
        });
    }


    $scope.saveModule = function () {

        if( $state.current.name === JCOMPONENT.user_edit_view && !$rootScope.hasPermission(JMODULE_NAME, JCOMPONENT.user_edit_view) ) {
            $rootScope.toastError("Sorry, you don't have this permission");
            return;
        }
        if( $state.current.name === JCOMPONENT.user_add_view && !$rootScope.hasPermission(JMODULE_NAME, JCOMPONENT.user_add_view) ) {
            $rootScope.toastError("Sorry, you don't have this permission");
            return;
        }

        if( $scope.email_cnt !== 0 && $state.current.name !== JCOMPONENT.user_edit_view ){
            $rootScope.toastError("Email is already in used! Please, use another.");
            return;
        }

        if( $scope.lan_id_cnt !== 0 && $state.current.name !== JCOMPONENT.user_edit_view ){
            $rootScope.toastError("User id is already in used! Please, use another.");
            return;
        }


        var req;

        if($state.current.name === JCOMPONENT.user_edit_view){
            req = Communication.request("PUT", API.USER_UPDATE + '/' + $scope.module.user_id, $scope.module);
        } else{
            req = Communication.request("POST", API.USER_SAVE, $scope.module);
        }

        req.then(function (resp) {
            log("user manage: " + JSON.stringify(resp));
            if (resp.code === 200) {
                $scope.module = resp.body;
                $rootScope.toastSuccess("Successfully saved");
                $state.go(JCOMPONENT.user_list_view);
            } else{
                $rootScope.toastError(resp.message);
            }
        }, function (err) {
            log("user error", JSON.stringify(err));
            $rootScope.toastError(err.message);
        });
    };

    $scope.resetPaword = function (reset_npwrd, reset_cpwrd, pas_type) {
        $scope.reset_npwrd = reset_npwrd;
        $scope.reset_cpwrd = reset_cpwrd;
        $scope.form_reset_radio = pas_type;

        if( pas_type == 2 ){
            /*var msg = ClientService.isStrongPassword($scope.reset_npwrd);
            if( msg !== "OK" ){
                DialogBox.alert("Warning", msg);
                return;
            }*/

            if( $scope.reset_npwrd.trim().length < 1 ){
                DialogBox.alert("Warning", "Password is required!");
                angular.element('#reset_npwrd').focus();
                return;
            }

            if( $scope.reset_cpwrd.trim().length < 1 ){
                DialogBox.alert("Warning", "Retype password is required!");
                angular.element('#reset_cpwrd').focus();
                return;
            }

            if( $scope.reset_npwrd !== $scope.reset_cpwrd ){
                DialogBox.alert("Warning", "Password doesn't match!");
                angular.element('#reset_cpwrd').focus();
                return;
            }
        }

        DialogBox.confirm("Are you sure to reset password?").then(function(resp){
            if( resp ){
                var new_pwd = AES_ENC( $scope.reset_npwrd, AE5_SKY );
                var re_cnf_pwd = AES_ENC( $scope.reset_cpwrd, AE5_SKY );
                var req = Communication.request("PUT", API.RESET_UPAWRD, {"new_pwd":new_pwd, "re_cnf_pwd":re_cnf_pwd, "p_type":pas_type, "reseting_id":$stateParams.user_id});
                req.then(function (resp) {
                    console.log( JSON.stringify(resp) );
                    if (resp.code === 200 && (resp.body === 1 || resp.body === "1" ) ) {
                        $scope.reset_npwrd = "";
                        $scope.reset_npwrd = "";
                        DialogBox.alert("SUCCESS", "Password has been reset successfully!!!");
                    } else{
                        DialogBox.alert("WARNING", "Sorry, unable to reset password");
                    }
                }, function (err) {
                    DialogBox.alert("ERROR", err.msg);
                });
            }
        });

    };

    $scope.unlockUserAccount = function () {
        req = Communication.request("PUT", API.USER_UNLOCK + '/' + $scope.module.user_id);
        req.then(function (resp) {
            log("user manage: " + JSON.stringify(resp));
            if (resp.code === 200) {
                $scope.module = resp.body;
                $rootScope.toastSuccess("Successfully user unlock");
                $state.go(JCOMPONENT.user_list_view);
            } else{
                $rootScope.toastError(resp.message);
            }
        }, function (err) {
            log("user error", JSON.stringify(err));
            $rootScope.toastError(err.message);
        });
    };


    $scope.selectRole = function(id){
        $scope.module.role_id = id;
        var sl = document.getElementById("role_id");
        $scope.module.role_name = sl.options[sl.selectedIndex].text;

        console.log(JSON.stringify($scope.module));
    };

    $scope.checkEmail = function(mail_txt){
        if( $scope.current_state == JCOMPONENT.user_edit_view || mail_txt.length < 1 ) return;

        $scope.valid_email = -1;
        $scope.email_cnt = -1;

        var req = Communication.request("PUT", API.CHECK_EMAIL, {"usremail":mail_txt});
        req.then(function (resp) {
            log("email check: " + JSON.stringify(resp));
            if (resp.code === 200 && resp.body.cnt >= 0) {
                $scope.email_cnt = resp.body.cnt;
            } else{
                $scope.valid_email = 0;
            }
        }, function (err) {
            log("email check error", JSON.stringify(err));
        });
    };

    $scope.checkLanId = function(lan_id_txt){
        if( $scope.current_state == JCOMPONENT.user_edit_view || lan_id_txt.length < 1 ) return;

        $scope.valid_lan_id = -1;
        $scope.lan_id_cnt = -1;

        var req = Communication.request("PUT", API.CHECK_LAN_ID, {"lan_id":lan_id_txt});
        req.then(function (resp) {
            log("Lan id check: " + JSON.stringify(resp));
            if (resp.code === 200 && resp.body.cnt >= 0) {
                $scope.lan_id_cnt = resp.body.cnt;
            } else{
                $scope.valid_lan_id = 0;
            }
        }, function (err) {
            log("Lan id check error", JSON.stringify(err));
        });
    };

    $scope.reset = function() {
        $scope.valid_email = -1;
        $scope.email_cnt = -1;
        $scope.valid_lan_id = -1;
        $scope.lan_id_cnt = -1;
    };

});