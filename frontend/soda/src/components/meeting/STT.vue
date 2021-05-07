<template>
	<div>
		<br />
		<br />
		<br />
		<br />
		<v-btn class="btn-start" @click="startBtn">start recording</v-btn>
		<!-- <v-btn class="btn-end">end recording</v-btn> -->
		<v-text-field class="output"></v-text-field>
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

export default {
	data() {
		return {
			f: false,
		};
	},
	methods: {
		startBtn() {
			this.f = !this.f;
			console.log(this.f);
			if (this.f) {
				this.speech.init();
				console.log(this.f + '켜짐');
			} else {
				this.speech.stop();
				console.log(this.f + '꺼짐');
			}
		},
	},
	computed: {
		speech() {
			return new SpeechRecognitionApi({
				output: document.querySelector('.output'),
			});
		},
	},
};

class SpeechRecognitionApi {
	constructor(options) {
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
			console.log(transcript);
			this.output.textContent = transcript;
		};
	}
	init() {
		this.speechApi.start();
	}
	stop() {
		this.speechApi.stop();
	}
}
</script>

<style>
.output {
	display: block;
	width: 80%;
	height: 400px;
	margin: 50px auto;
}
</style>
