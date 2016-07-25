
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

app.controller("routeEditBasicsCtl",['$scope','$location','$routeParams','ngDialog','BasicsService',function($scope,$location,$routeParams,ngDialog,BasicsService){
	var allStatus = [
		        	    {value : "1", show : "有效"},
		        	    {value : "0", show : "无效"}
		        	];
	$scope.statuses = allStatus;
	$scope.editType = 'add';
	var id = $routeParams.id;
	var routePath = $routeParams.routePath;
	var selectComplete = {};
	var allSelectInfoMap = {};
	var completeQueryById = false;
	
	function setSelectedInfo(){
		var data = $scope.form;
		if(data && completeQueryById){
			if(routePath == 'unitConversion' && allSelectInfoMap['unit']){
				var selectInfoMap = allSelectInfoMap['unit'];
				var selUnitAValue = data.unitA.id;
				$scope.selUnitA = selectInfoMap[selUnitAValue];
				var selUnitBValue = data.unitB.id;
				$scope.selUnitB = selectInfoMap[selUnitBValue];
			}else if((routePath == 'account' || routePath == 'terminal') && allSelectInfoMap['store']){
				var selectInfoMap = allSelectInfoMap['store'];
				var selStoreValue = data.store.id;
				$scope.selstore = selectInfoMap[selStoreValue];
			}else if(routePath == 'account' && allSelectInfoMap['role']){
				var selectInfoMap = allSelectInfoMap['role'];
				var selRoleValue = data.role.id;
				$scope.selrole = selectInfoMap[selRoleValue];
			}
		}
	}
	
	function queryBasicsInfoById(){
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
			if(routePath == 'materialCategory'){
				data.parentId = data.parent.id;
			}
			$scope.form = data;
			completeQueryById = true;
			setSelectedInfo();
        });
	}
	
	function querySelectInfo(type,selectParam,queryBasics){
		var pageBody = {};
		pageBody.pageNo = 1;
		pageBody.limit = 2147483647;
		BasicsService.queryByList(pageBody,type).then(function(data){
    		if(data && data.length > 0){
    			var len = data.length;
    			var allSelect = [];
    			var selectInfoMap = {};
    			for ( var i = 0; i < len; i++) {
    				var id = data[i].id;
    				var name = data[i].name;
					var selectObj = {"value":id,"show":name};
					allSelect.push(selectObj);
					selectInfoMap[id] = selectObj;
				}
    			$scope[selectParam] = allSelect;
    			
				allSelectInfoMap[type] = selectInfoMap;
				setSelectedInfo();
    		}
        });
	}
	
	if(id){		
		$scope.editType = 'update';
		
		if(routePath == 'unitConversion'){
			querySelectInfo('unit','units',1);
		}else if(routePath == 'account'){
			querySelectInfo('store','stores',1);
			querySelectInfo('role','roles',1);
		}else if(routePath == 'terminal'){
			querySelectInfo('store','stores',1);
		}
		queryBasicsInfoById();
	}else{
		$scope.form = {};
		if(routePath == 'unitConversion'){
			querySelectInfo('unit','units');
		}else if(routePath == 'account'){
			querySelectInfo('store','stores');
			querySelectInfo('role','roles');
		}
		else if(routePath == 'terminal'){
			querySelectInfo('store','stores');
		}
	}
	
	$scope.edit = function(isValid){
		if(isValid) {
			var selstatus = $scope.selstatus;
			if(selstatus){
				$scope.form.status = selstatus.value;
			}else{
				$scope.form.status = "1";
			}
			
			if(routePath == 'account'){
				if($scope.form.changePWD)
					$scope.form.changePWD = "1";
				else
					$scope.form.changePWD = "0";
			}
			
			if(routePath == 'unitConversion'){
				var selUnitA = $scope.selUnitA;
				if(selUnitA){
					$scope.form.unitAId = selUnitA.value;
				}
				var selUnitB = $scope.selUnitB;
				if(selUnitB){
					$scope.form.unitBId = selUnitB.value;
				}
			}
			if(routePath == 'materialCategory'){
				if($scope.form.parent.id)
					$scope.form.parentId = $scope.form.parent.id;
				else
					$scope.form.parentId = "0";
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
	
	//导购员信息查询.
	$scope.openMaterialCategoryTree = function(){
		
		ngDialog.open({
            template: '/backend/view/template/materialCategoryTree.html',
            scope: $scope,
            closeByEscape: false,
            controller: 'materialCategoryTreeCtrl'
        });
	}
}]);

app.controller("materialCategoryTreeCtrl",['$scope','$location','LoginService','ngDialog','BasicsService',function($scope,$location,LoginService,ngDialog,BasicsService){
	var selectedNode = {};
	
	
	//表格渲染完成后执行
	$scope.$on('ngRepeatFinished', function (ngRepeatFinishedEvent) {
		var body = {};
		BasicsService.queryTree(body,'materialCategory').then(function(data){
			var tree = data;
			$('#materialCategoryTree').treeview({
				data: tree,
				onNodeSelected: function(event, node) {
					selectedNode = node;
	            }}
			);
        });
	});
	$scope.close=function(){
		ngDialog.close();
	};
	
	$scope.selectMCTree=function(){
		if(!$scope.form.parent){
			$scope.form.parent = {};
		}
		$scope.form.parent.id = selectedNode.id;
		$scope.form.parent.name = selectedNode.text;
		selectedNode = {};
		ngDialog.close();
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

