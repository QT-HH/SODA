<template>
	<div class="footerBox">
		<v-footer padless color="#e0dcdd">
			<button class="btn1" @click="audio">
				<i :class="this.audioIcon"> </i>
				<span id="font2">
					{{ isAudio ? '음소거' : '음소거 해제' }}
				</span>
			</button>
			<button class="btn1" @click="video">
				<i :class="this.videoIcon"> </i>
				<span id="font2">
					{{ isVideo ? '비디오 끄기' : '비디오 켜기' }}
				</span>
			</button>
			<button class="btn1" @click="subtitle">
				<i :class="this.subtitleIcon"> </i>
				<span id="font2">
					{{ isSubtitle ? '자막 끄기' : '자막 켜기' }}
				</span>
			</button>
			<button class="btn1" @click="chatting">
				<i :class="this.chattingIcon"> </i>
				<span id="font2">
					{{ isChatting ? '채팅창 끄기' : '채팅창 열기' }}
				</span>
			</button>
			<button class="btn2" @click="outRoom">
				<i class="fas fa-sign-out-alt"></i>
				<span id="font2">면접 종료</span>
			</button>
		</v-footer>
	</div>
</template>

<script>
// class SpeechRecognitionApi {
// 	constructor(options) {
// 		const SpeechToText =
// 			window.speechRecognition || window.webkitSpeechRecognition;
// 		this.speechApi = new SpeechToText();
// 		this.output = options.output
// 			? options.output
// 			: document.createElement('div');
// 		this.speechApi.continuous = true;
// 		this.speechApi.interimResult = false;
// 		this.speechApi.onresult = event => {
// 			var resultIndex = event.resultIndex;
// 			var transcript = event.results[resultIndex][0].transcript;
// 			console.log(transcript);
// 			this.output.textContent = transcript;
// 		};
// 	}
// 	init() {
// 		this.speechApi.start();
// 	}
// 	stop() {
// 		this.speechApi.stop();
// 	}
// }

// window.onload = function () {
// 	var f = false;
// 	var speech = new SpeechRecognitionApi({
// 		output: document.querySelector('.output'),
// 	});
// 	document.querySelector('.btn-start').addEventListener('click', () => {
// 		f = !f;
// 		if (f) speech.init();
// 		else speech.stop();
// 	});
// };
export default {
	name: 'MeetingBottomBar',
	data() {
		return {
			isAudio: false,
			isVideo: true,
			isSubtitle: true,
			isChatting: true,
			audioIcon: 'fas fa-volume-mute redColor',
			videoIcon: 'fas fa-video greenColor',
			subtitleIcon: 'fas fa-closed-captioning greenColor',
			chattingIcon: 'fas fa-comment greenColor',
		};
	},
	methods: {
		audio() {
			this.isAudio = !this.isAudio;
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
				this.$store.state.sttOn = true;
				console.log('켜기' + this.$store.state.sttOn);
			} else {
				this.subtitleIcon = 'far fa-closed-captioning redColor';
				this.$store.state.sttOn = false;
				console.log('끄기' + this.$store.state.sttOn);
			}
		},
		chatting() {
			this.isChatting = !this.isChatting;
			this.$emit('chatOnOff');
			if (this.isChatting) {
				this.chattingIcon = 'fas fa-comment greenColor';
			} else {
				this.chattingIcon = 'fas fa-comment-slash redColor';
			}
		},
		outRoom() {
			this.$emit('outRoom');
		},
	},
	computed: {
		divClasses: function () {
			return {
				'fas fa-volume-up': this.isAudio,
				'fas fa-volume-mute': !this.isAudio,
			};
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
	/* border: 2px solid black; */
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
