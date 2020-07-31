package com.kevin.f1miniapp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

public class SeasonListAdapter extends ArrayAdapter<Season> {
     private Context context;
     private int resource;

    private static class ViewHolder {
        TextView yearTextView;
        Button wikiButton;
    }

    public SeasonListAdapter(Context context, int resource, List<Season> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        int year = getItem(position).getDate();
        String wikiUrl = getItem(position).getWikiUrl();

        Season season = new Season(year, wikiUrl);

        ViewHolder viewHolder;

        if(convertView == null) {
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            convertView = layoutInflater.inflate(resource, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.yearTextView = (TextView) convertView.findViewById(R.id.yearTextView);
            viewHolder.wikiButton = (Button) convertView.findViewById(R.id.seasonWikiButton);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.yearTextView.setText(String.valueOf(season.getDate()));
        viewHolder.wikiButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(wikiUrl));
                context.startActivity(browserIntent);
            }
        });

        return convertView;

    }
}
