package abdalion.me.easyfind.view.main;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import abdalion.me.easyfind.R;
import abdalion.me.easyfind.model.User;

/**
 * Created by Egon on 27/4/2017.
 */

public class UserRecyclerAdapter extends RecyclerView.Adapter implements View.OnClickListener {

    private List<String> mMailList;
    private View.OnClickListener clickListener;
    //private Context mContext;


    public List<String> getUserList() {
        return mMailList;
    }

    public void setClickListener(View.OnClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public UserRecyclerAdapter(Context context, List<String> mailList) {
        //mContext = context;
        this.mMailList = mailList;
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
        String mail = mMailList.get(position);
        UsersViewHolder recetasViewHolder = (UsersViewHolder) holder;
        recetasViewHolder.loadUser(mail/*, mContext*/);
    }

    @Override
    public int getItemCount() {
        return mMailList.size();
    }

    @Override
    public void onClick(View view) {
        clickListener.onClick(view);
    }

    private class UsersViewHolder extends RecyclerView.ViewHolder {
        private TextView usernameTextView;
        //private ImageView usericonImageView;

        public UsersViewHolder(View view) {
            super(view);
            usernameTextView = (TextView) view.findViewById(R.id.nav_item_user_name);
          //  usericonImageView = (ImageView) view.findViewById(R.id.nav_item_user_icon);
        }

        public void loadUser(String mail/*, Context context*/) {
            usernameTextView.setText(mail);
//            if (user.getImageUrl() != "") {
//                Glide.with(context).load("http://goo.gl/gEgYUd").into(usericonImageView);
//            }
        }
    }
}