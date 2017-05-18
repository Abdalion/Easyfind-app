package abdalion.me.easyfind.view.main;

import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import abdalion.me.easyfind.Listener;
import abdalion.me.easyfind.R;
import abdalion.me.easyfind.controller.UserController;
import abdalion.me.easyfind.model.User;

import static abdalion.me.easyfind.utils.Utils.isNull;

public class MainActivity extends AppCompatActivity {

    private static boolean CONFIRM_LEAVE;
    private RecyclerView mDrawerRecycler;
    private DrawerLayout mDrawerLayout;
    private UserRecyclerAdapter mUserRecyclerAdapter;
//    private List<String> mMailList;
    private MapViewFragment mMapViewFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        CONFIRM_LEAVE = false;
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerRecycler = (RecyclerView) findViewById(R.id.left_drawer_recycler);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mDrawerRecycler = (RecyclerView)findViewById(R.id.left_drawer_recycler);
        mDrawerRecycler.setHasFixedSize(true);
        mDrawerRecycler.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));

        final UserController userController = new UserController();

        userController.getObservedUsersMail(new Listener<List<String>>() {
            @Override
            public void update(final List<String> mailList) {
//                mMailList = mailList;
                mUserRecyclerAdapter = new UserRecyclerAdapter(MainActivity.this, mailList);
                mUserRecyclerAdapter.setClickListener(new ItemClickListener(mDrawerRecycler, mUserRecyclerAdapter, mMapViewFragment, mDrawerLayout));
                mDrawerRecycler.setAdapter(mUserRecyclerAdapter);

                if(!isNull(mailList.get(0))) {
                   userController.loadUser(mailList.get(0), new Listener<User>() {
                        @Override
                        public void update(final User user) {
                            loadMapWithUser(user);
                        }
                    });
                }
                else {
                    //You have no followed users, start following!
                }
            }
        });

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mDrawerLayout.setDrawerListener(toggle);
        toggle.syncState();
    }

    private void loadMapWithUser(final User user) {
        mMapViewFragment = new MapViewFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content_frame, mMapViewFragment)
                .commit();

        mMapViewFragment.setMapFinishedListener(new Listener<Boolean>() {
            @Override
            public void update(Boolean obj) {
                mMapViewFragment.updateObservedUser(user);
            }
        });
    }

    @Override
    public void onBackPressed () {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if (getSupportFragmentManager().getBackStackEntryCount() == 1) {
            if (CONFIRM_LEAVE) {
                finish();
            } else {
                Toast.makeText(MainActivity.this, R.string.confirm_leave, Toast.LENGTH_SHORT).show();
                CONFIRM_LEAVE = true;
            }
        } else {
            super.onBackPressed();
        }
    }

}
