package pahwa.deepansh.instagramclone;

import androidx.appcompat.app.AppCompatActivity;
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
import com.parse.SaveCallback;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.List;

//Sign up class

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button submitObject,getAllData,btnTransition;
    private EditText editName,editPunchSpeed,editKickSpeed,editKickPower;
    private TextView getData ;

    private String allKickBoxers ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        submitObject = findViewById(R.id.submitObject);
        submitObject.setOnClickListener(MainActivity.this);

        getAllData = findViewById(R.id.btnGetData);

        editName = findViewById(R.id.editName);
        editPunchSpeed = findViewById(R.id.editPunchSpeed);
        editKickSpeed = findViewById(R.id.editKickSpeed);
        editKickPower = findViewById(R.id.editKickPower);

        getData = findViewById(R.id.getData);

        btnTransition = findViewById(R.id.btnTransition);

        getData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParseQuery<ParseObject> parseQuery = ParseQuery.getQuery("KickBoxer");

                parseQuery.getInBackground("4ZNMJXaCHs", new GetCallback<ParseObject>() {
                    @Override
                    public void done(ParseObject object, ParseException e) {

                        if (object != null && e == null){
                            getData.setText(object.get("name").toString());
                        }

                    }
                });
            }
        });

        getAllData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                allKickBoxers = "";


                ParseQuery<ParseObject> queryAll = ParseQuery.getQuery("KickBoxer");

                queryAll.whereGreaterThanOrEqualTo("Punch_Speed",2000);

                queryAll.findInBackground(new FindCallback<ParseObject>() {
                    @Override
                    public void done(List<ParseObject> objects, ParseException e) {

                        if (e == null){
                            if (objects.size()>0){

                                for (ParseObject kickBoxer:objects) {
                                    allKickBoxers = allKickBoxers + kickBoxer.get("name") + "\n" ;                                 }
                                FancyToast.makeText(MainActivity.this, allKickBoxers, FancyToast.LENGTH_SHORT, FancyToast.SUCCESS, true).show();

                            } else {
                                FancyToast.makeText(MainActivity.this, "Error", FancyToast.LENGTH_SHORT, FancyToast.ERROR, true).show();

                            }
                        }
                    }
                });


            }
        });



        btnTransition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


    }

    @Override
    public void onClick(View v) {

        try {
            final ParseObject kickBoxer = new ParseObject("KickBoxer");
            kickBoxer.put("name", editName.getText().toString());
            kickBoxer.put("Punch_Speed", Integer.parseInt(editPunchSpeed.getText().toString()));
            kickBoxer.put("Kick_Speed", Integer.parseInt(editKickSpeed.getText().toString()));
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
