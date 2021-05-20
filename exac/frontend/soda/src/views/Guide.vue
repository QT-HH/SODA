<template>
	<div style="padding-top: 70px" class="bg">
		<button :class="isER" id="font2" @click="goInterviewer">면접관</button>
		<span class="toggleTextDevider" id="font2">/</span>
		<button :class="isEE" id="font2" @click="goInterviewee">면접자</button>

		<ForInterviewer v-if="toggle === true"></ForInterviewer>
		<ForInterviewee v-else></ForInterviewee>
		<button button="type" class="topBtn" id="font3">TOP</button>
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
	mounted() {
		var topBtn = document.querySelector('.topBtn');

		topBtn.addEventListener('click', function () {
			window.TweenMax.to(window, 1.5, {
				scrollTo: {
					y: 0,
					autoKill: true,
				},
				ease: window.Power3.easeInOut,
			});
		});
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
.bg {
	background-color: white;
}
.topBtn {
	position: fixed;
	bottom: 40px;
	right: 40px;
	padding: 5px;
	border: 2px solid black;
	border-radius: 10px;
	background-color: transparent;
	transition: all 0.3s ease-out;
	z-index: 101;
	font-size: 15px;
	color: black;
}
.topBtn:hover {
	background-color: #000;
	border-color: #333;
	color: #fff;
}
.topBtn:focus {
	outline: none;
}
</style>
