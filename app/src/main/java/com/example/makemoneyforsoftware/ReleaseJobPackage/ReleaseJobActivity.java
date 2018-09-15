package com.example.makemoneyforsoftware.ReleaseJobPackage;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.example.makemoneyforsoftware.LoginFragmentPackage.CurrentUser;
import com.example.makemoneyforsoftware.MyApplication;
import com.example.makemoneyforsoftware.R;
import com.jaygoo.widget.OnRangeChangedListener;
import com.jaygoo.widget.RangeSeekBar;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

public class ReleaseJobActivity extends AppCompatActivity {

    private TextView title_text;
    private TextView discrip_text;
    private TextView content_text;
    private TextView type_text;
    private TextView location_text;
    private TextView peoplenum;
    private TextView sex;
    private TextView cycle;
    private TextView range;
    private PopupWindow peoplepop;
    private PopupWindow sexpop;
    private PopupWindow cyclepop;
    private PopupWindow currrentpop;
    private TextView currentText;
    private TextView salary;
    private TextView startday;
    private TextView endday;
    private TextView time;
    public LocationClient mLocationClient;
    private View.OnClickListener mylistener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.edit_title_rel:
                    Intent intent=new Intent(ReleaseJobActivity.this,JobTitleActivity.class);
                    Bundle bundle=new Bundle();
                    bundle.putString("title",title_text.getText().toString());
                    intent.putExtras(bundle);
                    startActivityForResult(intent,101);
                    break;
                case R.id.edit_type_rel:
                    Intent intent1=new Intent(ReleaseJobActivity.this,JobTypeActivity.class);
                    startActivityForResult(intent1,102);
                    break;
                case R.id.edit_salary_rel:
                    Intent intent2=new Intent(ReleaseJobActivity.this,JobSalaryActivity.class);
                    startActivityForResult(intent2,103);
                    break;
                case R.id.require_people_lin:
                    peoplepop=createPopupWindow(getWindow().getDecorView(),R.layout.pop_people_layout);
                    currrentpop=peoplepop;
                    currentText=peoplenum;
                    break;
//                case R.id.require_age_lin:
//                    showPopwindow(getWindow().getDecorView());
//                    break;
                case R.id.require_sex_lin:
                    sexpop=createPopupWindow(getWindow().getDecorView(),R.layout.pop_sex_layout);
                    currrentpop=sexpop;
                    currentText=sex;
                    break;
                case R.id.require_cycle_lin:
                    cyclepop=createPopupWindow(getWindow().getDecorView(),R.layout.pop_circle_layout);
                    currrentpop=cyclepop;
                    currentText=cycle;
                    break;
                case R.id.edit_discrip_rel:
                    Intent intent3=new Intent(ReleaseJobActivity.this,JobDiscripActivity.class);
                    Bundle bundle1=new Bundle();
                    bundle1.putString("discrip",discrip_text.getText().toString());
                    intent3.putExtras(bundle1);
                    startActivityForResult(intent3,104);
                    break;
                case R.id.edit_content_rel:
                    Intent intent4=new Intent(ReleaseJobActivity.this,JobContentActivity.class);
                    Bundle bundle2=new Bundle();
                    bundle2.putString("content",content_text.getText().toString());
                    intent4.putExtras(bundle2);
                    startActivityForResult(intent4,105);
                    break;
                case R.id.edit_time_rel:
                    Intent intent5=new Intent(ReleaseJobActivity.this,JobTimeActivity.class);
                    startActivityForResult(intent5,106);
                    break;
                case R.id.edit_location_rel:
                    break;
                case R.id.save_as_draft:
                    break;
                case R.id.release:
                    Job job=getData();
                    uploadJob(job);
                    finish();
                    break;
                case R.id.back:
                    finish();
                    break;
                case R.id.edit_location_text_button:
                    mLocationClient.start();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLocationClient=new LocationClient(getApplicationContext());
        mLocationClient.registerLocationListener(new MyLocationListener());
        LocationClientOption option = new LocationClientOption();
        option.setIsNeedAddress(true);
        option.setIsNeedLocationDescribe(true);
        //可选，是否需要位置描述信息，默认为不需要，即参数为false
        // 如果开发者需要获得当前点的位置信息，此处必须为true
        mLocationClient.setLocOption(option);
        //mLocationClient为第二步初始化过的LocationClient对象
        // 需将配置好的LocationClientOption对象，通过setLocOption方法传递给LocationClient对象使用
        // 更多LocationClientOption的配置，请参照类参考中LocationClientOption类的详细说明
        setContentView(R.layout.activity_release_job);
        MyApplication myApplication=(MyApplication)getApplication();
        CurrentUser currentUser=myApplication.getLoginUser();
        EditText storename=(EditText)findViewById(R.id.store_name);
        EditText storetele=(EditText)findViewById(R.id.store_tele);
        EditText headname=(EditText)findViewById(R.id.head_name);
        storename.setText(currentUser.getStorename());
        storetele.setText(currentUser.getTele());
        headname.setText(currentUser.getName());
        title_text=(TextView)findViewById(R.id.edit_title_edit);
        discrip_text=(TextView)findViewById(R.id.edit_discrip_edit);
        content_text=(TextView)findViewById(R.id.edit_content_edit);
        type_text=(TextView)findViewById(R.id.edit_type_edit);
        location_text=(TextView)findViewById(R.id.edit_location_edit);
        peoplenum=(TextView)findViewById(R.id.require_people_img_text);
        sex=(TextView)findViewById(R.id.require_sex_img_text);
        cycle=(TextView)findViewById(R.id.require_cycle_img_text);
        salary=(TextView)findViewById(R.id.edit_salary_edit);
        RangeSeekBar rangeSeekBar=(RangeSeekBar)findViewById(R.id.rsb);
        rangeSeekBar.setRange(18,60);
        range=(TextView)findViewById(R.id.require_age_text);
        RelativeLayout title_rel=(RelativeLayout)findViewById(R.id.edit_title_rel);
        RelativeLayout type_rel=(RelativeLayout)findViewById(R.id.edit_type_rel);
        RelativeLayout salary_rel=(RelativeLayout)findViewById(R.id.edit_salary_rel);
        RelativeLayout discrip_rel=(RelativeLayout)findViewById(R.id.edit_discrip_rel);
        RelativeLayout content_rel=(RelativeLayout)findViewById(R.id.edit_content_rel);
        RelativeLayout time_rel=(RelativeLayout)findViewById(R.id.edit_time_rel);
        RelativeLayout location_rel=(RelativeLayout)findViewById(R.id.edit_location_rel);
        LinearLayout people_lin=(LinearLayout)findViewById(R.id.require_people_lin);
        LinearLayout sex_lin=(LinearLayout)findViewById(R.id.require_sex_lin);
        LinearLayout cycle_lin=(LinearLayout)findViewById(R.id.require_cycle_lin);
        Button save_as_draft=(Button)findViewById(R.id.save_as_draft);
        Button release=(Button)findViewById(R.id.release);
        ImageView back=(ImageView)findViewById(R.id.back);
        LinearLayout location=(LinearLayout)findViewById(R.id.edit_location_text_button);
        location.setOnClickListener(mylistener);
        title_rel.setOnClickListener(mylistener);
        type_rel.setOnClickListener(mylistener);
        salary_rel.setOnClickListener(mylistener);
        discrip_rel.setOnClickListener(mylistener);
        content_rel.setOnClickListener(mylistener);
        time_rel.setOnClickListener(mylistener);
        location_rel.setOnClickListener(mylistener);
        people_lin.setOnClickListener(mylistener);
        sex_lin.setOnClickListener(mylistener);
        cycle_lin.setOnClickListener(mylistener);
        save_as_draft.setOnClickListener(mylistener);
        release.setOnClickListener(mylistener);
        back.setOnClickListener(mylistener);
        rangeSeekBar.setIndicatorTextDecimalFormat("0");
        rangeSeekBar.setTypeface(Typeface.DEFAULT_BOLD);
        rangeSeekBar.getLeftSeekBar().setTypeface(Typeface.DEFAULT_BOLD);
        rangeSeekBar.setOnRangeChangedListener(new OnRangeChangedListener() {
            @Override
            public void onRangeChanged(RangeSeekBar view, float leftValue, float rightValue, boolean isFromUser) {
                range.setText(new DecimalFormat("0").format(leftValue)+"-"+new DecimalFormat("0").format(rightValue));
            }

            @Override
            public void onStartTrackingTouch(RangeSeekBar view, boolean isLeft) {

            }

            @Override
            public void onStopTrackingTouch(RangeSeekBar view, boolean isLeft) {

            }
        });
    }

    public void onTextClick(View view){
        currrentpop.dismiss();
        TextView textView=(TextView) view;
        String str=textView.getText().toString();
        if(str.equals("取消")){
            currrentpop.dismiss();
        }else if(str.equals("确定")){
            EditText editText=peoplepop.getContentView().findViewById(R.id.peoplepop_num);
            peoplepop.dismiss();
            peoplenum.setText(editText.getText().toString()+"人");
        } else {
            currentText.setText(str);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case 101:
                if(resultCode==RESULT_OK){
                    title_text.setVisibility(View.VISIBLE);
                    title_text.setText(data.getExtras().getString("title"));
                }
                break;
            case 102:
                if(resultCode==RESULT_OK){
                    type_text.setVisibility(View.VISIBLE);
                    type_text.setText(data.getExtras().getString("type"));
                }
                break;
            case 103:
                if(resultCode==RESULT_OK){
                    salary.setVisibility(View.VISIBLE);
                    salary.setText(data.getExtras().getString("salary"));
                }
                break;
            case 104:
                if(resultCode==RESULT_OK) {
                    discrip_text.setVisibility(View.VISIBLE);
                    discrip_text.setText(data.getExtras().getString("discrip"));
                }
                break;
            case 105:
                if(resultCode==RESULT_OK) {
                    content_text.setVisibility(View.VISIBLE);
                    content_text.setText(data.getExtras().getString("content"));
                }
                break;
            case 106:
                if(resultCode==RESULT_OK){
                    LinearLayout linearLayout=(LinearLayout)findViewById(R.id.time_lin);
                    linearLayout.removeAllViews();
                    View view=LayoutInflater.from(ReleaseJobActivity.this).inflate(R.layout.releasejobtimelayout,linearLayout,false);
                    startday=(TextView)view.findViewById(R.id.start_day);
                    endday=(TextView)view.findViewById(R.id.end_day);
                    time=(TextView)view.findViewById(R.id.time_text);
                    Log.d("msg",data.getExtras().getString("start_day"));
                    startday.setText(data.getExtras().getString("start_day"));
                    Log.d("msg",data.getExtras().getString("end_day"));
                    endday.setText(data.getExtras().getString("end_day"));
                    String str1=data.getExtras().getString("start_time");
                    String str2=data.getExtras().getString("end_time");
                    if(str1.equals("点击选择时间")&&str2.equals("点击选择时间")){
                        time.setText("报名后通知");
                    }else {
                        time.setText(str1+"-"+str2);
                    }
                    linearLayout.addView(view);
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public PopupWindow createPopupWindow(View view, int resourceId){
        View viewPopwindow = null;
        PopupWindow mPopWindow = null;
        viewPopwindow = LayoutInflater.from(this).inflate(resourceId, null);
        if (null != viewPopwindow) {
            mPopWindow = new PopupWindow(viewPopwindow,
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
            mPopWindow.setTouchable(true);
            mPopWindow.setOutsideTouchable(true);
            mPopWindow.showAtLocation(view, Gravity.BOTTOM, 0, 0);
            setBackgroundAlpha(0.5f);
            mPopWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    setBackgroundAlpha(1f);
                }
            });
        }
        return mPopWindow;
    }

    public void setBackgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        getWindow().setAttributes(lp);
    }

    public class MyLocationListener extends BDAbstractLocationListener {
        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            StringBuilder currentPosition=new StringBuilder();
//            currentPosition.append("纬度:").append(bdLocation.getLatitude()).append("\n");
//            currentPosition.append("经度:").append(bdLocation.getLongitude()).append("\n");
//            currentPosition.append("省:").append(bdLocation.getProvince()).append("\n");
//            currentPosition.append("市:").append(bdLocation.getCity()).append("\n");
//            currentPosition.append("区:").append(bdLocation.getDistrict()).append("\n");
//            currentPosition.append("街道:").append(bdLocation.getStreet()).append("\n");
//            currentPosition.append("镇:").append(bdLocation.getAddrStr()).append("\n");
//            currentPosition.append("定位方式:");
//            if(bdLocation.getLocType()==BDLocation.TypeGpsLocation){
//                currentPosition.append("GPS");
//            }else if(bdLocation.getLocType()==BDLocation.TypeNetWorkLocation){
//                currentPosition.append("网络");
//            }
            currentPosition.append(bdLocation.getProvince()).append(bdLocation.getCity());
            String locationDescribe = bdLocation.getLocationDescribe();    //获取位置描述信息
            currentPosition.append(locationDescribe);
            location_text.setText(currentPosition);
        }
    }

    public Job getData(){
        Job job=new Job();
        job.setJobtitle(title_text.getText().toString());
        Log.d("msg","title："+title_text.getText().toString());
        job.setJobtype(type_text.getText().toString());
        Log.d("msg","type："+type_text.getText().toString());
        job.setJobsalary(salary.getText().toString());
        Log.d("msg","salary："+salary.getText().toString());
        job.setJobrequirenum(peoplenum.getText().toString());
        Log.d("msg","peoplenum："+peoplenum.getText().toString());
        job.setJobrequiresex(sex.getText().toString());
        Log.d("msg","sex："+sex.getText().toString());
        job.setJobrequirecycle(cycle.getText().toString());
        Log.d("msg","cycle："+cycle.getText().toString());
        job.setJobrequireage(range.getText().toString());
        Log.d("msg","range："+range.getText().toString());
        job.setJobdiscrip(discrip_text.getText().toString());
        Log.d("msg","discrip_text："+discrip_text.getText().toString());
        job.setJobcontent(content_text.getText().toString());
        Log.d("msg","content_text："+content_text.getText().toString());
        job.setJobstartday(startday.getText().toString());
        Log.d("msg","startday："+startday.getText().toString());
        job.setJobendday(endday.getText().toString());
        Log.d("msg","endday："+endday.getText().toString());
        job.setJobworktime(time.getText().toString());
        Log.d("msg","time："+time.getText().toString());
        job.setJoblocation(location_text.getText().toString());
        Log.d("msg","location："+location_text.getText().toString());
        MyApplication myApplication=(MyApplication)ReleaseJobActivity.this.getApplication();
        CurrentUser currentUser=myApplication.getLoginUser();
        job.setBusinessmantele(currentUser.getTele());
        Log.d("msg","tele："+currentUser.getTele());
        return job;
    }

    public void uploadJob(final Job job){
        RequestQueue mQueue= Volley.newRequestQueue(getApplicationContext());
        String url="http://119.23.13.19:8080/Aliyun/InsertJobServlet";
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
                Map<String,String> map=new HashMap<>();
                map.put("title",job.getJobtitle());
                map.put("type",job.getJobtype());
                map.put("salary",job.getJobsalary());
                map.put("peoplenum",job.getJobrequirenum());
                map.put("sex",job.getJobrequiresex());
                map.put("cycle",job.getJobrequirecycle());
                map.put("age",job.getJobrequireage());
                map.put("discrip",job.getJobdiscrip());
                map.put("content",job.getJobcontent());
                map.put("startday",job.getJobstartday());
                map.put("endday",job.getJobendday());
                map.put("worktime",job.getJobworktime());
                map.put("location",job.getJoblocation());
                map.put("tele",job.getBusinessmantele());
                map.put("releaseday",job.getJobreleaseday());
                return map;
            }
        };
        mQueue.add(stringRequest);
    }
}
