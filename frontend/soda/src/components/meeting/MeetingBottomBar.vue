<template>
	<div>
		<v-footer padless absolute fixed color="#E3F2FD">
			<v-row justify="center" no-gutters>
				<v-col class="py-6 text-center white--text" cols="12">
					<v-btn
						v-if="isUser"
						color="rgb(204, 193, 243)"
						style="margin-right: 15px"
						@click="user"
					>
						<i class="fas fa-users"> 면접자리스트 OFF</i></v-btn
					>
					<v-btn
						v-else-if="!isUser"
						color="rgb(204, 193, 243)"
						style="margin-right: 15px"
						@click="user"
					>
						<i class="fas fa-users"> 면접자리스트 ON</i></v-btn
					>
					<v-btn
						v-if="isAudio"
						color="rgb(204, 193, 243)"
						style="margin-left: 15px; margin-right: 15px"
						@click="audio"
					>
						<i class="fas fa-volume-mute"> 오디오 OFF</i></v-btn
					>
					<v-btn
						v-else-if="!isAudio"
						color="rgb(204, 193, 243)"
						style="margin-left: 15px; margin-right: 15px"
						@click="audio"
					>
						<i class="fas fa-volume-up"> {{ isAudio }} </i></v-btn
					>
					<v-btn
						v-if="isVideo"
						color="rgb(204, 193, 243)"
						style="margin-left: 15px; margin-right: 15px"
						@click="video"
						><i class="fas fa-video-slash"> 비디오 OFF</i></v-btn
					>
					<v-btn
						v-else-if="!isVideo"
						color="rgb(204, 193, 243)"
						style="margin-left: 15px; margin-right: 15px"
						@click="video"
						><i class="fas fa-video"> 비디오 ON</i></v-btn
					>
					<v-btn
						v-if="isSubtitle"
						color="rgb(204, 193, 243)"
						style="margin-left: 15px; margin-right: 15px"
						@click="subtitle"
						><i class="fas fa-remove-format"> 자막 OFF</i></v-btn
					>
					<v-btn
						v-else-if="!isSubtitle"
						color="rgb(204, 193, 243)"
						style="margin-left: 15px; margin-right: 15px"
						@click="subtitle"
						><i class="fas fa-align-left"> 자막 ON</i></v-btn
					>
					<v-btn
						v-if="isChatting"
						color="rgb(204, 193, 243)"
						style="margin-left: 15px; margin-right: 15px"
						@click="chatting"
						><i class="fas fa-comment-slash"> 채팅 OFF</i></v-btn
					>
					<v-btn
						v-else-if="!isChatting"
						color="rgb(204, 193, 243)"
						style="margin-left: 15px; margin-right: 15px"
						@click="chatting"
						><i class="fas fa-comment"> 채팅 ON</i></v-btn
					>
					<v-btn color="rgb(204, 193, 243)" style="margin-left: 15px"
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
			isUser: true,
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
		},
		video() {
			this.isVideo = !this.isVideo;
		},
		subtitle() {
			this.isSubtitle = !this.isSubtitle;
		},
		chatting() {
			this.isChatting = !this.isChatting;
		},
	},
};
</script>

<style>
.asd {
	background-color: rgb(204, 193, 243);
}
</style>
