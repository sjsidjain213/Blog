use Blog
go

select * from Log.BlogData
select * from sys.triggers
go

--------------------------------
select * from [User].PersonalDetail
go

-----------------------------------User Insert-------------------------------------
drop Trigger [User].trig_User_Insert 
go

create trigger [User].trig_User_Insert on [User].PersonalDetail
for insert
as
declare @userid as int, @aid as int
set @userid  = (select [UserID] from inserted);
insert into Log.PersonalDetail values
(@userid,'New User inserted',GETDATE())
go

select * from Log.PersonalDetail
select * from [User].PersonalDetail---Blog Insert------------------------------------
drop Trigger Article.trig_Blog_Insert

create trigger Article.trig_Blog_Insert on Article.BlogData
for insert
as
declare @articleid as int
set @articleid = (select [ArticleID] from inserted)
insert into Log.BlogData values
(@articleid,GETDATE(),'New Article inserted')
go

select * from Article.BlogData
select * from Log.BlogData
go

-----------------------------------Comment Insert------------------------------------
drop Trigger Article.trig_Comment_Insert

create trigger Article.trig_Comment_Insert on [Article].Comment
for insert 
as 
declare @commentid int, @articleid int
set @commentid = (select CommentID from inserted)
set @articleid = (select ArticleID from inserted)
insert into Log.Comment values
(@commentid,@articleid,'New Comment Inserted',GETDATE())
go

select * from Article.BlogData
select * from Log.Comment
go

--------------------------------------Blog Update---------------------------------------
drop Trigger Article.trig_Article_Update

enable Trigger [Article].trig_Article_Update on [Article].BlogData
update Article.BlogData
set Status = 1 
where ArticleID = 1
select * from Log.BlogData order by LAID desc

create trigger [Article].trig_Article_Update on [Article].BlogData
for update
as 
declare @articleid int, @userid int
set @articleid = (select [ArticleID] from inserted)
insert into Log.BlogData values
(@articleid,GETDATE(),'Article is Updated')
go

select * from Article.BlogData
select * from Log.BlogData
go

--------------------------------------Blog Delete--------------------------------------- 
drop Trigger Article.trig_Article_Delete

create trigger [Article].trig_Article_Delete on [Article].BlogData
for delete
as 
declare @articleid int, @uid int
set @articleid = (select [ArticleID] from deleted)
insert into Log.BlogData values
(@articleid,GETDATE(),'Article deleted')
go

select * from Article.BlogData
select * from Log.BlogData
go

