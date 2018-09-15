package com.example.makemoneyforsoftware.ReleaseJobPackage;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.makemoneyforsoftware.R;

import java.util.List;

public class JobTypeAdapter extends ArrayAdapter<JobType>{
    private int resourceId;
    public JobTypeAdapter(@NonNull Context context, int resource, @NonNull List<JobType> objects) {
        super(context, resource, objects);
        resourceId=resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view= LayoutInflater.from(getContext()).inflate(resourceId,parent,false);
        JobType jobType=getItem(position);
        ImageView imageView=view.findViewById(R.id.type_img);
        TextView textView=view.findViewById(R.id.type_text);
        imageView.setImageResource(jobType.getImgId());
        textView.setText(jobType.getText());
        return view;
    }
}
