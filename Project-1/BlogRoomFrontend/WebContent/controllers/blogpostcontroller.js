/**
 * 
 */
app.controller('BlogPostCtrl',function($scope,$location,BlogPostService,$rootScope){
	
	$scope.addBlogPost = function(blogPost){
		BlogPostService.addBlogPost(blogPost).then(
				function(response)
				{
					alert('Blog posted Successfully and Waiting for Approval')
					$location.path('/home')
				},
				function(response)
				{
					if(response.status==401)
						$location.path('/login')
					$scope.error = response.data
				})
		}
	
		function getBlogsApproved(){
			BlogPostService.getApprovedBlogs().then(
					function(response)
					{
						$scope.listOfBlogsApproved = response.data
					},
					function(response)
					{
						if(response.status==401)
							$location.path('/login')
					})
				}

		function getBlogsWaitingForApproval(){
			BlogPostService.getBlogsWaitingForApproval().then(
					function(response)
					{
						$scope.listOfBlogsWaitingForApproval = response.data
					},
					function(response)
					{
						if(response.status==401)
							$location.path('/login')
					})
				}
		
		getBlogsApproved()
		
		if($rootScope.user.role=='ADMIN')
			getBlogsWaitingForApproval()
		
})