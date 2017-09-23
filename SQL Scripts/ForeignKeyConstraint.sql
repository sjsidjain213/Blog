ALTER TABLE Article.BlogData 
ADD CONSTRAINT [FK_Article_BlogData_User_PersonalDetail_UserID] FOREIGN KEY(UserID)
REFERENCES [User].PersonalDetail(UserID)
ON UPDATE CASCADE
ON DELETE CASCADE
GO

----------------------------------------------------------------------------
AlTER TABLE Article.Comment
ADD CONSTRAINT [FK_Article_Comment_User_PersonalDetail_UserID] FOREIGN KEY(UserID)
REFERENCES [User].PersonalDetail(UserID)
GO

-----------------------------------------------------------------------------
ALTER TABLE Article.Comment 
ADD CONSTRAINT [FK_Article_Comment_Article_BlogData_ArticleID] FOREIGN KEY(ArticleID)
REFERENCES Article.BlogData(ArticleID)
ON UPDATE CASCADE
ON DELETE CASCADE
GO

-------------------------------------------------------------------------------
ALTER TABLE Article.GenreJunction 
ADD CONSTRAINT [FK_Article_Genre_Junction_Genre_GenreID] FOREIGN KEY(GenreID)
REFERENCES Article.Genre(GenreID)
ON UPDATE CASCADE
ON DELETE CASCADE
GO

-------------------------------------------------------------------------------
ALTER TABLE Article.GenreJunction 
ADD CONSTRAINT [FK_Article_GenreJunction_Article_BlogData_ArticleID] FOREIGN KEY(ArticleID)
REFERENCES Article.BlogData(ArticleID)
ON UPDATE CASCADE
ON DELETE CASCADE
GO

-----------------------------------------------------------------------------
Alter table [User].PersonalDetail
ADD CONSTRAINT [FK_User_PersonalDetail_Location_State_StateName_CityName] FOREIGN KEY(StateName,CityName)
REFERENCES Location.City (StateName,CityName)
ON UPDATE CASCADE
ON DELETE CASCADE
go


----------------------Upvote and Downvote--------------------------------
Alter table [Article].Upvote
ADD CONSTRAINT [FK_Article_Upvote_User_PersonalDetail_UserID] FOREIGN KEY(UserID)
REFERENCES [User].PersonalDetail (UserID)
ON UPDATE CASCADE
ON DELETE CASCADE
go

Alter table [Article].Upvote
ADD CONSTRAINT [FK_Article_Upvote_User_PersonalDetail_ArticleID] FOREIGN KEY(ArticleID)
REFERENCES [Article].BlogData (ArticleID)
go
--Adding update cascade and delete cascade will create cycle how to avoid it 
Alter table [Article].Downvote
ADD CONSTRAINT [FK_Article_Downvote_User_PersonalDetail_UserID] FOREIGN KEY(UserID)
REFERENCES [User].PersonalDetail (UserID)
ON UPDATE CASCADE
ON DELETE CASCADE
go

Alter table [Article].Downvote
ADD CONSTRAINT [FK_Article_Downvote_User_PersonalDetail_ArticleID] FOREIGN KEY(ArticleID)
REFERENCES [Article].BlogData (ArticleID)
go

