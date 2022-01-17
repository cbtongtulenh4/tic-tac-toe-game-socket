package javakit.minhphuc.model;

public class Achievements extends AbstractModel{

    private Integer match;
    private Integer win;
    private Integer lose;
    private Integer streak;
    private Float winRate;
    private Integer draw;
    private Double score;


    public Achievements(){
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
}
