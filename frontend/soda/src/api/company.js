import { instance } from './index';

function authCompany(authCode) {
	return instance.post('company/auth', authCode);
}
function delCompany(cid) {
	return instance.delete(`company/del/${cid}`);
}
function approveEmail(email, u_id) {
	return instance.get('company/email/approve', {
		params: {
			email: email,
			u_id: u_id,
		},
	});
}
function rejectEmail(email) {
	return instance.get('company/email/reject', {
		params: {
			email: email,
		},
	});
}
function findCompany(cname) {
	return instance.get(`company/find/${cname}`);
}
function companyList() {
	return instance.get('company/list');
}
function newCompany(cName, email, phone, role, uName) {
	return instance.post(
		`company/new?cName=${cName}&email=${email}&phone=${phone}&role=${role}&uName=${uName}`,
	);
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
