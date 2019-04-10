/**
 * 
 */
app.controller('ChatCtrl',function($rootScope,$scope,ChatService){
	
	$scope.stompClient = ChatService.stompClient
	$scope.users = [];   //Array of users in chatroom
	$scope.chats = [];    //Array of chats in message
	
	$scope.$on('sockConnected',function(event,frame){
		
		alert('Successfully Connected with Web Socket')
		$scope.userName = $rootScope.user.firstName
		alert($scope.userName +' has Joined the ChatRoom')
		
		$scope.stompClient.subscribe("/app/join/"+$scope.userName,function(message){
			console.log(message.body)
			alert(message.body)   //list of username
			$scope.users = JSON.parse(message.body)   //converting String to Json
			$scope.$apply();   //when user gets updated apply the changes
		})
		
		$scope.stompClient.subscribe("/topic/join",function(message){
			user = JSON.parse(message.body);    //Newly Joined user,String
			if(user != $scope.userName && $.inArray(user,$scope.users) == -1)
			{
				$scope.addUser(user);
				$scope.latestUser = user;
				$scope.$apply();
				alert($scope.latestUser + ' has Joined the Chat')
			}
		})

	})
	
	$scope.addUser = function(user){
		$scope.users.push(user)
		$scope.$apply()
	}
	
	$scope.sendMessage = function(chat){
		chat.from = $scope.userName
		$scope.stompClient.send("/app/chat",{},JSON.stringify(chat))  //stringify converts json format to string
		$rootScope.$broadcast('sendingChat',chat)
		$scope.chat.message=''
	}
	
	$scope.$on('sendingChat',function(event,sentChat){
		chat = angular.copy(sentChat)
		chat.from = 'Me'
		chat.direction = 'outgoing'
		$scope.addChat(chat)
	})
	
	$scope.addChat = function(chat){
		$scope.chats.push(chat)
	}
	
	$scope.$on('sockConnected',function(event,frame){
		$scope.userName = $rootScope.user.firstName;
		
		$scope.stompClient.subscribe("/queue/chats",function(message){
			alert('message'+ message.body)
			$scope.processIncomingMessage(message,true)
		});
		
		$scope.stompClient.subscribe("/queue/chats/"+$scope.userName,function(message){
			alert('message is'+ message.body)
			$scope.processIncomingMessage(message,false)
		})
	})
	
	$scope.processIncomingMessage = function(message,broadcast){
		message = JSON.parse(message.body)
		message.direction = 'incoming'
		if(message.from!=$scope.userName)
		{
			$scope.addChat(message)
			$scope.$apply()
		}
	}
})

