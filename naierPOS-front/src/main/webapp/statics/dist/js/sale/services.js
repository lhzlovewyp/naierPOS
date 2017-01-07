app.factory('LoginService',['$q','$location','$http','BaseService',function($q,$location,$http,BaseService){
    var loginInfo = {hasLogin:false};
    return {
        login:function(condition){
            var deferred = $q.defer();
            showMask();
            var t=setTimeout(function(){
            	closeMask();
            	alert('连接超时,请稍后重试.');
            },10000);
            BaseService.post('/rest/login', condition).then(function (obj) {
            	closeMask();
            	if(t){
            		clearTimeout(t);
            	}
            	
                if(obj && obj.data && obj.data.status == Status.SUCCESS) {
                    loginInfo = obj.data.data;
                    loginInfo.hasLogin = true;
                    loginInfo.loginerror = false;
                    $.cookie('userId',loginInfo.userId,{expires: 1,path:"/"});
                    $.cookie('nickName',loginInfo.nickName,{expires: 1,path:"/"});
                    $.cookie('token',loginInfo.token,{expires: 1,path:"/"});
                    $http.defaults.headers.common.token = loginInfo.token;
                }else{
                    loginInfo.hasLogin = false;
                    loginInfo.loginerror = true;
                    loginInfo.loginerroinfo = obj.data.msg;
                }
                deferred.resolve(loginInfo);
            });
            return deferred.promise;
        },
        chooseStore:function(store){
        	var deferred = $q.defer();
        	var token=$.cookie("token");
        	var obj={"token":token,"store":store};
        	showMask();
            var t=setTimeout(function(){
            	closeMask();
            	alert('连接超时,请稍后重试.');
            },10000);
            BaseService.post('/rest/login/chooseStore', obj).then(function (obj) {
            	closeMask();
            	if(t){
            		clearTimeout(t);
            	}
                if(obj && obj.data && obj.data.status == Status.SUCCESS) {
                    loginInfo = obj.data.data;
                    loginInfo.hasLogin = true;
                    loginInfo.loginerror = false;
                }else{
                    loginInfo.hasLogin = false;
                    loginInfo.loginerror = true;
                    loginInfo.loginerroinfo = obj.data.msg;
                }
                deferred.resolve(loginInfo);
            });
            return deferred.promise;
        },
        updatePWD:function(condition){
        	var deferred = $q.defer();
        	var newPwd=condition.newPassword;
        	var reNewPwd=condition.reNewPassword;
        	var info={};
        	if(newPwd != reNewPwd){
        		info.updateStatus = false;
        		info.error=true;
        		info.errorInfo="2次输入的密码不一致";
        		deferred.resolve(info);
        	}else{
        		showMask();
                var t=setTimeout(function(){
                	closeMask();
                	alert('连接超时,请稍后重试.');
                },10000);
        		//修改密码.
        		 BaseService.post('/rest/account/updatePWD', condition).then(function (obj) {
        			 closeMask();
                 	if(t){
                 		clearTimeout(t);
                 	}
                     if(obj && obj.data && obj.data.status == Status.SUCCESS) {
                         //info = obj.data.data;
                         info.error=false;
                         info.updateStatus = true;
                     }else{
                    	 info.updateStatus = false;
                    	 info.error = true;
                    	 info.errorInfo = obj.data.msg;
                     }
                     deferred.resolve(info);
                 });
        	}
        	return deferred.promise;
        },
        validateToken : function(){//获取当前用户信息
        	var deferred = $q.defer();
        	var token=$.cookie("token");
        	var obj={"token":token};
        	showMask();
            var t=setTimeout(function(){
            	closeMask();
            	alert('连接超时,请稍后重试.');
            },10000);
        	BaseService.post('/rest/login/validToken', obj).then(function (data) {
        		closeMask();
              	if(t){
              		clearTimeout(t);
              	}
        		if(data.data.status == Status.SUCCESS){
        			loginInfo.userName=data.data.data.nick;
        			loginInfo.storeName=data.data.data.store ? data.data.data.store.name:'';
        			deferred.resolve(loginInfo);
        		}else{
        			location.href = '/front/login.html';
        		}
        	});
        	return deferred.promise;
        },
        logOff : function(){
        	showMask();
            var t=setTimeout(function(){
            	closeMask();
            	alert('连接超时,请稍后重试.');
            },10000);
        	var token=$.cookie("token");
        	var obj={"token":token};
            BaseService.post('/rest/login/logout',obj).then(function(obj){
            	closeMask();
              	if(t){
              		clearTimeout(t);
              	}
                if(obj.data.status==Status.SUCCESS){
                    $.cookie('token', '', { expires: -1,path:"/"});
                    $.cookie('nickName',loginInfo.nickName,{expires: 1,path:"/"});
                    location.href = '/front/login.html'
                }
            });
        }
    }
}]);


