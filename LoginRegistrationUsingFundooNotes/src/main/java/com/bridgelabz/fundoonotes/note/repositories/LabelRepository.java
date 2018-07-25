package com.bridgelabz.fundoonotes.note.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.bridgelabz.fundoonotes.note.models.Label;

@Repository
public interface LabelRepository extends MongoRepository<Label, String> {
	
	public Label findByUserIdAndName(String userId, String name);
}