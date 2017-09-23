use blog
go

/*
all the data should get backed up such that allowing user to remove all the data from the table.
all the constraint should get removed
user can now make whatever changes likes
all the constraint should get added again
table should not get dropped. 
*/

-------restoring data into main tables after droping and adding new foreign key constraint------



-----------------------------PersonalDetail table recovered--------------------------
Insert into [User].PersonalDetail ([UID],[Name],[Username],[EmailID],[Password],[salt])
select * 
from test.personaldetail
select * from [User].PersonalDetail

---------------------------------BlogData table recovering-------------------
set identity_insert [Article].[BlogData] on
--disable trigger on the table
ALTER TABLE Article.BlogData DISABLE TRIGGER trig_Blog_Insert

insert into [Article].BlogData ([AID],[Title],[Content],[UID])
select * from test.BlogData as B
where B.UID IN (SELECT UID FROM [User].PersonalDetail)

--enable trigger on table
ALTER TABLE Article.BlogData ENABLE TRIGGER trig_Blog_Insert

select * from Article.BlogData
--------------------------------Comment Table recovering---------------------
set identity_insert [Article].[Comment] off

Insert into [Article].Comment([CID],[AID],[UID],[Comment])
select * from test.Comment as C 
where C.AID IN (SELECT AID FROM Article.BlogData) 
and C.UID IN (SELECT UID FROM [User].PersonalDetail)

select * from Article.Comment
go----------
------------------------------------------------------------------------------------------------
--Here I will drop the exsisting primary key of Article.GenreJunction table which GJID
-- and will create a new primary key which will be a combination of GID and AID
------------------------------------------------------------------------------------------------

select *
into #GenreJunction
from Article.GenreJunction
go

select * from #GenreJunction

alter table Article.GenreJunction
drop PK_GenreJunction_GJID

alter table Article.GenreJunction
drop column GJID

select * from Article.GenreJunction

alter table Article.GenreJunction
add constraint PK_Article_GenreJunction_GID_AID Primary Key(GID,AID)
