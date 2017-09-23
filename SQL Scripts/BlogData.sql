use blog
go

-------------------------------------------------------------------------------------------------------
CREATE TABLE [Article].[BlogData](
	[ArticleID] [int] IDENTITY(1,1) NOT NULL,
	[Title] [varchar](400) NOT NULL,
	[Content] [varchar](max) NOT NULL,
	[UserID] [int] NOT NULL,
	[CreationDate] DATETIME NOT NULL,
	[Status] bit not null,
    [Upvote] int DEFAULT 0 not null,
	[Downvote] int DEFAULT 0 not null
 CONSTRAINT [PK_Article_BlogData_AID] PRIMARY KEY CLUSTERED 
(
	[ArticleID] ASC
)) 
GO

-------------------------------------------------------------------------------------------------------
CREATE TABLE [User].[PersonalDetail](
	[UserID] [int] IDENTITY(1,1) NOT NULL,
	[Name] [varchar](400) NOT NULL,
	[Username] [varchar](400) NOT NULL,
	[EmailID] [varchar](320) NOT NULL,
	[Password] [varchar](30) NULL,
	[StateName] [VARCHAR](100) NOT NULL,
	[CityName] [varchar](100) NOT NULL,
    [Status] [Bit] not null
 CONSTRAINT [PK_PersonalDetail_UID] PRIMARY KEY CLUSTERED 
(
	[UserID] ASC
))
GO

ALTER TABLE [User].[PersonalDetail]
ADD CONSTRAINT [UQ_User_PersonalDetail_Username] UNIQUE(Username)
go

--Introducing FOREIGN KEY constraint 'FK_Article_Comment_User_PersonalDetail' on table 'Comment' may cause cycles or multiple cascade paths. Specify ON DELETE NO ACTION or ON UPDATE NO ACTION, or modify other FOREIGN KEY constraints.
-- how to implement update cascade and delete cascade
create table [Article].Comment(
CommentID int identity(1,1),
ArticleID int not null,
[UserID] int not null,
Comment varchar(7500) not null,
CreationDate DATETIME null
CONSTRAINT [PK_Article_Comment] PRIMARY KEY(CommentID) 
)

----------------------------------------------------------------------------------
                                      --Log tables--
----------------------------------------------------------------------------------
create table Log.[PersonalDetail](
LUID int identity(1,1),
UID int not null,
Operation varchar(500) not null,
[Date] datetime not null
CONSTRAINT [PK_Log_PersonalDetail_LUID] PRIMARY KEY(LUID)
)

create table Log.BlogData(
LAID int identity(1,1),
AID int not null,
Operation varchar(500) not null,
[Date] datetime not null
CONSTRAINT [PK_Log_BlogData_LAID] PRIMARY KEY(LAID)
)
go

create table Log.Comment(
LCID int identity(1,1),
CID int not null,
AID int not null,
Operation varchar(500) not null,
[Date] datetime not null
CONSTRAINT [PK_Log_Comment_LCID] PRIMARY KEY(LCID)
)
------------------------------------------------------------------------------------------------
                                           ----Genre----
----------------------------------------Reference table-----------------------------------------
create table Article.Genre(
GenreID int not null identity(1,1),
Value varchar(50) not null
CONSTRAINT PK_genre_GenreID Primary Key(GenreID)
)
go

select * from sys.key_constraints
----------------------------------------Genre Junction------------------------------------------
Create table Article.GenreJunction(
GenreID int not null,
ArticleID int not null
CONSTRAINT PK_Article_GenreJunction_GID_AID Primary Key(GenreID,ArticleID)
)
go

--------------------------Renameing ID columns---------------------------------
use blog
exec sp_rename '[User].PersonalDetail.UID','UserID','column'
go

---------------------------------------------Locations-----------------------------------------
create schema [Location]
go

create table [Location].[State](
StateID int IDENTITY not null,
StateName varchar(100) not null,
CountryID int not null
)

create table [Location].[City](
CityID int not null identity,
CityName varchar(100) not null,
StateID int not null,
StateName varchar(200) not null
)
go


--------------------------------------Article Upvote-----------------------------------
create Table [Article].[Upvote](
ArticleID int not null,
UserID int not null
)
go

-------------------------------------Article Downvote----------------------------------
create Table [Article].[Downvote](
ArticleID int not null,
UserID int not null
)
go






