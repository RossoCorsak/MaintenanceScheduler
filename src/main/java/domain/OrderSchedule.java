package domain;

import java.util.Date;

public class OrderSchedule {
    private Long oid;//订单号
    private Date time;//创建时间
    private Date latest_access_time;//最近访问时间
    private Double coordinate_x;
    private Double coordinate_y;
    private Integer mid;//维修人员id
    private String status;//订单状态

    public OrderSchedule() {
    }

    public Long getOid() {
        return oid;
    }
    public void setOid(Long oid) {
        this.oid = oid;
    }
    public Date getTime() {
        return time;
    }
    public void setTime(Date time) {
        this.time = time;
    }
    public Date getLatest_access_time(){
        return latest_access_time;
    }
    public void setLatest_access_time(Date latest_access_time){
        this.latest_access_time=latest_access_time;
    }
    public Double getCoordinate_x(){
        return coordinate_x;
    }
    public void setCoordinate_x(Double coordinate_x){
        this.coordinate_x=coordinate_x;
    }
    public Double getCoordinate_y(){
        return coordinate_y;
    }
    public void setCoordinate_y(Double coordinate_y){
        this.coordinate_y=coordinate_y;
    }
    public Integer getMid() {
        return mid;
    }
    public void setMid(Integer mid) {
        this.mid = mid;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }




    @Override
    public String toString() {
        return "order_schedule [oid=" + oid + ", time=" + time + ", coordinate_x=" + coordinate_x + ", coordinate_y=" + coordinate_y + ", mid" + mid + ", status" + status + "]";
    }
}
