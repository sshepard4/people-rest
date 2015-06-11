function People($scope, $http) {
    $http.get('http://localhost:8080/people').
        success(function(data) {
            $scope.person = data._embedded.people;
        });
}