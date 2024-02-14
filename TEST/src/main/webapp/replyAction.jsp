<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="reply.ReplyDAO"%>
<%@ page import="java.io.PrintWriter"%>
<%request.setCharacterEncoding("utf-8");%>

<jsp:useBean id="reply" class="reply.Reply" scope="page" /><!-- 현재 페이지에서만 User클래스를  javaBean로써 사용 -->
<jsp:setProperty name="reply" property="groupID" />
<jsp:setProperty name="reply" property="groupUserID" />
<jsp:setProperty name="reply" property="bbsID" />
<jsp:setProperty name="reply" property="replyContent" />

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>JSP 게시판 웹 사이트</title>
</head>
<body>
	<%
	String userID = null;
	if (session.getAttribute("userID") != null) {
		userID = (String) session.getAttribute("userID");
	}
	if (userID == null) {
		PrintWriter script = response.getWriter();
		script.println("<script>");
		script.println("alert('로그인을 하세요.')");
		script.println("location.href = 'login.jsp'");
		script.println("</script>");
	} else {
		if ( request.getParameter("replyContent") == null || request.getParameter("replyContent") == "") {

			PrintWriter script = response.getWriter();
			script.println("<script>");
			script.println("alert('입력이 안 된 사항이 있습니다.')");
			script.println("history.back()");
			script.println("</script>");
		} else {
			ReplyDAO replyDAO = new ReplyDAO();
			System.out.println("replyID - "+ reply.getReplyID());
			System.out.println("bbsID - "+ reply.getBbsID());
			System.out.println("userID - "+ userID);
			System.out.println("replyContent - "+ request.getParameter("replyContent"));
			System.out.println("userName - " + replyDAO.getUserName(userID));

			int result = replyDAO.write(reply.getGroupID(), reply.getBbsID() , userID, reply.getGroupUserID(),request.getParameter("replyContent"));
			if (result == -1) {
				PrintWriter script = response.getWriter();
				script.println("<script>");
				script.println("alert('글쓰기에 실패했습니다.')");
				script.println("history.back()");
				script.println("</script>");
			} else {
				PrintWriter script = response.getWriter();
				script.println("<script>");
				script.println("location.href = 'view.jsp?bbsID="+reply.getBbsID()+"'");
				script.println("</script>");
			}
					}
	}
	%>
</body>
</html>