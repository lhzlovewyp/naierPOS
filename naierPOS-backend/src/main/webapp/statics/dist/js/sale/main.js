var app=angular.module('mainApp',['ngRoute','tm.pagination']);

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
        .when('/backend/home.html', {
            templateUrl: '/backend/view/main.html',
            controller: 'routeMainCtl'
        })
        .when('/backend/list/:routePath', {
            templateUrl: function($routeParams){return '/backend/view/'+$routeParams.routePath+'.html';},
            controller: 'routeBasicsCtl'
        })
        .when('/backend/add/:routePath', {
            templateUrl: function($routeParams){return '/backend/view/edit'+$routeParams.routePath.substr(0,1).toUpperCase()+$routeParams.routePath.substr(1)+'.html';},
            controller: 'routeEditBasicsCtl'
        })
        .when('/backend/edit/:routePath/:id', {
            templateUrl: function($routeParams){return '/backend/view/edit'+$routeParams.routePath.substr(0,1).toUpperCase()+$routeParams.routePath.substr(1)+'.html';},
            controller: 'routeEditBasicsCtl'
        })
}]);