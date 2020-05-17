package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Controller {

    @FXML
    public TextField inputText;
    public Button btnAcction;
    public ListView listView;

    private boolean isWorking;
    private List<SinglePoint> points;
    private List<Centroid> centroids;
    private int centroidsCount;
    private PointService pointService;
    private List<SinglePoint> stackPoints;
    private ObservableList<String> listToView;

    public void actionsOnClick(ActionEvent event) {
        if(inputText.getText().isEmpty()) return;
        if(!isWorking){
            points = new LinkedList<>();
            centroids = new ArrayList<>();
            centroidsCount = Integer.parseInt(inputText.getText());
            pointService = new PointService("src/Resources/iris.data");
            stackPoints = new LinkedList<>();
            listToView = FXCollections.observableArrayList();
            setWorking(true);
            readPoints();
            createCentroids();
            int noChanges = 0;
            int iteracja = 0;
            while(true) {
                createEuclideanDistances();
                createGroups();
                if(!hasBeenChangeGroup()) {
                    noChanges++;
                }
                listToView.add(getSumAvgForAllIter(iteracja++));
                if(noChanges >= 1) break;
                modifyCentroids();
            }
            listToView.add(getGroups());
            listToView.add(getCleanliness());
            listView.setItems(listToView);
        }
    }

    public void setWorking(boolean working) {
        isWorking = working;
    }

    public void readPoints(){
        while(pointService.hasNext()){
            SinglePoint point = pointService.nextPoint();
            points.add(point);
            stackPoints.add(point);
        }
    }

    public void createCentroids(){
        for (int i = 0; i < centroidsCount; i++) {
            int index = (int)(Math.random()*stackPoints.size());
            Centroid centroid = new Centroid(stackPoints.get(index));
            stackPoints.remove(index);
            centroids.add(centroid);
        }
    }

    public void createEuclideanDistances(){
        for(Centroid centroid : centroids){
            centroid.resetEuclideanDistances();
            for (SinglePoint point : points) {
                centroid.getEuclideanDistances().add(singleEuclideanDistance(centroid,point));
            }
        }
    }

    public double singleEuclideanDistance(Centroid centroid, SinglePoint point){
        double sum = 0;
        for (int i = 0; i < centroid.getCoords().size(); i++) {
            sum += Math.pow(centroid.getCoords().get(i) - point.getCoords().get(i), 2);
        }
        return Math.sqrt(sum);
    }

    public void createGroups() {
        for (Centroid centroid : centroids) {
            centroid.setPreviousGroup();
            centroid.resetGroup();
        }
        for (int i = 0; i < points.size(); i++) {
            Centroid centroid = centroids.get(0);
            for (int j = 0; j < centroids.size(); j++) {
                Centroid centroidToCheck = centroids.get(j);
                if(!centroid.equals(centroidToCheck)) {
                    if (centroid.getEuclideanDistances().get(i) > centroidToCheck.getEuclideanDistances().get(i)) {
                        centroid = centroidToCheck;
                    }
                }
            }
            centroid.addToGroup(points.get(i));
        }
    }

    public void modifyCentroids(){
        for (Centroid centroid : centroids) {
            centroid.countNewCoords();
        }
    }

    public boolean hasBeenChangeGroup(){
        for (Centroid centroid : centroids) {
            for (int i = 0; i < centroid.getGroup().size(); i++) {
                if(centroid.getGroup().size() != centroid.getPreviousGroup().size()) return true;
                if(!centroid.getGroup().get(i).equals(centroid.getPreviousGroup().get(i))) return true;
            }
        }
        return false;
    }

    public String getSumAvgForAllIter(int iteracja){
        String text = "Iteracja: " + iteracja + "\n";
        int i=0;
        for (Centroid centroid : centroids) {
            text+="\nSuma od C" + (i) + ": " + centroid.getSumOfDistance();
            text+=" Srednia od C" + (i) + ": " + centroid.getAvarageOfDistance();
            i++;
        }
        return text;
    }

    public String getGroups(){
        String text = "Grupy koncowe: \n";
        int i = 1;
        for (Centroid centroid : centroids) {
            text+="Grupa "+ (i++) +": " + centroid.getGroup() + "\n";
        }
        return text;
    }

    public String getCleanliness(){
        String text = "Procentowe sklady grupy\n";
        int i = 1;
        for (Centroid centroid : centroids) {
            text+="Grupa "+ (i++) +":\n" + centroid.getCleanlinessOfGroup() + "\n";
        }
        return text;
    }

}
