package pahwa.deepansh.instagramclone;


import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.shashank.sony.fancytoastlib.FancyToast;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileTab extends Fragment {

    private EditText enterProfileName,enterBio,enterProfession,enterHobbies;
    private Button updateInfo;


    public ProfileTab() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_profile_tab, container, false);

        enterProfileName = view.findViewById(R.id.enterName);
        enterBio = view.findViewById(R.id.enterBio);
        enterProfession = view.findViewById(R.id.enterProfession);
        enterHobbies = view.findViewById(R.id.enterHobbies);
        updateInfo = view.findViewById(R.id.updateInfo);

        final ParseUser parseUser = ParseUser.getCurrentUser();

        if(parseUser.get("profileName") == null) {

            enterProfileName.setText("");

        } else {
            enterProfileName.setText(parseUser.get("profileName").toString());
        }

        if(parseUser.get("profileBio") == null) {

            enterBio.setText("");

        } else {
            enterBio.setText(parseUser.get("profileBio").toString());
        }


        if(parseUser.get("profileProfession") == null) {

            enterProfession.setText("");

        } else {
            enterProfession.setText(parseUser.get("profileProfession").toString());
        }


        if(parseUser.get("profileHobbies") == null) {

            enterHobbies.setText("");

        } else {
            enterHobbies.setText(parseUser.get("profileHobbies").toString());
        }



        updateInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                parseUser.put("profileName",enterProfileName.getText().toString());
                parseUser.put("profileBio",enterBio.getText().toString());
                parseUser.put("profileProfession",enterProfession.getText().toString());
                parseUser.put("profileHobbies",enterHobbies.getText().toString());


                parseUser.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {


                        if (e == null) {


                            FancyToast.makeText(getContext(),
                                    "Info Updated",
                                    FancyToast.LENGTH_SHORT, FancyToast.SUCCESS, true).show();
                        } else {
                            FancyToast.makeText(getContext(), "Error",
                                    FancyToast.LENGTH_SHORT, FancyToast.ERROR, true).show();
                        }

                    }
                });

            }
        });


        return view;
    }

}
