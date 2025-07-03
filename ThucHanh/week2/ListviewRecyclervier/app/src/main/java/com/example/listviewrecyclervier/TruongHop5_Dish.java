package com.example.listviewrecyclervier;

public class TruongHop5_Dish {
    private final String name;
    private final int imageResource;
    private final boolean hasPromotion;

    public TruongHop5_Dish() {
        this.name = "";
        this.imageResource = 0;
        this.hasPromotion = false;
    }

    public TruongHop5_Dish(String name, int imageResource, boolean hasPromotion) {
        this.name = name;
        this.imageResource = imageResource;
        this.hasPromotion = hasPromotion;
    }

    public String getName() { return name; }
    public int getImageResource() { return imageResource; }
    public boolean hasPromotion() { return hasPromotion; }
}
