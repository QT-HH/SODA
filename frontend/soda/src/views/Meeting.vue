<template>
	<div>
		<div v-if="isUser">
			<v-container
				fluid
				class="sticky-box"
				style="border: 2px solid; color: #4527a0"
			>
				<p style="color: black">면접자리스트</p>
				<v-list>
					<v-list-item style="padding: 0px; text-align: center">
						<div class="text-center">
							<p style="margin: 3px">youlee602@hanmail.net</p>
							<v-chip-group mandatory>
								<v-chip color="indigo darken-3" outlined small> 예정 </v-chip>
								<v-chip color="indigo darken-3" outlined small> 진행 </v-chip>
								<v-chip color="indigo darken-3" outlined small> 완료 </v-chip>
							</v-chip-group>
						</div>
					</v-list-item>
				</v-list>
			</v-container>
		</div>
		<div style="margin: 50px"></div>
		<div class="videos-container"></div>
		<input
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
		<br />
		<br />
		<br />
		<br />
		<div id="chat-container">
			<div class="chat-output"></div>
			<input
				type="text"
				id="input-text-chat"
				placeholder="Enter Text Chat"
				v-model="chatInfo.data"
				@keyup.enter="inputChat"
			/>
			<v-btn depressed @click="inputChat">입력</v-btn>
		</div>
		<MeetingBottomBar
			v-if="meetingStart"
			@userlist="userlist"
		></MeetingBottomBar>
		<!-- <MeetingUser v-if="isUser"></MeetingUser> -->
	</div>
</template>

<script src="https://rtcmulticonnection.herokuapp.com/dist/RTCMultiConnection.min.js"></script>
<script src="https://rtcmulticonnection.herokuapp.com/socket.io/socket.io.js"></script>

<script>
import { getConfirmMeetingCode } from '@/api/meeting.js';
import MeetingBottomBar from '@/components/meeting/MeetingBottomBar.vue';
// import MeetingUser from '@/components/meeting/MeetingUser.vue';
export default {
	components: {
		MeetingBottomBar,
		// MeetingUser,
	},
	data() {
		return {
			isUser: '',
			roomid: '',
			meetingStart: false,
			connection: null,
			streaming: false,
			chatInfo: {
				data: '',
				sender: null,
			},
			publicRoomIdentifier: 'sodasoda',
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
		userlist() {
			this.isUser = !this.isUser;
		},
		async openRoom() {
			this.meetingStart = !this.meetingStart;
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
			this.connection.publicRoomIdentifier = this.publicRoomIdentifier;

			this.connection.onmessage = this.appendDIV;
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
			div.innerHTML = `${event.data.sender} : ${event.data.data}`;
			chatContainer.insertBefore(div, chatContainer.firstchild);
			div.tabIndex = 0;
			div.focus();

			document.getElementById('input-text-chat').focus();
		},
	},
};
</script>

<style>
.videos-container video {
	width: 500px;
	margin: 10px;
}
.sticky-box {
	width: 200px;
	height: 570px;
	float: left;
	background-color: white;
	border-radius: 10px;
	padding: 100%, 0%;
}
</style>
