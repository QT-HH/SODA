<template>
	<div>
		<section class="bgcolor"></section>
		<div class="contentwrap">
			<h1 class="pb-3" id="font3">
				면접을 초대받은 이메일과 참여 코드를 입력해주세요.
			</h1>
		</div>
		<div class="inputWidth">
			<v-text-field
				solo
				flat
				rounded
				v-model="inputSessionId"
				v-on:keyup.enter="guestbtn"
				label="이메일"
			></v-text-field>
			<v-text-field
				solo
				flat
				rounded
				v-model="inputCertifycode"
				label="참여 코드"
			></v-text-field>
			<button id="font3" @click="guestbtn">입장 하기</button>
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
</template>

<script>
import { mapActions } from 'vuex';
import { attendMeeting } from '@/api/meeting.js';
import { checkIsHost } from '@/api/member.js';

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
	methods: {
		...mapActions([
			'setMeetingCode',
			'setMeetingName',
			'setIsSuperUser',
			'setIsHost',
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
			attendMeeting(this.inputSessionId, this.inputCertifycode).then(res => {
				if (res.data === '미팅코드 오류') {
					alert('유효하지 않은 이메일 혹은 미팅코드입니다.');
					return;
				}
				const stat = res.data.split(',');
				if (stat[0] === '면접자') {
					switch (stat[1]) {
						case 'PLAN':
							alert('순서를 기다려주세요.');
							break;
						case 'PROGRESS':
							this.setMeetingName(`${stat[2]} (면접자)`);
							this.openOrJoin(this.inputCertifycode);
							break;
						case 'DONE':
							alert('종료된 면접입니다.');
							break;
					}
				} else {
					checkIsHost(this.inputSessionId, this.inputCertifycode).then(res => {
						if (res.data) {
							this.setIsHost(true);
						}
					});
					this.setMeetingName(`${stat[1]} (면접관)`);
					this.setIsSuperUser(true);
					this.openOrJoin(this.inputCertifycode);
				}
			});
		},
		openOrJoin(meetingCode) {
			this.setMeetingCode(meetingCode);
			this.$router.push('/meeting');
		},
	},
};
</script>

<style scoped>
.bgcolor {
	position: relative;
	width: 100vw;
	height: 100vh;
	background-color: #e0dcdd;
}
.contentwrap {
	width: 800px;
	position: fixed;
	top: calc(50% - 120px);
	left: calc(50% - 400px);
}
.inputWidth {
	width: 500px;
	position: fixed;
	top: calc(50% - 40px);
	left: calc(50% - 250px);
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
