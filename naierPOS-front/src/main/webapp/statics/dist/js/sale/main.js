
//增加公用方法，对数组进行插入.
Array.prototype.insert = function (index, item) {
this.splice(index, 0, item);
};

var app=angular.module('mainApp',['ngRoute','ngDialog']);

//app.factory('statusInterceptor', ['$q','$location',function($q,$location) {
//    var statusInterceptor = {
//            response: function(response) {
//                var deferred = $q.defer();
//                if(response.data.status == Status.ERROR){//系统错误
//                    $location.path('/error');
//                    return deferred.promise;
//                }else if(response.data.status == Status.FAILED){
//                    //alert(response.data.msg);
//                    //return deferred.promise;
//                	return response;
//                }else if(response.data.status == Status.INVALID_TOKEN){
//                    $location.path('/login');
//                    return deferred.promise;
//                }else if(response.data.status == Status.INVALID_USER){
//                    $location.path('/login');
//                    return deferred.promise;
//                }else{
//                    return response;
//                }
//            }
//        };
//        return statusInterceptor;
//}]);


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
            controller: 'routeMainCtl'
        })
        .when('/front/member.html', {
            templateUrl: '/front/view/member.html',
            controller: 'routeMainCtl'
        })
        .when('/front/order.html', {
            templateUrl: '/front/view/order.html',
            controller: 'routeMainCtl'
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

