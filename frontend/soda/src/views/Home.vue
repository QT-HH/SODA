<template>
	<div class="wrapper">
		<v-row cols="12" class="d-flex align-baseline mb-6">
			<v-col cols="1"> </v-col>
			<v-col cols="1">
				<v-btn
					id="font1"
					class="ma-2 pl-1 fontStyle"
					outlined
					color="#B71C1C"
					x-large
					rounded
				>
					면접 개설
				</v-btn>
			</v-col>
			<v-col cols="1">
				<v-btn
					id="font1"
					class="ma-2 pl-1 fontStyle"
					outlined
					color="#B71C1C"
					x-large
					rounded
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
};
</script>
<style scoped>
#bg {
	position: fixed;
	top: 0;
	left: 0;

	/* Preserve aspet ratio */
	width: 100%;
	height: 100%;
	object-fit: cover;
}
.wrapper {
	position: fixed;
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
</style>
