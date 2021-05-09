<template>
	<div>
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
</template>

<script>
export default {
	name: 'Chatting',
	props: {
		connection: Object,
	},
	data() {
		return {
			chatInfo: {
				sender: '',
				data: '',
			},
		};
	},
	mounted() {
		this.chatInfo.sender = this.connection.userid;
		this.connection.onmessage = this.appendDIV;
	},
	methods: {
		inputChat() {
			const myChat = {
				data: this.chatInfo,
			};
			if (myChat.data.data) {
				this.connection.send(myChat.data);
				this.appendDIV(myChat);
			}
			this.chatInfo.data = '';
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
	},
};
</script>

<style>
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
</style>
