package com.example.springdemo.repository;

import com.example.springdemo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional
public interface UserRepository extends JpaRepository<User, Integer>, CustomUserRepository, CrudRepository<User, Integer> {
	@Query("SELECT u FROM User u WHERE u.username = ?1")
	public User findByUsername(String username);

	@Query("SELECT u FROM User u WHERE u.id = ?1")
	public User findByID(int id);


	@Query("UPDATE User u SET u.failedAttempt = ?1 WHERE u.username = ?2")
	@Modifying
	public void updateFailedAttempts(int failedAttempts, String username);

	long deleteByUsername(String username);

	//Optional<User> findUserById(String username);

	//@Query("DELETE FROM User u WHERE u.username = ?1")
	//public void deleteUserByUsername(String username);

}
