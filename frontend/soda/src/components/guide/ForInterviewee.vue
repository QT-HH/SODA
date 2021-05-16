<template>
	<div class="bg">
		<section class="firstSection">
			<v-row class="content mt-15">
				<v-col class="firstLeftBox d-flex align-content-center flex-wrap">
					<div class="textArea">
						<p id="font3" style="font-size: 20px">STEP 01. 인증메일</p>
						<p id="font2" style="font-size: 35px">
							이메일로 발송된 미팅 코드 확인
						</p>
						<p id="font2">
							면접관이 면접자를 초대하면, 입사 지원시 등록하셨던 이메일로 미팅
							인증 코드가 발송됩니다.
						</p>
					</div>
				</v-col>
				<v-col class="firstRightBox" style="margin-left: 100px">
					<img src="https://i.ibb.co/nmjS0nK/2.png" alt="" class="imgArea" />
				</v-col>
			</v-row>
		</section>
		<section class="sectionGrey d-flex align-center">
			<v-row class="content">
				<v-col class="leftBox" style="margin-right: 100px">
					<img
						src="https://i.ibb.co/0fJCFf0/image.png"
						alt=""
						class="imgArea"
					/>
				</v-col>
				<v-col class="rightBox d-flex align-content-center flex-wrap">
					<div class="textArea">
						<p id="font3" style="font-size: 20px">STEP 02. 면접참가</p>
						<p id="font2" style="font-size: 35px">
							초대 받은 이메일과 미팅코드로 면접방 참가 가능
						</p>
						<p id="font2">
							링크를 통해 저희 서비스로 들어오시면 인증코드와 이메일 입력 후
							면접방에 참가하실 수 있습니다. 안내 받은 면접 시간 외에는 면접방
							입장이 불가능하니 입장 시간을 확인해주세요.
						</p>
					</div>
				</v-col>
			</v-row>
		</section>
		<section class="sectionWhite d-flex align-center">
			<v-row class="content">
				<v-col class="leftBox d-flex align-content-center flex-wrap">
					<div class="textArea">
						<p id="font3" style="font-size: 20px">STEP 03. 모의면접</p>
						<p id="font2" style="font-size: 35px">
							면접 환경을 테스트할 수 있는 모의 면접 기능
						</p>
						<p id="font2">
							모의 면접은 실제면접 환경과 동일하며 면접 환경을 미리 테스트할 수
							있습니다. 가이드 영상을 참고하여 모든 기능이 제대로 동작하는지
							확인해주세요.
						</p>
						<button class="testBtn" id="font3" @click="goSimulatedMeeting">
							모의면접 바로 가기 〉
						</button>
					</div>
				</v-col>
				<v-col class="rightBox" style="margin-left: 100px">
					<img
						src="https://i.ibb.co/H4Pxv79/image.png"
						alt=""
						class="imgArea"
					/>
				</v-col>
			</v-row>
		</section>
	</div>
</template>

<script>
import { createTestMeeting } from '@/api/meeting.js';
import { mapActions } from 'vuex';

export default {
	name: 'ForInterviewee',
	methods: {
		...mapActions(['setMeetingCode', 'setTestMeetingId', 'setMeetingName']),
		async goSimulatedMeeting() {
			await createTestMeeting()
				.then(res => {
					this.setMeetingCode(res.data.name);
					this.setTestMeetingId(res.data.id);
					this.setMeetingName('탁소다 (면접자)');
					this.$router.push({ name: 'MeetingTest' });
				})
				.catch(err => {
					console.log(err.message);
				});
		},
	},
};
</script>

<style scoped>
.firstSection {
	position: relative;
	width: 100vw;
	min-height: 70vh;
}
.content {
	width: 70vw;
	margin-left: 15vw;
	margin-right: 15vw;
}
.sectionGrey {
	background-color: #f5f5f5;
	position: relative;
	width: 100vw;
	min-height: 85vh;
}
.sectionWhite {
	position: relative;
	width: 100vw;
	min-height: 85vh;
}
.leftBox {
	min-height: 50vh;
	position: relative;
	left: 0px;
	text-align: start;
	padding: 0;
}
.rightBox {
	min-height: 50vh;
	position: relative;
	right: 0px;
	text-align: start;
	padding: 0;
}
.firstLeftBox {
	min-height: 50vh;
	position: relative;
	left: 0px;
	text-align: start;
	padding: 0;
}
.firstRightBox {
	min-height: 50vh;
	position: relative;
	right: 0px;
	padding: 0;
}
.imgArea {
	width: 550px;
}
.textArea {
	width: 400px;
}
.testBtn {
	font-size: 20px;
	cursor: pointer;
	color: #616161;
}
.testBtn:hover {
	color: #304ffe;
	outline: 0;
}
.testBtn:focus {
	outline: none;
}
</style>
