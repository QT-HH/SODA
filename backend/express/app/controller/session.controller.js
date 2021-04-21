const db = require("../models");
const Session = db.session;

// create a session
exports.createonesession = (req, res) => {
  // check for message or attachments
  if(!req.body.message && !req.body.attachment){
    return res.status(404).send({ message: "No message or attachment!" });
  }
  // session
  const session = new Session({
    message: req.body.message,
    attachment: req.body.attachment,
    meetingid: req.body.meetingid,
    sessionid: req.body.sessionid
  });

  // Save new session in the database
  session
    .save(session)
    .then(data => {
      res.send(data);
    })
    .catch(err => {
      res.status(500).send({
        message:
          err.message || "Some error occurred while creating the new message."
      });
    });
};

// retrieve all session from the database for that meeting.
exports.findallsession = (req, res) => {
  const id = req.params.id;
  var condition = { meetingid: id }

  Session.find(condition)
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

// retrieve one session from the database for that meeting.
exports.findonesession = (req, res) => {
  const id = req.params.id;
  //var condition = { sessionid: id }

  Session.findById(id)
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

// delete a session with the specified id in the request
exports.deleteonesession = (req, res) => {
  const id = req.params.id;

  Session.findByIdAndRemove(id)
    .then(data => {
      if (!data) {
        res.status(404).send({
          message: `Cannot delete contents with id=${id}!`
        });
      } else {
        res.send({
          message: "Session was deleted successfully!"
        });
      }
    })
    .catch(err => {
      res.status(500).send({
        message: "Could not delete session with id=" + id
      });
    });

};

// delete all session for the meeting.
exports.deleteallsession = (req, res) => {
  const id = req.params.id;
  var condition = { sessionid: id }
  
  Session.deleteMany(condition)
  .then(data => {
    res.send({
      message: `${data.deletedCount} Sessions were deleted successfully!`
    });
  })
  .catch(err => {
    res.status(500).send({
      message:
        err.message || "Some error occurred while removing all sessions."
    });
  });
};

