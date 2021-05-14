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
		sendSTT: false,
		showSTT: true,
		newChat: false,
		isChat: false,
		testMeetingId: String,
		meetingCode: String,
		meetingName: String,
		auth_code: String,
		loader: {
			load: false,
		},
	},
	mutations,
	actions,
	getters: {
		getLoader(state) {
			return state.loader.load;
		},
	},
});
