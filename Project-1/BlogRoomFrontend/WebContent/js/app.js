/**
 * 
 */
var app = angular.module("app",['ngRoute','ngCookies'])
app.config(function($routeProvider){
	$routeProvider
	.when('/register',{templateUrl:'views/Registration.html',controllers:'UserCtrl'})
	.when('/login',{templateUrl:'views/Login.html',controllers:'UserCtrl'})
	.when('/updateprofile',{templateUrl:'views/UserProfile.html',controllers:'UserCtrl'})
	.when('/addjob',{templateUrl:'views/JobForm.html',controllers:'JobCtrl'})
	.when('/getalljobs',{templateUrl:'views/JobList.html',controllers:'JobCtrl'})
	.when('/addblog',{templateUrl:'views/BlogPostForm.html',controllers:'BlogPostCtrl'})
	.when('/getapprovedblogs',{templateUrl:'views/ApprovedBlogList.html',controllers:'BlogPostCtrl'})
	.when('/getblog/:blogId',{templateUrl:'views/BlogsInDetail.html',controllers:'BlogInDetailCtrl'})
	.when('/blogswaitingforapproval',{templateUrl:'views/WaitingForApprovalBlogList.html',controllers:'BlogPostCtrl'})
	.when('/getblogwaitingforapproval/:blogId',{templateUrl:'views/BlogApprovalForm.html',controllers:'BlogInDetailCtrl'})
	.when('/getnotificaton/:id',{templateUrl:'views/NotificationDetails.html',controllers:'NotificationCtrl'})
	.when('/suggestedusers',{templateUrl:'views/SuggestedUsersList.html',controllers:'FriendCtrl'})
	.when('/pendingrequest',{templateUrl:'views/PendingRequest.html',controllers:'FriendCtrl'})
	.when('/friends',{templateUrl:'views/Friends.html',controllers:'FriendCtrl'})
	.when('/chat',{templateUrl:'views/Chat.html',controllers:'ChatCtrl'})
	.when('/uploadprofilepic',{templateUrl:'views/UploadProfilePicture.html'})
	.when('/home',{templateUrl:'views/Home.html',controllers:'HomeCtrl'})
	.otherwise({templateUrl:'views/Home.html'})
	
})
app.run(function($rootScope,$cookieStore,UserService,$location){
	if($rootScope.user == undefined)
		$rootScope.user = $cookieStore.get('userDetails')
		
		$rootScope.logout = function()
		{
			UserService.logout().then(
					function(response)
					{
						delete $rootScope.user
						$cookieStore.remove('userDetails')
						$location.path('/login')
					},
					function(response)
					{
						if($rootScope.user!=undefined)
						delete $rootScope.user
						$cookieStore.remove('userDetails')
						$location.path('/login')
					})
		}
})