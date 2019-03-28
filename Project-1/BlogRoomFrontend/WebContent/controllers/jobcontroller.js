app.controller('JobCtrl',function($scope,$location,JobService){
	$scope.showJob = false
	$scope.addJob = function(job){
		JobService.addJob(job).then(
				function(response)
				{
					alert('Job Details are inserted Successfully')
					$location.path('/getalljobs')
				},
				function(response)
				{
					if(response.data.errorCode==4)
						$location.path('/login')
					$scope.error = response.data
				})
	}
	
	JobService.getAllJobs().then(
			function(response)
			{
				$scope.listOfJobs = response.data
			},
			function(response)
			{
				if(response.status==401)
					$location.path('/login')
			})
			
	$scope.showJobDetails = function(jobId){
		$scope.id = jobId
		$scope.showJob = !$scope.showJob
	}
})