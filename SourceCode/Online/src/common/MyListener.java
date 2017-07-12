package common;

import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;

import javax.naming.NamingException;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;

import javax.servlet.ServletContextListener;
import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import myhis.History;
import myhis.HistoryDAO;
import myvisit.*;
import java.util.*;

@WebListener
public class MyListener implements ServletContextListener, HttpSessionListener, ServletRequestListener {
	HttpServletRequest request;
    /**
     * Default constructor. 
     */
    public MyListener() {
        // TODO Auto-generated constructor stub
    }

	/**
     * @see HttpSessionListener#sessionCreated(HttpSessionEvent)
     */
    public void sessionCreated(HttpSessionEvent arg0)  { 
    	HttpSession session=arg0.getSession();
    	session.setMaxInactiveInterval(10*60);

    	Visitor v=new Visitor();
    	v.setIp(request.getRemoteAddr());
    	v.setComeFrom(request.getHeader("Referer"));  //��ȡ��Դ����վ
    	v.setVisitTime(new Date());
    	//���浽���ݿ���
    	VisitorDAO dao;
    	int id=0;
    	try {
			dao=new VisitorDAO();
			id=dao.saveVisitor(v);  //�������ݿ�     �õ�id,�պ����
		} catch (NamingException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	v.setId(id);  //�õ�id��д��visitor��
    	//���浽�����û��б���,����session id��������Ϣ
       	ServletContext application=arg0.getSession().getServletContext();
    	@SuppressWarnings("unchecked")
		HashMap<String, Visitor> map=(HashMap<String, Visitor>) application.getAttribute("ONLINE");
    	map.put(session.getId(), v);
    	//application.setAttribute("ONLINE", map);	//����֮��map�е������Զ��ģ������ټ�app����
    	
    	session.setAttribute("USER", v);  //���id��ֱ�ӷ�USER������ʹ��
    }

	/**
     * @see ServletRequestListener#requestDestroyed(ServletRequestEvent)
     */
    public void requestDestroyed(ServletRequestEvent arg0)  { 
         // TODO Auto-generated method stub
    }

	/**
     * @see ServletRequestListener#requestInitialized(ServletRequestEvent)
     */
    public void requestInitialized(ServletRequestEvent arg0)  { 
    	request=(HttpServletRequest) arg0.getServletRequest();
    	History his=new History();
    	his.setVisitTime(new Date());
    	his.setUrl(request.getRequestURI().toString());   //��ȡ�����url��ַ
    	HttpSession session=request.getSession();   //���session
    	Visitor v=(Visitor)session.getAttribute("USER");//��������Visitor����id��
    	his.setId(v.getId());  //�������idȡ������his�У��û����û����ʶ���һ��id
    	try {
			HistoryDAO dao=new HistoryDAO();
			dao.saveHistory(his);   //�Ѵ�����Ϣ��his������
		} catch (NamingException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

	/**
     * @see HttpSessionListener#sessionDestroyed(HttpSessionEvent)
     */
    public void sessionDestroyed(HttpSessionEvent arg0)  { 
         //���û��������û�����ɾ��
    	ServletContext app=arg0.getSession().getServletContext();
    	@SuppressWarnings("unchecked")
		HashMap<String, Visitor> map=(HashMap<String, Visitor>)app.getAttribute("ONLINE");
    	String id=arg0.getSession().getId();

//����ĶԱ�Ѱ�ҳ�id
//    	Set<String> ids=map.keySet();  //��app��ȡ��id
//    	Iterator<String> it =ids.iterator();
//    	while(it.hasNext()){
//    		id=it.next();
//    		if(id.equals(arg0.getSession().getId())){  //��app��ȡ�õ�id�뵱ǰid�Ƚ�
//    			break;
//    		}
//    	}
		//ɾ��֮ǰ���ȸĵ�ǰ�û������ݿ���뿪ʱ��
		Visitor v=map.get(id);   //�ó��û�
    	v.setLeftTime(new Date());
    	try {
    		VisitorDAO dao=new VisitorDAO();
			dao.saveVisitor(v);
		} catch (SQLException |NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
    	//�����������û���ɾ��
    	map.remove(id);
//    	app.setAttribute("ONLINE", map);    ����ʡ�Դ˲���
    }

	/**
     * @see ServletContextListener#contextDestroyed(ServletContextEvent)
     */
    public void contextDestroyed(ServletContextEvent arg0)  { 
         // TODO Auto-generated method stub
    }

	/**
     * @see ServletContextListener#contextInitialized(ServletContextEvent)
     */
    public void contextInitialized(ServletContextEvent arg0)  { 
         // TODO Auto-generated method stub
    	//��������������ʱ�򣬱���һ���յ�hashmap��application��
    	HashMap<String, Visitor> map=new HashMap<String,Visitor>();
       	ServletContext application=arg0.getServletContext();
    	application.setAttribute("ONLINE", map);
    }
}
