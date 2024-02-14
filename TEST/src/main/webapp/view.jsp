<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.io.PrintWriter"%>
<%@ page import="bbs.Bbs"%>
<%@ page import="bbs.BbsDAO"%>
<%@ page import="reply.Reply"%>
<%@ page import="reply.ReplyDAO"%>
<%@ page import="java.util.ArrayList"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width" , initial-scale="1">
<link rel="stylesheet" href="css/bootstrap.css">


<title>JSP 게시판 웹 게시판</title>
</head>
<body>
	<%
	String userID = null;
	if (session.getAttribute("userID") != null) {
		userID = (String) session.getAttribute("userID");
	}
	int bbsID = 0;
	if (request.getParameter("bbsID") != null) {
		bbsID = Integer.parseInt(request.getParameter("bbsID"));
	}
	if (bbsID == 0) {
		PrintWriter script = response.getWriter();
		script.println("<script>");
		script.println("alert('유효하지 않은 글입니다.')");
		script.println("location.href = 'bbs.jsp'");
		script.println("</script>");
	}
	Bbs bbs = new BbsDAO().getBbs(bbsID);
	%>
	<script type="text/javascript">
		function update(replyID){
			
		}
		function cancel(name) {
			var reComment = document.getElementById(name);
			var parent = reComment.parentElement;
			parent.removeChild(reComment);

		}
		function reComment(replyID, groupID, bbsID, groupUserID) {
			if (document.getElementById('reComment') != null) {
				cancel("reComment");
			}
				var newReplyTr = document.createElement("tr")
				newReplyTr.setAttribute("id", "reComment");
				newReplyTr.setAttribute("name", 'reply' + replyID);
				newReplyTr.innerHTML = "<td></td>";
				
				var newReplyTd = document.createElement("td")
				newReplyTd.setAttribute("width", "80%");
				
				var reCommentForm = document.createElement("form")
				reCommentForm .setAttribute("method", "post");
				reCommentForm .setAttribute("action", "replyAction.jsp");
				
				reCommentForm.innerHTML = "<textarea id='reComment' class='form-control' placeholder='답글 내용'"
							+"name='replyContent' maxlength='1000' style='height: 70px; resize: none;'></textarea>"
						+ "<input type='text' name='groupID' value='" + groupID + "'>" 
						+ "<input type='text' name='groupUserID' value='" + groupUserID + "'>" 
						+ "<input type='text' name='bbsID' value='" + bbsID + "'>" 
						+ "<input type='submit' class='btn btn-primary pull-right' value='답글 작성'>"
						+ "<a class='btn btn-primary pull-right' href='javascript:void(0);' name='reComment' onclick='cancel(this.name);' >취소</a>";
				newReplyTd.appendChild(reCommentForm);
				newReplyTr.appendChild(newReplyTd);
						
				var reCommentLocation = document.getElementById('reply' + replyID);
				reCommentLocation.appendChild(newReplyTr);
			
		}
	</script>
	<nav class="navbar navbar-default">
		<div class="navbar-header">
			<button type="button" class="navbar-toggle collapsed"
				data-toggle="collapse" data-target="#bs-example-navbar-collapse-1"
				aria-expanded="false">
				<span class="icon-bar"></span>
				<!-- 메뉴 오른쪽 아이콘 바 -->
				<span class="icon-bar"></span> <span class="icon-bar"></span>
			</button>
			<a class="navbar-brand" href="main.jsp">JSP 교육용 게시판</a>
		</div>
		<div class="collapse navbar-collapse"
			id="bs-example-navbar-collapse-1">
			<ul class="nav navbar-nav">
				<!-- active 현제 접속한곳을 알려줌 -->
				<li><a href="main.jsp">메인</a></li>
				<li class="active"><a href="bbs.jsp">게시판</a></li>
			</ul>
			<%
			if (userID == null) {
			%>
			<ul class="nav navbar-nav navbar-right">
				<li class="dropdown">
					<!-- #은 가리키는 링크가 없다는 의미 --> <a href="#" class="dropdown-toggle"
					data-toggle="dropdown" role="button" aria-haspopup="true"
					aria-expanded="false">접속하기<span class="caret"></span></a>
					<ul class="dropdown-menu">
						<li><a href="login.jsp">로그인</a></li>
						<li><a href="join.jsp">회원가입</a></li>
						<li><a href="chat.jsp">메신저</a></li>
					</ul>
				</li>
			</ul>

			<%
			} else {
			%>
			<ul class="nav navbar-nav navbar-right">
				<li class="dropdown">
					<!-- #은 가리키는 링크가 없다는 의미 --> <a href="#" class="dropdown-toggle"
					data-toggle="dropdown" role="button" aria-haspopup="true"
					aria-expanded="false">회원관리<span class="caret"></span></a>
					<ul class="dropdown-menu">
						<li><a href="logoutAction.jsp">로그아웃</a></li>
					</ul>
				</li>
			</ul>
			<%
			}
			%>
		</div>
	</nav>
	<div class="container">
		<div class="row">
			<table class="table table-striped"
				style="text-align: center; border: 1px solid #dddddd">
				<thead>
					<tr>
						<!-- colspan 열을 몇 줄만큼 차지 할지 -->
						<th colspan="3"
							style="background-color: #eeeeee; text-align: center;">게시판</th>

					</tr>
				</thead>
				<tbody>
					<tr>
						<td colspan="3" style="text-align: center;"><%=bbs.getBbsTitle()
							.replaceAll("<", "&lt")
							.replaceAll(">", "&gt")
							.replaceAll("\n", "<br>")%>
						</td>
					</tr>
					<tr>
						<td colspan="2"><%=bbs.getUserID()%></td>
						<!-- 작성자 -->
						<td colspan="2"><%=bbs.getBbsDate()
							.substring(0, 11) + bbs.getBbsDate().substring(11, 13) + "시"
							+ bbs.getBbsDate().substring(14, 16) + "분"%>
						</td>
						<!-- 작성일 -->
					</tr>
					<tr>
						<td colspan="3" style="min-height: 200px; text-align: left;">
							<%=bbs.getBbsContent()
								.replaceAll(" ", "&nbsp;")
								.replaceAll("<", "&lt")
								.replaceAll(">", "&gt")
								.replaceAll("\n",
								"<br>")%>
						</td>
					</tr>
				</tbody>
			</table>
			<table class="table table-striped"
				style="text-align: center; border: 1px solid #dddddd">
				<thead>
					<tr>
						<!-- colspan 열을 몇 줄만큼 차지 할지 -->
						<th colspan="2"
							style="background-color: #eeeeee; text-align: center;">댓글 목록</th>

					</tr>
				</thead>
				<%
				ReplyDAO replyDAO = new ReplyDAO();
				ArrayList<Reply> replyList = replyDAO.getList(bbsID, 0);
				int replyID;
				for (int i = 0; i < replyList.size(); i++) {
					String replyUserID = replyList.get(i).getReplyUserID();
					replyID = replyList.get(i).getReplyID();
				%>
				<tbody id="reply<%=replyID%>">
					<tr>
						<td style="text-align: left;" colspan="2"><%=replyList.get(i).getReplyUserName()
							.replaceAll("<", "&lt")
							.replaceAll(">", "&gt")
							.replaceAll("\n", "<br>")%>(<%=replyUserID%>)<br>
							<%=replyList.get(i).getReplyContent()%><br> 
							<%=replyList.get(i).getReplyDate().substring(0, 11) 
							+ replyList.get(i).getReplyDate().substring(11, 13) + "시"
							+ replyList.get(i).getReplyDate().substring(14, 16) + "분"%> 
							<a href="javascript:void(0);" id="<%=replyID%>"
								onclick="reComment(this.id,<%=replyID%>,<%=bbsID%>,'');">답글</a> 
							<%
 							if (userID != null && userID.equals(replyUserID)) {
 							%> 
 							<a href="replyUpdate.jsp?bbsID=<%=bbsID%>">수정</a> 
 							<a href="replydelete.jsp?bbsID=<%=bbsID%>"
								onclick="return confirm('정말로 삭제하시겠습니까?')">삭제</a> 
							<%
							 }
 							%>
 						</td>
					</tr>
				</tbody>
				<%
				//댓글ID를 groupID로 같은 댓글을 확인하고 출력
					if (replyDAO.getGroupID(replyID) == -1) {
						PrintWriter script = response.getWriter();
						script.println("<script>");
						script.println("alert('SQL 오류.')");
						script.println("location.href = 'bbs.jsp'");
						script.println("</script>");
					}
					if (replyDAO.getGroupID(replyID) != 0) {
						ArrayList<Reply> reComent = replyDAO.getList(bbsID, replyID);
					
					for (int j = 0; j < reComent.size(); j++) {
						replyUserID = reComent.get(j).getReplyUserID();
						int reComentID = reComent.get(j).getReplyID();
				%>
				<tbody id="reply<%=reComentID%>">
					<tr>
						<td colspan="1" width="5%"></td>
						<td style="text-align: left;"><%=reComent.get(j).getReplyUserName()
							.replaceAll("<", "&lt")
							.replaceAll(">", "&gt")
							.replaceAll("\n", "<br>")%>(<%=replyUserID%>)
							<%if( !replyDAO.getGroupUserName(reComent.get(j).getGroupUserID()).equals(null)&&
									!replyDAO.getGroupUserName(reComent.get(j).getGroupUserID()).equals("")){%>
								▶<%= reComent.get(j).getGroupUserID()%><%=replyDAO.getGroupUserName(reComent.get(j).getGroupUserID())%>
							<%} %><br>
							<%=reComent.get(j).getReplyContent()%><br> 
							<%=reComent.get(j).getReplyDate().substring(0, 11) 
							+ reComent.get(j).getReplyDate().substring(11, 13) + "시"
							+ reComent.get(j).getReplyDate().substring(14, 16) + "분"%> 
							<a href="javascript:void(0);" id="<%=reComentID%>"
								onclick="reComment(this.id,<%=replyID%>,<%=bbsID%>,'<%=replyUserID%>');">답글</a> 
							<%
 							if (userID != null && userID.equals(replyUserID)) {
 							%> 
 								<a href="replyUpdate.jsp?bbsID=<%=bbsID%>" >수정</a>
								<a href="replydelete.jsp?bbsID=<%=bbsID%>"
									onclick="return confirm('정말로 삭제하시겠습니까?')" >삭제</a>
							<%
							}
							%></td>
					</tr>
				</tbody>
				<%
						}
					}
				}
				%>
				<form method="post" id='reply' action="replyAction.jsp">
					<input type="hidden" name="groupID" value="0"> 
					<input type="hidden" name="groupUserID" value=""> 
					<input type="hidden" name="bbsID" value="<%=bbsID%>">
					<table class="table table-striped">
						<tr>
							<td colspan="1" width="90%">
								<textarea id="reply"
									class="form-control" placeholder="댓글 내용" name="replyContent"
									maxlength="1000" style="height: 70px; resize: none;"></textarea>
							</td>
							<td>
								<input type="submit" class="btn btn-primary" value="댓글 작성">
							</td>
						</tr>
					</table>
				</form>
				
			<a href="bbs.jsp" class="btn btn-primary">목록</a>
			<%
			if (userID != null && userID.equals(bbs.getUserID())) {
			%>
			<a href="update.jsp?bbsID=<%=bbsID%>" class="btn btn-primary">수정</a>
			<a onclick="return confirm('정말로 삭제하시겠습니까?')"
				href="deleteAction.jsp?bbsID=<%=bbsID%>" class="btn btn-primary">삭제</a>
			<%
			}
			%>
		</div>
	</div>
	<script src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
	<script src="js/bootstrap.js"></script>
</body>
</html>