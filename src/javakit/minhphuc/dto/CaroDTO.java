package javakit.minhphuc.dto;

import java.util.Timer;

public class CaroDTO extends BaseDTO<CaroDTO>{
    
    private int TURN_TIME_LIMIT;
    private int MATCH_TIME_LIMIT;
    private int timeLimit;
    private int currentTick;
    private int tickInterval;
    private Timer timer;
    private PlayerDTO LastMovePlayer;


    public PlayerDTO getLastMovePlayer() {
        return LastMovePlayer;
    }

    public void setLastMovePlayer(PlayerDTO lastMovePlayer) {
        LastMovePlayer = lastMovePlayer;
    }

    public int getTimeLimit() {
        return timeLimit;
    }

    public void setTimeLimit(int timeLimit) {
        this.timeLimit = timeLimit;
    }

    public int getCurrentTick() {
        return currentTick;
    }

    public void setCurrentTick(int currentTick) {
        this.currentTick = currentTick;
    }

    public int getTickInterval() {
        return tickInterval;
    }

    public void setTickInterval(int tickInterval) {
        this.tickInterval = tickInterval;
    }

    public Timer getTimer() {
        return timer;
    }

    public void setTimer(Timer timer) {
        this.timer = timer;
    }

    public int getTURN_TIME_LIMIT() {
        return TURN_TIME_LIMIT;
    }

    public void setTURN_TIME_LIMIT(int TURN_TIME_LIMIT) {
        this.TURN_TIME_LIMIT = TURN_TIME_LIMIT;
    }

    public int getMATCH_TIME_LIMIT() {
        return MATCH_TIME_LIMIT;
    }

    public void setMATCH_TIME_LIMIT(int MATCH_TIME_LIMIT) {
        this.MATCH_TIME_LIMIT = MATCH_TIME_LIMIT;
    }
}
