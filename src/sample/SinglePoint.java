package sample;

import java.util.LinkedList;
import java.util.List;

public class SinglePoint {

    private List<Double> coords;
    private String name;

    public void addCoord(double coord) {
        if (coords == null) coords = new LinkedList<>();
        coords.add(coord);
    }

    public void setName(String name) {
        this.name = name;
    }

    public static SinglePoint createSinglePoint(String line) {
        String[] splited = line.split(",");
        SinglePoint point = new SinglePoint();
        for (int i = 0; i < splited.length; i++) {
            if (i == splited.length - 1) point.setName(splited[i]);
            else point.addCoord(Double.parseDouble(splited[i]));
        }
        return point;
    }

}
