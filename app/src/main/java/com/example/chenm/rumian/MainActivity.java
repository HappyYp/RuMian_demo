package com.example.chenm.rumian;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.Toast;

import com.alibaba.fastjson.JSONArray;
import com.google.gson.Gson;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmModel;
import io.realm.RealmResults;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    //数据库操作
    private Realm mrealm;
    //网络连接
    private String BaseURL = "http://10.7.84.118:8080/app/";
    private OkHttpClient okHttpClient;
    private static final String TAG = "OkHttpClient";

    private ViewPager viewPager;
    private MenuItem menuItem;
    private BottomNavigationView navigation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //创建OKhttpclient对象
        okHttpClient = new OkHttpClient();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        viewPager = findViewById(R.id.viewpager);
        viewPager.addOnPageChangeListener(mOnPageChangeListener);
        setupViewPager();
        navigation = findViewById(R.id.navigation);
        navigation.setSelectedItemId(R.id.navigation_home);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        BottomNavigationViewHelper.disableShiftMode(navigation);//取消导航栏图标移动

    }

    private ViewPager.OnPageChangeListener mOnPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            if (menuItem != null){
                menuItem.setChecked(false);
            }else {
                navigation.getMenu().getItem(0).setChecked(false);
            }
            menuItem = navigation.getMenu().getItem(position);
            menuItem.setChecked(true);
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    private void setupViewPager(){
        final List<View> viewList = new ArrayList<>();
        final LayoutInflater inflater = getLayoutInflater();
        View viewHome,viewExplore,viewMessage,viewPublish,viewMarket;
        viewHome = inflater.inflate(R.layout.layout_home,null);
        viewExplore = inflater.inflate(R.layout.layout_explore,null);
        viewMessage = inflater.inflate(R.layout.layout_message,null);
        viewPublish = inflater.inflate(R.layout.layout_publish,null);
        viewMarket = inflater.inflate(R.layout.layout_market,null);

        ListView homeList = viewHome.findViewById(R.id.home_list);
        final List<Message> dataList = getDataList();
        homeList.setAdapter(new MultipleListAdapter(this,dataList));
        homeList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Message message = dataList.get(i);
                Intent intent = new Intent();
                intent.setClass(MainActivity.this,MessageDetailActivity.class);
                intent.putExtra("messageType",message.getMessageType());
                putExtra(intent,message);
                switch (dataList.get(i).getMessageType()){
                    case Constant.MESSAGE_ITEM_VIEW_TYPE_Message:         //Message
                        startActivity(intent);
                        break;
                    case Constant.MESSAGE_ITEM_VIEW_TYPE_IO:         //IO
                        intent.putExtra("orderTextContent",message.getOrderTextContent());
                        intent.putExtra("price",message.getPrice());
                        intent.putExtra("deadline",message.getDeadline());
                        intent.putExtra("acceptCount",message.getAcceptCount());
                        startActivity(intent);
                        break;
                    case Constant.MESSAGE_ITEM_VIEW_TYPE_AO:         //AO
                        intent.putExtra("issueCount",message.getIssueCount());
                        startActivity(intent);
                        break;
                }
            }
        });

        viewList.add(viewHome);
        viewList.add(viewMarket);
        viewList.add(viewPublish);
        viewList.add(viewExplore);
        viewList.add(viewMessage);

        PagerAdapter pagerAdapter = new PagerAdapter() {
            @Override
            public int getCount() {
                return viewList.size();
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView(viewList.get(position));
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                container.addView(viewList.get(position));
                return viewList.get(position);
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }
        };

        viewPager.setAdapter(pagerAdapter);
    }

    private void putExtra(Intent intent,Message message){
        intent.putExtra("userImage",message.getUserImage());
        intent.putExtra("imgUrl",message.getImgUrl());
        intent.putExtra("username",message.getUserName());
        intent.putExtra("pushTime",message.getPushTime());
        intent.putExtra("textContent",message.getTextContent());
        intent.putExtra("likeCount",message.getLikeCount());
        intent.putExtra("commentCount",message.getCommentCount());
        intent.putExtra("shareCount",message.getShareCount());
    }
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    viewPager.setCurrentItem(0);
                    return true;
                case R.id.navigation_market:
                    viewPager.setCurrentItem(1);
                    return true;
                case R.id.navigation_publish:
                    viewPager.setCurrentItem(2);
                    return true;
                case R.id.navigation_explore:
                    viewPager.setCurrentItem(3);
                    return true;
                case R.id.navigation_message:
                    viewPager.setCurrentItem(4);
                    return true;
            }
            return false;
        }
    };
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
            startActivity(new Intent(MainActivity.this,PersonalPageActivity.class));
        } else if (id == R.id.nav_gallery) {
            startActivity(new Intent(MainActivity.this,PersonalPageActivity.class));
        } else if (id == R.id.nav_slideshow) {
            startActivity(new Intent(MainActivity.this,GalleryActivity.class));
        } else if (id == R.id.nav_manage) {
            startActivity(new Intent(MainActivity.this,SettingsActivity.class));
        } else if (id == R.id.nav_share) {
            startActivity(new Intent(MainActivity.this,ShareActivity.class));
        } else if (id == R.id.nav_send) {
            startActivity(new Intent(MainActivity.this,SendActivity.class));
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public List<Message> getDataList(){
//        List<Message> dataList = new ArrayList<>();
//        for (int j=0;j<5;j++) {
//            for (int i = 0; i < 5; i++) {
//                Message message = new Message(2, "王五", "5-19 15:20",
//                        "求硬笔中文单，5天交单，童叟无欺。",
//                        "12", "14", "23", null, "5-30", "30",
//                        "小时不  识月，呼作白玉盘", "0");
//                dataList.add(message);
//            }
//            for (int i = 0; i < 2; i++) {
//                Message message = new Message(0, "张三", "5-18 15:20",
//                        "普通信息文本文本文本文本文本文本文本文本文本文本文本文本文本文本文本文本文本文本",
//                        "12", "23", "3", null, null, null,
//                        null, null);
//                dataList.add(message);
//            }
//            for (int i = 0; i < 3; i++) {
//                Message message = new Message(1, "李四", "5-18 15:20",
//                        "接硬笔中文单，10块一字，量大打折，示例如下。5天交单，童叟无欺。",
//                        "13", "14", "1", "3", null, null,
//                        null, null);
//                dataList.add(message);
//            }
//        }
        return doPostString();
    }
    //设置短时间内触发两次退出时间允许退出
    private long exitTime = 0;

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_HOME) {
            return true;
        } else if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                finish();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    //向服务器请求数据并接受json字符串
    private List<Message> doPostString(){
        final List<Message> dataList = new ArrayList<>();
        final Gson gson = new Gson();

        Request request = new Request.Builder()
                .url(BaseURL+"messagesList")
                .build();
        //创建Call对象，并执行请求
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e(TAG,"error");
//                e.printStackTrace();
                //同步操作：findAll查询
                mrealm = UIUtils.getRealmInstance();
//                RealmResults<Message> messagesList = mrealm.where(Message.class)
//                        .findAll();
                RealmResults<Message> messagesList = mrealm.where(Message.class)
                        .findAllAsync();
                messagesList.load();
                if(messagesList != null && messagesList.isLoaded()) {
                    for (int i = 0; i < messagesList.size(); i++) {
                        dataList.add(messagesList.get(i));
                    }
                }else {
                    Log.e(TAG,"errormessage");
                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                String string = response.body().string();
                Log.e(TAG,string);
                JSONArray jsonArray = JSONArray.parseArray(string);

                for (int i = 0; i < jsonArray.size(); i++){
                    mrealm = UIUtils.getRealmInstance();
                    final Message message = gson.fromJson(jsonArray.getString(i),Message.class);
                    dataList.add(message);
                    mrealm.executeTransaction(new Realm.Transaction() {
                        @Override
                        public void execute(Realm realm) {
                            realm.copyToRealmOrUpdate(message);
                        }
                    });
                }

            }
        });
        return dataList;
    }
}
