<template>
	<div>
		<v-simple-table>
			<template>
				<thead>
					<tr>
						<th class="text-center">기업명</th>
						<th class="text-center">담당자명</th>
						<th class="text-center">직책</th>
						<th class="text-center">연락처</th>
						<th class="text-center">이메일</th>
					</tr>
				</thead>
			</template>
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

<style></style>
