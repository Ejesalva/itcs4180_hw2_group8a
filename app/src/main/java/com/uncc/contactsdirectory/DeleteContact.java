package com.uncc.contactsdirectory;

import android.app.AlertDialog;
import android.content.DialogInterface;
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

import java.util.ArrayList;

public class DeleteContact extends AppCompatActivity {
    User selectedUser = null;
    int selectedUserId;
    ImageButton btn_picSelected;
    String image_uri = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_contact);

        Button btn_selectContactToDel = (Button) findViewById(R.id.btn_selectContactToDel);
        Button btn_delContact = (Button) findViewById(R.id.btn_delContact);
        Button btn_cancelDelContact = (Button) findViewById(R.id.btn_cancelDelContact);
        final ImageButton btn_picSelectedToDel = (ImageButton) findViewById(R.id.btn_addImageToDel);

        if (this.getIntent().getParcelableArrayListExtra(MainActivity.CONTACTS_KEY) != null) {
            final ArrayList<User> tempContactList = this.getIntent().getParcelableArrayListExtra(MainActivity.CONTACTS_KEY);
            final ArrayList<String> tempNameList = new ArrayList<>();

            for (User user : tempContactList) {
                tempNameList.add(user.name);
            }

            final CharSequence[] charSequenceItemsList = tempNameList.toArray(new CharSequence[tempNameList.size()]);

            btn_selectContactToDel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(DeleteContact.this);
                    builder.setTitle("Select Contact").setItems(charSequenceItemsList, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            User contact = tempContactList.get(which);
                            EditText edt_nameSelectedToDel = (EditText) findViewById(R.id.edt_nameToDel);
                            EditText edt_phoneSelectedToDel = (EditText) findViewById(R.id.edt_phoneToDel);
                            EditText edt_emailSelectedToDel = (EditText) findViewById(R.id.edt_emailToDel);

                            edt_nameSelectedToDel.setText(contact.name);
                            edt_phoneSelectedToDel.setText(contact.phoneNumber);
                            edt_emailSelectedToDel.setText(contact.email);
                            btn_picSelectedToDel.setImageURI(Uri.parse(contact.img_uri));

                            selectedUser = contact;
                            selectedUserId = which;
                        }
                    });

                    final AlertDialog dialog_contactSelection = builder.create();
                    dialog_contactSelection.show();
                }
            });
        }

        btn_delContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(selectedUser != null){

                    Intent intent_editContact = new Intent();
                    intent_editContact.putExtra(MainActivity.ID_KEY, selectedUserId);
                    setResult(RESULT_OK, intent_editContact);

                    finish();
                }
            }
        });

        btn_cancelDelContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    

}
