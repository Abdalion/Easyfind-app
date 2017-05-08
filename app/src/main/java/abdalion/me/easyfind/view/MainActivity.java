package abdalion.me.easyfind.view;

import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import abdalion.me.easyfind.Listener;
import abdalion.me.easyfind.R;
import abdalion.me.easyfind.controller.UserController;
import abdalion.me.easyfind.model.User;

public class MainActivity extends AppCompatActivity {

    private static boolean CONFIRM_LEAVE;
    private ArrayList<User> mUserList;
    private RecyclerView mDrawerRecycler;
    private DrawerLayout mDrawerLayout;
    private UserRecyclerAdapter mUserRecyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        CONFIRM_LEAVE = false;
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerRecycler = (RecyclerView) findViewById(R.id.left_drawer_recycler);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        loadFollowedUsers();

        List<User> usersList = mUserList;

        mDrawerRecycler = (RecyclerView)findViewById(R.id.left_drawer_recycler);

        mDrawerRecycler.setHasFixedSize(true);

        mDrawerRecycler.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));

        mUserRecyclerAdapter = new UserRecyclerAdapter(usersList);
        mUserRecyclerAdapter.setClickListener(new UsersListener());

        mDrawerRecycler.setAdapter(mUserRecyclerAdapter);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content_frame, new MapViewFragment())
                .commit();
        Toast.makeText(this, "Created map", Toast.LENGTH_SHORT).show();

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mDrawerLayout.setDrawerListener(toggle);
        toggle.syncState();
    }

    private void loadLocations() {
        new UserController().getObservedUsersID(new Listener<ArrayList<String>>() {
            @Override
            public void update(ArrayList<String> obj) {

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

    private class UsersListener implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            int posicion = mDrawerRecycler.getChildAdapterPosition(view);
            List<User>usersList = mUserRecyclerAdapter.getUserList();
            User userClickeado = usersList.get(posicion);
            //Start maps fragment with X user
            Toast.makeText(MainActivity.this, "Clickeado: " + userClickeado.getUserID(), Toast.LENGTH_SHORT).show();
        }
    }

}
