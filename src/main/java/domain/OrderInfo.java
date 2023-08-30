package domain;

import java.util.Date;

public class OrderInfo {
    private Long oid;//订单号
    private Date time;//创建时间
    private Double coordinate_x;
    private Double coordinate_y;
    private Integer uid;//integer会不会太小
    private String uname;//用户姓名
    private String uphone;//用户电话
    private String uaddress;//用户详细地址
    private String content;//维修内容
    private Integer mid;//维修人员id
    private String mname;//维修人员姓名
    private String mphone;//维修人员电话
    private String status;//订单状态

    public OrderInfo() {
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

    public Integer getUid() {
        return uid;
    }
    public void setUid(Integer uid) {
        this.uid = uid;
    }
    public String getUname() {
        return uname;
    }
    public void setUname(String uname) {
        this.uname = uname;
    }
    public String getUphone() {
        return uphone;
    }
    public void setUphone(String uphone) {
        this.uphone = uphone;
    }
    public String getUaddress() {
        return uaddress;
    }
    public void setUaddress(String uaddress) {
        this.uaddress = uaddress;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    //维修人员信息是否分离还未定

    public Integer getMid() {
        return mid;
    }
    public void setMid(Integer mid) {
        this.mid = mid;
    }
    public String getMname() {
        return mname;
    }
    public void setMname(String mname) {
        this.mname = mname;
    }
    public String getMphone() {
        return mphone;
    }
    public void setMphone(String mphone) {
        this.mphone = mphone;
    }
    public String getStatus(){
        return status;
    }
    public void setStatus(String status){
        this.status=status;
    }



    @Override
    public String toString() {
        return "order_info [oid=" + oid + ", time=" + time + ", coordinate_x=" + coordinate_x + ", coordinate_y" + coordinate_y + ", uid" + uid + ", uname=" + uname + ", uphone=" + uphone + ", uaddress=" + uaddress + ", content=" + content
                +  ", mid=" + mid + ", mname=" + mname + ", mphone=" + mphone + ", status=" + status + "]";
    }
}
