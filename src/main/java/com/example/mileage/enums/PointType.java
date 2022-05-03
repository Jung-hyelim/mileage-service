package com.example.mileage.enums;

public enum PointType {
    CONTENT(1),
    PHOTO(1),
    FIRST_PLACE(1);

    public int point;
    PointType(int point) {
        this.point = point;
    }
}
