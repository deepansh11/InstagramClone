package pahwa.deepansh.instagramclone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.parse.ParseUser;

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        TextView textView = findViewById(R.id.textView);
        Button logOut = findViewById(R.id.logOut);
        textView.setText("Welcome "+ ParseUser.getCurrentUser().get("username") );

        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ParseUser.getCurrentUser().logOut();
                finish();

            }
        });
    }
}
