package com.nci.graeme.livescoregaa;

public class Match {
    private String league;
    private String team1;
    private String team2;
    private String score1;
    private String score2;
    private String status;
    private String time;

    public Match(){}

    public Match(String league, String team1, String team2, String score1, String score2, String status, String time) {
        this.league = league;
        this.team1 = team1;
        this.team2 = team2;
        this.score1 = score1;
        this.score2 = score2;
        this.status = status;
        this.time = time;
    }

    public String getLeague() {
        return league;
    }

    public void setLeague(String league) {
        this.league = league;
    }



    public void setTeam1(String team1) {
        this.team1 = team1;
    }

    public void setTeam2(String team2) {
        this.team2 = team2;
    }

    public void setScore1(String score1) {
        this.score1 = score1;
    }

    public void setScore2(String score2) {
        this.score2 = score2;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTeam1() {
        return team1;
    }

    public String getTeam2() {
        return team2;
    }

    public String getScore1() {
        return score1;
    }

    public String getScore2() {
        return score2;
    }

    public String getStatus() {
        return status;
    }

    public String getTime() {
        return time;
    }

}
