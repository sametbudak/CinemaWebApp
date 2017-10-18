package org.kiev.cinema.dto.clerks;

import org.kiev.cinema.date.DateUtils;
import org.kiev.cinema.entity.Address;
import org.kiev.cinema.entity.Screening;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class AddressShowtimeSelect {
    private Integer addressId;
    private String addressStr;
    List<ScreeningDateStr> screeningDateList = new ArrayList<>();
    List<ScreeningTime> screeningTimeList = new ArrayList<>();

    public AddressShowtimeSelect(Address address) {
        this.addressId = address.getId();
        this.addressStr = address.getFullAddress();
    }

    public AddressShowtimeSelect(Address address, List<Screening> screenings) {
        this.addressId = address.getId();
        this.addressStr = address.getFullAddress();
        for(Screening screening : screenings) {
            addScreening(screening);
        }
    }

    public Integer getAddressId() {
        return addressId;
    }

    public String getAddressStr() {
        return addressStr;
    }

    public List<ScreeningDateStr> getScreeningDateList() {
        return screeningDateList;
    }

    public List<ScreeningTime> getScreeningTimeList() {
        return screeningTimeList;
    }

    public void addScreening(Screening screening) {
        addScreeningDate(screening);
        addScreeningTime(screening);
    }

    private void addScreeningDate(Screening screening) {
        String dateStr = DateUtils.formatAsEEEMMMd(screening.getStartTime());
        for(ScreeningDateStr screeningDate : screeningDateList) {
            if(screeningDate.dateStr.equals(dateStr)) {
                return;
            }
        }
        screeningDateList.add(new ScreeningDateStr(dateStr));
    }

    private void addScreeningTime(Screening screening) {
        for(ScreeningTime screeningTime : screeningTimeList) {
            if(screeningTime.getScreeningId().equals(screening.getId())) {
                return;
            }
        }
        screeningTimeList.add(new ScreeningTime(screening));
    }

    // inner class
    public class ScreeningDateStr {
        private String dateStr;

        public ScreeningDateStr(String dateStr) {
            this.dateStr = dateStr;
        }

        public String getDateStr() {
            return dateStr;
        }
    }

    // inner class
    public class ScreeningTime {
        private Long screeningId;
        private Timestamp timestamp;

        public ScreeningTime(Screening screening) {
            this.screeningId = screening.getId();
            this.timestamp = screening.getStartTime();
        }

        public Long getScreeningId() {
            return screeningId;
        }

        public Timestamp getTimestamp() {
            return timestamp;
        }

        public String getTimeAsHHmm() {
            return DateUtils.formatAsHHmm(timestamp);
        }

        public String getDateAsyyyyMMdd() {
            return DateUtils.formatAsEEEMMMd(this.timestamp);
        }
    }
}
