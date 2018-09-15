package com.example.makemoneyforsoftware.CV;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.contrarywind.adapter.WheelAdapter;
import com.contrarywind.listener.OnItemSelectedListener;
import com.contrarywind.view.WheelView;
import com.example.makemoneyforsoftware.LoginFragmentPackage.CurrentUser;
import com.example.makemoneyforsoftware.MyApplication;
import com.example.makemoneyforsoftware.R;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddeduActivity extends AppCompatActivity {

    private PopupWindow currentpop;
    private TextView school_text;
    private TextView endtext;
    private TextView starttext;
    private EditText majortext;
    private TextView xuelitext;
    private EditText editText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addedu);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = AddeduActivity.this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.black));
        }
        school_text=(TextView)findViewById(R.id.school_text);
        majortext=(EditText)findViewById(R.id.major);
        editText=(EditText)findViewById(R.id.auto_text);
        final TextView textView=(TextView) findViewById(R.id.count);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                textView.setText(editText.getText().length()+"/"+300);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    public void onaddeduclick(View view){
        switch (view.getId()){
            case R.id.back:
                finish();
                break;
            case R.id.save:
                Intent intent=getIntent();
                Bundle bundle=new Bundle();
                if(school_text.getText().toString().equals("")||majortext.getText().toString().equals("")||xuelitext.getText().toString().equals("")||starttext.getText().toString().equals("")||endtext.getText().toString().equals("")){
                    Toast.makeText(this,"请将信息填写完整",Toast.LENGTH_SHORT).show();
                }else {
                    bundle.putString("school",school_text.getText().toString());
                    bundle.putString("major",majortext.getText().toString());
                    bundle.putString("xueli",xuelitext.getText().toString());
                    bundle.putString("start",starttext.getText().toString());
                    bundle.putString("end",endtext.getText().toString());
                    bundle.putString("exp",editText.getText().toString());
                    intent.putExtras(bundle);
                    uploadEduexp();
                    setResult(RESULT_OK,intent);
                    finish();
                }
                break;
            case R.id.edu_school:
                Intent intent1=new Intent(AddeduActivity.this,AddeduschoolActivity.class);
                startActivityForResult(intent1,105);
                break;
            case R.id.xueli:
                currentpop=createPopupWindow(getWindow().getDecorView(),R.layout.pop_wheel_layout);
                final WheelView wheelView=(WheelView)currentpop.getContentView().findViewById(R.id.wheelview);
                wheelView.setCyclic(false);
                final List<String> list=new ArrayList<>();
                list.add("博士");
                list.add("硕士");
                list.add("本科");
                list.add("大专");
                list.add("高中/中专");
                list.add("其他");
                wheelView.setAdapter(new WheelAdapter() {
                    @Override
                    public int getItemsCount() {
                        return list.size();
                    }

                    @Override
                    public Object getItem(int index) {
                        return list.get(index);
                    }

                    @Override
                    public int indexOf(Object o) {
                        return list.indexOf(o);
                    }
                });
                final TextView yes=(TextView)currentpop.getContentView().findViewById(R.id.yes) ;
                TextView no=(TextView)currentpop.getContentView().findViewById(R.id.no);
                xuelitext=(TextView)findViewById(R.id.xueli_text) ;
                wheelView.setOnItemSelectedListener(new OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(final int index) {
                        yes.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                xuelitext.setTextColor(Color.BLACK);
                                xuelitext.setText(list.get(index));
                                currentpop.dismiss();
                            }
                        });

                    }
                });

                no.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        currentpop.dismiss();
                    }
                });
                break;
            case R.id.edu_start:
                currentpop=createPopupWindow(getWindow().getDecorView(),R.layout.pop_date_layout);
                final TextView yes1=(TextView)currentpop.getContentView().findViewById(R.id.yes) ;
                TextView no1=(TextView)currentpop.getContentView().findViewById(R.id.no);
                starttext=(TextView)findViewById(R.id.start_text);
                final DatePicker datePicker=(DatePicker)currentpop.getContentView().findViewById(R.id.datePicker1);
                yes1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String str=datePicker.getYear()+"年"+(datePicker.getMonth()+1)+"月"+datePicker.getDayOfMonth()+"日";
                        starttext.setTextColor(Color.BLACK);
                        starttext.setText(str);
                        currentpop.dismiss();
                    }
                });
                no1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        currentpop.dismiss();
                    }
                });
                break;
            case R.id.edu_end:
                currentpop=createPopupWindow(getWindow().getDecorView(),R.layout.pop_date_layout);
                final TextView yes2=(TextView)currentpop.getContentView().findViewById(R.id.yes) ;
                TextView no2=(TextView)currentpop.getContentView().findViewById(R.id.no);
                endtext=(TextView)findViewById(R.id.end_text);
                final DatePicker datePicker1=(DatePicker)currentpop.getContentView().findViewById(R.id.datePicker1);
                yes2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String str=datePicker1.getYear()+"年"+(datePicker1.getMonth()+1)+"月"+datePicker1.getDayOfMonth()+"日";
                        endtext.setTextColor(Color.BLACK);
                        endtext.setText(str);
                        currentpop.dismiss();
                    }
                });
                no2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        currentpop.dismiss();
                    }
                });
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==105){
            if(resultCode==RESULT_OK){
                String school=data.getExtras().getString("school");
                Log.d("msg","school："+school);
                school_text.setTextColor(Color.BLACK);
                school_text.setText(school);
            }
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

    public void uploadEduexp(){
        MyApplication myApplication=(MyApplication)getApplication();
        final CurrentUser currentUser=myApplication.getLoginUser();
        final RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        final String url="http://119.23.13.19:8080/Aliyun/QueryCVServlet";
        StringRequest request=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("msg",response);
                try {
                    JSONObject jsonObject=new JSONObject(response);
                    JSONObject result=new JSONObject(jsonObject.getString("result"));
                    if(result.getString("school").equals("")){
                        String url1="http://119.23.13.19:8080/Aliyun/EnterCVServlet";
                        StringRequest request1=new StringRequest(Request.Method.POST, url1, new Response.Listener<String>() {
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
                                map.put("tele",currentUser.getTele());
                                map.put("school",school_text.getText().toString());
                                map.put("major",majortext.getText().toString());
                                map.put("xueli",xuelitext.getText().toString());
                                map.put("startday",starttext.getText().toString());
                                map.put("endday",endtext.getText().toString());
                                map.put("eduexp",editText.getText().toString());
                                map.put("type","edu");
                                return map;
                            }
                        };
                        requestQueue.add(request1);
                    }else {
                        String url1="http://119.23.13.19:8080/Aliyun/UpdateCVServlet";
                        StringRequest request1=new StringRequest(Request.Method.POST, url1, new Response.Listener<String>() {
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
                                map.put("tele",currentUser.getTele());
                                map.put("school",school_text.getText().toString());
                                map.put("major",majortext.getText().toString());
                                map.put("xueli",xuelitext.getText().toString());
                                map.put("startday",starttext.getText().toString());
                                map.put("endday",endtext.getText().toString());
                                map.put("eduexp",editText.getText().toString());
                                map.put("type","edu");
                                return map;
                            }
                        };
                        requestQueue.add(request1);
                    }
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
                map.put("type","edu");
                map.put("tele",currentUser.getTele());
                return map;
            }
        };
        requestQueue.add(request);
    }
}
