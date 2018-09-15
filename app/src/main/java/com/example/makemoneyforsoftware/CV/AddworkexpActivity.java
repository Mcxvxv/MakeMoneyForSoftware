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
import com.example.makemoneyforsoftware.LoginFragmentPackage.CurrentUser;
import com.example.makemoneyforsoftware.MyApplication;
import com.example.makemoneyforsoftware.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class AddworkexpActivity extends AppCompatActivity {


    private PopupWindow currentpop;
    private EditText editText;
    private EditText company;
    private TextView starttext;
    private TextView endtext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addworkexp);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = AddworkexpActivity.this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.black));
        }
        company=(EditText)findViewById(R.id.company);
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

    public void onaddworkexpclick(View view){
        switch (view.getId()){
            case R.id.save:
                Intent intent=getIntent();
                Bundle bundle=new Bundle();
                if(company.getText().toString().equals("")||starttext.getText().toString().equals("")||endtext.getText().toString().equals("")||editText.getText().toString().equals("")){
                    Toast.makeText(this,"请将信息填写完整",Toast.LENGTH_SHORT).show();
                }else {
                    bundle.putString("company",company.getText().toString());
                    bundle.putString("start",starttext.getText().toString());
                    bundle.putString("end",endtext.getText().toString());
                    bundle.putString("workexp",editText.getText().toString());
                    intent.putExtras(bundle);
                    uploadEduexp();
                    setResult(RESULT_OK,intent);
                    finish();
                }
                break;
            case R.id.back:
                finish();
                break;
            case R.id.adk_start:
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
            case R.id.adk_end:
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
                    if(result.getString("company").equals("")){
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
                                map.put("company",company.getText().toString());
                                map.put("startday",starttext.getText().toString());
                                map.put("endday",endtext.getText().toString());
                                map.put("workexp",editText.getText().toString());
                                map.put("type","work");
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
                                map.put("company",company.getText().toString());
                                map.put("startday",starttext.getText().toString());
                                map.put("endday",endtext.getText().toString());
                                map.put("workexp",editText.getText().toString());
                                map.put("type","work");
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
                map.put("type","work");
                map.put("tele",currentUser.getTele());
                return map;
            }
        };
        requestQueue.add(request);
    }
}
