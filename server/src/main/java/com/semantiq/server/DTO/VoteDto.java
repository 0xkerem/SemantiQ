package com.semantiq.server.DTO;

public class VoteDto {
    private int countPos;
    private int countNeg;

    public int getCountPos() {
        return countPos;
    }

    public void setCountPos(int countPos) {
        this.countPos = countPos;
    }

    public int getCountNeg() {
        return countNeg;
    }

    public void setCountNeg(int countNeg) {
        this.countNeg = countNeg;
    }
}
