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
			$scope.info.totalDiscPrice=discPrice;
			$scope.info.pay=(totalPrice-Math.abs(discPrice)).toFixed(2);
			$scope.info.payed=0;
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
            if(data.data.property=="1"){//需要维护属性.
            	
            	$scope.tempMat=data.data;
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
            }
        });
	}
	//删除商品.
	$scope.deleteSaleInfo=function(saleInfo){
		$scope.info.saleInfos.splice($scope.info.saleInfos.indexOf(saleInfo), 1);
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
		SaleService.getPromotions($scope.info).then(function(data){
			var effPromotions=data.data.effPromotions;
			//如果没有可以参加的促销活动.
			if(!effPromotions){
				 $scope.value = true;
				 if($scope.payments){
					 ngDialog.open({
				            template: '/front/view/template/pay.html',
				            scope: $scope,
				            closeByEscape: false,
				            width:700,
				            controller: 'payCtrl'
				        });
				 }else{
					 PayService.initPay().then(function(data){
							$scope.payments=data.data;
							//打开付款浮层.
							ngDialog.open({
					            template: '/front/view/template/pay.html',
					            scope: $scope,
					            closeByEscape: false,
					            width:700,
					            controller: 'payCtrl'
					        });
					 });
				 }
				 
				
			}else{
				$scope.effPromotions=effPromotions;
				ngDialog.open({
		            template: '/front/view/template/promotion.html',
		            scope: $scope,
		            closeByEscape: false,
		            width:700,
		            controller: 'promotionCtrl',
		            preCloseCallback: function () {
		            	var saleInfos = clearPromotionSaleInfos($scope.info.saleInfos);
		            	$scope.info.saleInfos=saleInfos;
	                }
		        });
			}
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
					totalPrice+=Number((saleInfo.count*saleInfo.retailPrice).toFixed(2));//保留2位小数.
				}else if(saleInfo.discType == "1"){//单项折扣.
					totalPrice+=Number(saleInfo.totalPrice);
				}else if(saleInfo.discType == "2"){
					totalPrice+=Number(saleInfo.totalPrice);
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
			var saleInfo={};
			saleInfo.code=code;
			saleInfo.totalPrice=disc;
			saleInfo.discType="1";//单项打折
			if(!saleInfo.sort){
				saleInfo.sort=$scope.info.saleInfos.length;
			}
			//保存折扣信息，方便后续二次计算.
			saleInfo.dict={
					type:type,
					saleInfo:tempMat,
					disc:inputDisc
			};
			$scope.info.saleInfos.insert($scope.info.saleInfos.indexOf(tempMat)+1,saleInfo);
			//$scope.info.saleInfos.push(saleInfo);
			
		}
		ngDialog.close();
		
	}
	$scope.close=function(){
		ngDialog.close();
	}
}]);

app.controller("payCtrl",['$scope','$location','PayService','SaleService','ngDialog','$timeout',function($scope,$location,PayService,SaleService,ngDialog,$timeout){
	
	$scope.close=function(){
		loadPayInfo();
		ngDialog.close();
	}
	
	$scope.pay=function(payment){
		if(payment.selected==1){
			payment.selected=0;
			loadPayInfo();
			return;
		}else{
			payment.selected=1;
		}
		//如果是select状态,需要判断支付类型.
		if(payment.selected==1){
			if(Status.CASH == payment.payment.code || Status.UNIONPAY_OFF == payment.payment.code){//现金支付 银行卡支付
				loadPayInfo();
			}else if(Status.BS_PREPAID == payment.payment.code){//百胜储值卡
				  //支付金额为空判断.
				  if(!checkPayEmpty(payment)){
					  return false;
				  }
				  //百盛储值卡支付
			}else if(Status.BS_COUPON == payment.payment.code){//百胜电子券
				if(!checkPayEmpty(payment)){
					  return false;
				 }
				//百盛电子券支付.
			}else if(Status.BS_POINT == payment.payment.code){//百胜会员积分
				if(!checkPayEmpty(payment)){
					  return false;
				 }
				//百盛电子券支付.
			}else if(Status.ALIPAY == payment.payment.code){//支付宝支付
				if(!checkPayEmpty(payment)){
					  return false;
				 }
				//支付宝支付只能作为最后一个支付渠道
				
			}else if(Status.WXPAY == payment.payment.code){//微信支付.
				//微信支付只能作为最后一个支付渠道
			}
		}
		
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
				alert("数据保存成功.");
				ngDialog.close();
				$timeout(function(){
					$("#cancelSale").trigger("click");
				});
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
			if(payment.selected ==1 ){
				payed += parseFloat(payment.amount || 0);
			}
		}
		info.payed=payed.toFixed(2);
		info.needPay=(pay-info.payed).toFixed(2);
		
	}
	
}]);

app.controller("promotionCtrl",['$scope','$location','SaleService','ngDialog','PayService',function($scope,$location,SaleService,ngDialog,PayService){
	$scope.close=function(){
		ngDialog.close();
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






//定义各控制器公用方法，方便控制器进行通用调用.
/**
 * 调用结算按钮，打开支付页面.
 */
function gotoPay(ngDialog,$scope,PayService){
	var openPay = function(ngDialog,$scope){
		ngDialog.open({
            template: '/front/view/template/pay.html',
            scope: $scope,
            closeByEscape: false,
            width:700,
            controller: 'payCtrl'
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
			result.push(saleInfos[i]);
		}
	}
	return result;
}

