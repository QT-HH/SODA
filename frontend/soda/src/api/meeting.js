import { instance } from './index';

// function getConfirmMeetingCode(cidentify) {
// 	return instance.get(`meeting/inviteCode?cidentify=${cidentify}`);
// }
function getConfirmMeetingCode(cidentify) {
	return instance.get('meeting/inviteCode', {
		params: {
			cidentify: cidentify,
		},
	});
}
// function getConfirmMeetingCode(authCode) {
// 	return instance.post('company/auth', authCode);
// }
// function getConfirmMeetingCode(authCode) {
// 	return instance.post(`company/auth?authCod=${authCode}`);
// }
// function getConfirmMeetingCode(authCode) {
// 	return instance.post(`company/auth?authCod=${authCode}`);
// }
function attendMeeting(email, inviteCode) {
	return instance.get(`meeting/attend`, {
		params: {
			email: email,
			inviteCode: inviteCode,
		},
	});
}
function delMeeting(inviteCode) {
	return instance.delete(`meeting/del/${inviteCode}`);
}
function meetingInviteCode(cidentify) {
	return instance.get(`meeting/inviteCode/${cidentify}`);
}

export { getConfirmMeetingCode, attendMeeting, delMeeting, meetingInviteCode };
