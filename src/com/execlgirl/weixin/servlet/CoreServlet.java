package com.execlgirl.weixin.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.execlgirl.weixin.service.CoreService;
import com.execlgirl.weixin.util.SignUtil;


public class CoreServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	


	public CoreServlet() {
		super();
	}

	public void destroy() {
		super.destroy();
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {


		String signature = request.getParameter("signature");

		String timestamp = request.getParameter("timestamp");

		String nonce = request.getParameter("nonce");

		String echostr = request.getParameter("echostr");

		PrintWriter out = response.getWriter();

		if (SignUtil.checkSignature(signature, timestamp, nonce)) {
			out.println(echostr);
		}
		out.close();
		out = null;

	}

public	void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	request.setCharacterEncoding("utf-8");
	response.setCharacterEncoding("utf-8");

	String signature = request.getParameter("signature");
	String timestamp = request.getParameter("timestamp");
	String nonce = request.getParameter("nonce");
	PrintWriter out = response.getWriter();
	
	if(SignUtil.checkSignature(signature, timestamp, nonce)){
		String respXML = CoreService.processRequest(request);
		out.write(respXML);
	}
	out.close();
	
	}

	public void init() throws ServletException {

	}

}

























