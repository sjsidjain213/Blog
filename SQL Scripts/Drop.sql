use blog
go
-------------------------------------------------------------------------------------------------------
/*  objective : is to rename the column UID to UserID in Table [User].[PerosnalDetail] to UserID
    Problems : The UID has been used in various Foreign key contraints and Views, Procedures, Triggers
	Approach : Find all the dependent Foreing key
				Find all the dependent tables views procedure and triggers
*/
-------------------------------------------------------------------------------------------------------
exec sp_rename '[User].[PersonalDetail].UID','UserID','Column'
--result of execution Object '[User].[PersonalDetail].UID' cannot be renamed because the object participates in enforced dependencies.

--How to find Dependecies
exec sp_depends '[User].[PersonalDetail]'
/*name type
dbo.func_HomePage	inline function
dbo.func_HomePageFilter	inline function
dbo.userRegistration	stored procedure
dbo.usp_BlogData_Insert	stored procedure
dbo.usp_BlogData_Insert_Genre	stored procedure
dbo.usp_password_verifier	stored procedure
dbo.usp_username_validation	stored procedure
dbo.vArticleProfile	view
dbo.vComment	view
dbo.vHomePage	view
dbo.vProfile	view
dbo.vSpecificBlog	view
User.trig_User_Insert	trigger
*/
--**Finding all the foreign key constraints
select * from INFORMATION_SCHEMA.REFERENTIAL_CONSTRAINTS

--**Dropping Foreign keys
Alter table Article.BlogData 
drop FK_Article_BlogData_User_PersonalDetail_UID

Alter table Article.Comment 
drop FK_Article_Comment_User_PersonalDetail_UID

Alter table Article.Comment 
drop FK_Article_Comment_Article_BlogData_AID

Alter table Article.GenreJunction
drop FK_Article_Genre_Junction_Genre_GID

Alter table Article.GenreJunction
drop FK_Article_GenreJunction_Article_BlogData_AID

--droping primary key
Alter table [User].PersonalDetail
drop constraint PK_PersonalDetail_UID
go

drop procedure signup
drop procedure userLogin
go

--rename UID to UserID
exec sp_rename '[User].[PersonalDetail].UID','UserID','Column'
exec sp_depends '[User].[PersonalDetail]'


--After dropping all the procedure view trigger foreign key primary key
exec sp_rename '[User].[PersonalDetail].UID','UserID','Column'
go

--renaming AID to ArticleID
exec sp_depends '[Article].[BlogData]'
exec sp_rename '[Article].[BlogData].AID','ArticleID','Column'

--renaming CID to CommentID
exec sp_depends '[Article].[Comment]'
exec sp_rename '[Article].[Comment].CID','CommentID','Column'

--renaming UID to UserID in Article.BlogData
exec sp_rename '[Article].[BlogData].UID','UserID','Column'

--renaming AID to ArticleID
exec sp_depends '[Article].[Comment]'
exec sp_rename '[Article].[Comment].AID','ArticleID','Column'
 
--renaming UID to UserID
exec sp_depends '[Article].[Comment]'
exec sp_rename '[Article].[Comment].UID','UserID','Column'



