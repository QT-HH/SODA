<template>
	<div>
		<div class="videos-container"></div>
		<input v-model="roomid" placeholder="Unique room ID" />
		<v-btn depressed color="primary" @click="openRoom">open or join</v-btn>
		<v-btn depressed color="warning" @click="outRoom">퇴장</v-btn>
	</div>
</template>

<script>
export default {
	data() {
		return {
			roomid: '',
			connection: null,
		};
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
		},
		outRoom() {
			this.connection.getAllParticipants().forEach(participantId => {
				this.connection.disconnectWith(participantId);
			});

			this.connection.attachStreams.forEach(function (localStream) {
				localStream.stop();
			});

			this.connection.closeSocket();
		},
	},
};
</script>

<style></style>
