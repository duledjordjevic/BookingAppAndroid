package com.example.bookingapplication.model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.bookingapplication.R;

public class ApartmentCard implements Parcelable {
    private Long id;
    private String title;
    private String descriptionInfo;
    private String descriptionRating;
    private  String  image;
    private Boolean isLiked;
//    private Bitmap bitmap;

    public ApartmentCard() {
    }
    public ApartmentCard(Long id, String title, String descriptionInfo, String descriptionRating,  String  image) {
        this.id = id;
        this.title = title;
        this.descriptionInfo = descriptionInfo;
        this.descriptionRating = descriptionRating;
        this.image = image;
//        this.isLiked = isLiked;
//        Log.d("Slika: ", String.valueOf(this.image));
//        this.bitmap = BitmapFactory.decodeByteArray(this.image, 0, this.image.length);
    }
    public ApartmentCard(Long id, String title, String descriptionInfo, String descriptionRating,  String  image,Boolean isLiked) {
        this.id = id;
        this.title = title;
        this.descriptionInfo = descriptionInfo;
        this.descriptionRating = descriptionRating;
        this.image = image;
        this.isLiked = isLiked;

    }

    public ApartmentCard(Card card){
        this.id = card.getId();
        this.title = card.getTitle();
        this.descriptionInfo = card.getAddress().toString();
        this.descriptionRating = card.getAvgRate().toString();
        this.isLiked = false;
//        this.image = card.getImage();
    }
    protected ApartmentCard(Parcel in) {
        id = in.readLong();
        title = in.readString();
        descriptionInfo = in.readString();
        descriptionRating = in.readString();
        image = in.readString();
        isLiked = in.readBoolean();
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
    public Boolean getIsLiked(){return isLiked;}
    public void setIsLiked(Boolean isLiked){this.isLiked = isLiked;}

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
        dest.writeString(image);
        dest.writeBoolean(isLiked);
    }

//    public Bitmap getBitmap() {
//        return bitmap;
//    }
//
//    public void setBitmap(Bitmap bitmap) {
//        this.bitmap = bitmap;
//    }
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
