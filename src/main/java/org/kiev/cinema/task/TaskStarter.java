package org.kiev.cinema.task;

import org.kiev.cinema.DataContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TaskStarter {

    @Autowired
    private DataContainer dataContainer;

    public void init() {
        DailyDataUploader task = new DailyDataUploader(dataContainer);
        task.execute();
        task.scheduleNextTask();
    }
}
