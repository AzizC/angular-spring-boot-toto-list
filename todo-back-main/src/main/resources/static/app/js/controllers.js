myApp.controller("RootController", ['$scope','$rootScope', '$http', '$location','Email',
  function($scope,$rootScope, $http, $location,Email){
    var classMap = {
      "A faire": "danger",
      "En cours": "warning",
      "Fait": "success"
    };

    $scope.getBackgroundColour = function(stat) {
      return classMap[stat];
    };
    
   

    	  var authenticate = function(credentials, callback) {

    	    var headers = credentials ? {authorization : "Basic "
    	        + btoa(credentials.email + ":" + credentials.password)
    	    } : {};

    	    $http.get('/users/user', {headers : headers}).then(function(response) {
    	      if (response.data.name) { 
    	    	var user = Email.get({email: response.data.name}, function() {
    	    	      $rootScope.userId = user.id;
                });

    	        $rootScope.authenticated = true;
    	      } else {
    	        $rootScope.authenticated = false;
    	      }
    	      callback && callback();
    	    }, function() {
    	      $rootScope.authenticated = false;
    	      callback && callback();
    	    });
    	  }

    	  authenticate();
    	  $scope.credentials = {};
    	  $scope.login = function() { 
    	      authenticate($scope.credentials, function() {
    	        if ($rootScope.authenticated) {
    	          $location.path("/");
    	          $scope.error = false;
    	        } else {
    	          $location.path("/login");
    	          $scope.error = true;
    	        }
    	      });
    	  };
    	  
    	  $scope.logout = function() {
			  $http.post('logout', {}).finally(function() {
				$rootScope.userEmail = '';
			    $rootScope.authenticated = false;
			    $location.path("/");
			  });
			}
    
  }]);

myApp.controller("TodoDetailCtrl", ['$scope', 'Todo', '$routeParams', '$window', '$rootScope',
  function($scope,Todo,$routeParams,$window,$rootScope){
	
	//---------------------REDIRECT--------------------------
	if(!$rootScope.authenticated){
		$window.location.href = '#/login'; //redirect 
	}
	//-------------------------------------------------
	
    $scope.getTodo = function(){
      $scope.todo = Todo.get({id: $routeParams.todoId}, function() {
      
      });
    } 

    $scope.getTodo();
  }]);

myApp.controller("ListController", ['$scope', 'TodoUser', 'TodoStat', '$rootScope','$window',
  function($scope,TodoUser, TodoStat,$rootScope,$window){
	//---------------------REDIRECT--------------------------
	if(!$rootScope.authenticated){
		$window.location.href = '#/login'; //redirect 
	}
	//-------------------------------------------------
	
	console.log($rootScope.userId);
    $scope.todos = TodoUser.query({id:$rootScope.userId}, function() {
        
    });
    		

    $scope.display = "Tous";
    $scope.toDisplay = function(){
        if($scope.display != "Tous"){
          $scope.todos = TodoStat.query({id:$rootScope.userId,stat: $scope.display}, function() {
      
                      });
        } else {
          $scope.todos = TodoUser.query({id:$rootScope.userId}, function() {
              
          });
        }
    };
  }]);

myApp.controller("AddController", ['$scope', 'Todo', '$window', '$rootScope',
  function($scope,Todo,$window,$rootScope){

	//---------------------REDIRECT--------------------
	if(!$rootScope.authenticated){
		$window.location.href = '#/login'; //redirect 
	}
	//-------------------------------------------------
	
    $scope.init = function(){
       $scope.todo = new Todo();
       $scope.todo.stat = 'A faire';
       $scope.todo.idUser = $rootScope.userId;
    }

    $scope.init();

    $scope.addTodo = function(){
    	
    	
      $scope.todo.$save(function(){
        //$scope.todos.push($scope.todo);
         $scope.todo = new Todo();
         $window.location.href = '#/todos'; //redirect 
      });
    };
    
    $scope.cancel = function(){
       $scope.init();
    }

  }]);

myApp.controller("DelController", ['$scope', 'Todo', 
  function($scope,Todo){
    $scope.delTodo = function(todo){
      todo.$remove(function(){
        $scope.todos.splice($scope.todos.indexOf(todo), 1);
      });
    };
    
  }]);

myApp.controller("UpdtController", ['$scope', 'Todo',
  function($scope,Todo){
    $scope.updtTodo = function(){
      $scope.todo.$update(function(){

      });
    };

    $scope.cancel = function(){
      $scope.getTodo();
    }
  }]);


myApp.controller("RegisterController", ['$scope','User','$window', '$rootScope',
  function($scope,User,$window,$rootScope){

	//---------------------REDIRECT--------------------
	if($rootScope.authenticated){
		$window.location.href = '#/todos'; //redirect 
	}
	//-------------------------------------------------
	
      init = function(){
         $scope.user = new User();
      }

      init();

      $scope.addUser = function(){
        $scope.user.$save(function(){
        	$window.location.href = '#/login'; //redirect
        	init();
        });
      };
      
      $scope.cancel = function(){
         init();
      }
  }]);

myApp.controller("LoginController", ['$rootScope','$window', 
  function($rootScope,$window){
	//---------------------REDIRECT--------------------
	if($rootScope.authenticated){
		$window.location.href = '#/todos'; //redirect 
	}
	//-------------------------------------------------

}]);