package javakit.minhphuc.dto;

import java.util.*;

public class BaseDTO<T> {
    private Long id;
    private String type;
    private String inType;
    private String message = "";
    private Boolean onl = false;
    private String result;
    private String title;
    private List<T> lists = new ArrayList();



    public Boolean getOnl() {
        return onl;
    }

    public void setOnl(Boolean onl) {
        this.onl = onl;
    }

    public String getInType() {
        return inType;
    }

    public void setInType(String inType) {
        this.inType = inType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String message) {
        this.title = message;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void  setMessage(String message) {
        this.message = message;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<T> getLists() {
        return lists;
    }

    public void setLists(List<T> lists) {
        this.lists = lists;
    }
}
