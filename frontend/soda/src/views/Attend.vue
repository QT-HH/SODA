<template>
	<v-row justify="center">
		<v-col cols="12" sm="10" md="8" lg="6" style="margin: auto">
			<div>
				<h2 id="font1">면접에 초대받으신 이메일을 입력해주세요.</h2>
				<br />
				<br />
				<div>
					<v-text-field
						v-model="inputSessionId"
						v-on:keyup.enter="guestbtn"
						label="Email"
					></v-text-field>
				</div>
				<br />
				<br />
				<br />
				<h2 id="font1">인증코드를 입력해주세요.</h2>
				<br />
				<br />
				<div>
					<v-text-field v-model="inputCertifycode" label="Code"></v-text-field>
				</div>
				<div>
					<v-btn
						rounded
						color="primary"
						dark
						v-show="inputSessionId"
						@click="guestbtn"
					>
						입장하기
					</v-btn>
					<br />
					<br />
				</div>
				<div class="text-center">
					<v-dialog v-model="dialogOpen" width="500">
						<v-card>
							<v-card-title class="headline grey lighten-2">
								유효성 검사
							</v-card-title>

							<v-card-text> 유효성 검사를 다시 확인해주세요! </v-card-text>

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
import { mapActions, mapGetters, mapState } from 'vuex';

export default {
	name: 'AttendPage',
	components: {},
	data: () => {
		return {
			inputSessionId: '',
			inputCertifycode: '',
			dialogOpen: false,
		};
	},
	computed: {
		...mapGetters(['getId']),
		...mapState(['user']),
		...mapState('meetingStore', ['mySessionId', 'meetingDialog']),
	},
	methods: {
		...mapActions('meetingStore', [
			'createSessionId',
			'checkSessionId',
			'changeMeetingDialog',
		]),
		guestbtn() {
			// 이메일&인증코드 유효성 확인
			if (this.inputSessionId == '' || this.inputCertifycode == '') {
				this.dialogOpen = true;
				return false;
			}
			let regExp = /^([\w-]+(?:\.[\w-]+)*)@((?:[\w-]+\.)*\w[\w-]{0,66})\.([a-z]{2,6}(?:\.[a-z]{2})?)$/i;
			if (regExp.test(this.inputSessionId) == false) {
				//이메일 형식이 알파벳+숫자@알파벳+숫자.알파벳+숫자 형식이 아닐경우
				this.dialogOpen = true;
				return false;
			}
			// 이메일이 초대된 이메일인지 판단하는 api 불러오기
			// 인증코드의 미팅방으로 이동
			this.$router.push('/meeting'); // 임시 routing
		},
	},
};
</script>

<style scoped></style>
