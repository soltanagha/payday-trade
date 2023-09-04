IAM-MS is responsible for authentication and authorization services. After a user signs up, an email is sent to the user's email address for account activation. This email handling is conducted in the Email-MS service, where the email-verification topic is consumed, and an email is sent. Upon activation, a new event is produced, and the user is activated in the IAM-MS service. Then, IAM-MS generates an event to create the user's account balance in the Order-MS service. Users can interact with these services through the following endpoints:

Sign-up endpoint:
```
POST http://localhost:8668/api/auth/signup 
{
    "firstName": "spring",
    "lastName": "security",
    "email": "soltanagha.huseynov@gmail.com",
    "password": "12345"
}
```

Sign-in endpoint:
```
POST http://localhost:8668/api/auth/signin
{
    "email": "soltanagha.huseynov@gmail.com",
    "password": "12345"
}
```
Retrieve account balance lists:

GET http://localhost:8243/api/account?email=soltanagha.huseynov@gmail.com
Header: Authorization: Bearer fhjaiowe849234


Order creation BUY endpoint:
```
POST http://localhost:8143/api/orders
Header: Authorization: Bearer fhjaiowe849234
{
    "toSymbol": "AAPL",
    "fromSymbol": "AZN",
    "price": 300,
    "amount": 300.0,
    "orderType": "BUY",
    "isOpen": true,
    "isReserved": false
}
```

Order creation SELL endpoint:
```
POST http://localhost:8143/api/orders
Header: Authorization: Bearer fhjaiowe849234
{
    "toSymbol": "EUR",
    "fromSymbol": "AAPL",
    "price": 120,
    "amount": 1,
    "orderType": "SELL",
    "isOpen": true,
    "isReserved": false
}
```

Order retrieve endpoint:
```
GET http://localhost:8143/api/orders
Header: Authorization: Bearer fhjaiowe849234
```

Stock retrieve endpoint:
```
GET http://localhost:8889/api/stock
Header: Authorization: Bearer fhjaiowe849234
```


A new order can be created through the above endpoint.

Previously, Alpha Vantage API was used for stock data, but due to limitations, a switch was made to randomly generated stock prices. This production of stock data is done in the Stock-MS service every 30 seconds with a scheduler. The Order-MS service consumes this event and checks appropriate orders. If an order matches the price, the operation proceeds.

Apache Kafka is configured at `localhost:9092`.

