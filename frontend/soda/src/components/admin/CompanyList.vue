<template>
	<div>
		<v-simple-table>
			<tbody>
				<tr @click="modal">
					<td class="text-left">{{ companyList.cname }}</td>
					<td class="text-left">{{ companyList.uname }}</td>
					<td class="text-left">{{ companyList.cjob }}</td>
					<td class="text-left">{{ companyList.cphone }}</td>
					<td class="text-left">{{ companyList.cemail }}</td>
				</tr>
				<v-dialog v-model="dialog" max-width="500">
					<v-card>
						<v-card-text>
							<br />
							<h2 class="text-left">기업명 : {{ companyList.cname }}</h2>
							<h2 style="line-height: 50%"><br /></h2>
							<h2 class="text-left">담당자명 : {{ companyList.uname }}</h2>
							<h2 style="line-height: 50%"><br /></h2>
							<h2 class="text-left">직책 : {{ companyList.cjob }}</h2>
							<h2 style="line-height: 50%"><br /></h2>
							<h2 class="text-left">연락처 : {{ companyList.cphone }}</h2>
							<h2 style="line-height: 50%"><br /></h2>
							<h2 class="text-left">이메일 : {{ companyList.cemail }}</h2>
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
import { deleteCompany } from '@/api/company';
import { postApproveEmail } from '@/api/email';
import { postRejectEmail } from '@/api/email';
export default {
	name: 'CompanyList',
	props: {
		companyList: Object,
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
		delcompany() {
			// console.log(this.companyList.cid);
			const cid = this.companyList.cid;
			deleteCompany(cid);
			this.dialog = false;
			this.$router.go(this.$router.currentRoute);
		},
		approve() {
			const companyInfo = this.companyList;
			postApproveEmail(companyInfo);
			this.dialog = false;
		},
		reject() {
			const companyInfo = this.companyList;
			postRejectEmail(companyInfo);
			this.dialog = false;
		},
	},
};
</script>

<style></style>
