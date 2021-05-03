import { instance } from './index';

// function getConfirmMeetingCode(cidentify) {
// 	return instance.get(`meeting/inviteCode?cidentify=${cidentify}`);
// }
// function getConfirmMeetingCode(authCode) {
// 	return instance.post('company/auth', {
// 		params: {
// 			authCode: authCode,
// 		},
// 	});
// }
// function getConfirmMeetingCode(authCode) {
// 	return instance.post('company/auth', authCode);
// }
function getConfirmMeetingCode(authCode) {
	return instance.post(`company/auth?authCod=${authCode}`);
}

export { getConfirmMeetingCode };
