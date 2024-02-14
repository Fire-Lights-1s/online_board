package user;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/UserRegisterServlet")
public class UserRegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		String userID = request.getParameter("userID");
		String userPassword = request.getParameter("userPassword");
		String userName = request.getParameter("userName");
		String userGender = request.getParameter("userGender");
		String userEmail = request.getParameter("userEmail");
	User user = new User();
	user.setUserID(userID);
	user.setUserPassword(userPassword);
	user.setUserName(userName);
	user.setUserGender(userGender);
	user.setUserEmail(userEmail);
		if(userID == null || userID.equals("") || userPassword == null || userPassword.equals("")
				|| userName == null || userName.equals("") || userGender == null || userGender.equals("") || userEmail == null || userEmail.equals("")) {
			request.getSession().setAttribute("messageType", "오류메시지");
			request.getSession().setAttribute("messageContent", "모든 내용을 입력하세요");
			response.sendRedirect("join.jsp");
			return;
			
		}
		int result = new UserDAO().join(user);
		if(result == 1) {
			request.getSession().setAttribute("messageType", "성공메시지");
			request.getSession().setAttribute("messageContent", "회원가입에 성공했습니다.");
			response.sendRedirect("index.jsp");
			request.getSession().setAttribute("UserID", userID);
		}
		else {
			request.getSession().setAttribute("messageType", "오류메시지");
			request.getSession().setAttribute("messageContent", "이미 존재하는 회원입니다.");
			response.sendRedirect("join.jsp");
		}

	}
}
