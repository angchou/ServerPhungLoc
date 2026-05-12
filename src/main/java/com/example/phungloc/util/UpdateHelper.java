package com.example.phungloc.util;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class UpdateHelper {
    public static <T> boolean updateIfChange(Supplier<T> oldValue, T newValue, Consumer<T> setter) {
        if (!Objects.equals(oldValue.get(), newValue)) {
            setter.accept(newValue);
            return true;
        }
        return false;
    }
}
