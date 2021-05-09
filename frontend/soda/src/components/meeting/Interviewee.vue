<template>
	<p>
		{{ interviewee.u_name }}
		<v-chip-group mandatory>
			<v-chip
				color="#dddddd"
				outlined
				small
				@click="changeStatus(interviewee.mm_id, 'PLAN')"
			>
				예정
			</v-chip>
			<v-chip
				color="#dddddd"
				outlined
				small
				@click="changeStatus(interviewee.mm_id, 'PROGRESS')"
			>
				진행
			</v-chip>
			<v-chip
				color="#dddddd"
				outlined
				small
				@click="changeStatus(interviewee.mm_id, 'DONE')"
			>
				완료
			</v-chip>
		</v-chip-group>
	</p>
</template>

<script>
import { editStatus } from '@/api/member.js';
export default {
	name: 'Interviewee',
	props: {
		interviewee: Object,
	},
	methods: {
		async changeStatus(mm_id, status) {
			await editStatus(mm_id, status).catch(err => {
				console.log(err);
			});
		},
	},
};
</script>

<style></style>
