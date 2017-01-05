
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
	

	//绑定时间控件.
	$scope.$on('$viewContentLoaded', function(){
		$('[data-provide="datepicker-inline"]').datepicker();
	});
	
	var manualClick = false;
	var initItemsPerPage = 10;
	var initCurrentPage = 1;
	var totalItems = 0;
	var selectedId ="";
	$scope.chk = false;
	var routePath = $routeParams.routePath;
	
	if(routePath == 'promotionOfferMatchContent' || routePath == 'promotionCondition'){
		var promotionId = $location.search()['promotionId'];
		$scope.promotionId = promotionId;
	}
	
	function goPage(pageNo,selectForm){
		var body = {};
		body.pageNo = pageNo;
		body.limit = $scope.paginationConf.itemsPerPage;
		body.likeName = $scope.likeName;
		if(selectForm){
			body=$.extend(body,selectForm);
		}
		
		if(routePath == 'promotionOffer' || routePath == 'promotionStore' || routePath == 'promotionPayment'){
			var promotionId = $location.search()['promotionId'];
			if(promotionId){
				$scope.promotionId = promotionId;
				body.promotionId = promotionId;
			}else{
				$location.path('/backend/list/promotion');
				return;
			}
		}
		
		if(routePath == 'promotionOfferMatchContent' || routePath == 'promotionCondition'){
			var promotionOfferId = $location.search()['promotionOfferId'];
			if(promotionOfferId){
				$scope.promotionOfferId = promotionOfferId;
				body.promotionOfferId = promotionOfferId;
			}else{
				$location.path('/backend/list/promotion');
				return;
			}
		}
		
		if(routePath == 'promotionConditionMatchContent'){
			var promotionConditionId = $location.search()['promotionConditionId'];
			if(promotionConditionId){
				$scope.promotionConditionId = promotionConditionId;
				body.promotionConditionId = promotionConditionId;
			}else{
				$location.path('/backend/list/promotion');
				return;
			}
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
        	if(!manualClick){
        		goPage($scope.paginationConf.currentPage,$scope.selectForm);
        	}else{
        		manualClick = false;
        	}
        }
    };
	
	if(routePath == 'retailConfig'){
		goPage($scope.paginationConf.currentPage);
	}
	
	$scope.queryByPage = function(){
		manualClick = true;
		goPage(1,$scope.selectForm);
	};
	
	$scope.del = function(){
		if(confirm("确定执行删除?")){
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
		}
	};
	
	$scope.check = function(val,chk){
		if(!chk == true){
			selectedId += val+",";
        }else{
        	selectedId = selectedId.replace(val+",","");
        }
	};
	$scope.checkAll=function(arr,chk){
		$scope.chk=!chk;
		selectedId="";
		for(var i=0;i<arr.length;i++){
			var result=arr[i];
			if(!chk == true){//选中
				result.chk=true;
				selectedId+=result.id+",";
			}else{
				result.chk=false;
			}
		}
		
	}
	
	$scope.showYesOrNo = function(value){
		if(value == "1"){
			return "是";
		}else{
			return "否";
		}
	};
	$scope.showStatusDesc = function(value){
		if(value == "1"){
			return "有效";
		}else{
			return "无效";
		}
	};
	$scope.showAllowDesc = function(value){
		if(value == "1"){
			return "允许";
		}else{
			return "不允许";
		}
	};
	$scope.showOfferType = function(value){
		if(value == "DISC"){
			return "折扣";
		}else if(value == "RED"){
			return "折让";
		}else if(value == "SPCL"){
			return "特价";
		}else if(value == "FREE"){
			return "赠送商品";
		}else if(value == "EXT"){
			return "加价购买";
		}else{
			return value;
		}
	};
	$scope.showMatchType = function(value){
		if(value == "MATCAT"){
			return "品类";
		}else if(value == "BRAND"){
			return "品牌";
		}else if(value == "MAT"){
			return "物料";
		}else{
			return value;
		}
	};
	$scope.showConditionType = function(value){
		if(value == "MATQTY"){
			return "商品数量";
		}else if(value == "MATAMT"){
			return "商品金额";
		}else if(value == "TTLAMT"){
			return "整单金额";
		}else{
			return value;
		}
	};
	$scope.showMatch = function(value){
		if(value == "ANY"){
			return "任意";
		}else if(value == "SAME"){
			return "相同";
		}else if(value == "DIFF"){
			return "不同";
		}else{
			return value;
		}
	};
}]);

