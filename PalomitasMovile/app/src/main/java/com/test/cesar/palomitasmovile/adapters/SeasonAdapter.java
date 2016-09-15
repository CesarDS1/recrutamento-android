package com.test.cesar.palomitasmovile.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.test.cesar.palomitasmovile.R;
import com.test.cesar.palomitasmovile.models.Episode;

import java.util.ArrayList;

public class SeasonAdapter extends RecyclerView.Adapter<SeasonAdapter.ViewHolder> {

    private ArrayList<Episode> episodesList;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView nameEpisode;
        public TextView numberEpisode;

        public ViewHolder(View v) {
            super(v);
            nameEpisode = (TextView) itemView.findViewById(R.id.name_episode);
            numberEpisode=(TextView) itemView.findViewById(R.id.number_episode);
        }
    }


    public SeasonAdapter(ArrayList<Episode> myDataset) {
        episodesList = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public SeasonAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_episode_layout, parent, false);

        ViewHolder vh = new ViewHolder(v);

        return vh;
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.nameEpisode.setText(episodesList.get(position).getName());

        StringBuilder strb=new StringBuilder();
        strb.append("E");
        strb.append(String.valueOf(episodesList.get(position).getNumber()));

        holder.numberEpisode.setText(strb.toString());

    }


    @Override
    public int getItemCount() {
        return episodesList.size();
    }
}