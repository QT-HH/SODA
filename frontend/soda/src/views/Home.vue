<template>
	<div>
		<ul class="pointWrap">
			<p>SODA</p>
			<p>서비스 소개</p>
			<p>주요 기술</p>
			<p>사용 방법</p>
		</ul>
		<button button="type" class="topBtn" id="font3">TOP</button>
		<section class="bgimg">
			<div class="page1content">
				<h1 class="sodaFont pb-3" id="font3">
					<span>소</span>
					<span>리</span>
					<span>를 </span>
					<span>보</span>
					<span>다</span>
					<span>, </span>
					<span>소</span>
					<span>다</span>
				</h1>
				<p class="sodaContentFont margin0" id="font2">
					소다는 면접 참여자의 말을 실시간 자막으로 제공하는 화상 면접
					서비스입니다.
				</p>
				<p
					class="sodaContentFont margin0"
					id="font2"
					style="padding-bottom: 24px"
				>
					소리를 보여주는 SODA, 지금 만나보세요.
				</p>
				<button id="font3" class="mr-3 btn" @click="createMeeting">
					면접 개설
				</button>
				<button id="font3" class="mr-3 btn" @click="goMeeting">
					면접 참가
				</button>
			</div>
		</section>
		<section class="bgcolor" style="margin-top: 100px">
			<div style="padding-top: 75px">
				<p>
					<span id="font3" class="text1">SODA</span>
					<span id="font2" class="text2">는 </span>
					<span id="font3" class="text1">청각장애인</span>
					<span id="font2" class="text2">을 위한 </span>
					<span id="font3" class="text1">화상 면접 </span>
					<span id="font2" class="text2">플랫폼입니다.</span>
				</p>
				<p class="mb-0">
					<span id="font2" class="text3">면접 참여자의 발언을 </span>
					<span id="font3" class="text4">실시간 자막</span>
					<span id="font2" class="text3"> 으로 제공함으로써 사용자에게 </span>
				</p>
				<p>
					<span id="font2" class="text3">
						더 편리한 면접 환경을 제공합니다.
					</span>
				</p>
				<v-lazy
					v-model="isActive"
					:options="{
						threshold: 0.9,
					}"
					transition="scroll-y-reverse-transition"
					min-height="200"
					class="pt-7"
				>
					<img
						style="width: 70%; margin-bottom: 30px"
						src="@/assets/intro.png"
						alt=""
					/>
				</v-lazy>
			</div>
		</section>
		<section class="bgcolor">
			<v-container class="pt-6">
				<v-row justify="center">
					<v-col>
						<div class="box1 pr-0 pt-7">
							<p id="font3" class="text5 mb-10">WebRTC</p>
							<p id="font2" class="text3 mb-0">
								WebRTC 기술을 활용하여 별도의 플러그인 없이
							</p>
							<p class="text3">
								<span id="font3">자체적</span>
								<span id="font2">으로 </span>
								<span id="font3">화상 회의 환경</span>
								<span id="font2">을 제공합니다.</span>
							</p>
							<img
								style="width: 55%; margin-top: 15px; margin-bottom: 30px"
								src="@/assets/webrtc.png"
								alt=""
							/>
							<div class="textBox1">
								<p>
									<span id="font2">※ </span>
									<span id="font3" class="deco">WebRTC</span>
									<span id="font3">(Web Real-Time Communication)</span>
									<span id="font2"
										>는 중간자 없이 브라우저 간에 오디오나 영상 미디어를
										마음대로 스트림 할 뿐 아니라, 임의의 데이터도 교환할 수
										있도록 하는 기술입니다.</span
									>
								</p>
							</div>
						</div>
					</v-col>
					<v-col>
						<div class="box1 pr-0 pt-7">
							<p id="font3" class="text5 mb-10">STT</p>
							<p id="font2" class="text3 mb-0">
								음성 인식 기술을 활용하여 면접 참여자의 대화를
							</p>
							<p class="text3">
								<span id="font2">텍스트로 </span>
								<span id="font3">실시간 전환</span>
								<span id="font2">하여 </span>
								<span id="font3">자막</span>
								<span id="font2">으로 제공합니다.</span>
							</p>
							<img
								style="width: 55%; margin-bottom: 40px"
								src="@/assets/stt.png"
								alt=""
							/>
							<div class="textBox1">
								<p>
									<span id="font2">※ </span>
									<span id="font3" class="deco">STT</span>
									<span id="font3">(Sppech-to-Text)</span>
									<span id="font2"
										>는 사람이 말하는 음성 언어를 컴퓨터가 해석하여 그 내용을
										문자 데이터로 전환하여 처리하는 것을 말하며
									</span>
									<span id="font3">음성인식</span>
									<span id="font2">(Speech Recognition)이라고도 부릅니다.</span>
								</p>
							</div>
						</div>
					</v-col>
				</v-row>
			</v-container>
		</section>
		<section class="bgcolor">
			<p class="pt-10 pb-10">
				<span id="font3" class="text6">소다</span>
				<span id="font2" class="text7">는 이렇게 사용합니다!</span>
			</p>

			<img
				src="@/assets/interviewer.png"
				alt=""
				style="width: 55%"
				class="mb-10"
			/>
			<img
				src="@/assets/interviewee.png"
				alt=""
				style="width: 55%"
				class="mb-10"
			/>
			<p>
				<span style="color: black" class="under">
					※ 더 자세한 사용 가이드를 원하신다면 상단의 서비스 가이드 탭을
					클릭하여 확인해주세요.
				</span>
			</p>
		</section>
	</div>
