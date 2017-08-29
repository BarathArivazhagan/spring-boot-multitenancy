package com.barath.app;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class UserService {
	
	private UserRepository userRepo;
	
	public UserService(UserRepository userRepo){
		this.userRepo=userRepo;
	}
	
	public User saveUser(User user){
		
		if(!isUserExists(user.getUserName())){
			userRepo.save(user);
		}
		
		return getUser(user.getUserName());
	}
	
	
	public User getUser(String userName){
		
		return userRepo.findByUserName(userName);
	}
	
	
	public List<User> getUsers(){
		
		return userRepo.findAll();
	}
	
	public boolean isUserExists(Long userId){
		
		return userRepo.getOne(userId) !=null ? true : false;
	}
	
	public boolean isUserExists(String userName){
		
		return userRepo.findByUserName(userName) !=null ? true : false;
	}

}
