package bean;

public class SignUp {
private String name,username,password,emailid,statename,cityname;

public SignUp(String name, String username, String password, String emailid, String statename, String cityname) {
	super();
	this.name = name;
	this.username = username;
	this.password = password;
	this.emailid = emailid;
	this.statename = statename;
	this.cityname = cityname;
}

public String getStatename() {
	return statename;
}

public void setStatename(String statename) {
	this.statename = statename;
}

public String getCityname() {
	return cityname;
}

public void setCityname(String cityname) {
	this.cityname = cityname;
}

public String getName() {
	return name;
}

public void setName(String name) {
	this.name = name;
}

public SignUp(String name, String username, String emailid) {
	super();
	this.name = name;
	this.username = username;
	this.emailid = emailid;
}

public String getUsername() {
	return username;
}

public void setUsername(String username) {
	this.username = username;
}

public String getPassword() {
	return password;
}

public void setPassword(String password) {
	this.password = password;
}

public String getEmailid() {
	return emailid;
}

public void setEmailid(String emailid) {
	this.emailid = emailid;
}

@Override
public String toString() {
	return "SignUp [name=" + name + ", username=" + username + ", password=" + password + ", emailid=" + emailid + "]";
}

public SignUp(String name, String username, String password, String emailid) {
	super();
	this.name = name;
	this.username = username;
	this.password = password;
	this.emailid = emailid;
}

public SignUp() {
	super();
	// TODO Auto-generated constructor stub
}

}
