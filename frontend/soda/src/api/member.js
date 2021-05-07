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
function editStatus(mm_id, status) {
	return instance.put(`member/edit/status?mm_id=${mm_id}&status=${status}`);
}
// function editStatus(mm_id, status) {
// 	return instance.put('member/edit/status', {
// 		params: {
// 			mm_id: mm_id,
// 			status: status,
// 		},
// 	});
// }
function sendMeetingCode(dto) {
	return instance.post('member/invite', dto);
}
function memberList(inviteCode) {
	return instance.get('member/list', {
		params: {
			inviteCode: inviteCode,
		},
	});
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
