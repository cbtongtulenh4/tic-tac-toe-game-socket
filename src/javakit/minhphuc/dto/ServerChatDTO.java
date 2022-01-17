package javakit.minhphuc.dto;

import javakit.minhphuc.util.TimeUtils;

import java.util.Date;

public class ServerChatDTO {
    Date time;
    String title;
    String content;

    public ServerChatDTO(Date time, String title, String content) {
        this.time = time;
        this.title = title;
        this.content = content;
    }

    @Override
    public String toString() {
        return "[" + TimeUtils.formatTime().format(time) + "][" + title + "]: " + content;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
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
