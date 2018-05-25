package com.example.chenm.rumian;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.accessibility.AccessibilityManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.zip.Inflater;

public class MessageDetailActivity extends AppCompatActivity {
    private int likeCheck = 0;
    private  String BaseURL = "http://10.7.84.109:8080/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        switch (intent.getIntExtra("messageType",Constant.ERROR)){
            case Constant.MESSAGE_ITEM_VIEW_TYPE_Message:
                View viewMessage = getLayoutInflater().inflate(R.layout.home_list_message_item,null);
                setMessageView(viewMessage,intent);
                setContentView(viewMessage);
                break;
            case Constant.MESSAGE_ITEM_VIEW_TYPE_IO:
                View viewIO = getLayoutInflater().inflate(R.layout.home_list_io_item,null);
                setIOView(viewIO,intent);
                setContentView(viewIO);
                break;
            case Constant.MESSAGE_ITEM_VIEW_TYPE_AO:
                View viewAO = getLayoutInflater().inflate(R.layout.home_list_ao_item,null);
                setAOView(viewAO,intent);
                setContentView(viewAO);
                break;
            case Constant.ERROR:
                setContentView(R.layout.error_page);
        }
    }

    private void setMessageView(View view, final Intent intent){
        TextView userName = view.findViewById(R.id.username);
        TextView pushTime = view.findViewById(R.id.push_time);
        TextView textContent  = view.findViewById(R.id.text_content);
        LinearLayout btnLike = view.findViewById(R.id.btn_like);
        final TextView likeCount = view.findViewById(R.id.like_count);
        LinearLayout btnComment = view.findViewById(R.id.btn_comment);
        TextView commentCount = view.findViewById(R.id.comment_count);
        LinearLayout btnShare = view.findViewById(R.id.btn_share);
        TextView shareCount = view.findViewById(R.id.share_count);
        ImageView userImage = view.findViewById(R.id.user_image);
        ImageView imgUrl = view.findViewById(R.id.Iv_message_image);

        setImages(intent.getStringExtra("userImage"),userImage,MessageDetailActivity.this);
        setImages(intent.getStringExtra("imgUrl"),imgUrl,MessageDetailActivity.this);
        userName.setText(intent.getStringExtra("username"));
        pushTime.setText(intent.getStringExtra("pushTime"));
        textContent.setText(intent.getStringExtra("textContent"));
        likeCount.setText(intent.getStringExtra("likeCount"));
        commentCount.setText(intent.getStringExtra("commentCount"));
        shareCount.setText(intent.getStringExtra("shareCount"));

        btnLike.setOnClickListener(new View.OnClickListener() {//点赞方法
            @Override
            public void onClick(View view) {
                switch (likeCheck){
                    case 0:
                        Toast.makeText(MessageDetailActivity.this,"你点了赞",Toast.LENGTH_SHORT).show();
                        likeCheck = 1;
                        break;
                    case 1:
                        Toast.makeText(MessageDetailActivity.this,"你取消了赞",Toast.LENGTH_SHORT).show();
                        likeCheck = 0;
                        break;
                }
            }
        });

        btnComment.setOnClickListener(new View.OnClickListener() {//评论功能
            @Override
            public void onClick(View view) {
                Toast.makeText(MessageDetailActivity.this,"你点击了评论",Toast.LENGTH_SHORT).show();

            }
        });

        btnShare.setOnClickListener(new View.OnClickListener() {//转发功能
            @Override
            public void onClick(View view) {
                Toast.makeText(MessageDetailActivity.this,"你点击了分享",Toast.LENGTH_SHORT).show();
            }
        });

//        View viewInclude = view.findViewById(R.id.list_include);
//        viewInclude.setVisibility(View.VISIBLE);

    }

    private void setIOView(View view, final Intent intent){
        setMessageView(view,intent);
        TextView orderTextContent = view.findViewById(R.id.order_text_content);
        TextView price = view.findViewById(R.id.price);
        TextView deadline = view.findViewById(R.id.deadline);
        LinearLayout btnAccept = view.findViewById(R.id.btn_accept);

        orderTextContent.setText(intent.getStringExtra("orderTextContent"));
        price.setText("￥"+intent.getStringExtra("price"));
        deadline.setText(intent.getStringExtra("deadline"));

        btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (intent.getIntExtra("accept",1) == 0){
                    Toast.makeText(MessageDetailActivity.this,"你点击了接单",Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(MessageDetailActivity.this,"此单不可接取",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void setAOView(View view, final Intent intent){
        setMessageView(view,intent);
        LinearLayout btnIssue = view.findViewById(R.id.btn_issue);
        final TextView issueCount = view.findViewById(R.id.issue_count);



        issueCount.setText(intent.getStringExtra("issueCount"));

        btnIssue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //此处逻辑错误,待修改
                Toast.makeText(MessageDetailActivity.this,"你点击了下单",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setImages(String url,ImageView imageView,Activity content){
        RequestOptions requestOptions = new RequestOptions()
                .error(R.drawable.errorimg);
        Glide.with(content)
                .load(BaseURL+url)
                .into(imageView);
    }

}
