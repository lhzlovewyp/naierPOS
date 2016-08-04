
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
	function goPage(pageNo,selectForm){
		var body = {};
		body.pageNo = pageNo;
		body.limit = $scope.paginationConf.itemsPerPage;
		body.likeName = $scope.likeName;
		if(selectForm){
		  body=$.extend(body,selectForm);
		}
		
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
        	goPage($scope.paginationConf.currentPage,$scope.selectForm);
        }
    };
	
	$scope.queryByPage = function(){
		goPage(1,$scope.selectForm);
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
	
	$scope.showYesOrNo = function(value){
		if(value == "1"){
			return "是";
		}else{
			return "否";
		}
	}
	$scope.showStatusDesc = function(value){
		if(value == "1"){
			return "有效";
		}else{
			return "无效";
		}
	}
	$scope.showAllowDesc = function(value){
		if(value == "1"){
			return "允许";
		}else{
			return "不允许";
		}
	}
}]);

app.controller("routeEditBasicsCtl",['$scope','$location','$routeParams','ngDialog','BasicsService',function($scope,$location,$routeParams,ngDialog,BasicsService){
	var allStatus = [
		        	    {value : "1", show : "有效"},
		        	    {value : "0", show : "无效"}
		        	];
	var allOfferRelation = [
		        	    {value : "SIN", show : "只享受一个"},
		        	    {value : "ALL", show : "同时享受"}
		        	];
	var allPaymentRestrict = [
		        	    {value : "NON", show : "不限制"},
		        	    {value : "INCL", show : "包含"},
		        	    {value : "EXCL", show : "排除"}
		        	];
	var allMemberRestrict = [
		        	    {value : "ALL", show : "会员能参加"},
		        	    {value : "ONLY", show : "只能会员参加"},
		        	    {value : "NON", show : "会员不能参加"}
		        	];
	$scope.statuses = allStatus;
	$scope.editType = 'add';
	var id = $routeParams.id;
	var routePath = $routeParams.routePath;
	var selectComplete = {};
	var allSelectInfoMap = {};
	var completeQueryById = false;
	//绑定时间控件.
	$scope.$on('$viewContentLoaded', function(){
		$('[data-provide="datepicker-inline"]').datepicker();
	});
	
	$scope.routePath = routePath;
	
	if(routePath == 'promotion'){
		$scope.offerRelations = allOfferRelation;
		$scope.paymentRestricts = allPaymentRestrict;
		$scope.memberRestricts = allMemberRestrict;
	}
	
	function setSelectedInfo(){
		var data = $scope.form;
		if(data && completeQueryById){
			if(routePath == 'unitConversion' && allSelectInfoMap['unit']){
				var selectInfoMap = allSelectInfoMap['unit'];
				if(data.unitA && data.unitA.id){
					var selUnitAValue = data.unitA.id;
					$scope.selUnitA = selectInfoMap[selUnitAValue];
				}
				if(data.unitB && data.unitB.id){
					var selUnitBValue = data.unitB.id;
					$scope.selUnitB = selectInfoMap[selUnitBValue];
				}
			}
			if((routePath == 'account' || routePath == 'terminal' || routePath == 'salesConfig'|| routePath == 'retailPrice') && allSelectInfoMap['store']){
				var selectInfoMap = allSelectInfoMap['store'];
				if(data.store && data.store.id){
					var selStoreValue = data.store.id;
					$scope.selstore = selectInfoMap[selStoreValue];
				}
			}
			if(routePath == 'account' && allSelectInfoMap['role']){
				var selectInfoMap = allSelectInfoMap['role'];
				if(data.roles && data.roles.length > 0){
					for(var roleIndex =0 ; roleIndex < data.roles.length; roleIndex++){
						var selRole = data.roles[roleIndex];
						var selRoleValue = selRole.id;
						selectInfoMap[selRoleValue].ticked = true;
					}
				}
			}
			if(routePath == 'salesConfig' && allSelectInfoMap['terminal']){
				var selectInfoMap = allSelectInfoMap['terminal'];
				if(data.terminal && data.terminal.id){
					var selTerminalValue = data.terminal.id;
					$scope.selterminal = selectInfoMap[selTerminalValue];
				}
			}
			if(routePath == 'clientPayment' && allSelectInfoMap['payment']){
				var selectInfoMap = allSelectInfoMap['payment'];
				if(data.payment && data.payment.id){
					var selPaymentValue = data.payment.id;
					$scope.selPayment = selectInfoMap[selPaymentValue];
				}
			}
			if(routePath == 'retailPrice' && allSelectInfoMap['material']){
				var selectInfoMap = allSelectInfoMap['material'];
				if(data.material && data.material.id){
					var selMaterialValue = data.material.id;
					$scope.selMaterial = selectInfoMap[selMaterialValue];
				}
			}
			if(routePath == 'retailPrice' && allSelectInfoMap['unit']){
				var selectInfoMap = allSelectInfoMap['unit'];
				if(data.unit && data.unit.id){
					var selUnitValue = data.unit.id;
					$scope.selUnit = selectInfoMap[selUnitValue];
				}
			}
			if(routePath == 'material' && allSelectInfoMap['brand']){
				var selectInfoMap = allSelectInfoMap['brand'];
				if(data.brand && data.brand.id){
					var selBrandValue = data.brand.id;
					$scope.selBrand = selectInfoMap[selBrandValue];
				}
			}
			if(routePath == 'material' && allSelectInfoMap['unit']){
				var selectInfoMap = allSelectInfoMap['unit'];
				if(data.basicUnit && data.basicUnit.id){
					var selBasicUnitValue = data.basicUnit.id;
					$scope.selBasicUnit = selectInfoMap[selBasicUnitValue];
				}
				if(data.salesUnit && data.salesUnit.id){
					var selSalesUnitValue = data.salesUnit.id;
					$scope.selSalesUnit = selectInfoMap[selSalesUnitValue];
				}
			}
			if(routePath == 'promotionPayment' && allSelectInfoMap['promotion']){
				var selectInfoMap = allSelectInfoMap['promotion'];
				if(data.promotion && data.promotion.id){
					var selPromotionValue = data.promotion.id;
					$scope.selPromotion = selectInfoMap[selPromotionValue];
				}
			}
			if(routePath == 'promotionPayment' && allSelectInfoMap['clientPayment']){
				var selectInfoMap = allSelectInfoMap['clientPayment'];
				if(data.clientPayment && data.clientPayment.id){
					var selClientPaymentValue = data.clientPayment.id;
					$scope.selClientPayment = selectInfoMap[selClientPaymentValue];
				}
			}
			if(routePath == 'materialProperty' && allSelectInfoMap['material']){
				var selectInfoMap = allSelectInfoMap['material'];
				if(data.material && data.material.id){
					var selMaterialValue = data.material.id;
					$scope.selMaterial = selectInfoMap[selMaterialValue];
				}
			}
			if(routePath == 'materialProperty' && allSelectInfoMap['color']){
				var selectInfoMap = allSelectInfoMap['color'];
				if(data.color && data.color.id){
					var selColorValue = data.color.id;
					$scope.selColor = selectInfoMap[selColorValue];
				}
			}
			if(routePath == 'materialProperty' && allSelectInfoMap['size']){
				var selectInfoMap = allSelectInfoMap['size'];
				if(data.size && data.size.id){
					var selSizeValue = data.size.id;
					$scope.selSize = selectInfoMap[selSizeValue];
				}
			}
		}
	}
	
	function queryBasicsInfoById(){
		var body = {};
		body.id = id;
		BasicsService.queryById(body,routePath).then(function(data){
			if(data){
				if(data.client){
					data.clientId = data.client.id;
				}
				var selStatusValue = data.status;
				for ( var i = 0; i < allStatus.length; i++) {
					if(allStatus[i].value == selStatusValue){
						$scope.selstatus = allStatus[i];	
						break;
					}
				}
				if(routePath == 'store'){
					if(data.opened){
						data.opened=new Date(data.opened).format('yyyy-MM-dd');
					}
				}
				
				if(routePath == 'salesConfig'){
					if(data.salesDate){
						data.salesDate=new Date(data.salesDate).format('yyyy-MM-dd');
					}
				}
				
				if(routePath == 'retailPrice'){
					if(data.effectiveDate){
						data.effectiveDate=new Date(data.effectiveDate).format('yyyy-MM-dd');
					}
					if(data.expiryDate){
						data.expiryDate=new Date(data.expiryDate).format('yyyy-MM-dd');
					}
				}
				
				if(routePath == 'account'){
					if(data.changePWD == 1){
						data.changePWD = true;
					}
				}
				
				if(routePath == 'role'){
					if(data.loginTerminal == 1){
						data.loginTerminal = true;
					}
					if(data.loginAdmin == 1){
						data.loginAdmin = true;
					}
					if(data.itemDISC == 1){
						data.itemDISC = true;
					}
					if(data.allDISC == 1){
						data.allDISC = true;
					}
				}
				
				if(routePath == 'material'){
					if(data.property == 1){
						data.property = true;
					}
				}
				if(routePath == 'promotion'){
					var selOfferRelation = data.offerRelation;
					for ( var i = 0; i < allOfferRelation.length; i++) {
						if(allOfferRelation[i].value == selOfferRelation){
							$scope.selOfferRelation = allOfferRelation[i];	
							break;
						}
					}
					var selPaymentRestrict = data.paymentRestrict;
					for ( var i = 0; i < allPaymentRestrict.length; i++) {
						if(allPaymentRestrict[i].value == selPaymentRestrict){
							$scope.selPaymentRestrict = allPaymentRestrict[i];	
							break;
						}
					}
					var selMemberRestrict = data.memberRestrict;
					for ( var i = 0; i < allMemberRestrict.length; i++) {
						if(allMemberRestrict[i].value == selMemberRestrict){
							$scope.selMemberRestrict = allMemberRestrict[i];	
							break;
						}
					}
					if(data.repeatEffect == 1){
						data.repeatEffect = true;
					}
					if(data.effDate){
						data.effDate=new Date(data.effDate).format('yyyy-MM-dd');
					}
					if(data.expDate){
						data.expDate=new Date(data.expDate).format('yyyy-MM-dd');
					}
				}
				
				$scope.form = data;
			}
			completeQueryById = true;
			setSelectedInfo();
        });
	}
	
	function querySelectInfo(type,selectParam,queryBasics){
		var pageBody = {};
		BasicsService.queryByList(pageBody,type).then(function(data){
    		if(data && data.length > 0){
    			var len = data.length;
    			var allSelect = [];
    			var selectInfoMap = {};
    			for ( var i = 0; i < len; i++) {
    				var id = data[i].id;
    				if(type == 'payment'){
    					id = data[i].code;
    				}
    				var name = data[i].name;
    				if(type == 'clientPayment'){
    					name = data[i].payment.name;
    				}
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
			querySelectInfo('role','modernRoles',1);
		}else if(routePath == 'terminal'){
			querySelectInfo('store','stores',1);
		}else if(routePath == 'salesConfig'){
			querySelectInfo('store','stores',1);
			querySelectInfo('terminal','terminals',1);
		}else if(routePath == 'clientPayment'){
			querySelectInfo('payment','Payments',1);
		}else if(routePath == 'retailPrice'){
			querySelectInfo('store','stores',1);
			querySelectInfo('material','materials',1);
			querySelectInfo('unit','units',1);
		}else if(routePath == 'material'){
			querySelectInfo('brand','brands',1);
			querySelectInfo('unit','units',1);
		}else if(routePath == 'promotionPayment'){
			querySelectInfo('clientPayment','clientPayments',1);
			querySelectInfo('promotion','promotions',1);
		}else if(routePath == 'materialProperty'){
			querySelectInfo('material','materials',1);
			querySelectInfo('color','colors',1);
			querySelectInfo('size','sizes',1);
		}
		queryBasicsInfoById();
	}else{
		$scope.form = {};
		if(routePath == 'unitConversion'){
			querySelectInfo('unit','units');
		}else if(routePath == 'account'){
			querySelectInfo('store','stores');
			querySelectInfo('role','modernRoles');
		}else if(routePath == 'terminal'){
			querySelectInfo('store','stores');
		}else if(routePath == 'salesConfig'){
			querySelectInfo('store','stores');
			querySelectInfo('terminal','terminals');
		}else if(routePath == 'clientPayment'){
			querySelectInfo('payment','Payments');
		}else if(routePath == 'retailPrice'){
			querySelectInfo('store','stores');
			querySelectInfo('material','materials');
			querySelectInfo('unit','units');
		}else if(routePath == 'material'){
			querySelectInfo('brand','brands');
			querySelectInfo('unit','units');
		}else if(routePath == 'promotionPayment'){
			querySelectInfo('clientPayment','clientPayments');
			querySelectInfo('promotion','promotions');
		}else if(routePath == 'materialProperty'){
			querySelectInfo('material','materials');
			querySelectInfo('color','colors');
			querySelectInfo('size','sizes');
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
				
				if($scope.outputRoles){
					var finalRole = "";
					for(var roleIndex =0 ; roleIndex < $scope.outputRoles.length; roleIndex++){
						var selRole = $scope.outputRoles[roleIndex];
						var selRoleValue = selRole.value;
						finalRole += selRoleValue + ',';
					}
					$scope.form.roleId = finalRole;
				}
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
			
			if(routePath == 'terminal' || routePath == 'salesConfig' || routePath == 'retailPrice' || routePath == 'account'){
				var selstore = $scope.selstore;
				if(selstore){
					$scope.form.storeId = selstore.value;
				}
			}
			if(routePath == 'salesConfig'){
				var selterminal = $scope.selterminal;
				if(selterminal){
					$scope.form.terminalId = selterminal.value;
				}
			}
			if(routePath == 'clientPayment'){
				var selPayment = $scope.selPayment;
				if(selPayment){
					$scope.form.paymentCode = selPayment.value;
				}
			}
			if(routePath == 'retailPrice'){
				var selUnit = $scope.selUnit;
				if(selUnit){
					$scope.form.unitId = selUnit.value;
				}
				var selMaterial = $scope.selMaterial;
				if(selMaterial){
					$scope.form.materialId = selMaterial.value;
				}
			}
			
			if(routePath == 'role'){
				if($scope.form.loginTerminal)
					$scope.form.loginTerminal = "1";
				else
					$scope.form.loginTerminal = "0";
				if($scope.form.loginAdmin)
					$scope.form.loginAdmin = "1";
				else
					$scope.form.loginAdmin = "0";
				if($scope.form.itemDISC)
					$scope.form.itemDISC = "1";
				else
					$scope.form.itemDISC = "0";
				if($scope.form.allDISC)
					$scope.form.allDISC = "1";
				else
					$scope.form.allDISC = "0";
			}
			
			if(routePath == 'material'){
				if($scope.form.category.id)
					$scope.form.categoryId = $scope.form.category.id;
				else
					$scope.form.categoryId = "";
				var selBrand = $scope.selBrand;
				if(selBrand){
					$scope.form.brandId = selBrand.value;
				}
				var selBasicUnit = $scope.selBasicUnit;
				if(selBasicUnit){
					$scope.form.basicUnitId = selBasicUnit.value;
				}
				var selSalesUnit = $scope.selSalesUnit;
				if(selSalesUnit){
					$scope.form.salesUnitId = selSalesUnit.value;
				}
				if($scope.form.property)
					$scope.form.property = "1";
				else
					$scope.form.property = "0";
			}
			if(routePath == 'promotion'){
				var selOfferRelation = $scope.selOfferRelation;
				if(selOfferRelation){
					$scope.form.offerRelationId = selOfferRelation.value;
				}
				var selPaymentRestrict = $scope.selPaymentRestrict;
				if(selPaymentRestrict){
					$scope.form.paymentRestrictId = selPaymentRestrict.value;
				}
				var selMemberRestrict = $scope.selMemberRestrict;
				if(selMemberRestrict){
					$scope.form.memberRestrictId = selMemberRestrict.value;
				}
				if($scope.form.repeatEffect)
					$scope.form.repeatEffect = "1";
				else
					$scope.form.repeatEffect = "0";
			}
			if(routePath == 'promotionPayment'){
				var selPromotion = $scope.selPromotion;
				if(selPromotion){
					$scope.form.promotionId = selPromotion.value;
				}
				var selClientPayment = $scope.selClientPayment;
				if(selClientPayment){
					$scope.form.clientPaymentId = selClientPayment.value;
				}
			}
			if(routePath == 'materialProperty'){
				var selMaterial = $scope.selMaterial;
				if(selMaterial){
					$scope.form.materialId = selMaterial.value;
				}
				var selColor = $scope.selColor;
				if(selColor){
					$scope.form.colorId = selColor.value;
				}
				var selSize = $scope.selSize;
				if(selSize){
					$scope.form.sizeId = selSize.value;
				}
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
	
	$scope.cancel=function(){
		$location.path('/backend/list/'+routePath);
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
		if($scope.routePath == 'materialCategory'){
			if(!$scope.form.parent){
				$scope.form.parent = {};
			}
			$scope.form.parent.id = selectedNode.id;
			$scope.form.parent.name = selectedNode.text;
		}else if($scope.routePath == 'material'){
			if(!$scope.form.category){
				$scope.form.category = {};
			}
			$scope.form.category.id = selectedNode.id;
			$scope.form.category.name = selectedNode.text;
		}
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

