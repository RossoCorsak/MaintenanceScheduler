package listener;

import java.util.Date;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class CTaskListener implements ServletContextListener {
    private java.util.Timer timer = null;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        timer = new java.util.Timer(true);
        sce.getServletContext().log("initializing system core task...");
        timer.schedule(new OrderScheduleTask(sce.getServletContext()), new Date(), 60*1000);//30*60*1000
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        sce.getServletContext().log("system core auto task ended.");
        timer.cancel();
    }

}

