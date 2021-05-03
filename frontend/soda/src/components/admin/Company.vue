<template>
	<div>
		<v-simple-table>
			<tbody>
				<tr @click="modal">
					<td class="text-center table" style="padding: 0px">
						{{ company.cname }}
					</td>
					<td class="text-center table" style="padding: 0px">
						{{ company.uname }}
					</td>
					<td class="text-center table" style="padding: 0px">
						{{ company.role }}
					</td>
					<td class="text-center table" style="padding: 0px">
						{{ company.phone }}
					</td>
					<td class="text-center table" style="padding: 0px">
						{{ company.email }}
					</td>
				</tr>
				<v-dialog v-model="dialog" max-width="500">
					<v-card>
						<v-card-text>
							<br />
							<h2 class="text-left">기업명 : {{ company.cname }}</h2>
							<h2 style="line-height: 50%"><br /></h2>
							<h2 class="text-left">담당자명 : {{ company.uname }}</h2>
							<h2 style="line-height: 50%"><br /></h2>
							<h2 class="text-left">직책 : {{ company.role }}</h2>
							<h2 style="line-height: 50%"><br /></h2>
							<h2 class="text-left">연락처 : {{ company.phone }}</h2>
							<h2 style="line-height: 50%"><br /></h2>
							<h2 class="text-left">이메일 : {{ company.email }}</h2>
						</v-card-text>
						<v-card-actions>
							<v-spacer></v-spacer>
							<v-btn color="error" text @click="approve"> 등록 승인 </v-btn>
							<v-btn color="error" text @click="reject"> 등록 거부 </v-btn>
							<v-btn color="error" text @click="delcompany">
								기업정보삭제
							</v-btn>
							<v-btn color="green darken-1" text @click="close"> 닫기 </v-btn>
						</v-card-actions>
					</v-card>
				</v-dialog>
			</tbody>
		</v-simple-table>
	</div>
</template>

<script>
// import { deleteCompany } from '@/api/company';
// import { postApproveEmail } from '@/api/email';
// import { postRejectEmail } from '@/api/email';
export default {
	name: 'company',
	props: {
		company: Object,
	},
	data() {
		return {
			dialog: false,
		};
	},
	methods: {
		modal() {
			this.dialog = true;
		},
		close() {
			this.dialog = false;
		},
		// delcompany() {
		// 	// console.log(this.company.cid);
		// 	const cid = this.company.cid;
		// 	deleteCompany(cid);
		// 	this.dialog = false;
		// 	this.$router.go(this.$router.currentRoute);
		// },
		// approve() {
		// 	const companyInfo = this.company;
		// 	postApproveEmail(companyInfo);
		// 	this.dialog = false;
		// },
		// reject() {
		// 	const companyInfo = this.company;
		// 	postRejectEmail(companyInfo);
		// 	this.dialog = false;
		// },
	},
};
</script>

<style scoped>
.table {
	width: 20%;
}
</style>
