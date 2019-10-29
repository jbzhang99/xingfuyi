package com.yixiekeji.web.util;

import javax.servlet.http.HttpServletRequest;

/**
 * 登录设备信息工具类
 */
public class SystemUtil {

	public final static int	ANDROID	= 3;
	public final static int	IOS		= 4;

	/**
	 * 获取设备系统信息：3、Android；4、ios
	 * @param request
	 * @return
	 */
	public static int getSysVersion(HttpServletRequest request) {
		int sv = 0;
		String agent = request.getHeader("user-agent");
		if (agent.contains("Android")) {
			sv = ANDROID;
		} else if (agent.contains("iPhone") || agent.contains("iPod") || agent.contains("iPad")) {
			sv = IOS;
		}
		return sv;
	}
}
