package com.example.listviewrecyclervier;

public enum TruongHop5_Thumbnail {
    Thumbnail1("Thumbnail 1", R.drawable.first_thumnail),
    Thumbnail2("Thumbnail 2", R.drawable.second_thumnail),
    Thumbnail3("Thumbnail 3", R.drawable.third_thumnail),
    Thumbnail4("Thumbnail 4", R.drawable.fourth_thumnail);

    private final String name;
    private final int img;

    TruongHop5_Thumbnail() {
        this.name = "";
        this.img = 1;
    }

    TruongHop5_Thumbnail(String name, int img) {
        this.name = name;
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public int getImg() {
        return img;
    }
}
