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

db.birds.find({"_id" : ObjectId("6268471e613e55b82d7065d7")}) 

db.routes.find({
  $or: [{ dst_airport: "SEA" }, { src_airport: "SEA" }],
})

db.routes.find({ "airline.name": "Southwest Airlines", stops: { $gte: 1 } })


Find every document in the sales collection that meets the following criteria:
Purchased online
Used a coupon
Purchased by a customer 25 years old or younger

db.sales.find({
  couponUsed: true,
  purchaseMethod: "Online",
  "customer.age": { $lte: 25 }
})

Return every document in the sales collection that meets one of the following criteria:
Item with the name of pens
Item with a writing tag

db.sales.find({
  $or: [{ "items.name": "pens" }, { "items.tags": "writing" }],
})

REPLACE THE DOCUMENT. 

db.books.replaceOne(
  {
    _id: ObjectId("6282afeb441a74a98dbbec4e"),
  },
  {
    title: "Data Science Fundamentals for Python and MongoDB",
    isbn: "1484235967",
    publishedDate: new Date("2018-5-10"),
    thumbnailUrl:
      "https://m.media-amazon.com/images/I/71opmUBc2wL._AC_UY218_.jpg",
    authors: ["David Paper"],
    categories: ["Data Science"],
  }
)


Atlas atlas-d3opcw-shard-0 [primary] bird_data> db.birds.find({common_name:"Northern Cardinal"});
[
  {
    _id: ObjectId("6286809e2f3fa87b7d86dccd"),
    common_name: 'Northern Cardinal',
    scientific_name: 'Cardinalis cardinalis',
    wingspan_cm: 25.32,
    habitat: 'woodlands',
    diet: [ 'grain', 'seeds', 'fruit' ],
    last_seen: ISODate("2022-05-19T20:20:44.083Z")
  }
]
Atlas atlas-d3opcw-shard-0 [primary] bird_data> db.birds.replaceOne({_id: ObjectId("6286809e2f3fa87b7d86dccd")}, {
...   "common_name": "Morning Dove",
...   "scientific_name": "Zenaida macroura",
...   "wingspan_cm": 37.23,
...   "habitat": ["urban areas", "farms", "grassland"],
...   "diet": ["seeds"],
... });
{
  acknowledged: true,
  insertedId: null,
  matchedCount: 1,
  modifiedCount: 1,
  upsertedCount: 0
}

db.birds.findOne({common_name:"Canada Goose"});
{
  _id: ObjectId("6268413c613e55b82d7065d2"),
  common_name: 'Canada Goose',
  scientific_name: 'Branta canadensis',
  wingspan_cm: 152.4,
  habitat: 'wetlands',
  diet: [ 'grass', 'algae' ],
  last_seen: ISODate("2022-05-19T20:20:44.083Z")
}


db.birds.updateOne({common_name:"Canada Goose"},{$set:{tags:["geese","herbivore","migration"]}});
{
  acknowledged: true,
  insertedId: null,
  matchedCount: 1,
  modifiedCount: 1,
  upsertedCount: 0
}

db.birds.findOne({common_name:"Canada Goose"});
{
  _id: ObjectId("6268413c613e55b82d7065d2"),
  common_name: 'Canada Goose',
  scientific_name: 'Branta canadensis',
  wingspan_cm: 152.4,
  habitat: 'wetlands',
  diet: [ 'grass', 'algae' ],
  last_seen: ISODate("2022-05-19T20:20:44.083Z"),
  tags: [ 'geese', 'herbivore', 'migration' ]
}

db.birds.updateOne({"_id" : ObjectId("6268471e613e55b82d7065d7")}, {$push: {diet: ["newts", "opossum", "skunks", "squirrels"]}}) 
{
  acknowledged: true,
  insertedId: null,
  matchedCount: 1,
  modifiedCount: 1,
  upsertedCount: 0
}


db.birds.updateOne(
  {
    common_name: "Robin Redbreast",
  },
  {
    $inc: {
      "sightings": 1,
    },
    $set: {
      last_updated: new Date(),
    },
  },
  {
    upsert: true,
  }
)

db.podcasts.findAndModify({
  query: { _id: ObjectId("6261a92dfee1ff300dc80bf1") },
  update: { $inc: { subscribers: 1 } },
  new: true,
})

 db.birds.findAndModify({query:{common_name:"Blue Jay"}, update: {$inc: {sightings_count:1}}, new: true})
{
  _id: ObjectId("628682d92f3fa87b7d86dcce"),
  common_name: 'Blue Jay',
  scientific_name: 'Cyanocitta cristata',
  wingspan_cm: 34.17,
  habitat: 'forests',
  diet: [ 'vegetables', 'nuts', 'grains' ],
  sightings_count: 5,
  last_seen: ISODate("2022-05-19T20:20:44.083Z")
}


db.books.updateMany(
  { publishedDate: { $lt: new Date("2019-01-01") } },
  { $set: { status: "LEGACY" } }
)


db.birds.updateMany(
  {
    common_name: {
      $in: ["Blue Jay", "Grackle"],
    },
  },
  {
    $set: {
      last_seen: ISODate("2022-01-01"),
    },
  }
)

db.podcasts.deleteOne({ _id: Objectid("6282c9862acb966e76bbf20a") })

db.podcasts.deleteMany({category: “crime”})
