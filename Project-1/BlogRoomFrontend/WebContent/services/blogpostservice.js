/**
 * 
 */
app.factory('BlogPostService',function($http){
	
	var blogPostService = {}
	var BASE_URL = "http://localhost:8081/BlogroomMiddlewhere"
		
		blogPostService.addBlogPost = function(blogPost)
		{
			var url = BASE_URL + "/addblog"
			return $http.post(url,blogPost)
		}
	
	blogPostService.getApprovedBlogs = function()
	{
		var url = BASE_URL+ "/approvedblogs"
		return $http.get(url)
	}
	
	blogPostService.getBlog = function(blogId)
	{
		var url = BASE_URL+ "/getblog/"+blogId
		return $http.get(url)
	}
	
	blogPostService.getBlogWaitingForApproval = function(blogId)
	{
		var url = BASE_URL+ "/getblog/"+blogId
		return $http.get(url)
	}
	
	blogPostService.getBlogsWaitingForApproval = function()
	{
		var url = BASE_URL+ "/blogswaitingforapproval"
		return $http.get(url)
	}
	
	blogPostService.approveBlog = function(blogPost)
	{
		var url = BASE_URL + "/updateblog"
		return $http.put(url,blogPost)
	}
	
	blogPostService.rejectBlog = function(blogPost,rejectionReason)
	{
		var url = BASE_URL + "/deleteblog?rejectionReason="+rejectionReason
		return $http.put(url,blogPost)
	}
	
	blogPostService.getListOfNotificationWhichAreNotViewed = function()
	{
		var url = BASE_URL + "/notifications"
		return $http.get(url)
	}
	
	blogPostService.addBlogComment = function(blog,commentTxt)    //blog is BlogPost object
	{
		var url = BASE_URL + "/addcomment?commentTxt="+commentTxt
		return $http.post(url,blog)
	}
	
	blogPostService.getComments = function(blogId)
	{
		var url = BASE_URL + "/getcomments/"+blogId
		return $http.get(url)
	}
	
	blogPostService.hasUserLikedPost=function(blogId)
	{
    	return $http.get(BASE_URL + "/haspostliked/"+blogId);//response.data -> BlogPostLikes [1 / null]
    }
	
    //when glyphicon-thumps-up is clicked
    blogPostService.updateLikes=function(blogId)
    {
    	return $http.put(BASE_URL + "/updatelikes/"+blogId);//response.data -> BlogPost object with updated likes
    	// http://localhost:9090/co..../updatelikes/735
    }
	
	return blogPostService;
})