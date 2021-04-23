const express = require("express")
const router = express.Router()
const session = require("../controller/session.controller");

// Create a new session
router.post("/", session.createonesession);

// find all session
router.get("/all/:id", session.findallsession);

// retrieve one session for the meeting
router.get("/:id", session.findonesession);

// delete a single session
router.delete("/:id", session.deleteonesession)

// delete all session for spefic session
router.delete("/all/:id", session.deleteallsession);


module.exports = router