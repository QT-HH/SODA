<template>
	<v-row justify="center">
		<v-col cols="12" sm="10" md="8" lg="6" style="margin: auto">
			<div>
				<h2 id="font1">미팅을 시작하시겠습니까?</h2>
				<br />
				<h2 id="font1">초대할 이메일을 입력해주세요.</h2>
				<br />
				<br />
				<p id="font1">면접관</p>
				<div>
					<v-text-field v-model="firemailmg"></v-text-field>
				</div>
				<div v-for="(item, index) of inputsmg" v-bind:key="index">
					<v-text-field v-model="emailsmg[index]"></v-text-field>
				</div>
				<div>
					<v-btn
						color="secondary"
						fab
						x-small
						dark
						@click="addMG"
						v-show="inviteok"
					>
						+
					</v-btn>
				</div>
				<br />
				<br />
				<br />
				<br />
				<p id="font1">면접자</p>
				<div>
					<v-text-field v-model="firemailmj"></v-text-field>
				</div>
				<div v-for="(item, index) of inputsmj" v-bind:key="index">
					<v-text-field v-model="emailsmj[index]"></v-text-field>
				</div>
				<div>
					<v-btn color="secondary" fab x-small dark @click="addMJ"> + </v-btn>
				</div>
				<br />
				<br />
				<br />
				<br />
				<div>
					<v-btn color="primary" @click="startMeeting"> 시작하기 </v-btn>
				</div>
			</div>
		</v-col>
	</v-row>
</template>

<script>
import { mapActions, mapGetters, mapState } from 'vuex';

export default {
	name: 'InvitePage',
	data: () => {
		return {
			inputsmg: 0,
			inputsmj: 0,
			inviteok: true,
			firemailmj: '',
			firemailmg: '',
			emailsmg: [],
			emailsmj: [],
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
		addMG() {
			this.inputsmg++;
			if (this.inputsmg >= 2) this.inviteok = false;
		},
		addMJ() {
			this.inputsmj++;
		},
		startMeeting() {
			if (this.firemailmj == '' || this.firemailmg) {
				alert('이메일을 입력해주세요.');
				return;
			}
			console.log(this.firemailmj);
			for (let index = 0; index < this.emailsmj.length; index++) {
				if (this.emailsmj[index] == '') {
					alert('이메일을 입력해주세요.');
					return;
				}
				var el = this.emailsmj[index];
				console.log(el);
			}
			console.log(this.firemailmg);
			for (let index = 0; index < this.emailsmg.length; index++) {
				if (this.emailsmg[index] == '') {
					alert('이메일을 입력해주세요.');
					return;
				}
				var el2 = this.emailsmg[index];
				console.log(el2);
			}
			// 면접관&면접자 초대 이메일 전송 API
		},
	},
};
</script>

<style scoped></style>
