module.exports = mongoose => {
  const Meet = mongoose.model(
    "Meet",
    mongoose.Schema(
      {
        name: String, // session name
        meetingid: String, // meeting id
        sessionid: String, // socket id
      },
      { timestamps: true }
    )
  );
  return Meet;
};