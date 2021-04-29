import { instance } from './index';

function postApproveEmail(companyInfo) {
	return instance.post('email/approve', companyInfo);
}
function postRejectEmail(companyInfo) {
	return instance.post('email/reject', companyInfo);
}

export { postApproveEmail, postRejectEmail };
