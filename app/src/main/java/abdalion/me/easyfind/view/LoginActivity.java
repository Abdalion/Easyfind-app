package abdalion.me.easyfind.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import abdalion.me.easyfind.R;
import abdalion.me.easyfind.view.main.MainActivity;
import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.login_button_google) Button googleButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        if(isUserLogged()) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }
        else {
            googleButton.setVisibility(View.VISIBLE);
        }

    }

    private boolean isUserLogged() {
        //todo: Return is logged?
        return true;
    }
}
