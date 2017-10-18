package org.kiev.cinema.dto.admins;

import org.kiev.cinema.date.DateUtils;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SchedulingDto {

    private static Integer price = 10; // default
    private Integer positionsCount;
    private List<Date> dateList;
    private List<AddressAndRoomList> addressAndRoomList;
    private List<TimeRange> timeRangeList = new ArrayList<>();
    private Map<String, ScreeningDto> screeningDtoMap = new HashMap<>();

    public SchedulingDto(List<Date> dateList, List<AddressAndRoomList> addressAndRoomList, List<TimeRange> timeRangeList) {
        this.dateList = dateList;
        this.addressAndRoomList = addressAndRoomList;
        this.timeRangeList = timeRangeList;
        this.positionsCount = dateList.size() * addressAndRoomList.size() * timeRangeList.size();
        for(AddressAndRoomList roomList : addressAndRoomList) {
            this.positionsCount *= roomList.getRoomDtoList().size();
        }
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public void setScreeningDtoMap(List<ScreeningDto> screeningDtoList) {
        for(ScreeningDto screeningDto : screeningDtoList) {
            Timestamp timeStart = screeningDto.getStartTime();
            Date date = new Date(timeStart.getTime());
            for(TimeRange timeRange : timeRangeList) {
                if(timeRange.isWithin(timeStart, screeningDto.getMinutes())) {
                    String key = createKey(screeningDto.getRoomId(), date, timeRange);
                    screeningDtoMap.put(key, screeningDto);
                }
            }
        }
    }

    public ScreeningDto getScreening(Integer roomId, Date date, TimeRange timeRange) {
        String key = createKey(roomId, date, timeRange);
        return screeningDtoMap.get(key);
    }

    public String createKey(Integer roomId, Date date, TimeRange timeRange) {
        String dateStr = DateUtils.formatAsyyyyMMdd(date);
        return String.format("%d_%s_%s", roomId, dateStr, timeRange);
    }

    public Integer getPrice() {
        return price;
    }

    public List<Date> getDateList() {
        return dateList;
    }

    public List<AddressAndRoomList> getAddressAndRoomList() {
        return addressAndRoomList;
    }

    public List<TimeRange> getTimeRangeList() {
        return timeRangeList;
    }

    public Integer getPositionsCount() {
        return positionsCount;
    }

    public List<AddressAndRoomList.RoomDto> getRoomsByAddress(Long addressId) {
        for(AddressAndRoomList addressAndRoomList : this.addressAndRoomList) {
            if (addressAndRoomList.getAddressId().equals(addressId)) {
                return addressAndRoomList.getRoomDtoList();
            }
        }
        return null;
    }

}
