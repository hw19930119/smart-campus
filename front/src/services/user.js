import request from '../utils/request';

import { user as api } from '../config/service';

export function getUserList() {
    return request(api.getUserList, true);
}
import PortalMessenger from '@share/portal-messenger';
const portalMessenger = PortalMessenger('appiframe');
// 网格化3.0新开tab页
export const zeusPortalOpen = (url, key, label) => {
    //kye不能为空，为空时使用[label]_rand作为key
    const id = key || label+"_rand";
    portalMessenger.openTab({
                                app_id: id,
                                key: id,
                                label,
                                url
                            });
};
