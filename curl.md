**Команды для выполнения в среде Windows**

**GetAll**:

curl http://localhost:8080/topjava/rest/meals

**GetByID**:

curl http://localhost:8080/topjava/rest/meals/100006

**GetFiltered**:

curl http://localhost:8080/topjava/rest/meals/filterSep?startDate=2015-05-31

**Delete**:

curl -H "Content-Type: application/json" -X DELETE http://localhost:8080/topjava/rest/meals/100010

**Update**:

curl -H "Content-Type: application/json" -X PUT http://localhost:8080/topjava/rest/meals/100006 -d "{\\"id\\":100006,\\"dateTime\\":\\"2019-05-31T13:00:00\\",\\"description\\": \\"Обед\\",\\"calories\\": 1000}"

**Create**:

curl -H "Content-Type: application/json" -X POST http://localhost:8080/topjava/rest/meals -d "{\\"dateTime\\":\\"2029-05-31T13:00:00\\",\\"description\": \\"Обед\\",\\"calories\\": 1000}"



