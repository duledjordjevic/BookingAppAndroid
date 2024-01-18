package com.example.bookingapplication.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.bookingapplication.helpers.LocalDateTimeAdapter;
import com.example.bookingapplication.model.enums.NotificationType;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;

import java.time.LocalDateTime;

public class NotificationForHost implements Parcelable {
    private Long id;
    private NotificationType type;
    private String description;
    private Host host;
    private boolean read;
    @JsonAdapter(LocalDateTimeAdapter.class)
    @SerializedName("dateTime")
    private LocalDateTime dateTime;

    public NotificationForHost(Long id, NotificationType type, String description, Host host, boolean isRead, LocalDateTime dateTime) {
        this.id = id;
        this.type = type;
        this.description = description;
        this.host = host;
        this.read = isRead;
        this.dateTime = dateTime;
    }

    protected NotificationForHost(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readLong();
        }
        description = in.readString();
        read = in.readByte() != 0;
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
        dest.writeByte((byte) (read ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<NotificationForHost> CREATOR = new Creator<NotificationForHost>() {
        @Override
        public NotificationForHost createFromParcel(Parcel in) {
            return new NotificationForHost(in);
        }

        @Override
        public NotificationForHost[] newArray(int size) {
            return new NotificationForHost[size];
        }
    };

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public NotificationType getType() {
        return type;
    }

    public void setType(NotificationType type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Host getHost() {
        return host;
    }

    public void setHost(Host host) {
        this.host = host;
    }

    public boolean isRead() {
        return read;
    }

    public void setRead(boolean read) {
        read = read;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }
}
