package assignment.product;

import javafx.scene.image.Image;

import java.io.File;
import java.net.URL;
import java.util.Random;


/*
 *
 * Class handling the reading of files and icons from file
 *
 *  ***NOTHING TO DO HERE*** loaded from this directory everything loaded from out/production/ week5/...
 *
 */
public class FileService {

    private FileService() {
    } // Can't instantiate

    // Construct a randomly ordered list of pairs of card names
    public static String[] getCardNames(int nCards) {
        String[] fileNames = FileService.getImageFileNames();
        shuffle(fileNames);
        String[] tmp = new String[nCards];
        for (int i = 0; i < tmp.length; i += 2) {
            tmp[i] = fileNames[i];
            tmp[i + 1] = fileNames[i];
        }
        shuffle(tmp);
        return tmp;
    }

    public static Image getImage(String fileName, double requestedWidth, double requestedHeight) {
        URL url = FileService.class.getResource("icons/" + fileName);
        return new Image(url.toString(), requestedWidth, requestedHeight, true, true);
    }

    // ----  Private helpers -------------------

    // Fisher - Yates once again !!!
    private static <T> void shuffle(T[] arr) {
        Random rand = new Random();
        for (int i = arr.length - 1; i > 0; i--) {
            int j = rand.nextInt(i);
            T o = arr[i];
            arr[i] = arr[j];
            arr[j] = o;
        }
    }

    private static String[] getImageFileNames() {
        URL url = FileService.class.getResource("icons");
        File folder = new File(url.getPath());
        File[] listOfFiles = folder.listFiles();
        String[] names = new String[listOfFiles.length];
        for (int i = 0; i < names.length; i++) {
            names[i] = listOfFiles[i].getName();
        }
        return names;
    }
}
