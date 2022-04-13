package kg.geekteck.weatherapp.common;

import kg.geekteck.weatherapp.data.models.MainResponse;

public interface OnItemClick<T> {
    void buttonClick(T data);
    void click(MainResponse fragment);
}
