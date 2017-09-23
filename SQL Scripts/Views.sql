use Blog
-----------------------------Function Home page----------------------------------------
drop function func_HomePage
go

alter function func_HomePage()
returns table
as
return
(
select B.ArticleID,B.Title,B.Content,P.Name,STUFF((select ','+G.value 
														 from Article.Genre as G 
														 join Article.GenreJunction as GJI on G.GenreID = GJI.GenreID
														 where B.ArticleID = GJI.ArticleID 
														 FOR XML PATH('')),1,1,'') as [value]
from Article.[BlogData] as B  
JOIN  [User].PersonalDetail as P ON P.UserID = B.UserID
WHERE B.Status=1  
)
go

----------------------------Function Home page with Filter--------------------------------
drop function func_HomePageFilter
go

alter function func_HomePageFilter(@filter as varchar(20))
returns table
as
return
(
select B.ArticleID,B.Title,B.Content,P.Name,STUFF((select ','+G.value 
														 from Article.Genre as G 
														 join Article.GenreJunction as GJI on G.GenreID = GJI.GenreID
														 where B.ArticleID = GJI.ArticleID 
														 FOR XML PATH('')),1,1,'') as [value],GJ.GenreID
from Article.[BlogData] as B  
JOIN  [User].PersonalDetail as P ON P.UserID = B.UserID  
JOIN Article.GenreJunction as GJ on GJ.ArticleID = B.ArticleID
where GJ.GenreID = (select G.GenreID from Article.Genre as G where G.[Value] = @filter) and B.Status = 1
)
go

----------------------------------View Comments---------------------------------
drop view vComment
go

create view vComment
as
select pd.Name as [Name], c.Comment as [Comment], c.ArticleID
FROM [User].PersonalDetail as pd,[Article].Comment as c
where pd.UserID = c.UserID
go 

------------------------------View Specific Blog-----------------------------------
drop view vSpecificBlog
go

create view vSpecificBlog WITH SCHEMABINDING as
select B.ArticleID,B.Title,B.Content,P.[Name] from Article.BlogData as B
JOIN
[User].PersonalDetail as P
ON P.UserID = B.UserID
where B.Status = 1
go

CREATE UNIQUE CLUSTERED INDEX CIX_vSpecificBlog on vSpecificBlog(ArticleID)
go

---------------------------------Function Profile (replaced vProfile)-----
alter function fn_user_profile(@username as varchar(300))
returns table
as
return (select PDT.Name,PDT.Username,PDT.EmailID,STUFF((select ','+G.value 
														from [User].PersonalDetail as PD	
														join Article.BlogData as BD on PD.UserID = BD.UserID
														join Article.GenreJunction as GJ on BD.ArticleID = GJ.ArticleID
														join Article.Genre as G on G.GenreID = GJ.GenreID
														where PD.UserID = PDT.UserID
    												    FOR XML PATH('')),1,1,'') as [Tags]
from [User].PersonalDetail as PDT
where PDT.UserID = (select UserID from [User].PersonalDetail where Username = @username) )
go
---use procedure

------------------------------Tag User basis-----------------------------------------
select PDT.UserID,STUFF((select ','+G.value 
														from [User].PersonalDetail as PD	
														join Article.BlogData as BD on PD.UserID = BD.UserID
														join Article.GenreJunction as GJ on BD.ArticleID = GJ.ArticleID
														join Article.Genre as G on G.GenreID = GJ.GenreID
														where PD.UserID = PDT.UserID
    												    FOR XML PATH('')),1,1,'') as [value]
from [User].PersonalDetail as PDT
where	PDT.UserID = 5

-------------------------------View Article name on Profile---------------------------------
drop view vArticleProfile
go

create view vArticleProfile WITH SCHEMABINDING AS
select PD.Username as Username, BD.Title as Title, BD.ArticleID as ArticleID
from [User].PersonalDetail as PD join [Article].BlogData as BD 
ON PD.UserID = BD.UserID
go

----------------function for displaying name of all article on profile---------------------
alter function fn_article_list_profile(@username varchar(300))
returns table
as
return(
select PD.Username as Username, BD.Title as Title, BD.ArticleID as ArticleID, 
(select count(ArticleID) from Article.Upvote where ArticleID = BD.ArticleID) as [Upvote],(select count(ArticleID) from Article.Downvote where ArticleID = BD.ArticleID) as [Downvote]
from [User].PersonalDetail as PD 
join [Article].BlogData as BD ON PD.UserID = BD.UserID
left join [Article].Upvote as U ON U.ArticleID = BD.ArticleID
where PD.Username = @username
)
go
select * from fn_article_list_profile('sjsidjain')
-----------------------------View Home page : Removed--------------------------------------
drop view vHomePage
go

create view vHomePage with SCHEMABINDING
as   
select B.ArticleID,B.Title,B.Content,P.[Name]
from Article.BlogData as B  
JOIN  [User].PersonalDetail as P ON P.UserID = B.UserID  
go

CREATE UNIQUE CLUSTERED INDEX CIX_vHomePage on vHomePage(ArticleID)
go
----------------------------function upvote---------------------------------------------
alter function fn_upvote_count()
returns table 
as 
return(select Count(U.ArticleID) as count 
from Article.BlogData as BD
left join Article.Upvote as U on BD.ArticleID = U.ArticleID
group by(BD.ArticleID))
go
----------------------------function downvote---------------------------------------------
create function fn_downvote_count()
returns table 
as 
return(select Count(U.ArticleID) as count 
from Article.BlogData as BD
left join Article.Downvote as U on BD.ArticleID = U.ArticleID
group by(BD.ArticleID))
go
--------------------------------------------------------------------------------------
use blog

select Count(U.ArticleID) as count 
from Article.BlogData as BD
left join Article.Upvote as U on BD.ArticleID = U.ArticleID
group by(BD.ArticleID) 

select * from fn_upvote_count()
select * from fn_downvote_count()



