package javastudy;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class BookDAO {
	Connection conn;
	Statement st;
	String sql;
	PreparedStatement ps;
	
	public BookDAO() throws ClassNotFoundException,SQLException{
		System.out.println("�����ʼ��");
		Class.forName("com.mysql.jdbc.Driver");
	    String url="jdbc:mysql://127.0.0.1:3306/book";
	  	String user="root";	
	  	String pwd="123456";
	  	conn=DriverManager.getConnection(url,user,pwd);
	  	st=conn.createStatement();  //��������
	}
	
	public ArrayList<Book> getBooks() throws SQLException{
		sql="select * from books";
		ResultSet rs=st.executeQuery(sql);
		ArrayList<Book> al=new ArrayList<Book>();
		while(rs.next()){
			Book book=new Book();
			book.setId(rs.getInt(1));
			book.setName(rs.getString(2));
			book.setAuthor(rs.getString(3));
			book.setPrice(rs.getFloat(4));
			book.setPublisher(rs.getString(5));
			al.add(book);
		}
		return al;
	}
}
