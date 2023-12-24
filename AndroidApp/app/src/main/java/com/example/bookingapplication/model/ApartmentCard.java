package com.example.bookingapplication.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class ApartmentCard implements Parcelable {
    private Long id;
    private String title;
    private String descriptionInfo;
    private String descriptionRating;
    private int image;

    public ApartmentCard() {
    }
    public ApartmentCard(Long id, String title, String descriptionInfo, String descriptionRating, int image) {
        this.id = id;
        this.title = title;
        this.descriptionInfo = descriptionInfo;
        this.descriptionRating = descriptionRating;
        this.image = image;
    }

    protected ApartmentCard(Parcel in) {
        id = in.readLong();
        title = in.readString();
        descriptionInfo = in.readString();
        descriptionRating = in.readString();
        image = in.readInt();
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescriptionInfo() {
        return descriptionInfo;
    }

    public void setDescriptionInfo(String descriptionInfo) {
        this.descriptionInfo = descriptionInfo;
    }

    public String getDescriptionRating() {
        return descriptionRating;
    }

    public void setDescriptionRating(String descriptionRating) {
        this.descriptionRating = descriptionRating;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(title);
        dest.writeString(descriptionInfo);
        dest.writeString(descriptionRating);
        dest.writeInt(image);
    }

    @Override
    public String toString() {
        return "ApartmentCard{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", descriptionInfo='" + descriptionInfo + '\'' +
                ", descriptionRating='" + descriptionRating + '\'' +
                ", image=" + image +
                '}';
    }

    public static final Creator<ApartmentCard> CREATOR = new Creator<ApartmentCard>() {
        @Override
        public ApartmentCard createFromParcel(Parcel in) {
            return new ApartmentCard(in);
        }

        @Override
        public ApartmentCard[] newArray(int size) {
            return new ApartmentCard[size];
        }
    };
}
