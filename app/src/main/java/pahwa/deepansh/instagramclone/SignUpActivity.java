package pahwa.deepansh.instagramclone;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.SignUpCallback;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.List;

//Sign up class


public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText enterEmail,enterUserName,enterPassword ;
    private Button signUp,logIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Sign Up");

        enterEmail = findViewById(R.id.enterEmail);
        enterUserName = findViewById(R.id.enterName);
        enterPassword = findViewById(R.id.enterPassword);
        signUp = findViewById(R.id.SignUp);
        logIn = findViewById(R.id.Login);


        signUp.setOnClickListener(this);
        logIn.setOnClickListener(this);

        if (ParseUser.getCurrentUser() != null){

            ParseUser.getCurrentUser().logOut();

        }


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.SignUp:
                final ParseUser appUser = new ParseUser();
                appUser.setEmail(enterEmail.getText().toString());
                appUser.setUsername(enterUserName.getText().toString());
                appUser.setPassword(enterPassword.getText().toString());


                final ProgressDialog progressDialog = new ProgressDialog(this);

                progressDialog.setMessage("Signing Up " + enterUserName.getText().toString());
                progressDialog.show();

                appUser.signUpInBackground(new SignUpCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e == null){
                            FancyToast.makeText(SignUpActivity.this,
                                    appUser.getUsername() +" Signed Up successful",
                                    FancyToast.LENGTH_SHORT,FancyToast.SUCCESS,true).show();
                        } else {
                            FancyToast.makeText(SignUpActivity.this, "Error in signing up",
                                    FancyToast.LENGTH_SHORT,FancyToast.ERROR,true).show();
                        }

                        progressDialog.dismiss();

                    }
                });

                break;

            case R.id.Login:

                Intent intent = new Intent(SignUpActivity.this,LoginActivity.class);

                startActivity(intent);
                break;
        }


    }
}
