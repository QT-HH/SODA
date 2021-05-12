<template>
	<div>
		<p>{{ interviewee.u_name }} ({{ interviewee.u_email }})</p>
		<div class="pb-5 interviewItem">
			<button
				@click="changeStatus(interviewee.mm_id, 'PLAN')"
				:class="plan"
				id="font3"
			>
				예정
			</button>
			<button
				@click="changeStatus(interviewee.mm_id, 'PROGRESS')"
				:class="progress"
				id="font3"
			>
				진행
			</button>
			<button
				@click="changeStatus(interviewee.mm_id, 'DONE')"
				:class="done"
				id="font3"
			>
				완료
			</button>
			<button
				@click="dltInterviewee(interviewee.u_id)"
				class="dltBtn"
				id="font3"
			>
				삭제
			</button>
		</div>
		<hr class="devider" />
	</div>
</template>

<script>
import { editStatus } from '@/api/member.js';
import { deleteInterviewee } from '@/api/meeting.js';
export default {
	name: 'Interviewee',
	props: {
		interviewee: Object,
	},
	computed: {
		plan() {
			return {
				plan: this.interviewee.status === 'PLAN',
				defaultBtn: this.interviewee.status !== 'PLAN',
			};
		},
		progress() {
			return {
				progress: this.interviewee.status === 'PROGRESS',
				defaultBtn: this.interviewee.status !== 'PROGRESS',
			};
		},
		done() {
			return {
				done: this.interviewee.status === 'DONE',
				defaultBtn: this.interviewee.status !== 'DONE',
			};
		},
	},
	methods: {
		async changeStatus(mm_id, status) {
			await editStatus(mm_id, status).then(() => {
				this.interviewee.status = status;
			});
		},
		async dltInterviewee(u_id) {
			await deleteInterviewee(u_id)
				.then(() => {
					console.log('삭제');
				})
				.catch(err => {
					console.log(err.message);
				});
			this.$emit('dltUser', u_id);
		},
	},
};
</script>

<style scoped>
.defaultBtn:hover {
	background-color: #bbbbbb;
	color: black;
	outline: 0;
}
.defaultBtn:focus {
	outline: none;
}
.defaultBtn {
	background-color: white;
	color: black;
	cursor: pointer;
	font-size: 15px;
	padding: 6px 11px;
	transition: all 200ms;
	margin-right: 5px;
}
.done:hover {
	background-color: #bbbbbb;
	color: black;
	outline: 0;
}
.done:focus {
	outline: none;
}
.done {
	background-color: #616161;
	color: white;
	cursor: pointer;
	font-size: 15px;
	padding: 6px 11px;
	transition: all 200ms;
	margin-right: 5px;
}
.plan:hover {
	background-color: #bbbbbb;
	color: black;
	outline: 0;
}
.plan:focus {
	outline: none;
}
.plan {
	background-color: #f9a825;
	color: white;
	cursor: pointer;
	font-size: 15px;
	padding: 6px 11px;
	transition: all 200ms;
	margin-right: 5px;
}
.progress:hover {
	background-color: #bbbbbb;
	color: black;
	outline: 0;
}
.progress:focus {
	outline: none;
}
.progress {
	background-color: #388e3c;
	color: white;
	cursor: pointer;
	font-size: 15px;
	padding: 6px 11px;
	transition: all 200ms;
	margin-right: 5px;
}
.devider {
	border-color: #dddddd;
	width: 220px;
	margin-bottom: 10px;
}
.dltBtn:hover {
	background-color: red;
	color: white;
	outline: 0;
}
.dltBtn:focus {
	outline: none;
}
.dltBtn {
	background-color: white;
	color: black;
	cursor: pointer;
	font-size: 15px;
	padding: 6px 11px;
	transition: all 200ms;
	margin-right: 5px;
}
</style>
