const dbConfig = require("../config/db.config");

const mongoose = require("mongoose");
mongoose.Promise = global.Promise;

const db = {};
db.mongoose = mongoose;
db.url = dbConfig.url;
// databases
db.meet = require("./meet.model.js")(mongoose);
// db.user = require("./user.model")(mongoose);
db.session = require("./session.model")(mongoose);

module.exports = db;