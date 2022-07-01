package com.example.cocktailsrecipe.Models;

import java.io.Serializable;
import java.util.ArrayList;

public class CocktailsResponse implements Serializable {
    private ArrayList<Drink> drinks;

    public ArrayList<Drink> getDrinks() {
        return drinks;
    }

    public void setDrinks(ArrayList<Drink> drinks) {
        this.drinks = drinks;
    }
}
