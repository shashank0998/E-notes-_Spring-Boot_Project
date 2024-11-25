package com.springboot.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springboot.entity.Notes;
import com.springboot.entity.User;

public interface NotesRepository extends JpaRepository<Notes, Integer>
{
	public List<Notes> findByUser(User user);
}
