package reply;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import bbs.Bbs;

public class ReplyDAO {
	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;

	public ReplyDAO() {
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
	public String getDate() {
		String SQL = "SELECT NOW()";
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				return rs.getString(1);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return "";
	}
	public int getNextReply() {//현제 작성되는 댓글의 번호를 만들기 위해 필요
		String SQL = "SELECT replyID FROM REPLY ORDER BY replyID DESC";
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				return rs.getInt(1) + 1;
			}
			return 1;
		}catch(Exception e) {
			e.printStackTrace();
		}
		return -1;
	}
	public String getGroupUserName(String groupUserID) {//답글에 답글을 달때 누구에게 다는것인지 표시
		String SQL = "SELECT userName FROM USER WHERE userID = ?";
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, groupUserID);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				return rs.getString(1);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return "";
	}
	
	public String getUserName(String userID) {
		String SQL = "SELECT userName FROM USER WHERE userID = ? ";
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, userID);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				return rs.getString(1);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return "";
		
	}
	public int getGroupID(int replyID) {//댓글 ID를 받아서 답글이 있는지 확인
		String SQL = "SELECT groupID FROM REPLY WHERE groupID = ? ";
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, replyID);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				return rs.getInt(1);
			}
			return 0;
		}catch(Exception e) {
			e.printStackTrace();
		}
		return -1;
		
	}
	public int write(int groupID,int bbsID, String userID, String groupUserID, String replyContent) {//댓글 작성할때 쓸거
		String SQL = "INSERT INTO REPLY VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		int groupDeep=0;
		try {
			if(groupID!=0) {
				groupDeep=1;
			}
			if(groupUserID==null || groupUserID.equals("")) {
				groupUserID=null;
			}
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, getNextReply());
			pstmt.setInt(2, bbsID);
			pstmt.setInt(3, groupID);//groupID
			pstmt.setString(4, groupUserID);//groupSquence
			pstmt.setInt(5, groupDeep);//groupDeep
			pstmt.setString(6, userID);//replyUser
			pstmt.setString(7, getUserName(userID));//replyUserName
			pstmt.setString(8, getDate());
			pstmt.setString(9, replyContent);
			pstmt.setInt(10, 1);
			return pstmt.executeUpdate();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return -1;
	}
	public ArrayList<Reply> getList(int bbsID, int groupID){ //댓글 목록 출력
		String SQL = "SELECT * FROM REPLY WHERE bbsID = ? AND groupID = ? AND replyAvailable = 1 ORDER BY replyID ASC ";
		ArrayList<Reply> list = new ArrayList<Reply>();
		
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			//
			pstmt.setInt(1, bbsID);
			pstmt.setInt(2, groupID);
			
			rs = pstmt.executeQuery();
			while(rs.next()) {
				Reply reply = new Reply();
				reply.setReplyID(rs.getInt(1));
				reply.setBbsID(rs.getInt(2));
				reply.setGroupID(rs.getInt(3));
				reply.setGroupUserID(rs.getString(4));
				reply.setGroupDeep(rs.getInt(5));
				reply.setReplyUserID(rs.getString(6));
				reply.setReplyUserName(rs.getString(7));
				reply.setReplyDate(rs.getString(8));
				reply.setReplyContent(rs.getString(9));
				reply.setReplyAvailable(rs.getInt(10));
				list.add(reply);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	public Reply getReply(int replyID) {
		String SQL = "SELECT * FROM REPLY WHERE replyID = ?";
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, replyID);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				Reply reply = new Reply();
				reply.setReplyID(rs.getInt(1));
				reply.setBbsID(rs.getInt(2));
				reply.setGroupID(rs.getInt(3));
				reply.setGroupUserID(rs.getString(4));
				reply.setGroupDeep(rs.getInt(5));
				reply.setReplyUserID(rs.getString(6));
				reply.setReplyUserName(rs.getString(7));
				reply.setReplyDate(rs.getString(8));
				reply.setReplyContent(rs.getString(9));
				reply.setReplyAvailable(rs.getInt(10));
				return reply;
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	public int update(int replyID, String replyContent) {
		String SQL = "UPDATE REPLY SET replyContent = ? WHERE replyID = ?";
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, replyContent);
			pstmt.setInt(2, replyID);
			return pstmt.executeUpdate();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return -1;
		
	}
	public int delete(int replyID) {
		String SQL = "UPDATE REPLY SET replyAvailable = 0 WHERE replyID = ?";
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, replyID);
			return pstmt.executeUpdate();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return -1;//데이터 베이스 오류
	}
	
}
