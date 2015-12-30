package com.execlgirl.weixin.test;

import org.apache.log4j.Logger;

public class Log4jTest {
	
	private static Logger logger = Logger.getLogger(Log4jTest.class);

	public static void main(String[] args) {
		logger.debug("debug");
		logger.info("info");
		logger.warn("warn");
		logger.error("error");
		
	}

}