app.factory('HomeService',['$q','$location','$http','BaseService',function($q,$location,$http,BaseService){
	return {
		getHomeData : function(){
			var token=$.cookie("token");
			var obj={"token":token};
			var deferred = $q.defer();
			var info={};
			showMask();
            var t=setTimeout(function(){
            	closeMask();
            	alert('连接超时,请稍后重试.');
            },10000);
			BaseService.post('/rest/home/index',obj).then(function(obj){
				closeMask();
            	if(t){
            		clearTimeout(t);
            	}
                if(obj.data.status==Status.SUCCESS){
                    var dto=obj.data.data;
                	info.saleDate=dto.saleDate;
                	info.perTicketSales=dto.perTicketSales;
                	info.salesAmount = dto.salesAmount;
                	info.orders=dto.orders;
                	deferred.resolve(info);
                }
            });
			return deferred.promise;
		},
		dayReport : function(){
			showMask();
            var t=setTimeout(function(){
            	closeMask();
            	alert('连接超时,请稍后重试.');
            },10000);
			var token=$.cookie("token");
			var obj={"token":token};
			var deferred = $q.defer();
			var info={};
			BaseService.post('/rest/home/dayReport',obj).then(function(obj){
				closeMask();
            	if(t){
            		clearTimeout(t);
            	}
               	info.status=obj.data.status;
            	deferred.resolve(info);
            });
			return deferred.promise;
		}
	}
	
}]);


