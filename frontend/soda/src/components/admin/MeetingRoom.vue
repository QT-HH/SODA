<template>
	<tr>
		<td class="text-left">{{ Room.sessionid }}</td>
		<td class="text-left">{{ Room.owner }}</td>
		<td class="text-left">{{ Room.participants }}</td>
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

<style></style>
