package abdalion.me.easyfind.view;

import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.maps.MapView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import abdalion.me.easyfind.Listener;
import abdalion.me.easyfind.R;
import abdalion.me.easyfind.controller.UserController;
import abdalion.me.easyfind.model.User;

public class MainActivity extends AppCompatActivity {

    private static boolean CONFIRM_LEAVE;
    private RecyclerView mDrawerRecycler;
    private DrawerLayout mDrawerLayout;
    private UserRecyclerAdapter mUserRecyclerAdapter;
    private List<String> mMailList;
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

        loadMailList(new Listener<List<String>>() {
            @Override
            public void update(List<String> mailList) {
                mMailList = mailList;
                mUserRecyclerAdapter = new UserRecyclerAdapter(MainActivity.this, mailList);
                mUserRecyclerAdapter.setClickListener(new UsersListener());
                mDrawerRecycler.setAdapter(mUserRecyclerAdapter);

            }
        });

            loadUser(new Listener<User>() {
                @Override
                public void update(final User user) {
                    if(mMailList == null) {
                        Toast.makeText(MainActivity.this, "Maillist is null", Toast.LENGTH_SHORT).show();
                    }

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
            }, mMailList.get(0));



        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mDrawerLayout.setDrawerListener(toggle);
        toggle.syncState();
    }

    private void loadMailList(final Listener<List<String>> mailList) {
        final UserController userController = new UserController();
        userController.getObservedUsersMail(new Listener<ArrayList<String>>() {
            @Override
            public void update(ArrayList<String> obj) {
                mailList.update(obj);
            }
        });
    }

    private void loadUser(final Listener<User> userListener, String mail) {
        final UserController userController = new UserController();
        userController.observeUser(new Listener<User>() {
            @Override
            public void update(User obj) {
                userListener.update(obj);
                Toast.makeText(MainActivity.this, "Loaded user" + obj.getMail(), Toast.LENGTH_SHORT).show();
            }
        }, mail);
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

    private class UsersListener implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            int posicion = mDrawerRecycler.getChildAdapterPosition(view);
            List<String>usersList = mUserRecyclerAdapter.getUserList();
            final String mailClickeado = usersList.get(posicion);

            loadUser(new Listener<User>() {
                @Override
                public void update(User obj) {
                    mMapViewFragment.updateObservedUser(obj);
                    Toast.makeText(MainActivity.this, "Cargado: "+ obj.getMail() + " Loc: " + obj.getLocation().toString() , Toast.LENGTH_SHORT).show();
                    mDrawerLayout.closeDrawer(Gravity.LEFT);

                }
            }, mailClickeado);





        }
    }

}
