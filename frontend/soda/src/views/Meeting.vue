<template>
	<div>
		<div class="videos-container"></div>
		<input v-model="roomid" placeholder="Unique room ID" />
		<div v-if="streaming">
			<v-btn depressed color="primary" @click="openRoom">open or join</v-btn>
		</div>
		<div v-if="!streaming">
			<v-btn depressed color="warning" @click="outRoom">퇴장</v-btn>
		</div>
	</div>
</template>

<script src="https://rtcmulticonnection.herokuapp.com/dist/RTCMultiConnection.min.js"></script>
<script src="https://rtcmulticonnection.herokuapp.com/socket.io/socket.io.js"></script>

<script>
export default {
	data() {
		return {
			roomid: '',
			connection: null,
			streaming: true,
		};
	},
	beforeDestroy() {
		this.outRoom();
	},
	methods: {
		openRoom() {
			this.connection = new RTCMultiConnection();

			this.connection.session = {
				audio: true,
				video: true,
				data: true,
			};

			this.connection.socketURL =
				'https://rtcmulticonnection.herokuapp.com:443/';

			this.connection.sdpConstraints.mandatory = {
				OfferToReceiveAudio: true,
				OfferToReceiveVideo: true,
			};
			this.connection.openOrJoin(this.roomid);
			this.connection.videosContainer = document.querySelector(
				'.videos-container',
			);
			this.streaming = !this.streaming;
			console.log('participants : ', this.connection.userid);
		},
		outRoom() {
			this.connection.getAllParticipants().forEach(participantId => {
				this.connection.disconnectWith(participantId);
			});
			// this.connection.disconnectWith(this.connection.userid);

			this.connection.attachStreams.forEach(function (localStream) {
				localStream.stop();
			});

			this.connection.closeSocket();
			this.streaming = !this.streaming;
		},
	},
};
</script>

<style></style>
