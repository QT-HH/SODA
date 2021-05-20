<template>
	<div class="bg">
		<section class="firstSection">
			<v-row class="content mt-15">
				<v-col class="firstLeftBox" style="margin-right: 100px">
					<img src="https://i.ibb.co/F7wntgb/2.png" alt="" class="imgArea" />
				</v-col>
				<v-col class="firstRightBox d-flex align-content-center flex-wrap">
					<div class="textArea">
						<p id="font3" style="font-size: 20px">STEP 01. 기업등록</p>
						<p id="font2" style="font-size: 35px">
							인증된 기업에게 제공하는 무료 면접 서비스
						</p>
						<p id="font2">
							기업 등록 한번으로 저희 서비스를 언제든지 사용하실 수 있습니다.
							기업 등록 승인은 최대 2일이 소요됩니다.
						</p>
					</div>
				</v-col>
			</v-row>
		</section>
		<section class="sectionGrey d-flex align-center">
			<v-row class="content">
				<v-col class="leftBox d-flex align-content-center flex-wrap">
					<div class="textArea">
						<p id="font3" style="font-size: 20px">STEP 02. 인증메일</p>
						<p id="font2" style="font-size: 35px">
							담당자 메일로 기업 코드와 미팅 코드 발송
						</p>
						<p id="font2">
							기업 등록이 승인되면, 인증메일을 통해 기업 인증 코드와 미팅 인증
							코드를 발송하며 링크를 통해 저희 서비스로 들어오실 수 있습니다.
						</p>
					</div>
				</v-col>
				<v-col class="rightBox" style="margin-left: 100px">
					<img
						src="https://i.ibb.co/JvBVDtt/image.png"
						alt=""
						class="imgArea"
					/>
				</v-col>
			</v-row>
		</section>
		<section class="sectionWhite d-flex align-center">
			<v-row class="content">
				<v-col class="leftBox" style="margin-right: 100px">
					<img
						src="https://i.ibb.co/H4Pxv79/image.png"
						alt=""
						class="imgArea"
					/>
				</v-col>
				<v-col class="rightBox d-flex align-content-center flex-wrap">
					<div class="textArea">
						<p id="font3" style="font-size: 20px">STEP 03. 기업코드 입력</p>
						<p id="font2" style="font-size: 35px">
							기업 인증 코드로 로그인 없이 사용 가능!
						</p>
						<p id="font2">
							메일로 받은 기업 인증 코드를 입력하여 로그인 기능 없이 면접방을
							개설할 수 있습니다.
						</p>
					</div>
				</v-col>
			</v-row>
		</section>
		<section class="sectionGrey d-flex align-center">
			<v-row class="content">
				<v-col class="leftBox d-flex align-content-center flex-wrap">
					<div class="textArea">
						<p id="font3" style="font-size: 20px">STEP 04. 면접자 초대</p>
						<p id="font2" style="font-size: 35px">
							면접관과 면접자를 한번에 초대 가능
						</p>
						<p id="font2">
							그 날의 면접을 진행할 면접관과 면접자들을 한번에 초대할 수
							있습니다. 초대하실 분의 이메일과 이름을 작성하시고 우측의 [+]
							버튼을 눌러야지 초대 리스트에 추가됩니다. 참여자를 다 작성하신 후
							[시작하기] 버튼을 누르면 해당 참여자에게 면접방에 참가할 수 있는
							코드가 발송되고 면접방이 개설됩니다.
						</p>
					</div>
				</v-col>
				<v-col class="rightBox" style="margin-left: 100px">
					<img
						src="https://i.ibb.co/T2gXzsR/image.png"
						alt=""
						class="imgArea"
					/>
				</v-col>
			</v-row>
		</section>
		<section class="sectionWhite d-flex align-center">
			<v-row class="content">
				<v-col class="leftBox" style="margin-right: 100px">
					<img
						src="https://i.ibb.co/dQQf14J/image.png"
						alt=""
						class="imgArea"
					/>
				</v-col>
				<v-col class="rightBox d-flex align-content-center flex-wrap">
					<div class="textArea">
						<p id="font3" style="font-size: 20px">STEP 05. 면접자 리스트</p>
						<p id="font2" style="font-size: 35px">
							면접자 리스트를 통해 그날의 면접자를 한번에 관리
						</p>
						<p id="font2">
							면접방에서 면접자를 관리할 수 있으며 면접자의 상태를 [예정] [진행]
							[완료] [삭제]로 분류할 수 있습니다. [진행] 상태의 면접자만이 해당
							면접방에 참가할 수 있습니다. 초대된 면접자들의 면접이 모두 종료된
							경우, [리스트 전체 삭제] 버튼을 통해 면접방 리스트를 초기화할 수
							있습니다.
						</p>
					</div>
				</v-col>
			</v-row>
		</section>
	</div>
</template>

<script>
export default {
	name: 'ForInterviewer',
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
	padding: 0;
}
.firstRightBox {
	min-height: 50vh;
	position: relative;
	right: 0px;
	text-align: start;
	padding: 0;
}
.imgArea {
	width: 550px;
}
.textArea {
	width: 400px;
}
</style>
