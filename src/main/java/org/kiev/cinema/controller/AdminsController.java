package org.kiev.cinema.controller;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.name.Rename;
import org.kiev.cinema.CinemaConstants;
import org.kiev.cinema.date.DateUtils;
import org.kiev.cinema.date.WeekOrdinal;
import org.kiev.cinema.dto.admins.*;
import org.kiev.cinema.entity.Genre;
import org.kiev.cinema.entity.Movie;
import org.kiev.cinema.enums.FileType;
import org.kiev.cinema.enums.GenreEnum;
import org.kiev.cinema.enums.MovieStatus;
import org.kiev.cinema.service.MovieService;
import org.kiev.cinema.service.ScreeningService;
import org.kiev.cinema.service.controller_specific.AdminsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.sql.Timestamp;
import java.util.*;

@Controller
@RequestMapping("/admin")
public class AdminsController {

    @Autowired
    private AdminsService adminsService;
    @Autowired
    private MovieService movieService;
    @Autowired
    private ScreeningService screeningService;

    @ModelAttribute("login")
    private String addLoginToRequestScope() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String login = user.getUsername();
        return login;
    }

    @ExceptionHandler(Exception.class)
    public String handleError(Model model, HttpServletRequest req, Exception ex) {
        ex.printStackTrace();
        return "staff/admin/admin_error";
    }

    @RequestMapping("/403")
    public String accessDeniedPage(){
        return "403";
    }

    @RequestMapping("/home")
    public String home(ModelMap model) {
        return "staff/admin/admin_home";
    }

    @RequestMapping("/get_add_movie_page")
    public String getAddMoviePage(ModelMap model,
                                  @ModelAttribute("message") String message) {
        Set<MovieStatus> statusEnumSet = EnumSet.allOf(MovieStatus.class);
        Set<GenreEnum> genreEnumSet = EnumSet.allOf(GenreEnum.class);
        model.addAttribute("statusList", statusEnumSet);
        model.addAttribute("genreList", genreEnumSet);
        return "staff/admin/admin_movie_add";
    }

    @RequestMapping("/list_movies/{page}")
    public String listMovies(ModelMap model,
                                   @ModelAttribute("message") String message,
                                   @PathVariable("page") Integer page) {
        Pageable pageable = new PageRequest(page, CinemaConstants.MOVIE_RESULTS_ON_PAGE, new Sort(Sort.Direction.DESC, "id"));
        Long moviesCount = movieService.countAll();
        List<ListableMovieDto> listableMovieDtoList = adminsService.getListableMovieDtoList(pageable);
        long pagesCount = moviesCount/CinemaConstants.MOVIE_RESULTS_ON_PAGE + (moviesCount%CinemaConstants.MOVIE_RESULTS_ON_PAGE > 0 ? 1 : 0 );
        long fromPage = (page-5) >= 0 ? page-5 : 0;
        long tillPage = (page+5) <= pagesCount ? page+5 : (pagesCount==0 ? 0 : pagesCount-1);
        model.addAttribute("fromPage", fromPage);
        model.addAttribute("tillPage", tillPage);
        model.addAttribute("activePage", page);
        model.addAttribute("listableMovieInfoList", listableMovieDtoList);
        return "staff/admin/admin_movie_list";
    }

    @RequestMapping("/delete_movie/page{page}/movie{id}")
    public String deleteMovie(RedirectAttributes redirectAttributes,
                              @PathVariable("page") Integer page,
                              @PathVariable("id") Long movieId) {
        boolean deleted =  adminsService.deleteMovie(movieId);
        if(deleted) {
            redirectAttributes.addFlashAttribute("message", "Movie has been deleted successfully!");
        } else {
            redirectAttributes.addFlashAttribute("message", "Error deleting movie!");
        }
        return "redirect:/admin/list_movies/" + page;
    }

    @RequestMapping("/get_edit_movie_page/{id}")
    public String getEditMoviePage(ModelMap model,
                                   @ModelAttribute("message") String message,
                                   @PathVariable("id") Long movieId) {
        EditableMovieDto editableMovieDto = adminsService.getEditableMovieDto(movieId);
        Set<MovieStatus> statusEnumSet = EnumSet.allOf(MovieStatus.class);
        model.addAttribute("statusList", statusEnumSet);
        model.addAttribute("editableMovieDto", editableMovieDto);
        return "staff/admin/admin_movie_edit";
    }

    @RequestMapping("/edit_movie")
    public String editMovie(ModelMap model,
                            RedirectAttributes redirectAttributes,
                            @ModelAttribute("edited_movie") Movie editedMovie,
                            @RequestParam("genres_param") GenreEnum[] movieGenreEnumList,
                            @RequestParam(value = "poster_param", required = false) MultipartFile poster,
                            @RequestParam(value = "trailer_param", required = false) MultipartFile trailer) {
        List<Genre> genreList = new ArrayList<>();
        for(GenreEnum genreEnum : movieGenreEnumList) {
            genreList.add(new Genre(genreEnum));
        }
        editedMovie.setGenres(genreList);
        try {
            if(poster != null && !poster.isEmpty()) {
                String posterPath = saveFile(poster, editedMovie.getTitle(), FileType.IMG);
                editedMovie.setPoster(posterPath);
            }
            if(trailer != null && !trailer.isEmpty()) {
                String trailerPath = saveFile(trailer, editedMovie.getTitle(), FileType.VIDEO);
                editedMovie.setTrailer(trailerPath);
            }
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("message", "Error has occurred while updating the movie!"); // TODO
            return "redirect:/admin/get_edit_movie_page/" + editedMovie.getId();
        }
        EditableMovieDto editableMovieDto = adminsService.updateMovie(editedMovie);
        Movie movie = editableMovieDto.getMovie();
        model.addAttribute("movie", movie);
        Set<MovieStatus> statusEnumSet = EnumSet.allOf(MovieStatus.class);
        model.addAttribute("statusList", statusEnumSet);
        model.addAttribute("message", "Movie has been updated successfully!");
        return "staff/admin/admin_edit-add_success";
    }

    @RequestMapping("/add_movie")
    public String addMovie(ModelMap model,
                           RedirectAttributes redirectAttributes,
                           @ModelAttribute("new_movie") Movie newMovie,
                           @RequestParam("genres_param") GenreEnum[] movieGenreEnumList,
                           @RequestParam("poster_param") MultipartFile poster,
                           @RequestParam("trailer_param") MultipartFile trailer){
        List<Genre> genreList = new ArrayList<>();
        for(GenreEnum genreEnum : movieGenreEnumList) {
            genreList.add(new Genre(genreEnum));
        }
        newMovie.setGenres(genreList);
        try {
            String posterPath = saveFile(poster, newMovie.getTitle(), FileType.IMG);
            newMovie.setPoster(posterPath);
            String trailerPath = saveFile(trailer, newMovie.getTitle(), FileType.VIDEO);
            newMovie.setTrailer(trailerPath);
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("message", "Error has occurred while adding movie!");
            return "redirect:/admin/get_add_movie_page";
        }
        movieService.add(newMovie);
        model.addAttribute("movie", newMovie);
        model.addAttribute("message", "Movie has been added successfully!");
        return "staff/admin/admin_edit-add_success";
    }

    private String saveFile(MultipartFile file, String movieTitle, FileType fileType) throws Exception {
        String mime = file.getContentType().toLowerCase();
        if(fileType.isMimeTypeAllowed(mime) == false) {
            throw new Exception("Uploading " +  fileType.name() + ": mime type " + mime + " is not allowed");
        }
        String originalFilename = file.getOriginalFilename();
        int ind = originalFilename.lastIndexOf("\\");
        String fileName = originalFilename.substring(ind+1);
        String folderPath = CinemaConstants.MOVIES_PATH + File.separator +
                movieTitle.replaceAll("[^0-9a-zA-Z_\\s-]", "").replaceAll("\\s", "-").toLowerCase();

        File path = new File(folderPath);
        if( !path.exists()) {
            path.mkdir();
        }
        String newFilePath = folderPath + File.separator + fileName;
        File newFile = new File(newFilePath);
        file.transferTo(newFile);
        if(fileType == FileType.IMG) {
            Thumbnails.of(newFile)
                    .forceSize(670, 1000)
                    .outputFormat("jpg")
                    .toFiles(Rename.NO_CHANGE);
        }
        return newFile.toString();
    }

    @RequestMapping("/get_scheduler/{week}")
    public String getScheduler(ModelMap model,
                               @ModelAttribute("message") String message,
                               @PathVariable("week") Integer weekIndex) {
        WeekOrdinal week = WeekOrdinal.values()[weekIndex];
        model.addAttribute("week", weekIndex);
        if(week == WeekOrdinal.CURRENT) {
            model.addAttribute("disabled", "disabled");
        }

        SchedulingDto schedulingDto = adminsService.getSchedulingDto(week);
        model.addAttribute("schedulingInfo", schedulingDto);

        List<ActiveMovieDto> activeMovieDtoList = adminsService.getActiveMovieDtoList();
        model.addAttribute("activeMovieInfoList", activeMovieDtoList);

        return "staff/admin/admin_screening_scheduler";
    }

    @RequestMapping("/add_screenings")
    public String addScrinings(RedirectAttributes redirectAttributes,
                                     @RequestParam("week") Integer weekIndex,
                                     WebRequest webRequest) {
        List<CreateScreeningDto> createScreeningDtoList = getCreateScreeningDtoList(webRequest);
        if(createScreeningDtoList.isEmpty()) {
            redirectAttributes.addFlashAttribute("message", "Nothing has been added!");
        } else if(WeekOrdinal.CURRENT.getIndex().equals(weekIndex)) {
            redirectAttributes.addFlashAttribute("message", "Current week can not be updated!");
        } else {
            int count = adminsService.addScreenings(createScreeningDtoList);
            if(count == 0) {
                redirectAttributes.addFlashAttribute("message", "Nothing has been added!");
            } else if(count == createScreeningDtoList.size()) {
                redirectAttributes.addFlashAttribute("message", count + " screening(s) have been added!");
            } else {
                int failed = createScreeningDtoList.size() - count;
                redirectAttributes.addFlashAttribute("message", count + " screening(s) have been added! " + failed + "(s) have failed to be added!");
            }
        }
        return "redirect:/admin/get_scheduler/" + weekIndex;
    }

    private List<CreateScreeningDto> getCreateScreeningDtoList(WebRequest webRequest) {
        List<CreateScreeningDto> createScreeningDtoList = new ArrayList<>();
        Timestamp current = new Timestamp(DateUtils.getTodayCalendarAtTheEndOfTheDay().getTimeInMillis());
        try {
            Integer count = Integer.parseInt(webRequest.getParameter("count"));
            for(int i = 0; i < count; i++) {
                String movieDateRoomTime = webRequest.getParameter("movie_date_time_room_"+i);
                if(movieDateRoomTime == null || movieDateRoomTime.length()==0) {
                    continue;
                }
                Double price = Double.parseDouble(webRequest.getParameter("price_"+i));
                Long movieId = null;
                Timestamp timestamp = null;
                Integer roomId = null;
                String[] arr = movieDateRoomTime.split(";");
                for(String str : arr) {
                    if (str.contains("movie=")) {
                        movieId = Long.parseLong(str.replace("movie=", ""));
                    } else if (str.contains("date_time=")) {
                        timestamp = DateUtils.parseTimestampFromyyyyMMddHHmm( str.replace("date_time=","") );
                    } else if (str.contains("room=")) {
                        roomId = Integer.parseInt(str.replace("room=", ""));
                    }
                }
                if(timestamp.before(current)) {
                    throw new RuntimeException("Error : trying to add date before current date");
                }
                CreateScreeningDto screeningDto = new CreateScreeningDto(roomId, timestamp, movieId, price);
                createScreeningDtoList.add(screeningDto);
            }
        } catch (Exception e) {
            e.printStackTrace();
            createScreeningDtoList = new ArrayList<>(); // empty list
        }
        return createScreeningDtoList;
    }

    @RequestMapping("/list_screenings/{page}")
    public String listScreenings(ModelMap model,
                                 @ModelAttribute("message") String message,
                                 @PathVariable("page") Integer page) {
        final int RESULTS_PER_PAGE = CinemaConstants.SCREENING_RESULTS_ON_PAGE;
        Pageable pageable = new PageRequest(page, RESULTS_PER_PAGE, new Sort(Sort.Direction.DESC, "id"));
        Long screeningCount = screeningService.countAll();
        List<ListableScreeningDto> listableScreeningDtoList = adminsService.getListableScreeningDtoList(pageable);
        Long pagesCount = screeningCount/RESULTS_PER_PAGE + (screeningCount%RESULTS_PER_PAGE > 0 ? 1 : 0 );
        long fromPage = (page-5) >= 0 ? page-5 : 0;
        long tillPage = (page+5) <= pagesCount ? page+5 : (pagesCount==0 ? 0 : pagesCount-1);
        model.addAttribute("fromPage", fromPage);
        model.addAttribute("tillPage", tillPage);
        model.addAttribute("activePage", page);
        model.addAttribute("listableScreeningInfoList", listableScreeningDtoList);
        return "staff/admin/admin_screening_list";
    }

    @RequestMapping("delete_screening/page{page}/screening{id}")
    public String deleteScreening(RedirectAttributes redirectAttributes,
                              @PathVariable("page") Integer page,
                              @PathVariable("id") Long screeningId) {
        boolean deleted =  adminsService.deleteScreening(screeningId);
        if(deleted) {
            redirectAttributes.addFlashAttribute("message", "Screening has been deleted successfully!");
        } else {
            redirectAttributes.addFlashAttribute("message", "Error deleting screening!");
        }
        return "redirect:/admin/list_screenings/" + page;
    }

}
