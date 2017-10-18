package org.kiev.cinema.dto.visitors;

import org.kiev.cinema.date.DateUtils;
import org.kiev.cinema.entity.Address;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class AllMoviesShowtimesDto {
    private Long movieId;
    private String movieTitle;
    private Date date;
    private Integer movieDuration;
    private String dateAsEEEMMMdyyyy;
    private List<AddressIdValueMapper> addressIdValueMapperList = new ArrayList<>();
    private List<ScreeningIdKeyValueMapper> screeningIdKeyValueMapperList = new ArrayList<>();

    public AllMoviesShowtimesDto() {

    }

    // Spring repository works with java.util.Date
    public AllMoviesShowtimesDto(Long movieId, String movieTitle, Integer movieDuration, java.util.Date date) {
        this.movieId = movieId;
        this.movieTitle = movieTitle;
        this.movieDuration = movieDuration;
        this.date = new Date(date.getTime());
        this.dateAsEEEMMMdyyyy = DateUtils.formatAsEEEMMMdyyyy(this.date);
    }

    private void addAddressIdValueMapper(AddressIdValueMapper addressIdValueMapper) {
        this.addressIdValueMapperList.add(addressIdValueMapper);
    }

    private void addScreeningIdKeyValueMapper(ScreeningIdKeyValueMapper screeningIdKeyValueMapper) {
        this.screeningIdKeyValueMapperList.add(screeningIdKeyValueMapper);
    }

    public Long getMovieId() {
        return movieId;
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public Date getDate() {
        return date;
    }

    public Integer getMovieDuration() {
        return movieDuration;
    }

    public String getDateAsEEEMMMdyyyy() {
        return dateAsEEEMMMdyyyy;
    }

    public List<AddressIdValueMapper> getAddressIdValueMapperList() {
        return addressIdValueMapperList;
    }

    public List<ScreeningIdKeyValueMapper> getScreeningIdKeyValueMapperList() {
        return screeningIdKeyValueMapperList;
    }

    public Integer hours() {
        return movieDuration/60;
    }

    public Integer minutes() {
        return movieDuration%60;
    }

    public class AddressIdValueMapper {
        private String addressHtmlId;
        private String addressHtmlValue;

        public AddressIdValueMapper(Integer addressId, String district, String street, Integer streetNumber) {
            this(new Address(addressId, district, street, streetNumber));
        }

        public AddressIdValueMapper(Address address) {
            this.addressHtmlId = String.format("%d_%s_%d",
                    AllMoviesShowtimesDto.this.movieId, DateUtils.formatAsyyyyMMdd(AllMoviesShowtimesDto.this.date), address.getId());
            this.addressHtmlValue = address.getFullAddress();
            // add to outer class's collection
            AllMoviesShowtimesDto.this.addressIdValueMapperList.add(this);
        }

        public String getAddressHtmlId() {
            return addressHtmlId;
        }

        public String getAddressHtmlValue() {
            return addressHtmlValue;
        }
    }

    public class ScreeningIdKeyValueMapper {
        private String screeningHtmlId;
        private Long screeningHtmlKey;
        private String screeningHtmlValue;

        public ScreeningIdKeyValueMapper(String addressHtmlId, Long screeningId, Timestamp timestamp) {
            this.screeningHtmlId = String.format("%s_%d", addressHtmlId, screeningId);
            this.screeningHtmlKey = screeningId;
            this.screeningHtmlValue = DateUtils.formatAsHHmm(timestamp);
            // add to outer class's collection
            AllMoviesShowtimesDto.this.addScreeningIdKeyValueMapper(this);
        }

        public String getScreeningHtmlId() {
            return screeningHtmlId;
        }

        public Long getScreeningHtmlKey() {
            return screeningHtmlKey;
        }

        public String getScreeningHtmlValue() {
            return screeningHtmlValue;
        }
    }
}
