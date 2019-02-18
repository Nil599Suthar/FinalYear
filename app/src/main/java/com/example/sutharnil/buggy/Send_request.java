package com.example.sutharnil.buggy;

public class Send_request {

    private String Id;
    private String UId;
    private String pickpoint;
    private String droppoint;
    private String pessanger;
    private String uname;

    public Send_request(String id, String UId,String uname, String pickpoint, String droppoint, String pessanger) {

        this.UId = UId;
        this.uname=uname;
        this.pickpoint = pickpoint;
        this.droppoint = droppoint;
        this.pessanger = pessanger;
    }

    public Send_request() {

    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public void setId(String id) {
        Id = id;
    }

    public void setUId(String UId) {
        this.UId = UId;
    }

    public void setPickpoint(String pickpoint) {
        this.pickpoint = pickpoint;
    }

    public void setDroppoint(String droppoint) {
        this.droppoint = droppoint;
    }

    public void setPessanger(String pessanger) {
        this.pessanger = pessanger;
    }

    public String getId() {
        return Id;
    }

    public String getUId() {
        return UId;
    }

    public String getPickpoint() {
        return pickpoint;
    }

    public String getDroppoint() {
        return droppoint;
    }

    public String getPessanger() {
        return pessanger;
    }
}
