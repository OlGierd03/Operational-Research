package agh.or;

import java.util.*;

public class LightsSets {
    private static final Set<Lights> invalids = new HashSet<>();
    private static final Set<Lights> valids = new HashSet<>();

    private static final Set<Integer> collWithLeft = Set.of(
            3, 4, 7, 8, 9, 10
    );
    private static final Set<Integer> collWithStraight = Set.of(
            3, 4, 5, 6, 9, 10
    );
    private static final Set<Integer> collWithRight = Set.of(
            6, 10
    );

    private static final Set<Integer> all = Set.of(
            0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11
    );

    private static Set<Integer> diff(Set<Integer> set1, Set<Integer> set2) {
        var tempSet = new HashSet<>(set1);
        tempSet.removeAll(set2);
        return tempSet;
    }

    private static final Set<Integer> noncollWithLeft = diff(all, collWithLeft);
    private static final Set<Integer> noncollWithStraigth = diff(all, collWithStraight);
    private static final Set<Integer> noncollWithRight = diff(all, collWithRight);

    private static boolean areColliding(Lights lights){
        for(int i = 0; i != 4; ++i){
            int left = i * 3;
            int straight = i * 3 + 1;
            int right = i * 3 + 2;

            if(lights.get(left) == 1) for(var coll : collWithLeft){
                int checked = (left + coll) % Lights.LIGHT_COUNT;
                if(lights.get(checked) == 1){
                    return true;
                }
            }
            if(lights.get(straight) == 1) for(var coll : collWithStraight){
                int checked = (straight + coll - 1) % Lights.LIGHT_COUNT;
                if(lights.get(checked) == 1){
                    return true;
                }
            }
            if(lights.get(right) == 1) for(var coll : collWithRight){
                int checked = (right + coll - 2) % Lights.LIGHT_COUNT;
                if(lights.get(checked) == 1){
                    return true;
                }
            }
        }
        return false;
    }

    private static boolean areMaximized(Lights lights){
        Set<Integer> canBeOn = new HashSet<>(all);

        for(var on : lights.on()){
            int relativeLight = on % 3;
            canBeOn.remove(on);

            if(relativeLight == 0) {
                for(int coll : collWithLeft){
                    coll = (coll + on) % Lights.LIGHT_COUNT;
                    canBeOn.remove(coll);
                }
            }
            else if(relativeLight == 1) {
                for(int coll : collWithStraight){
                    coll = (coll + on - 1) % Lights.LIGHT_COUNT;
                    canBeOn.remove(coll);
                }
            }
            else if(relativeLight == 2) {
                for (int coll : collWithRight) {
                    coll = (coll + on - 2) % Lights.LIGHT_COUNT;
                    canBeOn.remove(coll);
                }
            }
            if(canBeOn.isEmpty()) return true;
        }

        return canBeOn.isEmpty();
    }

    private static boolean areValid(Lights lights){
        return !areColliding(lights) && lights.anyOn() && areMaximized(lights);
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
        return Collections.unmodifiableSet(invalids);
    }

    public static Set<Lights> getValids() {
        if(valids.isEmpty()) {
            generate();
        }
        return Collections.unmodifiableSet(valids);
    }
}
