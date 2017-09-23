package bean;

public class Comment {
private String comment,name;
private int aid,uid;
public Comment() {
	super();
	// TODO Auto-generated constructor stub
}
public Comment(String name, String comment) {
	super();
	this.comment = comment;
	this.name = name;
}
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
public Comment(String comment, int aid, int uid) {
	super();
	this.comment = comment;
	this.aid = aid;
	this.uid = uid;
}
public String getComment() {
	return comment;
}
public void setComment(String comment) {
	this.comment = comment;
}
public int getAid() {
	return aid;
}
public void setAid(int aid) {
	this.aid = aid;
}
public int getUid() {
	return uid;
}
public void setUid(int uid) {
	this.uid = uid;
}
}
