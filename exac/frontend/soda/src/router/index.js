import Vue from 'vue';
import VueRouter from 'vue-router';
import Home from '../views/Home.vue';
import Register from '../views/Register.vue';
import Certify from '../views/Certify.vue';
import Admin from '../views/Admin.vue';
import MeetingUser from '../components/meeting/MeetingUser.vue';
import Guide from '../views/Guide.vue';

Vue.use(VueRouter);

const routes = [
	{
		path: '/',
		name: 'Home',
		component: Home,
	},
	{
		path: '/register',
		name: 'Register',
		component: Register,
	},
	{
		path: '/certify',
		name: 'Certify',
		component: Certify,
	},
	{
		path: '/aaddmmiinn',
		name: 'Admin',
		component: Admin,
	},
	{
		path: '/meetinguser',
		name: 'MeetingUser',
		component: MeetingUser,
	},
	{
		path: '/serviceguide',
		name: 'Guide',
		component: Guide,
	},
	{
		path: '/meeting',
		name: 'Meeting',
		component: () => import('../views/Meeting.vue'),
	},
	{
		path: '/meetingtest',
		name: 'MeetingTest',
		component: () => import('../views/MeetingTest.vue'),
	},
	{
		path: '/attend',
		name: 'Attend',
		component: () => import('../views/Attend.vue'),
	},
	{
		path: '/invite',
		name: 'Invite',
		component: () => import('../views/Invite.vue'),
	},
];

const router = new VueRouter({
	mode: 'history',
	base: process.env.BASE_URL,
	routes,
	scrollBehavior() {
		return { x: 0, y: 0 };
	},
});

export default router;
