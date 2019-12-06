package com.example.popularity.adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.popularity.R;
import com.example.popularity.model.Friend;

import java.util.ArrayList;
import java.util.List;



public class FriendsListAdapter extends RecyclerView.Adapter<FriendsListAdapter.FriendsHolder> {


    private List<Friend> friends = new ArrayList<>();
    private Context context;

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

    @Override
    public void onBindViewHolder(@NonNull FriendsHolder friendsHolder, int i) {

        if (friendsHolder instanceof FriendsHolder) {
            FriendsHolder view = (FriendsHolder) friendsHolder;
            final Friend obj = friends.get(i);
            view.username.setText(obj.getName());
        }
    }

    @Override
    public int getItemCount() {
        return friends.size();
    }

    public class FriendsHolder extends RecyclerView.ViewHolder {

        //CircleImageView profile_image;
        private ImageView rate;
        private TextView username;
        private ImageView profileimage;

        public FriendsHolder(@NonNull View itemView) {
            super(itemView);
            //profileImage = itemView.findViewById(R.id.profileimage);
           // rate = itemView.findViewById(R.id.rate);
            username = itemView.findViewById(R.id.username);
        }
    }
}
