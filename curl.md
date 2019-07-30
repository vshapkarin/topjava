cURLs for Windows cmd:

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
