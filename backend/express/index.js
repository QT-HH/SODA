const express = require("express");
const bodyParser = require("body-parser");
const cors = require("cors");
require('dotenv').config()

const app = express();

// parse application/json
app.use(bodyParser.json())

// parse application/x-www-form-urlencoded
app.use(bodyParser.urlencoded({ extended: true }))

// use cors options
app.use(cors())
app.use(require('serve-static')(__dirname + '/../../public'));
// socket io
const httpServer = require("http").createServer(app);
const io = require("socket.io")(httpServer, {
  cors: {
    origin: "*",
    methods: ["GET", "POST"]
  }
});

const Meet = Meet.db
io.on("connection", (socket) => {

  // join a new meeting
  socket.on('joined', async (data) => {
    let meetingid = JSON.parse(data).meetingid
    //let username = JSON.parse(data).username
    //console.log("joined", meetingid)
    //const new_meet = {
       //name: username,
       //meetingid: meetingid,
       //sessionid: socket.id
    //}
    //await Meet.createonemeet(meet)
    if(meetingid !== null){
      socket.join(meetingid);
      // notify everyone of a new user
      socket.to(`${meetingid}`).emit("joined", `${socket.id}`)
    }
  });

  socket.on('offer_message', (data) => {
    let sessionid = JSON.parse(data).offerto
    console.log("[OFFER] Send to session id", sessionid)
    if(data !== null){
      // notify everyone of a new user
      io.to(`${sessionid}`).emit("offer_message", `${data}`)
    }
  });


  socket.on('answer_message', (data) => {
    let sessionid = JSON.parse(data).offerto
    console.log("[ANSWER] Send to session id", sessionid)
    if(data !== null){
      // notify everyone of a new user
      io.to(`${sessionid}`).emit("answer_message", `${data}`)
    }
  });


  // send a message
  socket.on('send', (data) => {
    let meetingid = JSON.parse(data).meetingid
    let sessionid = JSON.parse(data).sessionid
    if(data !== null){
      socket.join(meetingid);
      // notify everyone of a new message
      socket.to(`${meetingid}`).emit("sendmessage", `${sessionid}`)
    }
  });

  // disconnect
  socket.on("disconnect", (data) => {
    if(data !== null){
      // notify everyone of a user has exited
      socket.to(`${data}`).emit("exitmeeting",  'someone has exited')
    }
  });
});



// mongo db database connection
const db = require("./app/models");
db.mongoose
  .connect(db.url, {
    useNewUrlParser: true,
    useUnifiedTopology: true,
    useFindAndModify: false,
    useCreateIndex: true
  })
  .then(() => {
    console.log("Connected to the database!");
  })
  .catch(err => {
    console.log("Cannot connect to the database!", err);
    process.exit();
  });


// routes
const meet = require('./app/routes/meet')
const session = require('./app/routes/session')
app.use('/meet', meet)
app.use('/session', session)

// listening port
const PORT = process.env.PORT || 3000;
httpServer.listen(PORT);
// app.listen(PORT, () => {
//     console.log(`Server is running on port ${PORT}.`);
// });