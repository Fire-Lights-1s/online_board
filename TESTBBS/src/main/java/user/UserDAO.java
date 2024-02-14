package user;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserDAO {
	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;
	
	public UserDAO() {
		try {
			String dbURL = "jdbc:mysql://localhost:3306/bbs";
			String dbID = "root";
			String dbPassword = "0000";
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("����̺� ���� ����");
			conn = DriverManager.getConnection(dbURL,dbID, dbPassword);
			System.out.println("DB���� ����");
			
		} catch(ClassNotFoundException e){
			System.out.println("����̹��� ã�� �� �����ϴ�");
		}catch(Exception e){
			System.out.println("���ῡ �����Ͽ����ϴ�");
		}
	}
	public int login(String userID, String userPassword) {
		System.out.println(userID);
		System.out.println(userPassword);
		String SQL = "SELECT userPassword FROM USER WHERE userID = ?";
		try{
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, userID);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				if(rs.getString(1).equals(userPassword))
					return 1;//�α��� ����
				else
					return 0;//�α��� ����
			}
			return -1;//���̵� ����
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		return -2;//�����ͺ��̽� ����
	}
	public int join(User user) {
		String SQL = "INSERT INTO USER VALUES (?, ?, ?, ?, ?)";
		try {
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, user.getUserID());
			pstmt.setString(2, user.getUserPassword());
			pstmt.setString(3, user.getUserName());
			pstmt.setString(4, user.getUserGender());
			pstmt.setString(5, user.getUserEmail());
			return pstmt.executeUpdate();
		}catch(Exception e){
			e.printStackTrace();
		}
		return -1; //�����ͺ��̽� ����
	}
}
