package com.user.registration.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.user.registration.entity.SuspendedUser;


public interface SuspendedUserRepository extends CrudRepository<SuspendedUser, Long> {

	Optional<SuspendedUser> findByEmailId(String emailId);
}
