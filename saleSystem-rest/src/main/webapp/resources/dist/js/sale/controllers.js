
app.controller("loginCtrl",['$scope','$location','LoginService',function($scope,$location,LoginService){
	  var form = {};
      $scope.form = form;

	$scope.login = function(isValid){
		if(isValid) {
            LoginService.login($scope.form).then(function(data){
                if(data.hasLogin){
                    $location.path("my");
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
	}
}]);

