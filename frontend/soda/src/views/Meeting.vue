<template>
	<div>
		<div class="videos-container"></div>
		<input v-model="roomid" placeholder="Unique room ID" />
		<div v-if="!streaming">
			<v-btn depressed color="primary" @click="openRoom">open or join</v-btn>
		</div>
		<div v-if="streaming">
			<v-btn depressed color="warning" @click="outRoom">퇴장</v-btn>
		</div>
		<div v-if="streaming">
			<v-btn depressed @click="screenOff">화면OFF</v-btn>
			<v-btn depressed @click="screenOn">화면ON</v-btn>
			<v-btn depressed @click="checkVideo">체크</v-btn>
		</div>
		<div v-if="streaming">
			<v-btn depressed @click="voiceOff">마이크OFF</v-btn>
			<v-btn depressed @click="voiceOn">마이크ON</v-btn>
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
			streaming: false,
		};
	},
	beforeDestroy() {
		this.outRoom();
	},
	// mounted() {
	// 	const test = require('https://rtcmulticonnection.herokuapp.com/dist/RTCMultiConnection.min.js');
	// 	console.log(test);
	// },
	methods: {
		async openRoom() {
			await getConfirmMeetingCode(this.roomid)
				.then(res => {
					if (res.data) {
						console.log(res.data);
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
						this.streaming = !this.streaming;
						this.connection = new RTCMultiConnection();
						this.connection.session = {
							audio: true,
							video: true,
							data: true,
						};

						this.connection.socketURL = `https://rtcmulticonnection.herokuapp.com:443/`;
						this.connection.sdpConstraints.mandatory = {
							OfferToReceiveAudio: true,
							OfferToReceiveVideo: true,
						};
						// this.connection.openOrJoin(this.roomid);
						this.connection.checkPresence(
							this.roomid,
							function (isRoomOpened, roomid) {
								if (isRoomExist === true) {
									connection.join(roomid);
								} else {
									connection.open(roomid);
								}
							},
						);
						this.connection.videosContainer = document.querySelector(
							'.videos-container',
						);
						console.log('participants : ', this.connection.sessionid);
					} else {
						alert('유효하지 않은 미팅코드입니다.');
					}
				})
				.catch(err => {
					console.log('에러');
				});
		},
		outRoom() {
			this.connection.getAllParticipants().forEach(participantId => {
				console.log(participantId);
				this.connection.disconnectWith(participantId);
			});
			// this.connection.disconnectWith(this.connection.userid);

			this.connection.attachStreams.forEach(function (localStream) {
				localStream.stop();
			});

			this.connection.closeSocket();
			this.connection = null;
			this.streaming = !this.streaming;
			this.roomid = '';
		},
		screenOff() {
			const event = this.findMyVideo();
			event.stream.mute('video');
		},
		screenOn() {
			const event = this.findMyVideo();
			event.stream.unmute('video');
		},
		voiceOff() {
			const event = this.findMyVideo();
			event.stream.mute('audio');
		},
		voiceOn() {
			const event = this.findMyVideo();
			event.stream.unmute('audio');
		},
		findMyVideo() {
			let events = this.connection.streamEvents.selectAll();
			let event = events.find(event => {
				return event.type === 'local';
			});
			return event;
		},
		checkVideo() {
			let video = this.connection.streamEvents.selectAll();
			console.log(video);
		},
	},
};
</script>

<style>
.videos-container video {
	width: 500px;
	margin: 10px;
}
</style>
