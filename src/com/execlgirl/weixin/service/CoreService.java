package com.execlgirl.weixin.service;

import java.util.Date;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.execlgirl.weixin.function.Reservation;
import com.execlgirl.weixin.function.Signin;
import com.execlgirl.weixin.message.TextMessage;
import com.execlgirl.weixin.util.MessageUtil;
import com.execlgirl.weixin.util.MySQLUtil;
import com.execlgirl.weixin.util.MySQLUtil_Xin;

public class CoreService {
	private static Logger logger = Logger.getLogger(CoreService.class);
	

	public static String processRequest(HttpServletRequest request) {
		
		String respXML = null;
		TextMessage tm = new TextMessage();
		
		try{
		    HashMap<String,String> requestMap = MessageUtil.parseXML(request);
		    String fromUserName = requestMap.get("FromUserName");
		    String toUserName = requestMap.get("ToUserName");
		    String msgType = requestMap.get("MsgType");
		    
		    logger.info(String.format("openId:%s,type:%s",fromUserName,msgType));
		    
		    tm.setFromUserName(toUserName);
		    tm.setToUserName(fromUserName);
		    tm.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
		    tm.setCreatetime(new Date().getTime());
		    
		    if(msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_TEXT)){
		    	
		    	String content = requestMap.get("Content");
		    	
		    	if(content.contains("sign")){
		    		tm.setContent(Signin.dealContent(fromUserName));
		    	}else if(content.contains("reservation")){
		    		tm.setContent(Reservation.dealContent(content, fromUserName));
		    	}else if(content.contains("QDCS")){
		    		// 测试签到总次数
		    		int count = MySQLUtil_Xin.getTotalCount(fromUserName);
		    		tm.setContent("total_count -> " + count);
		    	}else {
					tm.setContent("1.reply 'reservation'." + "\n" + "2.reply 'sign'.");
				}
		    	
		    	logger.info(content);
		    	
		    }
		    else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_EVENT)){
		    	
		    	String eventType = requestMap.get("Event");
		    	if(eventType.equals(MessageUtil.EVENT_TYPE_SUBSCRIBE)){
		    		tm.setContent("welcome!" + "\n" + "1.reply 'reservation'. "+ "\n" +"2.reply 'sign'.");
		    		MySQLUtil.saveWeixinUser(fromUserName);
		    	}
		    	else if(eventType.equals(MessageUtil.EVENT_TYPE_UNSUBSCRIBE)){
		    		
		    	}
		    }
		    

		    else if(msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_IMAGE)){
		    	tm.setContent("image");
		    }
		    else if(msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LINK)){
		    	tm.setContent("link");
		    }
		    else if(msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_VIDEO)){
		    	tm.setContent("video");
		    }
		    else if(msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_VOICE)){
		    	tm.setContent("voice");
		    }
		    else if(msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LOCATION)){
		    	tm.setContent("location");
		    }
		    
		    respXML = MessageUtil.messageToXML(tm);

		}catch(Exception e){
			e.printStackTrace();
		}
	
		return respXML;
	}

}




























