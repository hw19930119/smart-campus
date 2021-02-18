/*
 * @(#) getXzqh.js
 * 版权声明 厦门畅享信息技术有限公司, 版权所有 违者必究
 *
 * <br> Copyright:  Copyright (c) 2020
 * <br> Company:厦门畅享信息技术有限公司
 * <br> @author liuce
 * <br> 2020-05-11 16:45:59
 */

import '@/services/eos3/eos3.min';
import '@/services/auth/xzqhQgService';

export default function(code, level) {
    return eos.auth.xzqhQgService.getXzqh(code, level).then(data => {
        return data;
    });
}
