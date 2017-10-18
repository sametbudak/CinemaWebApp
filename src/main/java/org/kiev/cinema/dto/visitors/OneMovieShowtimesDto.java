package org.kiev.cinema.dto.visitors;

import org.kiev.cinema.date.DateUtils;
import org.kiev.cinema.entity.Address;

import java.sql.*;
import java.util.*;

public class OneMovieShowtimesDto {

    private Long movieId;
    private String movieTitle;
    private Integer movieDuration;
    private List<AddressIdValueMapper> addressIdValueMapperList = new ArrayList<>();
    private List<DateIdValueMapper> dateIdValueMapperList = new ArrayList<>();
    private List<ScreeningIdKeyValueMapper> screeningIdKeyValueMapperList = new ArrayList<>();

    public OneMovieShowtimesDto(Long movieId, String movieTitle, Integer movieDuration) {
        this.movieId = movieId;
        this.movieTitle = movieTitle;
        this.movieDuration = movieDuration;
    }

    public Long getMovieId() {
        return movieId;
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public Integer getMovieDuration() {
        return movieDuration;
    }

    public List<AddressIdValueMapper> getAddressIdValueMapperList() {
        return addressIdValueMapperList;
    }

    public List<DateIdValueMapper> getDateIdValueMapperList() {
        return dateIdValueMapperList;
    }

    public List<ScreeningIdKeyValueMapper> getScreeningIdKeyValueMapperList() {
        return screeningIdKeyValueMapperList;
    }

    public void setAddressesInfo(List<Address> addressList) {
        for(Address address : addressList) {
            addressIdValueMapperList.add(new AddressIdValueMapper(address));
        }
    }

    private void addDateIdValueMapper(Timestamp timestamp) {
        dateIdValueMapperList.add(new DateIdValueMapper(timestamp));
    }

    private void addScreeningIdKeyValueMapper(Integer addressId, Long screeningId, Timestamp timestamp) {
        screeningIdKeyValueMapperList.add(new ScreeningIdKeyValueMapper(addressId, screeningId, timestamp));
    }

    public void setShowtimesInfo(List<Object[]> addressIdAndScreeningIdAndTime) {
        List<String> dateList = new ArrayList<>();
        for(Object[] objArr : addressIdAndScreeningIdAndTime) {
            Integer addressId = (Integer)objArr[0];
            Long screeningId = (Long)objArr[1];
            Timestamp timestamp = (Timestamp) objArr[2];
            String yyyyMMdd = DateUtils.formatAsyyyyMMdd(timestamp);
            if(dateList.contains(yyyyMMdd) == false) {
                dateList.add(yyyyMMdd);
                addDateIdValueMapper(timestamp);
            }
            addScreeningIdKeyValueMapper(addressId, screeningId, timestamp);
        }
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

        public AddressIdValueMapper(Address address) {
            this.addressHtmlId = String.format("a%d", address.getId());
            this.addressHtmlValue = address.getFullAddress();
        }

        public String getAddressHtmlId() {
            return addressHtmlId;
        }

        public String getAddressHtmlValue() {
            return addressHtmlValue;
        }
    }

    public class DateIdValueMapper {
        private String dateHtmlId;
        private String dateHtmlValue;

        public DateIdValueMapper(Timestamp timestamp) {
            this.dateHtmlId = String.format("d%s", DateUtils.formatAsyyyyMMdd(timestamp));
            this.dateHtmlValue = DateUtils.formatAsdMMMyyyyEEEE(timestamp);
        }

        public String getDateHtmlId() {
            return dateHtmlId;
        }

        public String getDateHtmlValue() {
            return dateHtmlValue;
        }
    }

    public class ScreeningIdKeyValueMapper {
        private String screeningHtmlId;
        private Long screeningHtmlKey;
        private String screeningHtmlValue;

        public ScreeningIdKeyValueMapper(Integer addressId, Long screeningId, Timestamp timestamp) {
            this.screeningHtmlId = String.format("a%d_d%s_s%d", addressId,DateUtils.formatAsyyyyMMdd(timestamp), screeningId);
            this.screeningHtmlKey = screeningId;
            this.screeningHtmlValue = DateUtils.formatAsHHmm(timestamp);
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