app.controller("routeEditBasicsCtl",['$scope','$location','$routeParams','ngDialog','BasicsService','$timeout', '$route',function($scope,$location,$routeParams,ngDialog,BasicsService,$timeout, $route){
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
	var allOfferType = [
	 		        	    {value : "DISC", show : "折扣"},
	 		        	    {value : "RED", show : "折让"},
	 		        	    {value : "SPCL", show : "特价"},
	 		        	    {value : "FREE", show : "赠送商品"},
	 		        	    {value : "EXT", show : "加价购买"}
	 		        	];
	var allMatchType = [
	 		        	    {value : "MATCAT", show : "品类"},
	 		        	    {value : "BRAND", show : "品牌"},
	 		        	    {value : "MAT", show : "物料"}
	 		        	];
	var allConditionType = [
 		        	    {value : "MATQTY", show : "商品数量"},
 		        	    {value : "MATAMT", show : "商品金额"},
 		        	    {value : "TTLAMT", show : "整单金额"}
 		        	];
	var allMatch = [
	 		        	    {value : "ANY", show : "任意"},
	 		        	    {value : "SAME", show : "相同"},
	 		        	    {value : "DIFF", show : "不同"}
	 		        	];
	$scope.statuses = allStatus;
	$scope.editType = 'add';
	$scope.addNedit = false;
	$scope.form={};
	var id = $routeParams.id;
	var routePath = $routeParams.routePath;
	
	var promotionId = $location.search()['promotionId'];
	var promotionOfferId = $location.search()['promotionOfferId'];
	var promotionConditionId = $location.search()['promotionConditionId'];
	
	var selectComplete = {};
	var allSelectInfoMap = {};
	var completeQueryById = false;
	//默认设置状态为有效.
	var selStatusValue = 1;
	for ( var i = 0; i < allStatus.length; i++) {
		if(allStatus[i].value == selStatusValue){
			$scope.selstatus = allStatus[i];	
			break;
		}
	}
	
	$scope.$watch("form", function(newValue, oldValue){
		$timeout(function(){
			$scope.$apply();
		});
		
	},true);
	
	$scope.routePath = routePath;
	
	//绑定时间控件.
	$scope.$on('$viewContentLoaded', function(){
		$('[data-provide="datepicker-inline"]').datepicker();
		
		if(routePath == 'materialCategory'){
			var body = {};
			body.needRoot = "1";
			BasicsService.queryTree(body,'materialCategory').then(function(data){
				var tree = data;
				$('#materialCategoryTree').treeview({
					data: tree,
					onNodeSelected: function(event, node) {
						if(!$scope.form){
							$scope.form = {};
						}
						if(node.data){
							$scope.form.code = node.data.code;
							$scope.form.name = node.text.split(":")[1];
							$scope.form.parent = {};
							$scope.form.parent.id = node.id;
						}else{
							$scope.form.code = "";
							$scope.form.name = "";
							$scope.form.parent = {};
							$scope.form.parent.id = 0;
						}
						$scope.$apply();//需要手动刷新
		            }}
				);
	        });
		}
	});
	
	if(routePath == 'promotion'){
		$scope.offerRelations = allOfferRelation;
		$scope.paymentRestricts = allPaymentRestrict;
		$scope.memberRestricts = allMemberRestrict;
	}
	if(routePath == 'promotionOffer'){
		$scope.offerTypes = allOfferType;
		$scope.matchTypes = allMatchType;
	}
	if(routePath == 'promotionCondition'){
		$scope.conditionTypes = allConditionType;
		$scope.matchs = allMatch;
		$scope.matchTypes = allMatchType;
	}
	
	/**
	 * 设置选择框中被选中的值
	 */
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
			if((routePath == 'account' || routePath == 'terminal' || routePath == 'salesConfig'|| routePath == 'retailPrice'|| routePath == 'shoppingGuide') && allSelectInfoMap['store']){
				var selectInfoMap = allSelectInfoMap['store'];
				if(data.store && data.store.id){
					var selStoreValue = data.store.id;
					$scope.selstore = selectInfoMap[selStoreValue];
				}
			}
			if(routePath == 'account'){
				if(allSelectInfoMap['role']){
					var selectInfoMap = allSelectInfoMap['role'];
					if(data.roles && data.roles.length > 0){
						for(var roleIndex =0 ; roleIndex < data.roles.length; roleIndex++){
							var selRole = data.roles[roleIndex];
							var selRoleValue = selRole.id;
							selectInfoMap[selRoleValue].ticked = true;
						}
					}
				}
				if(allSelectInfoMap['store']){//如果存在选中的门店.
					var selectInfoMap = allSelectInfoMap['store'];
					var readyStores=[];
					var selectedStores=[];
					if(data.stores && data.stores.length > 0){
						selectedStores=data.stores;
						for(var i=0;i<$scope.stores.length;i++){
							var store=$scope.stores[i];
							var flag=0;
							for(var j=0;j<selectedStores.length;j++){
								var selectedStore=selectedStores[j];
								if(selectedStore.code==store.code){
									flag=1;
									break;
								}
							}
							if(flag==0){
								readyStores.push(store);
							}
							
						}
					}else{
						readyStores=$scope.stores;
					}
					$scope.readyStores=readyStores;
					$scope.selectedStores=selectedStores;
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
				if(data.payment && data.payment.code){
					var selPaymentValue = data.payment.code;
					$scope.selPayment = selectInfoMap[selPaymentValue];
				}
			}
			if(routePath == 'retailPrice' && allSelectInfoMap['material']){
				var selectInfoMap = allSelectInfoMap['material'];
				if(data.material && data.material.id){
					var selMaterialValue = data.material.id;
					selectInfoMap[selMaterialValue].ticked = true;
					$scope.selMaterial = [selectInfoMap[selMaterialValue]];
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
				if(promotionId){
					$scope.selPromotion = selectInfoMap[promotionId];
					$("#Promotion").attr("disabled","disabled");
				}else if(data.promotion && data.promotion.id){
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
					selectInfoMap[selMaterialValue].ticked = true;
					$scope.selMaterial = [selectInfoMap[selMaterialValue]];
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
			if(routePath == 'promotionStore' && allSelectInfoMap['promotion']){
				var selectInfoMap = allSelectInfoMap['promotion'];
				if(promotionId){
					$scope.selPromotion = selectInfoMap[promotionId];
					$("#Promotion").attr("disabled","disabled");
				}else if(data.promotion && data.promotion.id){
					var selPromotionValue = data.promotion.id;
					$scope.selPromotion = selectInfoMap[selPromotionValue];
				}
			}
			if(routePath == 'promotionStore' && allSelectInfoMap['store']){
				var selectInfoMap = allSelectInfoMap['store'];
				if(data.store && data.store.id){
					var selStoreValue = data.store.id;
					$scope.selStore = selectInfoMap[selStoreValue];
				}
			}
			if(routePath == 'promotionOffer' && allSelectInfoMap['promotion']){
				var selectInfoMap = allSelectInfoMap['promotion'];
				if(promotionId){
					$scope.selPromotion = selectInfoMap[promotionId];
					$("#Promotion").attr("disabled","disabled");
				}else if(data.promotion && data.promotion.id){
					var selPromotionValue = data.promotion.id;
					$scope.selPromotion = selectInfoMap[selPromotionValue];
				}
			}
			if((routePath == 'promotionOfferMatchContent' || routePath == 'promotionConditionMatchContent') && allSelectInfoMap['materialCategory']){
				var selectInfoMap = allSelectInfoMap['materialCategory'];
				if(data.matchContent){
					var selMatchContentValue = data.matchContent;
					$scope.selMaterialCategory = selectInfoMap[selMatchContentValue];
				}
			}
			if((routePath == 'promotionOfferMatchContent' || routePath == 'promotionConditionMatchContent') && allSelectInfoMap['brand']){
				var selectInfoMap = allSelectInfoMap['brand'];
				if(data.matchContent){
					var selMatchContentValue = data.matchContent;
					$scope.selBrand = selectInfoMap[selMatchContentValue];
				}
			}
			if((routePath == 'promotionOfferMatchContent' || routePath == 'promotionConditionMatchContent') && allSelectInfoMap['material']){
				var selectInfoMap = allSelectInfoMap['material'];
				if(data.matchContent){
					var selMatchContentValue = data.matchContent;
					selectInfoMap[selMatchContentValue].ticked = true;
					$scope.selMaterial = [selectInfoMap[selMatchContentValue]];
				}
			}
		}else{
			if(routePath == 'account'){
				$scope.readyStores=$scope.stores;
			}
		}
	}
	
	/**
	 * 根据id查询明细
	 */
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
						$('#opened').datepicker('update',data.opened);
					}
				}
				
				if(routePath == 'salesConfig'){
					if(data.salesDate){
						data.salesDate=new Date(data.salesDate).format('yyyy-MM-dd');
						$('#SalesDate').datepicker('update',data.salesDate);
					}
				}
				
				if(routePath == 'retailPrice'){
					if(data.effectiveDate){
						data.effectiveDate=new Date(data.effectiveDate).format('yyyy-MM-dd');
						$('#EffectiveDate').datepicker('update',data.effectiveDate);
					}
					if(data.expiryDate){
						data.expiryDate=new Date(data.expiryDate).format('yyyy-MM-dd');
						$('#ExpiryDate').datepicker('update',data.expiryDate);
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
						$('#EffectiveDate').datepicker('update',data.effDate);
					}
					if(data.expDate){
						data.expDate=new Date(data.expDate).format('yyyy-MM-dd');
						$('#ExpiryDate').datepicker('update',data.expDate);
					}
				}
				if(routePath == 'promotionStore'){
					if(data.effDate){
						data.effDate=new Date(data.effDate).format('yyyy-MM-dd');
						$('#EffectiveDate').datepicker('update',data.effDate);
					}
					if(data.expDate){
						data.expDate=new Date(data.expDate).format('yyyy-MM-dd');
						$('#ExpiryDate').datepicker('update',data.expDate);
					}
				}
				if(routePath == 'promotionOffer'){
					var selOfferType = data.offerType;
					for ( var i = 0; i < allOfferType.length; i++) {
						if(allOfferType[i].value == selOfferType){
							$scope.selOfferType = allOfferType[i];	
							break;
						}
					}
					var selMatchType = data.matchType;
					for ( var i = 0; i < allMatchType.length; i++) {
						if(allMatchType[i].value == selMatchType){
							$scope.selMatchType = allMatchType[i];	
							break;
						}
					}
				}
				if(routePath == 'promotionCondition'){
					var selConditionType = data.conditionType;
					for ( var i = 0; i < allConditionType.length; i++) {
						if(allConditionType[i].value == selConditionType){
							$scope.selConditionType = allConditionType[i];	
							break;
						}
					}
					var selMatch = data.match;
					for ( var i = 0; i < allMatch.length; i++) {
						if(allMatch[i].value == selMatch){
							$scope.selMatch = allMatch[i];	
							break;
						}
					}
					var selMatchType = data.matchType;
					for ( var i = 0; i < allMatchType.length; i++) {
						if(allMatchType[i].value == selMatchType){
							$scope.selMatchType = allMatchType[i];	
							break;
						}
					}
				}
				if(routePath == 'promotionOfferMatchContent'){
					if(data.promotionOffer && data.promotionOffer.matchType){
						$('.MatchContent').hide();
						$('#'+data.promotionOffer.matchType).show();
					}
				}
				if(routePath == 'promotionConditionMatchContent'){
					if(data.promotionCondition && data.promotionCondition.matchType){
						$('.MatchContent').hide();
						$('#'+data.promotionCondition.matchType).show();
					}
				}
				
				$scope.form = data;
			}
			completeQueryById = true;
			setSelectedInfo();
        });
	}
	
	/**
	 * 获取下拉菜单
	 */
	function querySelectInfo(type,selectParam,queryBasics,paramObj){
		var pageBody = {};
		if(paramObj){
			for(var key in paramObj){
				pageBody[key] = paramObj[key];
			}
		}
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
    				var text = name + "(" + data[i].code + ")";
					var selectObj = {"value":id,"show":name,"id":id,"name":name,"code":data[i].code,"text":text};
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
			querySelectInfo('material','materials',1,{'start':0,'limit':20});
			querySelectInfo('unit','units',1);
		}else if(routePath == 'material'){
			querySelectInfo('brand','brands',1);
			querySelectInfo('unit','units',1);
		}else if(routePath == 'promotionPayment'){
			querySelectInfo('clientPayment','clientPayments',1);
			querySelectInfo('promotion','promotions',1);
		}else if(routePath == 'materialProperty'){
			querySelectInfo('material','materials',1,{'start':0,'limit':20});
			querySelectInfo('color','colors',1);
			querySelectInfo('size','sizes',1);
		}else if(routePath == 'promotionStore'){
			querySelectInfo('promotion','promotions',1);
			querySelectInfo('store','stores',1);
		}else if(routePath == 'promotionOffer'){
			querySelectInfo('promotion','promotions',1);
		}else if(routePath == 'promotionOfferMatchContent' || routePath == 'promotionConditionMatchContent'){
			querySelectInfo('materialCategory','materialCategorys',1);
			querySelectInfo('brand','brands',1);
			querySelectInfo('material','materials',1,{'start':0,'limit':20});
		}else if(routePath == 'shoppingGuide'){
			querySelectInfo('store','stores',1);
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
			querySelectInfo('material','materials',null,{'start':0,'limit':20});
			querySelectInfo('unit','units');
		}else if(routePath == 'material'){
			querySelectInfo('brand','brands');
			querySelectInfo('unit','units');
		}else if(routePath == 'promotionPayment'){
			querySelectInfo('clientPayment','clientPayments');
			querySelectInfo('promotion','promotions');
			if(promotionId){
				completeQueryById = true;
			}
		}else if(routePath == 'materialProperty'){
			querySelectInfo('material','materials',null,{'start':0,'limit':20});
			querySelectInfo('color','colors');
			querySelectInfo('size','sizes');
		}else if(routePath == 'promotionStore'){
			querySelectInfo('promotion','promotions');
			querySelectInfo('store','stores');
			if(promotionId){
				completeQueryById = true;
			}
		}else if(routePath == 'promotionOffer'){
			querySelectInfo('promotion','promotions');
			if(promotionId){
				completeQueryById = true;
			}
		}else if(routePath == 'promotionOfferMatchContent'){
			var body = {};
			body.id = promotionOfferId;
			BasicsService.queryById(body,'promotionOffer').then(function(data){
				if(data && data.matchType){
					if(data.matchType == 'MATCAT'){
						querySelectInfo('materialCategory','materialCategorys');
					}else if(data.matchType == 'BRAND'){
						querySelectInfo('brand','brands');
					}else if(data.matchType == 'MAT'){
						querySelectInfo('material','materials',null,{'start':0,'limit':20});
					}
					$('.MatchContent').hide();
					$('#'+data.matchType).show();
				}				
			})
		}else if(routePath == 'promotionConditionMatchContent'){
			var body = {};
			body.id = promotionConditionId;
			BasicsService.queryById(body,'promotionCondition').then(function(data){
				if(data && data.matchType){
					if(data.matchType == 'MATCAT'){
						querySelectInfo('materialCategory','materialCategorys');
					}else if(data.matchType == 'BRAND'){
						querySelectInfo('brand','brands');
					}else if(data.matchType == 'MAT'){
						querySelectInfo('material','materials',null,{'start':0,'limit':20});
					}
					$('.MatchContent').hide();
					$('#'+data.matchType).show();
				}				
			})
		}else if(routePath == 'shoppingGuide'){
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
				
				if($scope.outputRoles){
					var finalRole = "";
					for(var roleIndex =0 ; roleIndex < $scope.outputRoles.length; roleIndex++){
						var selRole = $scope.outputRoles[roleIndex];
						var selRoleValue = selRole.value;
						finalRole += selRoleValue + ',';
					}
					$scope.form.roleId = finalRole;
				}
				//获取用户所属门店信息.
				if($scope.selectedStores){
					var finalStore="";
					var stores=$scope.selectedStores;
					for(var i=0;i<stores.length;i++){
						var store=stores[i];
						finalStore+=(store.id)+',';
					}
					$scope.form.storeIds=finalStore;
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
				if($scope.editType == 'add'){
					if($scope.form.parent && $scope.form.parent.id){
						$scope.form.parentId = $scope.form.parent.id;
					}else{
						$scope.form.parentId = "0";
					}
				}else if($scope.editType == 'update'){
					if($scope.form.parent && $scope.form.parent.id){
						$scope.form.id = $scope.form.parent.id;
					}else{
						alert("请选择要修改的节点");
						return false;
					}
				}
					
				
			}
			
			if(routePath == 'terminal' || routePath == 'salesConfig' || routePath == 'retailPrice' || routePath == 'account' || routePath == 'shoppingGuide'){
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
				if(selMaterial && selMaterial.length >= 1){
					$scope.form.materialId = selMaterial[0].value;
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
				if(selMaterial && selMaterial.length >= 1){
					$scope.form.materialId = selMaterial[0].value;
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
			if(routePath == 'promotionStore'){
				var selPromotion = $scope.selPromotion;
				if(selPromotion){
					$scope.form.promotionId = selPromotion.value;
				}
				var selStore = $scope.selStore;
				if(selStore){
					$scope.form.storeId = selStore.value;
				}
			}
			if(routePath == 'promotionOffer'){
				var selPromotion = $scope.selPromotion;
				if(selPromotion){
					$scope.form.promotionId = selPromotion.value;
				}
				var selOfferType = $scope.selOfferType;
				if(selOfferType){
					$scope.form.offerTypeId = selOfferType.value;
				}
				var selMatchType = $scope.selMatchType;
				if(selMatchType){
					$scope.form.matchTypeId = selMatchType.value;
				}
			}
			if(routePath == 'promotionOfferMatchContent'){
				$scope.form.promotionOfferId = promotionOfferId;
				$('.MatchContent').each(function(){
					if($(this).css('display') != 'none'){
						var matchTypeId = $(this).attr('id');
						if(matchTypeId == 'MATCAT'){
							var selMaterialCategory = $scope.selMaterialCategory;
							if(selMaterialCategory){
								$scope.form.matchContent = selMaterialCategory.value;
							}
						}else if(matchTypeId == 'BRAND'){
							var selBrand = $scope.selBrand;
							if(selBrand){
								$scope.form.matchContent = selBrand.value;
							}
						}else if(matchTypeId == 'MAT'){
							var selMaterial = $scope.selMaterial;
							if(selMaterial && selMaterial.length >= 1){
								$scope.form.matchContent = selMaterial[0].value;
							}
						}
					}
				})
			}
			if(routePath == 'promotionCondition'){
				$scope.form.promotionOfferId = promotionOfferId;
				var selConditionType = $scope.selConditionType;
				if(selConditionType){
					$scope.form.conditionTypeId = selConditionType.value;
				}
				var selMatch = $scope.selMatch;
				if(selMatch){
					$scope.form.matchId = selMatch.value;
				}
				var selMatchType = $scope.selMatchType;
				if(selMatchType){
					$scope.form.matchTypeId = selMatchType.value;
				}
			}
			if(routePath == 'promotionConditionMatchContent'){
				$scope.form.promotionConditionId = promotionConditionId;
				$('.MatchContent').each(function(){
					if($(this).css('display') != 'none'){
						var matchTypeId = $(this).attr('id');
						if(matchTypeId == 'MATCAT'){
							var selMaterialCategory = $scope.selMaterialCategory;
							if(selMaterialCategory){
								$scope.form.matchContent = selMaterialCategory.value;
							}
						}else if(matchTypeId == 'BRAND'){
							var selBrand = $scope.selBrand;
							if(selBrand){
								$scope.form.matchContent = selBrand.value;
							}
						}else if(matchTypeId == 'MAT'){
							var selMaterial = $scope.selMaterial;
							if(selMaterial && selMaterial.length >= 1){
								$scope.form.matchContent = selMaterial[0].value;
							}
						}
					}
				})
			}
			
			if($scope.editType == 'add'){
				BasicsService.add($scope.form,routePath).then(function(data){
					if(data && data.error){
						alert(data.error);
						//$scope.info = data;
	                }else if(routePath == 'materialCategory'){
	                	$route.reload();
	                	$scope.addNedit=false;
	                }else{
	                	$location.path('/backend/list/'+routePath);
	                	$scope.addNedit=false;
	                }
	            });
			}else if($scope.editType == 'update'){
				BasicsService.update($scope.form,routePath).then(function(data){
					if(data && data.error){
						alert(data.error);
						//$scope.info = data;
	                }else if(routePath == 'materialCategory'){
	                	$route.reload();
	                	$scope.addNedit=false;
	                }else{
	                	$location.path('/backend/list/'+routePath);
	                	$scope.addNedit=false;
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
	
	var beforeKeyword = null;
	$scope.fSearchMaterialChange = function(data){
		if(data == '' || data.keyword != beforeKeyword){
			if(data == ''){
				beforeKeyword = '';
			}else{
				beforeKeyword = data.keyword;
			}
			querySelectInfo('material','materials',null,{'start':0,'limit':20,'likeOne':data.keyword});
		}
	}
	var initMaterialSearchFlag = false;
	$scope.initMaterialSearch = function(data){
		initMaterialSearchFlag = true;
		$("#multi_material").find(".inputFilter").bind('input propertychange', function() {
			if($(this).val()==''){
				beforeKeyword = '';
				querySelectInfo('material','materials',null,{'start':0,'limit':20,'likeOne':''});
			}
		})
	}
	
	
	$scope.materialLocalLang = {
		nothingSelected:"没有选择",
		reset:"重置",
		search:"搜索物料编码或者物料名称..."
	}
	
	$scope.cancel=function(){
		if(routePath == 'promotionOffer' || routePath == 'promotionStore' || routePath == 'promotionPayment'){
			$location.url('/backend/list/'+routePath+'?promotionId='+promotionId);
			return;
		}
		
		if(routePath == 'promotionOfferMatchContent' || routePath == 'promotionCondition'){
			$location.url('/backend/list/'+routePath+'?promotionOfferId='+promotionOfferId);
			return;
		}
		
		if(routePath == 'promotionConditionMatchContent'){
			$location.url('/backend/list/'+routePath+'?promotionConditionId='+promotionConditionId);
			return;
		}
		if(routePath == 'materialCategory'){
			$scope.addNedit=false;
			var  node=$('#materialCategoryTree').treeview('getSelected')[0];
			$scope.form.code = node.data.code;
			$scope.form.name = node.text.split(":")[1];
			$scope.form.parent = {};
			$scope.form.parent.id = node.id;
			
			return;
		}
		$location.path('/backend/list/'+routePath);
	}
	
	$scope.storeCheck=function(store){
		if(!store.chk){
			store.chk=1;
		}else{
			store.chk=0;
		}
	}
	$scope.selectLeft=function(){
		var readyStores=$scope.readyStores;
		var readyStoresTmp=[];
		var selectedStores=$scope.selectedStores || [];
		for(var i=0;i<readyStores.length;i++){
			var readyStore=readyStores[i];
			if(readyStore.chk){
				readyStore.chk=0;
				selectedStores.push(readyStore);
			}else{
				readyStoresTmp.push(readyStore);
			}
		}
		$scope.readyStores=readyStoresTmp;
		$scope.selectedStores=selectedStores;
		$scope.allcheck1=false;
		$scope.allcheck2=false;
	}
	
	$scope.selectRight=function(){
		var readyStores=$scope.readyStores || [];
		var selectedStoresTmp=[];
		var selectedStores=$scope.selectedStores || [];
		for(var i=0;i<selectedStores.length;i++){
			var selectedStore=selectedStores[i];
			if(selectedStore.chk){
				selectedStore.chk=0;
				readyStores.push(selectedStore);
			}else{
				selectedStoresTmp.push(selectedStore);
			}
		}
		$scope.readyStores=readyStores;
		$scope.selectedStores=selectedStoresTmp;
		$scope.allcheck1=false;
		$scope.allcheck2=false;
	}
	$scope.allcheck1=false;
	$scope.allcheck2=false;
	$scope.checkAll=function(arr,chk,type){
		
		$scope["allcheck"+type]=!chk;
		selectedId="";
		for(var i=0;i<arr.length;i++){
			var result=arr[i];
			if(!chk == true && chk!="undefined"){//选中
				result.chk=true;
			}else{
				result.chk=false;
			}
		}
		
	}
	
	$scope.uploadFile = function(type){
		$scope.uploadFileType=type;
		ngDialog.open({
            template: '/backend/view/template/uploadFile.html',
            scope: $scope,
            closeByEscape: false,
            width:'300px',
            controller: 'uploadFileCtrl',
        });
	}
	
	//导购员信息查询.
	$scope.openMaterialCategoryTree = function(){
		var type=$scope.uploadFileType;
		ngDialog.open({
            template: '/backend/view/template/materialCategoryTree.html',
            scope: $scope,
            closeByEscape: false,
            controller: 'materialCategoryTreeCtrl',
            
        });
	}
	
	//增加品类信息
	$scope.addMc = function(){
		
		$("#Code").val("");
		$("#Name").val("");
		$("#Code").attr("readonly",false);
		$("#Name").attr("readonly",false);
		$scope.editType = 'add';
		$scope.addNedit=true;
	}
	
	$scope.delMc = function(){
		if($scope.form.parent && $scope.form.parent.id){
			if(confirm("确定执行删除?")){
				var body = {};
				body.id = $scope.form.parent.id;
				BasicsService.del(body,routePath).then(function(data){
					if(data && data.delerror){
						alert("删除数据出错:"+data.delerror);
		            }else{
		            	alert("删除数据成功");
		            	$route.reload();
		            }
		        });
			}
		}else{
			alert("请选择需要删除的节点!");
			return false;
		}
	}
	
	$scope.editMc = function(){
		
		if($scope.form.parent && $scope.form.parent.id){
			$("#Code").attr("readonly",false);
			$("#Name").attr("readonly",false);
			$scope.editType = 'update';
			$scope.addNedit=true;
		}else{
			alert("请选择需要修改的节点!");
			return false;
		}
	}
	
	
	// 编辑物料信息
	var lastSalesConversion = null;
	$scope.fillSalesConversion = function(){
		if($scope.editType == 'update' && (lastSalesConversion == null || typeof lastSalesConversion == 'undefined')
				&& $scope.form.salesConversion != null && typeof $scope.form.salesConversion != 'undefined'){
			lastSalesConversion = $scope.form.salesConversion;
		}else if($scope.editType == 'update' && lastSalesConversion != null && typeof lastSalesConversion != 'undefined'){
			$scope.form.salesConversion = lastSalesConversion;
		}else if($scope.editType == 'add'){
			$scope.form.salesConversion = "";
		}
		$("#SalesConversion").attr("readonly",false);
		var selBasicUnit = $scope.selBasicUnit;
		var selSalesUnit = $scope.selSalesUnit;
		if(selBasicUnit && selSalesUnit){
			var basicUnitVal = selBasicUnit.value;
			var salesUnitVal = selSalesUnit.value;
			if(basicUnitVal != null && typeof basicUnitVal != 'undefined' && salesUnitVal != null && typeof salesUnitVal != 'undefined'){
				var pageBody = {};
				pageBody.unitAId = basicUnitVal;
				pageBody.unitBId = salesUnitVal;
				pageBody.unitCanChange = "1";
				BasicsService.queryByList(pageBody,"unitConversion").then(function(data){
		    		if(data && data.length > 0){
		    			if(data.length == 1){
		    				var info = data[0];
		    				if(info.unitA && info.unitB && info.qtyA != null && typeof info.qtyA != 'undefined' && info.qtyB != null && typeof info.qtyB != 'undefined'){
		    					var unitAId = info.unitA.id;
			    				var qtyA = info.qtyA;
			    				var unitBId = info.unitB.id;
			    				var qtyB = info.qtyB;
			    				if(unitAId == basicUnitVal && unitBId == salesUnitVal){
			    					$scope.form.salesConversion = (qtyA/qtyB).toFixed(2);
			    					$("#SalesConversion").attr("readonly",true);
			    				}else if(unitBId == basicUnitVal && unitAId == salesUnitVal){
			    					$scope.form.salesConversion = (qtyB/qtyA).toFixed(2);
			    					$("#SalesConversion").attr("readonly",true);
			    				}
		    				}
		    			}else if(data.length > 1){
		    				alert("单位换算有多条,请检查!");
		    			}
		    		}
		        });
			}	
		}
	}
	
	/* 检测字符串是否为空 */
	function isnull( str ) {
		var i=0;
		var sch="";
		for ( i = str.length-1; i >= 0; i--){
			sch=str.charAt(i);
			if ( sch!=" " && sch!="\t" && sch!="\r" && sch!="\n") return false;
		}
		return true;
	}
	
	/*校验是否是整数 */
	function isInteger(str) { 
		if(isnull(str)){return false;}
		return /^([+-]?)(\d+)$/.test(str); 
	}
	
	function parseRange(range){
		var obj = null;
		if(range && range.length >= 3 && (range.substr(0,1) == '[' || range.substr(0,1) == '(') && (range.substr(range.length-1) == ']' || range.substr(range.length-1) == ')')){
			var orRange = range;
			obj = {};
			var minStr = null;
			var maxStr = null;
			range = range.substr(1,range.length-2);
			if(range.indexOf(',') >= 0){
				var rangeArr = range.split(',');
				minStr = $.trim(rangeArr[0]);
				maxStr = $.trim(rangeArr[1]);
				if(!isNaN(minStr)){
					obj.min = $.trim(minStr);
				}
				if(!isNaN(maxStr)){
					obj.max = $.trim(maxStr);
				}
			}else if(!isNaN(range)){
				obj.min = $.trim(range);
			}
		}
		if(obj && obj.min != null && typeof obj.min != 'undefined'){
			if(orRange.substr(0,1) == '['){
				obj.minEqual = true;
			}
		}
		if(obj && obj.max != null && typeof obj.max != 'undefined'){
			if(orRange.substr(orRange.length-1) == ']'){
				obj.maxEqual = true;
			}
		}
		return obj;
	}
	
	$scope.validNumber = function(select,range,mustInt){
		var obj = parseRange(range);
		if(obj){
			var min = obj.min;
			var max = obj.max
			$this = $(select);
			var id = $this.attr("id") + "_valid";
			var $warn = $("#"+id);
			var warnLen = $warn.length;
			var content = $this.val() + '';
			if((mustInt && !isInteger(content)) || (isNaN(content)) || 
					(min != null && typeof min != 'undefined' && min != '' && ((obj.minEqual && Number(content) < Number(min)) || (!obj.minEqual && Number(content) <= Number(min)))) 
				|| (max != null && typeof max != 'undefined' && max != '' && ((obj.maxEqual && Number(content) > Number(max)) || (!obj.maxEqual && Number(content) >= Number(max))))){
				if(warnLen == 0){
					$this.parent('div').after("<label id='"+id+"' style='color:red'>请输入正确的数字</label>");
				}
			}else if(warnLen > 0){
				$warn.remove();
			}
		}
	}
}]);

app.controller("uploadFileCtrl",['$scope','$location','LoginService','ngDialog','BasicsService',function($scope,$location,LoginService,ngDialog,BasicsService){
	//执行图片上传操作
	$scope.submit=function(){
		var token=$.cookie("token");
		var type=$scope.uploadFileType;
		var maxSize=$scope.uploadFileMaxSize || 10;
		var url="/rest/file/uploadImg?token="+token+"&type="+type+"&maxSize="+maxSize;
		$.ajaxFileUpload({
			 url:url,
			 secureuri:false,
			 formId:"uploadForm",
			 fileElementId:["fileUpload"],
			 dataType: 'json',
			 success: function (data, status) {
				 $('#uploadFile').remove();
				 var reg = /<pre.*?>(.+)<\/pre>/g;  
				 var result = data.match(reg);  
				 data = RegExp.$1;
				 data=$.parseJSON(data);
				 if(data.status == Status.SUCCESS){
					 //文件上传成功
					 alert("文件上传成功");
					 
					 var form=$scope.form;
					 
					 form.displayPhoto=decodeURIComponent(data.data);
					 $scope.form=form;
					 $scope.$apply();//需要手动刷新
					 ngDialog.close();
				 }else{
					 alert(data.msg);
				 }
			 },
			 error: function (data, status, e) {
				 alert(e);
			 }		
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

