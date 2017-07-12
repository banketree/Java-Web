package db;

import java.sql.*;

import javax.naming.NamingException;

import java.io.*;

public class InitDB {
	public static void initialize(String scriptFile) throws NamingException, SQLException, IOException{
		Connection conn=DbLib.getConnection();
		Statement st=conn.createStatement();   //���ݿ����
		FileReader fr=new FileReader(scriptFile);
		BufferedReader br=new BufferedReader(fr);
		
		String sql="";
		String line="";
		while((line=br.readLine())!=null){
			if(!line.endsWith(";")){   //�ǣ���β���������
				sql=sql+line;
				continue;
			}
			sql=sql+line;
			st.addBatch(sql);
			sql="";     //���sql������
		}
		st.executeBatch();   //ִ�����
		br.close();   //�رճ���
	}
}
