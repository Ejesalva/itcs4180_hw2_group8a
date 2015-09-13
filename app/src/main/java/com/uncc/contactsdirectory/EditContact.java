package com.uncc.contactsdirectory;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;

public class EditContact extends AppCompatActivity {
    User selectedUser = null;
    int selectedUserId;
    ImageButton btn_picSelected;
    String image_uri = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_contact);

        Button btn_selectContact = (Button) findViewById(R.id.btn_selectContact);
        Button btn_editContact = (Button) findViewById(R.id.btn_editContact);
        Button btn_cancelEditContact = (Button) findViewById(R.id.btn_cancelEditContact);
        final ImageButton btn_picSelected = (ImageButton) findViewById(R.id.btn_addImageSelected);


        if(this.getIntent().getParcelableArrayListExtra(MainActivity.CONTACTS_KEY) != null){
            final ArrayList<User> tempContactList = this.getIntent().getParcelableArrayListExtra(MainActivity.CONTACTS_KEY);
            final ArrayList<String> tempNameList = new ArrayList<>();

            for (User user : tempContactList) {
                tempNameList.add(user.name);
            }

            final CharSequence[] charSequenceItemsList = tempNameList.toArray(new CharSequence[tempNameList.size()]);

            btn_selectContact.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(EditContact.this);
                    builder.setTitle("Select Contact").setItems(charSequenceItemsList, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            User contact = tempContactList.get(which);
                            EditText edt_nameSelected = (EditText) findViewById(R.id.edt_nameSelected);
                            EditText edt_phoneSelected = (EditText) findViewById(R.id.edt_phoneSelected);
                            EditText edt_emailSelected = (EditText) findViewById(R.id.edt_emailSelected);

                            edt_nameSelected.setText(contact.name);
                            edt_phoneSelected.setText(contact.phoneNumber);
                            edt_emailSelected.setText(contact.email);
                            btn_picSelected.setImageURI(Uri.parse(contact.img_uri));

                            selectedUser = contact;
                            selectedUserId = which;
                        }
                    });

                    final AlertDialog dialog_contactSelection = builder.create();
                    dialog_contactSelection.show();
                }
            });
        }

        if(selectedUser != null){
            btn_editContact.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    EditText edt_nameSelected = (EditText) findViewById(R.id.edt_nameSelected);
                    EditText edt_phoneSelected = (EditText) findViewById(R.id.edt_phoneSelected);
                    EditText edt_emailSelected = (EditText) findViewById(R.id.edt_emailSelected);

                    if(getStringFromEdt(edt_nameSelected).trim().equals("")){
                        Toast.makeText(EditContact.this,"Please input a valid name",Toast.LENGTH_SHORT).show();
                        edt_nameSelected.requestFocus();
                    }
                    else if(getStringFromEdt(edt_phoneSelected).trim().length() < 10){
                        Toast.makeText(EditContact.this,"Please input a valid phone number",Toast.LENGTH_SHORT).show();
                        edt_phoneSelected.requestFocus();
                    }
                    else if(!(isEmailValid(getStringFromEdt(edt_emailSelected))) ){
                        Toast.makeText(EditContact.this,"Please input a valid email",Toast.LENGTH_SHORT).show();
                        edt_emailSelected.requestFocus();
                    }
                    else {
                        Intent intent_editContact = new Intent();
                        selectedUser.editAllFields(getStringFromEdt(edt_emailSelected), getStringFromEdt(edt_phoneSelected), getStringFromEdt(edt_nameSelected), image_uri);
                        intent_editContact.putExtra(MainActivity.USER_KEY, selectedUser);
                        intent_editContact.putExtra(MainActivity.ID_KEY, selectedUserId);
                        setResult(RESULT_OK, intent_editContact);

                        finish();
                    }
                }
            });
        }
        else Toast.makeText(EditContact.this,"Please select a contact to edit", Toast.LENGTH_SHORT).show();

        btn_cancelEditContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btn_picSelected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_addImage = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                intent_addImage.setType("image/*");
                startActivityForResult(intent_addImage, MainActivity.REQUEST_IMAGE_OPEN);
            }
        });
    }

    protected String getStringFromEdt(EditText edt){
        return edt.getText().toString();
    }

    boolean isEmailValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == MainActivity.REQUEST_IMAGE_OPEN && resultCode == RESULT_OK) {
            Uri fullPhotoUri = data.getData();

            btn_picSelected.setImageURI(fullPhotoUri);
            image_uri = fullPhotoUri.toString();
        }
    }
}
