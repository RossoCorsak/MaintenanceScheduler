package listener;

import service.OrderScheduleService;

import java.util.TimerTask;

import javax.servlet.ServletContext;

public class OrderScheduleTask extends TimerTask{
    private ServletContext sc = null;
    private static boolean running=false;
    private OrderScheduleService order_schedule_service = new OrderScheduleService();

    //constructor method of CCoreTask
    public OrderScheduleTask(ServletContext sc){
        this.sc = sc;
    }

    @Override
    public void run() {
        if (!running){
            running = true;
            this.sc.log("start running CCoreTask...");
            doTask();
            running = false;
            this.sc.log("CCoreTask runned.");
        }
    }

    private void doTask(){
        order_schedule_service.OrderSchedule();
    }
}

