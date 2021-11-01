package com.example.myapplication;

public class Pizza {

    double pizza_size_price;
    double chicken = 0;
    double cheese = 0;
    double corn = 0;

    public Pizza() {

    }

    public double getPizza_size_price() {
        return pizza_size_price;
    }

    public void setPizza_size_price(double pizza_size_price) {
        this.pizza_size_price = pizza_size_price;
    }

    public double getChicken() {
        return chicken;
    }

    public void setChicken(double chicken) {
        this.chicken = chicken;
    }

    public double getCheese() {
        return cheese;
    }

    public void setCheese(double cheese) {
        this.cheese = cheese;
    }

    public double getCorn() {
        return corn;
    }

    public void setCorn(double corn) {
        this.corn = corn;
    }
}
