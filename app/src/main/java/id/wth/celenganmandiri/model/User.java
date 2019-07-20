package id.wth.celenganmandiri.model;

import java.io.Serializable;

/**
 * Created by adminmc on 15/03/17.
 */

public class User implements Serializable {
    String nama, email, phone, pt, alamat, username, id, photo, rating, longitude, latitude;

    public User() {}

    public User(String nama, String email, String phone, String pt, String alamat, String username,
                String id, String photo, String rating, String longitude, String latitude) {
        this.nama = nama;
        this.username = username;
        this.email = email;
        this.phone = phone;
        this.pt = pt;
        this.alamat = alamat;
        this.id = id;
        this.photo = photo;
        this.rating = rating;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getRating() {
        return rating;
    }

    public void setPt(String pt) {
        this.pt = pt;
    }

    public String getPt() {
        return pt;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getPhoto() {
        return photo;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getNama() {
        return nama;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhone() {
        return phone;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}
