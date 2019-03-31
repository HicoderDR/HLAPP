package com.example.jsrgjhl.hlapp.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.jsrgjhl.hlapp.R;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by xuxiaojin on 2019/3/24.
 */

public class SolutionsAdapter extends ArrayAdapter<Solution> {
    private int resourceId;
    public SolutionsAdapter(@NonNull Context context, int textViewResourceId, @NonNull List<Solution> objects) {
        super(context,textViewResourceId,objects);
        resourceId=textViewResourceId;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Solution solution=getItem(position);
        View view;
        SolutionsAdapter.ViewHolder viewHolder;
        if (convertView==null){
            view= LayoutInflater.from(getContext()).inflate(resourceId,parent,false);
            viewHolder=new SolutionsAdapter.ViewHolder();
            viewHolder.recordnumTextview=(TextView)view.findViewById(R.id.record_num);
            viewHolder.recordtitleTextview=(TextView)view.findViewById(R.id.solutiontitleTv);
            viewHolder.recordcontextTextview=(TextView)view.findViewById(R.id.solutioncontexttv);
            viewHolder.recordtimeTextview=(TextView)view.findViewById(R.id.deltimeTv);
            view.setTag(viewHolder);
        }else{
            view=convertView;
            viewHolder=(SolutionsAdapter.ViewHolder)view.getTag();
        }
        viewHolder.recordnumTextview.setText(solution.getDevicenum());
        viewHolder.recordtitleTextview.setText(solution.getTitle());
        viewHolder.recordcontextTextview.setText(solution.getContext());
        try {
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
            Date dt=df.parse(String.valueOf(solution.getDeltime()));
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String date=format.format(dt);
            viewHolder.recordtimeTextview.setText(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return view;
    }
    class ViewHolder{
        TextView recordnumTextview;
        TextView recordtitleTextview;
        TextView recordcontextTextview;
        TextView recordtimeTextview;

    }
}
