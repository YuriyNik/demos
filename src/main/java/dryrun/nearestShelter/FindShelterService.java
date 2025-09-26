package dryrun.nearestShelter;

import java.util.*;

public class FindShelterService {
    private final Map<Integer, Coordinates> shelters = new HashMap<>();
    public static void main(String[] args) {
        FindShelterService findShelterService = new FindShelterService();
        findShelterService.initLoad();
        findShelterService.findNearest(32.11, 34.90);

    }

    void initLoad(){
        shelters.put(1 , new Coordinates(31.778345, 35.225079));
        shelters.put(2 , new Coordinates(31.858897, 34.974976));
        shelters.put(3 , new Coordinates(32.043587, 34.882965));
        shelters.put(4 , new Coordinates(32.039512, 34.858246));
        shelters.put(5 , new Coordinates(32.100026, 34.891205));
    }

    // Простая евклидова метрика (без учёта сферичности Земли)
    private static double euclidean(double x1, double y1, double x2, double y2) {
        return Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
    }

    private void findNearest(double x, double y){
        final Map<Integer, Double> distances = new HashMap<>();
        for (Map.Entry<Integer,Coordinates> entry: shelters.entrySet()){
            Integer id = entry.getKey();
            Coordinates shelterCoords = entry.getValue();
            distances.put(id,euclidean(x,y,shelterCoords.getX(),shelterCoords.getY()));
        }
        System.out.println(distances);
        List<Map.Entry<Integer, Double>> list = new ArrayList<>(distances.entrySet());
        list.sort(Comparator.comparingDouble(Map.Entry::getValue));

        for (int i = 0; i < Math.min(2, list.size()); i++) {
            Map.Entry<Integer, Double> e = list.get(i);
            System.out.println("Shelter " + e.getKey() + " → " + e.getValue());
        }

    }

    private class Coordinates{
        private double x;
        private double y;
        Coordinates(double x, double y){
            this.x=x;
            this.y=y;
        }

        public double getX() {
            return x;
        }

        public double getY() {
            return y;
        }

        public void setX(double x) {
            this.x = x;
        }

        public void setY(double y) {
            this.y = y;
        }
    }

}
