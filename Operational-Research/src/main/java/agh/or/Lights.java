package agh.or;

import java.util.Objects;

public class Lights {
    public static final int LIGHT_COUNT = 12;

    private final int value;

    public Lights(int state){
        this.value = state;
    }

    public boolean get(int index){
        assert index >= 0 && index < LIGHT_COUNT;
        return (value & (1 << index)) != 0;
    }

    public boolean anyOn() {
        return value != 0;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();

        str.append('[');

        for(int i = 0; i < LIGHT_COUNT; ++i){
            str.append("%d, ".formatted(get(i) ? 1 : 0));
        }

        str.deleteCharAt(str.length() - 1);
        str.deleteCharAt(str.length() - 1);
        str.append(']');

        return str.toString();
    }
}
