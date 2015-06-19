function People($scope, $http) {
	// For now, delete family
	$http.delete('/family/1');
    $http.get('/people').
        success(function(data) {
            $scope.person = data._embedded.people;
        });
    $scope.removeRow = function(p) {
    	$http.delete(p._links.self.href).
    		success(function(data) {
    			 $http.get('people').
    		        success(function(data) {
    		            $scope.person = data._embedded.people;
    		        });
    		});
    	/*var index = -1;
    	var pArr = eval( $scope.person );
    	for () {
    		
    	}
    	if ( index == -1) {
    		alert("Something gone wrong");
    	}
    	$scope.person.splice( index, 1);*/
    };
    
}