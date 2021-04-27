<template>
	<v-row justify="center">
		<v-col cols="12" sm="10" md="8" lg="6" style="margin: auto">
			<div>
				<br />
				<h2>인증코드를 입력해주세요.</h2>
				<br />
				<br />
				<br />
				<p>인증코드</p>
				<br />
				<div>
					<v-text-field filled rounded dense v-model="code"></v-text-field>
				</div>
				<div>
					<v-btn color="primary" text @click="certify"> 다음 </v-btn>
				</div>
			</div>
		</v-col>
	</v-row>
</template>

<script>
export default {
	name: 'Certify',
	components: {},
	data: () => ({
		code: null,
	}),
	methods: {
		certify() {
			if (this.code === null) {
				alert('인증코드를 입력해주세요.');
			} else {
				console.log(this.code);
				// this.$router.push('/invite');  -> 초대페이지로 넘어감
			}
		},
	},
};
</script>

<style></style>
