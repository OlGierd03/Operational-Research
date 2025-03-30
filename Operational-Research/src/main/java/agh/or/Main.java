package agh.or;

public class Main {
    public static void main(String[] args) {
        for (var invalidLight : LightsSets.getValids()){
            System.out.println(invalidLight);
        }
        System.out.println(LightsSets.getValids().size());
    }
}