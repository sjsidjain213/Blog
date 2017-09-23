drop procedure usp_hashed_password_generator
go

create PROC usp_hashed_password_generator
  @NewAccountPwd VARCHAR(50),
  @saltout varchar(25) output,
  @hashedpassword varbinary(30) output
AS
BEGIN
  SET NOCOUNT ON;
  DECLARE @Salt VARCHAR(25);
  DECLARE @PwdWithSalt VARCHAR(125);

  -- Generate the salt
  DECLARE @Seed int;
  DECLARE @LCV tinyint;
  DECLARE @CTime DATETIME;

  SET @CTime = GETDATE();
  SET @Seed = (DATEPART(hh, @Ctime) * 10000000) + (DATEPART(n, @CTime) * 100000)
      + (DATEPART(s, @CTime) * 1000) + DATEPART(ms, @CTime);
  SET @LCV = 1;
  SET @Salt = CHAR(ROUND((RAND(@Seed) * 94.0) + 32, 3));

  WHILE (@LCV < 25)
  BEGIN
    SET @Salt = @Salt + CHAR(ROUND((RAND() * 94.0) + 32, 3));
 SET @LCV = @LCV + 1;
  END;

  set @saltout = @Salt
  SET @PwdWithSalt = @Salt + @NewAccountPwd;

set @hashedpassword = HASHBYTES('SHA1', @PwdWithSalt);

END
GO 

drop procedure usp_password_verifier
go

create PROC usp_password_verifier
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
   set @response = 'true'
  ELSE
   set @response ='false'
END;
GO 
