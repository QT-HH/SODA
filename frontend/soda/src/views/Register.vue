<template>
	<div>
		<section class="bgcolor"></section>
		<div class="contentwrap">
			<h1 class="pb-3" id="font3">기업 등록</h1>
			<p class="content" id="font2">* 면접 개설 권한을 받기 위한 절차입니다.</p>
			<v-text-field
				v-model="cName"
				solo
				flat
				rounded
				label="기업명"
				required
				color="#388E3C"
			></v-text-field>
			<v-text-field
				v-model="uName"
				solo
				flat
				rounded
				label="담당자명"
				required
				color="#388E3C"
			></v-text-field>
			<v-text-field
				v-model="role"
				solo
				flat
				rounded
				label="직책"
				required
				color="#388E3C"
			></v-text-field>
			<v-text-field
				v-model="phone"
				solo
				flat
				rounded
				label="연락처"
				required
				color="#388E3C"
			></v-text-field>
			<v-text-field
				v-model="email"
				solo
				flat
				rounded
				label="이메일"
				required
				color="#388E3C"
			></v-text-field>
			<button id="font3" @click="submit">등록</button>
			<v-dialog v-model="dialog" max-width="290">
				<v-card>
					<v-card-title class="headline"> 기업등록 완료! </v-card-title>
					<v-card-text>
						승인 후 이메일로 기업코드를 보내드리겠습니다. (약 2일 소요)
					</v-card-text>
					<v-card-actions>
						<v-spacer></v-spacer>
						<v-btn color="green darken-1" text @click="close"> 확인 </v-btn>
					</v-card-actions>
				</v-card>
			</v-dialog>
		</div>
	</div>
</template>

<script>
import { newCompany } from '@/api/company';
export default {
	name: 'Register',
	components: {},
	data() {
		return {
			dialog: false,
			cName: null,
			email: null,
			phone: null,
			role: null,
			uName: null,
		};
	},
	methods: {
		async submit() {
			if (this.cName === null) {
				alert('기업명을 입력해주세요.');
			} else if (this.uName === null) {
				alert('담당자명을 입력해주세요.');
			} else if (this.role === null) {
				alert('직책을 입력해주세요.');
			} else if (this.phone === null) {
				alert('연락처를 입력해주세요.');
			} else if (this.email === null) {
				alert('이메일을 입력해주세요.');
			} else {
				this.dialog = true;
				const cName = this.cName;
				const uName = this.uName;
				const role = this.role;
				const phone = this.phone;
				const email = this.email;
				await newCompany(cName, email, phone, role, uName);
			}
		},
		close() {
			this.dialog = false;
			this.$router.push('/');
		},
	},
};
</script>

<style scoped>
button {
	background-color: transparent;
	border: 2px solid #b71c1c;
	border-radius: 30px;
	color: #b71c1c;
	cursor: pointer;
	font-size: 17px;
	padding: 6px 20px;
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
.bgcolor {
	position: relative;
	width: 100vw;
	height: 100vh;
	background-color: #e0dcdd;
}
.contentwrap {
	width: 500px;
	position: fixed;
	top: calc(50% - 250px);
	left: calc(50% - 250px);
}
.content {
	font-size: 13px;
}
</style>
