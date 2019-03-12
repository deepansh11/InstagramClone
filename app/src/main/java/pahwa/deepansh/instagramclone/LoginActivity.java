package pahwa.deepansh.instagramclone;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SignUpCallback;
import com.shashank.sony.fancytoastlib.FancyToast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    private EditText enterName,enterPassword,enterNameLogin,enterPasswordLogin;
    private Button btnSignUp,btnLogin;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        enterName = findViewById(R.id.enterName);
        enterPassword = findViewById(R.id.enterPassword);
        enterNameLogin = findViewById(R.id.enterNameLogin);
        enterPasswordLogin = findViewById(R.id.enterPasswordLogin);
        btnSignUp = findViewById(R.id.btnSignUp);
        btnLogin = findViewById(R.id.btnLogin);


        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ParseUser parseUser = new ParseUser();
                parseUser.setUsername(enterName.getText().toString());
                parseUser.setPassword(enterPassword.getText().toString());

                parseUser.signUpInBackground(new SignUpCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e == null) {
                            FancyToast.makeText(LoginActivity.this, parseUser.get("username") + " SignUp Successful", FancyToast.LENGTH_SHORT, FancyToast.SUCCESS, true).show();
                            Intent intent = new Intent(LoginActivity.this,WelcomeActivity.class);
                            startActivity(intent);

                        } else {
                            FancyToast.makeText(LoginActivity.this, "Error", FancyToast.LENGTH_SHORT, FancyToast.ERROR, true).show();
                        }
                    }
                });
            }
        });


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ParseUser.logInInBackground(enterNameLogin.getText().toString(),
                        enterPasswordLogin.getText().toString(), new LogInCallback() {
                            @Override
                            public void done(ParseUser user, ParseException e) {
                                if (user!= null && e == null){
                                    FancyToast.makeText(LoginActivity.this, user.get("username") + " Logged In Successful", FancyToast.LENGTH_SHORT, FancyToast.SUCCESS, true).show();
                                    Intent intent = new Intent(LoginActivity.this,WelcomeActivity.class);
                                    startActivity(intent);
                                } else  {
                                    FancyToast.makeText(LoginActivity.this, e.getMessage(), FancyToast.LENGTH_SHORT, FancyToast.ERROR, true).show();
                                }
                            }
                        });


            }
        });


    }
}
