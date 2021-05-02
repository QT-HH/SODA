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
			</v-toolbar-items>
		</v-app-bar>
	</div>
</template>

<script>
export default {
	name: 'TopBar',
	data() {
		return {
			bg: 'transparent',
		};
	},
	methods: {
		createMeeting() {
			this.$router.push({ name: 'Certify' });
		},
		attendMeeting() {
			this.$router.push({ name: 'Attend' });
		},
		goSimulatedMeeting() {
			this.$router.push({ name: 'Meeting' });
		},
		goRegister() {
			this.$router.push({ name: 'Register' });
		},
		goMain() {
			this.$router.push({ name: 'Home' });
		},
		changeColor() {
			if (
				document.body.scrollTop > 100 ||
				document.documentElement.scrollTop > 100
			) {
				this.bg = 'rgb(255,255,255,0.9)';
			} else {
				this.bg = 'transparent';
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
