
Table Creation
==============

student_by_course_and_email
---------------------------
create table student_by_course_and_email (
  course text,
  email text,
  phone double,
  name text,
  primary key((course), email, phone, name)
);

student_by_course_and_phone
---------------------------
create table student_by_course_and_phone (
  course text,
  phone double,
  email text,
  name text,
  primary key((course), phone, email, name)
);

INSERT
======

BEGIN BATCH
    insert into student_by_course_and_email (course, email, phone, name) values ('CS', 'preet@test.com', 9988776655, 'Preetam Dutta');
    insert into student_by_course_and_phone (course, phone, email, name) values ('CS', 9988776655, 'preet@test.com', 'Preetam Dutta');
APPLY BATCH;

BEGIN BATCH
    insert into student_by_course_and_email (course, email, phone, name) values ('CS', 'vipin@test.com', 8877665544, 'Vipin Tondak');
    insert into student_by_course_and_phone (course, phone, email, name) values ('CS', 8877665544, 'vipin@test.com', 'Vipin Tondak');
APPLY BATCH;

BEGIN BATCH
    insert into student_by_course_and_email (course, email, phone, name) values ('NS', 'raj@test.com', 7766554433, 'Rajesh Sahay');
    insert into student_by_course_and_phone (course, phone, email, name) values ('NS', 7766554433, 'raj@test.com', 'Rajesh Sahay');
APPLY BATCH;

BEGIN BATCH
    insert into student_by_course_and_email (course, email, phone, name) values ('NS', 'amba@test.com', 6655443322, 'Amba Prasad');
    insert into student_by_course_and_phone (course, phone, email, name) values ('NS', 6655443322, 'amba@test.com', 'Amba Prasad');
APPLY BATCH;