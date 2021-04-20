import Vue from "vue";
import Vuex from "vuex";

Vue.use(Vuex);

export default new Vuex.Store({
  state: {
    meeting_url: null,
    sessions: [],
    content: []
  },
  mutations: {
    // save meeting url
    savemeetingurl(state, value) {
      state.meeting_url = value;
    },
    // save sessions in the meeting
    savesessions(state, value) {
      state.sessions = value;
    },
    // save all the contents for the meeting
    savecontent(state, value) {
      state.content = value;
    }
  },
  actions: {},
  modules: {}
});
