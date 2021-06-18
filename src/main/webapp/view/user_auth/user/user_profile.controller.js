app.controller('UserProfileCtrl', function ($scope, $http, $state, $timeout, $stateParams, $rootScope, $sce, $mdDialog, $interval, ClientService, DialogBox, encrypt, Communication) {
    if( $state.current.name === JCOMPONENT.user_profile_view ){
        angular.element('#page_name').html("User profile");
    } else if( $state.current.name === JCOMPONENT.user_profile_view ){
        angular.element('#page_name').html("Change password");
    } else{}
    $scope.current_state = $state.current.name;
    
    $scope.current_pawrd = "";
    $scope.new_pawrd = "";
    $scope.confirm_new_pawrd = "";
    
    $scope.module = {
        user_id: "",
        user_code: "",
        first_name: "",
        last_name: "",
        email: "",
        password: "",
        phone: "",
        address: "",
        country_id: "",
        role_name:"",
        branch_name:"",
        company_name:""
    };
    
    $scope.getDetails = function(){
        var req = Communication.request("GET", API.SHOW_PROFILE, $scope.module);

        req.then(function (resp) {
            console.log("user details: " + JSON.stringify(resp));

            if (resp.code === 200) {
                $scope.module = resp.body;
            }
            
        }, function (err) {
            console.log("user details error", JSON.stringify(err));
        });
    };
    
    
    $scope.changePassword = function (current_pawrd, new_pawrd, confirm_new_pawrd) {
        $scope.current_pawrd = current_pawrd;
        $scope.new_pawrd = new_pawrd;
        $scope.confirm_new_pawrd = confirm_new_pawrd;
        
        /*var msg = ClientService.isStrongPassword($scope.new_pawrd);
        if( msg !== "OK" ){
            DialogBox.alert("Warning", msg);
            return;
        }*/
        
        if( $scope.current_pawrd.trim().length < 1 ){
            DialogBox.alert("Warning", "Current password is required!");
            angular.element('#current_pawrd').focus();
            return;
        }
        
        if( $scope.new_pawrd.trim().length < 1 ){
            DialogBox.alert("Warning", "New password is required!");
            angular.element('#new_pawrd').focus();
            return;
        }
        
        if( $scope.confirm_new_pawrd.trim().length < 1 ){
            DialogBox.alert("Warning", "Confirm new password is required!");
            angular.element('#confirm_new_pawrd').focus();
            return;
        }
        
        if( $scope.new_pawrd !== $scope.confirm_new_pawrd ){
            DialogBox.alert("Warning", "Password doesn't match!");
            angular.element('#new_pawrd').focus();
            return;
        }
        
        DialogBox.confirm("Are you sure to change password?").then(function(resp){
            if( resp ){
                var pwd = AES_ENC( $scope.current_pawrd, AE5_SKY );
                var new_pwd = AES_ENC( $scope.new_pawrd, AE5_SKY );
                var re_cnf_pwd = AES_ENC( $scope.confirm_new_pawrd, AE5_SKY );
                
                var req = Communication.request("PUT", API.CHANGE_UPAWRD, {"pwd":pwd, "new_pwd":new_pwd, "re_cnf_pwd":re_cnf_pwd});
                
                req.then(function (resp) {
                    console.log( JSON.stringify(resp) );
                    
                    if (resp.code === 200 && (resp.body === 1 || resp.body === "1" ) ) {
                        $scope.current_pawrd = "";
                        $scope.new_pawrd = "";
                        $scope.confirm_new_pawrd = "";
                        DialogBox.alert("SUCCESS", "Password has been changed");
                        
                    }else if (resp.code === 200 && (resp.body === 0 || resp.body === "0" ) ) {
                        DialogBox.alert("WARNING", "Sorry! unable to change your password");
                        
                    } else{
                        DialogBox.alert("WARNING", resp.message);
                    }
                }, function(err){
                    DialogBox.alert("ERROR", err.message);
                });
            }
        });
        
    };

});