import axios from "axios";
const crypto = require("crypto");
const algorithm = "aes-256-cbc";
const key = process.env.VUE_APP_CRYPTO_KEY;
const iv = process.env.VUE_APP_CRYPTO_IV;

// encrypt data
export const encryptinformation = item => {
  const cipher = crypto.createCipheriv(algorithm, key, iv);
  let encrypted = cipher.update(item, "utf8", "hex");
  encrypted += cipher.final("hex");
  return encrypted;
};

// decrypt data
export const decryptinformation = item => {
  const decipher = crypto.createDecipheriv(algorithm, key, iv);
  var decrypted = decipher.update(item, "hex", "utf8") + decipher.final("utf8");
  return decrypted;
};

// save one meeting
export const saveonemeeting = async item => {
  let data = {
    name: item.user_name,
    meetingid: item.meeting_url,
    sessionid: item.socket_id
  };
  let request = {
    url: "https://webrtc-app-backend-vue.herokuapp.com/meet",
    method: "post",
    headers: {
      "Content-type": "application/json"
    },
    data: JSON.stringify(data)
  };
  const response = await axios(request);
  return response.data;
};

// save one content (message OR attachment)
export const saveonesession = async item => {
  let data = {
    message: item.message,
    attachment: item.attachment,
    sessionid: item.sessionid, // user session id
    meetingid: item.meetingid // meeting id
  };
  let request = {
    url: "https://webrtc-app-backend-vue.herokuapp.com/session",
    method: "post",
    headers: {
      "Content-type": "application/json"
    },
    data: JSON.stringify(data)
  };
  const response = await axios(request);
  return response.data;
};

/**
 * FETCH ACTIONS
 * ================
 */

// fetch all meetings using meetingid
export const fetchallmeetings = async item => {
  let request = {
    url: "https://webrtc-app-backend-vue.herokuapp.com/meet/all/" + item,
    method: "get",
    headers: {
      "Content-type": "application/json"
    }
  };
  const response = await axios(request);
  return response.data;
};

// fetch one session meeting using socket.id
export const fetchonemeeting = async item => {
  let request = {
    url: "https://webrtc-app-backend-vue.herokuapp.com/meet/" + item,
    method: "get",
    headers: {
      "Content-type": "application/json"
    }
  };
  const response = await axios(request);
  return response.data;
};

// fetch all sessions using meetingid
export const fetchallsessions = async item => {
  let request = {
    url: "https://webrtc-app-backend-vue.herokuapp.com/session/all/" + item,
    method: "get",
    headers: {
      "Content-type": "application/json"
    }
  };
  const response = await axios(request);
  return response.data;
};

// fetch one session by socket.id
export const fetchonesession = async item => {
  let request = {
    url: "https://webrtc-app-backend-vue.herokuapp.com/session/" + item,
    method: "get",
    headers: {
      "Content-type": "application/json"
    }
  };
  const response = await axios(request);
  return response.data;
};

/**
 * DELETE ACTIONS
 * ===============
 */

// delete one meeting
export const deleteonemeeting = async item => {
  let request = {
    url: "https://webrtc-app-backend-vue.herokuapp.com/meet/" + item,
    method: "delete",
    headers: {
      "Content-type": "application/json"
    }
  };
  const response = await axios(request);
  return response.data;
};

// delete all sessions
export const deleteallsession = async item => {
  let request = {
    url: "https://webrtc-app-backend-vue.herokuapp.com/session/all/" + item,
    method: "delete",
    headers: {
      "Content-type": "application/json"
    }
  };
  const response = await axios(request);
  return response.data;
};

// delete one session
export const deleteonesession = async item => {
  let request = {
    url: "https://webrtc-app-backend-vue.herokuapp.com/meet/" + item,
    method: "delete",
    headers: {
      "Content-type": "application/json"
    }
  };
  const response = await axios(request);
  return response.data;
};
