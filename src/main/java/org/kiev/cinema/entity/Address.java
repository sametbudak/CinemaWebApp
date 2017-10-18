package org.kiev.cinema.entity;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.kiev.cinema.CinemaConstants;

import javax.persistence.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="addresses")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private static String name = "Movie7Theater";

    private static String city = "Kiev";

    @Column(nullable = false)
    private String district;

    @Column(nullable = false)
    private String street;

    @Column(nullable = false, name = "street_number")
    private Integer streetNumber;

    @Column
    private String picture;

    @OneToMany(mappedBy = "address", cascade = CascadeType.ALL)
    private List<Room> rooms = new ArrayList<>();

    public Address() {
    }

    public Address(Integer id, String district, String street, Integer streetNumber) {
        this.id = id;
        this.district = district;
        this.street = street;
        this.streetNumber = streetNumber;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public static String getName() {
        return name;
    }

    public String getCity() {
        return city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public Integer getStreetNumber() {
        return streetNumber;
    }

    public void setStreetNumber(Integer streetNumber) {
        this.streetNumber = streetNumber;
    }

    public String getPicture() {
        return picture;
    }

    public String reducedPicturePath() {
        return picture.replace(CinemaConstants.ADDRESSES_PATH, "").replace(File.separator, "/");
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public List<Room> getRooms() {
        return rooms;
    }

    public void setRooms(List<Room> rooms) {
        this.rooms = rooms;
    }

    // add
    public void addRoom(Room room) {
        room.setAddress(this);
        this.rooms.add(room);
    }

    public static Address fromJSON(Path json) throws FileNotFoundException {
        Gson gson = new GsonBuilder().create();
        return gson.fromJson( new FileReader(json.toString()), Address.class );
    }

    public String getFullAddress() {
        return String.format("%s, No.%d %s, %s, %s", name, streetNumber, street, district, city);
    }

    public String getAddress() {
        return String.format("No.%d %s, %s, %s", streetNumber, street, district, city);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Address address = (Address) o;

        return id != null ? id.equals(address.id) : address.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
