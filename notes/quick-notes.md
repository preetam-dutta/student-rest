
DB Setup
========

keyspace
--------
- Execute commands via CQLSH
- create student keyspace
  ```cql
    CREATE  KEYSPACE student 
    WITH REPLICATION = { 
        'class' : 'SimpleStrategy', 
        'replication_factor' : 1 
    }; 
  ```
  
- go to student keyspace   
  ```
    use student;
  ```
   

create tables 
-------------
- Execute commands via CQLSH
- student table
  ```
  create table student (
    id uuid,
    name text,
    phone double,
    email text,
    course text,
    primary key(id)
  );
  ```

- student_by_course_and_email
  ```
  create table student_by_course_and_email (
    course text,
    email text,
    phone double,
    name text,
    id uuid,
    primary key((course), email, phone, name)
  );
  ```

- student_by_course_and_phone
  ```
  create table student_by_course_and_phone (
    course text,
    phone double,
    email text,
    name text,
    id uuid,
    primary key((course), phone, email, name)
  );
  ```


insert data
-----------

- insert data via CQLSH 
  ```
  BEGIN BATCH
      insert into student_by_course_and_email (course, email, phone, name, id) values ('CS', 'josh@test.com', 9988776655, 'Preetam Dutta', 4a5907df-35a5-4f33-bd72-b2c6a6c897f2);
      insert into student (course, email, phone, name, id) values ('CS', 'preet@test.com', 9988776655, 'Preetam Dutta', 4a5907df-35a5-4f33-bd72-b2c6a6c897f2);
      insert into student_by_course_and_phone (course, phone, email, name, id) values ('CS', 9988776655, 'preet@test.com', 'Preetam Dutta', 4a5907df-35a5-4f33-bd72-b2c6a6c897f2);
  APPLY BATCH;
  
  ```


REST operations
===============

HTTP GET (read data)
--------------------
  ```bash
    curl http://<host>:8080/student
    curl http://<host>:8080/student/course/CS
    curl http://<host>:8080/student/course/CS/email/rajesh@test.com
    curl http://<host>:8080/student/course/NS/phone/2277665500
  ```

HTTP POST (create data)
--------------------------
  ```bash
    curl -d '{"course":"CS", "email":"ashish@test.com", "phone":"2277665544", "name":"Ashish S"}' -H "Content-Type: application/json" -X POST http://<host>:8080/student
    curl -d '{"course":"NS", "email":"vipin@test.com", "phone":"2277665500", "name":"Vipin Tondak"}' -H "Content-Type: application/json" -X POST http://<host>:8080/student
    curl -d '{"course":"NS", "email":"amba@test.com", "phone":"2277665511", "name":"Amba Prasad"}' -H "Content-Type: application/json" -X POST http://<host>:8080/student
    curl -d '{"course":"NS", "email":"anuraag@test.com", "phone":"2277665599", "name":"Anuraag Bandal"}' -H "Content-Type: application/json" -X POST http://<host>:8080/student
    curl -d '{"course":"CS", "email":"rajesh@test.com", "phone":"2277665532", "name":"Rajesh Sahay"}' -H "Content-Type: application/json" -X POST http://<host>:8080/student
  ```

HTTP PUT (update data)
----------------------
  ```bash
    curl -d '{"id":"67919cf2-72cf-4c14-934b-a724096f1aee", "course":"CS", "email":"rajesh@test.com", "phone":"2277665530", "name":"Rajesh Sahay"}' -H "Content-Type: application/json" -X PUT http://<host>:8080/student
    curl -d '{"id":"13eaee65-7e35-4a78-a975-30ed7fdd83ae", "course":"NS", "email":"anuraag@test.com", "phone":"2277665599", "name":"Anuraag Bansal"}' -H "Content-Type: application/json" -X PUT http://<host>:8080/student
    
  ```


HTTP DELETE (delete data)
-------------------------
  ```bash
    curl -X DELETE http://<host>:8080/student/9ae88253-5a10-4f15-8e2c-0d79dca8e47e
  ```


cheat sheet commands
====================

commands
--------
- delete existing 
    ```
    kubectl delete rs student-rest
    kubectl delete service student-rest
    kubectl get all
    ```

- tag image
    ```
    docker image ls | head -20
    docker image tag student-rest:latest preetamdutta/student-rest:latest
    docker image tag student-rest:latest preetamdutta/student-rest:latest
    docker image ls | head -20
    ```

- execute
    ```
    kubectl apply -f replica-set.yaml
    ```


configure properties at runtime
-------------------------------
- If you need to override default configurations 
    
    ```
    java -jar student-rest.jar --spring.config.location=classpath:/another-location.properties
    ```