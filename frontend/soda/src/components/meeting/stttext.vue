<template>
	<div>
		<br />
		<br />
		<br />
		<br />
		<v-btn class="btn-start">start recording</v-btn>
		<v-btn class="btn-end">end recording</v-btn>
		<p class="output"></p>
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

window.onload = function () {
	var f = false;
	var speech = new SpeechRecognitionApi({
		output: document.querySelector('.output'),
	});
	document.querySelector('.btn-start').addEventListener('click', () => {
		f = !f;
		if (f) speech.init();
		else speech.stop();
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
