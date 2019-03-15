package pahwa.deepansh.instagramclone;


import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.io.ByteArrayOutputStream;


/**
 * A simple {@link Fragment} subclass.
 */
public class SharePicTab extends Fragment implements View.OnClickListener {

    private ImageView imageShare;
    private EditText imageDescription;
    private Button imageShareButton;

    Bitmap receivedImageBitmap;

    public SharePicTab() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_share_pic_tab, container, false);

        imageShare = view.findViewById(R.id.imageShare);
        imageDescription = view.findViewById(R.id.imageDescription);
        imageShareButton = view.findViewById(R.id.imageShareButton);

        imageShare.setOnClickListener(SharePicTab.this);
        imageShareButton.setOnClickListener(SharePicTab.this);

        return view;
    }

    @Override
    public void onClick(View view) {

        switch(view.getId()){

            case R.id.imageShare:
                if (Build.VERSION.SDK_INT >= 23 &&
                        ActivityCompat.checkSelfPermission(getContext(),
                                Manifest.permission.READ_EXTERNAL_STORAGE)
                                != PackageManager.PERMISSION_GRANTED){

                    requestPermissions(new String[]{
                    Manifest.permission.READ_EXTERNAL_STORAGE},
                            1000);
                } else {
                    getChosenImage();
                }

            case R.id.imageShareButton:

                if(receivedImageBitmap != null){

                    if (imageDescription.getText().toString().equals("")){
                        FancyToast.makeText(getContext(),"Error: You Must Enter a description!!",FancyToast.LENGTH_SHORT,FancyToast.WARNING,true).show();
                    }else {

                        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                        receivedImageBitmap.compress(Bitmap.CompressFormat.PNG,100,byteArrayOutputStream);
                        byte[] bytes = byteArrayOutputStream.toByteArray();
                        ParseFile parseFile = new ParseFile("pic.png",bytes);
                        ParseObject parseObject = new ParseObject("Photo");
                        parseObject.put("picture",parseFile);
                        parseObject.put("image_des",imageDescription.getText().toString());
                        parseObject.put("username", ParseUser.getCurrentUser().getUsername());

                        final ProgressDialog dialog = new ProgressDialog(getContext());

                        dialog.setMessage("Loading..");
                        dialog.show();

                        parseObject.saveInBackground(new SaveCallback() {
                            @Override
                            public void done(ParseException e) {

                                if (e==null){
                                    FancyToast.makeText(getContext(),"Task Complete!!",Toast.LENGTH_SHORT,FancyToast.SUCCESS,true).show();
                                }else {
                                    FancyToast.makeText(getContext(),"Unknown Error: "+e.getMessage(),Toast.LENGTH_SHORT,FancyToast.ERROR,true).show();
                                }
                                dialog.dismiss();
                            }
                        });

                    }

                } else {

                    FancyToast.makeText(getContext(),"Error: You Must Select an Image!!",FancyToast.LENGTH_SHORT,FancyToast.WARNING,true).show();

                }
                break;

        }


    }

    private void getChosenImage() {

        FancyToast.makeText(getContext(),"Now you can access the image", Toast.LENGTH_SHORT,FancyToast.SUCCESS,true).show();

        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(intent,2000);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 1000){

            if (grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                getChosenImage();
            }

        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 2000){

            if(resultCode==Activity.RESULT_OK){

                //DO something with your captured image

                try {

                    Uri selectedImage = data.getData();
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};
                    Cursor cursor = getActivity().getContentResolver().
                            query(selectedImage,filePathColumn,null,null,null);

                    cursor.moveToFirst();
                    int coloumnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    String picturePath = cursor.getString(coloumnIndex);
                    cursor.close();
                    receivedImageBitmap = BitmapFactory.decodeFile(picturePath);

                    imageShare.setImageBitmap(receivedImageBitmap);


                } catch (Exception e){
                    e.printStackTrace();
                }


            }
        }

    }
}
