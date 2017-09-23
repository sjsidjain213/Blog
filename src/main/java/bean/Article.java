package bean;

public class Article {
	
private String title, content,aggregation,name,creationdate;
public String getCreationdate() {
	return creationdate;
}
public void setCreationdate(String creationdate) {
	this.creationdate = creationdate;
}
private String [] genre;

private String genrestring;
private int count,upvote,downvote,id;

public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}

public int getUpvote() {
	return upvote;
}
public void setUpvote(int upvote) {
	this.upvote = upvote;
}
public int getDownvote() {
	return downvote;
}
public void setDownvote(int downvote) {
	this.downvote = downvote;
}

public Article(int upvote, int downvote) {
	super();
	this.upvote = upvote;
	this.downvote = downvote;
}
public int getCount() {
	return count;
}
public void setCount(int count) {
	this.count = count;
}

public String getGenrestring() {
	return genrestring;
}
public void setGenrestring(String genrestring) {
	this.genrestring = genrestring;
}
public String[] getGenre() {
	return genre;
}
public void setGenre(String[] genre) {
	this.genre = genre;
}

public String getName() {
	return name;
}

public void setName(String name) {
	this.name = name;
}

private int aid, uid,rating;

public String getTitle() {
	return title;
}
public void setTitle(String title) {
	this.title = title;
}

public String getContent() {
	return content;
}
public void setContent(String content) {
	this.content = content;
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
public int getRating() {
	return rating;
}
public void setRating(int rating) {
	this.rating = rating;
}


public String getAggregation() {
	return aggregation;
}
public void setAggregation(String aggregation) {
	this.aggregation = aggregation;
}



@Override
public String toString() {
	return "Article [title=" + title + ", content=" + content + ", aid=" + aid + ", uid=" + uid + ", rating=" + rating
			+ "]";
}

public Article() {
	super();
	// TODO Auto-generated constructor stub
}
public Article(String title, String content) {
	super();
	this.title = title;
	this.content = content;
}
public Article(String title, int aid) {
	super();
	this.title = title;
	this.aid = aid;
}
public Article(String title, String content, String name, int aid,String creationdate,String genrestring) {
	super();
	this.title = title;
	this.content = content;
	this.name = name;
	this.aid = aid;
	this.creationdate = creationdate;
    this.genrestring = genrestring;
}
public Article(String title, String content, int aid, int uid, int rating) {
	super();
	this.title = title;
	this.content = content;
	this.aid = aid;
	this.uid = uid;
	this.rating = rating;
}
public Article(String title, String content, String name, String[] genre, int aid) {
	super();
	this.title = title;
	this.content = content;
	this.name = name;
	this.genre = genre;
	this.aid = aid;
}
public Article(String title, String content, String name, String genrestring, int aid) {
	super();
	this.title = title;
	this.content = content;
	this.name = name;
	this.genrestring = genrestring;
	this.aid = aid;
}
public Article(int id,String title, String content, String name, String genrestring, 
		int aid, int upvote, int downvote,String creationdate) {
	super();
	this.title = title;
	this.content = content;
	this.name = name;
	this.genrestring = genrestring;
	this.upvote = upvote;
	this.downvote = downvote;
	this.id = id;
	this.aid = aid;
	this.creationdate = creationdate;
}

public Article(String title, String content, String name) {
	super();
	this.title = title;
	this.content = content;
	this.name = name;
}

public Article(String title,int aid, int upvote, int downvote) {
	super();
	this.title = title;
	this.upvote = upvote;
	this.downvote = downvote;
	this.aid = aid;
}
}