</template>

<script>
export default {
	name: 'Home',
	data: function () {
		return {
			users: function () {
				return [];
			},
			error: null,
			isRun: true,
			scroll0: true,
			length: 3,
			onboarding: 0,
			interviewerimg: [
				'https://i.ibb.co/XFx69N4/image.png',
				'https://i.ibb.co/Msz6tCF/image.png',
				'https://i.ibb.co/PTGQ4g5/image.png',
			],
			isActive: false,
		};
	},
	methods: {
		next() {
			this.onboarding =
				this.onboarding + 1 === this.length ? 0 : this.onboarding + 1;
		},
		prev() {
			this.onboarding =
				this.onboarding - 1 < 0 ? this.length - 1 : this.onboarding - 1;
		},
		createMeeting() {
			this.$router.push({ name: 'Certify' });
		},
		goMeeting() {
			this.$router.push({ name: 'Attend' });
		},
		start() {
			var section = document.getElementsByTagName('section');
			var pointBtn = document.querySelectorAll('.pointWrap p');
			var topBtn = document.querySelector('.topBtn');

			var pageNum = 0;
			var totalNum = section.length;

			var title = document.querySelector('.sodaFont');

			for (var i = 0; i < pointBtn.length; i++) {
				(function (idx) {
					pointBtn[idx].onclick = function () {
						pageNum = idx;
						pageChangeFunc();
						window.scrollTo({
							top: section[pageNum].offsetTop - 56,
							behavior: 'smooth',
						});
					};
				})(i);
			}
			window.addEventListener('scroll', function () {
				var scroll = this.scrollY;

				for (var i = 0; i < totalNum; i++) {
					if (
						scroll > section[i].offsetTop - window.outerHeight / 2 &&
						scroll <
							section[i].offsetTop -
								window.outerHeight / 2 +
								section[i].offsetHeight
					) {
						pageNum = i;
						break;
					}
				}
				pageChangeFunc();
			});

			function pageChangeFunc() {
				for (var i = 0; i < totalNum; i++) {
					section[i].classList.remove('active');
					pointBtn[i].classList.remove('active');
				}
				section[pageNum].classList.add('active');
				pointBtn[pageNum].classList.add('active');
			}
			pageChangeFunc();

			function textChangeFunc() {
				for (var j = 0; j < title.querySelectorAll('span').length; j++) {
					var _text = title.querySelectorAll('span')[j];

					window.TweenMax.from(_text, 1, {
						autoAlpha: 0,
						scale: 4,
						delay: Math.random() * 1.2,
						ease: window.Power4.easeInOut,
					});
				}
			}
			textChangeFunc();
			topBtn.addEventListener('click', function () {
				window.TweenMax.to(window, 1.5, {
					scrollTo: {
						y: 0,
						autoKill: true,
					},
					ease: window.Power3.easeInOut,
				});
			});
		},
	},
	mounted() {
		this.start();
	},
};
</script>

<style scoped>
.pointWrap {
	position: fixed;
	top: 40%;
	z-index: 100;
}

