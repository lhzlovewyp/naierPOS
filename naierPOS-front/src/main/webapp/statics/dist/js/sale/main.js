
//增加页面公共弹窗
function showMask(){
	$('body').css({'overflow': 'hidden'}).append('<div class="mask"></div><i class="fa fa-spin fa-spinner loading"></i>');
}

function closeMask(){
	$('.mask, .loading').remove()
	$('body').css({'overflow': 'auto'});
}


//增加公用方法，对数组进行插入.
Array.prototype.insert = function (index, item) {
this.splice(index, 0, item);
};
Array.prototype.remove = function(val) {
	var index = this.indexOf(val);
	if (index > -1) {
		this.splice(index, 1);
	}
};

Date.prototype.Format = function (fmt) { //author: meizz 
    var o = {
        "M+": this.getMonth() + 1, //月份 
        "d+": this.getDate(), //日 
        "h+": this.getHours(), //小时 
        "m+": this.getMinutes(), //分 
        "s+": this.getSeconds(), //秒 
        "q+": Math.floor((this.getMonth() + 3) / 3), //季度 
        "S": this.getMilliseconds() //毫秒 
    };
    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o)
    if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
}

var app=angular.module('mainApp',['ngRoute','ngDialog','tm.pagination']);

//app.factory('statusInterceptor', ['$q','$location',function($q,$location) {
//    var statusInterceptor = {
//            response: function(response) {
//                var deferred = $q.defer();
//                if(response.data.status==Status.REDIRECT){
//                	$location.href=response.data.data;
//                }
////                if(response.data.status == Status.ERROR){//系统错误
////                    $location.path('/error');
////                    return deferred.promise;
////                }else if(response.data.status == Status.FAILED){
////                    //alert(response.data.msg);
////                    //return deferred.promise;
////                	return response;
////                }else if(response.data.status == Status.INVALID_TOKEN){
////                    $location.path('/login');
////                    return deferred.promise;
////                }else if(response.data.status == Status.INVALID_USER){
////                    $location.path('/login');
////                    return deferred.promise;
////                }else{
////                    return response;
////                }
//            }
//        };
//        return statusInterceptor;
//}]);




//禁止模板缓存  
app.run(function($rootScope, $templateCache) {  
    $rootScope.$on('$routeChangeStart', function(event, next, current) {  
        if (typeof(current) !== 'undefined'){  
            $templateCache.remove(current.templateUrl);  
        }  
    });  
});  



/****
 *路由 模板设置
 */
app.config(['$routeProvider','$locationProvider','$httpProvider', function ($routeProvider,$locationProvider,$httpProvider) {
    $locationProvider.html5Mode(true);

//    $httpProvider.interceptors.push('statusInterceptor');
//    //取data 不用xxx.data
//    $httpProvider.defaults.transformResponse.push(function(responseData){
//        if(responseData && responseData.hasOwnProperty("data")){
//            return responseData;
//        }else{
//            return responseData;
//        }
//    });

    $routeProvider
        .when('/front/home.html', {
            templateUrl: '/front/view/main.html',
            controller: 'routeMainCtl'
        })
        .when('/front/sale.html', {
            templateUrl: '/front/view/sale.html',
            controller: 'routeSaleCtl'
        })
        .when('/front/pinBack.html', {
            templateUrl: '/front/view/pinBack.html',
            controller: 'pinBackCtl'
        })
        .when('/front/member.html', {
            templateUrl: '/front/view/member.html',
            controller: 'memberCtrl'
        })
        .when('/front/order.html', {
            templateUrl: '/front/view/order.html',
            controller: 'routeMainCtl'
        })
        .when('/front/salesDetail.html', {
            templateUrl: '/front/view/salesDetail.html',
            controller: 'salesDetailCtl'
            
        })
        .when('/front/salesSummary.html', {
            templateUrl: '/front/view/salesSummary.html',
            controller: 'salesSummaryCtl'
        })
        .when('/front/paymentSummary.html', {
            templateUrl: '/front/view/paymentSummary.html',
            controller: 'paymentSummaryCtl'
        })
        
}]);

app.config(['ngDialogProvider', function (ngDialogProvider) {
    ngDialogProvider.setDefaults({
        className: 'ngdialog-theme-default',
        plain: false,
        showClose: true,
        closeByDocument: true,
        closeByEscape: true,
        appendTo: false,
        preCloseCallback: function () {
            //console.log('default pre-close callback');
        }
    });
}]);

app.directive('onRenderFinish', function ($timeout) {
    return {
        restrict: 'A',
        link: function(scope, element, attr) {
            if (scope.$last === true) {
                $timeout(function() {
                    scope.$emit('ngRepeatFinished');
                });
            }
        }
    };
});

app.config(['$httpProvider',function($httpProvider){
	$httpProvider.interceptors.push('myInterceptor');
}])
