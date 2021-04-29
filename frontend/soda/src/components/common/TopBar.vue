<template>
	<div>
		<v-toolbar flat fixed>
			<v-toolbar-title>
				<img
					src="https://i.ibb.co/09bdJjX/soda1.png"
					alt=""
					width="100px"
					height="50px"
					@click="goMain"
					style="cursor: pointer"
				/>
			</v-toolbar-title>
			<v-toolbar-items>
				<v-btn text plain @click="createMeeting"> 면접 개설 </v-btn>
				<v-btn text plain @click="attendMeeting"> 면접 참가 </v-btn>
				<v-btn text plain @click="goSimulatedMeeting"> 모의 면접 </v-btn>
				<v-btn text plain @click="goRegister"> 기업 등록 </v-btn>
			</v-toolbar-items>
		</v-toolbar>
	</div>
</template>

<script>
export default {
	name: 'TopBar',
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
	},
};
</script>

<style></style>
