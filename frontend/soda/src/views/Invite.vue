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
			</div>
			<br />
			<br />
			<br />
			<br />
			<p id="font1">면접자</p>
			<div>
				<v-text-field></v-text-field>
			</div>
			<div>
				<v-btn color="primary"> 시작하기 </v-btn>
			</div>
		</v-col>
	</v-row>
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

<style scoped></style>
