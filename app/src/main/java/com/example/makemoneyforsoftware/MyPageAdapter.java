package com.example.makemoneyforsoftware;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ImageView;

import java.util.List;

public class MyPageAdapter extends PagerAdapter {

    private List<ImageView> imgs;
    //返回显示数据的总条数，为了实现无限循环，把返回的值设置为最大整数

    private Context mContext;
    public MyPageAdapter(Context context,List<ImageView> list){
        imgs=list;
    }

    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    //指定复用的判断逻辑，固定写法：view == object

    @Override

    public boolean isViewFromObject(View view, Object object) {
        //当创建新的条目，又反回来，判断view是否可以被复用(即是否存在)
        return view == object;
    }


    //返回要显示的条目内容

    @Override

    public Object instantiateItem(ViewGroup container, int position) {
        //对Viewpager页号求模去除View列表中要显示的项
        position %= imgs.size();

        if (position<0) {
            position = imgs.size() + position;
        }
        ImageView view = imgs.get(position);
        //如果View已经在之前添加到了一个父组件，则必须先remove，否则会抛出IllegalStateException。
        ViewParent viewParent = view.getParent();
        if (viewParent!=null){
            ViewGroup parent = (ViewGroup)viewParent;
            parent.removeView(view);
        }
        container.addView(view);
        return view;
    }


    //销毁条目
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        //object:刚才创建的对象，即要销毁的对象
        container.removeView((View) object);
    }


//
//    //新的页面被选中
//
//    @Override
//
//    public void onPageSelected(int position) {
//
//        //当前的位置可能很大，为了防止下标越界，对要显示的图片的总数进行取余
//
//        int newPosition = position % 5;
//
//        //设置描述信息
//
//        tv_desc.setText(contentDescs[newPosition]);
//
//        //设置小圆点为高亮或暗色
//
//        ll_point.getChildAt(lastPosition).setEnabled(false);
//
//        ll_point.getChildAt(newPosition).setEnabled(true);
//
//        lastPosition = newPosition; //记录之前的点
//
//    }


}
