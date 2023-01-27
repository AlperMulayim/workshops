const { Db } = require('mongodb');
const mongoose = require('mongoose');

mongoose.Promise = global.Promise;
mongoose.set('strictQuery', false);


// Connect MongoDB at default port 27017.
mongoose.connect('mongodb+srv://myAtlasDBUser:myatlas-001@myatlasclusteredu.1pk9p0i.mongodb.net/?retryWrites=true&w=majority', {
    useNewUrlParser: true,
}, (err) => {
    if (!err) {
        console.log('MongoDB Connection Succeeded.')
    } else {
        console.log('Error in DB connection: ' + err)
    }
});


