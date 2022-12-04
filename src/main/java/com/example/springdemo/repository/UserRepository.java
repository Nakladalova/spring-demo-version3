package com.example.springdemo.repository;

import com.example.springdemo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long>, CustomUserRepository {
	@Query("SELECT u FROM User u WHERE u.username = ?1")
	public User findByUsername(String username);

	@Query("UPDATE User u SET u.failedAttempt = ?1 WHERE u.username = ?2")
	@Modifying
	public void updateFailedAttempts(int failedAttempts, String username);

	Optional<User> findUserById(String username);
}
