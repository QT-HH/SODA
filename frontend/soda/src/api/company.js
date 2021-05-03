import { instance } from './index';

function getCompanyList() {
	return instance.get('company/list');
}
function deleteCompany(cid) {
	return instance.delete('company/del', {
		params: {
			cid: cid,
		},
	});
}
function postCompanyInfo(info) {
	return instance.post('company/new-company', info);
}

export { getCompanyList, deleteCompany, postCompanyInfo };
