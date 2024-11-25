package com.springboot.service;

import com.springboot.entity.User;

public interface UserService 
{
	public User saveUser(User user);
	
	public boolean existEmailCheck(String email);
}
