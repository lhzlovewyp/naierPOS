app.controller("loginCtrl",['$scope','$location','LoginService','ngDialog',function($scope,$location,LoginService,ngDialog){
	var form = {};
    $scope.form = form;
	$scope.login = function(isValid){
		if(isValid) {
            LoginService.login($scope.form).then(function(data){
                if(data.hasLogin){
                	var stores=data.stores;
                	if(stores.length>1){
                		//弹窗提示选择门店.
                		$scope.stores=stores;
                		ngDialog.open({
                            template: 'chooseStore',
                            scope: $scope,
                            closeByDocument: false,
                            width:400,
                            controller: 'loginCtrl',
                            showClose:false
                        });
                	}else{
                		//如果是初次登陆,强制修改密码
                    	if(data.changePWD == "1"){
                    		location.href="changePWD.html";
                    	}else{
                    		location.href="home.html";
                    	}
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
	
	$scope.choose=function(store){
		LoginService.chooseStore(store).then(function(data){
			 if(data.hasLogin){
				//如果是初次登陆,强制修改密码
		        	if(data.changePWD == "1"){
		        		location.href="changePWD.html";
		        	}else{
		        		location.href="home.html";
		        	}
			 }else{
				 alert('登陆失效,请重新登陆.');
				 location.href="login.html";
			 }
    		
        });
	}
	
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
	
	$scope.changePassword=function(){
		location.href="changePWD.html";
	}
	
	$scope.logout = function(){
		LoginService.logOff();
	}
}]);

app.controller("routeMainCtl",['$scope','$location','HomeService',function($scope,$location,HomeService){
	HomeService.getHomeData().then(function(data){
		$scope.info=data;
	});
	
	$scope.dayReport = function(){
		var saleDate=$scope.info.saleDate;
		 		var strDate1=new Date(saleDate).Format("yyyy-MM-dd");
		 		var strDate2=new Date(saleDate+24*60*60*1000).Format("yyyy-MM-dd");
		 		var data="日结后营业日期将从"+strDate1+" 变更为"+strDate2+"，请确认是否要继续？";
		 		if(confirm(data)){
		 			HomeService.dayReport().then(function(data){
		 				if(data.status == Status.SUCCESS){
		 					location.reload();
		 				}else{
		 					alert('日结失败，请稍后重试.');
		 				}
		 			});
		 		};
	}
}]);

app.controller("routeSaleCtl",['$scope','$location','SaleService','ngDialog','PayService',function($scope,$location,SaleService,ngDialog,PayService){
	
	//表格渲染完成后执行，主要用于编辑商品数量.
	$scope.$on('ngRepeatFinished', function (ngRepeatFinishedEvent) {
        //下面是在table render完成后执行的js
		$("#tableSale").editableTableWidget({selector:'.editable'});
		$('.editable').on('change', function(evt, newValue) {
			//如果不是数字，不允许改变;
			if(isNaN(newValue)){
				newValue=1;
				$(this).html(newValue);
			}
			var index=$(this).attr("index");
			var saleInfo=$scope.info.saleInfos[index];
			saleInfo.count=newValue;
			reloadSaleInfos($scope.info.saleInfos);
			$scope.$apply();
		})

	});
	//初始化销售订单.
	SaleService.initSalesOrder().then(function(data){
		$scope.info=data;
		$scope.info.saleInfos=[];
		
		//监听变化，计算总数.
		$scope.$watch("info.saleInfos", function(newValue, oldValue){
			var saleInfos=$scope.info.saleInfos;
			var count=0;
			var totalPrice=0;
			var discPrice=0;
			if(saleInfos){
				for(var i=0;i<saleInfos.length;i++){
					var saleInfo=saleInfos[i];
					if(!saleInfo) continue;
					if(!saleInfo.discType || saleInfo.discType == '4'){
						count+=Number(saleInfo.count);
						totalPrice+=Number((saleInfo.count*saleInfo.retailPrice).toFixed(2));
					}else{
						discPrice+=Number(saleInfo.totalPrice);
					}
				}
			}
			$scope.info.totalPrice=totalPrice.toFixed(2);
			$scope.info.totalNum=count;
			$scope.info.totalDiscPrice=discPrice.toFixed(2);
			$scope.info.pay=(totalPrice-Math.abs(discPrice)).toFixed(2);
			$scope.info.payed='0.00';
			$scope.info.needPay=($scope.info.pay-Math.abs($scope.info.payed)).toFixed(2);
		},true);
	});
	
	
	//取消交易
	$scope.cancelSale=function(){
		SaleService.initSalesOrder().then(function(data){
			$scope.info=data;
			$scope.promotions=null;
			$scope.payments=null;
		});
	}
	//查找会员.
	$scope.memberSearch = function(){
		 ngDialog.open({
             template: '/front/view/template/memberSearch.html',
             scope: $scope,
             closeByEscape: false,
             controller: 'memberCtrl'
         });
	}
	//导购员信息查询.
	$scope.shoppingGuideSearch = function(){
		
		ngDialog.open({
            template: '/front/view/template/shoppingGuideSearch.html',
            scope: $scope,
            closeByEscape: false,
            controller: 'shoppingGuideCtrl'
        });
	}
	
	//查找商品.
	$scope.searchMat = function(){
		if(!$scope.matForm){
			return;
		}
		SaleService.searchMat($scope.matForm).then(function(data){
			if(data.data.property=="1" && !data.data.color && !data.data.size){//需要维护属性.
            	
            	$scope.tempMat=data.data;
            	$scope.matForm={};
            	ngDialog.open({
                    template: '/front/view/template/matAttr.html',
                    scope: $scope,
                    closeByEscape: false,
                    width:600,
                    controller: 'matAttrCtrl'
                });
            }else{
            	//把当前data值填充到对应对象中.
            	var saleInfos=$scope.info.saleInfos || [];
            	$scope.info.saleInfos=saleInfos;
            	mat=data.data
        		mat.count=1;
            	mat.totalPrice=mat.count*mat.retailPrice;
            	if(!mat.sort){
            		mat.sort=saleInfos.length;
            	}
        		saleInfos.push(mat);
        		$scope.matForm={};
            }
        });
	}
	$scope.keydown = function(event){
		var code=event.keyCode;
		if(code == '13'){
			$scope.searchMat();
		}
	}
	//删除商品.
	$scope.deleteSaleInfo=function(saleInfo){
		$scope.info.saleInfos.splice($scope.info.saleInfos.indexOf(saleInfo), 1);
		reloadSaleInfos($scope.info.saleInfos);
	}
	
	//修改商品属性.
	$scope.updateAttr=function(saleInfo){
		//只有存在属性值的时候,才允许修改属性.
		if(saleInfo.property=="1"){
			saleInfo.ope="update";
			$scope.tempMat=saleInfo;
			ngDialog.open({
                template: '/front/view/template/matAttr.html',
                scope: $scope,
                closeByEscape: false,
                controller: 'matAttrCtrl'
            });
			
		}
	}
	
	//单项打折
	$scope.itemDISC=function(saleInfo){
		if($scope.info.itemDISC!="1"){
			return;
		}
		saleInfo.disc="1";//1-单项打折,2-整单打折.
		$scope.info.discType ="1";
		$scope.tempMat=saleInfo;
		ngDialog.open({
            template: '/front/view/template/disc.html',
            scope: $scope,
            closeByEscape: false,
            controller: 'discCtrl'
        });
	}
	//整单打折.
	$scope.allDISC=function(){
		if($scope.info.allDISC!="1"){
			return;
		}
		//如果没有选择商品，不允许整单打折.
		if(!$scope.info.saleInfos || $scope.info.saleInfos.length<=0){
			return;
		}
		$scope.info.discType="2";//1-单项打折,2-整单打折.
		ngDialog.open({
            template: '/front/view/template/disc.html',
            scope: $scope,
            closeByEscape: false,
            controller: 'discCtrl'
        });
	}
	
	//结算
	$scope.checkout = function(){
		SaleService.autoJoinPromotions($scope.info).then(function(obj){
			 if(obj.data.status==Status.SUCCESS){
				 
                   var dto=obj.data.data;
                   $scope.info.saleInfos=dto.saleInfos;
                   gotoPay(ngDialog,$scope,PayService);
            }else{
            	alert(obj.data.msg);
            }
	   });
//		SaleService.getPromotions($scope.info).then(function(data){
//			var effPromotions=data.data.effPromotions;
//			//如果没有可以参加的促销活动.
//			if(!effPromotions){
//				 $scope.value = true;
//				 if($scope.payments){
//					 ngDialog.open({
//				            template: '/front/view/template/pay.html',
//				            scope: $scope,
//				            closeByDocument: false,
//				            width:700,
//				            controller: 'payCtrl',
//				            showClose:false
//				        });
//				 }else{
//					 PayService.initPay().then(function(data){
//							$scope.payments=data.data;
//							//打开付款浮层.
//							ngDialog.open({
//					            template: '/front/view/template/pay.html',
//					            scope: $scope,
//					            closeByDocument: false,
//					            width:700,
//					            controller: 'payCtrl',
//					            showClose:false
//					        });
//					 });
//				 }
//				 
//				
//			}else{
//				$scope.effPromotions=effPromotions;
//				ngDialog.open({
//		            template: '/front/view/template/promotion.html',
//		            scope: $scope,
//		            closeByDocument: false,
//		            width:700,
//		            controller: 'promotionCtrl',
//		            preCloseCallback: function () {
//		            	var saleInfos = clearPromotionSaleInfos($scope.info.saleInfos);
//		            	$scope.info.saleInfos=saleInfos;
//	                }
//		        });
//			}
//		});
	}
	
	
}]);
app.controller("memberCtrl",['$scope','$location','MemberService','ngDialog','BasicsService',function($scope,$location,MemberService,ngDialog,BasicsService){
	var allSex = [
		        	    {value : "1", show : "男"},
		        	    {value : "2", show : "女"}
		        	];
	
	var allMarriage = [
		        	    {value : "1", show : "已婚"},
		        	    {value : "2", show : "未婚"},
		        	    {value : "0", show : "保密"}
		        	];
	$scope.sexs = allSex;
	$scope.marriages = allMarriage;
	
	$scope.search = function(){
		var form=$scope.memberForm;
		if(!form){
			return;
		}
		MemberService.getSinglerMember(form).then(function(obj){
			var dto=obj.data.data;
			if(obj.data.status==Status.SUCCESS){
                $scope.member=dto;
            }else{
            	$scope.member=null;
            }
		});
	};
	
	$scope.choose=function(member){
		ngDialog.close();
		var info=$scope.info || {};
		info.member=member;
	}
	
	$scope.showNewMember=function(member){
		ngDialog.open({
	        template: '/front/view/template/newMember.html',
	        scope: $scope,
	        closeByDocument: false,
	        width:'40%',
	        controller: 'memberCtrl'
	    });
	}	
	
	var manualClick = false;
	var initItemsPerPage = 10;
	var initCurrentPage = 1;
	function goPage(pageNo,selectForm){
		var body = {};
		body.pageNo = pageNo;
		body.limit = $scope.paginationConf.itemsPerPage;
		if(selectForm){
			body=$.extend(body,selectForm);
		}
		
		BasicsService.queryByPage(body,'member').then(function(data){
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
	
	$scope.searchMore = function(){
		manualClick = true;
		goPage(1,$scope.selectForm);
	};
	
	$scope.close=function(){
		ngDialog.close();
	};
	
	$scope.edit = function(isValid){
		if(isValid) {
			var selSex = $scope.selSex;
			if(selSex){
				$scope.form.customer_sex = selSex.value;
			}
			var selMarriage = $scope.selMarriage;
			if(selMarriage){
				$scope.form.marriage = selMarriage.value;
			}
			if(!$scope.form){
				$scope.form = {};
			}
			BasicsService.add($scope.form,"member").then(function(data){
				if(data && data.error){
					alert(data.error);
					//$scope.info = data;
                }else{
                	ngDialog.close();
                }
            });
		}
	}
	
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
	
	$scope.choose = function(guide){
		ngDialog.close();
		var info=$scope.info || {};
		info.shoppingGuide=guide;
	}
	
}]);

app.controller("matAttrCtrl",['$scope','$location','SaleService','ngDialog',function($scope,$location,SaleService,ngDialog){
	$scope.array=[];
	
	var tempMat=$scope.tempMat;
	$scope.tempAttrDtos=tempMat.attrDtos;
	
	//初始化颜色和尺寸.
	if(tempMat.ope=="update"){
		var attrs=tempMat.attrDtos;
		for(var i=0;i<attrs.length;i++){
			var attr=attrs[i];
			if(attr.color.selected==1){
				$scope.tempSizes=attr.sizes;
			}
		}
	}
	
	$scope.close=function(){
		//初始化颜色和尺寸.
		if(tempMat.ope=="update"){
			if(!tempMat.size || !tempMat.color){
				alert("请选择颜色和尺寸");
				return;
			}
		}
		ngDialog.close();
	};
	
	$scope.selectColor = function(_this){
		tempMat.color=null;
		//取消所有的颜色选择.
		for(var i=0;i<tempMat.attrDtos.length;i++){
			if(tempMat.attrDtos[i].color.id==_this.color.id){
				continue;
			}
			tempMat.attrDtos[i].color.selected=0;
			
			$scope.tempSizes=[];
		}
		if(_this.color.selected){
			_this.color.selected=0;
			tempMat.color=null;
			$scope.tempSizes=[];
		}else{
			_this.color.selected=1;
			tempMat.color=_this.color;
		}
		//显示尺寸.
		for(var i=0;i<tempMat.attrDtos.length;i++){
			if(tempMat.attrDtos[i].color.id==_this.color.id){
				$scope.tempSizes=tempMat.attrDtos[i].sizes;
				break;
			}
		}
	}
	
	$scope.selectSize = function(_this){
		var sizes=$scope.tempSizes;
		tempMat.size=null;
		//取消所有size的选择
		for(var i=0;i<sizes.length;i++){
			if(sizes[i].id==_this.id){
				continue;
			}
			sizes[i].selected=0;
		}
		if(_this.selected){
			tempMat.size=null;
			_this.selected=0;
		}else{
			_this.selected=1;
			tempMat.size=_this;
		}
	}
	
	$scope.submit = function(){
		
		if(!tempMat.size || !tempMat.color){
			alert("请选择颜色和尺寸");
			return;
		}
		
		var info = $scope.info || {};
		var saleInfos=info.saleInfos || [];
		info.saleInfos=saleInfos;
		$scope.tempMat.count=1;
		if(!$scope.tempMat.sort){
			$scope.tempMat.sort=saleInfos.length;
		}
		//$scope.tempMat.totalPrice=1*$scope.tempMat.retailPrice;
		if(saleInfos.indexOf($scope.tempMat)<0){
			saleInfos.push($scope.tempMat);
		}
		ngDialog.close();
	}
}]);

app.controller("discCtrl",['$scope','$location','SaleService','ngDialog',function($scope,$location,SaleService,ngDialog){
	var tempMat=$scope.tempMat;
	$scope.discForm={};
	$scope.discForm.selected="1";
	$scope.submit=function(){
		var condition=$scope.discForm;
		if(!condition){
			return;
		}
		var type=condition.selected;
		var inputDisc=condition.disc;
		if(!type || !inputDisc || isNaN(inputDisc)){
			return;
		}
		//整单打折
		if($scope.info.discType == "2"){
			var saleInfos=$scope.info.saleInfos;
			var code="整单打折";
			var disc="";
			var totalPrice=0;
			//循环遍历出整个商品
			for(var i=0;i<saleInfos.length;i++){
				var saleInfo=saleInfos[i];
				if(!saleInfo) continue;
				if(!saleInfo.discType){//discType不存在,说明是普通商品.
					totalPrice+=Number(Number(saleInfo.count*saleInfo.retailPrice).toFixed(2));//保留2位小数.
					if(saleInfo.discount){
						totalPrice+=Number(Number(saleInfo.discount).toFixed(2));//增加该商品的单项折扣.
					}
					if(saleInfo.allDiscount){
						totalPrice+=Number(Number(saleInfo.allDiscount).toFixed(2));//增加该商品的单项折扣.
					}
					
				}else{
					if(saleInfo.discType=="2"){//如果存在整单折扣，删除掉 。
						saleInfos.remove(saleInfo);
					}
				}
			}
			if(type=="1"){//折扣%
				if(disc>100 || disc<0){
					return ;
				}
				disc=0-(totalPrice*(100-inputDisc)/100).toFixed(2);
			}else if(type=="2"){//折让
				if(disc<0 || disc > totalPrice){
					return;
				}
				disc=0-inputDisc;
			}
			
			//计算折扣在每件商品中的折扣.
			for(var i=0;i<saleInfos.length;i++){
				var saleInfo=saleInfos[i];
				if(!saleInfo) continue;
				if(!saleInfo.discType){//discType不存在,说明是普通商品.
					var saleInfoPrice=Number(Number(saleInfo.count*saleInfo.retailPrice).toFixed(2));//保留2位小数.
					if(saleInfo.discount){
						saleInfoPrice+=Number(Number(saleInfo.discount).toFixed(2));//增加该商品的单项折扣.
					}
					if(saleInfo.allDiscount){
						saleInfoPrice+=Number(Number(saleInfo.allDiscount).toFixed(2));//增加该商品的单项折扣.
					}
					var saleInfoDisc=(disc*saleInfoPrice/totalPrice).toFixed(2);
					saleInfo.allDiscount=saleInfoDisc;
				}
			}
			
			var saleInfo={};
			saleInfo.code=code;
			saleInfo.totalPrice=disc;
			saleInfo.discType="2";//整单折扣.
			if(!saleInfo.sort){
				saleInfo.sort=$scope.info.saleInfos.length;
			}
			//保存折扣信息，方便后续二次计算.
			saleInfo.dict={
					type:type,
					disc:inputDisc
			};
			$scope.info.saleInfos.push(saleInfo);
			
		}else{//单项打折
			var code="单项折扣";
			var disc="";
			var totalPrice=(tempMat.count*tempMat.retailPrice).toFixed(2);//保留2位小数.
			if(type=="1"){//折扣%
				if(inputDisc>100 || inputDisc<0){
					return ;
				}
				disc=0-(totalPrice*(100-inputDisc)/100).toFixed(2);
			}else if(type=="2"){//折让
				if(inputDisc<0 || (inputDisc - totalPrice>0)){
					return;
				}
				disc=0-inputDisc;
			}
			var saleInfo={};
			saleInfo.code=code;
			saleInfo.totalPrice=disc;
			saleInfo.discType="1";//单项打折
			if(!saleInfo.sort){
				saleInfo.sort=$scope.info.saleInfos.length;
			}
			tempMat.discount=disc;
			//保存折扣信息，方便后续二次计算.
			saleInfo.dict={
					type:type,
					saleInfo:tempMat,
					disc:inputDisc
			};
			//计算此商品是否存在单项折扣信息,如果存在，则删除单项折扣数据.
			var tempMatIndex = $scope.info.saleInfos.indexOf(tempMat)+1;
			var saleInfos=$scope.info.saleInfos;
			var flag=0;
			if(saleInfos.length>tempMatIndex){
				var nextData=saleInfos[tempMatIndex];
				if(nextData && nextData.discType && nextData.discType == "1"){
					flag=1;
				}
			}
			if(flag==1){
					saleInfos[tempMatIndex]=saleInfo;
			}else{
					$scope.info.saleInfos.insert(tempMatIndex,saleInfo);
			}
			//$scope.info.saleInfos.push(saleInfo);
			
		}
		ngDialog.close();
		
	}
	$scope.close=function(){
		ngDialog.close();
	}
}]);
app.controller("payingCtrl",['$scope','$location','PayService','SaleService','MemberService','ngDialog','$timeout',function($scope,$location,PayService,SaleService,MemberService,ngDialog,$timeout){

	$scope.searchMember=function(){
		var value=$("#memberCode").val();
		if(!value){
			alert("请输入会员号");
			return;
		}
		var form={memNo:value};
		MemberService.getSinglerMember(form).then(function(obj){
			var dto=obj.data.data;
			if(obj.data.status==Status.SUCCESS && dto){
                $scope.info.member=dto;
            }else{
            	$scope.info.member=null;
            	alert('找不到会员.');
            	return;
            }
		});
		
	};
	
	//现金和银行卡支付.
	$scope.payCash=function(payment){
		var form=$scope.cashForm;
		if(!form || isNaN(form.amount)){
			alert("请输入金额.");
			return ;
		}
		//如果金额超过需要支付的金额，进行提示.
		var amount=form.amount;
		var currentPayment=payment.amount || 0;
		if((amount-0)> ($scope.info.needPay+currentPayment)){
			alert("超出需要支付的金额.");
			return;
		}
		payment.amount=amount;
		$scope.loadPayInfo();
		$scope.closeThisDialog();
	};
	
	$scope.payAlipay=function(payment){
		var form=$scope.alipayForm;
		if(!form || !form.account){//如果没有输入账号，提示错误.
			alert("请输入用户账号.");
			return ;
		}
		var amount=$scope.info.needPay;
		if(amount == 0){
			alert("支付金额不能为0");
			return ;
		}
		var barcode=form.account;
		var paymentCode=payment.payment.code;
		var channel='';
		var code='';
		if(paymentCode == Status.ALIPAY){//支付宝支付.
			channel=1;
			code="01";
		}else{//微信支付.
			channel=2;
			code="04";
		}
		aliPay(PayService,$scope,channel,code,payment,barcode,amount);
	};
	
	$scope.prepaidPay=function(payment){
		var form=$scope.prepaidForm;
		if(!form || !form.amount){//如果没有输入账号，提示错误.
			alert("请输入金额.");
			return ;
		}
		var amount=form.amount;
		if(isNaN(amount)){
			alert("请输入数字");
			return;
		}
		var member=$scope.info.member;
		if(!member || member.memberBalance<amount){
			alert("余额不足");
			return;
		}
		var currentPayment=payment.amount || 0
		if((amount-0)> ($scope.info.needPay+currentPayment)){
			alert("超出需要支付的金额.");
			return;
		}
		var payInfo={};
		var id = $scope.info.id;
		var salesDate=$scope.info.saleDate;
		payInfo.code=member.memberCode;
		payInfo.salesDtoId=id;
		payInfo.salesDate=salesDate;
		payInfo.amount=amount;
		payInfo.type="3";
		
		PayService.prepaidPay(payInfo).then(function(obj){
			var dto=obj.data.data;
			if(obj.data.status==Status.SUCCESS){
				alert("付款成功.");
				payment.amount=amount;
				$scope.loadPayInfo();
				$scope.closeThisDialog();
	        }else{
	        	alert(obj.data.msg);
	        }
		});
		
	}
	
	$scope.payPoint=function(payment){
		var form=$scope.pointForm;
		var member = $scope.info.member;
		if(!member){
			alert('请输入会员信息');
		}
		if(!form || !form.point){//如果没有输入账号，提示错误.
			alert("请输入积分.");
			return ;
		} 
		var points = member.memberPointPayConfig.points;//可用积分
		if(points<form.point){
			alert('输入积分超过上限');
			return;
		}
		//计算输入积分换算金额.
		var amount = (form.point || 0)/member.memberPointPayConfig.pointPay * member.memberPointPayConfig.pointPayAMT
		
		var currentPayment=payment.amount || 0
		if((amount-0)> ($scope.info.needPay+currentPayment)){
			alert("超出需要支付的金额.");
			return;
		}

		var payInfo={};
		var id = $scope.info.id;
		var salesDate=$scope.info.saleDate;
		payInfo.code=member.memberCode;
		payInfo.salesDtoId=id;
		payInfo.salesDate=salesDate;
		payInfo.points=form.point;
		
		PayService.pointPay(payInfo).then(function(obj){
			var dto=obj.data.data;
			if(obj.data.status==Status.SUCCESS){
				alert("付款成功.");
				payment.amount=amount;
				payment.points=form.point;
				$scope.loadPayInfo();
				$scope.closeThisDialog();
	        }else{
	        	alert(obj.data.msg);
	        }
		});
		
	}
	
	$scope.couponPay=function(payment){
		var form=$scope.couponForm;
		
		if(!form || !form.code){//如果没有输入账号，提示错误.
			alert("请输入电子券号.");
			return ;
		} 
		
		var currentPayment=payment.amount || 0;
		if(($scope.info.needPay+currentPayment)<=0){
			alert("超出需要支付的金额.");
			return;
		}

		var payInfo={};
		
		payInfo.code=form.code;
		
		PayService.couponPay(payInfo).then(function(obj){
			var dto=obj.data.data;
			if(obj.data.status==Status.SUCCESS){
				alert("支付成功.");
				payment.amount=$scope.couponInfo.money;
				$scope.loadPayInfo();
				$scope.closeThisDialog();
	        }else{
	        	alert(obj.data.msg);
	        }
		});
		
	}
	
	$scope.pointChange = function(){
		var form=$scope.pointForm;
		var input=form.point;
		if(isNaN(input)){
			alert("请输入数字");
			return;
		}
		var minPoint = $scope.info.member.memberPointPayConfig.pointPay;
		if(input%minPoint!=0){
			alert('输入积分只能是'+minPoint+'的整数倍');
			form.point=0;
			return;
		}
	}
	
	$scope.queryCoupon = function(){
		var form=$scope.couponForm;
		if(!form || !form.code){
			return;
		}
		PayService.queryCoupon(form).then(function(obj){
			if(obj.data.status==Status.SUCCESS){					 
                var dto=obj.data.data.data;
                var isUser=dto.detail.is_use;
                if(isUser=="1"){
                	alert('券已被使用');
                	return;
                }
                dto.coupon.money=parseFloat(dto.coupon.money).toFixed(2);
                $scope.couponInfo=dto.coupon;
			}else{
				alert(obj.data.msg);
			}
		});
	}
	$scope.keydown1=function(event){
		var code=event.keyCode;
		if(code == '13'){
			$scope.queryCoupon();
		}
	}
	
	
}]);	
app.controller("payCtrl",['$scope','$location','printService','PayService','SaleService','ngDialog','$timeout',function($scope,$location,printService,PayService,SaleService,ngDialog,$timeout){
	
	$scope.$watch("payments", function(newValue, oldValue){
		loadPayInfo();
	},true);
	
	//取消支付,如果涉及到接口的,则需要调用退款逻辑.
	$scope.close=function(){
		var payments=$scope.payments;
		for(var i=0;i<payments.length;i++){
			var payment=payments[i];
			var amount=payment.amount;
			var transNo = payment.transNo;
			var barcode=payment.barcode;
			if(!amount || amount==0){
				continue;
			}else{
				var paymentCode=payment.payment.code;
				switch(paymentCode)
				{
				case Status.CASH://现金支付
				case Status.UNIONPAY_OFF://银行卡支付
					payment.amount=0;
				  	break;
				case Status.BS_PREPAID://百胜储值卡
					//payPayment="payPrepaid";
					var payInfo={};
					payInfo.code=$scope.info.member.memberCode;
					payInfo.salesDtoId=$scope.info.id;
					payInfo.amount=payment.amount;
					payInfo.type="2";
					
					PayService.prepaidPay(payInfo).then(function(obj){
						var dto=obj.data.data;
						if(obj.data.status==Status.SUCCESS){
							payment.amount=0;
							$scope.loadPayInfo();
							//ngDialog.close();
				        }else{
				        	alert(obj.data.msg);
				        }
					});
					break;
				case Status.BS_COUPON://百胜电子券
					//payPayment="payCoupon";
					  break;
				case Status.BS_POINT://百胜会员积分.
					//payPayment="payPoint";
					var payInfo={};
					payInfo.code=$scope.info.member.memberCode;
					payInfo.salesDtoId=$scope.info.id;
					payInfo.points=(0-payment.points)+"";
					
					PayService.pointPay(payInfo).then(function(obj){
						var dto=obj.data.data;
						if(obj.data.status==Status.SUCCESS){
							payment.amount=0;
							payment.points=null;
							$scope.loadPayInfo();
							//ngDialog.close();
				        }else{
				        	alert(obj.data.msg);
				        }
					});
					  break;
				case Status.ALIPAY://支付宝退款.
					var channel=1,code="02";
					aliPay(PayService,$scope,channel,code,payment,barcode,amount,transNo);
					break;
				case Status.WXPAY://微信支付.
					var channel=2,code="05";
					aliPay(PayService,$scope,channel,code,payment,barcode,amount,transNo);
					break;
				default:
				  break;
				}	
			}
		}
		loadPayInfo();
		ngDialog.close();
		var saleInfos = clearPromotionSaleInfos($scope.info.saleInfos);
    	$scope.info.saleInfos=saleInfos;
		//销售单初始化
//		$timeout(function(){
//			$("#cancelSale").trigger("click");
//		});
	}
	
	$scope.pay=function(payment){
		openPay(payment);
	}
	//打开支付.
	function openPay(payment){
		var paymentCode=payment.payment.code;
		var payTemplate="";
		$scope.currPayment=payment;
		switch(paymentCode)
		{
		case Status.CASH://现金支付
		case Status.UNIONPAY_OFF://银行卡支付
			payPayment="payCash";
			$scope.cashForm=$scope.cashForm || {};
			$scope.cashForm.amount = $scope.info.needPay || 0;
		  	break;
		case Status.BS_PREPAID://百胜储值卡
			payPayment="payPrepaid";	
			$scope.prepaidForm=$scope.prepaidForm || {};
			$scope.prepaidForm.amount = $scope.info.needPay || 0;
			  break;
		case Status.BS_COUPON://百胜电子券
			payPayment="payCoupon";
			  break;
		case Status.BS_POINT://百胜会员积分.
			payPayment="payPoint";
			  break;
		case Status.ALIPAY://支付宝支付.
		case Status.WXPAY://微信支付.
			payPayment="payAlipay";
			  break;
		default:
		  break;
		}	
		ngDialog.open({
			template:payPayment,
			controller:"payingCtrl",
			scope:$scope,
			preCloseCallback:function(value){
				//关闭支付之前,判断是否已经支付完成，如果没有支付，则把当前payment设置为未支付状态.
				var payment=$scope.currPayment;
				var transNo=payment.transNo;
				if(transNo){
					payment.selected=1;
				}else{
					payment.selected=0;
				}
				$scope.payChannel=null;
				$scope.currPayment=null;
				$scope.barcode=null;
				loadPayInfo();
			}
			
		});
	}
	
	
	
	$scope.submit=function(){
		var info=$scope.info;
		//如果支付没有完成，不允许提交.
		if(info.needPay>0){
			alert('支付未完成');
			return;
		}
		info.payments=$scope.payments;
		SaleService.submit(info).then(function(data){
			//支付完成后刷新数据.
			if(data.data.status==Status.SUCCESS){
				/*alert("数据保存成功.");*/
				ngDialog.close();
				$timeout(function(){
					$("#cancelSale").trigger("click");
				});
				$scope.details = data.data.data.details;
				var details = JSON.stringify(data.data.data)
				window.localStorage.setItem("details",details)
				printService.printPage("sales",$scope);
				
			}else{
				
			}
			
		});
	}
	
	//判断支付输入的金额是否为空.
	function checkPayEmpty(payment){
		var amount=payment.amount;
		if(!amount || isNaN(amount)){
			payment.selected=0;
			return false;
		}
		payment.selected=1;
		return true;
	}
	
	$scope.loadPayInfo=loadPayInfo;
	//基于目前的支付信息，刷新支付数据.
	function loadPayInfo(){
		var info=$scope.info;
		var pay=info.pay;
		//var needPay=info.needPay;
		//var payed=parseFloat(info.payed);
		
		var payments = $scope.payments;
		var payed=0;
		for(var i=0;i<payments.length;i++){
			var payment=payments[i];
			if(payment.amount && payment.amount >0){
				payment.selected=1;
				payed += parseFloat(payment.amount || 0);
			}else{
				payment.selected=0;
			}
		}
		info.payed=payed.toFixed(2);
		info.needPay=(pay-info.payed).toFixed(2);
		
	}
	
	
	
}]);

app.controller("promotionCtrl",['$scope','$location','SaleService','ngDialog','PayService','$timeout',function($scope,$location,SaleService,ngDialog,PayService,$timeout){
	
	
	
	$scope.close=function(){
		ngDialog.close();
	}
	
	$scope.choosePromotion = function(promotion){
		
		if(!$("#promotion_"+promotion.id).prop("checked")){
			return ;
		}
		
		var id = promotion.id;
		var effPromotions=$scope.effPromotions;
		var excluded=promotion.excluded;
		var index=effPromotions.indexOf(promotion);
		for(var i=0;i<effPromotions.length;i++){
			var effp=effPromotions[i];
			if(effp.selected && excluded && excluded.indexOf(effp.id)>=0){
				//促销互斥，不允许参加此促销活动.
				promotion.selected = false;
				effPromotions[index]=promotion;
				alert("和促销活动["+effp.name+"]互斥");
				$("#promotion_"+promotion.id).prop("checked",false)
				$scope.effPromotions=effPromotions;
				return;
			}
		}
	}
	
	$scope.submit=function(flag){
		if(flag==1){//都不参加活动.
			$scope.info.cancelPromotion=flag;
			gotoPay(ngDialog,$scope,PayService);
		}else{//参加活动.
			var effpromotions = $scope.effPromotions;
			var promotions=[];
			for(var i=0;i<effpromotions.length;i++){
				if(effpromotions[i].selected){
					promotions.push(effpromotions[i]);
				}
			}
			if(promotions.length==0){//如果没有勾选任何促销.
				alert('请选择促销活动');
				return;
			}
			$scope.info.promotions=promotions;
			SaleService.cacPromotions($scope.info).then(function(obj){
				 if(obj.data.status==Status.SUCCESS){
					 
	                    var dto=obj.data.data;
	                    $scope.info.saleInfos=dto.saleInfos;
	                    gotoPay(ngDialog,$scope,PayService);
	             }else{
	            	 alert(obj.data.msg);
	             }
		   });
		}
	}
}]);

app.controller("pinBackCtl",['$scope','$location','PinBackService','ngDialog','PayService','$timeout',function($scope,$location,PinBackService,ngDialog,PayService,$timeout){
	
	$scope.$on('$viewContentLoaded', function(){
		$('[data-provide="datepicker-inline"]').datepicker();
	});
	
	//初始化数据.
	var initCurrentPage = 1;
	var totalItems = 0;
	
	$scope.paginationConf = {
	        currentPage: initCurrentPage,
	        onChange: function(){
	        	goPage($scope.paginationConf.currentPage);
	        }
	};
	
	$scope.queryByPage = function(){
		goPage(1);
	};
	
	$scope.pageTurn = function(flag){
		var currentPage=$scope.paginationConf.pageNo;
		goPage(currentPage+flag);
	}
	//整单退款.
	$scope.matBack = function(id){
		$scope.salesOrderId=id;
		ngDialog.open({
            template: '/front/view/template/back.html',
            scope: $scope,
            closeByDocument: false,
            width:'100%',
            controller: 'backCtrl'
        });
	}
	goPage();
	
	function goPage(pageNo){
		var body = {};
		pageNo = pageNo || 1;
		body.pageNo = pageNo || 1;
		body.limit = $scope.paginationConf.itemsPerPage || 10;
		if($scope.selectForm){
			body.startDate=$scope.selectForm.startDate;
			body.endDate=$scope.selectForm.endDate;
			body.id=$scope.selectForm.id;
			body.tel=$scope.selectForm.tel;
		}
		
		PinBackService.getSalesOrder(body).then(function(obj){
			var data=obj.data;
			 if(data.status==Status.SUCCESS){
	             var dto=data.data;
	             $scope.basicsInfo = dto;
	             $scope.paginationConf.totalItems=dto.totalRecord;
	             $scope.paginationConf.totalPage=dto.totalPage;
	             $scope.paginationConf.pageNo=pageNo;
	         }else if(data.status==Status.INVALID_TOKEN){
	        	 alert(data.msg);
	        	 location.href="/front/login.html";
	         }else{
	        	 alert(data.msg);
	        	 return;
	         }
		});
	}
	

}]);
app.controller("backCtrl",['$scope','$location','PinBackService','ngDialog','PayService','$timeout',function($scope,$location,PinBackService,ngDialog,PayService,$timeout){
	var id=$scope.salesOrderId;
	var condition={id:id};
	PinBackService.getSalesOrderDetails(condition).then(function(obj){
		var data=obj.data;
		if(data.status==Status.SUCCESS){
			$scope.salesOrder=data.data;
		}else{
			alert(data.msg);
		}
	});
	
	$scope.refund = function(salesOrderId){
		var condition={};
		condition.salesOrderId=salesOrderId;
		PinBackService.refund(condition).then(function(obj){
			var data=obj.data;
			if(data.status==Status.SUCCESS){
				alert("操作成功");
				ngDialog.close();
				$timeout(function(){
					$("#searchSalesOrder").trigger("click");
				});
			}else{
				alert(data.msg);
			}
		});
	};
	$scope.close=function(){
		ngDialog.close();
	}
	
}]);
//定义各控制器公用方法，方便控制器进行通用调用.
/**
 * 调用结算按钮，打开支付页面.
 */
function gotoPay(ngDialog,$scope,PayService){
	var openPay = function(ngDialog,$scope){
		ngDialog.open({
            template: '/front/view/template/pay.html',
            scope: $scope,
            closeByDocument: false,
            width:700,
            controller: 'payCtrl',
            showClose:false
            
        });
	}
	
	if($scope.payments){
		openPay(ngDialog,$scope);
	 }else{
		 PayService.initPay().then(function(data){
				$scope.payments=data.data;
				//打开付款浮层.
				openPay(ngDialog,$scope);
		 });
	 }
	
}

function clearPromotionSaleInfos(saleInfos){
	//删除掉促销相关的信息.
	var result=[];
	for(var i=0;i<saleInfos.length;i++){
		if(!saleInfos[i]) continue;
		if(saleInfos[i].discType == '3' || saleInfos[i].discType=='4'){
			delete saleInfos[i];
		}else{
			saleInfos[i].promotionDiscount=0;
			saleInfos[i].promotionDiscountDetails=[];
			result.push(saleInfos[i]);
		}
	}
	return result;
}
//重新计算saleinfos.
function reloadSaleInfos(saleInfos){
	//提取出单项折扣选项.
	var itemDiscs=[];
	//提取出整单折扣.
	var allDiscs=[];
	var mats=[];
	for(var i=0;i<saleInfos.length;i++){
		var saleInfo=saleInfos[i];
		if(!saleInfo) continue;
		if(!saleInfo.discType){//商品
			mats.push(saleInfo);
		}else if(saleInfo.discType == 1){//单项折扣 .
			itemDiscs.push(saleInfo);
		}else if(saleInfo.discType == 2){//整单折扣.
			allDiscs.push(saleInfo);
		}
	}
	//首先处理单项折扣.
	if(itemDiscs.length>0){//如果存在单项折扣.
		for(var i=0;i<itemDiscs.length;i++){
			var itemDisc=itemDiscs[i];
			var flag=false;
			for(var j=0;j<mats.length;j++){
				var mat=mats[j];
				if(itemDisc.dict.saleInfo.sort==mat.sort){//如果单项折扣对应的商品能够匹配上。
					mat.discount=cacItemDisc(mat,itemDisc.dict);
					itemDisc.totalPrice=mat.discount;
					flag=true;
				}
			}
			if(!flag){//如果折扣没有匹配上,删除此折扣信息.
				saleInfos.remove(itemDisc);
			}
		}
	}
	//处理整单折扣.
	if(allDiscs.length>0){
		for(var i=0;i<allDiscs.length;i++){
			var allDisc=allDiscs[i];
			if(mats.length==0){
				saleInfos.remove(allDisc);
				continue;
			}
			cacAllDisc(allDisc,mats);
		}
	}
	//处理物料信息,如果物料中存在单项折扣或者整单折扣，而选项又没有，则需要删除相关属性.
	if(mats.length>0){
		for(var i=0;i<mats.length;i++){
			var mat=mats[i];
			//单项折扣匹配.
			if(mat.discount && mat.discount>0){
				var flag=false;
				for(var i=0;i<itemDiscs.length;i++){
					var itemDisc=itemDiscs[i];
					if(mat.sort == itemDisc.dict.saleInfo.sort){
						flag=true;
						break;
					}
				}
				if(!flag){
					mat.discount=0;
				}
				//如果不存在整单折扣，删除折扣信息.
				if(allDiscs.length==0){
					mat.allDiscount=0;
				}
			}
		}
	}
}

function cacItemDisc(saleInfo,dict){
	var disc="0";
	var totalPrice=(saleInfo.count*saleInfo.retailPrice).toFixed(2);//保留2位小数.
	var inputDisc=dict.disc;
	var type=dict.type;
	if(type=="1"){//折扣%
		disc=0-(totalPrice*(100-inputDisc)/100).toFixed(2);
	}else if(type=="2"){//折让
		disc=0-inputDisc;
	}
	return disc;
}

function cacAllDisc(allDisc,saleInfos){
	var disc="";
	var totalPrice=0;
	var type=allDisc.dict.type;
	var inputDisc=allDisc.dict.disc;
	//循环遍历出整个商品
	for(var i=0;i<saleInfos.length;i++){
		var saleInfo=saleInfos[i];
		if(!saleInfo) continue;
		if(!saleInfo.discType){//discType不存在,说明是普通商品.
			totalPrice+=Number(Number(saleInfo.count*saleInfo.retailPrice).toFixed(2));//保留2位小数.
			if(saleInfo.discount){
				totalPrice+=Number(Number(saleInfo.discount).toFixed(2));//增加该商品的单项折扣.
			}
			if(saleInfo.allDiscount){
				totalPrice+=Number(Number(saleInfo.allDiscount).toFixed(2));//增加该商品的单项折扣.
			}
		}
	}
	if(type=="1"){//折扣%
		disc=0-(totalPrice*(100-inputDisc)/100).toFixed(2);
	}else if(type=="2"){//折让
		disc=0-inputDisc;
	}
	allDisc.totalPrice=disc;

	//计算折扣在每件商品中的折扣.
	for(var i=0;i<saleInfos.length;i++){
		var saleInfo=saleInfos[i];
		if(!saleInfo) continue;
		if(!saleInfo.discType){//discType不存在,说明是普通商品.
			var saleInfoPrice=Number(Number(saleInfo.count*saleInfo.retailPrice).toFixed(2));//保留2位小数.
			if(saleInfo.discount){
				saleInfoPrice+=Number(Number(saleInfo.discount).toFixed(2));//增加该商品的单项折扣.
			}
			if(saleInfo.allDiscount){
				saleInfoPrice+=Number(Number(saleInfo.allDiscount).toFixed(2));//增加该商品的单项折扣.
			}
			var saleInfoDisc=(disc*saleInfoPrice/totalPrice).toFixed(2);
			saleInfo.allDiscount=saleInfoDisc;
		}
	}
}



//支付宝支付.
function aliPay(PayService,$scope,channel,code,payment,barcode,amount,transNo){
	var success = function(data){
		//支付成功,存储当前支付的流水号，退款使用.
		if(code=="01" || code=="04"){
			payment.transNo=data.trade_no;
			payment.barcode=barcode;
			payment.amount=amount;
			$scope.closeThisDialog();
			alert('支付成功.');
		}else{
			payment.transNo='';
			payment.barcode='';
			payment.amount=0;
			alert('退款成功');
		}
		$scope.loadPayInfo();
		
	};
	var error = function(data){
		payment.selected=0;
		//$scope.closeThisDialog();
	}
	basicPay(PayService,$scope,channel,code,transNo,barcode,amount,success,error);
}

//微信支付宝基础支付方法.
function basicPay(PayService,$scope,channel,code,transNo,barcode,amount,success,error){
	var payInfo={};
	var id = $scope.info.id;
	var salesDate=$scope.info.saleDate;
	payInfo.channel=channel;
	payInfo.code=code;
	payInfo.salesDtoId=id;
	payInfo.salesDate=salesDate;
	payInfo.transNo=transNo;
	payInfo.barcode=barcode;
	payInfo.amount=amount;
	PayService.aliPay(payInfo).then(function(obj){
		var dto=obj.data.data;
		if(obj.data.status==Status.SUCCESS){
            if(success){
            	success(dto);
            }
        }else{
        	if(error){
        		error(dto);
        	}
        	alert(obj.data.msg);
        	
        }
	});
}

app.controller("salesDetailCtl",['$scope','$location','SaleDetailService','printService',
                                 function($scope,$location,SaleDetailService,printService){
	$scope.$on('$viewContentLoaded', function(){
		$(".input-daterange").datepicker({
		    language: "zh-CN",
		    autoclose: true,
		    todayBtn:true
		    
		}); 
	});
	var initCurrentPage = 1;
	var totalItems = 0;
	
	$scope.paginationConf = {
	        currentPage: initCurrentPage,
	        onChange: function(){
	        	goPage($scope.paginationConf.currentPage);
	        }
	};
	$scope.queryByPage = function(){
		goPage(1);
	};
	$scope.pageTurn = function(flag){
		var currentPage=$scope.paginationConf.pageNo;
		goPage(currentPage+flag);
	}
	$scope.print = function(target){
		var orderId = $(target).siblings("input").val();
		printService.getData("salesDetail",orderId);
		
		
	}
	function goPage(pageNo){
		var body = {};
		pageNo = pageNo || 1;
		body.pageNo = pageNo || 1;
		body.limit = $scope.paginationConf.itemsPerPage || 3;
		if($scope.selectForm){
			body.startDate=$scope.selectForm.startDate;
			body.endDate=$scope.selectForm.endDate;
			body.matCode=$scope.selectForm.matCode;
		
		}
		
		SaleDetailService.searchMat(body).then(function(data){
			
			var data=data.data;
			 if(data.status==Status.SUCCESS){
	             var dto=data.data;
	             if(dto!=null){
	            	 $scope.info = dto;
		             $scope.paginationConf.totalItems=dto.totalRecord;
		             $scope.paginationConf.totalPage=dto.totalPage;
		             $scope.paginationConf.pageNo=pageNo;
		             $scope.info.notNull = true;
	             }else{
	            	 $scope.info=new Object;
	            	 $scope.info.notNull = false;
	             }
	         }else{
	        	 alert(data.msg);
	        	 return;
	         }
        });
	};
	
}]);

app.controller("salesSummaryCtl",['$scope','$location','SaleSummaryService',
                                  function($scope,$location,SaleSummaryService){
	
	$scope.$on('$viewContentLoaded', function(){
		$(".input-daterange").datepicker({
		    language: "zh-CN",
		    autoclose: true,
		    todayBtn:true
		    
		}); 
	});
	var initCurrentPage = 1;
	var totalItems = 0;
	
	
	$scope.paginationConf = {
	        currentPage: initCurrentPage,
	        onChange: function(){
	        	goPage($scope.paginationConf.currentPage);
	        }
	};
	$scope.queryByPage = function(){
		goPage(1);
	};
	$scope.pageTurn = function(flag){
		var currentPage=$scope.paginationConf.pageNo;
		goPage(currentPage+flag);
	}
	function goPage(pageNo){
		var body = {};
		pageNo = pageNo || 1;
		body.pageNo = pageNo || 1;
		body.limit = $scope.paginationConf.itemsPerPage || 10;
		if($scope.selectForm){
			body.startDate=$scope.selectForm.startDate;
			body.endDate=$scope.selectForm.endDate;
			body.matCode=$scope.selectForm.matCode;
		
		}
		
		SaleSummaryService.searchMat(body).then(function(data){
			
			var data=data.data;
			 if(data.status==Status.SUCCESS){
	             var dto=data.data;
	             if(dto.results!=null){
	            	 $scope.info = dto;
		             $scope.paginationConf.totalItems=dto.totalRecord;
		             $scope.paginationConf.totalPage=dto.totalPage;
		             $scope.paginationConf.pageNo=pageNo;
		             $scope.info.notNull = true;
	             }else{
	            	 $scope.info=new Object;
	            	 $scope.info.notNull = false;
	             }
	         }else{
	        	 alert(data.msg);
	        	 return;
	         }
        });
	};
}]);

app.controller("paymentSummaryCtl",['$scope','$location','PaymentSummaryService',function($scope,$location,PaymentSummaryService){
	$scope.$on('$viewContentLoaded', function(){
		$(".input-daterange").datepicker({
		    language: "zh-CN",
		    autoclose: true,
		    todayBtn:true
		    
		}); 
	});
	$scope.searchMat = function(){
		
		if(!$scope.ordForm){
			return;
		}
		
		PaymentSummaryService.searchMat($scope.ordForm).then(function(data){
			
			$scope.info = data;
			
			
			
			$scope.barChart=true;
        });
		
	};
	
	
}])



