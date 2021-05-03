<template>
	<div>
		<section class="bgcolor"></section>
		<div class="contentwrap">
			<h1 class="pb-3" id="font3">기업 코드를 입력해주세요.</h1>
			<p class="content" id="font2">
				* 기업 등록 시 기입한 이메일로 발송된 인증 코드
			</p>
			<v-text-field solo flat rounded v-model="code"></v-text-field>
			<button id="font3" @click="certify">다음</button>
			<div class="text-center">
				<v-dialog v-model="dialogOpen" width="500">
					<v-card>
						<v-card-title class="headline grey lighten-2">
							인증코드 오류
						</v-card-title>

						<v-card-text> 유효하지 않은 인증코드입니다. </v-card-text>

						<v-divider></v-divider>

						<v-card-actions>
							<v-spacer></v-spacer>
							<v-btn color="primary" text @click="dialogOpen = false">
								확인
							</v-btn>
						</v-card-actions>
					</v-card>
				</v-dialog>
			</div>
		</div>
	</div>
</template>

<script>
// import { getConfirmMeetingCode } from '@/api/meeting.js';

export default {
	name: 'Certify',
	components: {},
	data: () => ({
		code: null,
		dialogOpen: false,
	}),
	methods: {
		// async certify() {
		// 	if (this.code === null) {
		// 		alert('인증코드를 입력해주세요.');
		// 	} else {
		// 		await getConfirmMeetingCode(this.code)
		// 			.then(res => {
		// 				if (res.data) this.$router.push('/invite');
		// 				else this.dialogOpen = true;
		// 			})
		// 			.catch(err => {
		// 				console.log('에러:' + err);
		// 			});
		// 	}
		// },
	},
};
</script>
<style scoped>
.contentwrap {
	width: 500px;
	position: fixed;
	top: calc(50% - 111px);
	left: calc(50% - 250px);
}
.content {
	font-size: 13px;
}
.bgcolor {
	position: relative;
	width: 100vw;
	height: 100vh;
	background-color: #e0dcdd;
}
button {
	background-color: transparent;
	border: 2px solid #b71c1c;
	border-radius: 30px;
	color: #b71c1c;
	cursor: pointer;
	font-size: 17px;
	padding: 6px 20px;
	transition: all 200ms;
}
button:hover {
	background-color: #b71c1c;
	color: white;
	outline: 0;
}
button:focus {
	outline: none;
}
</style>
