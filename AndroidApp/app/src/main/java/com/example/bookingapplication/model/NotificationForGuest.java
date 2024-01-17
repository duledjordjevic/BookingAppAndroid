package com.example.bookingapplication.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.time.LocalDateTime;

public class NotificationForGuest implements Parcelable {
    private Long id;
    private String description;
    private Guest guest;
    private boolean isRead;
    private LocalDateTime dateTime;

    public NotificationForGuest(Long id, String description, Guest guest, boolean isRead, LocalDateTime dateTime) {
        this.id = id;
        this.description = description;
        this.guest = guest;
        this.isRead = isRead;
        this.dateTime = dateTime;
    }

    protected NotificationForGuest(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readLong();
        }
        description = in.readString();
        isRead = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (id == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(id);
        }
        dest.writeString(description);
        dest.writeByte((byte) (isRead ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<NotificationForGuest> CREATOR = new Creator<NotificationForGuest>() {
        @Override
        public NotificationForGuest createFromParcel(Parcel in) {
            return new NotificationForGuest(in);
        }

        @Override
        public NotificationForGuest[] newArray(int size) {
            return new NotificationForGuest[size];
        }
    };

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Guest getGuest() {
        return guest;
    }

    public void setGuest(Guest guest) {
        this.guest = guest;
    }

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        isRead = read;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }
}
