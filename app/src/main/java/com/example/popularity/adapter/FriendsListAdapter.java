package com.example.popularity.adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.popularity.R;
import com.example.popularity.model.Friend;
import com.example.popularity.myInterface.ItemClickListener;

import java.util.ArrayList;
import java.util.List;



public class FriendsListAdapter extends RecyclerView.Adapter<FriendsListAdapter.FriendsHolder> implements Filterable {


    private List<Friend> friends = new ArrayList<>();
    private Context context;
    private ItemClickListener listener;


    public void setOnItemClickListener(ItemClickListener clickListener)
    {
        listener=clickListener;
    }
    public FriendsListAdapter(List<Friend> friends, Context context) {
        this.friends = friends;
        this.context = context;
    }

    @NonNull
    @Override
    public FriendsHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_friend, viewGroup, false);

        return new FriendsHolder(view);

    }

    public void addItem(Friend item){
        friends.add(item);
        this.notifyDataSetChanged();
    }

    public void addAllItems(List<Friend> items){
        friends.addAll(items);
        this.notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull FriendsHolder friendsHolder, int i) {

        if (friendsHolder instanceof FriendsHolder) {
            FriendsHolder view = friendsHolder;
            final Friend obj = friends.get(i);
            view.username.setText(obj.getName());


            friendsHolder.itemView.setOnClickListener(view1 -> listener.onItemClick(i));
        }
    }

    @Override
    public int getItemCount() {
        return friends.size();
    }


    //fixMe  add for Search in recyclerView
    List<Friend> contactListFiltered;
    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    contactListFiltered = friends;
                } else {
                    List<Friend> filteredList = new ArrayList<>();
                    for (Friend row : friends) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getName().toLowerCase().contains(charString.toLowerCase()) || row.getUserId().contains(charSequence)) {
                            filteredList.add(row);
                        }
                    }

                    contactListFiltered = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = contactListFiltered;
                return filterResults;
            }




            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {

                contactListFiltered = (List<Friend>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public interface ContactAdapterListener {
        void onContactSelected(Friend contact);
    }

    public class FriendsHolder extends RecyclerView.ViewHolder {

        private ImageView rate;
        private TextView username;
        private ImageView profileimage;

        public FriendsHolder(@NonNull View itemView) {
            super(itemView);
            //profileImage = itemView.findViewById(R.id.profileimage);
           // rate = itemView.findViewById(R.id.rate);
            username = itemView.findViewById(R.id.txtName);
            profileimage=itemView.findViewById(R.id.imgUserProfile);
        }

    }
}
