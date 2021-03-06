export default {
	meetingOnOff({ commit }, value) {
		commit('MEETING_ON_OFF', value);
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
	setIsHost({ commit }, value) {
		commit('SET_IS_HOST', value);
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
	setLoaderTrue({ commit }) {
		commit('SET_LOADER_TRUE');
	},
	setLoaderFalse({ commit }) {
		commit('SET_LOADER_FALSE');
	},
	STTshow({ commit }, value) {
		commit('STT_SHOW', value);
	},
	STTsend({ commit }) {
		commit('STT_SEND');
	},
	setNewChat({ commit }, value) {
		commit('SET_NEW_CHAT', value);
	},
	setIsChat({ commit }) {
		commit('SET_IS_CHAT');
	},
};
