package com.example.springdemo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Long> {
	@Query("SELECT u FROM User u WHERE u.username = ?1")
	public User findByUsername(String username);

	@Query("UPDATE User u SET u.failedAttempt = ?1 WHERE u.username = ?2")
	@Modifying
	public void updateFailedAttempts(int failedAttempts, String username);
	
}
