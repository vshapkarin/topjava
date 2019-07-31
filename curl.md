cURLs for Windows cmd:

Admin commands:
  Get all users:
  curl http://localhost:8080/topjava/rest/admin/users

  Get user with id 100000:
  curl http://localhost:8080/topjava/rest/admin/users/100000
    
  Create new user:
  curl -H "Content-Type: application/json" -X POST http://localhost:8080/topjava/rest/admin/users -d "{\"name\":\"NewUser\",\"email\": \"newuser@gmail.com\",\"password\":\"password\",\"roles\":[\"ROLE_USER\"]}"

  Delete user with id 100000:
  curl -X DELETE http://localhost:8080/topjava/rest/admin/users/100000
    
  Update user with id 100001:
  curl -H "Content-Type: application/json" -X PUT http://localhost:8080/topjava/rest/admin/users/100001 -d "{\"name\":\"UpdatedUser\",\"email\": \"updt@gmail.com\",\"password\":\"password\",\"roles\":[\"ROLE_USER\"]}"
   
  Get by email:
  curl http://localhost:8080/topjava/rest/admin/users/by?email=updt@gmail.com 

User commands:
  Get profile:
  curl http://localhost:8080/topjava/rest/profile

  Update profile:
  curl -H "Content-Type: application/json" -X PUT http://localhost:8080/topjava/rest/profile -d "{\"name\":\"UpdatedProfile\",\"email\": \"updtprfl@gmail.com\",\"password\":\"password\",\"roles\":[\"ROLE_USER\"]}"

  Delete profile:
  curl -X DELETE http://localhost:8080/topjava/rest/profile
  
Meal commands:
  Get all meals:
  curl http://localhost:8080/topjava/rest/meals

  Delete meal with id 100002:
  curl -X DELETE http://localhost:8080/topjava/rest/meals/100002

  Get meals with filtering:
  curl -H "Content-Type: application/json" -X POST http://localhost:8080/topjava/rest/meals/filter?startDate=2015-05-31^&endDate=2015-05-31^&startTime=15%3A00^&endTime=23%3A00

  Update meal with id 100006:
  curl -H "Content-Type: application/json" -X PUT http://localhost:8080/topjava/rest/meals/100006 -d "{\"dateTime\":\"2015-05-31T22:00:00\",\"description\": \"UPDATED\",\"calories\":2000}"

  Create new meal:
  curl -H "Content-Type: application/json" -X POST http://localhost:8080/topjava/rest/meals -d "{\"dateTime\":\"2018-05-31T22:00:00\",\"description\": \"CREATED\",\"calories\":2000}"
