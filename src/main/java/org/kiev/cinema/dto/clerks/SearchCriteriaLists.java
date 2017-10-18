package org.kiev.cinema.dto.clerks;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class SearchCriteriaLists {

    private List<Long> movieIdList = new ArrayList<>();
    private List<Integer> addressIdList = new ArrayList<>();
    private List<Date> dateList = new ArrayList<>();
    private ShowtimeRange showtimeRange;

    public SearchCriteriaLists() {
    }

    public List<Integer> getAddressIdList() {
        return addressIdList;
    }

    public void setAddressIds(List<Integer> addressIdList) {
        this.addressIdList = addressIdList;
    }

    public List<Date> getDateList() {
        return dateList;
    }

    public void setDates(List<Date> dateList) {
        this.dateList = dateList;
    }

    public ShowtimeRange getShowtimeRange() {
        return showtimeRange;
    }

    public void setShowtimeRange(ShowtimeRange showtimeRange) {
        this.showtimeRange = showtimeRange;
    }

    public List<Long> getMovieIdList() {
        return movieIdList;
    }

    public void setMovieIds(List<Long> movieIdList) {
        this.movieIdList = movieIdList;
    }

}
