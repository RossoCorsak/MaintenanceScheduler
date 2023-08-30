package domain;

public class User {
    private Integer uid;
    private String password;
    private String name;
    private String gender;
    private String phone;
    private String address;

    public User(){

    }
    public User(String password,String name, String gender, String phone, String address){
        this.password=password;
        this.name = name;
        this.gender = gender;
        this.phone = phone;
        this.address = address;
    }

    public Integer getUid() {
        return uid;
    }
    public void setUid(Integer uid) {
        this.uid = uid;
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
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    @Override
    public String toString() {
        return "user [uid=" + uid + ", password=" + password + ", name=" + name + ", gender=" + gender + ", phone=" + phone + ", address=" + address + "]";
    }
}
