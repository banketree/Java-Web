package myuser;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.websocket.Session;

import myvisit.*;

/**
 * Servlet implementation class CheckIt
 */
@WebServlet("/CheckIt")
public class CheckIt extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CheckIt() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
		HttpSession session=request.getSession();
		
		request.setCharacterEncoding("UTF-8");
		User user=new User();
		user.setUserName(request.getParameter("uname"));   //uname�����ڵ���б�񴫹�����
		user.setPwd(request.getParameter("pwd")); 
		int id=0;
		PrintWriter out=response.getWriter();
		try {
			UserDAO dao =new UserDAO();
			id=dao.getUserId(user); //��ȡidֵ
			if(id==0){  //���idֵΪ0����ʾ������
				response.sendRedirect("login.jsp");  //��ת��¼ҳ��
				return;   //�Լ�����
				//��Ҫ��һ����ʾ��ȥ��ǿ��˵��session��������
				//��������
			}
		} catch (NamingException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//������ľ���id���ڣ���֤ͨ�������
			//�޸��û����߱���idд��

		Visitor v=(Visitor) session.getAttribute("USER");  //�õ���ǰ�ÿ�
		v.setUserId(id);   //�µĶ��е�userid,�и�ë�ã���������ע���ʱ������𣿣���
		try {
			VisitorDAO dao=new VisitorDAO();
			dao.updateVisitor(v);
		} catch (NamingException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		response.sendRedirect("main.jsp");  //��ת��ҳ��
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
