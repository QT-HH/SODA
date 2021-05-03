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
							<v-btn color="green darken-1" text @click="close"> 닫기 </v-btn>
						</v-card-actions>
					</v-card>
				</v-dialog>
			</tbody>
		</v-simple-table>
	</div>
</template>

<script>
// import { approveEmail } from '@/api/company';
import { rejectEmail } from '@/api/company';
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
		approve() {
			const companyInfo = this.company;
			// const email = companyInfo.email;
			console.log(companyInfo);
			// approveEmail(email);
			this.dialog = false;
		},
		reject() {
			const companyInfo = this.company;
			const email = companyInfo.email;
			// console.log(email);
			rejectEmail(email);
			this.dialog = false;
		},
	},
};
</script>

<style scoped>
.table {
	width: 20%;
}
</style>
