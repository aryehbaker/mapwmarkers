package com.sample.aryeh.map.MyServer.Restaurant;

/**
 * Created by aryeh on 7/14/2017.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Grade {

    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("grade")
    @Expose
    private String grade;
    @SerializedName("score")
    @Expose
    private Integer score;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

}