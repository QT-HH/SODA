<template>
	<div>
		<section class="bgcolor"></section>
		<div class="contentwrap">
			<h1 class="pb-3" id="font3">면접 참여자의 이메일을 입력해주세요.</h1>
			<p class="content" id="font2">
				* 해당 이메일로 면접 참여 코드가 전송됩니다.
			</p>
			<p id="font1">면접관</p>
			<div>
				<v-text-field></v-text-field>
			</div>
			<div v-for="i in inputs" v-bind:key="i">
				<invite-input></invite-input>
			</div>
			<v-btn
				color="secondary"
				fab
				x-small
				dark
				@click="addInvite"
				v-show="inviteok"
			>
				+
			</v-btn>

			<p id="font1">면접자</p>
			<div>
				<v-text-field></v-text-field>
			</div>
			<div>
				<v-btn color="primary"> 시작하기 </v-btn>
			</div>
		</div>
	</div>
</template>

<script>
import { mapActions, mapGetters, mapState } from 'vuex';
import InviteInput from '../components/InviteInput';

export default {
	name: 'InvitePage',
	components: {
		'invite-input': InviteInput,
	},
	data: () => {
		return {
			inputs: 0,
			inviteok: true,
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
		addInvite() {
			this.inputs++;
			console.log('추가' + this.inputs);
			if (this.inputs >= 2) this.inviteok = false;
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
	width: 600px;
	position: fixed;
	top: calc(50% - 241px);
	left: calc(50% - 250px);
}
.content {
	font-size: 13px;
}
</style>
