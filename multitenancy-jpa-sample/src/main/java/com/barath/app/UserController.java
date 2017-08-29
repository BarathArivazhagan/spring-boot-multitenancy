package com.barath.app;

import java.util.List;
import java.util.Optional;

import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
	
	private UserService userService;
	
	public UserController(UserService userService) {
		this.userService=userService;
	}
	
	
	@PostMapping("/create")
	public User createUser(@RequestParam String userName){
		
		Optional<User> user=Optional.ofNullable(userService.saveUser(new User(userName)));
		if( user.isPresent()){
			return user.get();
		}
		
		return null;
			
	}
	
	@GetMapping("/get")
	public User getUser(@RequestParam String userName){		
		
		Optional<User> user= Optional.ofNullable(userService.getUser(userName));
		if( user.isPresent()){
			return user.get();
		}
		return null;
	}
	
	@GetMapping("/getUsers")
	public List<User> fetchUsers(){
		
		return userService.getUsers();
	}
	
	

}
