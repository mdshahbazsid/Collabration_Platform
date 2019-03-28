app.factory('JobService',function($http){
	
	var jobService = {}
	var BASE_URL ="http://localhost:8081/BlogroomMiddlewhere"
		
	jobService.addJob = function(job)
	{	
		var url = BASE_URL + "/addjob"
		return $http.post(url,job)
	}
	
	jobService.getAllJobs = function()
	{
		var url = BASE_URL + "/getalljobs"
		return $http.get(url)
	}
	return jobService;
})