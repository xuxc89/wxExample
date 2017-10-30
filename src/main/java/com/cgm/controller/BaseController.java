package com.cgm.controller;

import static com.cgm.common.constant.WXConstant.SessionKey_openId;

import javax.servlet.http.HttpSession;

public class BaseController {
//	@Autowired
//	private HttpServletRequest request;
	
	public String getOpenId(HttpSession session) {
		String openId = (String) session.getAttribute(SessionKey_openId);
		//TODO 调试接口
//		if(StringUtils.isBlank(openId)) {
//			openId =
//		}
		return openId;
	}
	
}
