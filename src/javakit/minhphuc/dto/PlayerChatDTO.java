package javakit.minhphuc.dto;

import javakit.minhphuc.util.TimeUtils;

import java.util.Date;

public class PlayerChatDTO extends BaseDTO<PlayerChatDTO> {

    private Date time;
    private String name;
    private String content;

    public PlayerChatDTO(){

    }

    public PlayerChatDTO(Date time, String name, String content){
        this.time = time;
        this.name = name;
        this.content = content;
    }

    @Override
    public String toString(){
        return "[" + TimeUtils.formatTime().format(time) + "][" + name + "]: " + content;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
