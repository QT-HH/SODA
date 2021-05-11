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
					<div class="videos-container">
						<video
							autoplay
							loop
							controls
							muted
							src="https://ifh.cc/v/CA0iFG.mp4"
							controlsList="nodownload"
							class="videoExample"
						>
							<track
								default
								kind="subtitles"
								srclang="en"
								src="@/assets/vttTest.vtt"
							/>
							Sorry, your browser doesn't support embedded videos.
						</video>
					</div>
				</v-row>
			</v-container>
		</v-sheet>

		<STT :connection="connection"></STT>

		<div class="footer">
			<MeetingBottomBar
				@userlist="userlist"
				@outRoom="outRoom"
				@voiceOn="voiceOn"
				@voiceOff="voiceOff"
				@screenOn="screenOn"
				@screenOff="screenOff"
				@chatOnOff="chatOnOff"
			></MeetingBottomBar>
		</div>
	</div>
</template>

<script src="https://rtcmulticonnection.herokuapp.com/dist/RTCMultiConnection.min.js"></script>
<script src="https://rtcmulticonnection.herokuapp.com/socket.io/socket.io.js"></script>

<script>
import MeetingBottomBar from '@/components/meeting/MeetingBottomBar.vue';
import Chatting from '@/components/meeting/Chatting.vue';
import IntervieweeList from '@/components/meeting/IntervieweeList.vue';
import STT from '@/components/meeting/STT.vue';
import { deleteTestMeeting } from '@/api/meeting.js';
export default {
	components: {
		MeetingBottomBar,
		Chatting,
		IntervieweeList,
		STT,
	},
	data() {
		return {
			isUser: false,
			isChat: false,
			roomid: '',
			roomDBid: '',
			meetingStart: false,
			connection: null,
			chatInfo: {
				data: '',
				sender: null,
			},
			publicRoomIdentifier: 'sodasodaTest',
			mention: String,
		};
	},
	// computed: {
	// 	vttsrc() {
	// 		return require('@/assets/vttTest.vtt');
	// 	},
	// },
	created() {
		this.roomid = this.$store.state.meetingCode;
		this.roomDBid = this.$store.state.testMeetingId;
		this.setRoom(this.roomid);
	},
	mounted() {
		this.openRoom(this.roomid);
		this.setVideo();
	},
	beforeDestroy() {
		this.outRoom();
	},
	methods: {
		setRoom(code) {
			if (!!code) {
				this.meetingStart = !this.meetingStart;
				this.$store.state.meetingOn = true;
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

				// this.connection.onmessage = this.appendDIV;
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
				this.connection.openOrJoin(this.roomid);
				this.userlist();
				this.chatOnOff();
			}
		},
		outRoom() {
			deleteTestMeeting(this.roomDBid)
				.then(() => {
					this.userlist();
					this.chatOnOff();
					if (!!this.connection) {
						// this.connection.onstreamended = null;
						this.connection.getAllParticipants().forEach(participantId => {
							this.connection.disconnectWith(participantId);
						});

						this.connection.attachStreams.forEach(function (localStream) {
							localStream.stop();
						});

						this.connection.closeSocket();
						this.connection = null;
						this.roomid = '';
						this.$store.state.meetingOn = false;
						this.$store.state.meetingCode = '';
						this.$router.push('/attend');
						let el = document.getElementById('apdiv');
						if (!!el) {
							el.remove();
						}
					}
				})
				.catch(err => {
					console.log(err);
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
			this.connection.videosContainer.insertBefore(
				video,
				this.connection.videosContainer.firstChild,
			);
			this.notify('입장', event.userid);
		},
		onStreamEnded(event) {
			let video = document.getElementById(event.streamid);
			if (video && video.parentNode) {
				video.parentNode.removeChild(video);
			}
			this.notify('퇴장', event.userid);
		},
		notify(mention, user_id) {
			let user = user_id;
			if (Notification.permission !== 'granted') {
				alert(`모의면접을 ${mention}하셨습니다.`);
			} else {
				const notification = new Notification(`면접 ${mention}`, {
					icon: 'https://i.ibb.co/wypPBQx/sodalogo.png',
					body: `모의면접을 ${mention}하셨습니다.`,
				});
			}
		},
		setVideo() {
			const videoContainer = document.querySelector('.videoExample');
			console.log(videoContainer);
			// let div = document.createElement('div');
			// div.setAttribute('id', 'apdiv');
			// div.innerHTML = `${event.data.sender} : ${event.data.data}`;
			// chatContainer.insertBefore(div, chatContainer.firstchild);
			// div.tabIndex = 0;
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
</style>
