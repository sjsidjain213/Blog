select * from Article.Comment

select BD.ArticleID,BD.Title,BD.Content,GJ.GID,G.Value from Article.BlogData  as BD 
join Article.GenreJunction as GJ on GJ.AID = BD.ArticleID 
join Article.Genre as G on G.GID = GJ.GID
order by BD.ArticleID
go

select * from [User].PersonalDetail
go

use blog
select * from INFORMATION_SCHEMA.REFERENTIAL_CONSTRAINTS
select * from sys.key_constraints
go

alter table [Article].[Genre]
add constraint PK_genre_GenreID Primary Key(GenreID)
go

alter table [Article].[GenreJunction]
add constraint FK_Article_Genre_Junction_Genre_GID
go

alter table [Article].[GenreJunction]
drop constraint FK_Article_GenreJunction_Article_BlogData_AID
go


