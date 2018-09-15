package com.example.makemoneyforsoftware;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
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
import com.example.makemoneyforsoftware.AllJob.JobAdapter;
import com.example.makemoneyforsoftware.AllJob.JobItemActivity;
import com.example.makemoneyforsoftware.AllJob.MyJobListView;
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

public class SearchActivity extends AppCompatActivity {
    private LinearLayout hot;
//    private ListView listView;
    private EditText editText;
    private List<Job> list;
    private SmartRefreshLayout refreshLayout;
    private MyJobListView listView;
    private View.OnClickListener mylistener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.seaech_button:
                    hot.setVisibility(View.GONE);
                    listView.setVisibility(View.VISIBLE);
                    initList();
                    break;
                case R.id.back:
                    finish();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = SearchActivity.this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.black));
        }
        setContentView(R.layout.activity_search);
        ImageView back=(ImageView)findViewById(R.id.back);
        editText=(EditText)findViewById(R.id.search_edit);
        TextView search=(TextView)findViewById(R.id.seaech_button);
        hot=(LinearLayout)findViewById(R.id.hot);
        listView=(MyJobListView)findViewById(R.id.listview);
        search.setOnClickListener(mylistener);
        back.setOnClickListener(mylistener);
        refreshLayout = (SmartRefreshLayout)findViewById(R.id.refresh);//设置 Header 为 贝塞尔雷达 样式
        refreshLayout.setRefreshHeader(new MaterialHeader(SearchActivity.this));
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
//                refreshlayout.finishRefresh(2000/*,false*/);//传入false表示刷新失败
                initList();
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(SearchActivity.this, JobItemActivity.class);
                Bundle bundle=new Bundle();
                bundle.putSerializable("job",list.get(position));
                bundle.putInt("type",0);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    public void initList(){
        RequestQueue queue= Volley.newRequestQueue(getApplicationContext());
        String url="http://119.23.13.19:8080/Aliyun/QueryJobByType";
        StringRequest request=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
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
                    JobAdapter adapter=new JobAdapter(SearchActivity.this,R.layout.jobitemlayout,list);
                    listView.setAdapter(adapter);
                    listView.setFocusable(false);
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
               Log.d("msg",error.getMessage());
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map=new HashMap<>();
                map.put("type",editText.getText().toString());
                return map;
             }
        };
        queue.add(request);
    }
}
