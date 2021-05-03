<template>
	<tr>
		<td class="text-center">{{ Room.sessionid }}</td>
		<td class="text-center">{{ Room.owner }}</td>
		<td class="text-center">{{ Room.participants }}</td>
	</tr>
</template>

<script>
export default {
	name: 'MeetingRoom',
	props: {
		Room: Object,
	},
};
</script>

<style scoped>
.table {
	width: 20%;
}
</style>
