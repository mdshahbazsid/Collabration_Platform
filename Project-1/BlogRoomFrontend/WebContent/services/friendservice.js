/**
 * 
 */
app.factory('FriendService',function($http){
	
	var friendService = {}
	var BASE_URL = "http://localhost:8081/BlogroomMiddlewhere"
	
	friendService.getSuggestedUsers = function()
	{
		var url = BASE_URL + "/suggestedusers"
		return $http.get(url)
	}
	
	friendService.sendFriendRequest = function(toId)
	{
		var url = BASE_URL + "/friendrequest"
		return $http.post(url,toId)
	}
	
	friendService.getPendingRequest = function()
	{
		var url = BASE_URL + "/pendingrequest"
		return $http.get(url)
	}
	
	friendService.acceptRequest = function(pendingRequest)
	{
		var url = BASE_URL + "/acceptrequest"
		return $http.put(url,pendingRequest)
	}
	
	friendService.deleteRequest = function(pendingRequest)
	{
		var url = BASE_URL + "/deleterequest"
		return $http.put(url,pendingRequest)
	}
	
	friendService.getFriends = function()
	{
		var url = BASE_URL + "/friends"
		return $http.get(url)
	}
	
	return friendService;
})