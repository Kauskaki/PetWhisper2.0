package com.petpawology.petwhisper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

public class NotifPreferenceAdapter extends BaseExpandableListAdapter {

    private Context context;
    private List<String> groupList;
    private Map<String, List<NotificationItem>> childMap;

    public static class NotificationItem {
        public String name;
        public boolean isEnabled;

        public NotificationItem(String name, boolean isEnabled) {
            this.name = name;
            this.isEnabled = isEnabled;
        }
    }

    public NotifPreferenceAdapter(Context context, List<String> groupList, Map<String, List<NotificationItem>> childMap) {
        this.context = context;
        this.groupList = groupList;
        this.childMap = childMap;
    }

    @Override
    public int getGroupCount() {
        return groupList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return childMap.get(groupList.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return groupList.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return childMap.get(groupList.get(groupPosition)).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) { return groupPosition; }

    @Override
    public long getChildId(int groupPosition, int childPosition) { return childPosition; }

    @Override
    public boolean hasStableIds() { return false; }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.enter_pet_info_notifs_group_items, parent, false);
        }

        TextView title = convertView.findViewById(R.id.groupTitle);
        title.setText(groupList.get(groupPosition));

        ImageView arrowIcon = convertView.findViewById(R.id.arrowIcon);
        arrowIcon.setRotation(isExpanded ? 180f : 0f);
        arrowIcon.animate().scaleX(1.2f).setDuration(150).withEndAction(() ->
                arrowIcon.animate().scaleX(1f).setDuration(150)
        ).start();

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        NotificationItem item = (NotificationItem) getChild(groupPosition, childPosition);

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.enter_pet_info_notifs_collapsible_items, parent, false);
        }

        TextView title = convertView.findViewById(R.id.Notif_name);
        com.google.android.material.switchmaterial.SwitchMaterial toggle = convertView.findViewById(R.id.Switch_notif);


        title.setText(item.name);
        toggle.setChecked(item.isEnabled);

        toggle.setOnCheckedChangeListener((buttonView, isChecked) -> {
            item.isEnabled = isChecked;
            // You can persist this change here (e.g., save to SharedPreferences)
        });

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) { return true; }
}
