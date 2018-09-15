package com.example.makemoneyforsoftware;

import android.content.Context;
import android.graphics.PorterDuff;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.makemoneyforsoftware.BusinessmanApplicationFragment.User;
import com.example.makemoneyforsoftware.BusinessmanApplicationFragment.UserAdapter;
import com.example.makemoneyforsoftware.ReleaseJobPackage.Job;
import com.loopeer.cardstack.CardStackView;
import com.loopeer.cardstack.StackAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class MyAnotherStackAdapter extends StackAdapter<Job> {

    private int type;
    public static Integer[] TEST_DATAS = new Integer[]{
            R.color.color_1,
            R.color.color_2,
            R.color.color_3,
            R.color.color_4,
            R.color.color_5,
            R.color.color_6,
            R.color.color_7,
            R.color.color_8,
            R.color.color_9,
            R.color.color_10,
            R.color.color_11,
            R.color.color_12,
            R.color.color_13,
            R.color.color_14,
            R.color.color_15,
            R.color.color_16,
            R.color.color_17,
            R.color.color_18,
            R.color.color_19,
            R.color.color_20,
            R.color.color_21,
            R.color.color_22,
            R.color.color_23,
            R.color.color_24,
            R.color.color_25,
            R.color.color_26
    };
    public Context mContext;

    public MyAnotherStackAdapter(Context context,int type) {
        super(context);
        mContext = context;
        this.type=type;
    }

    @Override
    public Context getContext() {
        return mContext;
    }

    public int getType() {
        return type;
    }

    @Override
    public void bindView(Job data, int position, CardStackView.ViewHolder holder) {

        if (holder instanceof MyAnotherStackAdapter.ItemViewHolder) {
            MyAnotherStackAdapter.ItemViewHolder h = (MyAnotherStackAdapter.ItemViewHolder) holder;
            h.onBind(data, position);
        }

    }


    @Override
    protected CardStackView.ViewHolder onCreateView(ViewGroup parent, int viewType) {
        View view;
        view = getLayoutInflater().inflate(R.layout.another_list_card_layout, parent, false);
        return new MyAnotherStackAdapter.ItemViewHolder(view);
    }

    class ItemViewHolder extends CardStackView.ViewHolder {
        View mLayout;
        View mContainerContent;
        ImageView jobtype;
        TextView mTextTitle;
        TextView jobaddress;
        TextView jobaslary;
        TextView jobdate;
        ListView listView;
        private List<User> list;

        public ItemViewHolder(View view) {
            super(view);
            mLayout = view.findViewById(R.id.frame_list_card_item);
            mContainerContent = view.findViewById(R.id.container_list_content);
            jobtype = (ImageView) view.findViewById(R.id.jobtype);
            mTextTitle = (TextView) view.findViewById(R.id.text_list_card_title);
            jobaddress = (TextView) view.findViewById(R.id.jobaddress);
            jobaslary = (TextView) view.findViewById(R.id.jobsalary);
            jobdate = (TextView) view.findViewById(R.id.jobdate);
            listView = (ListView) view.findViewById(R.id.listview);
        }


        @Override
        public void onItemExpand(boolean b) {
            mContainerContent.setVisibility(b ? View.VISIBLE : View.GONE);
        }


        public void onBind(Job data, int position) {
            Random random = new Random();
            int i = random.nextInt(26);
            mLayout.getBackground().setColorFilter(ContextCompat.getColor(getContext(), TEST_DATAS[i]), PorterDuff.Mode.SRC_IN);
            mTextTitle.setText(data.getJobtitle());
            if (data.getJobtype().equals("外卖送餐")) {
                jobtype.setImageResource(R.drawable.outsell);
            } else if (data.getJobtype().equals("餐饮服务")) {
                jobtype.setImageResource(R.drawable.waiter);
            } else if (data.getJobtype().equals("传单调研")) {
                jobtype.setImageResource(R.drawable.diaoyan);
            } else if (data.getJobtype().equals("学生家教")) {
                jobtype.setImageResource(R.drawable.teacher_desk);
            } else if (data.getJobtype().equals("临时工")) {
                jobtype.setImageResource(R.drawable.worker);
            } else {
                jobtype.setImageResource(R.drawable.other);
            }
            jobaddress.setText(data.getJoblocation());
            jobaslary.setText(data.getJobsalary());
            jobdate.setText(data.getJobreleaseday());
            initListView(data.getJobid(), listView);
        }

        public void setListViewHeightBasedOnChildren(ListView listView) {
            // 获取ListView对应的Adapter
            ListAdapter listAdapter = listView.getAdapter();
            if (listAdapter == null) {
                return;
            }
            int totalHeight = 0;
            for (int i = 0; i < listAdapter.getCount(); i++) { // listAdapter.getCount()返回数据项的数目
                View listItem = listAdapter.getView(i, null, listView);
                listItem.measure(0, 0); // 计算子项View 的宽高
                totalHeight += listItem.getMeasuredHeight(); // 统计所有子项的总高度
            }
            ViewGroup.LayoutParams params = listView.getLayoutParams();
            params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
            // listView.getDividerHeight()获取子项间分隔符占用的高度
            // params.height最后得到整个ListView完整显示需要的高度
            listView.setLayoutParams(params);
        }

        public void initListView(final int jobid, final ListView listView) {
            final RequestQueue requestQueue = Volley.newRequestQueue(getContext().getApplicationContext());
            //请求地址
            final String url = "http://119.23.13.19:8080/Aliyun/QueryApplicationUserServlet";    //注①
            //创建StringRequest，定义字符串请求的请求方式为POST(省略第一个参数会默认为GET方式)
            final StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.d("msg", response);
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        list = new ArrayList<>();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i).getJSONObject("user");
                            Log.d("msg", jsonObject.toString());
                            User user = new User();
                            user.setName(jsonObject.getString("username"));
                            user.setUrl(jsonObject.getString("userheadimgurl"));
                            user.setTele(jsonObject.getString("tele"));
                            user.setMail(jsonObject.getString("mail"));
                            user.setLocation(jsonObject.getString("loaction"));
                            user.setSex(jsonObject.getString("sex"));
//                        user.setBirthday(jsonObject.getString("birthday"));
                            user.setJobid(jobid);
                            list.add(user);
                        }
                        Log.d("msg", "Thread：" + Thread.currentThread().getName());
                        UserAdapter adapter = new UserAdapter(getContext(),R.layout.baominguserlayout, list,type);
                        listView.setAdapter(adapter);
                        setListViewHeightBasedOnChildren(listView);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    //做自己的响应错误操作，如Toast提示（“请稍后重试”等）
                    Log.e("TAG", error.getMessage(), error);
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("jobid", Integer.toString(jobid));
                    params.put("type", Integer.toString(type));
                    return params;
                }
            };
            requestQueue.add(request);
        }

    }

}

