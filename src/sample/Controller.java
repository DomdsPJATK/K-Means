package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.util.LinkedList;
import java.util.List;

public class Controller {

    @FXML
    public TextField inputText;
    public Button btnAcction;
    public ListView listView;

    private boolean isWorking;
    private List<SinglePoint> points;
    private int pointsRange;
    private PointService pointService;

    public void acctionOnClick(ActionEvent event) {
        if(inputText.getText().isEmpty()) return;
        if(!isWorking){
            points = new LinkedList<>();
            pointsRange = Integer.parseInt(inputText.getText());
            pointService = new PointService("src/Resources/iris.data");
            setWorking(true);
            readPoints();
        }
    }

    public void setWorking(boolean working) {
        isWorking = working;
    }

    public void readPoints(){
        while(pointService.hasNext()){
            points.add(pointService.nextPoint());
        }
        System.out.println(points);
    }

}
