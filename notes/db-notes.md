
Table Creation
==============

student
-------
create table student (
  id uuid,
  name text,
  phone double,
  email text,
  course text,
  primary key(id)
);

student_by_course_and_email
---------------------------
create table student_by_course_and_email (
  course text,
  email text,
  phone double,
  name text,
  id uuid,
  primary key((course), email, phone, name)
);

student_by_course_and_phone
---------------------------
create table student_by_course_and_phone (
  course text,
  phone double,
  email text,
  name text,
  id uuid,
  primary key((course), phone, email, name)
);

INSERT
======

BEGIN BATCH
    insert into student_by_course_and_email (course, email, phone, name, id) values ('CS', 'preet@test.com', 9988776655, 'Preetam Dutta', 4a5907df-35a5-4f33-bd72-b2c6a6c897f2);
    insert into student (course, email, phone, name, id) values ('CS', 'preet@test.com', 9988776655, 'Preetam Dutta', 4a5907df-35a5-4f33-bd72-b2c6a6c897f2);
    insert into student_by_course_and_phone (course, phone, email, name, id) values ('CS', 9988776655, 'preet@test.com', 'Preetam Dutta', 4a5907df-35a5-4f33-bd72-b2c6a6c897f2);
APPLY BATCH;


Curl Commands
=============



curl http://localhost:8080/student
curl http://localhost:8080/student/course/CS
curl http://localhost:8080/student/course/CS/email/rajesh@test.com
curl http://localhost:8080/student/course/NS/phone/2277665500



create records
--------------
curl -d '{"course":"CS", "email":"ashish@test.com", "phone":"2277665544", "name":"Ashish S"}' -H "Content-Type: application/json" -X POST http://localhost:8080/student
curl -d '{"course":"NS", "email":"vipin@test.com", "phone":"2277665500", "name":"Vipin Tondak"}' -H "Content-Type: application/json" -X POST http://localhost:8080/student
curl -d '{"course":"NS", "email":"amba@test.com", "phone":"2277665511", "name":"Amba Prasad"}' -H "Content-Type: application/json" -X POST http://localhost:8080/student
curl -d '{"course":"NS", "email":"anuraag@test.com", "phone":"2277665599", "name":"Anuraag Bandal"}' -H "Content-Type: application/json" -X POST http://localhost:8080/student
curl -d '{"course":"CS", "email":"rajesh@test.com", "phone":"2277665532", "name":"Rajesh Sahay"}' -H "Content-Type: application/json" -X POST http://localhost:8080/student