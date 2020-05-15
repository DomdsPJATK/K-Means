package sample;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class PointService {

    private String path;
    private Scanner scanner;

    public PointService(String path) {
        this.path = path;
        try {
            this.scanner = new Scanner(new File(path));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public SinglePoint nextPoint(){
        return SinglePoint.createSinglePoint(scanner.nextLine());
    }

    public boolean hasNext(){
        return scanner.hasNextLine();
    }


}
