package agh.or;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Lights {
    public static final int LIGHT_COUNT = 12;

    private final int state;
    private final List<Integer> value = new ArrayList<>();
    private final List<Integer> on = new ArrayList<>();

    public Lights(int state){
        this.state = state;
        for (int i = 0; i != Lights.LIGHT_COUNT; ++i) {
            value.add(state & 1);
            if((state & 1) == 1){
                on.add(i);
            }
            state >>= 1;
        }
    }

    public int get(int index){
        assert index >= 0 && index < LIGHT_COUNT;
        return value.get(index);
    }

    public int getState(){
        return state;
    }

    public boolean anyOn() {
        return state != 0;
    }

    public List<Integer> on() {
        return Collections.unmodifiableList(this.on);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();

        str.append('[');

        for(int light : on){
            str.append("%d, ".formatted(light));
        }

        str.deleteCharAt(str.length() - 1);
        str.deleteCharAt(str.length() - 1);
        str.append(']');

        return str.toString();
    }
}
