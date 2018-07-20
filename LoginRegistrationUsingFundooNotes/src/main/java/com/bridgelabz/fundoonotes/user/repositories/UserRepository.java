package com.bridgelabz.fundoonotes.user.repositories;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import com.bridgelabz.fundoonotes.user.models.User;

/**
 *
 * @author Bijaya Laxmi Senapati
 * @since 10/07/2018
 * @version 1.0
 *
 */
@Repository
public interface UserRepository extends MongoRepository<User, String> {

	public Optional<User> findByEmail(String email);

}
