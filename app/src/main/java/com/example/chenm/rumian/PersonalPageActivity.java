package com.example.chenm.rumian;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class PersonalPageActivity extends AppCompatActivity {

    private MultipleListAdapter personAdapter;
    private ListView lv_persons;
    private View vHead;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_nav_personal_page);

        vHead=View.inflate(this,R.layout.item_main,null);

        ImageView imageView=vHead.findViewById(R.id.image1);

        Resources resources=getResources();
        Bitmap bitmap= BitmapFactory.decodeResource(resources,R.drawable.account);

        Bitmap bitmap1=makeRroundCorner(bitmap);

        imageView.setImageBitmap(bitmap1);

        lv_persons=findViewById(R.id.lv_persons);

        lv_persons.addHeaderView(vHead);

//        personAdapter=new MultipleListAdapter(this,getDataList());
        lv_persons.setAdapter(personAdapter);




    }
//    public List<Message> getDataList(){
//        List<Message> dataList = new ArrayList<>();
//        for (int j=0;j<5;j++) {
//            for (int i = 0; i < 5; i++) {
//                Message message = new Message(2, "尘嘉", "5-19 15:20",
//                        "文本文本文本文本文本文本文本文本文本文本文本文本文本文本文本文本文本文本文本文本",
//                        "12", "14", "23", null, "5-30", "30",
//                        "小时不识月，呼作白玉盘","0");
//                dataList.add(message);
//            }
//            for (int i = 0; i < 2; i++) {
//                Message message = new Message(0, "尘嘉", "5-18 15:20",
//                        "文本文本文本文本文本文本文本文本文本文本文本文本文本文本文本文本文本文本文本文本",
//                        "12", "23", "3", null, null, null,
//                        null, null);
//                dataList.add(message);
//            }
//            for (int i = 0; i < 3; i++) {
//                Message message = new Message(1, "尘嘉", "5-18 15:20",
//                        "文本文本文本文本文本文本文本文本文本文本文本文本文本文本文本文本文本文本文本文本",
//                        "13", "14", "1", "3", null, null,
//                        null, null);
//                dataList.add(message);
//            }
//        }
//        return dataList;
//    }

    public Bitmap makeRroundCorner(Bitmap bitmap){


        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int left = 0, top = 0, right = width, bottom = height;
        float roundPx = height/2;
        if (width > height) {
            left = (width - height)/2;
            top = 0;
            right = left + height;
            bottom = height;
        } else if (height > width) {
            left = 0;
            top = (height - width)/2;
            right = width;
            bottom = top + width;
            roundPx = width/2;
        }

        Bitmap output = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        int color = 0xff424242;
        Paint paint = new Paint();
        Rect rect = new Rect(left, top, right, bottom);
        RectF rectF = new RectF(rect);

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);

        return output;
    }
}
