package com.example.springdemo.model;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;
//import java.util.Set;


@Entity
@Table(name = "users")
public class User {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false, unique = true, length = 45)
	private String username;
	
	@Column(nullable = false, length = 64)
	private String password;

	private boolean enabled;

	@ManyToMany(cascade = CascadeType.PERSIST,fetch= FetchType.EAGER)
	@JoinTable(
			name = "user_roles",
			joinColumns = {@JoinColumn(name = "fk_user_id", referencedColumnName = "id")},
			inverseJoinColumns = {@JoinColumn(name = "fk_role_id", referencedColumnName = "id" )}
	)
	private Set<Role> roles;
	//private Set<Role> roles = new HashSet<>();

	@Column(name = "account_non_locked")
	private boolean accountNonLocked;

	@Column(name = "failed_attempt")
	private int failedAttempt;

	@Column(name = "lock_time")
	private Date lockTime;

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

	public Date getLockTime() {
		return lockTime;
	}

	public void setLockTime(Date lockTime) {
		this.lockTime = lockTime;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
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
