<template>
	<div>
		<br />
		<br />
		<br />
		<br />
		<br />
		<br />
		<v-btn class="btn-start">start recording</v-btn>
		<v-btn class="btn-end">end recording</v-btn>
		<v-text-field class="output" v-model="stt"></v-text-field>
	</div>
</template>

<script>
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

window.onload = function () {
	var speech = new SpeechRecognitionApi({
		output: document.querySelector('.output'),
	});
	document.querySelector('.btn-start').addEventListener('click', () => {
		speech.init();
	});
	document.querySelector('.btn-end').addEventListener('click', () => {
		speech.stop();
	});
};
</script>

<style>
.output {
	display: block;
	width: 80%;
	height: 400px;
	margin: 50px auto;
}
</style>
