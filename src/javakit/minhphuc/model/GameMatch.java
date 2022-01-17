package javakit.minhphuc.model;

import java.sql.Timestamp;

public class GameMatch extends AbstractModel{

    private Long playerID1;
    private Long playerID2;
    private Integer winnerID;
    private Integer playTime;
    private Integer totalMove;
    private Timestamp startedTime;
    private String chat = "";



    public Long getPlayerID1() {
        return playerID1;
    }

    public void setPlayerID1(Long playerID1) {
        this.playerID1 = playerID1;
    }

    public Long getPlayerID2() {
        return playerID2;
    }

    public void setPlayerID2(Long playerID2) {
        this.playerID2 = playerID2;
    }

    public Integer getWinnerID() {
        return winnerID;
    }

    public void setWinnerID(Integer winnerID) {
        this.winnerID = winnerID;
    }

    public Integer getPlayTime() {
        return playTime;
    }

    public void setPlayTime(Integer playTime) {
        this.playTime = playTime;
    }

    public Integer getTotalMove() {
        return totalMove;
    }

    public void setTotalMove(Integer totalMove) {
        this.totalMove = totalMove;
    }

    public Timestamp getStartedTime() {
        return startedTime;
    }

    public void setStartedTime(Timestamp startedTime) {
        this.startedTime = startedTime;
    }

    public String getChat() {
        return chat;
    }

    public void setChat(String chat) {
        this.chat = chat;
    }
}
