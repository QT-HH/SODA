<template>
	<div>
		<div class="chat-output"></div>
		<div class="chat-input">
			<input
				type="text"
				id="font2"
				class="comp input-text-chat"
				placeholder="채팅메세지를 입력하세요."
				v-model="chatInfo.data"
				@keyup.enter="inputChat"
				style="width: 70%; left: 0px"
			/>
			<button
				@click="inputChat"
				class="inputBtn comp"
				id="font3"
				style="right: 0px"
			>
				입력
			</button>
		</div>
	</div>
</template>

<script>
import { mapState } from 'vuex';
export default {
	name: 'Chatting',
	props: {
		connection: Object,
	},
	computed: {
		...mapState(['recvSTT']),
	},
	data() {
		return {
			chatInfo: {
				type: 'chat',
				sender: '',
				data: '',
			},
		};
	},
	mounted() {
		this.chatInfo.sender = this.connection.username;
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
			if (event.data.type === 'chat') {
				const chatContainer = document.querySelector('.chat-output');
				let div = document.createElement('div');
				div.setAttribute('id', 'apdiv');
				div.innerHTML = `${event.data.sender} : ${event.data.data}`;
				chatContainer.insertBefore(div, chatContainer.firstchild);
				div.tabIndex = 0;
				div.focus();

				document.getElementsByClassName('input-text-chat')[0].focus();
			} else if (this.recvSTT) {
				let output = document.querySelector('.output');
				output.textContent = `${event.data.sender} : ${event.data.data}`;
			}
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
	outline: none;
}
.inputBtn:hover {
	background-color: #bbbbbb;
	color: black;
	outline: 0;
}
.inputBtn:focus {
	outline: none;
}
.inputBtn {
	background-color: #cccccc;
	color: black;
	cursor: pointer;
	font-size: 13px;
	padding: 4px 15px;
	transition: all 200ms;
}
</style>
