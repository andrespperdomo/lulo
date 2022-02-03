# Lulo bank

## Tecnologias utilizadas

springboot, docker, maven, junit, mockito y swagger

### Ejecución

Docker:

* docker build -t lulo .
* docker run -p 8085:8080 lulo

Endpoint:
* http://locahost:8085/v1/account 
* http://locahost:8085/v1/transaction

Swagger:
* http://localhost:8085/swagger-ui.html

Springboot

* mvn clean
* mvn package
* Dentro de target java -jar lulobank.jar

Endpoint:
* http://locahost:8080/v1/account
* http://locahost:8080/v1/transaction

Swagger:
* http://localhost:8080/swagger-ui.html

### Request
* account:
{
	"account":{
       "id":1,
       "active-card":true,
       "available-limit":50
       }
}
* transaction:
{
  "transaction": {
    "amount": 20,
    "id": 1,
    "merchant": "Burger king",
    "time": "2019-02-13T10:00:00.000Z"
  }
}