app.factory('SaleService',['$q','$location','$http','BaseService',function($q,$location,$http,BaseService){
	return {
		initSalesOrder : function(){
			showMask();
            var t=setTimeout(function(){
            	closeMask();
            	alert('连接超时,请稍后重试.');
            },10000);
			var token=$.cookie("token");
			var obj={"token":token};
			var deferred = $q.defer();
			var info={};
			BaseService.post('/rest/sale/initSaleOrder',obj).then(function(obj){
				closeMask();
            	if(t){
            		clearTimeout(t);
            	}
                if(obj.data.status==Status.SUCCESS){
                    var dto=obj.data.data;
                	info.saleDate=dto.saleDate;
                	info.itemDISC=dto.itemDISC;
                	info.allDISC=dto.allDISC;
                	info.id=dto.id;
                	deferred.resolve(info);
                }
            });
			return deferred.promise;
		},
		
		getShoppingGuide : function(condition){
			showMask();
            var t=setTimeout(function(){
            	closeMask();
            	alert('连接超时,请稍后重试.');
            },10000);
			var token=$.cookie("token");
			condition.token=token;
			var deferred = $q.defer();
			var info={};
			BaseService.post('/rest/shoppingGuide/getShoppingGuide',condition).then(function(obj){
				closeMask();
            	if(t){
            		clearTimeout(t);
            	}
                if(obj.data.status==Status.SUCCESS){
                    var dto=obj.data.data;
                    info.data=dto;
                	deferred.resolve(info);
                }
            });
			return deferred.promise;
		},
		searchMat : function(condition){
			showMask();
            var t=setTimeout(function(){
            	closeMask();
            	alert('连接超时,请稍后重试.');
            },10000);
			var token=$.cookie("token");
			if(!condition){
				return;
			}
			condition.token=token;
			var deferred = $q.defer();
			var info={};
			BaseService.post('/rest/m/getMaterialByCode',condition).then(function(obj){
				closeMask();
            	if(t){
            		clearTimeout(t);
            	}
                if(obj.data.status==Status.SUCCESS){
                    var dto=obj.data.data;
                    info.data=dto;
                	deferred.resolve(info);
                }
            });
			return deferred.promise;
		},
		getPromotions:function(condition){
			showMask();
            var t=setTimeout(function(){
            	closeMask();
            	alert('连接超时,请稍后重试.');
            },10000);
			var token=$.cookie("token");
			if(!condition){
				return;
			}
			condition.token=token;
			var deferred = $q.defer();
			var info={};
			BaseService.post('/rest/sale/getPromotions',condition).then(function(obj){
				closeMask();
            	if(t){
            		clearTimeout(t);
            	}
                if(obj.data.status==Status.SUCCESS){
                    var dto=obj.data.data;
                    info.data=dto;
                	deferred.resolve(info);
                }
            });
			return deferred.promise;
		},
		cacPromotions:function(condition){//参加活动.
			showMask();
            var t=setTimeout(function(){
            	closeMask();
            	alert('连接超时,请稍后重试.');
            },10000);
			var token=$.cookie("token");
			if(!condition){
				return;
			}
			condition.token=token;
			var deferred = $q.defer();
			var info={};
			BaseService.post('/rest/sale/cacPromotions',condition).then(function(obj){
				closeMask();
            	if(t){
            		clearTimeout(t);
            	}
				deferred.resolve(obj);
            });
			return deferred.promise;
		},
		autoJoinPromotions:function(condition){
			showMask();
            var t=setTimeout(function(){
            	closeMask();
            	alert('连接超时,请稍后重试.');
            },10000);
			var token=$.cookie("token");
			if(!condition){
				return;
			}
			condition.token=token;
			var deferred = $q.defer();
			var info={};
			BaseService.post('/rest/sale/autoJoinPromotions',condition).then(function(obj){
				closeMask();
            	if(t){
            		clearTimeout(t);
            	}
				deferred.resolve(obj);
            });
			return deferred.promise;
		},
		submit:function(pay){
			showMask();
            var t=setTimeout(function(){
            	closeMask();
            	alert('连接超时,请稍后重试.');
            },10000);
			var token=$.cookie("token");
			pay.token=token;
			var deferred = $q.defer();
			BaseService.post('/rest/sale/submit',pay).then(function(obj){
				closeMask();
            	if(t){
            		clearTimeout(t);
            	}
                deferred.resolve(obj);
            });
			return deferred.promise;
		}
	}
}]);	
app.factory('MemberService',['$q','$location','$http','BaseService',function($q,$location,$http,BaseService){
	return {
		getSinglerMember:function(condition){
			showMask();
            var t=setTimeout(function(){
            	closeMask();
            	alert('连接超时,请稍后重试.');
            },10000);
			var token=$.cookie("token");
			condition.token=token;
			var deferred = $q.defer();
			BaseService.post('/rest/member/getSingleMember',condition).then(function(obj){
				closeMask();
            	if(t){
            		clearTimeout(t);
            	}
                deferred.resolve(obj);
            });
			return deferred.promise;
		}
	}
}]);	

