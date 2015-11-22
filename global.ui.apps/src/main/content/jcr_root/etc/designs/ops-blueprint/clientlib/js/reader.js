var app = angular.module('opsblue', [ 'ui.bootstrap']);

/*app.controller('MainCtrl', function($scope,$http) {
 
  $http.get('sample.json').success(function(response){ //request to json file.
  console.info("response" + response);
			$scope.data = response; //Assign data recieved to $scope.data
		})
		.error(function(err){
			//handel error
		});
  */
  
  app.controller('MainCtrl', function ($scope, $http, $location) {  
  
  var searchObject = $location.search();
  console.info("searhdfsd"+searchObject.loanNum);
  var datatosend = searchObject.loanNum;
  var docTypetosend = searchObject.docType;
       $http({  
            url: "/bin/getReaderPage",  
            dataType: 'json',  
            method: 'GET',  
            params: {'loanNum':datatosend, 'docType': docTypetosend},
			/*data: {loanNum: datatosend},*/
			headers: {
				'Content-Type': undefined
			},
             
        }).success(function (response) {  
            $scope.data = response;  
        })  
        .error(function (error) {  
            alert(error);  
        });  
		
		 $http({  
            url: "/apps/opsBluePrint/components/global/readerPage/readerJson.json",  
            dataType: 'json',  
            method: 'POST',  
            /*params: {'loanNum':datatosend},GET*/
			//data: {loanNum: datatosend},
			headers: {
				'Content-Type': undefined
			},
             
        }).success(function (response) {  
            $scope.staticData = response;  
        })  
        .error(function (error) {  
            alert(error);  
        });
    }); 
	

	app.controller('accordController', ['$scope', function (scope) {
		      scope.$parent.isopen = (scope.$parent.default === scope.item);

                scope.$watch('isopen', function (newvalue, oldvalue, scope) {
                    scope.$parent.isopen = newvalue;
                });

            }]);