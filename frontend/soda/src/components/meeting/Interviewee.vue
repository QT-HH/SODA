<template>
	<p>
		{{ interviewee.u_name }}
		<v-chip-group mandatory>
			<v-chip
				outlined
				small
				@click="changeStatus(interviewee.mm_id, 'PLAN')"
				:class="plan"
			>
				예정
			</v-chip>
			<v-chip
				outlined
				small
				@click="changeStatus(interviewee.mm_id, 'PROGRESS')"
				:class="progress"
			>
				진행
			</v-chip>
			<v-chip
				outlined
				small
				@click="changeStatus(interviewee.mm_id, 'DONE')"
				:class="done"
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
	computed: {
		plan() {
			return {
				PLAN: this.interviewee.status === 'PLAN',
			};
		},
		progress() {
			return {
				PROGRESS: this.interviewee.status === 'PROGRESS',
			};
		},
		done() {
			return {
				DONE: this.interviewee.status === 'DONE',
			};
		},
	},
	methods: {
		async changeStatus(mm_id, status) {
			await editStatus(mm_id, status)
				.then(() => {
					this.interviewee.status = status;
				})
				.catch(err => {
					console.log(err);
				});
		},
	},
};
</script>

<style>
.DONE {
	color: red;
}
</style>
