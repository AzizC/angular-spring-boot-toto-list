var myAppService = angular.module('MyAppService', ['ngResource']);

myAppService.factory('Todo', ['$resource',
  function($resource){
    return $resource('http://localhost:8080/todos/:id', {id: '@id'}, {
    	update: {
        method: "PUT"
      },
      remove: {
        method: "DELETE"
      }
    });
  }]);



myAppService.factory('TodoUser', ['$resource',
        function($resource){
          return $resource('http://localhost:8080/todos/user/:id', {id: '@id'},{
        	  
          }); 
}]);


myAppService.factory('TodoStat', ['$resource',
  function($resource){
    return $resource('http://localhost:8080/todos/user/:id/stat/:stat', {}, {

    });
  }]);


myAppService.factory('User', ['$resource',
  function($resource){
    return $resource('http://localhost:8080/users/:id', {id: '@id'}, {
      update: {
        method: "PUT"
      },
      remove: {
        method: "DELETE"
      }
    });
  }]);



myAppService.factory('Email', ['$resource',
  function($resource){
    return $resource('http://localhost:8080/users/email/:email', {}, {
    });
  }]);



myAppService.service('UserEmail', ['$q','$http',
  
  function ($q, $http) {

    this.isUnique = function(email) {
       
        if (email) {
            var uri = 'http://localhost:8080/users/email/' + email;
            return $http.get(uri);
        }
 
        return $q.reject("Invalid mail");
    }
   
}]);
