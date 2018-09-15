package com.example.makemoneyforsoftware.AllJob;

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
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.makemoneyforsoftware.LoginFragmentPackage.CurrentUser;
import com.example.makemoneyforsoftware.MyApplication;
import com.example.makemoneyforsoftware.R;
import com.example.makemoneyforsoftware.ReleaseJobPackage.Job;


import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class JobItemActivity extends AppCompatActivity {

    private Intent intent;
    private Bundle bundle;
    private CircleImageView b_head;
    private TextView b_name;
    private TextView b_tele;
    private Job job;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = JobItemActivity.this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.black));
        }
        setContentView(R.layout.activity_job_item);
        b_head=(CircleImageView)findViewById(R.id.b_head_img);
        b_name=(TextView)findViewById(R.id.b_name);
        b_tele=(TextView)findViewById(R.id.b_tele);
        intent=getIntent();
        bundle=intent.getExtras();
        int fromtype=bundle.getInt("type");
        job=(Job)bundle.getSerializable("job");
        TextView title=(TextView)findViewById(R.id.title);
        title.setText(job.getJobtitle());
        TextView salary=(TextView)findViewById(R.id.salary);
        salary.setText(job.getJobsalary());
        TextView location=(TextView)findViewById(R.id.location);
        location.setText(job.getJoblocation());
        TextView type=(TextView)findViewById(R.id.type);
        type.setText(job.getJobtype());
        TextView releasetime=(TextView)findViewById(R.id.releasetime);
        releasetime.setText(job.getJobreleaseday());
        TextView people_num=(TextView)findViewById(R.id.people_num);
        people_num.setText("人数要求:"+job.getJobrequirenum());
        TextView sex=(TextView)findViewById(R.id.sex);
        sex.setText("性别要求:"+job.getJobrequiresex());
        TextView cycle=(TextView)findViewById(R.id.cycle);
        cycle.setText("结算周期:"+job.getJobrequirecycle());
        TextView age=(TextView)findViewById(R.id.age);
        age.setText("年龄要求:"+job.getJobrequireage());
        TextView discrip=(TextView)findViewById(R.id.discrip);
        discrip.setText(job.getJobdiscrip());
        TextView content=(TextView)findViewById(R.id.content);
        content.setText(job.getJobcontent());
        TextView startday=(TextView)findViewById(R.id.startday);
        startday.setText(job.getJobstartday());
        final TextView endday=(TextView)findViewById(R.id.endday);
        endday.setText(job.getJobendday());
        TextView worktime=(TextView)findViewById(R.id.time);
        worktime.setText(job.getJobworktime());
        TextView maplocation=(TextView)findViewById(R.id.map_location);
        maplocation.setText(job.getJoblocation());
        final ImageView back=(ImageView)findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        LinearLayout baoming=(LinearLayout)findViewById(R.id.baominglin);
        TextView textView=(TextView)findViewById(R.id.text1);
        switch (fromtype){
            case 0:
                baoming.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Job job1=(Job) bundle.getSerializable("job");
                        enterJob(job1);
                        Toast.makeText(JobItemActivity.this,"报名成功",Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
                break;
            case 1:
                textView.setText("已报名");
                break;
            case 2:
                textView.setText("已录用");
                break;
            case 3:
                textView.setText("已到岗");
                break;
            case 4:
                textView.setText("已结算");
                break;
        }
        queryUserInfo();
    }

    public void queryUserInfo() {
        RequestQueue mQueue = Volley.newRequestQueue(getApplicationContext());
        String url = "http://119.23.13.19:8080/Aliyun/QueryUserInfoServlet";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("msg", "businessmaninfo" + response);
                try {
                    JSONObject jsonObject = new JSONObject(new JSONObject(response).getString("result"));
                    b_name.setText(jsonObject.getString("businame"));
                    b_tele.setText(jsonObject.getString("busitele"));
                    Glide.with(JobItemActivity.this).load(jsonObject.getString("busiheadimgurl")).into(b_head);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("msg", error.getMessage());
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("tele", job.getBusinessmantele());
                map.put("type","businessman");
                return map;
            }
        };
        mQueue.add(stringRequest);

    }

    public void enterJob(final Job job){
        final RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        String url="http://119.23.13.19:8080/Aliyun/EnterJobServlet";
        StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
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
                MyApplication myApplication=(MyApplication)getApplication();
                CurrentUser currentUser=myApplication.getLoginUser();
                Map<String,String> map=new HashMap<>();
                map.put("jobid",Integer.toString(job.getJobid()));
                Log.d("msg",""+job.getJobid());
                map.put("usertele",currentUser.getTele());
                Date date=new Date();
                SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
                String str=simpleDateFormat.format(date);
                map.put("enterday",str);
                return map;
            }
        };
        requestQueue.add(stringRequest);

    }
}
