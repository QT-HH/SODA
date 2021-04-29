<template>
	<div class="wrapper">
		<div style="height: 230px"></div>
		<v-row cols="12">
			<v-col cols="2"> </v-col>
			<v-col class="sodaFont" cols="10">
				<p class="margin0">소리를 보다, 소다</p>
			</v-col>
			<v-col cols="2"> </v-col>
			<v-col class="sodaContentFont" cols="10">
				<p class="margin0" id="font1">
					소다는 면접 참여자의 말을 자막으로 실시간 변환하여 제공하는 화상 면접
					서비스입니다.
				</p>
			</v-col>
			<v-col cols="2"> </v-col>
			<v-col class="sodaContentFont" cols="10">
				<p class="margin0" id="font1">
					이 화면은 태진이오빠 때문에 할 수 없이 만든 임시 화면입니다^^
				</p>
			</v-col>
		</v-row>
		<v-row cols="12" class="d-flex align-baseline mb-6">
			<v-col cols="2"> </v-col>
			<v-col cols="1" sm="auto">
				<v-btn
					id="font1"
					class="ma-2 fontStyle"
					outlined
					color="#B71C1C"
					x-large
					rounded
					@click="createMeeting"
				>
					면접 개설
				</v-btn>
			</v-col>
			<v-col cols="1" sm="auto">
				<v-btn
					id="font1"
					class="ma-2 fontStyle"
					outlined
					color="#B71C1C"
					x-large
					rounded
					@click="goSimulatedMeeting"
				>
					모의 면접
				</v-btn>
			</v-col>
		</v-row>
	</div>
</template>

<script>
export default {
	name: 'Home',
	methods: {
		createMeeting() {
			this.$router.push({ name: 'Certify' });
		},
		goSimulatedMeeting() {
			this.$router.push({ name: 'Meeting' });
		},
	},
};
</script>
<style scoped>
.wrapper {
	width: 100%;
	height: 100%;
	object-fit: cover;
	background-image: url(https://i.ibb.co/gmpP16F/main2.jpg);
	background-size: cover;
	background-position: center;
	background-repeat: no-repeat;
}
.fontStyle {
	font-weight: bolder;
}
.sodaFont {
	font-size: 3em;
	font-weight: bolder;
	text-align: start;
	color: black;
	padding: 0px;
}
.sodaContentFont {
	font-size: medium;
	text-align: start;
	padding: 0px;
}
.margin0 {
	margin-bottom: 0px;
}
</style>
