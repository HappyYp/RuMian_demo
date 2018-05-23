package com.example.chenm.rumian;


import java.util.List;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

public class Message extends RealmObject{
    @PrimaryKey
    private int messageId;
    @Required
    //0:message
    //1:AOMessage
    //2:IOMessage
    private String userImage;
    //暂时不做图片相关操作

    private String imgUrl;
    private String userName;
    private String pushTime;
    private String textContent;
    private String likeCount;
    private String commentCount;
    private String shareCount;
    private String issueCount;//其取值小于等于库存时，方可点按
    private String deadline;
    private String price;
    private String orderTextContent;
    private String acceptCount;//其取值仅为0或1，分别表示可点按或不可点按

    private int messageType;//其取值为0，1，2，分表表示三种不同的消息类型

//    public Message(int messageType,
//                   String userName,
//                   String pushTime,
//                   String textContent,
//                   String likeCount,
//                   String commentCount,
//                   String shareCount,
//                   String issueCount,
//                   String deadline,
//                   String price,
//                   String orderTextContent,
//                   String acceptCount){
//        this.messageType = messageType;
//        this.userName = userName;
//        this.pushTime = pushTime;
//        this.textContent = textContent;
//        this.likeCount = likeCount;
//        this.commentCount = commentCount;
//        this.shareCount = shareCount;
//        this.issueCount = issueCount;
//        this.deadline = deadline;
//        this.price = price;
//        this.orderTextContent = orderTextContent;
//        this.acceptCount = acceptCount;
//    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
    public int getMessageId() {
        return messageId;
    }

    public void setMessageId(int messageId) {
        this.messageId = messageId;
    }

    public String getUserImage() {
        return userImage;
    }

    public void setUserImage(String userImage) {
        this.userImage = userImage;
    }
    public int getMessageType() {
        return messageType;
    }

    public void setMessageType(int messageType) {
        this.messageType = messageType;
    }

//    public String getUserImage() {
//        return userImage;
//    }
//
//    public void setUserImage(String userImage) {
//        this.userImage = userImage;
//    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPushTime() {
        return pushTime;
    }

    public void setPushTime(String pushTime) {
        this.pushTime = pushTime;
    }

    public String getTextContent() {
        return textContent;
    }

    public void setTextContent(String textContent) {
        this.textContent = textContent;
    }

    public String getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(String likeCount) {
        this.likeCount = likeCount;
    }

    public String getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(String commentCount) {
        this.commentCount = commentCount;
    }

    public String getShareCount() {
        return shareCount;
    }

    public void setShareCount(String shareCount) {
        this.shareCount = shareCount;
    }

    public String getIssueCount() {
        return issueCount;
    }

    public void setIssueCount(String issueCount) {
        this.issueCount = issueCount;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getOrderTextContent() {
        return orderTextContent;
    }

    public void setOrderTextContent(String orderTextContent) {
        this.orderTextContent = orderTextContent;
    }

    public String getAcceptCount() {
        return acceptCount;
    }

    public void setAcceptCount(String acceptCount) {
        this.acceptCount = acceptCount;
    }
}
