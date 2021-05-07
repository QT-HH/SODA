import Vue from 'vue';
import Vuex from 'vuex';
import mutations from './mutations';
import actions from './actions';

// import meetingStore from '@/store/modules/meetingStore';

Vue.use(Vuex);

export default new Vuex.Store({
	state: {
		meetingOn: false,
		meetingCode: '',
		auth_code: '',
		sttOn: false,
	},
	mutations,
	actions,
	// modules: {
	// 	meetingStore,
	// },
});
