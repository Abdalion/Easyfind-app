package abdalion.me.easyfind.view.main;

import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;

import java.util.List;

import abdalion.me.easyfind.Listener;
import abdalion.me.easyfind.controller.UserController;
import abdalion.me.easyfind.model.User;

/**
 * Created by Egon on 18/5/2017.
 */

public class ItemClickListener implements View.OnClickListener{

    private RecyclerView mDrawerRecycler;
    private UserRecyclerAdapter mUserRecyclerAdapter;
    private MapViewFragment mMapViewFragment;
    private DrawerLayout mDrawerLayout;

    public ItemClickListener(RecyclerView mDrawerRecycler, UserRecyclerAdapter mUserRecyclerAdapter, MapViewFragment mMapViewFragment, DrawerLayout mDrawerLayout) {
        this.mDrawerRecycler = mDrawerRecycler;
        this.mUserRecyclerAdapter = mUserRecyclerAdapter;
        this.mMapViewFragment = mMapViewFragment;
        this.mDrawerLayout = mDrawerLayout;
    }

    @Override
        public void onClick(View view) {
            int posicion = mDrawerRecycler.getChildAdapterPosition(view);
            List<String> usersList = mUserRecyclerAdapter.getUserList();
            final String mailClickeado = usersList.get(posicion);

            new UserController().loadUser(mailClickeado, new Listener<User>() {
                @Override
                public void update(User obj) {
                    mMapViewFragment.updateObservedUser(obj);
                    mDrawerLayout.closeDrawer(Gravity.LEFT);
                }
            });
        }
}
