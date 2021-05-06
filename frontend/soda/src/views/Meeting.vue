<template>
	<div class="bgcolor">
		<div v-if="streaming && isUser && !!connection.isInitiator">
			<v-container
				fluid
				class="sticky-box"
				style="border: 2px solid; color: #4527a0"
			>
				<p style="color: black">면접자리스트</p>
				<v-list>
					<v-list-item style="padding: 0px; text-align: center">
						<div class="text-center">
							<div class="text-center user">
								<p
									v-for="(item, idx) in participants"
									:key="idx"
									:user="item"
									style="margin: 2px"
								>
									{{ item.u_name }}
									<v-chip-group mandatory>
										<v-chip
											color="indigo darken-3"
											outlined
											small
											@click="changeStatus(item.mm_id, 'PLAN')"
										>
											예정
										</v-chip>
										<v-chip
											color="indigo darken-3"
											outlined
											small
											@click="changeStatus(item.mm_id, 'PROGRESS')"
										>
											진행
										</v-chip>
										<v-chip
											color="indigo darken-3"
											outlined
											small
											@click="changeStatus(item.mm_id, 'DONE')"
										>
											완료
										</v-chip>
									</v-chip-group>
								</p>
							</div>
						</div>
					</v-list-item>
				</v-list>
			</v-container>
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
		<!-- <MeetingUser v-if="isUser"></MeetingUser> -->
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
		};
	},
	async mounted() {
		const meetingCode = this.$store.state.meetingCode;
		await this.openRoom(meetingCode);
		await intervieweeOfMeeting(meetingCode)
			.then(res => {
				console.log(res.data);
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
			// await getConfirmMeetingCode(code)
			// 	.then(res => {
			// 		console.log(res.data);
			// 		if (res.data) {
			// 		} else {
			// 			alert('유효하지 않은 미팅코드입니다.');
			// 		}
			// 	})
			// 	.catch(err => {
			// 		console.log('에러');
			// 	});
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
				el.remove();
				console.log('outroom');
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
			console.log(1);
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
			await editStatus(mm_id, status)
				.then(res => {
					console.log(res.data);
				})
				.catch(err => {
					console.log(err);
				});
			await intervieweeOfMeeting(this.$store.state.meetingCode)
				.then(res => {
					console.log(res.data);
					this.participants = res.data;
				})
				.catch(err => {
					console.log(err);
				});
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
</style>
