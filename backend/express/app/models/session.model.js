module.exports = mongoose => {
  const Session = mongoose.model(
    "Session",
    mongoose.Schema(
      {
        message: String,
        attachment: String,
        sessionid: String, // user session id
        meetingid: String // meeting id
      },
      { timestamps: true }
    )
  );
  return Session;
};