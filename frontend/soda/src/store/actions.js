export default {
	meetingOnOff({ commit }) {
		commit('MEETING_ON_OFF');
	},
	setMeetingCode({ commit }, value) {
		commit('SET_MEETING_CODE', value);
	},
	setMeetingName({ commit }, value) {
		commit('SET_MEETING_NAME', value);
	},
	setIsSuperUser({ commit }, value) {
		commit('SET_IS_SUPER_USER', value);
	},
	setAuthCode({ commit }, value) {
		commit('SET_AUTH_CODE', value);
	},
	setTestMeetingId({ commit }, value) {
		commit('SET_TEST_MEETING_ID', value);
	},
	STTOnOff({ commit }, value) {
		commit('STT_ON_OFF', value);
	},
};
