package myuser;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.NamingException;

import db.DbLib;

public class UserDAO {
	Connection conn;
	PreparedStatement ps;
	String sql;
	public UserDAO() throws NamingException, SQLException{
		conn=DbLib.getConnection();
		sql="use visitor";   //������visitor��
		ps=conn.prepareStatement(sql);
		ps.executeUpdate();
	}
	
	public int getUserId(User user) throws SQLException, NamingException{
		if(conn.isClosed()){
			conn=DbLib.getConnection();
		}
		sql="select id from users where username=? and pwd=?";
		ps=conn.prepareStatement(sql);
		ps.setString(1, user.userName); //�����û���
		ps.setString(2, user.pwd); //�����û�����
		ResultSet rs=ps.executeQuery(); //��ȡִ�н��
		int id=0;
		if(rs.next()){ //�ж���������
			id=rs.getInt(1);   //ȡ��һ��
		}
		conn.close(); 
		return id;   //�������0����true
	} 
	//����id��ȡ����
	public String getNameById(int id) throws SQLException, NamingException{
		if(conn.isClosed()){			
			conn=DbLib.getConnection();
		}
		sql="select username from users where id=?";
		ps=conn.prepareStatement(sql);
		ps.setInt(1, id);
		ResultSet rs=ps.executeQuery();
		String name=null;
		if(rs.next()){
			name=rs.getString(1);  //��ȡ��һ��		
		}
		conn.close();
		return name;
	}
	//ͨ��������id
	public int getIdByName(String name) throws SQLException, NamingException{
		if(conn.isClosed()){			
			conn=DbLib.getConnection();
		}
		sql="select id from users where username=?";
		ps=conn.prepareStatement(sql);
		ps.setString(1, name);
		ResultSet rs=ps.executeQuery();
		int id=0;
		if(rs.next()){
			id=rs.getInt(1);  //��ȡ��һ��		
		}
		conn.close();
		return id;
	}
	
}