.pointWrap p {
	list-style: none;
	width: 70px;
	height: 12px;
	margin-top: 15px;
	cursor: pointer;
	color: #666;
	font-size: 12px;
	transition: background 0.3s ease-out;
}

.pointWrap p.active {
	color: black;
	font-weight: bolder;
}
section {
	position: relative;
	width: 100vw;
	height: 100vh;
}
.image {
	width: 100vw;
	height: 100vh;
}
.bgimg {
	background-image: url(https://i.ibb.co/gmpP16F/main2.jpg);
	background-size: cover;
	background-position: center;
	background-repeat: no-repeat;
}
.bgcolor {
	background-color: #e0dcdd;
}
.content {
	padding-left: 15vw;
	padding-right: 15vw;
}
.page1content {
	padding-left: 15vw;
	padding-right: 15vw;
	text-align: start;
	padding-top: 35vh;
}
.sodaFont {
	font-size: 3em;
	font-weight: bolder;
	text-align: start;
	color: black;
	padding: 0px;
}
.sodaContentFont {
	font-size: medium;
	text-align: start;
	padding: 0px;
}
.margin0 {
	margin-bottom: 0px;
}
.btn {
	background-color: transparent;
	border: 2px solid #b71c1c;
	border-radius: 30px;
	color: #b71c1c;
	cursor: pointer;
	font-size: 17px;
	padding: 12px 30px;
	transition: all 200ms;
}
.btn:hover {
	background-color: #b71c1c;
	color: white;
	outline: 0;
}
.btn:focus {
	outline: none;
}
.topBtn {
	position: fixed;
	bottom: 40px;
	right: 40px;
	padding: 5px;
	border: 2px solid black;
	border-radius: 10px;
	background-color: transparent;
	transition: all 0.3s ease-out;
	z-index: 101;
	font-size: 15px;
	color: black;
}
.topBtn:hover {
	background-color: #000;
	border-color: #333;
	color: #fff;
}
.highlight {
	border-bottom: 3px solid #0b0072;
	padding: 0px 15px;
}
.interviewer {
	background-color: rgb(187, 187, 187);
	height: 100vh;
}
.interviewee {
	background-color: rgb(149, 150, 218);
	height: 100vh;
}
.important {
	display: inline;
	box-shadow: inset 0 -40px 0 #c0ff96;
	margin: 0px;
	padding: 3px;
}
.under {
	text-decoration: underline;
}
@-webkit-keyframes sdb {
	0% {
		-webkit-transform: rotate(-45deg) translate(0, 0);
		opacity: 0;
	}
	50% {
		opacity: 1;
	}
	100% {
		-webkit-transform: rotate(-45deg) translate(-20px, 20px);
		opacity: 0;
	}
}
@keyframes sdb {
	0% {
		transform: rotate(-45deg) translate(0, 0);
		opacity: 0;
	}
	50% {
		opacity: 1;
	}
	100% {
		transform: rotate(-45deg) translate(-20px, 20px);
		opacity: 0;
	}
}
.text1 {
	color: #2962ff;
	font-size: 45px;
}
.text2 {
	color: #424242;
	font-size: 45px;
	font-weight: 500;
}
.text3 {
	color: #424242;
	font-size: 20px;
}
.text4 {
	color: #424242;
	font-size: 20px;
	text-emphasis-style: dot;
	text-emphasis-position: over left;
	text-emphasis-color: #2962ff;
	-webkit-text-emphasis-style: dot;
	-webkit-text-emphasis-position: over;
}
.text5 {
	color: #424242;
	font-size: 45px;
	font-weight: 500;
	text-decoration: underline solid #2962ff;
	text-underline-position: under;
}
.text6 {
	color: #2962ff;
	font-size: 40px;
}
.text7 {
	color: #424242;
	font-size: 40px;
}
.textBox1 {
	width: 400px;
	position: relative;
	left: 50%;
	transform: translateX(-50%);
	text-align: start;
}
.deco {
	text-emphasis-style: dot;
	text-emphasis-position: over left;
	text-emphasis-color: #2962ff;
	-webkit-text-emphasis-style: dot;
	-webkit-text-emphasis-position: over;
}
.box1 {
	background-color: white;
	min-width: 550px;
	min-height: 85vh;
}
</style>
