package com.example.thugwtsapp;

class User {

    String imgurl;
    String name;
    String mp3url;
    String rate;
    String chart;
    String movie;
    String actor;
    public User(String imgurl, String name, String mp3url, String rate, String chart, String movie, String actor) {
        this.imgurl = imgurl;
        this.name = name;
        this.mp3url = mp3url;
        this.rate = rate;
        this.chart = chart;
        this.movie = movie;
        this.actor = actor;
    }

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMp3url() {
        return mp3url;
    }

    public void setMp3url(String mp3url) {
        this.mp3url = mp3url;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getChart() {
        return chart;
    }

    public void setChart(String chart) {
        this.chart = chart;
    }

    public String getMovie() {
        return movie;
    }

    public void setMovie(String movie) {
        this.movie = movie;
    }

    public String getActor() {
        return actor;
    }

    public void setActor(String actor) {
        this.actor = actor;
    }
}
