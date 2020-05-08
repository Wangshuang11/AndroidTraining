package org.turings.near.entity;

public class ReplyDetailBean {
    private String nickName;
    private String userLogo;
    private int id;
    private int commentId;
    private String content;
    private String status;
    private String createDate;

    public ReplyDetailBean(String nickName, String content) {
        this.nickName = nickName;
        this.content = content;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
    public String getNickName() {
        return nickName;
    }

    public void setUserLogo(String userLogo) {
        this.userLogo = userLogo;
    }
    public String getUserLogo() {
        return userLogo;
    }

    public void setId(int id) {
        this.id = id;
    }
    public int getId() {
        return id;
    }

    public void setCommentId(int commentId) {
        this.commentId = commentId;
    }
    public int getCommentId() {
        return commentId;
    }

    public void setContent(String content) {
        this.content = content;
    }
    public String getContent() {
        return content;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    public String getStatus() {
        return status;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }
    public String getCreateDate() {
        return createDate;
    }

	@Override
	public String toString() {
		return "ReplyDetailBean [nickName=" + nickName + ", userLogo=" + userLogo + ", id=" + id + ", commentId="
				+ commentId + ", content=" + content + ", status=" + status + ", createDate=" + createDate + "]";
	}
}
