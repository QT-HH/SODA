<template>
	<div>
		<ul class="pointWrap">
			<p>SODA</p>
			<p>서비스 소개</p>
			<p>주요 기술</p>
			<p>사용 가이드</p>
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
				<button id="font3" class="mr-3" @click="createMeeting">
					면접 개설
				</button>
				<button id="font3" class="mr-3" @click="goMeeting">면접 참가</button>
			</div>
		</section>
		<section class="bgcolor" style="margin-top: 100px">
			<div>
				<h1 style="margin-bottom: 50px" class="content">
					<span class="highlight">간단 소개</span>
				</h1>
				<v-lazy
					v-model="isActive"
					:options="{
						threshold: 0.9,
					}"
					transition="scroll-y-reverse-transition"
					min-height="200"
				>
					<img
						style="width: 70%; margin-bottom: 30px"
						src="@/assets/intro.png"
						alt=""
					/>
				</v-lazy>
				<p class="content" style="font-size: x-large">
					<span style="color: navy; font-weight: bold">소다</span>는
					<span style="font-weight: bold">청각장애인</span>을 위한
					<span style="font-weight: bold">면접 플랫폼</span>입니다.
				</p>
				<p class="content" style="font-size: x-large">
					면접관의 발언을 <span style="font-weight: bold">실시간 자막</span>으로
					보여줌으로써 좀 더 편리한 면접 환경을 제공합니다.
				</p>
			</div>
		</section>
		<section class="bgcolor">
			<h1 style="margin-bottom: 70px">
				<span class="highlight">기술</span>
			</h1>
			<div style="margin-bottom: 20px">
				<v-row>
					<v-col style="margin-left: 70px">
						<img
							style="width: 55%; margin-bottom: 20px"
							src="@/assets/webrtc.png"
							alt=""
						/>
						<p style="margin: 0px 80px">
							<span style="font-weight: bold; font-size: 120%"
								>WebRTC(Web Real-Time Communication)는</span
							>
							중간자 없이 <span><br /></span> 브라우저 간에 오디오나 영상
							미디어를 마음대로 스트림 할 뿐 아니라, <span><br /></span> 임의의
							데이터도 교환할 수 있도록 하는 기술입니다.
						</p>
					</v-col>
					<v-col style="margin-right: 70px">
						<img
							style="width: 55%; margin-bottom: 20px"
							src="@/assets/stt.png"
							alt=""
						/>
						<p style="margin-left: 70px; margin-right: 70px">
							<span style="font-weight: bold; font-size: 120%"
								>STT(Sppech-to-Text)는</span
							>
							사람이 말하는 음성 언어를 컴퓨터가 해석하여
							<span><br /></span>
							그 내용을 문자 데이터로 전환하여 처리하는 것을 말하며
							<span><br /></span> 음성인식 (Speech Recognition)이라고도
							부릅니다.
						</p>
					</v-col>
				</v-row>
			</div>

			<!-- <p class="content important" style="font-size: xx-large">WebRTC + STT</p>
			<p class="content" style="font-size: x-large">
				<span style="color: navy; font-weight: bold">SODA</span>는 WebRTC와 STT
				기능을 사용하여 화상면접에 자막기능을 추가하였습니다. ~~~~~~~
			</p> -->
		</section>
		<section class="bgcolor">
			<h1 style="margin-bottom: 70px">
				<span class="highlight">사용 방법</span>
			</h1>
			<v-row>
				<v-col style="padding: 0px">
					<div class="interviewer">
						<h1 style="padding-top: 20px; padding-bottom: 50px">면접관</h1>
						<v-card flat tile>
							<v-window v-model="onboarding" reverse>
								<v-window-item v-for="n in interviewerimg" :key="`card-${n}`">
									<v-card color="grey" height="500">
										<v-row class="fill-height" align="center" justify="center">
											<img :src="n" style="width: 500px" alt="" />
										</v-row>
									</v-card>
								</v-window-item>
							</v-window>

							<v-card-actions class="justify-space-between">
								<v-btn text @click="prev">
									<i class="fas fa-chevron-left"></i>
								</v-btn>
								<v-item-group
									v-model="onboarding"
									class="text-center"
									mandatory
								>
									<v-item
										v-for="n in length"
										:key="`btn-${n}`"
										v-slot="{ active, toggle }"
									>
										<v-btn :input-value="active" icon @click="toggle">
											<i class="fas fa-circle"></i>
										</v-btn>
									</v-item>
								</v-item-group>
								<v-btn text @click="next">
									<i class="fas fa-chevron-right"></i>
								</v-btn>
							</v-card-actions>
						</v-card>
					</div>
				</v-col>
				<v-col style="padding: 0px">
					<div class="interviewee">
						<h1 style="padding-top: 20px">면접자</h1>
					</div>
				</v-col>
			</v-row>
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
button {
	background-color: transparent;
	border: 2px solid #b71c1c;
	border-radius: 30px;
	color: #b71c1c;
	cursor: pointer;
	font-size: 17px;
	padding: 12px 30px;
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
</style>
