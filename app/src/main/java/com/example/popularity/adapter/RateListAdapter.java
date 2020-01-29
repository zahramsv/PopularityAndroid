package com.example.popularity.adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatRatingBar;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.popularity.R;
import com.example.popularity.model.Rate;

import java.util.ArrayList;
import java.util.List;

public class RateListAdapter extends RecyclerView.Adapter<RateListAdapter.RateHolder> {


    private List<Rate> rates = new ArrayList<>();
    private Context context;
    private static ClickListener clickListener;

    public interface ClickListener {
        void onItemClick(int position, View v);
    }

    public void setOnItemClickListener(ClickListener clickListener)
    {
       RateListAdapter.clickListener=clickListener;

    }
    public RateListAdapter(List<Rate> rates, Context context) {
        this.rates = rates;
        this.context = context;
    }

    @NonNull
    @Override
    public RateHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_rate, viewGroup, false);
        return new RateHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RateHolder rateHolder, int i) {

        if (rateHolder instanceof RateHolder) {
            RateHolder view =  rateHolder;
            final Rate o = rates.get(i);
            view.attribute.setText(o.getAttribute());
            view.rating.setRating(rates.get(i).getRate());
        }
    }

    @Override
    public int getItemCount() {
        return rates.size();
    }

    public class RateHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        private TextView attribute;
        private AppCompatRatingBar rating;
        public RateHolder(@NonNull View itemView) {
            super(itemView);
            attribute=itemView.findViewById(R.id.txtAttribute);
            rating=itemView.findViewById(R.id.rating);
        }

        @Override
        public void onClick(View view) {
            clickListener.onItemClick(getAdapterPosition(),view);
        }
    }
}
