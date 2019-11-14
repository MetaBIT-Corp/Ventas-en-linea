package com.metabit.ventasenlinea.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "user_roles", 
	   uniqueConstraints = @UniqueConstraint(columnNames = {"role","id_user"})
)
public class UserRole {
	
	@Id
	@GeneratedValue
	@Column(name = "user_role_id", unique = true, nullable = false)
	private int userRoleId;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_user", nullable = false)
	private User user;
	
	@Column(name = "role",nullable = false)
	private String role;

	public UserRole(User user, String role) {
		super();
		this.user = user;
		this.role = role;
	}

	public UserRole() {
		super();
	}

	public int getUserRoleId() {
		return userRoleId;
	}

	public void setUserRoleId(int userRoleId) {
		this.userRoleId = userRoleId;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	@Override
	public String toString() {
		return "UserRole [userRoleId=" + userRoleId + ", user=" + user + ", role=" + role + "]";
	}
	
	
}
