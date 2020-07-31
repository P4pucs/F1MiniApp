package com.kevin.f1miniapp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

public class DriversExpendableListAdapter extends BaseExpandableListAdapter {

    private List<Driver> drivers;
    private Context context;

    public DriversExpendableListAdapter(Context context, List<Driver> drivers) {
        this.drivers = drivers;
        this.context = context;
    }

    @Override
    public int getGroupCount() {
        return this.drivers.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return 1;
    }

    @Override
    public Driver getGroup(int i) {
        return this.drivers.get(i);//.getGivenName() + " " + this.drivers.get(i).getFamilyName();
    }

    @Override
    public Driver getChild(int i, int i1) {
        return this.drivers.get(i);
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
    public View getGroupView(int i, boolean isCollapsed, View view, ViewGroup viewGroup) {

        String name = getGroup(i).getGivenName() + " " + getGroup(i).getFamilyName();

        if(view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.drivers_list_group, null);
        }
            TextView textView1 = (TextView) view.findViewById(R.id.nameTextView);
            textView1.setText(name);

            Button expandButton = (Button) view.findViewById(R.id.ExpandButton);
            expandButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (isCollapsed) {
                        ((ExpandableListView) viewGroup).collapseGroup(i);
                        expandButton.setText("expand");
                    } else {
                        ((ExpandableListView) viewGroup).expandGroup(i, false);
                        expandButton.setText("collapse");
                    }
                }
            });

        return view;
    }

    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {

        String nationality = getChild(i, i1).getNationality();
        String dateOfBirth = getChild(i, i1).getDateOfBirth();
        String permanentNumber = ((getChild(i, i1).getPermanentNumber() == -1) ? "no racenumber" : "raceNumber: " + getChild(i, i1).getPermanentNumber());
        TextView nationalityTextView = null;
        TextView dateOfBirthTextView = null;
        TextView permanentNumberTextView = null;
        Button wikiButton = null;

        if(view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.drivers_list_item, null);

        }
        nationalityTextView = view.findViewById(R.id.nationalityTextView);
        nationalityTextView.setText(nationality);
        dateOfBirthTextView = view.findViewById(R.id.dateOfBirthTextView);
        dateOfBirthTextView.setText(dateOfBirth);
        permanentNumberTextView = view.findViewById(R.id.permanentNumberTextView);
        permanentNumberTextView.setText(permanentNumber);

        wikiButton = view.findViewById(R.id.driverWikiButton);

        wikiButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(getChild(i, i1).getWikiUrl()));
                context.startActivity(browserIntent);
            }
        });

        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }


}
