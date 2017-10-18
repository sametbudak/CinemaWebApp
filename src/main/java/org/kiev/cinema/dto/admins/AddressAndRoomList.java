package org.kiev.cinema.dto.admins;

import org.kiev.cinema.entity.Address;
import org.kiev.cinema.entity.Room;

import java.util.ArrayList;
import java.util.List;

public class AddressAndRoomList {
    private Integer addressId;
    private String addressStr;

    private List<RoomDto> roomDtoList = new ArrayList<>();

    public AddressAndRoomList(Address address, List<Room> roomList) {
        this.addressId = address.getId();
        this.addressStr = address.getAddress();
        setRoomInfoList(roomList);
    }

    private void setRoomInfoList(List<Room> roomList) {
        for(Room room : roomList) {
            RoomDto roomDto = new RoomDto(room.getId(), room.getTitle(), room.getMaxColumn()*room.getMaxRow());
            this.roomDtoList.add(roomDto);
        }
    }

    public Integer getAddressId() {
        return addressId;
    }

    public String getAddressStr() {
        return addressStr;
    }

    public List<RoomDto> getRoomDtoList() {
        return roomDtoList;
    }

    public class RoomDto {
        private Integer roomId;
        private String roomTitle;
        private Integer numberOfSeats;

        public RoomDto(Integer roomId, String roomTitle, Integer numberOfSeats) {
            this.roomId = roomId;
            this.roomTitle = roomTitle;
            this.numberOfSeats = numberOfSeats;
        }

        public Integer getRoomId() {
            return roomId;
        }

        public String getRoomTitle() {
            return roomTitle;
        }

        public Integer getNumberOfSeats() {
            return numberOfSeats;
        }
    }
}
