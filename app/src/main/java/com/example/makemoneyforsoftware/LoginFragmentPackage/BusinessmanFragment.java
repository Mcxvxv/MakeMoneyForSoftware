package com.example.makemoneyforsoftware.LoginFragmentPackage;

import android.content.Intent;
import android.os.BaseBundle;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.makemoneyforsoftware.BusinessmanFrameActivity;
import com.example.makemoneyforsoftware.FrameActivity;
import com.example.makemoneyforsoftware.MyApplication;
import com.example.makemoneyforsoftware.R;
import com.example.makemoneyforsoftware.RegesterActivityPackage.Register_businessman_Activity;
import com.example.makemoneyforsoftware.Users.Businessman;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class BusinessmanFragment extends Fragment{
        private EditText tele;
        private EditText psd;
//        private RequestQueue requestQueue;
        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.businessmanloginlayout, container, false);
            tele=(EditText)view.findViewById(R.id.businessman_tele);
            psd=(EditText)view.findViewById(R.id.businessman_password);
            TextView reg_user=view.findViewById(R.id.businessman_register);
            reg_user.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(getActivity(), Register_businessman_Activity.class);
                    startActivity(intent);
                }
            });
            Button user_login=(Button)view.findViewById(R.id.businessman_login);
            user_login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String telenumber=tele.getText().toString();
                    String password=psd.getText().toString();
                    LoginRequest(telenumber,password);
                }
            });
            return view;
        }

    @Override
    public void onResume() {
        super.onResume();
        Intent intent=getActivity().getIntent();
        Bundle bundle=intent.getExtras();
        if(bundle!=null){
            String str=bundle.getString("tele");
            tele.setText(str);
        }
    }

    public void LoginRequest(final String accountNumber, final String password) {
        BmobQuery<Businessman> query=new BmobQuery<>();
        query.addWhereEqualTo("tele",accountNumber);
        query.findObjects(new FindListener<Businessman>() {
            @Override
            public void done(List<Businessman> list, BmobException e) {
                if(e==null){
                    Businessman businessman=list.get(0);
                    if(businessman.getPsd().equals(password)){
                        MyApplication myApplication=(MyApplication) getActivity().getApplication();
                        CurrentUser currentUser=new CurrentUser();
                        currentUser.setObjectid(businessman.getObjectId());
                        currentUser.setTele(businessman.getTele());
                        currentUser.setPsd(businessman.getPsd());
                        currentUser.setUrl(businessman.getUrl());
                        currentUser.setName(businessman.getName());
                        currentUser.setSex(businessman.getSex());
                        currentUser.setMail(businessman.getMail());
                        currentUser.setBirthday(businessman.getBirthday());
                        currentUser.setLocation(businessman.getLocation());
                        currentUser.setOpendate(businessman.getOpendate());
                        currentUser.setStorename(businessman.getStorename());
                        myApplication.userLogin(currentUser);
                        Toast.makeText(getContext(),"登录成功",Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(getActivity(), BusinessmanFrameActivity.class);
                        startActivity(intent);
                        getActivity().finish();
                    }else {
                        Toast.makeText(getContext(),"登录失败",Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Log.d("msg",e.getMessage());
                    Toast.makeText(getContext(),"登录失败",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
