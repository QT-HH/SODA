import { instance } from './index';

function getCompanyList() {
	return instance.get('company/company-list');
}
function deleteCompany(cid) {
	return instance.delete('company/del', cid);
}
function postCompanyInfo(info) {
	return instance.post('company/new-company', info);
}

export { getCompanyList, deleteCompany, postCompanyInfo };
