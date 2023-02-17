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


// Return the three music companies with the highest number of employees. Ensure consistent sort order.
db.companies
  .find({ category_code: "music" })
  .sort({ number_of_employees: -1, _id: 1 })
  .limit(3);
  
// Return data on all music companies, sorted alphabetically from A to Z. Ensure consistent sort order
db.companies.find({ category_code: "music" }).sort({ name: 1, _id: 1 });

// Return data on all music companies, sorted alphabetically from A to Z.
db.companies.find({ category_code: "music" }).sort({ name: 1 });


Return the data on the three most recent sales made from the London store that included one or more of the following items: a laptop, a backpack or printer paper.

db.sales.find({ "items.name": { $in: ["laptop", "backpack", "printer paper"] }, "storeLocation": "London", }).sort({ saleDate: -1, }).limit(3)


// Return all restaurant inspections - business name, result, and _id fields only
db.inspections.find(
  { sector: "Restaurant - 818" },
  { business_name: 1, result: 1 }
)

// Return all inspections with result of "Pass" or "Warning" - exclude date and zip code
db.inspections.find(
  { result: { $in: ["Pass", "Warning"] } },
  { date: 0, "address.zip": 0 }
)

Returning Specific Data from a Query in MongoDB
Review the following code, which demonstrates how to return selected fields from a query.

Add a Projection Document
To specify fields to include or exclude in the result set, add a projection document as the second parameter in the call to db.collection.find().

Syntax:

db.collection.find( <query>, <projection> )
Include a Field
To include a field, set its value to 1 in the projection document.

Syntax:

db.collection.find( <query>, { <field> : 1 })
Example:

// Return all restaurant inspections - business name, result, and _id fields only
db.inspections.find(
  { sector: "Restaurant - 818" },
  { business_name: 1, result: 1 }
)
Exclude a Field
To exclude a field, set its value to 0 in the projection document.

Syntax:

db.collection.find(query, { <field> : 0, <field>: 0 })
Example:

// Return all inspections with result of "Pass" or "Warning" - exclude date and zip code
db.inspections.find(
  { result: { $in: ["Pass", "Warning"] } },
  { date: 0, "address.zip": 0 }
)
While the _id field is included by default, it can be suppressed by setting its value to 0 in any projection.

// Return all restaurant inspections - business name and result fields only
db.inspections.find(
  { sector: "Restaurant - 818" },
  { business_name: 1, result: 1, _id: 0 }
)

db.sales.find({storeLocation:"Denver"},{storeLocation:1,saleDate:1,purchaseMethod:1});


Find the data on sales to customers less than 30 years old in which the customer satisfaction rating was greater than three. Return only the customer's age and satisfaction rating, the sale date and store location. Do not include the _id field. 


db.sales.find({ storeLocation: { $in: ["Seattle", "New York"] }, }, { couponUsed: 0, purchaseMethod: 0, customer: 0, })

// Count number of docs in trip collection
db.trips.countDocuments({})
// Count number of trips over 120 minutes by subscribers
db.trips.countDocuments({ tripduration: { $gt: 120 }, usertype: "Subscriber" })

db.sales.countDocuments({storeLocation:"Denver",couponUsed:true});
db.sales.countDocuments({"item.name":"laptop","item.price": {$lt:600}});


AGGREGATE : 

The following aggregation pipeline finds the documents with a field named "state" that matches a value "CA" and then groups those documents by the group key "$city" and shows the total number of zip codes in the state of California.

db.zips.aggregate([
{   
   $match: { 
      state: "CA"
    },
   $group: {
      _id: "$city",
      totalZips: { $count : { } }
   }
}
])

db.sightings.aggregate([
  {
    $match: {
        species_common: 'Eastern Bluebird'
    }
  }, {
    $group: {
        _id: '$location.coordinates',
        number_of_sightings: {
            $count: {}
        }
    }
  }
])

