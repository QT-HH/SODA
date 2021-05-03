<template>
	<div>
		<div style="margin: 50px"></div>
		<input
			v-if="!streaming"
			v-model="roomid"
			placeholder="Unique room ID"
			@keyup.enter="openRoom"
		/>
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
		<div v-if="streaming">
			<v-btn depressed @click="chatOn">채팅ON</v-btn>
		</div>
		<br />
		<br />
		<br />
		<br />

		<v-sheet height="500" class="overflow-hidden" style="position: relative">
			<v-container class="fill-height">
				<v-row align="center" justify="center">
					<div class="videos-container"></div>
				</v-row>
			</v-container>
			<div id="chat-container" v-show="streaming && chatting">
				<v-navigation-drawer v-model="chatting" absolute>
					<v-list-item>
						<!-- <v-list-item-avatar>
						<v-img src="https://randomuser.me/api/portraits/men/78.jpg"></v-img>
					</v-list-item-avatar> -->

						<v-list-item-content>
							<v-list-item-title>Chat</v-list-item-title>
						</v-list-item-content>
					</v-list-item>

					<v-divider></v-divider>

					<div class="chat-output"></div>

					<div class="bottom">
						<v-divider></v-divider>
						<input
							type="text"
							id="input-text-chat"
							placeholder="Enter Text Chat"
							v-model="chatInfo.data"
							@keyup.enter="inputChat"
						/>
						<v-btn depressed @click="inputChat">입력</v-btn>
					</div>
				</v-navigation-drawer>
			</div>
		</v-sheet>
		<div>
			<button @click="updateRoomList">방정보 새로고침</button>
			<div id="rooms-list"></div>
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
			chatInfo: {
				data: '',
				sender: null,
			},
			chatting: false,

			drawer: null,
			items: [
				{ title: 'Home', icon: 'mdi-view-dashboard' },
				{ title: 'About', icon: 'mdi-forum' },
			],
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
			if (this.connection) {
				if (this.connection.sessionid !== this.roomid) {
					this.outRoom();
				} else {
					console.log('already');
					return;
				}
			}
			this.streaming = !this.streaming;
			this.$store.state.meetingOn = this.streaming;
			this.connection = new RTCMultiConnection();
			this.chatInfo.sender = this.connection.userid;
			this.connection.autoCloseEntireSession = true;
			this.connection.socketMessageEvent = this.roomid;
			this.connection.publicRoomIdentifier = this.roomid;

			this.connection.onmessage = this.appendDIV;
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
			this.connection.openOrJoin(this.roomid);
			this.connection.videosContainer = document.querySelector(
				'.videos-container',
			);
			// console.log('participants : ', this.connection.sessionid);
			// await getConfirmMeetingCode(this.roomid)
			// 	.then(res => {
			// 		// console.log(res);
			// 		// if (res.data) {
			// 		// 	console.log(res.data);
			// 		// } else {
			// 		// 	alert('유효하지 않은 미팅코드입니다.');
			// 		// }
			// 	})
			// 	.catch(err => {
			// 		console.log('에러');
			// 	});
		},
		outRoom() {
			// if (this.connection.isInitiator) {
			// 	this.connection.socket.emit('close-socket');
			// }
			this.connection.getAllParticipants().forEach(participantId => {
				this.connection.disconnectWith(participantId);
			});

			this.connection.attachStreams.forEach(function (localStream) {
				localStream.stop();
			});

			this.connection.closeSocket();
			this.connection = null;
			this.streaming = !this.streaming;
			this.$store.state.meetingOn = this.streaming;
			this.roomid = '';
			var el = document.getElementById('apdiv');
			el.remove();
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
		chatOn() {
			this.chatting = !this.chatting;
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
			// console.log(video);
		},
		inputChat() {
			const myChat = {
				data: this.chatInfo,
			};
			if (!!myChat.data.data) {
				this.connection.send(myChat.data);
				this.appendDIV(myChat);
				this.chatInfo.data = '';
			}
		},
		appendDIV(event) {
			// console.log(event);
			const chatContainer = document.querySelector('.chat-output');
			let div = document.createElement('div');
			div.setAttribute('id', 'apdiv');
			div.innerHTML = `${event.data.sender} : ${event.data.data}`;
			chatContainer.insertBefore(div, chatContainer.firstchild);
			div.tabIndex = 0;
			div.focus();

			document.getElementById('input-text-chat').focus();
		},
		updateRoomList() {
			this.connection.socket.emit(
				'get-public-rooms',
				this.connection.publicRoomIidentifier,
				function (listOfRooms) {
					console.log(window.location);
					console.log(listOfRooms);
					// listOfRooms.forEach(function (room) {
					// 	console.log(roomid);
					// });
				},
			);
		},
		// updateListOfRooms(rooms) {
		// 	console.log(rooms);
		// },
	},
};
</script>

<style>
.videos-container video {
	width: 500px;
	margin: 10px;
}

.bottom {
	position: absolute;
	bottom: 0;
}
</style>
