export default {
	MEETING_ON_OFF(state) {
		state.meetingOn = !state.meetingOn;
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
	SET_AUTH_CODE(state, value) {
		state.auth_code = value;
	},
	SET_TEST_MEETING_ID(state, value) {
		state.testMeetingId = value;
	},
	STT_ON_OFF(state, value) {
		state.sttOn = value;
	},
	STT_SHOW(state) {
		state.showSTT = !state.showSTT;
	},
	STT_SEND(state) {
		state.sendSTT = !state.sendSTT;
	},
	STT_RECV(state) {
		state.recvSTT = !state.recvSTT;
	},
};
