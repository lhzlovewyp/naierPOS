/**
 * Created by crell on 2016/1/17.
 */

var app = angular.module('mainApp', ['ngRoute'])

.run(['$rootScope','BaseDataService',function($rootScope,BaseDataService) {
    // 加载 fastclick
    window.addEventListener('load', function () {
        FastClick.attach(document.body);
    }, false);
    
    BaseDataService.getAppData().success(function(obj){
        $rootScope.domain = obj.data.domain;
    });
}])
/*****
 * Interceptor
 */
.factory('statusInterceptor', ['$q','$location',function($q,$location) {
    var statusInterceptor = {
        response: function(response) {
            var deferred = $q.defer();
            if(response.data.status == Status.ERROR){//系统错误
                $location.path('/error');
                return deferred.promise;
            }else if(response.data.status == Status.FAILED){
                alert(response.data.msg);
                return deferred.promise;
            }else if(response.data.status == Status.INVALID_TOKEN){
                $location.path('/login');
                return deferred.promise;
            }else if(response.data.status == Status.INVALID_USER){
                $location.path('/login');
                return deferred.promise;
            }else{
                return response;
            }
        }
    };
    return statusInterceptor;
}])
/****
 *路由 模板设置
 */
.config(['$routeProvider','$locationProvider','$httpProvider', function ($routeProvider,$locationProvider,$httpProvider) {
    $locationProvider.html5Mode(true);

    $httpProvider.interceptors.push('statusInterceptor');
    //取data 不用xxx.data
    $httpProvider.defaults.transformResponse.push(function(responseData){
        if(responseData && responseData.hasOwnProperty("data")){
            return responseData;
        }else{
            return responseData;
        }
    });

    $routeProvider
        .when('/', {
            templateUrl: 'view/main.html',
            controller: 'RouteMainCtl'
        })
        .when('/business/:id', {
            templateUrl: '/view/business.html',
            controller: 'RouteBusinessCtl'
        })
        .when('/error', {
            templateUrl: 'view/error.html',
            controller: 'RouteErrorCtl'
        })
        .when('/login', {
            templateUrl: 'view/login.html',
            controller: 'RouteLoginCtl'
        })
        .when('/my', {
            templateUrl: 'view/my.html',
            controller: 'RouteMyCtl'
        })
        .when('/cart', {
            templateUrl: 'view/cart.html',
            controller: 'RouteCartCtl'
        })
        .when('/register', {
            templateUrl: 'view/register.html',
            controller: 'RouteRegisterCtl'
        })
        .when('/order', {
            templateUrl: 'view/order.html'
        })
        .when('/about', {
            templateUrl: 'view/about.html'
        })
        .otherwise({
            redirectTo: '/'
        });
}]);
