package com.springboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springboot.entity.User;

public interface UserRepository extends JpaRepository<User, Integer>
{
	public boolean existsByEmail(String email);
	
	public User findByEmail(String email);
}
