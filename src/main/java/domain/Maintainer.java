package domain;

public class Maintainer {
    private Integer mid;
    private String password;
    private String name;
    private String gender;
    private String phone;
    private Double coordinate_x;
    private Double coordinate_y;
    private String status;

    public Maintainer() {
    }

    public Integer getMid() {
        return mid;
    }
    public void setMid(Integer mid) {
        this.mid = mid;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getGender() {
        return gender;
    }
    public void setGender(String gender) {
        this.gender = gender;
    }
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
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

    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    @Override
    public String toString() {
        return "maintainer [mid=" + mid + ", password=" + password + ", name=" + name + ", gender=" + gender + ", phone=" + phone + ", coordinate_x" + coordinate_x + ", coordinate_y" + coordinate_y + ", status=" + status + "]";
    }
}
