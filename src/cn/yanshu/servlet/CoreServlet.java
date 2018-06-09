package cn.yanshu.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.yanshu.service.CoreService;
import cn.yanshu.util.SignUtil;

public class CoreServlet extends HttpServlet {

	/**
	 * The doGet method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
             request.setCharacterEncoding("utf-8");
             response.setContentType("text/html;charset=utf-8");
             // 微信加密签名
    		String signature = request.getParameter("signature");
    		// 时间戳
    		String timestamp = request.getParameter("timestamp");
    		// 随机数
    		String nonce = request.getParameter("nonce");
    		// 随机字符串
    		String echostr = request.getParameter("echostr");

    		PrintWriter out = response.getWriter();
    		// 通过检验signature对请求进行校验，若校验成功则原样返回echostr，表示接入成功，否则接入失败
    		if (SignUtil.checkSignature(signature, timestamp, nonce)) {
    			out.print(echostr);
    		}
    		out.close();
    		out = null;
	}

	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to post.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			 {
		    try {
				request.setCharacterEncoding("UTF-8");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}  
	        response.setCharacterEncoding("UTF-8");  
	  
	        // 调用核心业务类接收消息、处理消息  
	        String respMessage=null;
			try {
				respMessage = CoreService.processRequest(request);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}  
	          
	        // 响应消息  
	        PrintWriter out=null;
			try {
				out = response.getWriter();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}  
	        out.print(respMessage);  
	        out.close();  
	}

}
