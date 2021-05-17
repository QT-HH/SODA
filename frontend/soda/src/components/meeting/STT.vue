<template>
	<div>
		<div class="d-flex justify-center">
			<div class="subtitleContainer">
				<span class="output subtitleBg" id="font2"></span>
			</div>
		</div>
	</div>
</template>

<script>
import { mapState } from 'vuex';
export default {
	computed: {
		...mapState(['sttOn', 'sendSTT']),
		speech() {
			return new SpeechRecognitionApi({
				output: document.querySelector('.output'),
				connection: this.connection,
				sendSTT: false,
			});
		},
	},
	data() {
		return {
			chatInfo: {
				sender: 'STT',
				data: '',
			},
		};
	},
	watch: {
		sendSTT() {
			if (this.sendSTT) {
				console.log('녹음 시작');
				this.speech.init();
			} else {
				this.speech.stop();
			}
		},
	},
	props: {
		connection: Object,
	},
	methods: {},
};

class SpeechRecognitionApi {
	constructor(options) {
		const connection = options.connection;
		this.sendSTT = options.sendSTT;
		const SpeechToText =
			window.speechRecognition || window.webkitSpeechRecognition;
		this.speechApi = new SpeechToText();
		this.output = options.output
			? options.output
			: document.createElement('div');
		this.speechApi.continuous = true;
		this.speechApi.interimResult = false;
		this.speechApi.onresult = event => {
			var resultIndex = event.resultIndex;
			var transcript = event.results[resultIndex][0].transcript;
			let chatInfo = {
				type: 'STT',
				sender: connection.username,
				data: transcript,
			};
			this.inputChat(connection, chatInfo);
		};
	}
	init() {
		this.speechApi.start();
	}
	stop() {
		this.speechApi.stop();
	}
	inputChat(connection, chatInfo) {
		const myChat = {
			data: chatInfo,
		};
		if (myChat.data.data) {
			connection.send(myChat.data);
			this.output.textContent = `${myChat.data.sender} : ${myChat.data.data}`;
		}
	}
}
</script>

<style>
.subtitleBg {
	color: white;
	background-color: black;
	font-size: 25px;
	display: inline-block;
}
.subtitleContainer {
	width: 70%;
	position: absolute;
	bottom: 100px;
}
</style>
