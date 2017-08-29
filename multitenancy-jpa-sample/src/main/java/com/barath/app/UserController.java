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
	public Optional<User> createUser(@RequestParam String userName){
		
		return Optional.ofNullable(userService.saveUser(new User(userName)));
	}
	
	@GetMapping("/get")
	public Optional<User> getUser(@RequestParam String userName){		
		
			return Optional.ofNullable(userService.getUser(userName));
		
	}
	
	@PostMapping("/getUsers")
	public List<User> fetchUsers(){
		
		return userService.getUsers();
	}
	
	

}
