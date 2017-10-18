package org.kiev.cinema;

import org.kiev.cinema.dummy.DemoDataLoader;
import org.kiev.cinema.task.TaskStarter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Initializer implements CommandLineRunner {
    @Autowired
    private DemoDataLoader dataLoader;
    @Autowired
    private TaskStarter taskStarter;
    @Override
    public void run(String... strings) throws Exception {
        dataLoader.load();
        taskStarter.init();
    }
}
