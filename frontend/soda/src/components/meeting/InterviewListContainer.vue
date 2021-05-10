<template>
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
							<div
								v-for="(item, idx) in participants"
								:key="idx"
								:user="item"
								class="listName"
							>
								{{ item.u_name }}
								<v-btn-toggle mandatory dense dark color="white">
									<v-btn @click="changeStatus(item.mm_id, 'PLAN')">예정</v-btn>
									<v-btn @click="changeStatus(item.mm_id, 'PROGRESS')"
										>진행</v-btn
									>
									<v-btn @click="changeStatus(item.mm_id, 'DONE')">완료</v-btn>
								</v-btn-toggle>
							</div>
						</div>
					</v-list-item>
				</v-list>
			</div>
		</nav>
	</div>
</template>

<script>
export default {};
</script>

<style scoped>
.sticky-box {
	width: 200px;
	height: 570px;
	float: left;
	background-color: white;
	padding: 100%, 0%;
}
.bgcolor {
	background-color: #e0dcdd;
}
.contentBox {
	height: 90%;
}
.divLocation {
	top: 100px;
}
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
	height: 90vh;
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
.basicBtn:hover {
	background-color: #b71c1c;
	color: white;
	outline: 0;
}
.basicBtn:focus {
	outline: none;
}
.basicBtn {
	background-color: transparent;
	border: 2px solid white;
	color: black;
	cursor: pointer;
	font-size: 13px;
	padding: 4px 15px;
	transition: all 200ms;
}
.prebtn:hover {
	background-color: #b71c1c;
	color: white;
	outline: 0;
}
.prebtn:focus {
	outline: none;
}
.prebtn {
	background-color: transparent;
	border: 2px solid #b71c1c;
	color: #b71c1c;
	cursor: pointer;
	font-size: 13px;
	padding: 4px 15px;
	transition: all 200ms;
}
.ingbtn:hover {
	background-color: #b71c1c;
	color: white;
	outline: 0;
}
.ingbtn:focus {
	outline: none;
}
.ingbtn {
	background-color: transparent;
	border: 2px solid #b71c1c;
	color: #b71c1c;
	cursor: pointer;
	font-size: 13px;
	padding: 4px 15px;
	transition: all 200ms;
}
.donebtn:hover {
	background-color: #b71c1c;
	color: white;
	outline: 0;
}
.donebtn:focus {
	outline: none;
}
.donebtn {
	background-color: transparent;
	border: 2px solid #b71c1c;
	color: #b71c1c;
	cursor: pointer;
	font-size: 13px;
	padding: 4px 15px;
	transition: all 200ms;
}
</style>
