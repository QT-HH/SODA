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
	setIsSuperUser({ commit }) {
		commit('SET_IS_SUPER_USER');
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
