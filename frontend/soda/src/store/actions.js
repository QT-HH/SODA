import { attendMeeting } from '@/api/meeting';

export default {
	async checkIsMG({ commit }, data) {
		await attendMeeting(data.email, data.inviteCode)
			.then(res => {
				if (res.data === '면접관') {
					commit('CHECK_IS_MG');
				}
			})
			.catch(err => {
				console.log(err);
			});
	},
};
