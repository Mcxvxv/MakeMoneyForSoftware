package com.example.makemoneyforsoftware.FrameFragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.makemoneyforsoftware.LoginFragmentPackage.MyFragmentAdapter;
import com.example.makemoneyforsoftware.Message.AddressListFragment;
import com.example.makemoneyforsoftware.Message.UserMessageFragment;
import com.example.makemoneyforsoftware.R;

import java.util.ArrayList;
import java.util.List;

public class MessageFragment extends Fragment{
    private View view;
    private List<Fragment> list;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.message_layout,container,false);
        TabLayout tabLayout=(TabLayout)view.findViewById(R.id.tablayout);
        ViewPager viewPager=(ViewPager)view.findViewById(R.id.viewpager);
        tabLayout.addTab(tabLayout.newTab().setText("消息"));
        tabLayout.addTab(tabLayout.newTab().setText("通讯录"));
        list=new ArrayList<>();
        list.add(new UserMessageFragment());
        list.add(new AddressListFragment());
        String[] title=new String[]{"消息","通讯录"};
        MyFragmentAdapter adapter=new MyFragmentAdapter(getActivity().getSupportFragmentManager(),list,title);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.setCurrentItem(0);
        return view;
    }
}
