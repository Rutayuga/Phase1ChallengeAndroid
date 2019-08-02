package outreachtraders.com.phase1challenge_android;

public class Post {
    private String full_name, phone, email, region, district, division, title, content;

    public Post() {
    }

    public Post(String full_name, String phone, String email, String region, String district, String division, String title, String content) {
        this.full_name = full_name;
        this.phone = phone;
        this.email = email;
        this.region = region;
        this.district = district;
        this.division = division;
        this.title = title;
        this.content = content;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getDivision() {
        return division;
    }

    public void setDivision(String division) {
        this.division = division;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
