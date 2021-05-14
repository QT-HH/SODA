<template>
	<div style="padding-top: 70px">
		<button :class="isER" id="font2" @click="goInterviewer">면접관</button>
		<span class="toggleTextDevider" id="font2">/</span>
		<button :class="isEE" id="font2" @click="goInterviewee">면접자</button>

		<ForInterviewer v-if="toggle === true"></ForInterviewer>
		<ForInterviewee v-else></ForInterviewee>
	</div>
</template>

<script>
import ForInterviewer from '@/components/guide/ForInterviewer.vue';
import ForInterviewee from '@/components/guide/ForInterviewee.vue';
export default {
	name: 'Guide',
	components: {
		ForInterviewer,
		ForInterviewee,
	},
	data: () => {
		return {
			toggle: true,
			isEE: 'toggleText',
			isER: 'toggleTextActive',
		};
	},
	methods: {
		goInterviewer() {
			this.toggle = true;
			this.isER = 'toggleTextActive';
			this.isEE = 'toggleText';
		},
		goInterviewee() {
			this.toggle = false;
			this.isEE = 'toggleTextActive';
			this.isER = 'toggleText';
		},
	},
};
</script>

<style scoped>
.toggleText {
	font-size: 30px;
	font-weight: bold;
	color: #757575;
	outline: 0;
	margin: 30px;
}
.toggleTextDevider {
	font-size: 30px;
}
.toggleTextActive {
	font-size: 30px;
	font-weight: bold;
	color: black;
	outline: 0;
	margin: 30px;
}
.toggleTextActive:focus {
	outline: none;
}
</style>
