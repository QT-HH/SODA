<template>
	<div>
		<Loader />
		<section class="bgcolor"></section>
		<div class="contentwrap">
			<h1 class="pb-3" id="font3">면접 참여자의 이메일을 입력해주세요.</h1>
			<p class="content ma-0" id="font2">
				* 해당 이메일로 면접 참여 코드가 전송됩니다.
			</p>
			<p class="content" id="font2">
				(+) 버튼을 누르셔야지 초대 목록에 추가됩니다.
			</p>
			<div class="section1">
				<span id="font3" style="font-size: 20px">면접관 </span>
				<span id="font2" style="font-size: 15px">최대 2명</span>
				<v-row cols="12" class="pt-3">
					<v-col cols="5">
						<v-text-field
							v-model="firemailmg"
							solo
							flat
							dense
							class="px-2"
							placeholder="이메일"
						></v-text-field>
					</v-col>
					<v-col cols="5">
						<v-text-field
							v-model="namemg"
							solo
							flat
							dense
							class="px-2"
							placeholder="이름"
						></v-text-field>
					</v-col>
					<v-col cols="1">
						<v-btn
							color="secondary"
							plain
							fab
							x-small
							@click="addMG()"
							v-show="inviteok"
							class="plusbtn"
						>
							+
						</v-btn>
					</v-col>
				</v-row>
				<div class="overflowScroll">
					<v-chip-group column>
						<v-chip
							v-for="(item, index) of emailsmg"
							v-bind:key="index"
							color="#303F9F"
							outlined
							label
							close
							@click:close="deletemg(index)"
							class="mb-1"
							>{{ item }} / {{ namesmg[index] }}</v-chip
						>
					</v-chip-group>
				</div>
			</div>
			<div class="section2">
				<span id="font3" style="font-size: 20px">면접자</span>
				<span id="font2" style="font-size: 15px">(필수) 최대 30명</span>
				<v-row cols="12" class="pt-3">
					<v-col cols="5">
						<v-text-field
							v-model="firemailmj"
							solo
							flat
							dense
							class="px-2"
							placeholder="이메일"
						></v-text-field>
					</v-col>
					<v-col cols="5">
						<v-text-field
							v-model="namemj"
							solo
							flat
							dense
							class="px-2"
							placeholder="이름"
						></v-text-field>
					</v-col>
					<v-col cols="1">
						<v-btn
							color="secondary"
							plain
							fab
							x-small
							@click="addMJ()"
							v-show="inviteok2"
							class="plusbtn"
						>
							+
						</v-btn>
					</v-col>
				</v-row>
				<div class="overflowScroll">
					<v-chip-group column>
						<v-chip
							v-for="(item, index) of emailsmj"
							v-bind:key="index"
							color="#388E3C"
							outlined
							label
							close
							@click:close="deletemj(index)"
							class="mb-1"
							>{{ item }} / {{ namesmj[index] }}</v-chip
						>
					</v-chip-group>
				</div>
			</div>
			<div class="startbtn">
				<button id="font3" @click="startMeeting">시작하기</button>
			</div>
		</div>
	</div>
</template>

<script>
import Loader from '@/components/common/Loader';

import { mapState, mapActions } from 'vuex';
import { authCompany } from '@/api/company.js';
import { sendMeetingCode } from '@/api/member.js';
export default {
	name: 'InvitePage',
	components: {
		Loader,
	},
	computed: {
		...mapState(['auth_code']),
	},
	data: () => {
		return {
			inputsmg: 0,
			inputsmj: 0,
			inviteok: true,
			inviteok2: true,
			firemailmj: '',
			firemailmg: '',
			namemj: '',
			namemg: '',
			emailsmg: [],
			emailsmj: [],
			namesmg: [],
			namesmj: [],
			cname: '',
			inviteCode: '',
		};
	},
	created() {
		this.getCompanyInfo(this.auth_code);
	},
	beforeDestroy() {
		this.setLoaderFalse();
	},
	methods: {
		...mapActions([
			'setMeetingCode',
			'setIsSuperUser',
			'setMeetingName',
			'setLoaderTrue',
			'setLoaderFalse',
		]),
		addMG() {
			const email = this.firemailmg;
			const name = this.namemg;
			if (!!email && !!name) {
				this.emailsmg.push(email);
				this.namesmg.push(name);
				if (this.emailsmg.length >= 2) this.inviteok = false;
				this.firemailmg = '';
				this.namemg = '';
			}
		},
		addMJ() {
			const email = this.firemailmj;
			const name = this.namemj;
			if (!!email && !!name) {
				this.emailsmj.push(email);
				this.namesmj.push(name);
				if (this.emailsmj.length >= 30) this.inviteok2 = false;
				this.firemailmj = '';
				this.namemj = '';
			}
		},
		deletemg(idx) {
			this.emailsmg.splice(idx, 1);
			this.namesmg.splice(idx, 1);
		},
		deletemj(idx) {
			this.emailsmj.splice(idx, 1);
			this.namesmj.splice(idx, 1);
		},
		startMeeting() {
			if (this.emailsmj.length == 0) {
				alert('면접자 이메일을 입력해주세요.');
				return;
			}
			this.postInviteCode();
		},
		async getCompanyInfo(code) {
			const res = await authCompany(code);
			this.setMeetingName(`${res.data[0]} (면접관)`);
			this.cname = res.data[1];
			this.inviteCode = res.data[2];
		},
		async postInviteCode() {
			this.setLoaderTrue();
			await sendMeetingCode({
				cname: this.cname,
				emails: this.emailsmg,
				inviteCode: this.inviteCode,
				names: this.namesmg,
				role: '면접관',
			});
			await sendMeetingCode({
				cname: this.cname,
				emails: this.emailsmj,
				inviteCode: this.inviteCode,
				names: this.namesmj,
				role: '면접자',
			}).catch(err => {
				console.log(err.message);
			});
			this.setMeetingCode(this.inviteCode);
			this.setIsSuperUser(true);
			setTimeout(() => {
				this.setLoaderFalse();
				this.$router.push('/meeting');
			}, 1500);
		},
	},
};
</script>

<style scoped>
.bgcolor {
	position: relative;
	width: 100vw;
	min-height: 100vh;
	background-color: #e0dcdd;
}
.contentwrap {
	width: 600px;
	position: absolute;
	top: calc(50% - 241px);
	left: calc(50% - 300px);
}
.content {
	font-size: 13px;
}
.section1 {
	position: fixed;
	top: 35%;
	left: 15vw;
	right: 53vw;
}
.section2 {
	position: fixed;
	top: 35%;
	left: 53vw;
	right: 15vw;
}
.startbtn:hover {
	background-color: #b71c1c;
	color: white;
	outline: 0;
}
.startbtn:focus {
	outline: none;
}
.startbtn {
	position: fixed;
	bottom: 15vh;
	left: calc(50% - 54px);
	background-color: transparent;
	border: 2px solid #b71c1c;
	border-radius: 30px;
	color: #b71c1c;
	cursor: pointer;
	font-size: 17px;
	padding: 6px 20px;
	transition: all 200ms;
}
.col {
	padding-left: 0px;
	padding-right: 0px;
}
.plusbtn {
	padding-left: 10px;
	padding-right: 0px;
	padding-top: 5px;
	font-size: 50px;
	color: black;
	width: 5px;
}
.overflowScroll {
	height: 200px;
	overflow-y: auto;
}
</style>
