application.yml
``````
spring:
  data:
      mongodb:
        user: test
        password: passwordvalue
        uri: mongodb://host:27017/db
``````


``````node
INSERT: 

db.accounts.insertOne({ "account_id": 111333, "limit": 12000, "products": 
[ "Commodity", "Brokerage"], "last_updated": new Date() });

FIND:

db.sales.find({ storeLocation: { $in: ["London", "New York"] } })
db.sales.find({ "items.price": { $gt: 50}})
db.sales.find({ "items.price": { $lt: 50}})
db.sales.find({ "customer.age": { $lte: 65}})
db.sales.find({ "customer.age": { $gte: 65}})

$elemMatch:  Use the $elemMatch operator to find all documents that contain the specified subdocument

db.sales.find({
  items: {
    $elemMatch: { name: "laptop", price: { $gt: 800 }, quantity: { $gte: 1 } },
  },
})

db.accounts.find({ products: { $elemMatch: { $eq: "CurrencyService"}}})

db.transactions.find({
    transactions: {
      $elemMatch: { amount: { $lte: 4500 }, transaction_code: "sell" },
    },
  })
  
Use the $and operator to use multiple $or expressions in your query.

db.routes.find({
  $and: [
    { $or: [{ dst_airport: "SEA" }, { src_airport: "SEA" }] },
    { $or: [{ "airline.name": "American Airlines" }, { airplane: 320 }] },
  ]
})


db.routes.find({
  $or: [{ dst_airport: "SEA" }, { src_airport: "SEA" }],
})

db.routes.find({ "airline.name": "Southwest Airlines", stops: { $gte: 1 } })

