package sample;

import java.io.*;
import java.net.MalformedURLException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MainController {


    @FXML
    private TextField search;

    @FXML
    private MenuItem openBar;

    @FXML
    private ListView<String> ListView;

    @FXML
    private MenuItem about;
    @FXML
    private ImageView imageView;

    @FXML
    void OpenBar(ActionEvent event) {

    }


    @FXML
    void aboutModal(ActionEvent event) {

    }

    @FXML
    void initialize() {
        addListView();
        openBar.setOnAction(actionEvent -> {
            setOpenBarButton();
        });
        about.setOnAction(actionEvent -> {


            try {
                File file = new File("../images/justMe.png");
                String localUrl = null;
                try {
                    localUrl = file.toURI().toURL().toString();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }

                Parent root = FXMLLoader.load(getClass().getResource("../values/fxml/aboutMe.fxml"));
                Image image = new Image(localUrl);
                imageView = new ImageView(image);
                imageView.setImage(image);
                Stage newWindow = new Stage();
                newWindow.setTitle("About");
                newWindow.setScene(new Scene(root));
                newWindow.initModality(Modality.WINDOW_MODAL);
                newWindow.initOwner(StartProgram.primaryFinalStage);
                newWindow.show();

            } catch (Exception e) {
                e.getStackTrace();
            }
        });


        ;
        search.setOnAction(e -> {
            updateListViewWhenEnteringASearch();

        });
    }

    private void addListView() {

        DataListView.getFileTxt().addAll(ControllerStartProgram.getList());
        ListView.getItems().addAll(DataListView.getFileTxt());
        ListView.setItems(DataListView.getFileTxt());
        DataListView.getList().addAll(ControllerStartProgram.getList());
        ControllerStartProgram.getList().clear();


    }

    private void addListViewThrowOpenBar() {
        ListView.getItems().clear();
        DataListView.getFileTxt().clear();
        DataListView.getFileTxt().addAll(DataListView.getList());
        ListView.setItems(DataListView.getFileTxt());

    }

    private void setOpenBarButton() {
        DataListView.getFileChooser().getExtensionFilters().addAll(//
                new FileChooser.ExtensionFilter("TXT", "*.txt"));
        File file;
        file = DataListView.getFileChooser().showOpenDialog(StartProgram.primaryFinalStage);
        if (file != null) {
            DataListView.getList().clear();
            readFileAndConvertToString(file);
            addListViewThrowOpenBar();

        }
    }

    private void readFileAndConvertToString(File file) {
        try {
            String read = "";

            try (BufferedReader buf = new BufferedReader(new FileReader(file))) {
                while ((read = buf.readLine()) != null) {
                    DataListView.getList().add(read);

                }
            }
        } catch (IOException e) {

        }


    }

    private void ListViewGetBack() {
        ListView.getItems().clear();
        DataListView.getFileTxt().clear();
        DataListView.getFileTxt().addAll(DataListView.getList());
        ListView.setItems(DataListView.getFileTxt());

    }


    private void updateListViewWhenEnteringASearch() {
        Set<String> wordListAndLap = new HashSet<>();
        Set<String> wordList = new HashSet<>();
        Set<String> wordListTwoLap = new HashSet<>();
        ArrayList<String> wordListArrayList = new ArrayList<>();
        ObservableList fileTxt = FXCollections.observableArrayList();
        boolean isCheck = true;

        while (isCheck) {
            String word = search.getText();
            int wordsCountCheckAnd = 0;
            int wordsCountCheckOr = 0;
            String[] wordsCheck = word.split(" ");   //проверка на и и или на их содержание
            for (int i = 0; i < wordsCheck.length; i++) {
                if (wordsCheck[i].equalsIgnoreCase("и")) {
                    wordsCountCheckAnd++;
                } else if (wordsCheck[i].equalsIgnoreCase("или")) {
                    wordsCountCheckOr++;
                }
            }
            if (wordsCountCheckAnd > 0 && wordsCountCheckOr > 0) {
                //System.out.println("Нельзя использовать их вместе");
            } else if (word.isEmpty()) {
                ListViewGetBack();  //Если поле пустое, то вернуть весь список
                break;
            } else if (word.contains(" ")) {

                if (wordsCountCheckOr > 0) {
                    ArrayList<String> wordOneList = new ArrayList<>();
                    ArrayList<String> wordTwoList = new ArrayList<>();
                    ArrayList<String> removeCharListDraft = new ArrayList<>();
                    ArrayList<String> removeCharList = new ArrayList<>();
                    String[] removeChar = word.split(" ");


                    removeCharListDraft.addAll(Arrays.asList(removeChar));

                    for (int i = 0; i < removeCharListDraft.size(); i++) {
                        Pattern charAndBig = Pattern.compile("^ИЛИ$");
                        Pattern charAndLow = Pattern.compile("^или$");
                        Matcher matcherBig = charAndBig.matcher(removeCharListDraft.get(i));
                        Matcher matcherLow = charAndLow.matcher(removeCharListDraft.get(i));
                        if (!matcherBig.find() && !matcherLow.find()) {
                            removeCharList.add(removeCharListDraft.get(i));
                        }

                    }
                    word = getString(wordList, wordListTwoLap, wordListArrayList, word, removeCharList);

                }


                //Конец с буквой  или


                else if (wordsCountCheckAnd > 0) {

                    String[] containsChar = word.split(" ");
                    int countChar = 0;
                    for (int i = 0; i < containsChar.length; i++) {
                        if (containsChar[i].equalsIgnoreCase("и")) {
                            countChar++;
                        }

                    }
                    if (countChar > 2) {
                        System.out.println("Ошибка");

                    } else {
                        //Начало


                        ArrayList<String> removeCharList = new ArrayList<>();
                        String[] removeChar = word.split(" ");


                        ArrayList<String> removeCharListDraft = new ArrayList<>(Arrays.asList(removeChar));

                        for (int i = 0; i < removeCharListDraft.size(); i++) {
                            Pattern charAndBig = Pattern.compile("^И$");
                            Pattern charAndLow = Pattern.compile("^и$");
                            Matcher matcherBig = charAndBig.matcher(removeCharListDraft.get(i));
                            Matcher matcherLow = charAndLow.matcher(removeCharListDraft.get(i));
                            if (!matcherBig.find() && !matcherLow.find()) {
                                removeCharList.add(removeCharListDraft.get(i));
                            }


                        }
                        word = getString(wordList, wordListTwoLap, wordListArrayList, word, removeCharList);

                        int countCoincidenceOfWords = 0;

                        //Использование с двумя и


                        String getTextFromSearch;
                        ArrayList<String> wordListArrayList2 = new ArrayList<>(wordList);


                        for (int i = 0; i < wordListArrayList2.size(); i++) {
                            getTextFromSearch = wordListArrayList2.get(i);
                            String[] massiveTextFromSearch = getTextFromSearch.split("(^ *)|( *?$)");
                            String breakingWordFromSearch = "";
                            for (int k = 0; k < massiveTextFromSearch.length; k++) {
                                breakingWordFromSearch = breakingWordFromSearch + massiveTextFromSearch[k];
                            }
                            String[] massiveBreakingWordInMiddle = breakingWordFromSearch.split(" ");
                            String breakingWordIntMiddle = "";
                            for (int f = 0; f < massiveBreakingWordInMiddle.length; f++) {
                                breakingWordIntMiddle = breakingWordIntMiddle + massiveBreakingWordInMiddle[f];
                                String delemiters = " \t\n\r\f,.!?–";
                                String wordFinally;
                                StringTokenizer tokenizer = new StringTokenizer(breakingWordIntMiddle, delemiters);
                                while (tokenizer.hasMoreTokens()) {
                                    wordFinally = tokenizer.nextToken(); // не убирает - "тире", поэтому добавляю часть кода, чтобы убрать
                                    String[] wordFinallyMassive = wordFinally.split("–");
                                    String wordFinallyConvert = wordFinallyMassive[0];
                                    String patternString = "/ый$|ой$|ая$|ое$|ые$|ому$|а$|о$|у$|е$|ого$|ему$|и$|ство$|ых$|ох$|ия$|ий$|ь$|я$|он$|ют$|ат$|ы$|" +
                                            "ЫЙ$|ОЙ$|АЯ$|ОЕ$|ЫЕ$|ОМУ$|А$|О$|У$|Е$|ОГО$|ЕМУ$|И$|СТВО$|ЫХ$|ОХ$|ИЯ$|ИЙ$|Ь$|Я$|ОН$|ЮТ$|АТ$|Ы$";
                                    for (int k = 0; k < removeCharList.size(); k++) {
                                        word = removeCharList.get(k);
                                        String wordFinallyPattern = word.replaceAll(patternString, ""); // tyt
                                        String wordFinallyConvertPattern = wordFinallyConvert.replaceAll(patternString, "");


                                        if (wordFinallyPattern.equalsIgnoreCase(wordFinallyConvertPattern)) {
                                            countCoincidenceOfWords++;
                                            if (countCoincidenceOfWords >= 2) {
                                                wordListAndLap.add(getTextFromSearch);
                                                countCoincidenceOfWords = 0;
                                            }
                                        }
                                    }

                                }
                                breakingWordIntMiddle = "";


                            }


                        }
                    }
                }
            } else {
                getTextFromSearch(wordList, word);
            }
            getTextFromSearch(wordList, wordListTwoLap, wordListArrayList, word);


            if (wordsCountCheckAnd > 0) {
                if (wordListAndLap.size() != 0) {
                    ListView.getItems().clear();
                    fileTxt.addAll(wordListAndLap);
                    ListView.setItems(fileTxt);
                    isCheck = false;
                } else {
                    ListView.getItems().clear();
                    isCheck = false;
                }
            } else {
                //  Если список слов из второй части пустой, то выводим результат из первого.
                if (wordListTwoLap.size() != 0) {
                    ListView.getItems().clear();
                    fileTxt.addAll(wordListTwoLap);
                    ListView.setItems(fileTxt);
                    isCheck = false;
                } else {
                    ListView.getItems().clear();
                    fileTxt.addAll(wordList);
                    ListView.setItems(fileTxt);
                    isCheck = false;
                }
            }
        }
    }

    private void getTextFromSearch(Set<String> wordList, Set<String> wordListTwoLap, ArrayList<String> wordListArrayList, String word) {
        String getTextFromSearch;
        wordListArrayList.addAll(wordList);

        for (int i = 0; i < wordListArrayList.size(); i++) {
            getTextFromSearch = wordListArrayList.get(i);
            String[] massiveTextFromSearch = getTextFromSearch.split("(^ *)|( *?$)");
            String breakingWordFromSearch = "";
            for (int k = 0; k < massiveTextFromSearch.length; k++) {
                breakingWordFromSearch = breakingWordFromSearch + massiveTextFromSearch[k];
            }
            String[] massiveBreakingWordInMiddle = breakingWordFromSearch.split(" ");
            String breakingWordIntMiddle = "";
            for (int f = 0; f < massiveBreakingWordInMiddle.length; f++) {
                breakingWordIntMiddle = breakingWordIntMiddle + massiveBreakingWordInMiddle[f];
                String delemiters = " \t\n\r\f,.!?–";
                String wordFinally;
                StringTokenizer tokenizer = new StringTokenizer(breakingWordIntMiddle, delemiters);
                while (tokenizer.hasMoreTokens()) {
                    wordFinally = tokenizer.nextToken(); // не убирает - "тире", поэтому добавляю часть кода, чтобы убрать
                    String[] wordFinallyMassive = wordFinally.split("–");
                    String wordFinallyConvert = wordFinallyMassive[0];
                    String patternString = "/ый$|ой$|ая$|ое$|ые$|ому$|а$|о$|у$|е$|ого$|ему$|и$|ство$|ых$|ох$|ия$|ий$|ь$|я$|он$|ют$|ат$|ы$|" +
                            "ЫЙ$|ОЙ$|АЯ$|ОЕ$|ЫЕ$|ОМУ$|А$|О$|У$|Е$|ОГО$|ЕМУ$|И$|СТВО$|ЫХ$|ОХ$|ИЯ$|ИЙ$|Ь$|Я$|ОН$|ЮТ$|АТ$|Ы$";
                    String wordFinallyPattern = word.replaceAll(patternString, "");
                    String wordFinallyConvertPattern = wordFinallyConvert.replaceAll(patternString, "");

                    if (wordFinallyPattern.equalsIgnoreCase(wordFinallyConvertPattern)) {
                        wordListTwoLap.add(getTextFromSearch);
                    }
                }
                breakingWordIntMiddle = "";


            }

        }
    }

    private void getTextFromSearch(Set<String> wordList, String word) {
        String getTextFromSearch;
        for (int i = 0; i < DataListView.getList().size(); i++) {
            getTextFromSearch = DataListView.getList().get(i);
            String[] massiveTextFromSearch;
            massiveTextFromSearch = getTextFromSearch.split("(^ *)|( *?$)");
            String breakingWordFromSearch = "";
            for (int k = 0; k < massiveTextFromSearch.length; k++) {
                breakingWordFromSearch = breakingWordFromSearch + massiveTextFromSearch[k];
            }
            String[] massiveBreakingWordInMiddle = breakingWordFromSearch.split(" ");
            String breakingWordInMiddle = "";
            for (int f = 0; f < massiveBreakingWordInMiddle.length; f++) {
                breakingWordInMiddle = new StringBuilder().append(breakingWordInMiddle).append(massiveBreakingWordInMiddle[f]).toString();
                String breakingWordSplitPoint = "";
                if (breakingWordInMiddle.contains(".")) {
                    String[] massiveBreakingWordSplitPoint = breakingWordInMiddle.split("\\.");

                    for (int a = 0; a < massiveBreakingWordSplitPoint.length; a++) {
                        breakingWordSplitPoint = breakingWordSplitPoint + massiveBreakingWordSplitPoint[a];
                        String breakingWordSplitChar = String.format("(?<=\\G.{%d})", word.length());
                        String[] massiveBreakingWordSplitChar = breakingWordSplitPoint.split(breakingWordSplitChar);

                        for (int j = 0; j < massiveBreakingWordSplitChar.length; j++) {
                            String breakingWordInMiddleTire = massiveBreakingWordSplitChar[j];
                            String[] getTextFromSearchBreakingWord = breakingWordInMiddleTire.split("-");

                            String patternString = "/ый$|ой$|ая$|ое$|ые$|ому$|а$|о$|у$|е$|ого$|ему$|и$|ство$|ых$|ох$|ия$|ий$|ь$|я$|он$|ют$|ат$|ы$|" +
                                    "ЫЙ$|ОЙ$|АЯ$|ОЕ$|ЫЕ$|ОМУ$|А$|О$|У$|Е$|ОГО$|ЕМУ$|И$|СТВО$|ЫХ$|ОХ$|ИЯ$|ИЙ$|Ь$|Я$|ОН$|ЮТ$|АТ$|Ы$";
                            String wordFinallyPattern = word.replaceAll(patternString, "");
                            String wordFinallyConvertPattern = getTextFromSearchBreakingWord[0].replaceAll(patternString, "");

                            if (wordFinallyPattern.equalsIgnoreCase(wordFinallyConvertPattern)) {
                                wordList.add(getTextFromSearch);
                                break;
                            }

                        }
                        breakingWordSplitPoint = "";
                        breakingWordInMiddle = "";
                    }


                } else {

                    try {


                        String getTextFromSearchBreakingWordSpilt = String.format("(?<=\\G.{%d})", word.length()); // tyt
                        String[] breakingWordInMiddleMassiveTire = breakingWordInMiddle.split("–");
                        for (int l = 0; l < breakingWordInMiddleMassiveTire.length; l++) {
                            String breakingWordInMiddleTire = breakingWordInMiddleMassiveTire[l];
                            String[] getTextFromSearchBreakingWord = breakingWordInMiddleTire.split(getTextFromSearchBreakingWordSpilt);
                            String patternString = "/ый$|ии$|ой$|ая$|ое$|ые$|ому$|а$|о$|у$|е$|ого$|ему$|и$|ство$|ых$|ох$|ия$|ий$|ь$|я$|он$|ют$|ат$|ы$|" +
                                    "ЫЙ$|ОЙ$|АЯ$|ОЕ$|ЫЕ$|ОМУ$|А$|О$|У$|Е$|ОГО$|ЕМУ$|И$|СТВО$|ЫХ$|ОХ$|ИЯ$|ИЙ$|Ь$|Я$|ОН$|ЮТ$|АТ$|Ы$|ИИ$|";
                            String wordFinallyPattern = word.replaceAll(patternString, "");
                            String wordFinallyConvertPattern = getTextFromSearchBreakingWord[0].replaceAll(patternString, "");

                            if (wordFinallyPattern.equalsIgnoreCase(wordFinallyConvertPattern)) {
                                wordList.add(getTextFromSearch);
                                break;
                            }
                            breakingWordInMiddle = "";
                        }
                    } catch (ArrayIndexOutOfBoundsException e) {
                        e.printStackTrace();
                    }
                }

            }
        }
    }

    private String getString(Set<String> wordList, Set<String> wordListTwoLap, ArrayList<String> wordListArrayList, String word, ArrayList<String> removeCharList) {
        for (int count = 0; count < removeCharList.size(); count++) {
            word = removeCharList.get(count);
            getTextFromSearch(wordList, word);
        }
        for (int count = 0; count < removeCharList.size(); count++) {
            word = removeCharList.get(count);
            getTextFromSearch(wordList, wordListTwoLap, wordListArrayList, word);
        }
        return word;
    }
}

