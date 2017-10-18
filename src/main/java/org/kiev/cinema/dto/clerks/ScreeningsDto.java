package org.kiev.cinema.dto.clerks;

import org.kiev.cinema.entity.Address;
import org.kiev.cinema.entity.Movie;
import org.kiev.cinema.entity.Screening;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class ScreeningsDto {
    private Movie movie;
    private List<AddressShowtime> addressShowtimeList = new ArrayList<>();

    public ScreeningsDto(Movie movie, List<Address> addresses, List<Date> dates) {
        this.movie = movie;
        for(Address address : addresses) {
            addressShowtimeList.add(new AddressShowtime(address, dates));
        }
    }

    public ScreeningsDto(Movie movie) {
        this.movie = movie;
    }

    public void addAddressShowtime(AddressShowtime addressShowtime) {
        for(AddressShowtime item : addressShowtimeList) {
            if(item.getAddressId().equals(addressShowtime.getAddressId())) {
                return;
            }
        }
        addressShowtimeList.add(addressShowtime);
    }

    public void addScreeningIfDatesExist(List<Screening> screenings, Integer addressId) {
        for(AddressShowtime addressShowtime : addressShowtimeList) {
            if(addressShowtime.getAddressId().equals(addressId)) {
                addressShowtime.addScreeningsIfDatesExist(screenings);
            }
        }
    }

    public Movie getMovie() {
        return movie;
    }

    public List<AddressShowtime> getAddressShowtimeList() {
        return addressShowtimeList;
    }
}
