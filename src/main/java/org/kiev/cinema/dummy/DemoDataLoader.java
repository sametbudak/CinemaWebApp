package org.kiev.cinema.dummy;

import org.json.simple.parser.ParseException;
import org.kiev.cinema.CinemaConstants;
import org.kiev.cinema.entity.*;
import org.kiev.cinema.entity.user.Admin;
import org.kiev.cinema.entity.user.Clerk;
import org.kiev.cinema.enums.GenreEnum;
import org.kiev.cinema.enums.TicketStatus;
import org.kiev.cinema.service.*;
import org.kiev.cinema.service.AddressService;
import org.kiev.cinema.service.security.AdminService;
import org.kiev.cinema.service.security.ClerkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Component
public class DemoDataLoader {

    @Autowired
    private AdminService adminService;
    @Autowired
    private ClerkService clerkService;
    @Autowired
    private AddressService addressService;
    @Autowired
    private RoomService roomService;
    @Autowired
    private SeatService seatService;
    @Autowired
    private GenreService genreService;
    @Autowired
    private MovieService movieService;
    @Autowired
    private ScreeningService screeningService;
    @Autowired
    private TicketService ticketService;

    public void load() {
        insertAdminTestData();
        insertClerkTestData();
        insertAddressesTestData();
        insertRoomsTestData();
        insertSeatsTestData();
        insertGenresTestData();
        insertMoviesTestData();
        insertScreeningsTestData();
        insertTicketsTestData();
    }

    public void insertAdminTestData() {
        Admin admin = new Admin();
        admin.setLogin("admin3");
        admin.setPassword("40bd001563085fc35165329ea1ff5c5ecbdbbeef"); // 123
        adminService.addAdmin(admin);
    }

    public void insertClerkTestData() {
        Clerk clerk = new Clerk();
        clerk.setLogin("clerk3");
        clerk.setPassword("40bd001563085fc35165329ea1ff5c5ecbdbbeef"); // 123
        clerkService.addClerk(clerk);
    }

    public void insertGenresTestData() {
        for(GenreEnum genreEnum : GenreEnum.values()) {
            Genre genre = new Genre();
            genre.setGenreEnum(genreEnum);
            genreService.add(genre);
        }
    }

    public void insertMoviesTestData () {
        Path root = Paths.get(CinemaConstants.MOVIES_PATH);
        List<Path> paths = new LinkedList<>();
        try {
            findJsonFiles(root, paths);
            List<Movie> movies = parseMovieJsons(paths);
            for(Movie movie : movies) {
                movieService.add(movie);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void insertAddressesTestData() {
        Path root = Paths.get(CinemaConstants.ADDRESSES_PATH);
        List<Path> paths = new LinkedList<>();
        try {
            findJsonFiles(root, paths);
            List<Address> addresses = parseAddressesJsons(paths);
            for(Address address : addresses) {
                addressService.add(address);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void insertRoomsTestData() {
        Path root = Paths.get(CinemaConstants.ROOMS_PATH);
        List<Path> paths = new LinkedList<>();
        try {
            findJsonFiles(root, paths);
            List<Room> rooms = parseRoomsJsons(paths);
            for(Room room : rooms) {
                roomService.add(room);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void insertSeatsTestData() {
        List<Seat> seats = new LinkedList<>();
        List<Room> rooms = roomService.listAll();
        for(Room room : rooms) {
            int maxRow = room.getMaxRow();
            int maxColumn = room.getMaxColumn();
            for(int row = 1; row <=maxRow; row++){
                for(int col = 1; col <=maxColumn; col++) {
                    Seat seat = new Seat();
                    seat.setRoom(room);
                    seat.setRowNumber(row);
                    seat.setColumnNumber(col);
                    seats.add(seat);
                }
            }
            seatService.addAll(seats);
        }
    }

    public void insertScreeningsTestData() {
        Path root = Paths.get(CinemaConstants.SCREENINGS_PATH);
        List<Path> paths = new LinkedList<>();
        try {
            findJsonFiles(root, paths);
            List<Screening> screenings = parseScreeningsJsons(paths);
            for(Screening screening : screenings) {
                screeningService.add(screening);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void insertTicketsTestData() {
        List<Screening> screenings = screeningService.listAll();
        for(Screening screening : screenings) {
            List<Seat> seats = seatService.listByScreening(screening.getId());
            List<Ticket> tickets = new LinkedList<>();
            for(Seat seat : seats) {
                Ticket ticket = new Ticket();
                ticket.setSeat(seat);
                ticket.setPrice(10d);
                ticket.setScreening(screening);
                tickets.add(ticket);
            }
            if(tickets.size() > 2) {
                tickets.get(0).setTicketStatus(TicketStatus.BOOKED);
                tickets.get(2).setTicketStatus(TicketStatus.SOLD);
            }
            ticketService.addAll(tickets);
        }
    }

    private void findJsonFiles(Path root, List<Path> paths) throws IOException {
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(root)) {
            for (Path input : stream) {
                if (Files.isDirectory(input)) {
                    findJsonFiles(input, paths);
                } else {
                    String name = input.getFileName().toString();
                    if(name.endsWith(".json")) {
                        paths.add(input);
                    }
                }
            }
        }
    }

    private List<Movie> parseMovieJsons(List<Path> paths) throws NoSuchMethodException, ParseException, IOException, InstantiationException, IllegalAccessException, InvocationTargetException, java.text.ParseException {
        List<Movie> movies = new ArrayList<>();
        for(Path path : paths) {
            movies.add(Movie.fromJSON(path));
        }
        return movies;
    }

    private List<Address> parseAddressesJsons(List<Path> paths) throws FileNotFoundException {
        List<Address> addresses = new ArrayList<>();
        for(Path path : paths) {
            addresses.add(Address.fromJSON(path));
        }
        return addresses;
    }

    private List<Room> parseRoomsJsons(List<Path> paths) throws FileNotFoundException {
        List<Room> rooms = new ArrayList<>();
        for(Path path : paths) {
            rooms.add(Room.fromJSON(path));
        }
        return rooms;
    }

    private List<Screening> parseScreeningsJsons(List<Path> paths) throws FileNotFoundException {
        List<Screening> screenings = new ArrayList<>();
        for(Path path : paths) {
            screenings.add(Screening.fromJSON(path));
        }
        return screenings;
    }
}
