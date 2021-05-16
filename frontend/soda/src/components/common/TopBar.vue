<template>
	<div>
		<v-app-bar flat fixed class="fixtop" height="56px" :color="bg">
			<v-toolbar-title style="margin-left: 15vw">
				<img
					src="https://i.ibb.co/wypPBQx/sodalogo.png"
					alt=""
					width="70px"
					height="30px"
					@click="goMain"
					style="cursor: pointer; margin-top: 5px"
				/>
			</v-toolbar-title>
			<v-toolbar-items style="margin-left: 1.5vw">
				<v-btn text plain @click="createMeeting"> 면접 개설 </v-btn>
				<v-btn text plain @click="attendMeeting"> 면접 참가 </v-btn>
				<v-btn text plain @click="goSimulatedMeeting"> 모의 면접 </v-btn>
				<v-btn text plain @click="goRegister"> 기업 등록 </v-btn>
				<v-btn text plain @click="goServiceGuide"> 사용 가이드 </v-btn>
			</v-toolbar-items>
		</v-app-bar>
	</div>
</template>

<script>
import { mapActions } from 'vuex';
import { createTestMeeting } from '@/api/meeting.js';

export default {
	name: 'TopBar',
	data() {
		return {
			bg: 'transparent',
		};
	},
	methods: {
		...mapActions(['setMeetingCode', 'setTestMeetingId', 'setMeetingName']),
		createMeeting() {
			if (this.$route.path !== '/certify') {
				this.$router.push({ name: 'Certify' });
			}
		},
		attendMeeting() {
			if (this.$route.path !== '/attend') {
				this.$router.push({ name: 'Attend' });
			}
		},
		async goSimulatedMeeting() {
			await createTestMeeting()
				.then(res => {
					this.setMeetingCode(res.data.name);
					this.setTestMeetingId(res.data.id);
					this.setMeetingName('탁소다 (면접자)');
					this.$router.push({ name: 'MeetingTest' });
				})
				.catch(err => {
					console.log(err.message);
				});
		},
		goRegister() {
			if (this.$route.path !== '/register') {
				this.$router.push({ name: 'Register' });
			}
		},
		goMain() {
			if (this.$route.path !== '/') {
				this.$router.push({ name: 'Home' });
			}
		},
		changeColor() {
			if (
				document.body.scrollTop > 50 ||
				document.documentElement.scrollTop > 50
			) {
				this.bg = 'rgb(255,255,255,0.9)';
			} else {
				this.bg = 'transparent';
			}
		},
		goServiceGuide() {
			if (this.$route.path !== '/serviceguide') {
				this.$router.push({ name: 'Guide' });
			}
		},
	},
	mounted() {
		window.onscroll = () => {
			this.changeColor();
		};
	},
};
</script>

<style scoped>
::v-deep .v-toolbar__content {
	padding: 0px !important;
}
.fixtop {
	position: fixed;
	z-index: 100;
}
</style>
