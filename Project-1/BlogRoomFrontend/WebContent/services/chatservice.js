/**
 * 
 */

app.directive('ngFocus',function(){
	return function(scope,element,attrs){
		element.bind('click',function(){
			$('.' + attrs.ngFocus)[0].focus();
		});
	};
});

app.factory('ChatService',function($rootScope){
	
	var socket = new SockJS("/BlogroomMiddlewhere/chatmodule")
	var stompClient = Stomp.over(socket);
	console.log(stompClient)
	
	stompClient.connect('','',function(frame){
		alert('in connect function in Service')
		$rootScope.$broadcast('sockConnected',frame)
	})
	return{
		stompClient : stompClient
	}
})