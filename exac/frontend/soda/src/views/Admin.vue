<template>
	<div>
		<div style="margin: 50px"></div>
		<CompanyList></CompanyList>
		<MeetingRoomList></MeetingRoomList>
	</div>
</template>

<script>
import CompanyList from '@/components/admin/CompanyList.vue';
import MeetingRoomList from '@/components/admin/MeetingRoomList.vue';

export default {
	name: 'Admin',
	components: {
		CompanyList,
		MeetingRoomList,
	},
};
</script>

<style scoped>
.table {
	width: 20%;
}
</style>
