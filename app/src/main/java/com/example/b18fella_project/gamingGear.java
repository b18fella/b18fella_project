package com.example.b18fella_project;

public class gamingGear {
    private String name;
    private String company;
    private int cost;
    private String location;
    private String category;

    public gamingGear() {
        name = "This gaming gear have no name.";
        company = "This gear isn't from any company";
        cost = 0;
        location = "This gear have no location.";
        category = "This gear have no category.";
    }

    public gamingGear(String n, String comp, int cos, String l, String cate) {
        name = n;
        company = comp;
        cost = cos;
        location = l;
        category = cate;
    }

    public String info() {
        String message = new String();
        message += name + " is a/an " + category + " from " + company + " that costs " + cost + "kr.";
        return message;
    }

    public void setName(String n) {
        name = n;
    }

    public  String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }
}
