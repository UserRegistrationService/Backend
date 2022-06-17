package com.user.registration.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.user.registration.entity.User;

public interface UserRepository extends CrudRepository<User, Long> {

	Optional<User> findByEmailId(String emailId);
}
