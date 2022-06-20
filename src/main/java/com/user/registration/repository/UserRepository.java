package com.user.registration.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.user.registration.entity.User;

public interface UserRepository extends CrudRepository<User, Long> {

	Optional<User> findByEmailId(String emailId);
	
	@Query("SELECT u.mobileNumber FROM User u WHERE u.locked = true")
	List<Long> findAllLockedUser();
}
