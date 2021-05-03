import { instance } from './index';

function delMember(id) {
	return instance.delete(`member/del/${id}`);
}
function editCompany(member) {
	return instance.put('member/edit/company', member);
}
function editEmail(member) {
	return instance.put('member/edit/email', member);
}
function editStatus(member) {
	return instance.put('member/edit/status', member);
}
function sendMeetingCode(dto) {
	return instance.post('member/invite', dto);
}
function memberList() {
	return instance.get('member/list');
}
function searchMember(uName) {
	return instance.get(`member/search/${uName}`);
}

export {
	delMember,
	editCompany,
	editEmail,
	editStatus,
	sendMeetingCode,
	memberList,
	searchMember,
};
