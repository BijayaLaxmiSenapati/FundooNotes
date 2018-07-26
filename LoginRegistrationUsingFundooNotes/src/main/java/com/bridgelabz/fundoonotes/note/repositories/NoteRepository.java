package com.bridgelabz.fundoonotes.note.repositories;

import org.springframework.stereotype.Repository;
import com.bridgelabz.fundoonotes.note.models.Note;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author adminstrato
 *
 */
@Repository
public interface NoteRepository extends MongoRepository<Note, String> {
	
	/**
	 * @param userId
	 * @return
	 */
	public List<Note> findAllByuserId(String userId);

}
