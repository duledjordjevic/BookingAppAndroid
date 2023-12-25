package com.example.bookingapplication.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class AccommodationApprovingCard  implements Parcelable {
    private Long id;
    private String title;
    private Address address;
    private int image;
    private String description;

    public AccommodationApprovingCard(Long id, String title, Address address, int image, String description) {
        this.id = id;
        this.title = title;
        this.address = address;
        this.image = image;
        this.description = description;
    }

    protected AccommodationApprovingCard(Parcel in) {
        id = in.readLong();
        title = in.readString();
        description = in.readString();
        address.setState(in.readString());
        address.setCity(in.readString());
        address.setStreet(in.readString());
    }
    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public Address getAddress() {
        return address;
    }

    public int getImage() {
        return image;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(title);
        dest.writeString(description);
        dest.writeString(address.getState());
        dest.writeString(address.getCity());
        dest.writeString(address.getStreet());
    }
    public static final Creator<AccommodationApprovingCard> CREATOR = new Creator<AccommodationApprovingCard>() {
        @Override
        public AccommodationApprovingCard createFromParcel(Parcel in) {
            return new AccommodationApprovingCard(in);
        }

        @Override
        public AccommodationApprovingCard[] newArray(int size) {
            return new AccommodationApprovingCard[size];
        }
    };
}
