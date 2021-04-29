<template>
	<v-row justify="center">
		<v-col cols="12" sm="10" md="8" lg="6" style="margin: auto">
			<div>
				<v-text-field v-model="cname" label="기업명" required></v-text-field>
				<v-text-field v-model="uname" label="담당자명" required></v-text-field>
				<v-text-field v-model="cjob" label="직책" required></v-text-field>
				<v-text-field v-model="cphone" label="연락처" required></v-text-field>
				<v-text-field v-model="cemail" label="이메일" required></v-text-field>
			</div>
			<v-btn dark @click="submit"> 등록하기 </v-btn>
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
		</v-col>
	</v-row>
</template>

<script>
import { postCompanyInfo } from '@/api/company';
export default {
	name: 'Register',
	components: {},
	data() {
		return {
			dialog: false,
			cname: null,
			uname: null,
			cjob: null,
			cphone: null,
			cemail: null,
		};
	},
	methods: {
		submit() {
			if (this.cname === null) {
				alert('기업명을 입력해주세요.');
			} else if (this.uname === null) {
				alert('담당자명을 입력해주세요.');
			} else if (this.cjob === null) {
				alert('직책을 입력해주세요.');
			} else if (this.cphone === null) {
				alert('연락처를 입력해주세요.');
			} else if (this.cemail === null) {
				alert('이메일을 입력해주세요.');
			} else {
				this.dialog = true;
				const info = {
					cname: this.cname,
					uname: this.uname,
					cjob: this.cjob,
					cphone: this.cphone,
					cemail: this.cemail,
				};
				postCompanyInfo(info);
			}
		},
		close() {
			this.dialog = false;
			this.$router.push('/');
		},
	},
};
</script>

<style></style>
