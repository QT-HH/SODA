<template>
	<div>
		<!-- <v-btn class="btn-start" @click="startBtn">start recording</v-btn> -->
		<!-- <v-btn class="btn-end">end recording</v-btn> -->
		<p class="output"></p>
	</div>
</template>

<script>
// window.onload = function () {
// 	var f = false;
// 	var speech = new SpeechRecognitionApi({
// 		output: document.querySelector('.output'),
// 	});
// 	document.querySelector('.btn-start').addEventListener('click', () => {
// 		f = !f;
// 		console.log(f);
// 		if (f) {
// 			speech.init();
// 			console.log(f + '켜짐');
// 		} else {
// 			speech.stop();
// 			console.log(f + '꺼짐');
// 		}
// 	});
// };
import { mapState } from 'vuex';

export default {
	computed: {
		...mapState(['sttOn']),
		speech() {
			return new SpeechRecognitionApi({
				output: document.querySelector('.output'),
				connection: this.connection,
			});
		},
		sttcheck() {
			return this.sttOn;
		},
	},
	watch: {
		sttcheck(val) {
			this.stt = val;
			console.log('와치시작');
			if (this.stt == true) {
				this.speech.init();
				console.log(this.stt + '켜짐');
			} else {
				this.speech.stop();
				console.log(this.stt + '꺼짐');
			}
		},
	},
	data() {
		return {
			stt: this.sttOn,
			chatInfo: {
				sender: 'STT',
				data: '',
			},
		};
	},
	props: {
		connection: Object,
	},
	methods: {
		// startBtn() {
		// 	this.stt = !this.stt;
		// 	console.log(this.stt);
		// 	if (this.stt) {
		// 		this.speech.init();
		// 		console.log(this.stt + '켜짐');
		// 	} else {
		// 		this.speech.stop();
		// 		console.log(this.stt + '꺼짐');
		// 	}
		// },
	},
};

class SpeechRecognitionApi {
	constructor(options) {
		const connection = options.connection;
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
			// console.log(transcript);
			let chatInfo = {
				type: 'STT',
				sender: connection.userid,
				data: transcript,
			};
			this.inputChat(connection, chatInfo);
			// this.output.textContent = transcript;
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

<style></style>
