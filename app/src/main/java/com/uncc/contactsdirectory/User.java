package com.uncc.contactsdirectory;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by renzotrigoso on 9/12/15.
 */
public class User implements Parcelable {
    String name, email, img_uri, phoneNumber;

    public User(String email, String name, String phoneNumber, String img_uri) {
        this.email = email;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.img_uri = img_uri;
    }

    public void editAllFields(String email, String name, String phoneNumber, String img_uri){
        this.email = email;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.img_uri = img_uri;
    }

    protected User(Parcel in) {
        name = in.readString();
        email = in.readString();
        img_uri = in.readString();
        phoneNumber = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(email);
        dest.writeString(img_uri);
        dest.writeString(phoneNumber);
    }
}
