---------------------------------New blog insert--------------------------------------
drop procedure usp_BlogData_Insert
go

alter procedure usp_BlogData_Insert(@title varchar(max),@content varchar(max),@username varchar(200),@genre1 varchar(50),@genre2 varchar(50),@genre3 varchar(50),@response bit output)
as 
begin
select * from Article.BlogData
--**insert new article**
declare @articleid int
Insert into Article.BlogData(Title,Content,UserID,CreationDate,[Status]) values
(@title,@content,(select UserID from [User].PersonalDetail where username = @username),getdate(),1)
set @articleid = SCOPE_IDENTITY()

--**add genre id in genrejunction table**
if @genre1 IS NOT NULL
insert into Article.GenreJunction values
((select GenreID from Article.Genre where [value] = @genre1) ,@articleid);

if @genre2 IS NOT NULL
insert into Article.GenreJunction values
((select GenreID from Article.Genre where [value] = @genre2) ,@articleid);

if @genre3 IS NOT NULL
insert into Article.GenreJunction values
((select GenreID from Article.Genre where [value] = @genre3) ,@articleid);

if @genre1 IS NULL AND @genre2 IS NULL AND @genre3 IS NULL
insert into Article.GenreJunction values
(0,@articleid)
end
go

-----------------------------update blog--------------------------------------------------
drop procedure usp_BlogData_Update
go

alter procedure usp_BlogData_Update(@title varchar(max),@content varchar(max),@username varchar(200),@genre1 varchar(50),@genre2 varchar(50),@genre3 varchar(50),@response bit output,@articleid int)
as
begin
----
update Article.BlogData
set Title = @title, Content = @content 
where ArticleID = @articleid

delete from Article.GenreJunction
where ArticleID = @articleid

--**add genre id in genrejunction table**
if @genre1 IS NOT NULL
insert into Article.GenreJunction values
((select GenreID from Article.Genre where [value] = @genre1) ,@articleid);

if @genre2 IS NOT NULL
insert into Article.GenreJunction values
((select GenreID from Article.Genre where [value] = @genre2) ,@articleid);

if @genre3 IS NOT NULL
insert into Article.GenreJunction values
((select GenreID from Article.Genre where [value] = @genre3) ,@articleid);

if @genre1 IS NULL AND @genre2 IS NULL AND @genre3 IS NULL
insert into Article.GenreJunction values
(0,@articleid)

end
go

------------------------------------------------------------------------
create type genre as table
([name] varchar(100))
go

create table content
([allname] varchar(200))
go

create procedure usp_BlogData_Insert_Genre(@title varchar(max),@content varchar(max),
@username varchar(200),@response bit output)
as 
begin
begin try
begin transaction
Insert into Article.BlogData values
(@title,@content,(select UserID from [User].PersonalDetail where username = @username),GETDATE())
set @response = 'true'
commit transaction 
end try
begin catch
if @@TRANCOUNT>0
set @response = 'false'
rollback transaction
end catch
end 
go

-------------------------------Article Insert---------------------------------------------------
create procedure usp_Article_Comment_Insert(@articleid int,@userid int,@comment varchar(7000),@response bit output)
as
begin
insert into Article.Comment values
(@articleid,@userid,@comment,getDate())
set @response = 'true'
end
go

----------------------------Article Delete------------------------------------------------------
create procedure usp_Article_Delete(@articleid int,@response bit output)
as
begin
delete from Article.BlogData
where ArticleID = @articleid
set @response = 1
end
go

--------------------------------User Disable-------------------------------
alter procedure usp_user_disable (@username varchar(200))
as 
begin
begin try
begin tran
update [User].PersonalDetail
set Status = 0
where Username = @username
alter table [Article].BlogData disable trigger trig_Article_Update

--**
declare @userid as int = (select UserID from [User].PersonalDetail where Username =  @username)

select B.ArticleID
into #tempArticleID
from Article.BlogData  as B
where B.UserID = @userid

declare cursor_articleid cursor for 
select B.ArticleID
from #tempArticleID as B

open cursor_articleid

declare @articleid as int
fetch next from cursor_articleid
into @articleid
 
while @@FETCH_STATUS = 0
begin 
update Article.BlogData
set Status = 0
where ArticleID = @articleid
insert into Log.BlogData values
(@articleid,GETDATE(),'Article is Disabled')

fetch next from cursor_articleid
into @articleid
end

close cursor_articleid
deallocate cursor_articleid
commit tran
Return 1
end try
begin catch
rollback tran
 Return 0
end catch
end
go

----------------------------------------------User Enable-----------------------------------
alter proc usp_user_enable (@username varchar(400))
as
begin
begin try
begin tran
update [User].PersonalDetail
set Status = 1
where Username = @username

declare @userid as int = (select UserID from [User].PersonalDetail where Username =  @username)

select B.ArticleID
into #tempArticleID
from Article.BlogData  as B
where B.UserID = @userid

declare cursor_articleid cursor for 
select B.ArticleID
from #tempArticleID as B

open cursor_articleid

declare @articleid as int
fetch next from cursor_articleid
into @articleid
 
while @@FETCH_STATUS = 0
begin 
update Article.BlogData
set Status = 1
where ArticleID = @articleid
insert into Log.BlogData values
(@articleid,GETDATE(),'Article is Enabled')

fetch next from cursor_articleid
into @articleid
end

close cursor_articleid
deallocate cursor_articleid

commit tran
Return 1
end try
begin catch
rollback tran
Return 0
end catch
end 

-------------------------------------------------------------------------------------
select * from [User].PersonalDetail
select * from [Article].BlogData
use blog
go
select * from Article.BlogData

---------------------------------------Article upvote------------------------------------------
exec usp_article_upvote 37,'sjsidjain'
select * from Article.Upvote


create proc usp_article_upvote(@articleid int, @username varchar(300))
as
begin
declare @userid int = (select UserID from [User].PersonalDetail where Username = @username)
if (select count(ArticleID) from [Article].Upvote where ArticleID = @articleid and UserID = @userid) = 1
begin
delete from Article.Upvote
where ArticleID = @articleid and UserID = @userid
end 
else 
begin
insert into Article.Upvote values
(@articleid,@userid)
end
if (select count(ArticleID) from Article.Downvote where ArticleID = @articleid and UserID = @userid) = 1
delete from [Article].Downvote
where ArticleID = @articleid and UserID = @userid 
return 1
end 
go

---------------------------------------Article downvote---------------------------------------
create proc usp_article_downvote(@articleid int, @username varchar(300))
as
begin
declare @userid int = (select UserID from [User].PersonalDetail where Username = @username)
if (select count(ArticleID) from [Article].Downvote where ArticleID = @articleid and UserID = @userid) = 1
begin
delete from Article.Downvote
where ArticleID = @articleid and UserID = @userid
end 
else 
begin
insert into Article.Downvote values
(@articleid,@userid)
end
if (select count(ArticleID) from Article.Upvote where ArticleID = @articleid and UserID = @userid) = 1
delete from [Article].Upvote
where ArticleID = @articleid and UserID = @userid 
return 1
end 
go

select * from Article.U


