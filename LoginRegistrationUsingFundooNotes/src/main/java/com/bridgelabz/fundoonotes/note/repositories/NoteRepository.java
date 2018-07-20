package com.bridgelabz.fundoonotes.note.repositories;

import org.springframework.stereotype.Repository;
import com.bridgelabz.fundoonotes.note.models.Note;
import com.bridgelabz.fundoonotes.note.models.NoteViewDTO;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

@Repository
public interface NoteRepository extends MongoRepository<Note, String> {
	
	public List<NoteViewDTO> findAllByuserId(String userId);

}
