<template>
	<v-app>
		<TopBar v-show="!meetingOn"></TopBar>
		<v-main class="bgcolor">
			<router-view />
		</v-main>
	</v-app>
</template>

<script>
import { mapState } from 'vuex';
import TopBar from './components/common/TopBar.vue';

export default {
	name: 'app',

	components: {
		TopBar,
	},
	computed: {
		...mapState(['meetingOn']),
	},

	created() {
		if (window.Notification) {
			Notification.requestPermission();
		}
	},
};
</script>

<style>
@font-face {
	font-family: 'Cafe24Simplehae';
	src: url('https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_twelve@1.1/Cafe24Simplehae.woff')
		format('woff');
	font-weight: normal;
	font-style: normal;
}
#font1 {
	font-family: Cafe24Simplehae;
}
@font-face {
	font-family: 'S-CoreDream-3Light';
	src: url('https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_six@1.2/S-CoreDream-3Light.woff')
		format('woff');
	font-weight: normal;
	font-style: normal;
}
#font2 {
	font-family: S-CoreDream-3Light;
}
@font-face {
	font-family: 'S-CoreDream-6Bold';
	src: url('https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_six@1.2/S-CoreDream-6Bold.woff')
		format('woff');
	font-weight: normal;
	font-style: normal;
}
#font3 {
	font-family: S-CoreDream-6Bold;
}
#app {
	font-family: Avenir, Helvetica, Arial, sans-serif;
	-webkit-font-smoothing: antialiased;
	-moz-osx-font-smoothing: grayscale;
	text-align: center;
	color: #2c3e50;
}
.bgcolor {
	background-color: #e0dcdd;
}
video {
	height: 40vh;
	margin: 5px;
}
.videoBox {
	height: 40vh;
	width: 60vh;
}
.effectName {
	position: relative;
	top: -40px;
	background-color: rgba(255, 255, 255, 0.8);
	margin: 0 3px;
}
.effectVideo {
	position: relative;
}
.retireBtn:hover {
	/* background-color: #bbbbbb; */
	color: #b71c1c;
	outline: 0;
}
.retireBtn:focus {
	outline: none;
}
.retireBtn {
	position: relative;
	top: -60px;
	right: -160px;
	color: black;
	cursor: pointer;
	font-size: 15px;
	padding: 2px 10px;
	transition: all 200ms;
	text-shadow: 2px 2px 2px gray;
}
</style>
