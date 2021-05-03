import { instance } from './index';

function authCompany(authCode) {
	return instance.post('company/auth', authCode);
}
function delCompany(cid) {
	return instance.delete(`company/del/${cid}`);
}
function approveEmail(email, u_id) {
	return instance.get(`company/email/approve/${email}/${u_id}`);
}
function rejectEmail(email) {
	return instance.get(`company/email/reject/${email}`);
}
function findCompany(cname) {
	return instance.get(`company/find/${cname}`);
}
function companyList() {
	return instance.get('company/list');
}
function newCompany(cName, email, phone, role, uName) {
	return instance.post('company/new', cName, email, phone, role, uName);
}

export {
	authCompany,
	delCompany,
	approveEmail,
	rejectEmail,
	findCompany,
	companyList,
	newCompany,
};
