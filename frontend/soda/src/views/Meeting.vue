<template>
	<div class="bgcolor">
		<IntervieweeList v-if="this.isSuperUser && isUser"></IntervieweeList>

		<Chatting
			v-show="isChat"
			id="chat-container"
			class="chatBox"
			:connection="connection"
		></Chatting>

		<v-sheet class="overflow-hidden bgcolor" style="position: relative">
			<v-container class="fill-height bgcolor">
				<v-row align="center" justify="center">
					<div
						class="videos-container vContainer d-flex flex-wrap align-center justify-center"
					></div>
				</v-row>
			</v-container>
		</v-sheet>

		<STT :connection="connection" v-show="showSTT"></STT>

		<div class="footer">
			<MeetingBottomBar
				@userlist="userlist"
				@outRoom="outRoom"
				@voiceOn="voiceOn"
				@voiceOff="voiceOff"
				@screenOn="screenOn"
				@screenOff="screenOff"
			></MeetingBottomBar>
		</div>
	</div>
</template>

<script src="https://rtcmulticonnection.herokuapp.com/dist/RTCMultiConnection.min.js"></script>
<script src="https://rtcmulticonnection.herokuapp.com/socket.io/socket.io.js"></script>

<script>
import { mapState, mapActions } from 'vuex';
import MeetingBottomBar from '@/components/meeting/MeetingBottomBar.vue';
import Chatting from '@/components/meeting/Chatting.vue';
import IntervieweeList from '@/components/meeting/IntervieweeList.vue';
import STT from '@/components/meeting/STT.vue';
export default {
	name: 'Meeting',
	components: {
		MeetingBottomBar,
		Chatting,
		IntervieweeList,
		STT,
	},
	computed: {
		...mapState([
			'meetingOn',
			'meetingCode',
			'meetingName',
			'isSuperUser',
			'showSTT',
			'isChat',
		]),
	},
	created() {
		if (this.meetingCode === String) {
			this.meetingOnOff();
			this.$router.push({ name: 'Attend' });
			return;
		}
		this.setRoom(this.meetingCode);
	},
	mounted() {
		this.openRoom(this.meetingCode);
	},
	beforeDestroy() {
		if (!this.connection) {
			this.outRoom();
		}
	},
	data() {
		return {
			isUser: true,
			// isChat: false,
			meetingStart: false,
			connection: null,
			chatInfo: {
				data: '',
				sender: null,
			},
			publicRoomIdentifier: 'sodasoda',
			mention: String,
		};
	},
	methods: {
		...mapActions([
			'meetingOnOff',
			'setMeetingCode',
			'setIsSuperUser',
			'STTshow',
		]),
		setRoom(code) {
			if (!!code) {
				this.meetingOnOff();
				this.meetingStart = !this.meetingStart;
				this.connection = new RTCMultiConnection();
				this.connection.username = this.meetingName;
				this.connection.userid =
					this.connection.userid + ',' + this.connection.username;
				this.chatInfo.sender = this.meetingName;
				this.connection.socketMessageEvent = code;
				this.connection.publicRoomIdentifier = this.publicRoomIdentifier;
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
				this.connection.onstream = this.onStream;
				this.connection.onstreamended = this.onStreamEnded;
				this.connection.iceTransportPolicy = 'all';
			}
		},
		openRoom(code) {
			if (!!code) {
				this.connection.videosContainer = document.querySelector(
					'.videos-container',
				);
				this.connection.openOrJoin(code);
			}
		},
		outRoom() {
			this.setIsSuperUser(false);
			if (!!this.connection) {
				this.connection.getAllParticipants().forEach(participantId => {
					this.connection.disconnectWith(participantId);
				});

				this.connection.attachStreams.forEach(function (localStream) {
					localStream.stop();
				});

				this.connection.closeSocket();
				this.connection = null;
				this.meetingOnOff();
				this.setMeetingCode(String);
				this.STTshow(false);
				this.$router.push({ name: 'Attend' });
				let el = document.getElementById('apdiv');
				if (!!el) {
					el.remove();
				}
			}
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
		userlist() {
			this.isUser = !this.isUser;
		},
		findMyVideo() {
			let events = this.connection.streamEvents.selectAll();
			let event = events.find(event => {
				return event.type === 'local';
			});
			return event;
		},
		onStream(event) {
			// console.log(event);
			let video = event.mediaElement;
			video.id = event.streamid;
			video.controls = false;
			const user = event.userid.split(',')[1];
			// console.log(user);
			let userTag = document.createElement('div');
			userTag.textContent = `${user}`;
			// console.log(userTag);

			let videoBox = document.createElement('div');
			videoBox.setAttribute('id', event.streamid);

			videoBox.insertBefore(userTag, videoBox.firstChild);
			videoBox.insertBefore(video, videoBox.firstChild);
			this.connection.videosContainer.insertBefore(
				videoBox,
				this.connection.videosContainer.firstChild,
			);
			// this.notify('입장', event.username);
		},
		onStreamEnded(event) {
			let video = document.getElementById(event.streamid);
			if (video && video.parentNode) {
				video.parentNode.removeChild(video);
			}
			// this.notify('퇴장', event.username);
		},
		notify(mention, user_id) {
			let user = user_id;
			if (Notification.permission !== 'granted') {
				alert(`${user}님께서 면접을 ${mention}하셨습니다.`);
			} else {
				const notification = new Notification(`면접 ${mention}`, {
					icon: 'https://i.ibb.co/wypPBQx/sodalogo.png',
					body: `${user}님께서 면접을 ${mention}하셨습니다.`,
				});
			}
		},
		test() {
			window.BeforeUnloadEvent = function () {
				alert('asdf');
			};
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
.divLocation {
	top: 100px;
}
.vContainer {
	height: 85vh;
	width: 90vw;
}
</style>
