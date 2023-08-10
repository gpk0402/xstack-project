package com.epam.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.epam.entity.User;

public interface UserRepository extends JpaRepository<User, Integer>{

	Optional<User> findByUsername(String username);

}