app.factory('PayService',['$q','$location','$http','BaseService',function($q,$location,$http,BaseService){
	return {
		initPay : function(condition){
			if(!condition){
				condition={};
			}
			var token=$.cookie("token");
			condition.token=token;
			var deferred = $q.defer();
			var info={};
			showMask();
            var t=setTimeout(function(){
            	closeMask();
            	alert('连接超时,请稍后重试.');
            },10000);
			BaseService.post('/rest/pay/initPay',condition).then(function(obj){
				closeMask();
            	if(t){
            		clearTimeout(t);
            	}
                if(obj.data.status==Status.SUCCESS){
                    var dto=obj.data.data;
                    info.data=dto;
                	deferred.resolve(info);
                }
            });
			return deferred.promise;
		},
		aliPay : function(condition){
			if(!condition){
				condition={};
			}
			var token=$.cookie("token");
			condition.token=token;
			var deferred = $q.defer();
			var info={};
			showMask();
            var t=setTimeout(function(){
            	closeMask();
            	alert('连接超时,请稍后重试.');
            },10000);
			BaseService.post('/rest/pay/aliPay',condition).then(function(obj){
				closeMask();
            	if(t){
            		clearTimeout(t);
            	}
                	deferred.resolve(obj);
            });
			return deferred.promise;
		},
		prepaidPay : function(condition){
			if(!condition){
				condition={};
			}
			var token=$.cookie("token");
			condition.token=token;
			var deferred = $q.defer();
			var info={};
			showMask();
            var t=setTimeout(function(){
            	closeMask();
            	alert('连接超时,请稍后重试.');
            },10000);
			BaseService.post('/rest/pay/prepaidPay',condition).then(function(obj){
				closeMask();
            	if(t){
            		clearTimeout(t);
            	}
                	deferred.resolve(obj);
            });
			return deferred.promise;
		},
		pointPay : function(condition){
			if(!condition){
				condition={};
			}
			var token=$.cookie("token");
			condition.token=token;
			var deferred = $q.defer();
			var info={};
			showMask();
            var t=setTimeout(function(){
            	closeMask();
            	alert('连接超时,请稍后重试.');
            },10000);
			BaseService.post('/rest/pay/pointPay',condition).then(function(obj){
				closeMask();
            	if(t){
            		clearTimeout(t);
            	}
                	deferred.resolve(obj);
            });
			return deferred.promise;
		},
		queryCoupon : function(condition){
			if(!condition){
				condition={};
			}
			var token=$.cookie("token");
			condition.token=token;
			var deferred = $q.defer();
			var info={};
			showMask();
            var t=setTimeout(function(){
            	closeMask();
            	alert('连接超时,请稍后重试.');
            },10000);
			BaseService.post('/rest/pay/selectCoupon',condition).then(function(obj){
				closeMask();
            	if(t){
            		clearTimeout(t);
            	}
                	deferred.resolve(obj);
            });
			return deferred.promise;
		},
		couponPay : function(condition){
			if(!condition){
				condition={};
			}
			var token=$.cookie("token");
			condition.token=token;
			var deferred = $q.defer();
			var info={};
			showMask();
            var t=setTimeout(function(){
            	closeMask();
            	alert('连接超时,请稍后重试.');
            },10000);
			BaseService.post('/rest/pay/couponPay',condition).then(function(obj){
				closeMask();
            	if(t){
            		clearTimeout(t);
            	}
                	deferred.resolve(obj);
            });
			return deferred.promise;
		}
	}
}]);	
app.factory('PinBackService',['$q','$location','$http','BaseService',function($q,$location,$http,BaseService){
	return {
		getSalesOrder : function(condition){
			if(!condition){
				condition={};
			}
			var token=$.cookie("token");
			condition.token=token;
			var deferred = $q.defer();
			showMask();
            var t=setTimeout(function(){
            	closeMask();
            	alert('连接超时,请稍后重试.');
            },10000);
			BaseService.post('/rest/pinBack/getSalesOrder',condition).then(function(obj){
				closeMask();
            	if(t){
            		clearTimeout(t);
            	}
                	deferred.resolve(obj);
            });
			return deferred.promise;
		},
		getSalesOrderDetails : function(condition){
			if(!condition){
				return ;
			}
			var token=$.cookie("token");
			condition.token=token;
			var deferred = $q.defer();
			showMask();
            var t=setTimeout(function(){
            	closeMask();
            	alert('连接超时,请稍后重试.');
            },10000);
			BaseService.post('/rest/pinBack/getSalesOrderDetails',condition).then(function(obj){
				closeMask();
            	if(t){
            		clearTimeout(t);
            	}
                	deferred.resolve(obj);
            });
			return deferred.promise;
		},
		refund : function(condition){
			var token=$.cookie("token");
			condition.token=token;
			var deferred = $q.defer();
			showMask();
            var t=setTimeout(function(){
            	closeMask();
            	alert('连接超时,请稍后重试.');
            },10000);
			BaseService.post('/rest/pinBack/refund',condition).then(function(obj){
				closeMask();
            	if(t){
            		clearTimeout(t);
            	}
                	deferred.resolve(obj);
            });
			return deferred.promise;
		}
		
	}
}]);

