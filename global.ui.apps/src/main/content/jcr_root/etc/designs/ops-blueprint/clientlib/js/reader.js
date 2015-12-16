var app = angular.module('opsblue', [ 'ui.bootstrap']);

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

		
		/*Function for Card Details */
		$scope.cardDetails = function(){
			var cardData = [];
			for(var i = 0 ; i < $scope.data.application.applicationParticipants.length; i++){
				
				for( j=0 ; j< $scope.data.application.applicationParticipants[i].sofp.creditCardLiabilities.length ; j++) {
				if( $scope.data.application.applicationParticipants[i].sofp.creditCardLiabilities[j].creditCardTypeID == 2) {
					
					cardData.push($scope.data.application.applicationParticipants[i].sofp.creditCardLiabilities[j])
				
				}
				}
			}
				return cardData;

		};
	/*Function for Loan Details */
	$scope.loanDetails = function(){
			var loanData = [];
			for(var i = 0 ; i < $scope.data.application.applicationParticipants.length; i++){
				
				for( j=0 ; j< $scope.data.application.applicationParticipants[i].sofp.loanLiabilities.length ; j++) {
				if( $scope.data.application.applicationParticipants[i].sofp.loanLiabilities[j].loanTypeID == 4) {
					
					loanData.push($scope.data.application.applicationParticipants[i].sofp.loanLiabilities[j])
				
				}
				}
			}
				return loanData;

		};
		
		/*Function for employmetn category */
		$scope.employmentCategory = function(isCurrent,isPrimary) {
			if(isCurrent === true && isPrimary === true) {
			return "Primary";
			} else if (isCurrent === true && isPrimary === false){
				
				return "Additional";
			} else if(isCurrent === false && isPrimary === false) {
				
				return "Previous";
			}
			
	};

      	/* Spouse Jointly Signed  */
		$scope.isSpouseJointly = function(){
			var spouseSigned = "";
						
			if($scope.data.application.applicationParticipants.length > 1 ) {
				/* To do logic to be Implemented */
				spouseSigned = true;
							
			} else {

			  spouseSigned = false;
				
			}
			
			return spouseSigned;

		};


      	$scope.returnRadioValue = function(flag){ 
	if(flag === true) {	
		return "Yes";
	} else {
		return "No";
		}
	};

      /* THis is for enabling check box */
	$scope.broker = true;
	$scope.Customer = true;
	$scope.Financial = true;
	$scope.Security = true;
	$scope.Solictors = true;
	$scope.OFI = true;	
	$scope.Document = true;
	$scope.Loan = true;
	$scope.Account = true;
	$scope.Compliance = true;
	$scope.Employment= true;
		
		
    /* Start for Static Data  To be moved to ajax call Later */

    $http.get('/apps/opsBluePrint/components/global/readerPage/readerJson.json').
    success(function(data, status, headers, config) {
      $scope.staticData = data;
    }).
    error(function(data, status, headers, config) {
      // log error
    });

      /* End for Controller */
    }); 


	app.controller('accordController', ['$scope', function (scope) {
		      scope.$parent.isopen = (scope.$parent.default === scope.item);

                scope.$watch('isopen', function (newvalue, oldvalue, scope) {
                    scope.$parent.isopen = newvalue;
                });

            }]);