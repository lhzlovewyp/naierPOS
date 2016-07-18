
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
	var itemsPerPage = 10;
	var currentPage = 1;
	var totalItems = 0;
	var selectedId ="";
	$scope.chk = false;
	
	$scope.paginationConf = {
        currentPage: currentPage,
        itemsPerPage: itemsPerPage,
        pagesLength: 15,
        perPageOptions: [10, 20, 30, 40, 50],
        rememberPerPage: 'perPageItems',
        onChange: function(){
        	var body = {};
    		body.start = $scope.paginationConf.currentPage - 1;
    		body.limit = itemsPerPage;
        	AccountService.queryByPage(body).then(function(data){
        		$scope.info = data;
        		$scope.paginationConf.totalItems = data.totalRecord;
            });
        }
    };
	
	$scope.queryByPage = function(){
		var body = {};
		body.start = 0;
		body.limit = itemsPerPage;
		AccountService.queryByPage(body).then(function(data){
			$scope.info = data;
			$scope.paginationConf.totalItems = data.totalRecord;
        });
	};
	
	$scope.delAccount = function(){
		var body = {};
		body.id = selectedId;
		AccountService.del(body).then(function(data){
			if(data && data.delerror){
				alert("删除数据出错:"+data.delerror);
            }else{
            	location.href="account.html";
            }
        });
	};
	
	$scope.check = function(val,chk){
		if(!chk == true){
			selectedId += val+",";
        }else{
        	selectedId = selectedId.replace(val+",","");
        }
	}
}]);

app.controller("routeAddAccountCtl",['$scope','$location','AccountService',function($scope,$location,AccountService){
	$scope.statuses = [
	        	    {value : "1", show : "有效"},
	        	    {value : "0", show : "无效"}
	        	];
	
	var form = {};
    $scope.form = form;
	$scope.addAccount = function(isValid){
		if(isValid) {
			var selstatus = $scope.selstatus;
			if(selstatus){
				$scope.form.status = selstatus.value;
			}else{
				$scope.form.status = "1";
			}
			if($scope.form.changePWD)
				$scope.form.changePWD = "1";
			else
				$scope.form.changePWD = "0";
			AccountService.add($scope.form).then(function(data){
				if(data && data.adderror){
					$scope.accountInfo = data;
                }else{
                	location.href="account.html";
                }
            });
        }else{
            angular.forEach($scope.accountForm,function(e){
                if(typeof(e) == 'object' && typeof(e.$dirty) == 'boolean'){
                    e.$dirty = true;
                }
            });
        }
	}
}]);

app.controller("routeBasicsCtl",['$scope','$location','$routeParams','BasicsService',function($scope,$location,$routeParams,BasicsService){
	var initItemsPerPage = 10;
	var initCurrentPage = 1;
	var totalItems = 0;
	var selectedId ="";
	$scope.chk = false;
	var routePath = $routeParams.routePath;
	function goPage(pageNo){
		var body = {};
		body.pageNo = pageNo;
		body.limit = $scope.paginationConf.itemsPerPage;
		body.likeName = $scope.likeName;
		BasicsService.queryByPage(body,routePath).then(function(data){
    		$scope.basicsInfo = data;
    		$scope.paginationConf.totalItems = data.totalRecord;
        });
	}
	
	$scope.paginationConf = {
        currentPage: initCurrentPage,
        itemsPerPage: initItemsPerPage,
        pagesLength: 15,
        perPageOptions: [10, 20, 30, 40, 50],
        rememberPerPage: 'perPageItems',
        onChange: function(){
        	goPage($scope.paginationConf.currentPage);
        }
    };
	
	$scope.queryByPage = function(){
		goPage(1);
	};
	
	$scope.del = function(){
		var body = {};
		body.id = selectedId;
		BasicsService.del(body,routePath).then(function(data){
			if(data && data.delerror){
				alert("删除数据出错:"+data.delerror);
            }else{
            	alert("删除数据成功");
            	goPage($scope.paginationConf.currentPage);
            }
        });
	};
	
	$scope.check = function(val,chk){
		if(!chk == true){
			selectedId += val+",";
        }else{
        	selectedId = selectedId.replace(val+",","");
        }
	}
}]);

app.controller("routeEditBasicsCtl",['$scope','$location','$routeParams','BasicsService',function($scope,$location,$routeParams,BasicsService){
	var allStatus = [
		        	    {value : "1", show : "有效"},
		        	    {value : "0", show : "无效"}
		        	];
	$scope.statuses = allStatus;
	$scope.editType = 'add';
	var id = $routeParams.id;
	var routePath = $routeParams.routePath;
	if(id){
		$scope.editType = 'update';
		var body = {};
		body.id = id;
		BasicsService.queryById(body,routePath).then(function(data){
			data.clientId = data.client.id;
			var selStatusValue = data.status;
			for ( var i = 0; i < allStatus.length; i++) {
				if(allStatus[i].value == selStatusValue){
					$scope.selstatus = allStatus[i];	
					break;
				}
			}
			$scope.form = data;
        });
	}else{
		$scope.form = {};
	}
	
	$scope.edit = function(isValid){
		if(isValid) {
			var selstatus = $scope.selstatus;
			if(selstatus){
				$scope.form.status = selstatus.value;
			}else{
				$scope.form.status = "1";
			}
			if($scope.editType == 'add'){
				BasicsService.add($scope.form,routePath).then(function(data){
					if(data && data.error){
						$scope.info = data;
	                }else{
	                	$location.path('/backend/list/'+routePath);
	                }
	            });
			}else if($scope.editType == 'update'){
				BasicsService.update($scope.form,routePath).then(function(data){
					if(data && data.error){
						$scope.info = data;
	                }else{
	                	$location.path('/backend/list/'+routePath);
	                }
	            });
			}
			
        }else{
            angular.forEach($scope.basicsForm,function(e){
                if(typeof(e) == 'object' && typeof(e.$dirty) == 'boolean'){
                    e.$dirty = true;
                }
            });
        }
	}
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

