package sample;

import java.io.File;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.FileChooser;

public class DataListView {
    private static  ArrayList<String> list = new ArrayList<>();
    private static ObservableList fileTxt = FXCollections.observableArrayList();
    private static File file;
    private static final FileChooser FILE_CHOOSER = new FileChooser();
    public static ArrayList<String> getList() {
        return list;
    }

    public static File getFile() {
        return file;
    }

    public static FileChooser getFileChooser() {
        return FILE_CHOOSER;
    }

    public static ObservableList getFileTxt() {
        return fileTxt;
    }
}
