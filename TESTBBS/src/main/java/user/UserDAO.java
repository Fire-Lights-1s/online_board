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
			System.out.println("드라이브 적재 성공");
			conn = DriverManager.getConnection(dbURL,dbID, dbPassword);
			System.out.println("DB연결 성공");
			
		} catch(ClassNotFoundException e){
			System.out.println("드라이버를 찾을 수 없습니다");
		}catch(Exception e){
			System.out.println("연결에 실패하였습니다");
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
					return 1;//로그인 성공
				else
					return 0;//로그인 실패
			}
			return -1;//아이디 없음
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		return -2;//데이터베이스 오류
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
		return -1; //데이터베이스 오류
	}
}
