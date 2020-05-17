package sample;

import java.util.ArrayList;
import java.util.List;

public class Centroid {

    private List<Double> coords;
    private List<Double> euclideanDistances;
    private List<SinglePoint> group;
    private List<SinglePoint> previousGroup;

    public Centroid(SinglePoint point) {
        this.coords = point.getCoords();
        euclideanDistances = new ArrayList<>();
        group = new ArrayList<>();
    }

    public void countNewCoords(){
        List<Double> newCoords = new ArrayList<>();
        for (int i = 0; i < coords.size(); i++) {
            newCoords.add(countSingleCoord(i));
        }
        this.coords = newCoords;
    }

    private double countSingleCoord(int index){
        double sum = 0;
        for (SinglePoint singlePoint : group) {
            sum+=singlePoint.getCoords().get(index);
        }
        return sum/group.size();
    }

    public List<Double> getEuclideanDistances() {
        return euclideanDistances;
    }

    public void addToGroup(SinglePoint point){
        this.group.add(point);
    }

    public List<Double> getCoords() {
        return coords;
    }

    public List<SinglePoint> getGroup() {
        return group;
    }

    public void resetGroup(){
        this.group = new ArrayList<>();
    }

    public void setPreviousGroup(){
        previousGroup = group;
    }

    public void resetEuclideanDistances(){
        this.euclideanDistances = new ArrayList<>();
    }

    public void setCoords(List<Double> coords) {
        this.coords = coords;
    }

    public List<SinglePoint> getPreviousGroup() {
        return previousGroup;
    }

    public double getSumOfDistance(){
        double sum = 0;
        for (Double euclideanDistance : getEuclideanDistances()) {
           sum+=euclideanDistance;
        }
        return sum;
    }

    public double getAvarageOfDistance(){
        double sum = 0;
        for (Double euclideanDistance : getEuclideanDistances()) {
            sum+=euclideanDistance;
        }
        return sum/getEuclideanDistances().size();
    }

    public String getCleanlinessOfGroup(){
        int irisSet = 0;
        int irisVersi = 0;
        int irisVir = 0;
        for (SinglePoint singlePoint : group) {
            if(singlePoint.getName().equals("Iris-setosa")) irisSet++;
            if(singlePoint.getName().equals("Iris-versicolor")) irisVersi++;
            if(singlePoint.getName().equals("Iris-virginica")) irisVir++;
        }
        double irisRet1 = (double)irisSet/group.size()*100;
        double irisRet2 = (double)irisVersi/group.size()*100;
        double irisRet3 = (double)irisVir/group.size()*100;
        System.out.println();
        return "Iris-Setosa: " + irisRet1+ "%\nIris-versicolor: " + irisRet2 + "%\nIris-virginica: " + irisRet3 + "%";
    }

}
