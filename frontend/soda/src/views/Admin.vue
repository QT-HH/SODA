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
		<CompanyList
			v-for="(item, index) in companyList"
			:companyList="item"
			:key="index"
		></CompanyList>
		<v-btn depressed @click="meetingRoomList = !meetingRoomList"
			>방 정보 보기</v-btn
		>
		<MeetingRoomList v-if="meetingRoomList"></MeetingRoomList>
	</div>
</template>

<script>
import CompanyList from '@/components/admin/CompanyList.vue';
import MeetingRoomList from '@/components/admin/MeetingRoomList.vue';
import { getCompanyList } from '@/api/company';
export default {
	name: 'Admin',
	components: {
		CompanyList,
		MeetingRoomList,
	},
	data() {
		return {
			companyList: Array,
			meetingRoomList: false,
		};
	},
	async created() {
		const company = await getCompanyList();
		console.log(company);
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
