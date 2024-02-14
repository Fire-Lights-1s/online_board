package reply;

public class Reply {
	private int replyID;
	private int bbsID;
	private int groupID;
	private String groupUserID;
	private int groupDeep;
	private String replyUserID;
	private String replyUserName;
	private String replyDate;
	private String replyContent;
	private int replyAvailable;

	public int getReplyID() {
		return replyID;
	}
	public void setReplyID(int replyID) {
		this.replyID = replyID;
	}
	public int getBbsID() {
		return bbsID;
	}
	public void setBbsID(int bbsID) {
		this.bbsID = bbsID;
	}
	public int getGroupID() {
		return groupID;
	}
	public void setGroupID(int groupNo) {
		this.groupID = groupNo;
	}
	public String getGroupUserID() {
		return groupUserID;
	}
	public void setGroupUserID(String groupUserID) {
		this.groupUserID = groupUserID;
	}
	public int getGroupDeep() {
		return groupDeep;
	}
	public void setGroupDeep(int groupDeep) {
		this.groupDeep = groupDeep;
	}
	public String getReplyUserID() {
		return replyUserID;
	}
	public void setReplyUserID(String replyUserID) {
		this.replyUserID = replyUserID;
	}
	public String getReplyUserName() {
		return replyUserName;
	}
	public void setReplyUserName(String replyUserName) {
		this.replyUserName = replyUserName;
	}
	public String getReplyDate() {
		return replyDate;
	}
	public void setReplyDate(String replyDate) {
		this.replyDate = replyDate;
	}
	public String getReplyContent() {
		return replyContent;
	}
	public void setReplyContent(String replyContent) {
		this.replyContent = replyContent;
	}
	public int getReplyAvailable() {
		return replyAvailable;
	}
	public void setReplyAvailable(int replyAvailable) {
		this.replyAvailable = replyAvailable;
	}
}
