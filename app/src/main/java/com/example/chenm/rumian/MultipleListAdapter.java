package com.example.chenm.rumian;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

public class MultipleListAdapter extends BaseAdapter {
    private  String BaseURL = "http://10.7.84.109:8080/";

    private List<Message> messageList;
    private Activity activity;

    public MultipleListAdapter(Activity activity, List<Message> messages){
        this.activity = activity;
        messageList = messages;
    }
    @Override
    public int getCount() {
        return messageList.size();
    }

    @Override
    public Object getItem(int i) {
        return messageList.get(i);
    }

    @Override
    public int getItemViewType(int position) {
        return messageList.get(position).getMessageType();
    }

    @Override
    public int getViewTypeCount() {
        return 3;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Message message = (Message) getItem(i);
        int itemType = this.getItemViewType(i);
        if (itemType == Constant.MESSAGE_ITEM_VIEW_TYPE_Message){
            MessageViewHolder messageViewHolder;
            if (view == null){
                //未缓存过
                messageViewHolder = new MessageViewHolder();
                view = activity.getLayoutInflater().inflate(R.layout.home_list_message_item,null);
                //messageViewHolder.userImage = viewMessage.findViewById(R.id.user_image);
                messageViewHolder.userName = view.findViewById(R.id.username);
                messageViewHolder.pushTime = view.findViewById(R.id.push_time);
                messageViewHolder.textContent = view.findViewById(R.id.text_content);
                messageViewHolder.likeCount = view.findViewById(R.id.like_count);
                messageViewHolder.commentCount = view.findViewById(R.id.comment_count);
                messageViewHolder.shareCount = view.findViewById(R.id.share_count);
                messageViewHolder.btnLike = view.findViewById(R.id.btn_comment);
                messageViewHolder.btnComment = view.findViewById(R.id.btn_comment);
                messageViewHolder.btnShare = view.findViewById(R.id.btn_share);
                messageViewHolder.messageImage = view.findViewById(R.id.Iv_message_image);
                messageViewHolder.userImage = view.findViewById(R.id.user_image);
                view.setTag(messageViewHolder);
            }else {
                messageViewHolder = (MessageViewHolder) view.getTag();
            }
            setImages(message.getUserImage(),messageViewHolder.userImage,activity);
            setImages(message.getImgUrl(),messageViewHolder.messageImage,activity);

            messageViewHolder.userName.setText(message.getUserName());
            messageViewHolder.pushTime.setText(message.getPushTime());
            messageViewHolder.textContent.setText(message.getTextContent());
            messageViewHolder.likeCount.setText(message.getLikeCount());
            messageViewHolder.commentCount.setText(message.getCommentCount());
            messageViewHolder.shareCount.setText(message.getShareCount());
        }else if (itemType == Constant.MESSAGE_ITEM_VIEW_TYPE_AO){
            AOViewHolder aoViewHolder;
            if (view == null){
                aoViewHolder = new AOViewHolder();
                view = activity.getLayoutInflater().inflate(R.layout.home_list_ao_item,null);
                aoViewHolder.userName = view.findViewById(R.id.username);
                aoViewHolder.pushTime = view.findViewById(R.id.push_time);
                aoViewHolder.commentCount = view.findViewById(R.id.comment_count);
                aoViewHolder.issueCount = view.findViewById(R.id.issue_count);
                aoViewHolder.likeCount = view.findViewById(R.id.like_count);
                aoViewHolder.shareCount = view.findViewById(R.id.share_count);
                aoViewHolder.textContent = view.findViewById(R.id.text_content);
                aoViewHolder.btnComment = view.findViewById(R.id.btn_comment);
                aoViewHolder.btnIssue = view.findViewById(R.id.btn_issue);
                aoViewHolder.btnLike = view.findViewById(R.id.btn_like);
                aoViewHolder.btnShare = view.findViewById(R.id.btn_share);
                aoViewHolder.messageImage = view.findViewById(R.id.Iv_message_image);
                aoViewHolder.userImage = view.findViewById(R.id.user_image);
                view.setTag(aoViewHolder);
            }else {
                aoViewHolder = (AOViewHolder) view.getTag();
            }
            setImages(message.getUserImage(),aoViewHolder.userImage,activity);
            setImages(message.getImgUrl(),aoViewHolder.messageImage,activity);

            aoViewHolder.userName.setText(message.getUserName());
            aoViewHolder.textContent.setText(message.getTextContent());
            aoViewHolder.shareCount.setText(message.getShareCount());
            aoViewHolder.likeCount.setText(message.getLikeCount());
            aoViewHolder.issueCount.setText(message.getIssueCount());
            aoViewHolder.pushTime.setText(message.getPushTime());
            aoViewHolder.commentCount.setText(message.getCommentCount());
        }else if (itemType == Constant.MESSAGE_ITEM_VIEW_TYPE_IO){
            IOViewHolder ioViewHolder;
            if (view == null){
                ioViewHolder = new IOViewHolder();
                view = activity.getLayoutInflater().inflate(R.layout.home_list_io_item,null);
                ioViewHolder.userName = view.findViewById(R.id.username);
                ioViewHolder.commentCount = view.findViewById(R.id.comment_count);
                ioViewHolder.deadline = view.findViewById(R.id.deadline);
                ioViewHolder.likeCount = view.findViewById(R.id.like_count);
                ioViewHolder.textContent = view.findViewById(R.id.text_content);
                ioViewHolder.orderTextContent = view.findViewById(R.id.order_text_content);
                ioViewHolder.price = view.findViewById(R.id.price);
                ioViewHolder.pushTime = view.findViewById(R.id.push_time);
                ioViewHolder.shareCount = view.findViewById(R.id.share_count);
                ioViewHolder.btnAccept = view.findViewById(R.id.btn_accept);
                ioViewHolder.btnComment = view.findViewById(R.id.btn_comment);
                ioViewHolder.btnLike = view.findViewById(R.id.btn_like);
                ioViewHolder.btnShare = view.findViewById(R.id.btn_share);
                ioViewHolder.userImage = view.findViewById(R.id.user_image);
                view.setTag(ioViewHolder);
            }else {
                ioViewHolder = (IOViewHolder) view.getTag();
            }
            setImages(message.getImgUrl(),ioViewHolder.userImage,activity);

            ioViewHolder.userName.setText(message.getUserName());
            ioViewHolder.commentCount.setText(message.getCommentCount());
            ioViewHolder.deadline.setText(message.getDeadline());
            ioViewHolder.likeCount.setText(message.getLikeCount());
            ioViewHolder.textContent.setText(message.getTextContent());
            ioViewHolder.orderTextContent.setText(message.getOrderTextContent());
            ioViewHolder.price.setText("￥"+message.getPrice());
            ioViewHolder.pushTime.setText(message.getPushTime());
            ioViewHolder.shareCount.setText(message.getShareCount());

        }

        return view;
    }

