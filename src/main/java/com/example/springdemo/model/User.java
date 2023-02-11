package com.example.springdemo.model;

//import com.example.springdemo.validation.ValidUsername;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "users")
public class User {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(nullable = false, unique = true, length = 45)
	//@ValidUsername
	private String username;

	@Column(nullable = false, length = 64)
	//@Pattern(regexp="^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$", message="Must contain at least one number, one uppercase and lowercase letter, one special character and at least 8 or more characters")
	private String password;

	private boolean enabled;

	@ManyToMany(cascade = CascadeType.PERSIST,fetch= FetchType.EAGER)
	@JoinTable(
			name = "user_roles",
			joinColumns = {@JoinColumn(name = "fk_user_id", referencedColumnName = "id")},
			inverseJoinColumns = {@JoinColumn(name = "fk_role_id", referencedColumnName = "id" )}
	)
	private Set<Role> roles;

	private boolean accountNonLocked;

	@Column(name = "failed_attempt")
	private int failedAttempt;

	public long getLockTime() {
		return lockTime;
	}

	public void setLockTime(long lockTime) {
		this.lockTime = lockTime;
	}

	@Column(name = "lock_time")
	private long lockTime;

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	private String active;
	public boolean isEnabled() {
		return enabled;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(nullable = false, unique = true, length = 45)
	private String email;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	private String message;

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public boolean isAccountNonLocked() {
		return accountNonLocked;
	}

	public void setAccountNonLocked(boolean accountNonLocked) {
		this.accountNonLocked = accountNonLocked;
	}

	public int getFailedAttempt() {
		return failedAttempt;
	}

	public void setFailedAttempt(int failedAttempt) {
		this.failedAttempt = failedAttempt;
	}

	public int getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String email) {
		this.username = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}


}
