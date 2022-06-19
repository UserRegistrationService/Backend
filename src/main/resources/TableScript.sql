DROP SCHEMA IF EXISTS userregistration_db;

CREATE SCHEMA userregistration_db;
USE userregistration_db;
   
create table user (
   mobile_number BIGINT,
   name VARCHAR(15)  not null,
   address VARCHAR(60)  not null,
   email_id  VARCHAR(30) not null unique,
   password VARCHAR(15) not null,
   email_verified  Boolean,
   number_verified Boolean,
   locked Boolean,
  consecutive_login_failure INT,
  registered_at  DATETIME,
  
   
   CONSTRAINT ps_user_pk PRIMARY KEY ( mobile_number)
);

create table suspended_user (
   mobile_number BIGINT,
   email_id  VARCHAR(30) not null unique,
   suspended_on  DATE,
   
   CONSTRAINT ps_user_pk PRIMARY KEY ( mobile_number)
);
SET GLOBAL event_scheduler = ON;


DELIMITER //  
CREATE PROCEDURE CHECK_AND_UPDATE_USER_DATA_AFTER_FOUR_HOURS() 
BEGIN  
    SET @INTERNAL_TIME_STAMP= NOW();
    SET @OLDEST_UNVERIFIED_USER = (SELECT mobile_number FROM user WHERE (email_verified = false OR number_verified = false) AND registered_at + INTERVAL 4 HOUR   <=  @INTERNAL_TIME_STAMP   ORDER BY registered_at LIMIT 1); 
  
        WHILE @OLDEST_UNVERIFIED_USER IS NOT NULL DO 
           SET @EMAIL = (SELECT  email_id FROM USER WHERE mobile_number = @OLDEST_UNVERIFIED_USER);
           INSERT INTO suspended_user (mobile_number,email_id,suspended_on) VALUES (@OLDEST_UNVERIFIED_USER,@EMAIL,NOW());
           DELETE FROM USER WHERE mobile_number = @OLDEST_UNVERIFIED_USER;        
           SET @OLDEST_UNVERIFIED_USER = (SELECT mobile_number FROM user WHERE (email_verified = false OR number_verified = false) AND registered_at + INTERVAL 4 HOUR <=  @INTERNAL_TIME_STAMP  ORDER BY registered_at LIMIT 1); 
        END WHILE;   
END //
DELIMITER ;


DELIMITER //  
CREATE PROCEDURE DELETE_AFTER_SIX_MONTHS() 
BEGIN  
    SET @INTERNAL_TODAY_DATE= DATE(NOW());
    SET @SIX_MONTHS_OLD_SUSPENDED_USER =(SELECT mobile_number FROM SUSPENDED_USER WHERE (suspended_on + INTERVAL 6 MONTH  <= @INTERNAL_TODAY_DATE ) ORDER BY suspended_on LIMIT 1);
    WHILE @SIX_MONTHS_OLD_SUSPENDED_USER IS NOT NULL DO
      DELETE FROM SUSPENDED_USER WHERE mobile_number = @SIX_MONTHS_OLD_SUSPENDED_USER;
      SET @SIX_MONTHS_OLD_SUSPENDED_USER =(SELECT mobile_number FROM SUSPENDED_USER WHERE (suspended_on + INTERVAL 6 MONTH  <=  @INTERNAL_TODAY_DATE ) ORDER BY suspended_on LIMIT 1);
    END WHILE;   
END //
DELIMITER ;

SET @STARTING_TIME=NOW();
DELIMITER //
CREATE EVENT IF NOT EXISTS every_one_minutes
ON SCHEDULE EVERY 1 MINUTE
STARTS @STARTING_TIME
ENDS @STARTING_TIME + INTERVAL 10 YEAR
DO
 BEGIN
 CALL CHECK_AND_UPDATE_USER_DATA_AFTER_FOUR_HOURS();
END //   
DELIMITER ;



DELIMITER //
CREATE EVENT IF NOT EXISTS every_day
ON SCHEDULE EVERY 1 DAY
STARTS @STARTING_TIME
ENDS @STARTING_TIME + INTERVAL 10 YEAR
DO
 BEGIN
 
 CALL DELETE_AFTER_SIX_MONTHS();
END //   
DELIMITER ;

SELECT * FROM USER;

SELECT * FROM SUSPENDED_USER;

SELECT NOW();

SELECT @STARTING_TIME;
