package com.example.makemoneyforsoftware;

import android.content.Context;
import android.graphics.PorterDuff;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.baoyachi.stepview.VerticalStepView;
import com.example.makemoneyforsoftware.CV.CvItem;
import com.loopeer.cardstack.CardStackView;
import com.loopeer.cardstack.StackAdapter;

import java.util.ArrayList;
import java.util.List;

public class MyStackAdapter extends StackAdapter<CvItem> {
    public Context mContext;

    public MyStackAdapter(Context context) {
        super(context);
        mContext = context;
    }


    @Override
    public void bindView(CvItem data, int position, CardStackView.ViewHolder holder) {

        if (holder instanceof MyStackAdapter.ItemViewHolder) {
            MyStackAdapter.ItemViewHolder h = (MyStackAdapter.ItemViewHolder) holder;
            h.onBind(data, position);
        }

    }


    @Override
    protected CardStackView.ViewHolder onCreateView(ViewGroup parent, int viewType) {
        View view;
        view = getLayoutInflater().inflate(R.layout.list_card_item, parent, false);
        return new MyStackAdapter.ItemViewHolder(view);
    }

    static class ItemViewHolder extends CardStackView.ViewHolder {
        View mLayout;
        View mContainerContent;
        TextView mTextTitle;
        VerticalStepView mStepView;

        public ItemViewHolder(View view) {
            super(view);
            mLayout = view.findViewById(R.id.frame_list_card_item);
            mContainerContent = view.findViewById(R.id.container_list_content);
            mTextTitle = (TextView) view.findViewById(R.id.text_list_card_title);
            mStepView = (VerticalStepView) view.findViewById(R.id.stepview);
        }


        @Override
        public void onItemExpand(boolean b) {
            mContainerContent.setVisibility(b ? View.VISIBLE : View.GONE);
        }


        public void onBind(CvItem data, int position) {
            mLayout.getBackground().setColorFilter(ContextCompat.getColor(getContext(), data.getColor()), PorterDuff.Mode.SRC_IN);
            mTextTitle.setText(data.getTitle());
            List<String> list0 = new ArrayList<>();
            Log.d("msg", "type" + data.getType());
            switch (data.getType()) {
                case 1:
                    list0.clear();
                    if (data.getStartday().equals("")) {
                        list0.add("入学日期:" + "未填写" + " - " + "毕业日期:" + "未填写");
                    } else {
                        list0.add("入学日期:" + data.getStartday() + " - " + "毕业日期:" + data.getEndday());
                    }
                    if (data.getSchool().equals("")) {
                        list0.add("学校:" + "未填写" + " | " + "学历:" + "未填写");
                    } else {
                        list0.add("学校:" + data.getSchool() + " | " + "学历:" + data.getXueli());
                    }
                    if (data.getMajor().equals("")) {
                        list0.add("主修专业:" + "未填写");
                    } else {
                        list0.add("主修专业:" + data.getMajor());
                    }
                    if (data.getMsg() == null || data.getMsg().equals("")) {
                        list0.add("个人经历:" + "未填写");
                    } else {
                        list0.add("个人经历:" + data.getMsg());
                    }
                    break;
                case 2:
                    list0.clear();
                    if (data.getStartday().equals("")) {
                        list0.add("开始日期:"+"未填写" + " - " + "结束日期:"+"未填写");
                    } else {
                        list0.add("开始日期:"+data.getStartday() + " - " + "结束日期:"+data.getEndday());
                    }
                    if(data.getCompany().equals("")){
                        list0.add("公司:" + "未填写");
                    }else {
                        list0.add("公司:" + data.getCompany());
                    }
                    if(data.getMsg().equals("")){
                        list0.add("工作经历:" + "未填写");
                    }else {
                        list0.add("工作经历:" + data.getMsg());
                    }
                    break;
                case 3:
                    list0.clear();
                    if(data.getMsg().equals("")){
                        list0.add("自我评价:" + "未填写");
                    }else {
                        list0.add("自我评价:" + data.getMsg());
                    }
                    break;
            }
            mStepView.setStepsViewIndicatorComplectingPosition(list0.size())//设置完成的步数
                    .reverseDraw(false)//default is true
                    .setStepViewTexts(list0)//总步骤
                    .setLinePaddingProportion(0.85f)//设置indicator线与线间距的比例系数
                    .setStepsViewIndicatorCompletedLineColor(ContextCompat.getColor(getContext(), android.R.color.white))//设置StepsViewIndicator完成线的颜色
                    .setStepsViewIndicatorUnCompletedLineColor(ContextCompat.getColor(getContext(), R.color.uncompleted_text_color))//设置StepsViewIndicator未完成线的颜色
                    .setStepViewComplectedTextColor(ContextCompat.getColor(getContext(), android.R.color.white))//设置StepsView text完成线的颜色
                    .setStepViewUnComplectedTextColor(ContextCompat.getColor(getContext(), R.color.uncompleted_text_color))//设置StepsView text未完成线的颜色
                    .setStepsViewIndicatorCompleteIcon(ContextCompat.getDrawable(getContext(), R.drawable.complted))//设置StepsViewIndicator CompleteIcon
                    .setStepsViewIndicatorDefaultIcon(ContextCompat.getDrawable(getContext(), R.drawable.default_icon))//设置StepsViewIndicator DefaultIcon
                    .setStepsViewIndicatorAttentionIcon(ContextCompat.getDrawable(getContext(), R.drawable.attention));//设置StepsViewIndicator AttentionIco


        }
    }

}
