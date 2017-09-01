package sample;

import com.openalpr.jni.AlprException;
import com.openalpr.jni.AlprPlate;
import com.openalpr.jni.AlprPlateResult;
import com.openalpr.jni.AlprResults;
import javafx.fxml.Initializable;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;



public class Controller implements Initializable{

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        String country = "eu", configfile = "openalpr.conf",
                runtimeDataDir = "runtime_data", licensePlate = "";

        com.openalpr.jni.Alpr alpr = new com.openalpr.jni.Alpr(country, configfile, runtimeDataDir);

        // Set top N candidates returned to 20.
        alpr.setTopN(20);

// Set pattern to Maryland.
        alpr.setDefaultRegion("md");

        AlprResults results = null;
        try {
            results = alpr.recognize("C:\\Users\\p0keT\\Desktop\\1.jpg");
        } catch (AlprException e) {
            e.printStackTrace();
        }
        System.out.format("  %-15s%-8s\n", "Plate Number", "Confidence");
        for (AlprPlateResult result : results.getPlates())
        {
            for (AlprPlate plate : result.getTopNPlates()) {
                if (plate.isMatchesTemplate())
                    System.out.print("  * ");
                else
                    System.out.print("  - ");
                System.out.format("%-15s%-8f\n", plate.getCharacters(), plate.getOverallConfidence());
            }
        }

// Make sure to call this to release memory.
        alpr.unload();
    }
}
