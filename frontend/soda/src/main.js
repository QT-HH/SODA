import Vue from 'vue';
import App from './App.vue';
import router from './router';
import store from './store';
import Vuetify from 'vuetify';
import 'vuetify/dist/vuetify.min.css';
Vue.use(Vuetify);
Vue.config.productionTip = false;

new Vue({
	vuetify: new Vuetify({
		icons: {
			iconfont: 'mdiSvg', // 'mdi' || 'mdiSvg' || 'md' || 'fa' || 'fa4' || 'faSvg'
		},
	}),
	router,
	store,
	render: h => h(App),
}).$mount('#app');
