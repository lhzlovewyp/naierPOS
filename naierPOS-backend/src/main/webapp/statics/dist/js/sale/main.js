Date.prototype.format = function (format) {
        var o = {
            "M+": this.getMonth() + 1,
            "d+": this.getDate(),
            "h+": this.getHours(),
            "m+": this.getMinutes(),
            "s+": this.getSeconds(),
            "q+": Math.floor((this.getMonth() + 3) / 3),
            "S": this.getMilliseconds()
        }
        if (/(y+)/.test(format)) {
            format = format.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
        }
        for (var k in o) {
            if (new RegExp("(" + k + ")").test(format)) {
                format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k] : ("00" + o[k]).substr(("" + o[k]).length));
            }
        }
        return format;
    }

$("#a_expand").click(function(){
	if($(".main-sidebar").css('display') == 'none'){
		$(".main-sidebar").show();
		$(".content-wrapper > div[ng-view]").css('margin-left','230px');
	}else{
		$(".main-sidebar").hide();
		$(".content-wrapper > div[ng-view]").css('margin-left','');
	}
	
});

var app=angular.module('mainApp',['ngRoute','tm.pagination','ngDialog', 'isteven-multi-select']);

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