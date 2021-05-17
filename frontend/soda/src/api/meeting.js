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
function createTestMeeting() {
	return instance.get(`practice/new`);
}
function deleteTestMeeting(mp_id) {
	return instance.delete(`practice/del?mp_id=${mp_id}`);
}
function deleteInterviewee(mm_id) {
	return instance.delete(`meeting/interviewee/del?mm_id=${mm_id}`);
}
function deleteAllInterviewee(inviteCode) {
	return instance.delete(
		`meeting/interviewee/list/del?inviteCode=${inviteCode}`,
	);
}

export {
	getConfirmMeetingCode,
	attendMeeting,
	delMeeting,
	intervieweeOfMeeting,
	createTestMeeting,
	deleteTestMeeting,
	deleteInterviewee,
	deleteAllInterviewee,
};
