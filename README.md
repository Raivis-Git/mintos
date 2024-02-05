To run application use command: mvn spring-boot:run Application will start on port 8080
Service used for exchange rates https://freecurrencyapi.com
--
Database : h2 (chosen for ease of use, so anyone can just start the project with pretty much no setup)
data.sql has some initial data about client, account, currency for startup

For DB schema versioning great tool would be liquibase, but h2 is not the best database to use it on. There are pretty much everything needed for liquibase to work, but it's commented out, as I didn't want to spend several hours on changing the project for it to work with h2 or even going away from h2
--
All requirements should be met except tests, db schema versioning and resiliency to 3rd party service is questionable
--
For populating data there are data.sql or REST(you can create account and client, no updating or deleting implemented)
currency table is populated only through data.sql. There are 3 currencies, for testing purposes I don't see any need for more (eur, gbp, usd)
To create transaction for account REST should be used. api/account/balance/transfer 
--
To use h2 database console open http://localhost:8080/h2-console 
driver class = org.h2.Driver
jdbc url = jdbc:h2:mem:mintos
name = sa
password = 
--
REST

GET get all client ids: http://localhost:8080/api/client/ids
GET get all client accounts: http://localhost:8080/api/account/client/{clientId}
POST creates a client, uses json: http://localhost:8080/api/client
          sample json:
          {
              "fullName":"Viens Divi",
              "email":"viens@divi.test",
              "phoneNumber":"+11111"
          }
POST creates an account using json: http://localhost:8080/api/account
          sample json:
          {
              "iso":"EUR",
              "balance":"99",
              "clientId":30
          }

POST transfer funds from one account to another, uses json: http://localhost:8080/api/account/balance/transfer
          sample json:
          {
              "fromAccountId":"11",
              "toAccountId":"12",
              "currencyIso":"gbp",
              "amount":30
          }

GET account transaction history sorted descending and using offset and limit for pagination: http://localhost:8080/api/account/transactions/{accountId}?offset=0&limit=10

return json didn't get almost any attention
--
If any question contact me
After reviewing the project I would love some feedback
