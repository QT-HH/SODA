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

export { getConfirmMeetingCode };
