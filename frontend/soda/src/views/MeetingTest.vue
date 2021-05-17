<template>
	<div class="bgcolor">
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
					>
						<video
							autoplay
							loop
							controls
							muted
							src="https://ifh.cc/v/CA0iFG.mp4"
							controlsList="nodownload"
							class="videoExample"
						></video>
					</div>
				</v-row>
			</v-container>
		</v-sheet>

		<STT :connection="connection" v-show="showSTT"></STT>

		<div class="footer">
			<TestMeetingBottomBar
				@userlist="userlist"
				@outRoom="goAnywhere"
				@voiceOn="voiceOn"
				@voiceOff="voiceOff"
				@screenOn="screenOn"
				@screenOff="screenOff"
				@chatOnOff="chatOnOff"
			></TestMeetingBottomBar>
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
import { deleteTestMeeting } from '@/api/meeting.js';
import TestMeetingBottomBar from '@/components/meeting/TestMeetingBottomBar.vue';
export default {
	components: {
		MeetingBottomBar,
		Chatting,
		IntervieweeList,
		STT,
		TestMeetingBottomBar,
	},
	data() {
		return {
			isUser: false,
			// isChat: false,
			meetingStart: false,
			connection: null,
			chatInfo: {
				data: '',
				sender: '',
			},
			publicRoomIdentifier: 'sodasodaTest',
			mention: String,
		};
	},
	computed: {
		...mapState([
			'meetingOn',
			'meetingCode',
			'meetingName',
			'showSTT',
			'isChat',
		]),
	},
	created() {
		const meetCode = localStorage.getItem('meetingCode');
		if (!!meetCode) {
			this.setMeetingCode(meetCode);
		}
		if (this.meetingCode === String || this.meetingCode === null) {
			this.$router.push({ name: 'Attend' });
		} else {
			this.setRoom(this.meetingCode);
		}
		// this.$router.push({ name: 'Attend' });
	},
	mounted() {
		// window.addEventListener('beforeunload', this.unLoadEvent);
		// window.onbeforeunload = this.unLoadEvent;
		this.openRoom(this.meetingCode);
		// if (this.meetingCode !== String) {
		// }
	},
	beforeDestroy() {
		// alert('qwer');
		// window.removeEventListener('beforeunload', this.unLoadEvent);
		this.outRoom();
		// if (this.meetingCode !== String) {
		// }
	},
	methods: {
		...mapActions([
			'meetingOnOff',
			'setMeetingCode',
			'STTshow',
			'setMeetingCode',
		]),
		setRoom(code) {
			if (!!code) {
				this.meetingOnOff();
				this.meetingStart = !this.meetingStart;
				this.connection = new RTCMultiConnection();
				this.connection.username = this.meetingName;
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
			deleteTestMeeting(localStorage.getItem('meetingCodeId'))
				.then(() => {
					localStorage.setItem('meetingCodeId', null);
					localStorage.setItem('meetingCode', null);
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
						let el = document.getElementById('apdiv');
						if (!!el) {
							el.remove();
						}
					}
				})
				.catch(err => {
					console.log(err.message);
				});
		},
		goAnywhere() {
			this.$router.push({ name: 'Attend' });
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
		chatOnOff() {
			this.isChat = !this.isChat;
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
			let video = event.mediaElement;
			video.id = event.streamid;
			video.controls = false;
			const user = '탁소다 (면접자)';
			// console.log(user);
			let userTag = document.createElement('div');
			let nameSpace = document.createElement('span');
			userTag.insertBefore(nameSpace, userTag.firstChild);
			nameSpace.textContent = `${user}`;
			// console.log(userTag);
			userTag.setAttribute('id', 'font3');

			nameSpace.className = 'effectName';
			video.className = 'effectVideo';

			let videoBox = document.createElement('div');
			videoBox.setAttribute('id', event.streamid);
			videoBox.className = 'videoBox';

			videoBox.insertBefore(userTag, videoBox.firstChild);
			videoBox.insertBefore(video, videoBox.firstChild);
			this.connection.videosContainer.insertBefore(
				videoBox,
				this.connection.videosContainer.firstChild,
			);
			// this.notify('입장');
		},
		onStreamEnded(event) {
			let video = document.getElementById(event.streamid);
			if (video && video.parentNode) {
				video.parentNode.removeChild(video);
			}
			// this.notify('퇴장');
		},
		notify(mention) {
			if (Notification.permission !== 'granted') {
				alert(`모의면접을 ${mention}하셨습니다.`);
			} else {
				const notification = new Notification(`면접 ${mention}`, {
					icon: 'https://i.ibb.co/wypPBQx/sodalogo.png',
					body: `모의면접을 ${mention}하셨습니다.`,
				});
			}
		},
		// unLoadEvent(event) {
		// 	console.log(event);
		// 	event.preventDefault();
		// 	event.returnValue = '';
		// 	// this.outRoom();
		// },
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
	background-color: #e0dcdd;
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
