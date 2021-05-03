<template>
	<v-app>
		<TopBar v-show="!$store.state.meetingOn"></TopBar>
		<v-main>
			<router-view />
		</v-main>
	</v-app>
</template>

<script>
import TopBar from './components/common/TopBar.vue';

export default {
	name: 'app',

	components: {
		TopBar,
	},

	created() {
		console.log('hi');
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
</style>
