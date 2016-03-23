package com.sprungsolutions.sitorstart.pick_player;

import com.sprungsolutions.sitorstart.pick_player.Player;

/**
 * Created by arisprung on 3/19/16.
 */
public class NewPlayerSet {

    private Player mPlayer1;
    private Player mPlayer2;
    private String id;

    public NewPlayerSet(Player mPlayer1, Player mPlayer2) {
        this.mPlayer1 = mPlayer1;
        this.mPlayer2 = mPlayer2;
    }

    public NewPlayerSet() {
    }

    public Player getmPlayer1() {
        return mPlayer1;
    }

    public void setmPlayer1(Player mPlayer1) {
        this.mPlayer1 = mPlayer1;
    }

    public Player getmPlayer2() {
        return mPlayer2;
    }

    public void setmPlayer2(Player mPlayer2) {
        this.mPlayer2 = mPlayer2;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
