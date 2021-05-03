<template>
	<div class="size">
		<v-container
			fluid
			class="backcolor sticky-box"
			style="border: 2px solid; color: #4527a0; height: 580px"
		>
			<p style="color: black">면접자리스트</p>
			<v-list>
				<v-list-item style="padding: 0px; text-align: center">
					<div class="text-center">
						<p style="margin: 3px">youlee602@hanmail.net</p>
						<v-chip-group mandatory>
							<v-chip color="indigo darken-3" outlined small> 예정 </v-chip>
							<v-chip color="indigo darken-3" outlined small> 진행 </v-chip>
							<v-chip color="indigo darken-3" outlined small> 완료 </v-chip>
						</v-chip-group>
					</div>
				</v-list-item>
			</v-list>
		</v-container>
	</div>
</template>

<script>
export default {
	name: 'MeetingUser',
};
</script>

<style>
.size {
	width: 200px;
}
.backcolor {
	background-color: white;
	border-radius: 10px;
}
.sticky-box {
	position: sticky;
	top: 0px;
}
</style>
