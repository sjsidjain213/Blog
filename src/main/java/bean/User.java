package bean;

public class User {
private String name,emailid,username,tags;

public String getName() {
	return name;
}

public void setName(String name) {
	this.name = name;
}

public String getEmailid() {
	return emailid;
}

public void setEmailid(String emailid) {
	this.emailid = emailid;
}

public String getUsername() {
	return username;
}

public void setUsername(String username) {
	this.username = username;
}

public String getTags() {
	return tags;
}

public void setTags(String tags) {
	this.tags = tags;
}

public User(String name, String emailid, String username, String tags) {
	super();
	this.name = name;
	this.emailid = emailid;
	this.username = username;
	this.tags = tags;
}

public User() {
	super();
}



}
