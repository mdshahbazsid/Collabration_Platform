/**
 * 
 */
app.factory('UserService',function($http){
	
	var userService = {}
	var BASE_URL ="http://localhost:8081/BlogroomMiddlewhere"
		
		userService.registeration = function(user)
		{
			var url = BASE_URL + "/register"
			return $http.post(url,user)
		}
	
		userService.login = function(user)
		{
			var url = BASE_URL + "/login"
			return $http.put(url,user)
		}
		
		userService.logout = function()
		{
			var url = BASE_URL + "/logout"
			return $http.put(url)
		}
		
		userService.updateProfile = function(user)
		{
			var url = BASE_URL + "/updateprofile"
			return $http.put(url,user)
		}
	return userService;
})