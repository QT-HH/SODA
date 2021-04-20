const express = require("express")
const router = express.Router()
const meet = require("../controller/meet.controller");

// Create a new blog
router.post("/", meet.createonemeet);

// retrieve all meets for the meeting
router.get("/all/:id", meet.findallmeet);

// retrieve one meets for the meeting
router.get("/:id", meet.findonemeet);

// delete a single meet
router.delete("/:id", meet.deleteonemeet)


module.exports = router