app.factory('SaleDetailService',['$q','$location','BaseService',function($q,$location,BaseService){
	return {
		searchMat:function(ordForm){
			var token=$.cookie("token");
			ordForm.token = token;
			if(!ordForm){
			    return;
			   };
			var deferred = $q.defer();
			showMask();
            var t=setTimeout(function(){
            	closeMask();
            	alert('连接超时,请稍后重试.');
            },10000);
			BaseService.post('/rest/statistics/getsalesDetail',ordForm).then(function(obj){
				closeMask();
            	if(t){
            		clearTimeout(t);
            	}
                 deferred.resolve(obj);
				
			});
			return deferred.promise;
		}
		
	}
}]);

app.factory('SaleSummaryService',['$q','$location','BaseService',function($q,$location,BaseService){
	return {
		searchMat:function(ordForm){
			var token=$.cookie("token");
			ordForm.token = token;
			if(!ordForm){
			    return;
			   };
			var deferred = $q.defer();
			showMask();
            var t=setTimeout(function(){
            	closeMask();
            	alert('连接超时,请稍后重试.');
            },10000);
			BaseService.post('/rest/statistics/getsalesSummary',ordForm).then(function(obj){
				closeMask();
            	if(t){
            		clearTimeout(t);
            	}
				deferred.resolve(obj);
				
			});
			return deferred.promise;
		}
		
	}
}]);

app.factory('PaymentSummaryService',['$q','$location','BaseService',function($q,$location,BaseService){
	return {
		searchMat:function(ordForm){
			var token=$.cookie("token");
			ordForm.token = token;
			if(!ordForm){
			    return;
			   };
			var deferred = $q.defer();
			showMask();
            var t=setTimeout(function(){
            	closeMask();
            	alert('连接超时,请稍后重试.');
            },10000);
			BaseService.post('/rest/statistics/getPaymentSummary',ordForm).then(function(obj){
				closeMask();
            	if(t){
            		clearTimeout(t);
            	}
				var info = {};
				var types = [];
				var amts = [];
				if(obj.data.status==Status.SUCCESS){
					var dto=obj.data.data;
					
                    /*info=dto;*/
                    for(var i=0;i<dto.length;i++){
                    	types.push(dto[i].payment.code);
                    	amts.push(dto[i].amount);
                    }
                    info.types = types;
                    info.amts = amts;
                    
                    deferred.resolve(info);
				}
			});
			return deferred.promise;
		}
		
	}
}]);

app.factory('AmtFormat',[function(){
	return {
		amtFormat:function(s,n){
			n = n > 0 && n <= 20 ? n : 2;
		    if(s==null) s=0;
		    s = parseFloat((s + "").replace(/[^\d\.-]/g, "")).toFixed(n) + "";
		    var l = s.split(".")[0],
		        r = s.split(".")[1];
		    var result  = '';
		    l = (l||0).toString();
		    while (l.length > 3) {
		        result = ',' + l.slice(-3) + result;
		        l = l.slice(0, l.length - 3);
		    }
		    if (l=="-") { result = l + result.slice(1); }
		    else result = l + result;
		    return result  + "." + r;
		}
	}
}]);

