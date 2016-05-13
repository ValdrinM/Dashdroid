package com.hyperion.dashdroid.radio;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hyperion.dashdroid.R;

import java.util.ArrayList;

/**
 * Created by Rainer on 12.05.2016.
 */
public class ChannelAdapter extends RecyclerView.Adapter<ChannelAdapter.ViewHolder>{

    private ArrayList<RadioChannel> radioChannels;

    public static class ViewHolder extends RecyclerView.ViewHolder{

        public TextView cardText;

        public ViewHolder(View itemView) {
            super(itemView);
            cardText = (TextView) itemView.findViewById(R.id.radioCardText);
        }
    }

    public ChannelAdapter(ArrayList<RadioChannel> radioChannels) {
        this.radioChannels = radioChannels;
    }

    @Override
    public ChannelAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View card = LayoutInflater.from(parent.getContext()).inflate(R.layout.radio_fragment_card, parent, false);
        return new ViewHolder(card);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.cardText.setText(radioChannels.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return radioChannels.size();
    }


}
