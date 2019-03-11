package pahwa.deepansh.instagramclone;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.SaveCallback;
import com.shashank.sony.fancytoastlib.FancyToast;

//Sign up class

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button submitObject;
    private EditText editName,editPunchSpeed,editKickSpeed,editKickPower;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        submitObject = findViewById(R.id.submitObject);
        submitObject.setOnClickListener(MainActivity.this);

        editName = findViewById(R.id.editName);
        editPunchSpeed = findViewById(R.id.editPunchSpeed);
        editKickSpeed = findViewById(R.id.editKickSpeed);
        editKickPower = findViewById(R.id.editKickPower);

        ParseInstallation.getCurrentInstallation().saveInBackground();

    }

    @Override
    public void onClick(View v) {

        try {
            final ParseObject kickBoxer = new ParseObject("KickBoxer");
            kickBoxer.put("name", editName.getText().toString());
            kickBoxer.put("Punch_Speed", Integer.parseInt(editPunchSpeed.getText().toString()));
            kickBoxer.put("Kick_Speed", Integer.parseInt(editKickPower.getText().toString()));
            kickBoxer.put("Kick_Power", Integer.parseInt(editKickPower.getText().toString()));
            kickBoxer.saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException e) {
                    if (e == null) {
                        FancyToast.makeText(MainActivity.this, kickBoxer.get("name") + " object is saved successfully", FancyToast.LENGTH_SHORT, FancyToast.SUCCESS, true).show();
                    } else {
                        Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } catch(Exception e) {

        }
    }
}
