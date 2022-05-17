## Assessment:
Create a simple Spring Boot application that manages users and tasks for those users.

#### Requirements:
* You do NOT need to create a UI for this application - only REST endpoints.
* The application should be able to list/create/update users via REST.
* The application should be able to list/create/update/delete tasks for users via REST.
* Data must be persisted to a database.
* You can use Hibernate, Spring Data etc.
* The query to select all tasks that are pending and old, should be done with JPA Criteria Builder.

#### Scheduling:
Setup a scheduled job to check all tasks in the Database - those that have a status of "pending" and who date_time has passed - print it to the console
and update the task to "done".

#### Considerations:
* Consider database migration
* Consider containerization
* Treat the code as if it was going to be sent to production - what would you do to ensure it is production-ready.

#### Distribution:
Once you are done, please upload the application to a repository and give us access so that we can download/view/test it.

#### Delivery
Provide us with a link to an online repository after completion.

#### Evaluation Criteria:
We should be able to clone the repo / download artifacts and run it without any installation or configuration.

As a guide, below are the curl commands with the REST endpoints we are expecting to test against.  You can use these urls as a guildeline on how to design/develop your REST endpoints.

If you do not have access to curl, you can use the Postman chrome plugin (or any other HTTP client) to perform these calls in order to test your application.

---

#### Data
- The application use an embedded H2 database
- The database is populated with User and Task Data

#### Create user
```sh
curl -i -H "Content-Type: application/json" -X POST -d '{"username":"jsmith","first_name" : "John", "last_name" : "Smith"}'http://localhost:8080/api/user
```

#### Update user
```
curl -i -H "Content-Type: application/json" -X PUT -d '{"first_name" : "John", "last_name" : "Doe"}' http://localhost:8080/api/user/{id}
```

#### List all users
```sh
curl -i -H "Accept: application/json" -H "Content-Type: application/json" -X GET http://localhost:8080/api/user
```

#### Get User info
```sh
curl -i -H "Accept: application/json" -H "Content-Type: application/json" -X GET http://localhost:8080/api/user/{id}
```
Expecting this structure (for the User):
```
{ 
  "id": 1,
  "username": "jsmith",
  "first_name": "James",
  "last_name": "Smith"
}
```

#### Create Task
```sh
curl -i -H "Content-Type: application/json" -X POST -d '{"name":"My task","description" : "Description of task", "date_time" : "2016-05-25 14:25:00"}' http://localhost:8080/api/user/{user_id}/task
```

#### Update Task
On window curl this does not work, you need to use postman or similar
```sh
curl -i -H "Content-Type: application/json" -X PUT -d '{"name":"My updated task"}' http://localhost:8080/api/user/{user_id}/task/{task_id}
```

#### Delete Task
```sh
curl -i -H "Content-Type: application/json" -X DELETE http://localhost:8080/api/user/{user_id}/task/{task_id}
```

#### Get Task Info
```sh
curl -i -H "Accept: application/json" -H "Content-Type: application/json" -X GET http://localhost:8080/api/user/{user_id}/task/{task_id}
```

#### List all tasks for a user

```sh
curl -i -H "Accept: application/json" -H "Content-Type: application/json" -X GET http://localhost:8080/api/user/{user_id}/task
```