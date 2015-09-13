package com.uncc.contactsdirectory;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class CreateContact extends AppCompatActivity {

    ImageButton btn_addImage;
    String image_uri = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_contact);

        btn_addImage = (ImageButton) findViewById(R.id.btn_addImage);
        Button btn_saveContact = (Button) findViewById(R.id.btn_saveContact);
        Button btn_cancelContact = (Button)findViewById(R.id.btn_cancelContact);

        btn_addImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_addImage = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                intent_addImage.setType("image/*");
                startActivityForResult(intent_addImage, MainActivity.REQUEST_IMAGE_OPEN);
            }
        });

        btn_saveContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText edt_name = (EditText) findViewById(R.id.edt_name);
                EditText edt_phone = (EditText) findViewById(R.id.edt_phone);
                EditText edt_email = (EditText) findViewById(R.id.edt_email);

                if(getStringFromEdt(edt_name).trim().equals("")){
                    Toast.makeText(CreateContact.this,"Please input a valid name",Toast.LENGTH_SHORT).show();
                    edt_name.requestFocus();
                }
                else if(getStringFromEdt(edt_phone).trim().length() < 10){
                    Toast.makeText(CreateContact.this,"Please input a valid phone number",Toast.LENGTH_SHORT).show();
                    edt_phone.requestFocus();
                }
                else if(!(isEmailValid(getStringFromEdt(edt_email))) ){
                    Toast.makeText(CreateContact.this,"Please input a valid email",Toast.LENGTH_SHORT).show();
                    edt_email.requestFocus();
                }
                else{
                    Intent intent_createContact = new Intent();
                    User newContact = new User(getStringFromEdt(edt_email),getStringFromEdt(edt_name),getStringFromEdt(edt_phone),image_uri);
                    intent_createContact.putExtra(MainActivity.USER_KEY, newContact);
                    setResult(RESULT_OK, intent_createContact);

                    finish();
                }
            }
        });

        btn_cancelContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    protected String getStringFromEdt(EditText edt){
        return edt.getText().toString();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == MainActivity.REQUEST_IMAGE_OPEN && resultCode == RESULT_OK) {
            Uri fullPhotoUri = data.getData();

            btn_addImage.setImageURI(fullPhotoUri);
            image_uri = fullPhotoUri.toString();
        }
    }

    boolean isEmailValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
}
