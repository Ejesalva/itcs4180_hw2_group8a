package com.uncc.contactsdirectory;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    static final int REQUEST_IMAGE_OPEN = 1;
    final static int CONTACT_CODE_CREATE = 1000;
    final static int CONTACT_CODE_EDIT = 1100;
    final static String CONTACTS_KEY = "contacts";
    final static String USER_KEY = "user";
    final static String ID_KEY = "userId";
    ArrayList<User> contactList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn_finish = (Button) findViewById(R.id.btn_finish);
        Button btn_createContact = (Button) findViewById(R.id.btn_createContact);
        Button btn_editContact = (Button) findViewById(R.id.btn_editContact);
        Button btn_deleteContact = (Button) findViewById(R.id.btn_deleteContact);
        Button btn_displayContacts = (Button) findViewById(R.id.btn_displayContacts);

        btn_finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btn_createContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent activity_CreateContact = new Intent(MainActivity.this, CreateContact.class);
                startActivityForResult(activity_CreateContact, CONTACT_CODE_CREATE);
            }
        });

        btn_editContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent activity_editContact = new Intent(MainActivity.this, EditContact.class);
                if(!contactList.isEmpty()){
                    activity_editContact.putParcelableArrayListExtra(CONTACTS_KEY,contactList);
                    startActivityForResult(activity_editContact, CONTACT_CODE_EDIT);
                }
                else{
                    Toast.makeText(MainActivity.this,"No contacts to edit",Toast.LENGTH_LONG).show();
                }

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == CONTACT_CODE_CREATE){
            if(resultCode == RESULT_OK){
                User data_user = data.getExtras().getParcelable(USER_KEY);
                contactList.add(data_user);

                Toast.makeText(getBaseContext(), "Contact " + data_user.name + " created", Toast.LENGTH_SHORT).show();

            }
            else if(resultCode == RESULT_CANCELED){
                Toast.makeText(getBaseContext(), "Contact creation failed", Toast.LENGTH_SHORT).show();
            }
        }
        else if(requestCode == CONTACT_CODE_EDIT){
            if(resultCode == RESULT_OK){
                int data_userId = data.getExtras().getInt(ID_KEY);
                User data_user = data.getExtras().getParcelable(USER_KEY);

                contactList.set(data_userId,data_user);
                Toast.makeText(getBaseContext(), "Contact " + data_user.name + " edited", Toast.LENGTH_SHORT).show();
            }
            else if(resultCode == RESULT_CANCELED){
                Toast.makeText(getBaseContext(), "Contact edit failed", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
