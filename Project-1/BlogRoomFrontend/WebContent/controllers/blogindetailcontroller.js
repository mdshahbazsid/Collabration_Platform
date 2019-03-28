/**
 * 
 */
app.controller('BlogInDetailCtrl',function($scope,$location,BlogPostService,$routeParams){
	var blogId = $routeParams.blogId
	$scope.showTextArea=false
	
	BlogPostService.getBlog(blogId).then(
			function(response)
			{
				$scope.blogPost = response.data
			},
			function(response)
			{
				if(response.status==401)
					$location.path('/login')
			})
			
	BlogPostService.getBlogWaitingForApproval(blogId).then(
			function(response)
			{
				$scope.blogPost = response.data
			},
			function(response)
			{
				if(response.status==401)
					$location.path('/login')
			})
			
			
	$scope.approveBlog = function(blogPost){		
	BlogPostService.approveBlog(blogPost).then(
			function(response)
			{
				alert('Blog Approved Successfully')
				$location.path('/blogswaitingforapproval')
			},
			function(response)
			{
				if(response.status==401)
					$location.path('/login')
			})
	}
	
	$scope.rejectBlog = function(blogPost){
		BlogPostService.rejectBlog(blogPost,$scope.rejectionReason).then(
				function(response)
				{
					alert('Blog Rejected')
					$location.path('/blogswaitingforapproval')
				},
				function(response)
				{
					if(response.status==401)
						$location.path('/login')
				})
		
	}
	
	$scope.confirmation = function(){
		$scope.showTextArea = !$scope.showTextArea
	}
	
	$scope.addBlogComment = function(blog,commentTxt){
		if(commentTxt==undefined||commentTxt=="")
			$scope.error = 'Please enter some Comment..'
			else
		BlogPostService.addBlogComment(blog,$scope.commentTxt).then(
				function(response)
				{
					alert("Comment added Successfuly")
					$scope.commentTxt = ""
					$scope.error = ""
					$scope.blogComment = response.data
				},
				function(response)
				{
					if(response.status==401)
						$location.path('/login')
				})	
	}
	
	$scope.getComments = function(blogId){
		BlogPostService.getComments(blogId).then(
				function(response)
				{
					$scope.blogComments = response.data
				},
				function(response)
				{
					if(response.status==401)
						$location.path('/login')
				})
	}
	
	BlogPostService.hasUserLikedPost(blogId).then(
			function(response)
			{
				if (response.data == '')
				{
					$scope.isLiked = false// glyphicon in black color
				}
				else 
				{
					$scope.isLiked = true // glyphicon in blue color
				}
			}, 
			function(response)
				{
					if(response.status==401)
						$location.path('/login')
				})

	$scope.updateLikes=function(blogId){
		BlogPostService.updateLikes(blogId).then(
				function(response)
				{
					$scope.blogPost=response.data//blogpost object with updated likes
					//update blogpost set likes=likes - 1 where id=? -> blogPostlikes != null, icon in blue color
					    //or
					//update blogpost set likes = likes + 1 where id=? -> blogPostlikes=null , glyphicon in black
					//if isLiked=false, -> isLiked=true
					//if isLiked=true,isLiked=false
					$scope.isLiked=!$scope.isLiked
				},
				function(response)
				{
					if (response.status == 401)
						$location.path('/login')
				})
	}
})