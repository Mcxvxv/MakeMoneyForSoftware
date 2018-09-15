package com.example.makemoneyforsoftware.BusinessmanApplicationFragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.util.Attributes;

import com.example.makemoneyforsoftware.LoginFragmentPackage.CurrentUser;
import com.example.makemoneyforsoftware.MyApplication;
import com.example.makemoneyforsoftware.R;
import com.example.makemoneyforsoftware.ReleaseJobPackage.Job;
import com.scwang.smartrefresh.header.MaterialHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BusinessmanReleaseJobsActivity extends AppCompatActivity {


    private ListView mListView;
    private ListViewAdapter mAdapter;
    private Context mContext = this;
    private List<Job> list;
    private SmartRefreshLayout refreshLayout;
    private CurrentUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_businessman_release_jobs);
        MyApplication myApplication=(MyApplication)getApplication();
        currentUser=myApplication.getLoginUser();
        ImageView back=(ImageView)findViewById(R.id.back);
        mListView = (ListView) findViewById(R.id.listview);
        refreshLayout = (SmartRefreshLayout)findViewById(R.id.refresh);//设置 Header 为 贝塞尔雷达 样式
        refreshLayout.setRefreshHeader(new MaterialHeader(this));
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
//                refreshlayout.finishRefresh(2000/*,false*/);//传入false表示刷新失败
                initList();
            }
        });
        initList();
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
//            ActionBar actionBar = getActionBar();
//            if (actionBar != null) {
//                actionBar.setTitle("ListView");
//            }
//        }
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ((SwipeLayout) (mListView.getChildAt(position - mListView.getFirstVisiblePosition()))).open(true);
            }
        });
        mListView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.e("ListView", "OnTouch");
                return false;
            }
        });
        mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(mContext, "OnItemLongClickListener", Toast.LENGTH_SHORT).show();
                return true;
            }
        });
        mListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                Log.e("ListView", "onScrollStateChanged");
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });

        mListView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.e("ListView", "onItemSelected:" + position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Log.e("ListView", "onNothingSelected:");
            }
        });

    }

    public void initList(){
        list=new ArrayList<>();
        String url="http://119.23.13.19:8080/Aliyun/QueryJobByBusiteleServlet";
        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("msg",response);
                try {
                    JSONArray jsonArray=new JSONArray(response);
                    list=new ArrayList<>();
                    for(int i=0;i<jsonArray.length();i++){
                        JSONObject jsonObject=jsonArray.getJSONObject(i).getJSONObject("job");
                        Log.d("msg",jsonObject.toString());
                        Job job=new Job();
                        job.setJobtitle(jsonObject.getString("jobtitle"));
                        Log.d("msg","title："+jsonObject.getString("jobtitle"));
                        job.setJobtype(jsonObject.getString("jobtype"));
                        Log.d("msg","type："+jsonObject.getString("jobtype"));
                        job.setJobsalary(jsonObject.getString("jobsalary"));
                        Log.d("msg","salary："+jsonObject.getString("jobsalary"));
                        job.setJobrequirenum(jsonObject.getString("jobrequirenum"));
                        Log.d("msg","peoplenum："+jsonObject.getString("jobrequirenum"));
                        job.setJobrequiresex(jsonObject.getString("jobrequiresex"));
                        Log.d("msg","sex："+jsonObject.getString("jobrequiresex"));
                        job.setJobrequirecycle(jsonObject.getString("jobrequirecycle"));
                        Log.d("msg","cycle："+jsonObject.getString("jobrequirecycle"));
                        job.setJobrequireage(jsonObject.getString("jobrequireage"));
                        Log.d("msg","range："+jsonObject.getString("jobrequireage"));
                        job.setJobdiscrip(jsonObject.getString("jobdiscrip"));
                        Log.d("msg","discrip_text："+jsonObject.getString("jobdiscrip"));
                        job.setJobcontent(jsonObject.getString("jobcontent"));
                        Log.d("msg","content_text："+jsonObject.getString("jobcontent"));
                        job.setJobstartday(jsonObject.getString("jobstartday"));
                        Log.d("msg","startday："+jsonObject.getString("jobstartday"));
                        job.setJobendday(jsonObject.getString("jobendday"));
                        Log.d("msg","endday："+jsonObject.getString("jobendday"));
                        job.setJobworktime(jsonObject.getString("jobworktime"));
                        Log.d("msg","time："+jsonObject.getString("jobworktime"));
                        job.setJoblocation(jsonObject.getString("joblocation"));
                        Log.d("msg","location："+jsonObject.getString("joblocation"));
                        job.setBusinessmantele(jsonObject.getString("businessmantele"));
                        job.setJobreleaseday(jsonObject.getString("jobreleaseday"));
                        job.setJobid(jsonObject.getInt("jobid"));
                        list.add(job);
                    }
                    Log.d("msg","Thread："+Thread.currentThread().getName());
                    mAdapter = new ListViewAdapter(BusinessmanReleaseJobsActivity.this,list);
                    mListView.setAdapter(mAdapter);
                    mAdapter.setMode(Attributes.Mode.Single);
                    refreshLayout.finishRefresh();
//                    getActivity().runOnUiThread(new Runnable() {
////                        @Override
////                        public void run() {
////
////                        }
////                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map=new HashMap<>();
                map.put("tele",currentUser.getTele());
                return map;
            }
        };
        requestQueue.add(stringRequest);
    }

}
