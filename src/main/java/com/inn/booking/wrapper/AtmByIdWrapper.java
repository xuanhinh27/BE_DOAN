package com.inn.booking.wrapper;

public class AtmByIdWrapper {
    Integer id;
    String date;
    String time;

    String status;



    Integer userId;
    String userName;
    public AtmByIdWrapper(Integer id, String date, String time, String status, Integer userId, String userName) {
        this.id = id;
        this.date = date;
        this.time = time;
        this.status = status;
        this.userId = userId;
        this.userName = userName;
    }
    public AtmByIdWrapper(  String date, String time, String status, Integer userId, String userName) {

        this.date = date;
        this.time = time;
        this.status = status;
        this.userId = userId;
        this.userName = userName;
    }
}
