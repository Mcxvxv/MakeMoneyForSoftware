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
import com.example.makemoneyforsoftware.BusinessmanApplicationActivity;
import com.example.makemoneyforsoftware.BusinessmanApplicationFragment.BusinessmanReleaseJobsActivity;
import com.example.makemoneyforsoftware.CompleteBusinessmanActivity;
import com.example.makemoneyforsoftware.Filehelper;
import com.example.makemoneyforsoftware.LoginFragmentPackage.CurrentUser;
import com.example.makemoneyforsoftware.MyApplication;
import com.example.makemoneyforsoftware.R;
import com.example.makemoneyforsoftware.ReleaseJobPackage.ReleaseJobActivity;
import com.example.makemoneyforsoftware.Users.Businessman;

import java.io.File;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadFileListener;
import de.hdodenhof.circleimageview.CircleImageView;


import static android.app.Activity.RESULT_OK;

public class BusinessmanMeFragment extends Fragment{

    private MyApplication myApplication;
    private CurrentUser currentUser;
    private View view;
    private CircleImageView head_img;
    public View.OnClickListener mylistener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getActivity(), BusinessmanApplicationActivity.class);
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
                case R.id.my_releasejob_rel:
                    Intent intent3=new Intent(getActivity(), BusinessmanReleaseJobsActivity.class);
                    startActivity(intent3);
                    break;
                case R.id.release_rel:
                    Intent intent2=new Intent(getActivity(), ReleaseJobActivity.class);
                    startActivity(intent2);
                    break;
                case R.id.complete_userinfo:
                    Intent intent = new Intent(getActivity(), CompleteBusinessmanActivity.class);
                    startActivity(intent);
                    break;
                case R.id.me_userheadimg:
                    Intent intent1 = new Intent("android.intent.action.GET_CONTENT");
                    intent1.setType("image/*");
                    startActivityForResult(intent1, 101);
                    break;
            }
        }
    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bmob.initialize(getContext(),"273d85ee821227067102d46670d56565");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.businessmanmelayout,container,false);
        RelativeLayout release=(RelativeLayout)view.findViewById(R.id.release_rel);
        RelativeLayout jobs=(RelativeLayout)view.findViewById(R.id.my_releasejob_rel);
        LinearLayout complete = (LinearLayout) view.findViewById(R.id.complete_userinfo);
        TextView tele = (TextView) view.findViewById(R.id.me_tele);
        TextView name=(TextView)view.findViewById(R.id.me_name);
        LinearLayout one = (LinearLayout) view.findViewById(R.id.baoming_lin);
        LinearLayout two = (LinearLayout) view.findViewById(R.id.luyong_lin);
        LinearLayout three = (LinearLayout) view.findViewById(R.id.daogang_lin);
        LinearLayout four = (LinearLayout) view.findViewById(R.id.jiesuan_lin);
        one.setOnClickListener(mylistener);
        two.setOnClickListener(mylistener);
        three.setOnClickListener(mylistener);
        four.setOnClickListener(mylistener);
        head_img = (CircleImageView) view.findViewById(R.id.me_userheadimg);
        release.setOnClickListener(myanotherlistener);
        jobs.setOnClickListener(myanotherlistener);
        complete.setOnClickListener(myanotherlistener);
        head_img.setOnClickListener(myanotherlistener);
        myApplication = (MyApplication) getActivity().getApplication();
        currentUser = myApplication.getLoginUser();
        if (!currentUser.getTele().equals("")) {
            Log.d("msg", "tele" + currentUser.getTele());
            tele.setText(currentUser.getTele());
        }
        if(!currentUser.getUrl().equals("")){
            SharedPreferences pref=getActivity().getSharedPreferences("data", Context.MODE_PRIVATE);
            String time=pref.getString("time","");
            Glide.with(view).load(currentUser.getUrl()).apply(new RequestOptions().placeholder(R.drawable.user).signature(new ObjectKey(time))).into(head_img);
        }
        if (!currentUser.getName().equals("")){
            name.setText(currentUser.getName());
        }else {
            name.setText("用户名");
        }
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 101) {
            if (resultCode == RESULT_OK) {
                final Uri uri = data.getData();
                Glide.with(view).load(uri).into(head_img);
                String path = Filehelper.getPath(getContext(), uri);
                File file = new File(path);
                final BmobFile bmobFile=new BmobFile(file);
                bmobFile.uploadblock(new UploadFileListener() {
                    @Override
                    public void done(BmobException e) {
                        if(e==null){
                            Toast.makeText(getContext(),"上传成功",Toast.LENGTH_SHORT).show();
                            String url=bmobFile.getUrl();
                            currentUser.setUrl(url);
                            Businessman businessman=new Businessman();
                            businessman.setUrl(url);
                            businessman.update(currentUser.getObjectid(), new UpdateListener() {
                                @Override
                                public void done(BmobException e) {
                                    if(e==null){

                                    }
                                }
                            });
                        }
                    }
                });
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
