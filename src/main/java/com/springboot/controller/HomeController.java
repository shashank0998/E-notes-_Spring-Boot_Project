package com.springboot.controller;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.entity.User;
import com.springboot.service.UserService;

import jakarta.servlet.http.HttpSession;

@Controller
public class HomeController 
{	
	@Autowired
	private UserService userService;
	
	
	@GetMapping("/")
	public String index()
	{
		return "index";
	}
	
	
	@GetMapping("/register")
	public String register()
	{
		return "register";
	}
	
	@PostMapping("/saveUser")
	public String saveUser(@ModelAttribute User user, HttpSession session)
	{
		System.out.println(user);
		
		boolean fun = userService.existEmailCheck(user.getEmail());
		
		if(fun)
		{
			session.setAttribute("msg", "email already exist");
		}
		else
		{
			User saveUser = userService.saveUser(user);
			if(saveUser != null)
			{
				session.setAttribute("msg", "register success");
			}
			else
			{
				session.setAttribute("msg", "something wrong on server");
			}
		}
		
		
		return "redirect:/register";
	}
	
	@GetMapping("/signin")
	public String login()
	{
		return "login";
	}
	
	
	
	
}
