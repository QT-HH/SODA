import Vue from 'vue';
import Vuex from 'vuex';

import meetingStore from '@/store/modules/meetingStore';

Vue.use(Vuex);

export default new Vuex.Store({
	state: {
		meetingOn: false,
	},
	mutations: {},
	actions: {},
	modules: {
		meetingStore,
	},
});
