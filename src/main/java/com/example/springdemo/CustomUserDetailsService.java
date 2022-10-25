package com.example.springdemo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Transactional
@Service
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository userRepo;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepo.findByUsername(username);
		if (user == null) {
			throw new UsernameNotFoundException("User not found");
		}
		return new CustomUserDetails(user);
	}
	public static final int MAX_FAILED_ATTEMPTS = 3;

	private static final long LOCK_TIME_DURATION = 300;

	public void increaseFailedAttempts(User user) {
		int newFailAttempts = user.getFailedAttempt() + 1;
		userRepo.updateFailedAttempts(newFailAttempts, user.getUsername());
	}

	public void resetFailedAttempts(String username) {
		userRepo.updateFailedAttempts(0, username);
	}

	public void lock(User user) {
		user.setAccountNonLocked(false);
		user.setLockTime(new Date());

		userRepo.save(user);
	}

	public boolean unlockWhenTimeExpired(User user) {
		long lockTimeInMillis = user.getLockTime().getTime();
		long currentTimeInMillis = System.currentTimeMillis();

		if (lockTimeInMillis + LOCK_TIME_DURATION < currentTimeInMillis) {
			user.setAccountNonLocked(true);
			user.setLockTime(null);
			user.setFailedAttempt(0);

			userRepo.save(user);

			return true;
		}

		return false;
	}

	public User getUserByUsername(String username) throws UsernameNotFoundException{
		User user = userRepo.findByUsername(username);
		if (user == null) {
			throw new UsernameNotFoundException("User not found");
		}
		return user;
	}
}
