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
	SET_IS_SUPER_USER(state) {
		state.isSuperUser = !state.isSuperUser;
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
};