db.zips.aggregate([
{
  $sort: {
    pop: -1
  },
  $limit: {
     5
  }
}

db.sightings.aggregate([
  {
    $project: {
        _id: 0,
        species_common: 1,
        date: 1
    }
  }
])

db.birds.aggregate([
  {
    $set: {
      'class': 'bird'
    }
  }
])


db.sightings.aggregate([
{
  $match: {
    date: {
      $gt: ISODate('2022-01-01T00:00:00.000Z'),
      $lt: ISODate('2023-01-01T00:00:00.000Z')
    },
    species_common: 'Eastern Bluebird'
  }
}, {
  $count: 'bluebird_sightings_2022'
}
])

db.sightings.aggregate([
  {
    $match: {
      date: {
        $gte: ISODate('2022-01-01T00:00:00.0Z'),
        $lt: ISODate('2023-01-01T00:00:00.0Z')
      }
    }
  },
  {
    $out: 'sightings_2022'
  }
])
db.sightings_2022.findOne()


INDEXES: 

db.accounts.getIndexes()
db.accounts.createIndex({account_id: 1})


[
  { v: 2, key: { _id: 1 }, name: '_id_' },
  {
    v: 2,
    key: { 'transfers complete': 1 },
    name: 'transfers complete_1'
  },
  {
    v: 2,
    key: { last_updated: 1 },
    name: 'last_updated_1',
    expireAfterSeconds: 3600
  },
  { v: 2, key: { account_id: 1 }, name: 'account_id_1' }
]

db.accounts.explain().find({account_id:"ABC123"});

// create a multikey index on the `transfers_complete` field:
db.accounts.createIndex({ transfers_complete: 1 })

// use the explain followed by the find method to view the winningPlan for a query that finds a specific `completed_transfers` array element
db.accounts.explain().find({ transfers_complete: { $in: ["TR617907396"] } })


// create a compound index using the `account_holder`, `balance` and `account_type` fields:
db.accounts.createIndex({ account_holder: 1, balance: 1, account_type: 1 })

// Use the explain method to view the winning plan for a query
db.accounts.explain().find({ account_holder: "Andrea", balance:{ $gt :5 }}, { account_holder: 1, balance: 1, account_type:1, _id: 0}).sort({ balance: 1 })

{
  winningPlan: {
      stage: 'PROJECTION_COVERED',
      transformBy: { account_holder: 1, balance: 1, account_type: 1, _id: 0 },
      inputStage: {
        stage: 'IXSCAN',
        keyPattern: { account_holder: 1, balance: 1, account_type: 1 },
        indexName: 'account_holder_1_balance_1_account_type_1',
        isMultiKey: false,
        multiKeyPaths: { account_holder: [], balance: [], account_type: [] },
        isUnique: false,
        isSparse: false,
        isPartial: false,
        indexVersion: 2,
        direction: 'forward',
        indexBounds: {
          account_holder: [ '["Andrea", "Andrea"]' ],
          balance: [ '(5, inf.0]' ],
          account_type: [ '[MinKey, MaxKey]' ]
        }
      }
    },
    rejectedPlans: []
}

db.customers.dropIndex(
  'active_1_birthdate_-1_name_1'
)


ATLAS SEARCH 
SEARCH INDEX: 

    {
        "name": "sample_supplies-sales-dynamic",
        "searchAnalyzer": "lucene.standard",
        "analyzer": "lucene.standard",
        "collectionName": "sales",
        "database": "sample_supplies",
        "mappings": {
            "dynamic": true
        }
    }
    
    atlas clusters search indexes create --clusterName myAtlasClusterEDU -f /app/search_index.json
    atlas clusters search indexes list --clusterName myAtlasClusterEDU --db sample_supplies --collection sales
    
    
 db.sales.aggregate([
  {
    $search: {
      index: 'sample_supplies-sales-dynamic',
      text: {
        query: 'notepad', path: { 'wildcard': '*' }
      }
    }
  }
])
