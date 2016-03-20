package com.sprungsolutions.sitorstart;

/**
 * Created by arisprung on 3/6/16.
 */
public class Player {

    private String bats;
    private String birth_year;
    private String mlb_id;
    private String mlb_name;
    private String mlb_pos;
    private String mlb_team;
    private String mlb_team_long;
    private String arm_throws;
    private int score;
    private boolean selected;


    public Player() {

    }


    public String getBats() {
        return bats;
    }

    public void setBats(String bats) {
        this.bats = bats;
    }

    public String getBirth_year() {
        return birth_year;
    }

    public void setBirth_year(String birth_year) {
        this.birth_year = birth_year;
    }

    public String getMlb_id() {
        return mlb_id;
    }

    public void setMlb_id(String mlb_id) {
        this.mlb_id = mlb_id;
    }

    public String getMlb_name() {
        return mlb_name;
    }

    public void setMlb_name(String mlb_name) {
        this.mlb_name = mlb_name;
    }

    public String getMlb_pos() {
        return mlb_pos;
    }

    public void setMlb_pos(String mlb_pos) {
        this.mlb_pos = mlb_pos;
    }

    public String getMlb_team() {
        return mlb_team;
    }

    public void setMlb_team(String mlb_team) {
        this.mlb_team = mlb_team;
    }

    public String getMlb_team_long() {
        return mlb_team_long;
    }

    public void setMlb_team_long(String mlb_team_long) {
        this.mlb_team_long = mlb_team_long;
    }

    public String getArm_throws() {
        return arm_throws;
    }

    public void setArm_throws(String arm_throws) {
        this.arm_throws = arm_throws;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    @Override
    public String toString() {
        return "Player{" +
                "bats='" + bats + '\'' +
                ", birth_year='" + birth_year + '\'' +
                ", mlb_id='" + mlb_id + '\'' +
                ", mlb_name='" + mlb_name + '\'' +
                ", mlb_pos='" + mlb_pos + '\'' +
                ", mlb_team='" + mlb_team + '\'' +
                ", mlb_team_long='" + mlb_team_long + '\'' +
                ", arm_throws='" + arm_throws + '\'' +
                ", score=" + score +
                ", selected=" + selected +
                '}';
    }
}
