package pahwa.deepansh.instagramclone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.shashank.sony.fancytoastlib.FancyToast;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText enterEmail,enterPassword ;
    private Button signUp,logIn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        enterEmail = findViewById(R.id.enterEmailLogin);
        enterPassword = findViewById(R.id.enterPasswordLogin);
        signUp = findViewById(R.id.SignUpLogin);
        logIn = findViewById(R.id.LoginLogin);


        signUp.setOnClickListener(this);
        logIn.setOnClickListener(this);

        if (ParseUser.getCurrentUser() != null){

            ParseUser.getCurrentUser().logOut();

        }


    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.SignUpLogin:

                Intent intent = new Intent(LoginActivity.this,SignUpActivity.class);
                startActivity(intent);


                break;
            case R.id.LoginLogin:
                ParseUser.logInInBackground(enterEmail.getText().toString(),
                        enterPassword.getText().toString(),
                        new LogInCallback() {
                            @Override
                            public void done(ParseUser user, ParseException e) {
                                if (user != null && e == null){
                                    FancyToast.makeText(LoginActivity.this,
                                            user.getUsername() +" Logged in",
                                            FancyToast.LENGTH_SHORT,FancyToast.SUCCESS,true).show();
                                } else {
                                    FancyToast.makeText(LoginActivity.this,
                                            e.getMessage(),
                                            FancyToast.LENGTH_SHORT,FancyToast.ERROR,true).show();
                                }
                            }
                        });
                break;
        }


    }
}
