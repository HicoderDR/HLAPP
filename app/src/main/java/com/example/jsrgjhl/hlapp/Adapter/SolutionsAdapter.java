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

import java.util.List;

/**
 * Created by xuxiaojin on 2019/3/24.
 */

public class SolutionsAdapter extends ArrayAdapter<Solutions> {
    private int resourceId;
    public SolutionsAdapter(@NonNull Context context, int textViewResourceId, @NonNull List<Solutions> objects) {
        super(context,textViewResourceId,objects);
        resourceId=textViewResourceId;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Solutions solutions=getItem(position);
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
        viewHolder.recordnumTextview.setText(solutions.getRecordnum());
        viewHolder.recordtitleTextview.setText(solutions.getSolutionTitle());
        viewHolder.recordcontextTextview.setText(solutions.getSolutionContext());
        viewHolder.recordtimeTextview.setText(solutions.getDelTime());
        return view;
    }
    class ViewHolder{
        TextView recordnumTextview;
        TextView recordtitleTextview;
        TextView recordcontextTextview;
        TextView recordtimeTextview;

    }
}
