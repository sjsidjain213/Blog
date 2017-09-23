use Blog
go
declare @time as datetime 
set @time = GETDATE() 
print cast(convert(date,getDate()) as varchar(100))
declare @path as varchar(100) = concat('E:\BlogBackup\Blog_Full',cast(convert(date,getDate()) as varchar(100)),'.bak')
BACKUP DATABASE Blog
TO DISK = @path
go

declare @path as varchar(100) = concat('E:\BlogBackup\Blog_Diff',cast(convert(date,getDate()) as varchar(100)),'.bak')
BACKUP DATABASE Blog
TO DISK = @path
WITH DIFFERENTIAL;
go



BACKUP LOG AdventureWorks2012
TO DISK = 'D:\BU\AdventureWorks2012_Log.bak';
go


use master
go



--restore process
Restore database Blog from disk = 'E:\BlogBackup\Blog_Full2017-08-26.bak' with norecovery, replace
Restore database Blog from disk = 'E:\BlogBackup\Blog_Diff2017-08-27.bak' with norecovery
Restore database Blog with recovery

use Blog
select * from Article.BlogData
select * from Ar


-----------------backup process-------------------------------------
declare @dbname as varchar(200),




