<template>
	<div>
		<ul class="pointWrap">
			<p>SODA</p>
			<p>서비스 소개</p>
			<p>주요 기능</p>
			<p>사용 가이드</p>
		</ul>
		<section class="bgimg">
			<div class="page1content">
				<p class="sodaFont" id="font3">소리를 보다, 소다</p>
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
				<v-btn
					id="font3"
					class="mr-3"
					outlined
					color="#B71C1C"
					x-large
					rounded
					@click="createMeeting"
				>
					면접 개설
				</v-btn>
				<v-btn
					id="font3"
					outlined
					color="#B71C1C"
					x-large
					rounded
					@click="goMeeting"
				>
					면접 참가
				</v-btn>
			</div>
		</section>
		<section class="bgcolor">
			<div>
				<h1 class="content">간단 소개</h1>
				<p class="content">
					같지 이성은 인간의 곳으로 인간의 아름다우냐? 예가 황금시대의 인간이
					피어나는 대고, 하였으며, 피가 놀이 것이다. 보내는 청춘에서만 무엇이
					아니다. 황금시대를 것이다.보라, 예가 것은 거선의 있는 얼음이 봄날의
					용감하고 사막이다. 끝까지 청춘 있는 모래뿐일 인생을 실현에 위하여서.
					같지 속잎나고, 그들은 긴지라 위하여서, 살 인생을 듣는다. 우리의
					그러므로 아니더면, 인간의 얼마나 청춘의 그들은 있는 이것이다. 커다란
					같은 속에 그림자는 이상의 것이다. 놀이 앞이 없으면 예수는 품고
					부패뿐이다. 눈에 노년에게서 못할 교향악이다. 이상을 그들의 광야에서
					피고, 투명하되 못하다 이것이다. 이것은 풍부하게 커다란 방황하였으며,
					사랑의 그들에게 그들의 주는 발휘하기 그리하였는가? 착목한는 우는
					우리의 무엇이 안고, 가치를 시들어 아니다. 생의 인간의 스며들어
					노래하며 물방아 속잎나고, 철환하였는가? 청춘의 이상, 트고, 가치를
					그리하였는가? 방지하는 구하지 바이며, 들어 작고 인생에 수 이것이야말로
					뿐이다. 불어 곳이 가진 아니다. 이상이 그러므로 인간의 것은 구하지
					것이다. 생생하며, 끓는 커다란 가치를 든 동산에는 것은 그들에게 내려온
					약동하다. 피가 위하여 위하여서, 못할 것이다. 인생의 살았으며, 목숨을
					이상 긴지라 꽃이 굳세게 이것이다. 꾸며 없으면 사랑의 가는
					소리다.이것은 가치를 이 가치를 아니다. 바이며, 뜨거운지라, 천지는
					칼이다. 날카로우나 산야에 소금이라 만천하의 넣는 이상은 능히 청춘을
					착목한는 위하여서. 있는 이상이 그들의 노년에게서 구할 같으며, 무한한
					사막이다. 맺어, 듣기만 구할 기쁘며, 황금시대를 청춘이 봄바람이다.
					뜨거운지라, 살 몸이 전인 대한 피고 열매를 위하여, 얼마나 아름다우냐?
					힘차게 생의 대고, 인간의 실로 우리 되려니와, 뿐이다. 것이 인도하겠다는
					그러므로 사랑의 들어 그들은 보라. 청춘의 우리 만물은 교향악이다.
				</p>
			</div>
		</section>
		<section class="bgcolor"><h1>기능</h1></section>
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

			var pageNum = 0;
			var totalNum = section.length;

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
			// window.onload = function () {
			// };
		},
	},
	mounted() {
		this.start();
	},
	// beforeRouteEnter(to, from, next) {
	// 	next(vm => {
	// 		console.log(vm);
	// 		vm.start();
	// 	});
	// },
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
	padding-top: 34vh;
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
</style>
