<template>
	<div>
		<v-footer padless absolute fixed color="#E3F2FD">
			<v-row justify="center" no-gutters>
				<v-col class="py-6 text-center white--text" cols="12">
					<v-btn
						color="rgb(204, 193, 243)"
						style="margin-right: 15px"
						@click="user"
					>
						<i class="fas fa-users">
							{{ isUser ? '면접자리스트 OFF' : '면접자리스트 ON' }}
						</i></v-btn
					>
					<v-btn
						color="rgb(204, 193, 243)"
						style="margin-left: 15px; margin-right: 15px"
						@click="audio"
					>
						<i class="fas fa-volume-up">
							{{ isAudio ? '오디오 OFF' : '오디오 ON' }}
						</i></v-btn
					>
					<v-btn
						color="rgb(204, 193, 243)"
						style="margin-left: 15px; margin-right: 15px"
						@click="video"
						><i class="fas fa-video">
							{{ isVideo ? '비디오 OFF' : '비디오 ON' }}
						</i></v-btn
					>
					<v-btn
						color="rgb(204, 193, 243)"
						style="margin-left: 15px; margin-right: 15px"
						@click="subtitle"
						><i class="fas fa-remove-format">
							{{ isSubtitle ? '자막 OFF' : '자막 ON' }}
						</i></v-btn
					>
					<v-btn
						color="rgb(204, 193, 243)"
						style="margin-left: 15px; margin-right: 15px"
						@click="chatting"
						><i class="fas fa-comment-slash">
							{{ isChatting ? '채팅 OFF' : '채팅 ON' }}
						</i></v-btn
					>
					<v-btn
						color="rgb(204, 193, 243)"
						style="margin-left: 15px"
						@click="outRoom"
						><i class="fas fa-sign-out-alt"> 면접 종료</i></v-btn
					>
				</v-col>
			</v-row>
		</v-footer>
	</div>
</template>

<script>
export default {
	name: 'MeetingBottomBar',
	data() {
		return {
			isUser: false,
			isAudio: true,
			isVideo: true,
			isSubtitle: true,
			isChatting: true,
		};
	},
	methods: {
		user() {
			this.isUser = !this.isUser;
			this.$emit('userlist', this.isUser);
		},
		audio() {
			this.isAudio = !this.isAudio;
			if (this.isAudio) {
				this.$emit('voiceOn');
			} else {
				this.$emit('voiceOff');
			}
		},
		video() {
			this.isVideo = !this.isVideo;
			if (this.isVideo) {
				this.$emit('screenOn');
			} else {
				this.$emit('screenOff');
			}
		},
		subtitle() {
			this.isSubtitle = !this.isSubtitle;
		},
		chatting() {
			this.isChatting = !this.isChatting;
			this.$emit('chatOnOff');
		},
		outRoom() {
			this.$emit('outRoom');
		},
	},
};
</script>

<style scoped>
.asd {
	background-color: rgb(204, 193, 243);
}
</style>
