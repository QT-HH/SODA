const db = require("../models");
const Meet = db.meet;

// create a meet
exports.createonemeet = (req, res) => {
  const meet = new Meet({
    name: req.body.name ? req.body.name : "User",
    meetingid: req.body.meetingid,
    sessionid: req.body.sessionid,
  });

  // Save new meet in the database
  meet
    .save(meet)
    .then(data => {
      res.send(data);
    })
    .catch(err => {
      res.status(500).send({
        message:
          err.message || "Some error occurred while creating the meeting."
      });
    });
};

// retrieve all meet from the database for that meeting.
exports.findallmeet = (req, res) => {
  const id = req.params.id;
  var condition = { meetingid: id }

  Meet.find(condition)
    .then(data => {
      res.send(data);
    })
    .catch(err => {
      res.status(500).send({
        message:
          err.message || "Some error occurred while retrieving meets."
      });
    });

};

// retrieve one meet from the database for that meeting.
exports.findonemeet = (req, res) => {
  const id = req.params.id;
  var condition = { sessionid: id }

  Meet.findOne(condition)
    .then(data => {
      res.send(data);
    })
    .catch(err => {
      res.status(500).send({
        message:
          err.message || "Some error occurred while retrieving sessions."
      });
    });

};

// delete a meet with the specified id in the request
exports.deleteonemeet = (req, res) => {
  const id = req.params.id;
  var condition = { sessionid: id }

  Meet.deleteOne(condition)
    .then(data => {
      if (!data) {
        res.status(404).send({
          message: `Cannot delete meet with id=${id}!`
        });
      } else {
        res.send({
          message: "Meet was deleted successfully!"
        });
      }
    })
    .catch(err => {
      res.status(500).send({
        message: "Could not delete meet with id=" + id
      });
    });

};
