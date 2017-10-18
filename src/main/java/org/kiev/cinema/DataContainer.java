package org.kiev.cinema;

import org.kiev.cinema.entity.Address;
import org.kiev.cinema.entity.Movie;
import org.kiev.cinema.enums.GenreEnum;
import org.kiev.cinema.service.AddressService;
import org.kiev.cinema.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class DataContainer {

    @Autowired
    private MovieService movieService;
    @Autowired
    private AddressService addressService;

    private List<Movie> movies;
    private List<Address> addresses;
    private Map<Long, String> moviesIdTitleMap = new HashMap<>();
    private Map<Integer, String> addressesIdStringMap = new HashMap<>();

    public List<Movie> getMoviesFromToday() {
        if(this.movies == null || this.movies.isEmpty()) {
            this.movies = movieService.listAllFromToday(CinemaConstants.DAYS_INTERVAL);
        }
        return this.movies;
    }

    public void uploadMoviesData() {
        this.movies = movieService.listAllFromToday(CinemaConstants.DAYS_INTERVAL);
        this.moviesIdTitleMap = new HashMap<>();
        for(Movie movie : this.movies) {
            moviesIdTitleMap.put(movie.getId(), movie.getTitle());
        }
    }

    public void uploadAddressesData() {
        this.addresses = addressService.listAll();
        this.addressesIdStringMap = new HashMap<>();
        for(Address address : this.addresses) {
            this.addressesIdStringMap.put(address.getId(), address.getFullAddress());
        }
    }

    public Map<Long, String> getMoviesIdTitleMap() {
        if(this.moviesIdTitleMap == null || this.moviesIdTitleMap.isEmpty()) {
            this.moviesIdTitleMap = new HashMap<>();
            getMoviesFromToday();
            for(Movie movie : this.movies) {
                moviesIdTitleMap.put(movie.getId(), movie.getTitle());
            }
        }
        return this.moviesIdTitleMap;
    }

    public List<Address> getAddresses() {
        if(this.addresses == null || this.addresses.isEmpty()) {
            this.addresses = addressService.listAll();
        }
        return this.addresses;
    }

    public Map<Integer, String> getAddressesIdStringMap() {
        if(this.addressesIdStringMap == null || this.addressesIdStringMap.isEmpty()) {
            this.addressesIdStringMap = new HashMap<>();
            getAddresses();
            for(Address address : this.addresses) {
                this.addressesIdStringMap.put(address.getId(), address.getFullAddress());
            }
        }
        return this.addressesIdStringMap;
    }

    public String getAddressById(Integer addressId) {
        return getAddressesIdStringMap().get(addressId);
    }

    public String getMovieById(Long movieId) {
        return getMoviesIdTitleMap().get(movieId);
    }

    public List<GenreEnum> getGenreEnums() {
        return Arrays.asList(GenreEnum.values());
    }

    public String getSkype() {
        return CinemaConstants.SKYPE;
    }

    public String getPhone() {
        return CinemaConstants.PHONE;
    }

    public String getEmail() {
        return CinemaConstants.EMAIL;
    }

}
