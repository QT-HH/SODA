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
		</v-col>
	</v-row>
</template>

<script>
import { getConfirmMeetingCode } from '@/api/meeting.js';

export default {
	name: 'Certify',
	components: {},
	data: () => ({
		code: null,
		dialogOpen: false,
	}),
	methods: {
		async certify() {
			if (this.code === null) {
				alert('인증코드를 입력해주세요.');
			} else {
				await getConfirmMeetingCode(this.code)
					.then(res => {
						if (res.data) this.$router.push('/invite');
						else this.dialogOpen = true;
					})
					.catch(err => {
						console.log('에러:' + err);
					});
			}
		},
	},
};
</script>

<style></style>
