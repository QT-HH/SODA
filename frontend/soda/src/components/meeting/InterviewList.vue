<template>
	<div>
		<v-list class="listBox">
			<InterviewItem></InterviewItem>
			<v-list-item style="padding: 0px">
				<div class="user">
					<div
						v-for="(item, idx) in participants"
						:key="idx"
						:user="item"
						class="listName"
					>
						{{ item.u_name }}
						<v-btn-toggle mandatory dense dark color="white">
							<v-btn @click="changeStatus(item.mm_id, 'PLAN')">예정</v-btn>
							<v-btn @click="changeStatus(item.mm_id, 'PROGRESS')">진행</v-btn>
							<v-btn @click="changeStatus(item.mm_id, 'DONE')">완료</v-btn>
						</v-btn-toggle>
					</div>
				</div>
			</v-list-item>
		</v-list>
	</div>
</template>

<script>
export default {};
</script>

<style></style>