    class MessageViewHolder{
        ImageView messageImage;
        ImageView userImage;
        TextView userName;
        TextView pushTime;
        TextView textContent;
        TextView likeCount;
        TextView commentCount;
        TextView shareCount;
        LinearLayout btnLike;
        LinearLayout btnComment;
        LinearLayout btnShare;
    }
    class AOViewHolder{
        ImageView messageImage;
        ImageView userImage;
        TextView userName;
        TextView pushTime;
        TextView textContent;
        TextView likeCount;
        TextView commentCount;
        TextView shareCount;
        TextView issueCount;//其取值小于等于库存时，方可点按
        LinearLayout btnLike;
        LinearLayout btnComment;
        LinearLayout btnShare;
        LinearLayout btnIssue;
    }
    class IOViewHolder{
        ImageView userImage;
        TextView userName;
        TextView pushTime;
        TextView deadline;
        TextView price;
        TextView orderTextContent;
        TextView textContent;
        TextView likeCount;
        TextView commentCount;
        TextView shareCount;
        //int acceptCount;//其取值仅为0或1，分别表示可点按或不可点按
        LinearLayout btnLike;
        LinearLayout btnComment;
        LinearLayout btnShare;
        LinearLayout btnAccept;
    }

    private void setImages(String url,ImageView imageView,Activity content){
        RequestOptions requestOptions = new RequestOptions()
                .error(R.drawable.errorimg);
        Glide.with(content)
                .load(BaseURL+url)
                .into(imageView);
    }

}
