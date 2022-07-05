package com.example.cocktailsrecipe.DBModels;

import androidx.annotation.NonNull;

public class FavoriteCocktail {
    private int id;
    private String name;
    private String glass;
    private String imageUrl;

    public FavoriteCocktail(int id, String name, String glass, String imageUrl) {
        this.id = id;
        this.name = name;
        this.glass = glass;
        this.imageUrl = imageUrl;
    }

    @NonNull
    @Override
    public String toString() {
        return "Cocktail {" +
                "id: " + id +
                ", name: " + name + "\n" +
                ", glass: " + glass +
                ", imageUrl: " + imageUrl;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGlass() {
        return glass;
    }

    public void setGlass(String glass) {
        this.glass = glass;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
