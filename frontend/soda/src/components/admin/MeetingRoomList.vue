<template>
	<div>
		<v-btn depressed @click="updateRoomList">방 정보 새로고침</v-btn>
		<v-simple-table>
			<template>
				<thead>
					<tr>
						<th class="text-center">미팅코드</th>
						<th class="text-center">담당자</th>
						<th class="text-center">참가자</th>
					</tr>
				</thead>
				<tbody>
					<MeetingRoom
						v-for="(item, index) in RoomList"
						:Room="item"
						:key="index"
					></MeetingRoom>
				</tbody>
			</template>
		</v-simple-table>
	</div>
</template>

<script src="https://rtcmulticonnection.herokuapp.com/dist/RTCMultiConnection.min.js"></script>
<script src="https://rtcmulticonnection.herokuapp.com/socket.io/socket.io.js"></script>

<script>
import MeetingRoom from '@/components/admin/MeetingRoom.vue';

export default {
	name: 'MeetingRoomList',
	components: {
		MeetingRoom,
	},
	data() {
		return {
			connection: null,
			publicRoomIdentifier: 'sodasoda',
			RoomList: [],
		};
	},
	mounted() {
		this.connection = new RTCMultiConnection();
		this.connection.session = {
			audio: true,
			video: true,
			data: true,
		};
		this.connection.publicRoomIdentifier = this.publicRoomIdentifier;
		this.connection.socketURL = `https://rtcmulticonnection.herokuapp.com:443/`;
		this.connection.connectSocket();
	},
	beforeDestroy() {
		this.connection.closeSocket();
		this.connection = null;
	},
	methods: {
		updateRoomList() {
			this.connection.socket.emit(
				'get-public-rooms',
				this.publicRoomIdentifier,
				function (listOfRooms) {
					this.rooms = listOfRooms;
				},
			);
			this.RoomList = this.connection.socket.rooms;
		},
	},
};
</script>

<style scoped>
.table {
	width: 20%;
}
</style>
