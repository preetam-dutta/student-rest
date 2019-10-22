
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

get records
-----------
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

update records
--------------
curl -d '{"id":"2262aaf7-1b65-48a3-9db2-d27b622074b9", "course":"CS", "email":"rajesh@test.com", "phone":"2277665530", "name":"Rajesh Sahay"}' -H "Content-Type: application/json" -X PUT http://localhost:8080/student
curl -d '{"id":"7a9c4c59-04d8-497a-a385-283b403bd38e", "course":"NS", "email":"anuraag@test.com", "phone":"2277665599", "name":"Anuraag Bansal"}' -H "Content-Type: application/json" -X PUT http://localhost:8080/student


delete record
-------------
curl -X DELETE http://localhost:8080/student/9ae88253-5a10-4f15-8e2c-0d79dca8e47e