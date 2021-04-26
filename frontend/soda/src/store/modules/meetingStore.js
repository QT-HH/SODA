import axios from 'axios';
//import moment from 'moment';
import Swal from 'sweetalert2';
import { OpenVidu } from 'openvidu-browser';

import SERVER from '@/api/url';
import secrets from '@/secrets';

const ToastEnter = Swal.mixin({
	toast: true,
	position: 'top-end',
	showConfirmButton: false,
	timer: 3000,
	timerProgressBar: false,
	background: '#A0C4FF',
	onOpen: toast => {
		toast.addEventListener('mouseenter', Swal.stopTimer);
		toast.addEventListener('mouseleave', Swal.resumeTimer);
	},
});

const ToastLeave = Swal.mixin({
	toast: true,
	position: 'top-end',
	showConfirmButton: false,
	timer: 3000,
	timerProgressBar: false,
	background: '#FFADAD',
	onOpen: toast => {
		toast.addEventListener('mouseenter', Swal.stopTimer);
		toast.addEventListener('mouseleave', Swal.resumeTimer);
	},
});

const meetingStore = {
	namespaced: true,

	/**********************          STATE           ****************************/
	state: {
		// pre meeting
		meetingDialog: false,
		mySessionId: null,
		roomId: null,
		nickName: null,
		roomHost: null,
		isHost: null,
		nextRoomHost: null,
		allTags: null,
		roomInfo: null,
		meetingInfoDialog: false,

		// openvidu
		OV: undefined,
		ovToken: null,
		session: undefined,
		mainStreamManager: undefined,
		publisher: undefined,
		subscribers: [],

		// screen share
		screenOV: undefined,
		screenSession: undefined,
		screenMainStreamManager: undefined,
		screenPublisher: undefined,
		screenSubscribers: [],
		screenOvToken: null,
		isSharingMode: false,

		// mode
		currentMode: null,
		modeHost: null,

		//chatting
		isChatPanel: false,
		messages: [],
		secretName: null,
	},

	/**********************          Getter          ****************************/
	getters: {},
	/**********************          Mutate          ****************************/
	mutations: {
		SET_MEETING_DIALOG(state, value) {
			state.meetingDialog = value;
		},
		SET_MYSESSIONID(state, sessionId) {
			state.mySessionId = sessionId;
		},
		SET_ROOMID(state, roomId) {
			state.roomId = roomId;
		},
		SET_NICKNAME(state, nickName) {
			state.nickName = nickName;
		},
		SET_ROOM_HOST(state, value) {
			state.roomHost = value;
		},
		SET_IS_HOST(state, value) {
			state.isHost = value;
		},
		SET_NEXT_ROOM_HOST(state, value) {
			state.nextRoomHost = value;
		},
		SET_ALL_TAGS(state, value) {
			state.allTags = value;
		},
		SET_ROOM_INFO(state, value) {
			state.roomInfo = value;
		},
		SET_MEETING_INFO_DIALOG(state, value) {
			state.meetingInfoDialog = value;
		},

		// Openvidu
		SET_OV(state, OV) {
			state.OV = OV;
		},
		SET_OVTOKEN(state, token) {
			state.ovToken = token;
		},
		SET_SESSION(state, session) {
			state.session = session;
		},
		SET_MAINSTREAMMANAGER(state, mainStreamManager) {
			state.mainStreamManager = mainStreamManager;
		},
		SET_PUBLISHER(state, publisher) {
			state.publisher = publisher;
		},
		SET_SUBSCRIBERS(state, subscribers) {
			state.subscribers = subscribers;
		},

		// screen share
		SET_SCREEN_OV(state, OV) {
			state.screenOV = OV;
		},
		SET_SCREEN_SESSION(state, session) {
			state.screenSession = session;
		},
		SET_SCREEN_MAINSTREAMMANAGER(state, mainStreamManager) {
			state.screenMainStreamManager = mainStreamManager;
		},
		SET_SCREEN_PUBLISHER(state, publisher) {
			state.screenPublisher = publisher;
		},
		SET_SCREEN_SUBSCRIBERS(state, subscribers) {
			state.screenSubscribers = subscribers;
		},
		SET_SCREEN_OVTOKEN(state, token) {
			state.screenOvToken = token;
		},
		SET_IS_SHARING_MODE(state, value) {
			state.isSharingMode = value;
		},

		// chatting
		SET_IS_CHATPANEL(state, value) {
			state.isChatPanel = value;
		},
		SET_MESSAGES(state, data) {
			state.messages.push(data);
		},
		SET_CLEARMESSAGES(state) {
			state.messages = [];
		},
		SET_SECRET_NAME(state, value) {
			state.secretName = value;
		},
	},
	/**********************          Action           ****************************/
	actions: {
		// meeting
		changeMeetingDialog({ commit }, value) {
			commit('SET_MEETING_DIALOG', value);
		},
		createSessionId({ dispatch }) {
			axios.get(SERVER.URL + SERVER.ROUTES.room + '/inviteCode').then(res => {
				dispatch('joinSession', {
					code: res.data,
					isCreator: true,
				});
			});
		},

		// openvidu
		joinSession({ state, commit, dispatch }, joinData) {
			commit('SET_MYSESSIONID', joinData.code);
			// --- Get an OpenVidu object ---
			const OV = new OpenVidu();
			// --- Init a session ---
			const session = OV.initSession();
			// --- Specify the actions when events take place in the session ---
			// On every new Stream received...
			const subscribers = [];
			session.on('streamCreated', ({ stream }) => {
				const subscriber = session.subscribe(stream);
				subscribers.push(subscriber);

				if (!state.isNewbie && !stream.connection.data.includes('screen')) {
					ToastEnter.fire({
						html: `<span style="color: #0764FF">🎉${stream.connection.data.slice(
							15,
							-2,
						)}</span><span>님이 입장하였습니다🎉</span>`,
					});
				}

				if (
					!state.isNewbie &&
					stream.connection.data.includes('screen') &&
					state.publisher.stream.connection.data.slice(15, -2) !==
						stream.connection.data.slice(15, -8)
				) {
					ToastEnter.fire({
						html: `<span style="color: #0764FF">🤘${stream.connection.data.slice(
							15,
							-8,
						)}</span><span>님이 화면공유를 시작했습니다🤘</span>`,
					});
				}
			});

			// On every Stream destroyed...
			session.on('streamDestroyed', ({ stream }) => {
				const index = subscribers.indexOf(stream.streamManager, 0);
				if (index >= 0) {
					subscribers.splice(index, 1);
				}

				if (!stream.connection.data.includes('screen')) {
					ToastLeave.fire({
						html: `<span style="color: #FFFCFA">✋${stream.connection.data.slice(
							15,
							-2,
						)}</span><span>님이 퇴장하였습니다✋</span>`,
					});
				}
			});
			// --- Connect to the session with a valid user token ---
			// 'getToken' method is simulating what your server-side should do.
			// 'token' parameter should be retrieved and returned by your own backend
			if (joinData.isCreator) {
				dispatch('getToken', joinData.code).then(token => {
					let publisher = OV.initPublisher(undefined, {
						audioSource: undefined, // The source of audio. If undefined default microphone
						videoSource: undefined, // The source of video. If undefined default webcam
						publishAudio: true, // Whether you want to start publishing with your audio unmuted or not
						publishVideo: true, // Whether you want to start publishing with your video enabled or not
						resolution: '640x480', // The resolution of your video
						frameRate: 30, // The frame rate of your video
						insertMode: 'APPEND', // How the video is inserted in the target element 'video-container'
						mirror: true, // Whether to mirror your local video or not
					});
					commit('SET_OV', OV);
					commit('SET_MAINSTREAMMANAGER', publisher);
					commit('SET_PUBLISHER', publisher);
					commit('SET_SESSION', session);
					commit('SET_SUBSCRIBERS', subscribers);
					commit('SET_OVTOKEN', token);
				});
			} else {
				dispatch('createToken', joinData.code).then(token => {
					let publisher = OV.initPublisher(undefined, {
						audioSource: undefined, // The source of audio. If undefined default microphone
						videoSource: undefined, // The source of video. If undefined default webcam
						publishAudio: true, // Whether you want to start publishing with your audio unmuted or not
						publishVideo: true, // Whether you want to start publishing with your video enabled or not
						resolution: '640x480', // The resolution of your video
						frameRate: 30, // The frame rate of your video
						insertMode: 'APPEND', // How the video is inserted in the target element 'video-container'
						mirror: true, // Whether to mirror your local video or not
					});
					commit('SET_OV', OV);
					commit('SET_MAINSTREAMMANAGER', publisher);
					commit('SET_PUBLISHER', publisher);
					commit('SET_SESSION', session);
					commit('SET_SUBSCRIBERS', subscribers);
					commit('SET_OVTOKEN', token);
				});
			}
		},
		getToken({ dispatch }, mySessionId) {
			return dispatch('createSession', mySessionId).then(sessionId =>
				dispatch('createToken', sessionId),
			);
		},
		createSession(context, sessionId) {
			console.log(secrets.OPENVIDU.SECRET_KEY);
			return new Promise((resolve, reject) => {
				axios
					.post(
						`${SERVER.OPENVIDU_URL}/openvidu/api/sessions`,
						JSON.stringify({
							customSessionId: sessionId,
						}),
						{
							headers: {
								'Content-Type': 'application/json',
							},
							auth: {
								username: 'OPENVIDUAPP',
								password: secrets.OPENVIDU.SECRET_KEY,
							},
						},
					)
					.then(response => response.data)
					.then(data => resolve(data.id))
					.catch(error => {
						if (error.response.status === 409) {
							resolve(sessionId);
							return {};
						} else {
							console.warn(
								`No connection to OpenVidu Server. This may be a certificate error at ${SERVER.OPENVIDU_URL}`,
							);
							if (
								window.confirm(
									`No connection to OpenVidu Server. This may be a certificate error at ${SERVER.OPENVIDU_URL}\n\nClick OK to navigate and accept it. If no certificate warning is shown, then check that your OpenVidu Server is up and running at "${SERVER.OPENVIDU_URL}"`,
								)
							) {
								location.assign(`${SERVER.OPENVIDU_URL}/accept-certificate`);
							}
							reject(error.response);
						}
					});
			});
		},
		createToken(context, sessionId) {
			return new Promise((resolve, reject) => {
				axios
					.post(
						`${SERVER.OPENVIDU_URL}/api/tokens`,
						JSON.stringify({
							session: sessionId,
							kurentoOptions: {
								allowedFilters: ['GStreamerFilter', 'FaceOverlayFilter'],
							},
						}),
						{
							headers: {
								'Content-Type': 'application/json',
							},
							auth: {
								username: 'OPENVIDUAPP',
								password: secrets.OPENVIDU.SECRET_KEY,
							},
						},
					)
					.then(response => response.data)
					.then(data => resolve(data.token))
					.catch(error => {
						Swal.fire({
							title:
								'오류가 발생했습니다. 입장 정보를 다시 한 번 확인해주세요.',
							icon: 'error',
						});
						reject(error.response);
					});
			});
		},
	},
};

export default meetingStore;
