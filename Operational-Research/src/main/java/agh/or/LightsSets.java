package agh.or;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class LightsSets {
    private static final Set<Lights> invalids = new HashSet<>();
    private static final Set<Lights> valids = new HashSet<>();

    private static final List<Integer> collWithLeft = List.of(
            3, 4, 7, 8, 9, 10
    );
    private static final List<Integer> collWithStraight = List.of(
            3, 4, 5, 6, 9, 10
    );
    private static final List<Integer> collWithRight = List.of(
            6, 10
    );

    private static boolean areColliding(Lights lights){
        for(int i = 0; i != 4; ++i){
            int left = i * 3;
            int straight = i * 3 + 1;
            int right = i * 3 + 2;

            if(lights.get(left)) for(var coll : collWithLeft){
                int checked = (left + coll) % Lights.LIGHT_COUNT;
                if(lights.get(checked)){
                    return true;
                }
            }
            if(lights.get(straight)) for(var coll : collWithStraight){
                int checked = (straight + coll - 1) % Lights.LIGHT_COUNT;
                if(lights.get(checked)){
                    return true;
                }
            }
            if(lights.get(right)) for(var coll : collWithRight){
                int checked = (right + coll - 2) % Lights.LIGHT_COUNT;
                if(lights.get(checked)){
                    return true;
                }
            }
        }
        return false;
    }

    private static boolean areValid(Lights lights){
        return !areColliding(lights) && lights.anyOn();
    }

    private static void generate() {
        int endState = 1 << 12;
        for(int state = 0; state != endState; ++state){
            Lights lights = new Lights(state);
            if(areValid(lights)){
                valids.add(lights);
            }
            else{
                invalids.add(lights);
            }
        }
    }

    public static Set<Lights> getInvalids() {
        if(invalids.isEmpty()) {
            generate();
        }
        return invalids;
    }

    public static Set<Lights> getValids() {
        if(valids.isEmpty()) {
            generate();
        }
        return valids;
    }
}
