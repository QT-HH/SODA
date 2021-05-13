import Vue from 'vue';
import Vuex from 'vuex';
import mutations from './mutations';
import actions from './actions';

Vue.use(Vuex);

export default new Vuex.Store({
	state: {
		meetingOn: false,
		sttOn: true,
		isSuperUser: false,
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
