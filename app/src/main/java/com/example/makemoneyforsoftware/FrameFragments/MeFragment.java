package com.example.makemoneyforsoftware.FrameFragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.signature.ObjectKey;
import com.example.makemoneyforsoftware.CV.MycvActivity;
import com.example.makemoneyforsoftware.CompleteUserInfoActivity;
import com.example.makemoneyforsoftware.Filehelper;
import com.example.makemoneyforsoftware.LoginFragmentPackage.CurrentUser;
import com.example.makemoneyforsoftware.MyApplicatiionActivity;
import com.example.makemoneyforsoftware.MyApplication;
import com.example.makemoneyforsoftware.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;
//import okhttp3.Call;
//import okhttp3.Callback;
//import okhttp3.MediaType;
//import okhttp3.MultipartBody;
//import okhttp3.OkHttpClient;
//import okhttp3.Request;
//import okhttp3.RequestBody;
//import okhttp3.Response;

import static android.app.Activity.RESULT_OK;

public class MeFragment extends Fragment {
    private MyApplication myApplication;
    private CurrentUser currentUser;
    private View view;
    private CircleImageView head_img;
    public View.OnClickListener mylistener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getActivity(), MyApplicatiionActivity.class);
            Bundle bundle = new Bundle();
            switch (v.getId()) {
                case R.id.baoming_lin:
                    bundle.putInt("page", 0);
                    break;
                case R.id.luyong_lin:
                    bundle.putInt("page", 1);
                    break;
                case R.id.daogang_lin:
                    bundle.putInt("page", 2);
                    break;
                case R.id.jiesuan_lin:
                    bundle.putInt("page", 3);
                    break;
            }
            intent.putExtras(bundle);
            startActivity(intent);
        }
    };
    public View.OnClickListener myanotherlistener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.complete_userinfo:
                    Intent intent = new Intent(getActivity(), CompleteUserInfoActivity.class);
                    startActivity(intent);
                    break;
                case R.id.me_userheadimg:
                    Intent intent1 = new Intent("android.intent.action.GET_CONTENT");
                    intent1.setType("image/*");
                    startActivityForResult(intent1, 101);
                    break;
                case R.id.jianli_rel:
                    Intent intent2=new Intent(getActivity(), MycvActivity.class);
                    if(currentUser.getName().equals("")){
                        Toast.makeText(getContext(),"请先完善个人信息",Toast.LENGTH_SHORT).show();
                    }else {
                        startActivity(intent2);
                    }
                    break;
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.me_layout, container, false);
        LinearLayout one = (LinearLayout) view.findViewById(R.id.baoming_lin);
        LinearLayout two = (LinearLayout) view.findViewById(R.id.luyong_lin);
        LinearLayout three = (LinearLayout) view.findViewById(R.id.daogang_lin);
        LinearLayout four = (LinearLayout) view.findViewById(R.id.jiesuan_lin);
        LinearLayout complete = (LinearLayout) view.findViewById(R.id.complete_userinfo);
        TextView tele = (TextView) view.findViewById(R.id.me_tele);
        TextView name=(TextView)view.findViewById(R.id.me_name);
        RelativeLayout jianli=(RelativeLayout)view.findViewById(R.id.jianli_rel);
        head_img = (CircleImageView) view.findViewById(R.id.me_userheadimg);
        one.setOnClickListener(mylistener);
        two.setOnClickListener(mylistener);
        three.setOnClickListener(mylistener);
        four.setOnClickListener(mylistener);
        complete.setOnClickListener(myanotherlistener);
        head_img.setOnClickListener(myanotherlistener);
        jianli.setOnClickListener(myanotherlistener);
        myApplication = (MyApplication) getActivity().getApplication();
        currentUser = myApplication.getLoginUser();
        if (currentUser.getTele() != null) {
            Log.d("msg", "tele" + currentUser.getTele());
            tele.setText(currentUser.getTele());
        }
        if(currentUser.getUrl()!=null){
            SharedPreferences pref=getActivity().getSharedPreferences("data",Context.MODE_PRIVATE);
            String time=pref.getString("time","");
            Glide.with(view).load(currentUser.getUrl()).apply(new RequestOptions().placeholder(R.drawable.user).signature(new ObjectKey(time))).into(head_img);
        }
        if (currentUser.getName()!=null){
            name.setText(currentUser.getName());
        }
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 101) {
            if (resultCode == RESULT_OK) {
                Uri uri = data.getData();
                Glide.with(view).load(uri).into(head_img);
                String path = Filehelper.getPath(getContext(), uri);
                String filepath = path;
                File file = new File(filepath);
                String str = currentUser.getTele() + "headimg";
                String filename = str + path.substring(path.lastIndexOf("."));
                Log.d("msg", filename);
//                RequestBody fileBody = RequestBody.create(MediaType.parse("application/octet-stream"), file);
//                RequestBody requestBody = new MultipartBody.Builder()
//                        .setType(MultipartBody.FORM)
//                        .addFormDataPart("tele", currentUser.getTele())
//                        .addFormDataPart("type","user")
//                        .addPart(okhttp3.Headers.of(
//                                "Content-Disposition",
//                                "form-data; name=\"username\"")
//                                , RequestBody.create(null, "HGR"))
//                        .addPart(okhttp3.Headers.of(
//                                "Content-Disposition",
//                                "form-data; name=\"mFile\"; filename=\"" + filename + "\""), fileBody)
//                        .build();
//                Request request = new Request.Builder()
//                        .url("http://119.23.13.19:8080/Aliyun/UploadHandleServlet")
//                        .post(requestBody).build();
//                OkHttpClient okHttpClient = new OkHttpClient();
//                Call call = okHttpClient.newCall(request);
//                call.enqueue(new Callback() {
//                    @Override
//                    public void onFailure(Call call, IOException e) {
//                        Log.e("msg", "failure upload!");
//                    }
//
//                    @Override
//                    public void onResponse(Call call, Response response) throws IOException {
//                        JSONObject jsonObject = null;
//                        try {
//                            jsonObject = new JSONObject(response.body().string());
//                            JSONObject jsonObject1=(JSONObject) jsonObject.get("params");
//                            Log.d("msg","changeUrl:"+jsonObject1.getString("url"));
//                            currentUser.setUrl(jsonObject1.getString("url"));
//                            myApplication.userLogin(currentUser);
//                            SharedPreferences.Editor editor=getActivity().getSharedPreferences("data", Context.MODE_PRIVATE).edit();
//                            String time=String.valueOf(System.currentTimeMillis());
//                            Log.d("msg","time:"+time);
//                            editor.putString("time",time);
//                            editor.apply();
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                        Log.d("msg", jsonObject.toString());
//                        Log.i("msg", "success upload!");
//                    }
//                });

            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
