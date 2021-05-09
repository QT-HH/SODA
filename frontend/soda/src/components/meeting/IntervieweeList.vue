<template>
	<div>
		<div :class="sideBar" ref="pageWrapper">
			<div
				id="font2"
				class="btn btn-sm btn-dark showside"
				href="#"
				@click="showSidebar"
			>
				<p class="ma-0">면접자 리스트</p>
			</div>
			<nav id="sidebar" class="sidebar-wrapper">
				<div class="sidebar-content">
					<div class="sidebar-brand">
						<p>면접자 리스트</p>
						<div id="close-sidebar" @click="closeSidebar">
							<i class="fas fa-times"></i>
						</div>
					</div>
					<v-list class="listBox">
						<v-list-item style="padding: 0px">
							<div class="user">
								<Interviewee
									v-for="(item, idx) in intervieweeList"
									:key="idx"
									:interviewee="item"
									class="listName"
								></Interviewee>
							</div>
						</v-list-item>
					</v-list>
				</div>
			</nav>
		</div>
	</div>
</template>

<script>
import { intervieweeOfMeeting } from '@/api/meeting.js';
import Interviewee from '@/components/meeting/Interviewee.vue';

export default {
	name: 'IntervieweeList',
	components: {
		Interviewee,
	},
	data() {
		return {
			intervieweeList: Array,
			sideBar: 'page-wrapper sideBarTheme',
		};
	},
	mounted() {
		const roomid = this.$store.state.meetingCode;
		intervieweeOfMeeting(roomid)
			.then(res => {
				this.intervieweeList = res.data;
			})
			.catch(err => {
				console.log(err);
			});
	},
	methods: {
		closeSidebar() {
			this.sideBar = 'page-wrapper sideBarTheme';
		},
		showSidebar() {
			this.sideBar = 'page-wrapper sideBarTheme toggled';
		},
	},
};
</script>

<style>
.page-wrapper .sidebar-wrapper,
/* .sidebar-wrapper .sidebar-brand > a, */
/* .page-wrapper .page-content, */
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
}
.page-wrapper.toggled .showside {
	left: -50px;
}
.sidebar-wrapper {
	width: 260px;
	height: 100%;
	max-height: 100%;
	position: fixed;
	top: 0;
	left: -300px;
	float: left;
	z-index: 999;
}
.sidebar-wrapper .sidebar-brand {
	padding: 10px 20px;
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
}
.listName {
	margin: 2px;
	color: #dddddd;
}
</style>
