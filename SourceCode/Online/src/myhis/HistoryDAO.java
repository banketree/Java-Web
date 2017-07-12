package myhis;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

import javax.naming.NamingException;
import db.DbLib;
import myvisit.Visitor;


public class HistoryDAO {
	Connection conn;
	PreparedStatement ps;
	String sql;
	//����ҳ��,����setter��getter(count����set)
	int pageSize;
	int pageNo;
	int pageCount;
	
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getPageNo() {
		return pageNo;
	}
	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}
	public int getPageCount() {
		return pageCount;
	}
	
	public HistoryDAO() throws NamingException, SQLException{
		conn=DbLib.getConnection();
		sql="use visitor";   //������visitor��
		ps=conn.prepareStatement(sql);
		ps.executeUpdate();
	}
	//���������û�
	public void saveHistory(History history) throws SQLException, NamingException{
		sql="insert into history(visitid,visittime,url) value(?,?,?)"; //id����д�����Զ�����
		if(conn.isClosed()){			
			conn=DbLib.getConnection();
		}
		ps=conn.prepareStatement(sql);
		ps.setInt(1, history.getVisitId());
		ps.setTimestamp(2, new Timestamp(history.getVisitTime().getTime()));
		ps.setString(3,history.getUrl());
		
		ps.executeUpdate();  //ִ��������id
		conn.close();    //�ر���Դ
	}
	//��ȡ�����û���Ϣ
	public ArrayList<History> getHistory() throws SQLException, NamingException{
		sql="select * from history";
		if(conn.isClosed()){			
			conn=DbLib.getConnection();
		}			
		ps=conn.prepareStatement(sql);
		ResultSet rs=ps.executeQuery();
		ArrayList<History> al=new ArrayList<History>();
		while(rs.next()){
			History his=new History();
			his.setId(rs.getInt(1));
			his.setVisitId(rs.getInt(2));
			his.setVisitTime(rs.getTimestamp(3));
			his.setUrl(rs.getString(4));
			al.add(his);
		}
		conn.close();
		return al;
	}
	//�½�computePageCount
	public void computePgeCount() throws SQLException, NamingException{
		//�½�sql
		sql="select count(*) from history";
		if(conn.isClosed()){			
			conn=DbLib.getConnection();
		}
		//��װsql���
		ps=conn.prepareStatement(sql);
		//ִ��sql��䷵�ؽ��
		ResultSet rs=ps.executeQuery();
		//ָ����һ��
		rs.next();
		//��ȡ��һ������ʱ��pageCount
		pageCount=rs.getInt(1);
		//������ҳ���������ò���+1
		if(pageCount%2==0){
			pageCount=pageCount/pageSize;
		}else{
			pageCount=pageCount/pageSize+1;
		}
		//�ر�����
		conn.close();
	}
	
	//�½���ȡ��ҳ���ݺ���
	//���Ϸ�sql���ģ�select * from history limit"+(pageNo-1)*pageSize+","+pageSize;
	public ArrayList<History> getPageDate() throws SQLException, NamingException{
		//sql="select * from history limit"+(pageNo-1)*pageSize+","+pageSize;  //�ӵ�n����ʼ����ȡm��
		sql="select * from history limit 10,10";  //�ӵ�n����ʼ����ȡm��!!!!!��ѯ������

		if(conn.isClosed()){			
			conn=DbLib.getConnection();
		}			
		ps=conn.prepareStatement(sql);
		ResultSet rs=ps.executeQuery();
		ArrayList<History> al=new ArrayList<History>();
		while(rs.next()){
			History his=new History();
			his.setId(rs.getInt(1));
			his.setVisitId(rs.getInt(2));
			his.setVisitTime(rs.getTimestamp(3));
			his.setUrl(rs.getString(4));
			al.add(his);
		}
		conn.close();
		return al;
	}
}
