package com.hcl.util;

public class User {
private String Username;
private String Email;
	public String getUsername() {
	return Username;
}
public void setUsername(String username) {
	Username = username;
}
public String getEmail() {
	return Email;
}
public void setEmail(String email) {
	Email = email;
}
@Override
public String toString() {
	return "User [username=" + Username + ", email=" + Email + "]";
}

}
