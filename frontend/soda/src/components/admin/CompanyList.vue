<template>
	<div>
		<v-simple-table dense>
			<thead>
				<tr>
					<th class="text-center table" style="padding: 0px">기업명</th>
					<th class="text-center table" style="padding: 0px">담당자명</th>
					<th class="text-center table" style="padding: 0px">직책</th>
					<th class="text-center table" style="padding: 0px">연락처</th>
					<th class="text-center table" style="padding: 0px">이메일</th>
				</tr>
			</thead>
		</v-simple-table>
		<Company
			v-for="(item, index) in companyList"
			:company="item"
			:key="index"
		></Company>
	</div>
</template>

<script>
import Company from '@/components/admin/Company.vue';
import { companyList } from '@/api/company';

export default {
	name: 'CompanyList',
	components: {
		Company,
	},
	data() {
		return {
			companyList: Array,
			meetingRoomList: false,
		};
	},
	async created() {
		const company = await companyList();
		// console.log(company);
		this.companyList = company.data;
		console.log(this.companyList);
	},
};
</script>

<style scoped>
.table {
	width: 20%;
}
</style>
