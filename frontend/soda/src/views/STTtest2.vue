<template>
	<div>
		<STTtext></STTtext>
	</div>
</template>

<script>
import STTtext from '@/components/meeting/stttext.vue';
export default {
	components: {
		STTtext,
	},
};
</script>

<style>
.output {
	display: block;
	width: 80%;
	height: 400px;
	margin: 50px auto;
}
</style>
