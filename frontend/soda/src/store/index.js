import Vue from 'vue';
import Vuex from 'vuex';
import mutations from './mutations';
import actions from './actions';

Vue.use(Vuex);

export default new Vuex.Store({
	state: {
		meetingOn: false,
		isSuperUser: false,
		sttOn: true,
		recvSTT: true,
		sendSTT: false,
		showSTT: true,
		testMeetingId: String,
		meetingCode: String,
		meetingName: String,
		auth_code: String,
	},
	mutations,
	actions,
});
