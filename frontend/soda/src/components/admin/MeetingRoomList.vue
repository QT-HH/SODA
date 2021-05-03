<template>
	<div>
		<v-btn depressed @click="updateRoomList">방 정보 새로고침</v-btn>
	</div>
</template>

<script src="https://rtcmulticonnection.herokuapp.com/dist/RTCMultiConnection.min.js"></script>
<script src="https://rtcmulticonnection.herokuapp.com/socket.io/socket.io.js"></script>

<script>
export default {
	name: 'MeetingRoomList',
	data() {
		return {
			connection: null,
			publicRoomIdentifier: 'sodasoda',
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
		// this.connection.openOrJoin('asdf');
	},
	methods: {
		updateRoomList() {
			this.connection.socket.emit(
				'get-public-rooms',
				this.publicRoomIdentifier,
				function (listOfRooms) {
					console.log(listOfRooms);
				},
			);
		},
	},
};
</script>

<style></style>
