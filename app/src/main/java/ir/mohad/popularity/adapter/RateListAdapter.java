package ir.mohad.popularity.adapter;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import ir.mohad.popularity.R;
import ir.mohad.popularity.model.Rate;

import java.util.ArrayList;
import java.util.List;

public class RateListAdapter extends RecyclerView.Adapter<RateListAdapter.RateHolder> {


    private List<Rate> rates = new ArrayList<>();
    private Context context;
    private static ClickListener clickListener;

    public interface ClickListener {
        void onItemClick(int position, View v);
    }

    public void setOnItemClickListener(ClickListener clickListener) {
        RateListAdapter.clickListener = clickListener;

    }

    public RateListAdapter(List<Rate> rates, Context context) {
        this.rates = rates;
        this.context = context;
    }

    @NonNull
    @Override
    public RateHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.receive_rate_item, viewGroup, false);
        return new RateHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RateHolder rateHolder, int i) {

        if (rateHolder instanceof RateHolder) {
            RateHolder view = rateHolder;
            view.TxtRateItem.setText(rates.get(i).getAttribute());
            view.TxtRateCount.setText(rates.get(i).getRate() + "");


        }
    }

    @Override
    public int getItemCount() {
        return rates.size();
    }

    public class RateHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        private TextView TxtRateItem, TxtRateCount;

        public RateHolder(@NonNull View itemView) {
            super(itemView);

            TxtRateCount = itemView.findViewById(R.id.TxtRateCount);
            TxtRateItem = itemView.findViewById(R.id.TxtRateItem);
        }

        @Override
        public void onClick(View view) {
            clickListener.onItemClick(getAdapterPosition(), view);
        }
    }
}
