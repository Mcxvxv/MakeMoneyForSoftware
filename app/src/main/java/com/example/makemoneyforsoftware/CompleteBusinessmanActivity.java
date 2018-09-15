package com.example.makemoneyforsoftware;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

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
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.makemoneyforsoftware.LoginFragmentPackage.CurrentUser;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import jp.wasabeef.glide.transformations.CropSquareTransformation;

public class CompleteBusinessmanActivity extends AppCompatActivity {

    private RadioButton radioButton;
    public LocationClient mLocationClient;
    private EditText store_name;
    private EditText head_name;
    private EditText mail;
    private EditText location;
    private EditText tele;
    private RadioGroup rg;
    static final int DATE_DIALOG_ID = 1;
    private int year;
    private int month;
    private int day;
    private TextView birthday;
    private CurrentUser currentUser;
    private MyApplication myApplication;

    private View.OnClickListener mylistener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.save:
                    uploaduserData();
                    break;
                case R.id.edit_location_text_button:
                    mLocationClient.start();
                    break;
                case R.id.back:
                    finish();
                    break;
                case R.id.birthday_lin:
                    showDialog(DATE_DIALOG_ID);
                    break;
            }
        }
    };

    private DatePickerDialog.OnDateSetListener myDatelistener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            CompleteBusinessmanActivity.this.year = year;
            CompleteBusinessmanActivity.this.month = month;
            CompleteBusinessmanActivity.this.day = dayOfMonth;
//            Toast.makeText(JobTimeActivity.this,year+"年"+(month+1)+"月"+dayOfMonth+"日",Toast.LENGTH_SHORT).show();
            birthday.setText(year + "年" + (month + 1) + "月" + dayOfMonth + "日");
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete_businessman);
        final Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        mLocationClient = new LocationClient(getApplicationContext());
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
        Button save = (Button) findViewById(R.id.save);
        ImageView back = (ImageView) findViewById(R.id.back);
        store_name = (EditText) findViewById(R.id.store_name);
        head_name=(EditText)findViewById(R.id.head_name);
        tele = (EditText) findViewById(R.id.tele);
        mail = (EditText) findViewById(R.id.mail);
        location = (EditText) findViewById(R.id.address);
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.edit_location_text_button);
        LinearLayout birth = (LinearLayout) findViewById(R.id.birthday_lin);
        birthday = (TextView) findViewById(R.id.birth_add);
        myApplication = (MyApplication) getApplication();
        ImageView img = (ImageView) findViewById(R.id.user_img);
        currentUser = myApplication.getLoginUser();
        RequestOptions requestOptions = new RequestOptions().transform(new CropSquareTransformation());
        Glide.with(this).load(currentUser.getUrl()).apply(requestOptions).into(img);
        tele.setText(currentUser.getTele());
        rg = (RadioGroup) findViewById(R.id.radiugroup);
        radioButton = (RadioButton) findViewById(rg.getCheckedRadioButtonId());
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                radioButton = (RadioButton) findViewById(checkedId);
            }
        });
        linearLayout.setOnClickListener(mylistener);
        save.setOnClickListener(mylistener);
        back.setOnClickListener(mylistener);
        birth.setOnClickListener(mylistener);
        queryUserInfo();
    }

    public class MyLocationListener extends BDAbstractLocationListener {
        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            StringBuilder currentPosition = new StringBuilder();
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
            location.setText(currentPosition);
        }
    }

    public void queryUserInfo() {
        final String teletext = tele.getText().toString();
        RequestQueue mQueue = Volley.newRequestQueue(getApplicationContext());
        String url = "http://119.23.13.19:8080/Aliyun/QueryUserInfoServlet";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("msg", "userinfo" + response);
                try {
                    JSONObject jsonObject = new JSONObject(new JSONObject(response).getString("result"));
                    head_name.setText(jsonObject.getString("businame"));
                    store_name.setText(jsonObject.getString("storename"));
                    mail.setText(jsonObject.getString("busimail"));
                    location.setText(jsonObject.getString("busilocation"));
                    birthday.setText(jsonObject.getString("busiopendate"));
                    if (jsonObject.getString("sex").equals("男")) {
                        rg.check(R.id.man);
                    } else {
                        rg.check(R.id.woman);
                    }
                    currentUser.setName(head_name.getText().toString());
                    currentUser.setStorename(store_name.getText().toString());
                    currentUser.setMail(mail.getText().toString());
                    currentUser.setLocation(location.getText().toString());
                    currentUser.setOpendate(birthday.getText().toString());
                    currentUser.setSex(jsonObject.getString("sex"));
                    myApplication.userLogin(currentUser);
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
                map.put("tele", teletext);
                map.put("type","businessman");
                return map;
            }
        };
        mQueue.add(stringRequest);

    }

    public void uploaduserData() {
        final String storenametext = store_name.getText().toString();
        final String headnametext=head_name.getText().toString();
        final String sextext = radioButton.getText().toString();
        final String mailtext = mail.getText().toString();
        final String locationtext = location.getText().toString();
        final String teletext = tele.getText().toString();
        final String birthtext=birthday.getText().toString();
        RequestQueue mQueue = Volley.newRequestQueue(getApplicationContext());
        String url = "http://119.23.13.19:8080/Aliyun/RegisterServlet";
        if (headnametext.equals("")||storenametext.equals("") || sextext.equals("") || mailtext.equals("") || locationtext.equals("") || teletext.equals("")||birthtext.equals("")) {
            Toast.makeText(getApplicationContext(), "请保证信息填写完整", Toast.LENGTH_SHORT).show();
        } else {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.d("msg", response);
                    Toast.makeText(getApplicationContext(), "保存个人信息成功", Toast.LENGTH_SHORT).show();
                    finish();
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
                    map.put("businame",headnametext);
                    map.put("storename",storenametext);
                    map.put("sex", sextext);
                    map.put("busimail", mailtext);
                    map.put("busilocation", locationtext);
                    map.put("busitele", teletext);
                    map.put("busiopendate",birthtext);
                    map.put("type", "updateBusinessmanInfo");
                    return map;
                }
            };
            mQueue.add(stringRequest);
        }
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_DIALOG_ID:
                DatePickerDialog datePickerDialog = new DatePickerDialog(CompleteBusinessmanActivity.this, AlertDialog.THEME_HOLO_LIGHT, myDatelistener, year, month, day);
                return datePickerDialog;
        }
        return null;
    }
}
