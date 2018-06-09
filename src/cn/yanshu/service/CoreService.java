package cn.yanshu.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import cn.yanshu.message.MessageUtil;
import cn.yanshu.message.res.Article;
import cn.yanshu.message.res.NewsMessage;
import cn.yanshu.message.res.TextMessage;

public class CoreService {
	/**
	 * 处理微信发来的请求
	 * 
	 * @param request
	 * @return
	 */
	public static String processRequest(HttpServletRequest request)throws Exception {
		    String respMessage = null;
			// 默认返回的文本消息内容
			String respContent = "请求已提交，微信已收到百度bae的回复，但出现异常！";
			
		
			
			// xml请求解析
			Map<String, String> requestMap = MessageUtil.parseXml(request);
			// 发送方帐号（open_id）
			String fromUserName = requestMap.get("FromUserName");
			// 公众帐号
			String toUserName = requestMap.get("ToUserName");
			// 消息类型
			String msgType = requestMap.get("MsgType");
			
			
			// 回复文本消息
			TextMessage textMessage = new TextMessage();
			textMessage.setToUserName(fromUserName);
			textMessage.setFromUserName(toUserName);
			textMessage.setCreateTime(new Date().getTime());
			textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
			textMessage.setFuncFlag(0);
			
			
			 //事件
		     if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_EVENT)) {
				// 事件类型
				String eventType = requestMap.get("Event");
				// 订阅
				if (eventType.equals(MessageUtil.EVENT_TYPE_SUBSCRIBE)) {
					//respContent = "谢谢您的关注！";
					  // 创建图文消息  
	                NewsMessage newsMessage = new NewsMessage();  
	                newsMessage.setToUserName(fromUserName);  
	                newsMessage.setFromUserName(toUserName);  
	                newsMessage.setCreateTime(new Date().getTime());  
	                newsMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);  
	                newsMessage.setFuncFlag(0);  
	     
					
		  
	                List<Article> articleList = new ArrayList<Article>();  
	               
                    Article article1 = new Article();  
                    article1.setTitle("引言\n卓翼IT微信公众帐号");  
                    article1.setDescription("");  
                    article1.setPicUrl("http://dengchouchou.duapp.com/webchat/img/mes.jpg");  
                    article1.setUrl("http://dengchouchou.duapp.com/mes");  
                    
                    Article article2 = new Article();  
                    article2.setTitle("第1部分\nMES异常微信处理");  
                    article2.setDescription("");  
                    article2.setPicUrl("http://dengchouchou.duapp.com/webchat/img/mes_s.jpg");  
                    article2.setUrl("http://dengchouchou.duapp.com/mes"); 
                    
                    Article article3 = new Article();  
                    article3.setTitle("第2部分\n其他相关功能待开发....");  
                    article3.setDescription("");  
                    article3.setPicUrl("http://dengchouchou.duapp.com/webchat/img/mes_s.jpg");  
                    article3.setUrl("http://dengchouchou.duapp.com/mes"); 
                    
  
                    articleList.add(article1);  
                    articleList.add(article2);  
                    articleList.add(article3);  
                    newsMessage.setArticleCount(articleList.size());  
                    newsMessage.setArticles(articleList);  
                    
                    respMessage = MessageUtil.newsMessageToXml(newsMessage);  
				}
				// 取消订阅
				else if (eventType.equals(MessageUtil.EVENT_TYPE_UNSUBSCRIBE)) {
					// TODO 取消订阅后用户再收不到公众号发送的消息，因此不需要回复消息
				}
				// 自定义菜单点击事件
				else if (eventType.equals(MessageUtil.EVENT_TYPE_CLICK)) {
					// TODO 自定义菜单权没有开放，暂不处理该类消息
				}
				
		     }else{
		    	    // 文本消息
					if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_TEXT)) {
						respContent = "您发送的是文本消息！";
					}
					// 图片消息
					else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_IMAGE)) {
						respContent = "您发送的是图片消息！";
					}
					// 地理位置消息
					else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LOCATION)) {
						respContent = "您发送的是地理位置消息！";
					}
					// 链接消息
					else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LINK)) {
						respContent = "您发送的是链接消息！";
					}
					// 音频消息
					else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_VOICE)) {
						respContent = "您发送的是音频消息！";
					}
					 textMessage.setContent(respContent);
					 respMessage = MessageUtil.textMessageToXml(textMessage);
		     }
		
		     
		  return respMessage;
	}
}