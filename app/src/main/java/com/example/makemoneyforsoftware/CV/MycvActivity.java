package com.example.makemoneyforsoftware.CV;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import com.example.makemoneyforsoftware.FrameActivity;
import com.example.makemoneyforsoftware.LoginFragmentPackage.CurrentUser;
import com.example.makemoneyforsoftware.MyApplication;
import com.example.makemoneyforsoftware.MyStackAdapter;
import com.example.makemoneyforsoftware.R;
import com.loopeer.cardstack.CardStackView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class MycvActivity extends AppCompatActivity implements CardStackView.ItemExpendListener {

    private CardStackView mStackView;
    private MyStackAdapter mTestStackAdapter;
    private LinearLayout mActionButtonContainer;
    private CurrentUser currentUser;

    private List<CvItem> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mycv);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = MycvActivity.this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.black));
        }
        MyApplication myApplication = (MyApplication) getApplication();
        currentUser = myApplication.getLoginUser();
        CircleImageView head = (CircleImageView) findViewById(R.id.cv_head_img);
        ImageView sex = (ImageView) findViewById(R.id.cv_sex);
        TextView name = (TextView) findViewById(R.id.cv_name);
        TextView age = (TextView) findViewById(R.id.cv_age);
        TextView tele = (TextView) findViewById(R.id.cv_tele);
        TextView mail = (TextView) findViewById(R.id.cv_mail);
        Glide.with(this).load(currentUser.getUrl()).into(head);
        name.setText(currentUser.getName());
        tele.setText(currentUser.getTele());
        mail.setText(currentUser.getMail());
        if (currentUser.getSex().equals("男")) {
            sex.setImageResource(R.mipmap.icon_male_fill);
        } else {
            sex.setImageResource(R.mipmap.icon_female_fill);
        }
        initlist();
        mStackView = (CardStackView) findViewById(R.id.stackview_main);
        mActionButtonContainer = (LinearLayout) findViewById(R.id.button_container);
        mStackView.setItemExpendListener(this);
        mTestStackAdapter = new MyStackAdapter(this);
        mStackView.setAdapter(mTestStackAdapter);
    }

    public void initlist() {
        list = new ArrayList<>();
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        String url = "http://119.23.13.19:8080/Aliyun/QueryCVServlet";
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("msg",response);
                try {
                    JSONArray jsonArray=new JSONArray(response);
                    JSONObject edu=new JSONObject(jsonArray.getJSONObject(0).getString("edu"));
                    JSONObject work=new JSONObject(jsonArray.getJSONObject(1).getString("work"));
                    JSONObject eva=new JSONObject(jsonArray.getJSONObject(2).getString("eva"));
                    CvItem item1 = new CvItem();
                    item1.setColor(R.color.color_1);
                    item1.setType(1);
                    item1.setTitle("添加教育经历");
                    item1.setSchool(edu.getString("school"));
                    item1.setMajor(edu.getString("major"));
                    item1.setXueli(edu.getString("xueli"));
                    item1.setStartday(edu.getString("startday"));
                    item1.setEndday(edu.getString("endday"));
                    item1.setMsg(edu.getString("eduexp"));
                    list.add(item1);
                    CvItem item2 = new CvItem();
                    item2.setType(2);
                    item2.setColor(R.color.color_2);
                    item2.setTitle("添加工作经历");
                    item2.setCompany(work.getString("company"));
                    item2.setStartday(work.getString("startday"));
                    item2.setEndday(work.getString("endday"));
                    item2.setMsg(work.getString("workexp"));
                    list.add(item2);
                    CvItem item3 = new CvItem();
                    item3.setColor(R.color.color_3);
                    item3.setType(3);
                    item3.setTitle("添加自我评价");
                    item3.setMsg(eva.getString("eva"));
                    list.add(item3);
                    mTestStackAdapter.updateData(list);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("msg",error.getMessage());
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map=new HashMap<>();
                map.put("tele",currentUser.getTele());
                map.put("type","all");
                return map;
            }
        };
        requestQueue.add(request);
    }

    public void onclick(View v) {
        Intent intent;
        String text=((TextView)v).getText().toString();
        if(text.equals("添加教育经历")){
            intent = new Intent(MycvActivity.this, AddeduActivity.class);
            startActivityForResult(intent, 101);
        }else if (text.equals("添加工作经历")){
            intent = new Intent(MycvActivity.this, AddworkexpActivity.class);
            startActivityForResult(intent, 102);
        }else {
            intent = new Intent(MycvActivity.this, AddselfevaActivity.class);
            startActivityForResult(intent, 103);
        }
    }

    public void onotherclick(View view){
        switch (view.getId()) {
            case R.id.yulan:
                Intent intent=new Intent(MycvActivity.this,MyCVpreviewActivity.class);
                startActivity(intent);
                break;
            case R.id.back:
                Intent intent1=new Intent(MycvActivity.this, FrameActivity.class);
                Bundle bundle=new Bundle();
                bundle.putInt("page",4);
                intent1.putExtras(bundle);
                startActivity(intent1);
                finish();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        List<CvItem> listcopy=new ArrayList<>();
        Bundle bundle=null;
        switch (requestCode) {
            case 101:
                if (resultCode == RESULT_OK) {
                    bundle=data.getExtras();
                    CvItem item=new CvItem();
                    item.setTitle("添加教育经历");
                    item.setColor(R.color.color_1);
                    item.setStartday(bundle.getString("start"));
                    item.setEndday(bundle.getString("end"));
                    item.setType(1);
                    item.setMsg(bundle.getString("exp"));
                    item.setMajor(bundle.getString("major"));
                    item.setXueli(bundle.getString("xueli"));
                    item.setSchool(bundle.getString("school"));
                    listcopy.add(item);
                    listcopy.add(list.get(1));
                    listcopy.add(list.get(2));
                    list=listcopy;
                    mTestStackAdapter.updateData(list);
                }
                break;
            case 102:
                if (resultCode == RESULT_OK) {
                    bundle=data.getExtras();
                    CvItem item=new CvItem();
                    item.setTitle("添加工作经历");
                    item.setColor(R.color.color_2);
                    item.setStartday(bundle.getString("start"));
                    item.setEndday(bundle.getString("end"));
                    item.setType(2);
                    item.setMsg(bundle.getString("workexp"));
                    item.setCompany(bundle.getString("company"));
                    listcopy.add(list.get(0));
                    listcopy.add(item);
                    listcopy.add(list.get(2));
                    list=listcopy;
                    mTestStackAdapter.updateData(list);
                }
                break;
            case 103:
                if (resultCode == RESULT_OK) {
                    bundle=data.getExtras();
                    CvItem item=new CvItem();
                    item.setTitle("添加自我评价");
                    item.setColor(R.color.color_3);
                    item.setType(3);
                    item.setMsg(bundle.getString("selfeva"));
                    listcopy.add(list.get(0));
                    listcopy.add(list.get(1));
                    listcopy.add(item);
                    list=listcopy;
                    mTestStackAdapter.updateData(list);
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override

    public void onItemExpend(boolean expend) {

    }
}
