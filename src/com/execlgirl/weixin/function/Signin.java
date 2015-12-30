package com.execlgirl.weixin.function;

import com.execlgirl.weixin.util.MySQLUtil_Xin;

public class Signin {

	public static String dealContent(String fromUserName) {
		StringBuffer tm = new StringBuffer("");

		// 判断用户今天是否已经签到
		if (!MySQLUtil_Xin.isTodaySigned(fromUserName)) {
			// 如果还没签到
			int count = MySQLUtil_Xin.sign(fromUserName);
			if( count != -1 ) {
				tm.append("Sign successful!Total number of signed is :" + count);
			}

		} else {
			// 今天已经签到
			tm.append("You had been sign!");
		}
		return tm.toString();
		
	}

}
