import { instance } from './index';

function postApproveEmail(companyInfo) {
	return instance.post('email/approve', {
		params: {
			companyInfo: companyInfo,
		},
	});
}
function postRejectEmail(companyInfo) {
	return instance.post('email/reject', {
		params: {
			companyInfo: companyInfo,
		},
	});
}

export { postApproveEmail, postRejectEmail };
