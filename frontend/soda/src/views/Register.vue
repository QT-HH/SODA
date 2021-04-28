<template>
	<v-row justify="center">
		<v-col cols="12" sm="10" md="8" lg="6" style="margin: auto">
			<div>
				<v-text-field v-model="company" label="기업명" required></v-text-field>
				<v-text-field v-model="name" label="담당자명" required></v-text-field>
				<v-text-field v-model="roll" label="직책" required></v-text-field>
				<v-text-field v-model="phone" label="연락처" required></v-text-field>
				<v-text-field v-model="email" label="이메일" required></v-text-field>
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
export default {
	name: 'Register',
	components: {},
	data() {
		return {
			dialog: false,
			company: null,
			name: null,
			roll: null,
			phone: null,
			email: null,
		};
	},
	methods: {
		submit() {
			if (this.company === null) {
				alert('기업명을 입력해주세요.');
			} else if (this.name === null) {
				alert('담당자명을 입력해주세요.');
			} else if (this.roll === null) {
				alert('직책을 입력해주세요.');
			} else if (this.phone === null) {
				alert('연락처를 입력해주세요.');
			} else if (this.email === null) {
				alert('이메일을 입력해주세요.');
			} else {
				console.log(this.company);
				console.log(this.name);
				console.log(this.roll);
				console.log(this.phone);
				console.log(this.email);
				this.dialog = true;
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
