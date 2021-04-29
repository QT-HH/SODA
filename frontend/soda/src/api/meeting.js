import { instance } from './index';

function getConfirmMeetingCode(cidentify) {
	return instance.get('meeting/inviteCode', cidentify);
}

export { getConfirmMeetingCode };
