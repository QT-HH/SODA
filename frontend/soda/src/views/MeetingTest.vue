<template>
	<div class="bgcolor">
		<div v-show="isChat" id="chat-container" class="chatBox">
			<div class="chat-output"></div>
			<div class="chat-input">
				<input
					type="text"
					id="input-text-chat"
					class="comp"
					placeholder="Enter Text Chat"
					v-model="chatInfo.data"
					@keyup.enter="inputChat"
					style="width: 70%; left: 0px"
				/>
				<v-btn
					depressed
					small
					class="comp"
					@click="inputChat"
					style="width: 30%; right: 0px"
					>입력</v-btn
				>
			</div>
		</div>
		<v-sheet class="overflow-hidden bgcolor" style="position: relative">
			<v-container class="fill-height bgcolor">
				<v-row align="center" justify="center">
					<video autoplay loop controls muted src="@/assets/ssiikk.mp4"></video>
					<div class="videos-container"></div>
				</v-row>
			</v-container>
		</v-sheet>
		<div class="footer">
			<MeetingBottomBar
				v-if="streaming"
				@outRoom="outRoom"
				@voiceOn="voiceOn"
				@voiceOff="voiceOff"
				@screenOn="screenOn"
				@screenOff="screenOff"
				@chatOnOff="chatOnOff"
			></MeetingBottomBar>
		</div>
		<!-- <MeetingUser v-if="isUser"></MeetingUser> -->
	</div>
</template>

<script src="https://rtcmulticonnection.herokuapp.com/dist/RTCMultiConnection.min.js"></script>
<script src="https://rtcmulticonnection.herokuapp.com/socket.io/socket.io.js"></script>

<script>
import MeetingBottomBar from '@/components/meeting/MeetingBottomBar.vue';
import { createTestMeeting, deleteTestMeeting } from '@/api/meeting.js';

export default {
	components: {
		MeetingBottomBar,
	},
	data() {
		return {
			isUser: false,
			isChat: false,
			roomid: '',
			connection: null,
			streaming: false,
			chatting: false,
			chatInfo: {
				data: '',
				sender: null,
			},
			participants: Array,
			publicRoomIdentifier: 'sodasodatest',
			room_id: null,
		};
	},
	async mounted() {
		if (window.Notification) {
			Notification.requestPermission();
			createTestMeeting()
				.then(res => {
					// console.log(res.data);
					this.room_id = res.data.id;
					this.openRoom(res.data.name);
				})
				.catch(err => {
					console.log(err.message);
				});
		}
	},
	beforeDestroy() {
		this.outRoom();
	},
	methods: {
		async openRoom(code) {
			if (!!code) {
				this.roomid = code;
				this.meetingStart = !this.meetingStart;
				this.streaming = !this.streaming;
				this.$store.state.meetingOn = this.streaming;
				this.connection = new RTCMultiConnection();
				this.chatInfo.sender = this.connection.userid;
				// this.connection.autoCloseEntireSession = true;
				this.connection.socketMessageEvent = this.roomid;
				this.connection.publicRoomIdentifier = this.publicRoomIdentifier;
				this.connection.session = {
					audio: true,
					video: true,
					data: true,
				};

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
				this.chatOnOff();
				this.notify('입장');
			}
		},
		outRoom() {
			deleteTestMeeting(this.room_id)
				.then(res => {
					// console.log(res);
					if (!!this.connection) {
						this.chatOnOff();
						this.connection.getAllParticipants().forEach(participantId => {
							this.connection.disconnectWith(participantId);
						});

						this.connection.attachStreams.forEach(function (localStream) {
							localStream.stop();
						});

						this.connection.closeSocket();
						this.connection = null;
						this.streaming = !this.streaming;
						this.roomid = '';
						this.$store.state.meetingOn = this.streaming;
						this.$store.state.meetingCode = '';
						this.$router.push('/attend');
						var el = document.getElementById('apdiv');
						if (!!el) {
							el.remove();
						}
						this.notify('퇴장');
					}
				})
				.catch(err => {
					console.log(err.message);
				});
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
		chatOnOff() {
			this.isChat = !this.isChat;
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
			const chatContainer = document.querySelector('.chat-output');
			let div = document.createElement('div');
			div.setAttribute('id', 'apdiv');
			div.innerHTML = `${event.data.sender} : ${event.data.data}`;
			chatContainer.insertBefore(div, chatContainer.firstchild);
			div.tabIndex = 0;
			div.focus();

			document.getElementById('input-text-chat').focus();
		},
		notify(mention) {
			if (Notification.permission !== 'granted') {
				alert('notification is disabled');
			} else {
				const notification = new Notification(`모의면접 ${mention}`, {
					icon: 'http://cdn.sstatic.net/stackexchange/img/logos/so/so-icon.png',
					body: `모의면접을 ${mention}하셨습니다.`,
				});
			}
		},
	},
};
</script>

<style scoped>
.sticky-box {
	width: 200px;
	height: 570px;
	float: left;
	background-color: white;
	padding: 100%, 0%;
}
.chat-output {
	height: 95%;
	overflow-y: auto;
}
.chat-input {
	position: relative;
	height: 5%;
}
.chat-input .comp {
	position: absolute;
	bottom: 0px;
}
.bgcolor {
	background-color: #e0dcdd;
}
.chatBox {
	color: black;
	float: right;
	text-align: start;
	padding: 10px;
	width: 20vw;
	height: 85vh;
	top: 0;
	background-color: white;
}
.footer {
	position: absolute;
	bottom: 0px;
	height: 20vh;
	display: flex;
	justify-content: center;
	width: 100vw;
	height: 15%;
}
.contentBox {
	height: 75%;
}
</style>
