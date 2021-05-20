export default {
	MEETING_ON_OFF(state, value) {
		state.meetingOn = value;
	},
	SET_MEETING_CODE(state, value) {
		state.meetingCode = value;
	},
	SET_MEETING_NAME(state, value) {
		state.meetingName = value;
	},
	SET_IS_SUPER_USER(state, value) {
		state.isSuperUser = value;
	},
	SET_IS_HOST(state, value) {
		state.isHost = value;
	},
	SET_AUTH_CODE(state, value) {
		state.auth_code = value;
	},
	SET_TEST_MEETING_ID(state, value) {
		state.testMeetingId = value;
	},
	STT_ON_OFF(state, value) {
		state.sttOn = value;
	},
	SET_LOADER_TRUE(state) {
		state.loader.load = true;
	},
	SET_LOADER_FALSE(state) {
		state.loader.load = false;
	},
	STT_SHOW(state, value) {
		state.showSTT = value;
	},
	STT_SEND(state) {
		state.sendSTT = !state.sendSTT;
	},
	SET_NEW_CHAT(state, value) {
		state.newChat = value;
	},
	SET_IS_CHAT(state) {
		state.isChat = !state.isChat;
	},
};
