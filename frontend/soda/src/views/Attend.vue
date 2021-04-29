<template>
	<v-row justify="center">
		<v-col cols="12" sm="10" md="8" lg="6" style="margin: auto">
			<div>
				<h2 id="font1">면접에 초대받으신 이메일을 입력해주세요.</h2>
				<br />
				<br />
				<br />
				<br />
				<br />
				<br />
				<div>
					<v-text-field
						v-model="inputSessionId"
						v-on:keyup.enter="guestbtn"
						label="Email"
					></v-text-field>
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
				</div>
			</div>
		</v-col>
	</v-row>
</template>

<script>
import { mapActions, mapGetters, mapState } from 'vuex';
import Swal from 'sweetalert2';
export default {
	name: 'AttendPage',
	components: {},
	data: () => {
		return {
			inputSessionId: '',
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
	},
};
</script>

<style scoped></style>