app.factory('myInterceptor',['$q','$rootScope',function($q,$rootScope){
	var interceptor = {
			'request':function(config){
				$rootScope.loading=true
				return config
			},
			'response':function(response){
				$rootScope.loading=false
				
				return response
			},
			'requestError':function(rejection){
				
				return rejection
			},
			'responseError':function(rejection){
				$rootScope.loading=false
				
				return rejection
            }
	}
	return interceptor;
}]);

app.factory('BasicsService',['$q','$location','$http','BaseService',function($q,$location,$http,BaseService){
	return {
		queryByPage : function(condition,routePath){
			var token=$.cookie("token");
			condition.token = token;
			var deferred = $q.defer();
			var info={};
			BaseService.post('/rest/'+routePath+'/queryByPage',condition).then(function(obj){
                if(obj.data.status==Status.SUCCESS){
                    var dto=obj.data.data;
                    info = dto;
                }
                deferred.resolve(info);
            });
			return deferred.promise;
		},
		queryByList : function(condition,routePath){
			var token=$.cookie("token");
			condition.token = token;
			var deferred = $q.defer();
			var info={};
			BaseService.post('/rest/'+routePath+'/queryByList',condition).then(function(obj){
                if(obj.data.status==Status.SUCCESS){
                    var dto=obj.data.data;
                    info = dto;
                }
                deferred.resolve(info);
            });
			return deferred.promise;
		},
		queryById : function(condition,routePath){
			var token=$.cookie("token");
			condition.token = token;
			var deferred = $q.defer();
			var info={};
			BaseService.post('/rest/'+routePath+'/queryById',condition).then(function(obj){
                if(obj.data.status==Status.SUCCESS){
                    var dto=obj.data.data;
                    info = dto;
                }
                deferred.resolve(info);
            });
			return deferred.promise;
		},
		add : function(condition,routePath){
			var token=$.cookie("token");
			condition.token = token;
			var deferred = $q.defer();
			var info={};
			BaseService.post('/rest/'+routePath+'/add',condition).then(function(obj){
				if(obj && obj.data && obj.data.status == Status.SUCCESS) {
            		info = obj.data.data;
                }else{
                	info.error = obj.data.msg;
                }
            	deferred.resolve(info);
            });
			return deferred.promise;
		},
		update : function(condition,routePath){
			var token=$.cookie("token");
			condition.token = token;
			var deferred = $q.defer();
			var info={};
			BaseService.post('/rest/'+routePath+'/update',condition).then(function(obj){
				if(obj && obj.data && obj.data.status == Status.SUCCESS) {
            		info = obj.data.data;
                }else{
                	info.error = obj.data.msg;
                }
            	deferred.resolve(info);
            });
			return deferred.promise;
		},
		del : function(condition,routePath){
			var token=$.cookie("token");
			condition.token = token;
			var deferred = $q.defer();
			var info={};
			BaseService.post('/rest/'+routePath+'/delete',condition).then(function(obj){
				if(obj && obj.data && obj.data.status == Status.SUCCESS) {
            		info = obj.data.data;
                }else{
                	info.delerror = obj.data.msg;
                }
            	deferred.resolve(info);
            });
			return deferred.promise;
		},
		queryTree : function(condition,routePath){
			var token=$.cookie("token");
			condition.token = token;
			var deferred = $q.defer();
			var info={};
			BaseService.post('/rest/'+routePath+'/queryByTree',condition).then(function(obj){
				if(obj.data.status==Status.SUCCESS){
                    var dto=obj.data.data;
                    info = dto;
                }
				deferred.resolve(info);
            });
			return deferred.promise;
		}
	}
}]);