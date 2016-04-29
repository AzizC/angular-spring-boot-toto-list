'use strict';

/* App Module */

var myApp = angular.module('MyApp', ['MyAppService', 'ngRoute', 'ngMessages']);


myApp.config(['$routeProvider', '$httpProvider',
  function($routeProvider, $httpProvider) {
    $routeProvider.
    when('/', {
        templateUrl: 'partials/welcome.html'
      }).
      when('/todos', {
        templateUrl: 'partials/todos.html',
        controller: 'ListController'
      }).
      when('/todos/:todoId', {
        templateUrl: 'partials/todo.html',
        controller: 'TodoDetailCtrl'
      }).
      when('/todoadd', {
        templateUrl: 'partials/todo_add.html',
        controller: 'AddController'
      }).
      when('/register', {
        templateUrl: 'partials/insc/form.html',
        controller: 'RegisterController'
      }).
      when('/login', {
        templateUrl: 'partials/insc/login.html',
        controller: 'LoginController'
        	
      }).
      otherwise({
        redirectTo: '/'
      });

      $httpProvider.defaults.headers.common["X-Requested-With"] = 'XMLHttpRequest';
  }]);


  myApp.directive('unicityEmail', ["UserEmail", function (UserEmail) {
      return {
           restrict: 'A',
           require: 'ngModel',
           link: function (scope, element, attrs, ngModel) {
        	   
               scope.$watch(attrs.ngModel, function(value) {
                   UserEmail.isUnique(value)
                              .then(function(data) {
                                   ngModel.$setValidity('unicityEmail',data.data.length==0);
                              })
                              .catch(function() {
                                   //ngModel.$setValidity('unicityEmail', false);
                              });
               });
           }
      };
  }]);



  myApp.directive('uniqueMail', ['Email', function (Email) {
    
       return {
           restrict: 'A', // S'utilise uniquement en tant qu'attribut
           require: 'ngModel',
           link: function (scope, elem, attrs, control) {
               var check = function () {

                var email = scope.$eval(attrs.ngModel);
                console.log(email) ;
                    if(email){
                     var mail = Email.get({email: email}, function() {
                          console.log('entre');
                     });
                   }

                   return mail == null;

           };

           scope.$watch(check, function (isValid) {
               // Défini si le champ est valide
               control.$setValidity("uniqueMail", isValid);
           });
       }
   };
}]);



myApp.directive('equalsTo', [function () {
       /*
       * <input type="password" ng-model="Password" />
       * <input type="password" ng-model="ConfirmPassword" equals-to="Password" />
       */
       return {
           restrict: 'A', // S'utilise uniquement en tant qu'attribut
           scope: true,
           require: 'ngModel',
           link: function (scope, elem, attrs, control) {
               var check = function () {
               //Valeur du champs courant 
               var v1 = scope.$eval(attrs.ngModel); // attrs.ngModel = "ConfirmPassword"
               //valeur du champ à comparer
               var v2 = scope.$eval(attrs.equalsTo).$viewValue; // attrs.equalsTo = "Password"
               return v1 == v2;
           };

           scope.$watch(check, function (isValid) {
               // Défini si le champ est valide
               control.$setValidity("equalsTo", isValid);
           });
       }
   };
}]);