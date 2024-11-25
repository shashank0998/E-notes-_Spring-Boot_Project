package com.springboot.controller;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.springboot.entity.Notes;
import com.springboot.entity.User;
import com.springboot.repository.UserRepository;
import com.springboot.service.NotesService;

import jakarta.servlet.http.HttpSession;
import jakarta.websocket.Session;

@Controller
@RequestMapping("/user")
public class UserController 
{
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private NotesService notesService;
	
	
	@ModelAttribute
	public User getUser(Principal p, Model m)
	{
		String email = p.getName();
		User user = userRepo.findByEmail(email);
		m.addAttribute("user",user);
		return user;
	}
	
	
	@GetMapping("/addNotes")
	public String addNotes()
	{
		return "add_notes";
	}
	
	@GetMapping("/viewNotes")
	public String viewNotes(Model m,Principal p)
	{
		User user = getUser(p, m);
		List<Notes> notesList = notesService.getNotesByUser(user);
		m.addAttribute("notesList",notesList);
		return "view_notes";
	}
	
	@GetMapping("/editNotes/{id}")
	public String editNotes(@PathVariable int id, Model m)
	{
		Notes notesId = notesService.getNotesById(id);
		m.addAttribute("n",notesId);
		return "edit_notes";
	}
	
	@PostMapping("/saveNotes")
	public String saveNotes(@ModelAttribute Notes notes, HttpSession session, Principal p, Model m)
	{
		notes.setDate(LocalDate.now());
		notes.setUser(getUser(p, m));
		
		Notes saveNotes = notesService.saveNotes(notes);
		
		if(saveNotes != null)
		{
			session.setAttribute("msg", "Notes save success");
		}
		else
		{
			session.setAttribute("msg", "Something wrong on server");
		}
		return "redirect:/user/addNotes";
	}
	
	
	@PostMapping("/updateNotes")
	public String updateNotes(@ModelAttribute Notes notes, HttpSession session, Principal p, Model m)
	{
		notes.setDate(LocalDate.now());
		notes.setUser(getUser(p, m));
		
		Notes saveNotes = notesService.saveNotes(notes);
		
		if(saveNotes != null)
		{
			session.setAttribute("msg", "Notes are updated");
		}
		else
		{
			session.setAttribute("msg", "Something wrong on server");
		}
		return "redirect:/user/viewNotes";
	}
	
	@GetMapping("/deleteNotes/{id}")
	public String deleteNotes(@PathVariable int id, Model m, HttpSession session)
	{
		boolean deleteNotes = notesService.deleteNotes(id);
		if(deleteNotes)
		{
			session.setAttribute("msg", "Delete notes success");
		}
		else
		{
			session.setAttribute("msg", "Semothing wrong on server");
		}
		return "redirect:/user/viewNotes";
	}
}
