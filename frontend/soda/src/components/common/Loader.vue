<template>
	<div class="text-center">
		<v-dialog
			v-model="$store.state.loader.load"
			hide-overlay
			persistent
			width="300"
		>
			<v-card color="#5C6BC0" dark>
				<v-card-text style="text-align: center">
					처리중입니다.
					<v-progress-linear
						indeterminate
						color="white"
						class="mb-0"
					></v-progress-linear>
				</v-card-text>
			</v-card>
		</v-dialog>
	</div>
</template>

<script>
import { mapGetters } from 'vuex';
export default {
	methods: {
		aaa() {
			this.$store.commit('SET_Loader');
			setTimeout(() => (this.$store.state.loader.load = false), 4000);
		},
	},
	computed: {
		...mapGetters(['getLoader']),
	},
	data() {
		return {
			dialog: false,
		};
	},

	watch: {
		dialog(val) {
			if (!val) return;

			setTimeout(() => (this.dialog = false), 4000);
		},
	},
};
</script>

<style></style>
