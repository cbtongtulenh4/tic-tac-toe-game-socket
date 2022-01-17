package javakit.minhphuc.model;

public class Player extends AbstractModel {

    private String email;
    private String password;
    private String avatar;
    private String name;
    private String gender;
    private Long roleID;
    private Integer yearOfBirth;
    private Integer status;
    private Roles role;

    private Integer match;
    private Integer win;
    private Integer lose;
    private Integer streak;
    private Float winRate;
    private Integer draw;
    private Double score;


    public Player(){
        this.match = 0;
        this.win = 0;
        this.lose = 0;
        this.streak = 0;
        this.winRate = 0F;
        this.draw = 0;
        this.score = 0D;
    }

    public Integer getMatch() {
        return match;
    }

    public void setMatch(Integer match) {
        this.match = match;
    }

    public Integer getWin() {
        return win;
    }

    public void setWin(Integer win) {
        this.win = win;
    }

    public Integer getLose() {
        return lose;
    }

    public void setLose(Integer lose) {
        this.lose = lose;
    }

    public Integer getStreak() {
        return streak;
    }

    public void setStreak(Integer streak) {
        this.streak = streak;
    }

    public Float getWinRate() {
        return winRate;
    }

    public void setWinRate(Float winRate) {
        this.winRate = winRate;
    }

    public Integer getDraw() {
        return draw;
    }

    public void setDraw(Integer draw) {
        this.draw = draw;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public Roles getRole() {
        return role;
    }

    public void setRole(Roles role) {
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getRoleID() {
        return roleID;
    }

    public void setRoleID(Long roleID) {
        this.roleID = roleID;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
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

    public Integer getYearOfBirth() {
        return yearOfBirth;
    }

    public void setYearOfBirth(Integer yearOfBirth) {
        this.yearOfBirth = yearOfBirth;
    }


//    @Override
//    public String toString(){
//        return "email = '" + (this.email.isEmpty() ? "" : this.email) +
//                "'password = '"+ (password.isEmpty() ? "" : this.password) +
//                "'avatar = '"+ (avatar.isEmpty() ? "" : this.avatar) +
//                "'name = '"+ (name.isEmpty() ? "" : this.name) +
//                "'gender = '"+ (gender.isEmpty() ? "" : this.gender) +
//                "'roleid = '"+ roleID +
//                "'yearOfBirth = '"+ yearOfBirth +
//                "'score = '"+ score +"'";
//    }
}
