
app.controller("loginCtrl",['$scope','$location','LoginService',function($scope,$location,LoginService){
	var form = {};
    $scope.form = form;
	$scope.login = function(isValid){
		if(isValid) {
            LoginService.login($scope.form).then(function(data){
                if(data.hasLogin){
                	//如果是初次登陆,强制修改密码
                	if(data.changePWD == "1"){
                		location.href="changePWD.html";
                	}else{
                		location.href="home.html";
                	}
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

app.controller("headNavCtrl",['$scope','$location','LoginService',function($scope,$location,LoginService){
	LoginService.validateToken().then(function(data){
		$scope.loginInfo = data;
	});
	
	$scope.logout = function(){
		LoginService.logOff();
	}
}]);

app.controller("routeMainCtl",['$scope','$location','HomeService',function($scope,$location,HomeService){
	HomeService.getHomeData().then(function(data){
		$scope.info=data;
	});
	
	$scope.dayReport = function(){
		HomeService.dayReport().then(function(data){
			if(data.status == Status.SUCCESS){
				location.reload();
			}else{
				alert('日结失败，请稍后重试.');
			}
		});
	}
}]);

app.controller("routeSaleCtl",['$scope','$location','SaleService','ngDialog',function($scope,$location,SaleService,ngDialog){
	SaleService.initSalesOrder().then(function(data){
		$scope.info=data;
	});
	
	$scope.memberSearch = function(){
		 ngDialog.open({
             template: '/front/view/template/memberSearch.html',
             scope: $scope,
             closeByEscape: false,
             controller: 'memberCtrl'
         });
	}
	
	$scope.shoppingGuideSearch = function(){
		
		ngDialog.open({
            template: '/front/view/template/shoppingGuideSearch.html',
            scope: $scope,
            closeByEscape: false,
            controller: 'shoppingGuideCtrl'
        });
	}
	
}]);
app.controller("memberCtrl",['$scope','$location','SaleService','ngDialog',function($scope,$location,SaleService,ngDialog){
	
}]);
app.controller("shoppingGuideCtrl",['$scope','$location','SaleService','ngDialog',function($scope,$location,SaleService,ngDialog){
	$scope.array=[];
	$scope.close=function(){
		ngDialog.close();
	};
	$scope.search=function(){
		
		 SaleService.getShoppingGuide($scope.form).then(function(data){
			 $scope.array[0]=data.data;
		 });
	}
	
	$scope.choose = function(id){
		ngDialog.close();
		var info=$scope.info || {};
		info.shoppingGuide=info.shoppingGuide || {};
		info.shoppingGuide.id=id;
	}
	
}]);

