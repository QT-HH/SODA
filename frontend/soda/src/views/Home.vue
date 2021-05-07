<template>
	<div>
		<ul class="pointWrap">
			<p>SODA</p>
			<p>서비스 소개</p>
			<p>주요 기능</p>
			<p>사용 가이드</p>
		</ul>
		<!-- <p v-show="scroll0" class="arrow"><span></span></p> -->
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
		<section class="bgcolor">
			<div>
				<h1 style="margin-bottom: 40px" class="content">간단 소개</h1>
				<img
					style="width: 70%; margin-bottom: 20px"
					src="@/assets/intro.png"
					alt=""
				/>
				<p class="content">
					소다는 청각장애인을 위한 면접 플랫폼입니다. 면접관의 발언을 자막으로
					제공함으로써 좀 더 편리한 면접 환경을 제공합니다.
				</p>
			</div>
		</section>
		<section class="bgcolor">
			<h1 style="margin-bottom: 40px">기능</h1>
			<div style="margin-bottom: 20px">
				<img
					style="width: 400px; margin-right: 100px; margin-top: 20px"
					src="@/assets/webrtc4.png"
					alt=""
				/>
				<img
					style="width: 400px; margin-left: 100px"
					src="@/assets/stt2.png"
					alt=""
				/>
			</div>
			<h1>webrtc + stt</h1>
		</section>
		<section class="bgcolor"><h1>사용 방법</h1></section>
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
		};
	},
	methods: {
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
		showArrow() {
			if (
				document.body.scrollTop > 100 ||
				document.documentElement.scrollTop > 100
			) {
				this.scroll0 = false;
			} else {
				this.scroll0 = true;
			}
		},
	},
	mounted() {
		// window.onscroll = () => {
		// 	this.showArrow();
		// };

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
.arrow {
	padding-top: 60px;
	position: fixed;
	bottom: 0;
	left: calc(50% - 20px);
	align-items: center;
	z-index: 100;
}
.arrow span {
	position: absolute;
	top: 0;
	left: 50%;
	width: 24px;
	height: 24px;
	margin-left: -12px;
	border-left: 3px solid white;
	border-bottom: 3px solid white;
	-webkit-transform: rotate(-45deg);
	transform: rotate(-45deg);
	-webkit-animation: sdb 1.5s infinite;
	animation: sdb 1.5s infinite;
	box-sizing: border-box;
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
