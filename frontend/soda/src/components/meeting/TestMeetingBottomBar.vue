<template>
	<div class="footerBox">
		<v-footer padless color="#e0dcdd">
			<v-tooltip top>
				<template v-slot:activator="{ on, attrs }">
					<button class="btn1" @click="audio" v-bind="attrs" v-on="on">
						<i :class="audioIcon"> </i>
						<span id="font2">
							{{ isAudio ? '음소거' : '음소거 해제' }}
						</span>
					</button>
				</template>
				<span>{{
					isAudio ? '나의 오디오를 음소거' : '나의 오디오 음소거 해제'
				}}</span>
			</v-tooltip>
			<v-tooltip top>
				<template v-slot:activator="{ on, attrs }">
					<button class="btn1" @click="video" v-bind="attrs" v-on="on">
						<i :class="videoIcon"> </i>
						<span id="font2">
							{{ isVideo ? '비디오 끄기' : '비디오 켜기' }}
						</span>
					</button>
				</template>
				<span>{{
					isVideo ? '나의 화상 비디오를 송출 중지' : '나의 화상 비디오를 송출'
				}}</span>
			</v-tooltip>
			<v-tooltip top>
				<template v-slot:activator="{ on, attrs }">
					<button class="btn1" @click="subtitle" v-bind="attrs" v-on="on">
						<i :class="subtitleIcon"> </i>
						<span id="font2">
							{{ isSubtitle ? '자막 끄기' : '자막 켜기' }}
						</span>
					</button>
				</template>
				<span>{{
					isSubtitle
						? '면접관에게 나의 말을 자막으로 제공'
						: '면접관에게 자막 송출을 중지'
				}}</span>
			</v-tooltip>
			<v-tooltip top>
				<template v-slot:activator="{ on, attrs }">
					<button class="btn1" @click="chatting" v-bind="attrs" v-on="on">
						<i :class="chattingIcon"> </i>
						<span id="font2">
							{{ isChat ? '채팅창 끄기' : '채팅창 열기' }}
						</span>
					</button>
				</template>
				<span>{{ isChat ? '채팅창 닫기' : '화면 우측에 채팅창 활성화' }}</span>
			</v-tooltip>
			<v-tooltip top>
				<template v-slot:activator="{ on, attrs }">
					<button class="btn2" @click="outRoom" v-bind="attrs" v-on="on">
						<i class="fas fa-sign-out-alt"></i>
						<span id="font2"> 모의 면접 종료</span>
					</button>
				</template>
				<span>면접방 퇴장</span>
			</v-tooltip>
		</v-footer>
	</div>
</template>

<script>
import { mapState, mapActions } from 'vuex';
export default {
	name: 'TestMeetingBottomBar',
	data() {
		return {
			isAudio: false,
			isVideo: true,
			isSubtitle: true,
			// isChatting: false,
			audioIcon: 'fas fa-volume-mute redColor',
			videoIcon: 'fas fa-video greenColor',
			subtitleIcon: 'fas fa-closed-captioning greenColor',
			chattingIcon: 'fas fa-comment greenColor',
		};
	},
	computed: {
		...mapState(['sttOn', 'isChat']),
		divClasses: function () {
			return {
				'fas fa-volume-up': this.isAudio,
				'fas fa-volume-mute': !this.isAudio,
			};
		},
	},
	methods: {
		...mapActions(['STTshow', 'STTsend', 'setIsChat']),
		audio() {
			this.isAudio = !this.isAudio;
			this.STTsend();
			if (this.isAudio) {
				this.audioIcon = 'fas fa-volume-up greenColor';
				this.$emit('voiceOn');
			} else {
				this.audioIcon = 'fas fa-volume-mute redColor';
				this.$emit('voiceOff');
			}
		},
		video() {
			this.isVideo = !this.isVideo;
			if (this.isVideo) {
				this.videoIcon = 'fas fa-video greenColor';
				this.$emit('screenOn');
			} else {
				this.videoIcon = 'fas fa-video-slash redColor';
				this.$emit('screenOff');
			}
		},
		subtitle() {
			this.isSubtitle = !this.isSubtitle;
			if (this.isSubtitle) {
				this.subtitleIcon = 'fas fa-closed-captioning greenColor';
				this.STTshow(true);
			} else {
				this.subtitleIcon = 'far fa-closed-captioning redColor';
				this.STTshow(false);
			}
		},
		chatting() {
			this.setIsChat();
			if (this.isChat) {
				this.chattingIcon = 'fas fa-comment greenColor';
			} else {
				this.chattingIcon = 'fas fa-comment-slash redColor';
			}
		},
		outRoom() {
			this.$emit('outRoom');
		},
	},
};
</script>

<style scoped>
.btn1:hover {
	background-color: #bbbbbb;
	color: black;
	outline: 0;
}
.btn1:focus {
	outline: none;
}
.btn1 {
	background-color: white;
	border-radius: 30px;
	color: black;
	cursor: pointer;
	font-size: 17px;
	padding: 6px 20px;
	transition: all 200ms;
	margin-right: 5px;
}
.btn2:hover {
	font-size: 20px;
	margin-left: 0px;
}
.btn2:focus {
	outline: none;
}
.btn2 {
	background-color: #b71c1c;
	border-radius: 30px;
	color: white;
	cursor: pointer;
	font-size: 17px;
	padding: 6px 20px;
	transition: all 200ms;
	margin-left: 0px;
}
.redColor {
	color: #b71c1c;
}
.greenColor {
	color: green;
}
.footerBox {
	justify-items: center;
	align-self: center;
}
</style>
