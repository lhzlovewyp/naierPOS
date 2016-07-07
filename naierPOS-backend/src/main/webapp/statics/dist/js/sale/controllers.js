
app.controller("loginCtrl",['$scope','$location','LoginService',function($scope,$location,LoginService){
	var form = {};
    $scope.form = form;
	$scope.login = function(isValid){
		if(isValid) {
            LoginService.login($scope.form).then(function(data){
                if(data.hasLogin){
                    location.href="home.html";
                }else{
                    $scope.loginInfo = data;
                }
            });
        }else{
            angular.forEach($scope.loginForm,function(e){
                if(typeof(e) == 'object' && typeof(e.$dirty) == 'boolean'){
                    e.$dirty = true;
                }
            });
        }
	};
	
	$scope.flushVerifyCode = function(){
		document.getElementById("verifyCodeImg").src="/rest/captcha-image"
	};
	
	
}]);

app.controller("changePWDCtrl",['$scope','$location','LoginService',function($scope,$location,LoginService){
	
	LoginService.validateToken().then(function(data){
		$scope.loginInfo = data;
	});
	
	//修改密码
	$scope.changePWD = function(isValid){
		if(isValid) {
			LoginService.updatePWD($scope.form).then(function(data){
                if(data.updateStatus){
                	//密码修改成功.
                	location.href="login.html";
                }else{
                    $scope.info = data;
                }
            });
        }else{
            angular.forEach($scope.changePWDForm,function(e){
                if(typeof(e) == 'object' && typeof(e.$dirty) == 'boolean'){
                    e.$dirty = true;
                }
            });
        }
	};
}]);

app.controller("routeAccountCtl",['$scope','$location','AccountService',function($scope,$location,AccountService){
	$scope.queryByPage = function(start,limit){
		var body = {};
		body.start = start||0;
		body.limit = limit||10;
		AccountService.queryByPage(body).then(function(data){
			$scope.info = data;
        });
	};	
	
}]);

app.controller("headNavCtrl",['$scope','$location','LoginService',function($scope,$location,LoginService){
	LoginService.validateToken().then(function(data){
		$scope.loginInfo = data;
	});
	
	$scope.goToChangePWD = function(){
		location.href = '/backend/changePWD.html';
	}
	
	$scope.logout = function(){
		LoginService.logOff();
	}
}]);

app.controller("routeMainCtl",['$scope','$location','LoginService',function($scope,$location,LoginService){
	
}]);

