package javakit.minhphuc.dto;


import java.time.LocalDateTime;

public class GameMatchDTO extends BaseDTO<GameMatchDTO>{

    private Long playerID1;
    private Long playerID2;
    private Integer winnerID;
    private Integer playTime;
    private Integer totalMove;
    private LocalDateTime startedTime;
    private String chat = "";


    public GameMatchDTO(){

    }

    public GameMatchDTO(Long playerID1, Long playerID2, Integer winnerID, Integer playTime, Integer totalMove, LocalDateTime startedTime){
        this.playerID1 = playerID1;
        this.playerID2 = playerID2;
        this.winnerID = winnerID;
        this.playTime = playTime;
        this.totalMove = totalMove;
        this.startedTime = startedTime;
    }


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

    public LocalDateTime getStartedTime() {
        return startedTime;
    }

    public void setStartedTime(LocalDateTime startedTime) {
        this.startedTime = startedTime;
    }

    public String getChat() {
        return chat;
    }

    public void setChat(String chat) {
        this.chat = chat;
    }


}
