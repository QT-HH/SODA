import { instance } from './index';

function getConfirmMeetingCode(inviteCode) {
	return instance.get('meeting/inviteCode', {
		params: {
			inviteCode: inviteCode,
		},
	});
}
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
function intervieweeOfMeeting(inviteCode) {
	return instance.get(`meeting/interviewee/list`, {
		params: {
			inviteCode: inviteCode,
		},
	});
}

export {
	getConfirmMeetingCode,
	attendMeeting,
	delMeeting,
	intervieweeOfMeeting,
};
