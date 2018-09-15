package com.example.makemoneyforsoftware.BusinessmanApplicationFragment;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.baoyachi.stepview.VerticalStepView;
import com.bumptech.glide.Glide;
import com.example.makemoneyforsoftware.R;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserCVPreviewActivity extends AppCompatActivity {

    private VerticalStepView stepView1;
    private VerticalStepView stepView2;
    private VerticalStepView stepView3;
    private User user;

    private View.OnClickListener mylistener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.luyong:
                    luyong();
                    Toast.makeText(UserCVPreviewActivity.this,"录用成功",Toast.LENGTH_SHORT).show();
                    finish();
                    break;
                case R.id.luyonglin:
                    luyong();
                    Toast.makeText(UserCVPreviewActivity.this,"录用成功",Toast.LENGTH_SHORT).show();
                    finish();
                    break;
                case R.id.jujuelin:
                    jujue();
                    finish();
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = UserCVPreviewActivity.this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.black));
        }
        setContentView(R.layout.activity_user_cvpreview);
        ImageView back=(ImageView)findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        TextView yulan=(TextView)findViewById(R.id.luyong);
        LinearLayout luyonglin=(LinearLayout)findViewById(R.id.luyonglin);
        LinearLayout jujuelin=(LinearLayout)findViewById(R.id.jujuelin);
        yulan.setOnClickListener(mylistener);
        jujuelin.setOnClickListener(mylistener);
        luyonglin.setOnClickListener(mylistener);
        Intent intent=getIntent();
        Bundle bundle=intent.getExtras();
        user=(User) bundle.getSerializable("user");
        CircleImageView head=(CircleImageView)findViewById(R.id.head);
        TextView name=(TextView)findViewById(R.id.name);
        TextView address=(TextView)findViewById(R.id.address);
        TextView tele=(TextView)findViewById(R.id.tele);
        TextView mail=(TextView)findViewById(R.id.mail);
        TextView sex=(TextView)findViewById(R.id.sex);
        Glide.with(this).load(user.getUrl()).into(head);
        name.setText(user.getName());
        address.setText(user.getLocation());
        tele.setText(user.getTele());
        mail.setText(user.getMail());
        sex.setText(user.getSex());
        stepView1=(VerticalStepView)findViewById(R.id.stepview1);
        stepView2=(VerticalStepView)findViewById(R.id.stepview2);
        stepView3=(VerticalStepView)findViewById(R.id.stepview3);
        getData();
    }

    public void getData() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        String url = "http://119.23.13.19:8080/Aliyun/QueryCVServlet";
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("msg",response);
                try {
                    JSONArray jsonArray=new JSONArray(response);
                    JSONObject edu=new JSONObject(jsonArray.getJSONObject(0).getString("edu"));
                    List<String> list1=new ArrayList<>();
                    if (edu.getString("startday").equals("")) {
                        list1.add("入学日期:" + "未填写" + " - " + "毕业日期:" + "未填写");
                    } else {
                        list1.add("入学日期:" + edu.getString("startday") + " - " + "毕业日期:" + edu.getString("endday"));
                    }
                    if (edu.getString("school").equals("")) {
                        list1.add("学校:" + "未填写" + " | " + "学历:" + "未填写");
                    } else {
                        list1.add("学校:" + edu.getString("school") + " | " + "学历:" + edu.getString("xueli"));
                    }
                    if (edu.getString("major").equals("")) {
                        list1.add("主修专业:" + "未填写");
                    } else {
                        list1.add("主修专业:" + edu.getString("major"));
                    }
                    if (edu.getString("eduexp") == null || edu.getString("eduexp").equals("")) {
                        list1.add("个人经历:" + "未填写");
                    } else {
                        list1.add("个人经历:" + edu.getString("eduexp"));
                    }
                    initstepview(list1,stepView1);
                    JSONObject work=new JSONObject(jsonArray.getJSONObject(1).getString("work"));
                    List<String> list2=new ArrayList<>();
                    if (work.getString("startday").equals("")) {
                        list2.add("开始日期:"+"未填写" + " - " + "结束日期:"+"未填写");
                    } else {
                        list2.add("开始日期:"+work.getString("startday") + " - " + "结束日期:"+work.getString("endday"));
                    }
                    if(work.getString("company").equals("")){
                        list2.add("公司:" + "未填写");
                    }else {
                        list2.add("公司:" + work.getString("company"));
                    }
                    if(work.getString("workexp").equals("")){
                        list2.add("工作经历:" + "未填写");
                    }else {
                        list2.add("工作经历:" + work.getString("workexp"));
                    }
                    initstepview(list2,stepView2);
                    JSONObject eva=new JSONObject(jsonArray.getJSONObject(2).getString("eva"));
                    List<String> list3=new ArrayList<>();
                    if(eva.getString("eva").equals("")){
                        list3.add("自我评价:" + "未填写");
                    }else {
                        list3.add("自我评价:" + eva.getString("eva"));
                    }
                    initstepview(list3,stepView3);
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
                map.put("tele",user.getTele());
                map.put("type","all");
                return map;
            }
        };
        requestQueue.add(request);
    }

    public void initstepview(List<String> list,VerticalStepView stepView){

        stepView.setStepsViewIndicatorComplectingPosition(list.size())//设置完成的步数
                .reverseDraw(false)//default is true
                .setStepViewTexts(list)//总步骤
                .setLinePaddingProportion(0.85f)//设置indicator线与线间距的比例系数
                .setStepsViewIndicatorCompletedLineColor(ContextCompat.getColor(UserCVPreviewActivity.this, android.R.color.darker_gray))//设置StepsViewIndicator完成线的颜色
                .setStepsViewIndicatorUnCompletedLineColor(ContextCompat.getColor(UserCVPreviewActivity.this, R.color.uncompleted_text_color))//设置StepsViewIndicator未完成线的颜色
                .setStepViewComplectedTextColor(ContextCompat.getColor(UserCVPreviewActivity.this, android.R.color.darker_gray))//设置StepsView text完成线的颜色
                .setStepViewUnComplectedTextColor(ContextCompat.getColor(UserCVPreviewActivity.this, R.color.uncompleted_text_color))//设置StepsView text未完成线的颜色
                .setStepsViewIndicatorCompleteIcon(ContextCompat.getDrawable(UserCVPreviewActivity.this, R.mipmap.yes_fill))//设置StepsViewIndicator CompleteIcon
                .setStepsViewIndicatorDefaultIcon(ContextCompat.getDrawable(UserCVPreviewActivity.this, R.drawable.default_icon))//设置StepsViewIndicator DefaultIcon
                .setStepsViewIndicatorAttentionIcon(ContextCompat.getDrawable(UserCVPreviewActivity.this, R.drawable.attention));//设置StepsViewIndicator AttentionIco
    }

    public void luyong(){
        RequestQueue queue=Volley.newRequestQueue(getApplicationContext());
        final String url="http://119.23.13.19:8080/Aliyun/UpdateUserJobTypeServlet";
        StringRequest request=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("msg",response);
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
                map.put("tele",user.getTele());
                map.put("type","2");
                map.put("jobid",Integer.toString(user.getJobid()));
                return map;
            }
        };
        queue.add(request);
    }

    public void jujue(){
        RequestQueue queue=Volley.newRequestQueue(getApplicationContext());
        final String url="http://119.23.13.19:8080/Aliyun/DeleteBaomingServlet";
        StringRequest request=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("msg",response);
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
                map.put("tele",user.getTele());
                map.put("jobid",Integer.toString(user.getJobid()));
                return map;
            }
        };
        queue.add(request);
    }
}
