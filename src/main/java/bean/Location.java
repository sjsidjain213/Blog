package bean;

public class Location {
private String city,state,country;
private int stateid,cityid;
public String getCity() {
	return city;
}

public Location( int cityid,String city) {
	super();
	this.city = city;
	this.cityid = cityid;
}

public int getCityid() {
	return cityid;
}

public void setCityid(int cityid) {
	this.cityid = cityid;
}

public int getStateid() {
	return stateid;
}

public void setStateid(int stateid) {
	this.stateid = stateid;
}

public Location(String state, int stateid) {
	super();
	this.state = state;
	this.stateid = stateid;
}

public void setCity(String city) {
	this.city = city;
}

public String getState() {
	return state;
}

public void setState(String state) {
	this.state = state;
}

public String getCountry() {
	return country;
}

public void setCountry(String country) {
	this.country = country;
}

}
