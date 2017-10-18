package org.kiev.cinema.dto.clerks;

import org.kiev.cinema.entity.Address;
import org.kiev.cinema.entity.Movie;
import org.kiev.cinema.entity.Screening;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class ScreeningsDtoList {
    List<ScreeningsDto> screeningsDtoList = new ArrayList<>();

    public ScreeningsDtoList() {
    }

    public ScreeningsDtoList(List<Movie> movies, List<Address> addresses, List<Date> dates) {
        for(Movie movie : movies) {
            screeningsDtoList.add(new ScreeningsDto(movie, addresses, dates));
        }
    }

    public ScreeningsDtoList(List<Movie> movies) {
        for(Movie movie : movies) {
            screeningsDtoList.add(new ScreeningsDto(movie));
        }
    }

    public void addAddressShowtime(List<Screening> screenings, Long movieId, Address address) {
        for(ScreeningsDto screeningsDto : screeningsDtoList) {
            if(screeningsDto.getMovie().getId().equals(movieId)) {
                AddressShowtime addressShowtime = new AddressShowtime(address);
                addressShowtime.addScreenings(screenings);
                screeningsDto.addAddressShowtime(addressShowtime);
            }
        }
    }

    public void addScreeningsIfDatesExist(List<Screening> screenings, Long movieId, Integer addressId) {
        for(ScreeningsDto screeningsDto : screeningsDtoList) {
            if(screeningsDto.getMovie().getId().equals(movieId)) {
                screeningsDto.addScreeningIfDatesExist(screenings, addressId);
            }
        }
    }

    public List<ScreeningsDto> getScreeningsDtoList() {
        return screeningsDtoList;
    }
}
