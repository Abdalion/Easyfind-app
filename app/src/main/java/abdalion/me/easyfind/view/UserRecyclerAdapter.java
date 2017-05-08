package abdalion.me.easyfind.view;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import abdalion.me.easyfind.R;
import abdalion.me.easyfind.model.User;

/**
 * Created by Egon on 27/4/2017.
 */

public class UserRecyclerAdapter extends RecyclerView.Adapter implements View.OnClickListener {

    private List<User> mUserList;
    private View.OnClickListener clickListener;

    public UserRecyclerAdapter(List<User> userList) {
        this.mUserList = userList;
    }

    public List<User> getUserList() {
        return mUserList;
    }

    public void setClickListener(View.OnClickListener clickListener) {
        this.clickListener = clickListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int position) {
        LayoutInflater inflater = (LayoutInflater)LayoutInflater.from(parent.getContext());
        View viewCelda = inflater.inflate(R.layout.nav_user_item,parent,false);
        UsersViewHolder usersViewHolder = new UsersViewHolder(viewCelda);
        viewCelda.setOnClickListener(this);
        return usersViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        User user = mUserList.get(position);
        UsersViewHolder recetasViewHolder = (UsersViewHolder) holder;
        recetasViewHolder.loadUser(user);
    }

    @Override
    public int getItemCount() {
        return mUserList.size();
    }

    @Override
    public void onClick(View view) {
        clickListener.onClick(view);
    }

    private class UsersViewHolder extends RecyclerView.ViewHolder {
        private TextView usernameTextView;
        private ImageView usericonImageView;

        public UsersViewHolder(View view) {
            super(view);
            usernameTextView = (TextView) view.findViewById(R.id.nav_item_user_name);
            usericonImageView = (ImageView) view.findViewById(R.id.nav_item_user_icon);
        }

        public void loadUser(User user) {
            usernameTextView.setText(user.getUserID());
            if (user.getImage() != null) {
                usericonImageView.setImageBitmap(user.getImage());
            }
        }
    }
}