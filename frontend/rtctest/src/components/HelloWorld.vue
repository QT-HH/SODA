<template>
  <div class="hello">
    <h1>
      Video Conferencing using RTCMultiConnection
      <p class="no-mobile">
        Multi-user (many-to-many) video chat using mesh networking model.
      </p>
    </h1>

    <section class="make-center">
      <div>
        <label><input type="checkbox" id="record-entire-conference"> Record Entire Conference In The Browser?</label>
        <span id="recording-status" style="display: none;"></span>
        <button id="btn-stop-recording" style="display: none;">Stop Recording</button>
        <br><br>

        <input type="text" id="room-id" value="abcdef" autocorrect=off autocapitalize=off size=20>
        <button id="open-room" @click="openroom">Open Room</button>
        <button id="join-room" @click="joinroom">Join Room</button>
        <button id="open-or-join-room" @click="openorjoinroom">Auto Open Or Join Room</button>
      </div>

      <div id="videos-container" style="margin: 20px 0;"></div>

      <div id="room-urls" style="text-align: center;display: none;background: #F1EDED;margin: 15px -10px;border: 1px solid rgb(189, 189, 189);border-left: 0;border-right: 0;"></div>
    </section>
  </div>
</template>

<script>
import * as RTCMultiConnection from 'rtcmulticonnection'

export default {
  name: 'HelloWorld',
  data: () => ({
    connection : Object,
    // videoList: [],
  }),
  mounted() {
    this.connection = new RTCMultiConnection();
    this.connection.socketURL = 'https://rtcmulticonnection.herokuapp.com:443/'
    this.connection.socketMessageEvent = 'video-conference-demo';

    // this.connection.session = {
    //   audio: true,
    //   video: true,
    // };

    // this.connection.sdpConstraints.mandatory = {
    //   OfferToReceiveAudio: true,
    //   OfferToReceiveVideo: true
    // };

    // this.connection.iceServers = [{
    //   'urls': [
    //       'stun:stun.l.google.com:19302',
    //       'stun:stun1.l.google.com:19302',
    //       'stun:stun2.l.google.com:19302',
    //       'stun:stun.l.google.com:19302?transport=udp',
    //   ]
    // }];

    // this.connection.autoCreateMediaElemnt = false;
    // this.connection.onstream = function(event) {
    //   this.videoList.push({
    //     id: event.streamid,
    //     srcObject: event.stream,
    //     muted: event.type === 'local'
    //   })
    // };

    // this.connection.onstreamended = function(event) {
    //   let newList = [];
    //   this.videoList.forEach(function(item) {
    //     if(item.id !== event.streamid) {
    //       newList.push(item);
    //     }
    //   });
    //   this.videoList = newList;
    // };

  },
  methods: {
    disableInputButtons() {
      // document.getElementById('room-id').onkeyup();

      document.getElementById('open-or-join-room').disabled = true;
      document.getElementById('open-room').disabled = true;
      document.getElementById('join-room').disabled = true;
      document.getElementById('room-id').disabled = true;
    },
    showRoomURL(roomid) {
      let roomHashURL = '#' + roomid;
      let roomQueryStringURL = '?roomid=' + roomid;

      let html = '<h2>Unique URL for your room:</h2><br>'

      html += 'Hash URL: <a href="' + roomHashURL + '" target="_blank">' + roomHashURL + '</a>';
      html += '<br>';
      html += 'QueryString URL: <a href="' + roomQueryStringURL + '" target="_blank">' + roomQueryStringURL + '</a>';

      const roomURLsDiv = document.getElementById('room-urls');
      roomURLsDiv.innerHTML = html;

      roomURLsDiv.style.display = 'block'
    },
    openroom() {
      this.disableInputButtons();
      this.connection.open(document.getElementById('room-id').value, function() {
        this.showRoomURL(this.connection.sessionid);
      });
    },
    joinroom() {
      this.disableInputButtons();
      this.connection.join(document.getElementById('room-id').value);
    },
    openorjoinroom(){
      this.disableInputButtons();
      this.connection.openOrJoin(document.getElementById('room-id').value, function(isRoomExist, roomid) {
        if (isRoomExist === false && this.connection.isInitiator === true) {
          this.showRoomURL(roomid);
        }
      })
    },
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>

</style>
