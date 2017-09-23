use blog
go;

-------------------------------------user regitration----------------------------------------------
drop procedure userRegistration
go

alter procedure userRegistration (@username as varchar(400),@name as varchar(400),@password as varchar(30), @emailid as varchar(320),@statename as varchar(100),@cityname as varchar(100), @response as bit output)
as
SET XACT_ABORT,NOCOUNT ON
begin 
declare @hashedpass as binary(30)
declare @salt as varchar(30) 
EXEC usp_hashed_password_generator @password, @saltout = @salt output,@hashedpassword = @hashedpass output
INSERT INTO [User].[PersonalDetail] (Username,[Name],EmailID,[Password],salt,StateName,CityName,Status) values
 (@username,@name,@emailid,@hashedpass,@salt,@statename,@cityname,1)
set @response = 'true'
end;
go

----------------------------------user login--------------------------------------------------
--drop procedure usp_password_verifier
drop procedure usp_password_verifier
go

alter PROC usp_password_verifier
  @AccountPwd VARCHAR(100),
  @username varchar(100),
  @response bit output
AS
BEGIN
  SET NOCOUNT ON;
  DECLARE @Salt CHAR(25);
  DECLARE @PwdWithSalt VARCHAR(125);
  DECLARE @PwdHash VARBINARY(20);  
  SELECT @Salt = salt, @PwdHash = [Password] 
  FROM [User].PersonalDetail WHERE Username = @username;  
  SET @PwdWithSalt = @Salt + @AccountPwd;
  IF (HASHBYTES('SHA1', @PwdWithSalt) = @PwdHash)
begin
   set @response = 'true'
   Exec usp_user_enable @username
  
 end
  ELSE
   set @response ='false'
END;
GO 

------------------------------------------username validation-----------------------------------------
drop procedure usp_username_validation
go

Create procedure usp_username_validation
  @username varchar(100),
  @response bit output
as
begin
SET NOCOUNT ON;
DECLARE @countusername as int
SELECT @countusername = COUNT(*) from [User].PersonalDetail where [Username] = @username
if @countusername>0
set @response = 0
else
set @response = 1
end
go

