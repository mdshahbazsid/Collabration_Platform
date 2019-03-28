/**
 * 
 */
app.factory('NotificationService',function($http){
	
	var notificationService = {}
	var BASE_URL = "http://localhost:8081/BlogroomMiddlewhere"
	
	notificationService.getNotification = function(id)
	{
		var url = BASE_URL + "/getnotification/"+id
		return $http.get(url)
	}
	
	notificationService.getListOfNotificationWhichAreNotViewed = function()
	{
		var url = BASE_URL + "/notifications"
		return $http.get(url)
	
	}
	
	notificationService.updateNotification = function(id)
	{
		var url = BASE_URL + "/updatenotification/"+id
		return $http.get(url)
	}
	
	return notificationService;
})