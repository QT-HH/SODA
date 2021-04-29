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
import { getConfirmMeetingCode } from '@/api/meeting.js';

export default {
	data() {
		return {
			roomid: '',
			connection: null,
			streaming: true,
			identify: 'GhE8fg0hdfjIj69',
		};
	},
	beforeDestroy() {
		this.outRoom();
	},
	methods: {
		async openRoom() {
			await getConfirmMeetingCode(this.identify)
				.then(res => {
					console.log('성공');
				})
				.catch(err => {
					console.log('에러');
				});
			// if (!this.roomid) {
			// 	alert('코드를 입력해주세요!');
			// 	return;
			// }
			if (this.connection) {
				// console.log('sessionid : ', this.connection.sessionid);
				// console.log('roomid : ', this.roomid);
				if (this.connection.sessionid !== this.roomid) {
					this.outRoom();
				} else {
					console.log('already');
					return;
				}
			}

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
			this.connection.sessionid = '';
			this.streaming = !this.streaming;
			this.roomid = '';
		},
	},
};
</script>

<style></style>
