package org.kiev.cinema;

import java.io.File;
import java.util.TimeZone;

public class CinemaConstants {

    //private final static Logger LOGGER = LogManager.getLogger(CinemaConstants.class);

    // Timezone
    public static final TimeZone TIME_ZONE = TimeZone.getTimeZone("Europe/Kiev");

    // allowed movie's video formats
    public static final String[] VIDEO_MIME = {"video/mp4"};  // string-elements must be in ascending order

    // allowed movie's posters formats
    public static final String[] IMG_MIME = {"image/jpeg"}; // string-elements must be in ascending order

    public static final int WAITING_FOR_VISITOR_CONFIRMATION_TIME_LIMIT = 4 * 60 * 1000; // 4 minutes
    public static final int WAITING_FOR_BOOKING_REDEEM_TIME_LIMIT = 24 * 60 * 60 * 1000; // 24 hours
    public static final int SELECTED_TICKET_TIME_LIMIT = 10 * 60 * 1000; // 10 minutes

    public static final int PENDING_CONFIRMATIONS_QUANTITY_LIMIT = 50;
    public static final int PENDING_BOOKINGS_QUANTITY_LIMIT = 1000;
    public static final int PENDING_SELECTED_TICKETS_QUANTITY_LIMIT = 150;

    public static final int MOVIE_RESULTS_ON_PAGE = 5;
    public static final int SCREENING_RESULTS_ON_PAGE = 10;

    // the interval within which to retrieve an info for tickets booking/selling
    public static final int DAYS_INTERVAL = 20;

    // contacts
    public static final String EMAIL = "movie7theater@gmail.com";
    public static final String PHONE = "+3800000000";
    public static final String SKYPE = "Movie7Theater";

    // files folders
    public static final String PARENT_PATH = System.getProperty("user.dir") + File.separator + "theater";  // RELATIVE PATH
    public static final String MOVIES_PATH = PARENT_PATH + File.separator + "movies";
    public static final String ADDRESSES_PATH = PARENT_PATH + File.separator + "addresses";
    public static final String ROOMS_PATH = PARENT_PATH + File.separator + "rooms";
    public static final String SCREENINGS_PATH = PARENT_PATH + File.separator + "screenings";
    public static final String TICKETS_PATH = PARENT_PATH + File.separator + "tickets";

    // Windows
    /*public static final String PARENT_PATH_WINDOWS = "C:\\theater";
    public static final String MOVIES_PATH_WINDOWS = PARENT_PATH_WINDOWS + "\\movies";
    public static final String ADDRESSES_PATH_WINDOWS = PARENT_PATH_WINDOWS + "\\addresses";
    public static final String ROOMS_PATH_WINDOWS = PARENT_PATH_WINDOWS + "\\rooms";
    public static final String SCREENINGS_PATH_WINDOWS = PARENT_PATH_WINDOWS + "\\screenings";
    public static final String TICKETS_PATH_WINDOWS = PARENT_PATH_WINDOWS + "\\tickets";

    // Linux
    public static final String PARENT_PATH_LINUX = "/home/theater";
    public static final String MOVIES_PATH_LINUX = PARENT_PATH_LINUX  + "/movies";
    public static final String ADDRESSES_PATH_LINUX = PARENT_PATH_LINUX  + "/addresses";
    public static final String ROOMS_PATH_LINUX = PARENT_PATH_LINUX  + "/rooms";
    public static final String SCREENINGS_PATH_LINUX = PARENT_PATH_LINUX  + "/screenings";
    public static final String TICKETS_PATH_LINUX = PARENT_PATH_LINUX  + "/tickets";

    public static final String MOVIES_PATH;
    public static final String ADDRESSES_PATH;
    public static final String ROOMS_PATH;
    public static final String SCREENINGS_PATH;
    public static final String TICKETS_PATH;

    static {
        String oc = System.getProperty("os.name");
        String path = System.getProperty("user.dir");
        System.out.println("path is " + path);
        System.out.println("OS is " + oc);
        if(oc.toLowerCase().contains("windows")) {
            MOVIES_PATH = MOVIES_PATH_WINDOWS;
            ADDRESSES_PATH = ADDRESSES_PATH_WINDOWS;
            ROOMS_PATH = ROOMS_PATH_WINDOWS;
            SCREENINGS_PATH = SCREENINGS_PATH_WINDOWS;
            TICKETS_PATH = TICKETS_PATH_WINDOWS;
        } else if(oc.toLowerCase().contains("linux")) {
            MOVIES_PATH = MOVIES_PATH_LINUX;
            ADDRESSES_PATH = ADDRESSES_PATH_LINUX;
            ROOMS_PATH = ROOMS_PATH_LINUX;
            SCREENINGS_PATH = SCREENINGS_PATH_LINUX;
            TICKETS_PATH = TICKETS_PATH_LINUX;
        } else {
            throw new Error("unacceptable oc name " + oc);
        }
    }*/
}
