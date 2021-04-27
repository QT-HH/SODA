<template>
	<div>
		<div class="row no-gutters">
			<div id="wrapper" class="d-flex justify-content-center">
				<!-- 주최하기 버튼 -->
				<div class="p-1 bd-highlight" id="host">
					<button @click="hostbtn">주최하기</button>
				</div>
				<!-- 입장 코드 입력란 -->
				<div class="p-1 bd-highlight" id="guest">
					<input
						placeholder="입장 코드를 입력하세요"
						v-model="inputSessionId"
						@keyup.enter="guestbtn"
					/>
					<button v-show="inputSessionId" @click="guestbtn">입장하기</button>
				</div>
				<!-- 공개방 보기 버튼 -->
				<div class="p-1 bd-highlight" id="entrance">
					<button @click="openbtn">공개방 보기</button>
				</div>
			</div>
		</div>
	</div>
</template>

<script>
import { mapActions, mapGetters, mapState } from 'vuex';
import Swal from 'sweetalert2';
export default {
	name: 'HomePage',
	components: {},
	data: () => {
		return {
			inputSessionId: '',
			ishost: false,
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
		hostbtn() {
			this.createSessionId();
			//주최한다는 새로운 변수 추가
			this.ishost = true;
			this.changeMeetingDialog(true);
		},
		guestbtn() {
			if (!this.getId) {
				Swal.fire({
					title: '먼저 로그인을 해주세요!',
					icon: 'warning',
				});
				return false;
			}
			this.checkSessionId(this.inputSessionId).then(() => {
				//주최 안한다는 새로운 변수 추가
				this.ishost = false;
				this.changeMeetingDialog(true);
			});
		},
		openbtn() {
			this.$router.push({ name: 'OpenRoom' });
		},
	},
};
</script>

<style></style>
