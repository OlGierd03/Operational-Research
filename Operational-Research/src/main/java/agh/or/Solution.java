package agh.or;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Solution {
    private List<O> values = new ArrayList<O>();

    public Solution(List<O> values) {
        this.values = values;
    }

    public List<O> getValues() {
        return Collections.unmodifiableList(values);
    }

    public static boolean willEnd(List<O> values) {
        int lightsFlag = 0;
        for (O o : values) {
            lightsFlag |= o.lights().getState();
        }
        return lightsFlag == ((1 << Lights.LIGHT_COUNT) - 1);
    }

    public boolean willEnd() {
        return willEnd(values);
    }
}
