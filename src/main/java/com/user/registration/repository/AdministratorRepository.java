package com.user.registration.repository;

import org.springframework.data.repository.CrudRepository;

import com.user.registration.entity.User;

public interface AdministratorRepository extends CrudRepository<User, Long> {
   
}
