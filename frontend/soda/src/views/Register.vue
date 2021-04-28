<template>
	<v-row justify="center">
		<v-col cols="12" sm="10" md="8" lg="6" style="margin: auto">
			<div>
				<div>
					<v-text-field
						v-model="company"
						label="기업명"
						required
					></v-text-field>
					<v-text-field v-model="name" label="담당자명" required></v-text-field>
					<v-text-field v-model="roll" label="직책" required></v-text-field>
					<v-text-field v-model="phone" label="연락처" required></v-text-field>
					<v-text-field v-model="email" label="이메일" required></v-text-field>
				</div>
				<div>
					<v-btn color="primary" text @click="submit"> 등록하기 </v-btn>
				</div>
			</div>
		</v-col>
	</v-row>
</template>

<script>
export default {
	name: 'Register',
	components: {},
	data: () => ({
		company: null,
		name: null,
		roll: null,
		phone: null,
		email: null,
	}),
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
				this.$router.push('/certify');
			}
		},
	},
};
</script>

<style></style>
