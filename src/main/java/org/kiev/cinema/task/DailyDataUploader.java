package org.kiev.cinema.task;

import org.kiev.cinema.DataContainer;
import org.kiev.cinema.date.DateUtils;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class DailyDataUploader extends TimerTask {
    private static Timer timer = new Timer();
    private DataContainer dataContainer;

    protected DailyDataUploader(DataContainer dataContainer) {
        this.dataContainer = dataContainer;
    }

    @Override
    public void run() {
        execute();
        scheduleNextTask();
    }

    protected void execute() {
        dataContainer.uploadMoviesData();
        dataContainer.uploadAddressesData();
    }

    protected void scheduleNextTask() {
        Date time = DateUtils.getCalendarForNextDayWithZeroTime().getTime();
        DailyDataUploader task = new DailyDataUploader(this.dataContainer);
        timer.schedule(task, time);
    }
}
