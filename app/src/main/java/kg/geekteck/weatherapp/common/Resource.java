package kg.geekteck.weatherapp.common;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class Resource<T> {

    @NotNull
    public final Status status;
    public final T data;
    public final String msc;

    public Resource(@NotNull Status status, T data, String msc) {
        this.status = status;
        this.data = data;
        this.msc = msc;
    }

    public static <T> Resource<T> success(@NotNull T data) {
        return new Resource<>(Status.SUCCESS, data, null);
    }

    public static <T> Resource<T> error(String msg, @Nullable T data) {
        return new Resource<>(Status.ERROR, data, msg);
    }

    public static <T> Resource<T> loading() {
        return new Resource<>(Status.LOADING, null, null);
    }


}
