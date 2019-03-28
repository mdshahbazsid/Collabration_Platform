/**
 * 
 */
app.controller('FriendCtrl',function($scope,$location,FriendService){
	
	function getSuggestedUsers(){     //because this statement is executed more than one time so we write in as function
	FriendService.getSuggestedUsers().then(
			function(response)
			{
				$scope.suggestedUsers = response.data
			},
			function(response)
			{
				if(response.status==401)
					$location.path('/login')
			})
	}
	
	$scope.sendFriendRequest = function(toId){      // toId is User object we can write user also in place of toId
	FriendService.sendFriendRequest(toId).then(
			function(response)
			{
				alert('Friend Request has been sended..')
				getSuggestedUsers()
			},
			function(response)
			{
				if(response.status==401)
					$location.path('/login')
			})
	}
	
	function getPendingRequest(){
	FriendService.getPendingRequest().then(
			function(response)
			{
				$scope.listOfPendingRequest = response.data
			},
			function(response)
			{
				if(response.status==401)
					$location.path('/login')
			})
	}
	
	$scope.acceptRequest = function(pendingRequest){
	FriendService.acceptRequest(pendingRequest).then(
			function(response)
			{
				alert('Friend Request has been Accepted...')
				getPendingRequest()
				$location.path('/friends')
			},
			function(response)
			{
				if(response.status==401)
					$location.path('/login')
			})
	}
	
	$scope.deleteRequest = function(pendingRequest){
	FriendService.deleteRequest(pendingRequest).then(
				function(response)
				{
					alert('Friend Request has been Deleted...')
					getPendingRequest()
					$location.path('/friends')
				},
				function(response)
				{
					if(response.status==401)
						$location.path('/login')
				})
		}
	
	FriendService.getFriends().then(
			function(response)
			{
				$scope.listOfFriends = response.data
			},
			function(response)
			{
				if(response.status==401)
					$location.path('/login')
			})
	
	getSuggestedUsers()
	getPendingRequest()
})