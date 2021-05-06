<template>
	<div class="bgcolor">
		<div v-if="streaming && isUser && !!connection.isInitiator">
			<div :class="sideBar" ref="pageWrapper">
				<div
					id="font2"
					class="btn btn-sm btn-dark showside"
					href="#"
					@click="showSidebar"
				>
					<p class="ma-0">면접자 리스트</p>
				</div>
				<nav id="sidebar" class="sidebar-wrapper">
					<div class="sidebar-content">
						<div class="sidebar-brand">
							<p>면접자 리스트</p>
							<div id="close-sidebar" @click="closeSidebar">
								<i class="fas fa-times"></i>
							</div>
						</div>
						<v-list class="listBox">
							<v-list-item style="padding: 0px">
								<div class="user">
									<p
										v-for="(item, idx) in participants"
										:key="idx"
										:user="item"
										class="listName"
									>
										{{ item.u_name }}
										<v-chip-group mandatory>
											<v-chip
												color="#dddddd"
												outlined
												small
												@click="changeStatus(item.mm_id, 'PLAN')"
											>
												예정
											</v-chip>
											<v-chip
												color="#dddddd"
												outlined
												small
												@click="changeStatus(item.mm_id, 'PROGRESS')"
											>
												진행
											</v-chip>
											<v-chip
												color="#dddddd"
												outlined
												small
												@click="changeStatus(item.mm_id, 'DONE')"
											>
												완료
											</v-chip>
										</v-chip-group>
									</p>
								</div>
							</v-list-item>
						</v-list>
					</div>
				</nav>
			</div>
		</div>
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
		<input
			v-if="!streaming"
			v-model="roomid"
			placeholder="Unique room ID"
			@keyup.enter="openRoom"
		/>
		<div v-if="!streaming">
			<v-btn depressed color="primary" @click="openRoom">open or join</v-btn>
		</div>
		<v-sheet class="overflow-hidden bgcolor" style="position: relative">
			<v-container class="fill-height bgcolor">
				<v-row align="center" justify="center">
					<div class="videos-container"></div>
				</v-row>
			</v-container>
		</v-sheet>
		<div class="footer">
			<MeetingBottomBar
				v-if="streaming"
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
import { intervieweeOfMeeting } from '@/api/meeting.js';
import { editStatus } from '@/api/member.js';
import MeetingBottomBar from '@/components/meeting/MeetingBottomBar.vue';
export default {
	components: {
		MeetingBottomBar,
	},
	data() {
		return {
			isUser: false,
			isChat: false,
			roomid: '',
			meetingStart: false,
			connection: null,
			streaming: false,
			chatting: false,
			chatInfo: {
				data: '',
				sender: null,
			},
			participants: Array,
			publicRoomIdentifier: 'sodasoda',
			sideBar: 'page-wrapper sideBarTheme',
		};
	},
	async mounted() {
		const meetingCode = this.$store.state.meetingCode;
		await this.openRoom(meetingCode);
		await intervieweeOfMeeting(meetingCode)
			.then(res => {
				this.participants = res.data;
			})
			.catch(err => {
				console.log(err);
			});
	},
	beforeDestroy() {
		this.outRoom();
	},
	methods: {
		chatOnOff() {
			this.isChat = !this.isChat;
		},
		userlist() {
			this.isUser = !this.isUser;
		},
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
				this.userlist();
				this.chatOnOff();
			} else {
				alert('미팅코드 입력해랑');
			}
		},
		outRoom() {
			this.userlist();
			this.chatOnOff();
			if (!!this.connection) {
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
		async changeStatus(mm_id, status) {
			await editStatus(mm_id, status).catch(err => {
				console.log(err);
			});
			await intervieweeOfMeeting(this.$store.state.meetingCode)
				.then(res => {
					this.participants = res.data;
				})
				.catch(err => {
					console.log(err);
				});
		},
		closeSidebar() {
			this.sideBar = 'page-wrapper sideBarTheme';
		},
		showSidebar() {
			this.sideBar = 'page-wrapper sideBarTheme toggled';
		},
	},
};
</script>

<style scoped>
.videos-container video {
	width: 500px;
	margin: 10px;
}
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
.divLocation {
	top: 100px;
}
.page-wrapper .sidebar-wrapper,
/* .sidebar-wrapper .sidebar-brand > a, */
/* .page-wrapper .page-content, */
#show-sidebar,
#close-sidebar {
	-webkit-transition: all 0.3s ease;
	-moz-transition: all 0.3s ease;
	-ms-transition: all 0.3s ease;
	-o-transition: all 0.3s ease;
	transition: all 0.3s ease;
}
.page-wrapper.toggled .sidebar-wrapper {
	left: 0px;
}
.showside {
	position: fixed;
	left: 0;
	top: 10px;
	border-radius: 0 4px 4px 0px;
	width: 50px;
	height: 50px;
	transition-delay: 0.3s;
	background-color: #31353d;
	color: white;
	font-size: 13px;
	z-index: 999;
}
.page-wrapper.toggled .showside {
	left: -50px;
}
.sidebar-wrapper {
	width: 260px;
	height: 100%;
	max-height: 100%;
	position: fixed;
	top: 0;
	left: -300px;
	float: left;
	z-index: 999;
}
.sidebar-wrapper .sidebar-brand {
	padding: 10px 20px;
	display: flex;
	align-items: center;
}
.sidebar-wrapper .sidebar-brand > p {
	text-transform: uppercase;
	font-weight: bold;
	flex-grow: 1;
	color: white;
}
.sidebar-wrapper .sidebar-brand #close-sidebar {
	cursor: pointer;
	font-size: 20px;
}

.sideBarTheme .sidebar-wrapper {
	background: #31353d;
}

.page-wrapper.sideBarTheme.toggled #close-sidebar {
	color: #bdbdbd;
}

.page-wrapper.sideBarTheme.toggled #close-sidebar:hover {
	color: #ffffff;
}
.listBox {
	background-color: transparent;
	text-align: start;
	padding-left: 15px;
}
.listName {
	margin: 2px;
	color: #dddddd;
}
</style>
