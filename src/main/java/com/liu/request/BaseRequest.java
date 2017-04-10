package com.liu.request;

import com.liu.utils.ApiParamCheckUtil;

public class BaseRequest {

	public String check() {
		return ApiParamCheckUtil.checkParam(this);
	}

}
