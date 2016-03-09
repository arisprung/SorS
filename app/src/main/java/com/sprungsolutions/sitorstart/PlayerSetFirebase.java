package com.sprungsolutions.sitorstart;

/**
 * Created by arisprung on 3/8/16.
 */
public class PlayerSetFirebase {

    private int mlb_set_score_2;
    private int mlb_set_score_1;
    private String mlb_name_2;
    private String mlb_name_1;
    private int mlb_id_2;
    private int mlb_id_1;
    private String set_id;
    private boolean player1selected;
    private boolean player2selected;


    public PlayerSetFirebase() {
    }

    public int getMlb_set_score_2() {
        return mlb_set_score_2;
    }

    public void setMlb_set_score_2(int mlb_set_score_2) {
        this.mlb_set_score_2 = mlb_set_score_2;
    }

    public int getMlb_set_score_1() {
        return mlb_set_score_1;
    }

    public void setMlb_set_score_1(int mlb_set_score_1) {
        this.mlb_set_score_1 = mlb_set_score_1;
    }

    public String getMlb_name_2() {
        return mlb_name_2;
    }

    public void setMlb_name_2(String mlb_name_2) {
        this.mlb_name_2 = mlb_name_2;
    }

    public String getMlb_name_1() {
        return mlb_name_1;
    }

    public void setMlb_name_1(String mlb_name_1) {
        this.mlb_name_1 = mlb_name_1;
    }

    public int getMlb_id_2() {
        return mlb_id_2;
    }

    public void setMlb_id_2(int mlb_id_2) {
        this.mlb_id_2 = mlb_id_2;
    }

    public int getMlb_id_1() {
        return mlb_id_1;
    }

    public void setMlb_id_1(int mlb_id_1) {
        this.mlb_id_1 = mlb_id_1;
    }

    public String getSet_id() {
        return set_id;
    }

    public void setSet_id(String set_id) {
        this.set_id = set_id;
    }

    public boolean isPlayer1selected() {
        return player1selected;
    }

    public void setPlayer1selected(boolean player1selected) {
        this.player1selected = player1selected;
    }

    public boolean isPlayer2selected() {
        return player2selected;
    }

    public void setPlayer2selected(boolean player2selected) {
        this.player2selected = player2selected;
    }
}
