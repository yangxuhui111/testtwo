package com.w.school_herper_front.HomePage.fragment.task;

public class task {
    private int myhead;
    private String name;
    private String endtime;
    private String Tname;
    private String condition;

    public task(){}

    public task(int myhead, String name, String endtime, String tname, String condition) {
        this.myhead = myhead;
        this.name = name;
        this.endtime = endtime;
        Tname = tname;
        this.condition = condition;
    }

    public int getMyhead() {
        return myhead;
    }

    public void setMyhead(int myhead) {
        this.myhead = myhead;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEndtime() {
        return endtime;
    }

    public void setEndtime(String endtime) {
        this.endtime = endtime;
    }

    public String getTname() {
        return Tname;
    }

    public void setTname(String tname) {
        Tname = tname;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }
}
