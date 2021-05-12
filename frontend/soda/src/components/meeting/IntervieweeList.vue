<template>
	<div :class="sideBar" ref="pageWrapper">
		<div
			id="font2"
			class="btn btn-sm btn-dark showside d-flex align-content-center flex-wrap"
			href="#"
			@click="showSidebar"
		>
			<p class="ma-0" id="font2">면접자 리스트</p>
		</div>
		<nav id="sidebar" class="sidebar-wrapper">
			<div class="sidebar-content sidebar-brand">
				<p class="mb-0" id="font3">면접자 리스트</p>
				<div id="close-sidebar" @click="closeSidebar">
					<i class="fas fa-times"></i>
				</div>
			</div>
			<div class="listBox">
				<Interviewee
					v-for="(item, idx) in intervieweeList"
					:key="idx"
					:interviewee="item"
					class="listName"
					@dltUser="dltUser"
				></Interviewee>
			</div>
			<div class="d-flex justify-center">
				<button class="dltBtn" id="font3" @click="dltIntervieweeList">
					리스트 전체 삭제
				</button>
			</div>
		</nav>
	</div>
</template>

<script>
import { mapState } from 'vuex';
import { intervieweeOfMeeting } from '@/api/meeting.js';
import { deleteAllInterviewee } from '@/api/meeting.js';
import Interviewee from '@/components/meeting/Interviewee.vue';

export default {
	name: 'IntervieweeList',
	components: {
		Interviewee,
	},
	computed: {
		...mapState(['meetingCode']),
	},
	data() {
		return {
			intervieweeList: Array,
			sideBar: 'page-wrapper sideBarTheme',
		};
	},
	mounted() {
		const roomid = this.meetingCode;
		intervieweeOfMeeting(roomid).then(res => {
			this.intervieweeList = res.data;
		});
	},
	methods: {
		closeSidebar() {
			this.sideBar = 'page-wrapper sideBarTheme';
		},
		showSidebar() {
			this.sideBar = 'page-wrapper sideBarTheme toggled';
		},
		async dltIntervieweeList() {
			await deleteAllInterviewee(this.meetingCode);
			this.intervieweeList = [];
		},
		dltUser(deleteId) {
			const len = this.intervieweeList.length;
			for (let i = 0; i < len; i++) {
				if (this.intervieweeList[i].u_id == deleteId) {
					this.intervieweeList.splice(i, 1);
					break;
				}
			}
		},
	},
};
</script>

<style scoped>
.page-wrapper .sidebar-wrapper,
#show-sidebar,
#close-sidebar {
	-webkit-transition: all 0.3s ease;
	-moz-transition: all 0.3s ease;
	-ms-transition: all 0.3s ease;
	-o-transition: all 0.3s ease;
	transition: all 0.3s ease;
}
.page-wrapper.toggled .sidebar-wrapper {
	left: 0px;
}
.showside {
	position: fixed;
	left: 0;
	top: 10px;
	border-radius: 0 4px 4px 0px;
	width: 50px;
	height: 50px;
	transition-delay: 0.3s;
	background-color: #31353d;
	color: white;
	font-size: 13px;
	z-index: 999;
	justify-content: center;
	cursor: pointer;
}
.page-wrapper.toggled .showside {
	left: -50px;
}
.sidebar-wrapper {
	width: 265px;
	height: 85vh; /* 사이드바 높이 설정 */
	max-height: 100%;
	position: fixed;
	top: 0;
	left: -300px;
	float: left;
	z-index: 999;
	background-color: transparent;
}
.sidebar-wrapper .sidebar-brand {
	padding: 10px 20px 20px 20px;
	display: flex;
	align-items: center;
}
.sidebar-wrapper .sidebar-brand > p {
	text-transform: uppercase;
	font-weight: bold;
	flex-grow: 1;
	color: white;
}
.sidebar-wrapper .sidebar-brand #close-sidebar {
	cursor: pointer;
	font-size: 20px;
}

.sideBarTheme .sidebar-wrapper {
	background: #31353d;
}

.page-wrapper.sideBarTheme.toggled #close-sidebar {
	color: #bdbdbd;
}

.page-wrapper.sideBarTheme.toggled #close-sidebar:hover {
	color: #ffffff;
}
.listBox {
	background-color: transparent;
	text-align: start;
	padding-left: 15px;
	overflow-y: auto;
	height: 70vh;
}
.listName {
	margin: 2px;
	color: #dddddd;
}
.dltBtn {
	color: #bdbdbd;
	position: absolute;
	bottom: 10px;
}
.dltBtn:hover {
	color: red;
}
</style>
