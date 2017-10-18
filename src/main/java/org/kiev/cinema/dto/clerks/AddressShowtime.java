package org.kiev.cinema.dto.clerks;

import org.kiev.cinema.date.DateUtils;
import org.kiev.cinema.entity.Address;
import org.kiev.cinema.entity.Screening;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class AddressShowtime {
    private Integer addressId;
    private String addressStr;
    List<ScreeningDate> screeningDateList = new ArrayList<>();

    public AddressShowtime (Address address) {
        this.addressId = address.getId();
        this.addressStr = address.getAddress();
    }

    public AddressShowtime(Address address, List<Date> dates) {
        this.addressId = address.getId();
        this.addressStr = address.getAddress();
        for(Date date : dates) {
            screeningDateList.add(new ScreeningDate(DateUtils.formatAsEEEMMMd(date)));
        }
    }

    public void addScreenings(List<Screening> screenings) {
        for(Screening screening : screenings) {
            boolean isDateExists = false;
            String dateStr = DateUtils.formatAsEEEMMMd(screening.getStartTime());
            for(ScreeningDate dateShowtime : screeningDateList) {
                if(dateShowtime.dateStr.equals(dateStr)) {
                    dateShowtime.addShowtime(screening);
                    isDateExists = true;
                    break;
                }
            }
            if (isDateExists == false)  {
                screeningDateList.add(new ScreeningDate(screening));
            }
        }
    }

    public void addScreeningsIfDatesExist(List<Screening> screenings) {
        for(Screening screening : screenings) {
            String dateStr = DateUtils.formatAsEEEMMMd(screening.getStartTime());
            for(ScreeningDate dateShowtime : screeningDateList) {
                if(dateShowtime.dateStr.equals(dateStr)) {
                    dateShowtime.addShowtime(screening);
                    return;
                }
            }
        }
    }

    public Integer getAddressId() {
        return addressId;
    }

    public String getAddressStr() {
        return addressStr;
    }

    public List<ScreeningDate> getScreeningDateList() {
        return screeningDateList;
    }

    // inner class
    public class ScreeningDate {
        private String dateStr;
        private List<ScreeningTime> screeningTimeList = new ArrayList<>();

        private ScreeningDate (String dateStr) {
            this.dateStr = dateStr;
        }

        private ScreeningDate (Screening screening) {
            this.dateStr = DateUtils.formatAsEEEMMMd(screening.getStartTime());
            screeningTimeList.add(new ScreeningTime(screening));
        }

        public String getDateStr() {
            return dateStr;
        }

        public List<ScreeningTime> getScreeningTimeList() {
            return screeningTimeList;
        }

        private void addShowtime(Screening screening) {
            if(this.dateStr.equals(DateUtils.formatAsEEEMMMd(screening.getStartTime()))) {
                addScreeningTimeIfNotExist(screening);
            }
        }

        private void addScreeningTimeIfNotExist(Screening screening) {
            for(ScreeningTime showtime : this.screeningTimeList) {
                if(showtime.getTimestamp().equals(screening.getStartTime())) {
                    return;
                }
            }
            this.screeningTimeList.add(new ScreeningTime(screening));
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
        }
    }
}

