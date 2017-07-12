package myuser;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import javax.naming.NamingException;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import myvisit.Visitor;

/**
 * Servlet implementation class CheckExists
 */
@WebServlet("/CheckExists")
public class CheckExists extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CheckExists() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String name=request.getParameter("username");
		int id=0;
		try {
			UserDAO dao=new UserDAO();
			id=dao.getIdByName(name);
		} catch (NamingException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//��idΪ0��˵��û������û�
		//���ñ��룬�ж�idΪ0������û��������ڣ�return��������
		response.setCharacterEncoding("UTF-8");
		PrintWriter out=response.getWriter();
		if(id==0){   //˵���ѵ�¼
			out.println("�û���������");
			return;
		}	
		
//�ж������û������Ƿ��и�id		
		ServletContext application=request.getServletContext(); //application�Ļ�ȡ
		@SuppressWarnings("unchecked")
		HashMap<String,Visitor> map=(HashMap<String,Visitor>)application.getAttribute("ONLINE");
		Set<String> ids=map.keySet();
		Iterator<String> it=ids.iterator();
		Visitor current=null;
		while(it.hasNext()){
			String sid=it.next();
			Visitor v=map.get(sid);
			if(v.getUserId()==id){
				current=v;
				break;
			}
		}
		
		if(current!=null){   //˵���ѵ�¼
			out.println("��ǰ�û�����IPΪ["+current.getIp()+"]���豸��¼");
		}	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
