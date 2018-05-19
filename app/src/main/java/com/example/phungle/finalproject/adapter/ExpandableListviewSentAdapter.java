package com.example.phungle.finalproject.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.example.phungle.finalproject.R;
import com.example.phungle.finalproject.model.QuesAndAnswer;
import com.example.phungle.finalproject.model.Topic;
import com.example.phungle.finalproject.model.TopicSent;

import java.util.List;

public class ExpandableListviewSentAdapter extends BaseExpandableListAdapter {
    Context context;
    List<TopicSent> topics;

    public ExpandableListviewSentAdapter(Context context, List<TopicSent> topics) {
        this.context = context;
        this.topics = topics;
    }

    @Override
    public int getGroupCount() {
        return topics.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return topics.get(i).listWord.size();
    }

    @Override
    public Object getGroup(int i) {
        return topics.get(i).name;
    }

    @Override
    public Object getChild(int i, int i1) {
        return topics.get(i).listWord.get(i1);
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int i, int i1) {
        return i1;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
        String headeTitle = String.valueOf(getGroup(i));
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.header_item_listview, null);
        TextView header = view.findViewById(R.id.headerItemListview);
        header.setText(headeTitle);
        return view;
    }

    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
        String Content = getChild(i, i1).toString();

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.child_item_listview_sent, null);
        TextView txtContent = view.findViewById(R.id.child_Content);
        txtContent.setText(Content);
        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return false;
    }
}
