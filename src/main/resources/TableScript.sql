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
   suspended_at  DATETIME,
   
   CONSTRAINT ps_user_pk PRIMARY KEY ( mobile_number)
);



commit;

