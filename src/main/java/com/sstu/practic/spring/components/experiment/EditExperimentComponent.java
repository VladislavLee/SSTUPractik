package com.sstu.practic.spring.components.experiment;

import com.sstu.practic.spring.annotations.HandleEvent;
import com.sstu.practic.spring.annotations.JavaFxComponent;
import com.sstu.practic.spring.components.FxComponent;
import com.sstu.practic.spring.components.MainComponent;
import com.sstu.practic.spring.components.arrangement.ListArrangementComponent;
import com.sstu.practic.spring.components.design.ListDesignComponent;
import com.sstu.practic.spring.components.experimentSubject.ListExperimentSubject;
import com.sstu.practic.spring.components.experimentType.ListExperimentTypeComponent;
import com.sstu.practic.spring.components.processingMethod.EditProcessingMethodComponent;
import com.sstu.practic.spring.components.processingMethod.ListProcessingMethodsComponents;
import com.sstu.practic.spring.components.user.ListUserComponent;
import com.sstu.practic.spring.data.model.*;
import com.sstu.practic.spring.data.repositories.UserRepository;
import com.sstu.practic.spring.services.*;
import com.sstu.practic.spring.services.security.SecurityContext;
import com.sstu.practic.spring.services.security.entites.Role;
import com.sstu.practic.spring.utils.StageHolder;
import com.sstu.practic.spring.utils.entities.EventPair;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileInputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@JavaFxComponent(path = "/view/editExperiment.fxml")
public class EditExperimentComponent extends FxComponent {
    @Autowired
    private StageHolder stageHolder;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SecurityContext securityContext;
    @Autowired
    private ListExperimentComponent listExperimentComponent;
    @Autowired
    private ExperimentSubjectService experimentSubjectService;
    @Autowired
    private ExperimentTypeService experimentTypeService;
    @Autowired
    private ExperimentDesignService experimentDesignService;
    @Autowired
    private MoodService moodService;
    @Autowired
    private ExperimentService experimentService;
    @Autowired
    private EditExperimentComponent editExperimentComponent;
    @Autowired
    private EditProcessingMethodComponent editProcessingMethodComponent;
    @Autowired
    private ListProcessingMethodsComponents listProcessingMethodsComponents;
    @Autowired
    private ListUserComponent listUserComponent;
    @Autowired
    private ListArrangementComponent listArrangementComponent;
    @Autowired
    private ListDesignComponent listDesignComponent;
    @Autowired
    private ListExperimentSubject listExperimentSubject;
    @Autowired
    private ListExperimentTypeComponent listExperimentTypeComponent;
    @Autowired
    private FavoriteExperimentService favoriteExperimentService;
    @Autowired
    private ListMyExperimentComponent listMyExperimentComponent;
    @Autowired
    private ListFavoriteExperimentComponent listFavoriteExperimentComponent;
    @Autowired
    private MainComponent mainComponent;
    @Autowired
    private UserService userService;


    private MultipleSelectionModel<TbUser> usersSelectionModel;



    private byte[] byteAgreement;
    private byte[] byteProtocol1;
    private byte[] byteProtocol2;
    private byte[] byteProtocol3;


    private TbExperiment tbExperiment;

    public Scene getScene(TbExperiment tbExperiment) {
        this.tbExperiment = tbExperiment;
        return scene;
    }


    public void setTextField(TbExperiment tbExperiment) {
        DatePicker date = (DatePicker) scene.lookup("#date");
        TextArea experimentComments = (TextArea) scene.lookup("#experimentComments");
        TextField subjectWeight = (TextField) scene.lookup("#subjectWeight");
        TextField recordDuration = (TextField) scene.lookup("#recordDuration");
        Slider motivationLevel = (Slider) scene.lookup("#motivationLevel");
        Slider restLevel = (Slider) scene.lookup("#restLevel");
        ListView userGroup = (ListView) scene.lookup("#userGroup");


        LocalDate dateTime = tbExperiment.getVcDate();
        motivationLevel.setValue(tbExperiment.getVcMotivationLevel());
        restLevel.setValue(tbExperiment.getVcRestLevel());
        date.setValue(dateTime);
        experimentComments.setText(tbExperiment.getVcDescription());
        subjectWeight.setText(tbExperiment.getVcSubjectWeight());
        recordDuration.setText(tbExperiment.getVcRecordDuration());

    }



    @HandleEvent(nodeName = "buttonEditExperiment")
    public EventPair eventHandler() {
        EventPair pair = new EventPair();

        EventHandler eventHandler = (x) -> {
            Stage stage = stageHolder.getStage();

            LocalDate date = ((DatePicker) scene.lookup("#date")).getValue();
            String experimentComments = ((TextArea) scene.lookup("#experimentComments")).getText();
            String subjectWeight = ( (TextField) scene.lookup("#subjectWeight")).getText();
            String recordDuration = ((TextField) scene.lookup("#recordDuration")).getText();


            String experimentType = (String)((ChoiceBox) scene.lookup("#experimentType")).getValue();
            String experimentSubject = (String)((ChoiceBox) scene.lookup("#experimentSubject")).getValue();
            String experimentDesign = (String)((ChoiceBox) scene.lookup("#experimentDesign")).getValue();
            String mood = (String)((ChoiceBox) scene.lookup("#mood")).getValue();

            Double motivationLevel = ((Slider) scene.lookup("#motivationLevel")).getValue();
            Double restLevel = ((Slider) scene.lookup("#restLevel")).getValue();

            tbExperiment.setVcMotivationLevel(motivationLevel);
            tbExperiment.setVcRestLevel(restLevel);
            tbExperiment.setVcDate(date);
            tbExperiment.setVcDescription(experimentComments);
            tbExperiment.setVcSubjectWeight(subjectWeight);
            tbExperiment.setVcRecordDuration(recordDuration);
            tbExperiment.setVcExperimentType(experimentType);
            tbExperiment.setVcExperimentSubject(experimentSubject);
            tbExperiment.setVcExperimentDesign(experimentDesign);
            tbExperiment.setVcMood(mood);

            tbExperiment.setVcAgreement(byteAgreement);
            tbExperiment.setVcProtocol1(byteProtocol1);
            tbExperiment.setVcProtocol2(byteProtocol2);
            tbExperiment.setVcProtocol3(byteProtocol3);




            experimentService.updateExperiment(tbExperiment);

            stage.setScene(listExperimentComponent.getScene());
            stage.show();
        };

        pair.setEventHandler(eventHandler);
        pair.setEventType(MouseEvent.MOUSE_CLICKED);

        return pair;
    }


    @HandleEvent(nodeName = "agreement")
    public EventPair uploadAgreement(){
        FileChooser fileChooser = new FileChooser();

        EventHandler eventHandler = new EventHandler() {
            @Override
            public void handle(Event event) {
                File files = fileChooser.showOpenDialog(stageHolder.getStage());
                byte[] bytes;

                try(FileInputStream fileInputStream = new FileInputStream(files)) {
                    bytes = new byte[fileInputStream.available()];

                    fileInputStream.read(bytes);
                    fileInputStream.close();

                    byteAgreement = bytes;


                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        };

        return new EventPair(MouseEvent.MOUSE_CLICKED, eventHandler);
    }


    @HandleEvent(nodeName = "protocol1")
    public EventPair uploadProtocol1(){
        FileChooser fileChooser = new FileChooser();

        EventHandler eventHandler = new EventHandler() {
            @Override
            public void handle(Event event) {
                File files = fileChooser.showOpenDialog(stageHolder.getStage());
                byte[] bytes;

                try(FileInputStream fileInputStream = new FileInputStream(files)) {
                    bytes = new byte[fileInputStream.available()];

                    fileInputStream.read(bytes);
                    fileInputStream.close();

                    byteProtocol1 = bytes;

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        };

        return new EventPair(MouseEvent.MOUSE_CLICKED, eventHandler);
    }

    @HandleEvent(nodeName = "protocol2")
    public EventPair uploadProtocol2(){
        FileChooser fileChooser = new FileChooser();

        EventHandler eventHandler = new EventHandler() {
            @Override
            public void handle(Event event) {
                File files = fileChooser.showOpenDialog(stageHolder.getStage());
                byte[] bytes;

                try(FileInputStream fileInputStream = new FileInputStream(files)) {
                    bytes = new byte[fileInputStream.available()];

                    fileInputStream.read(bytes);
                    fileInputStream.close();

                    byteProtocol2 = bytes;

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        };

        return new EventPair(MouseEvent.MOUSE_CLICKED, eventHandler);
    }


    @HandleEvent(nodeName = "protocol3")
    public EventPair uploadProtocol3(){
        FileChooser fileChooser = new FileChooser();

        EventHandler eventHandler = new EventHandler() {
            @Override
            public void handle(Event event) {
                File files = fileChooser.showOpenDialog(stageHolder.getStage());
                byte[] bytes;

                try(FileInputStream fileInputStream = new FileInputStream(files)) {
                    bytes = new byte[fileInputStream.available()];

                    fileInputStream.read(bytes);
                    fileInputStream.close();

                    byteProtocol3 = bytes;

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        };

        return new EventPair(MouseEvent.MOUSE_CLICKED, eventHandler);
    }



    private ObservableList<TbExperimentType> getExperimentTypeList() {
        List<TbExperimentType> experimentTypes = experimentTypeService.getExperimentType();
        ObservableList<TbExperimentType> list = FXCollections.observableArrayList(experimentTypes);
        return list;
    }


    private ObservableList<TbExperimentSubject> getExperimentSubjectList() {
        List<TbExperimentSubject> subjects = experimentSubjectService.getAllUSubject();
        ObservableList<TbExperimentSubject> list = FXCollections.observableArrayList(subjects);
        return list;
    }

    private ObservableList<TbExperimentDesign> getExperimentDesignList() {
        List<TbExperimentDesign> experimentDesigns= experimentDesignService.getAllExperimentDesign();
        ObservableList<TbExperimentDesign> list = FXCollections.observableArrayList(experimentDesigns);
        return list;
    }

    private ObservableList<TbMood> getMoodList() {
        List<TbMood> moods = moodService.getAllMoods();
        ObservableList<TbMood> list = FXCollections.observableArrayList(moods);
        return list;
    }

    private ObservableList<TbUser> getUserList() {
        List<TbUser> users = userService.getAllUsers();
        ObservableList<TbUser> list = FXCollections.observableArrayList(users);
        return list;
    }

    @PostConstruct
    public void init(){
        ChoiceBox choiceBox =(ChoiceBox) scene.lookup("#experimentType");
        ObservableList<TbExperimentType> experimentTypeList = getExperimentTypeList();
        List<String> fieldNameList = experimentTypeList.stream().map(urEntity -> urEntity.getVcName()).collect(Collectors.toList());
        checkedIsEmptyList(fieldNameList,choiceBox);


        ChoiceBox choiceBox2 =(ChoiceBox) scene.lookup("#experimentSubject");
        ObservableList<TbExperimentSubject> experimentSubjects = getExperimentSubjectList();
        List<String> fieldNameList2 = experimentSubjects.stream().map(urEntity -> urEntity.getVcFirstName()).collect(Collectors.toList());
        checkedIsEmptyList(fieldNameList2,choiceBox2);


        ChoiceBox choiceBox3 =(ChoiceBox) scene.lookup("#experimentDesign");
        ObservableList<TbExperimentDesign> experimentDesigns = getExperimentDesignList();
        List<String> fieldNameList3 = experimentDesigns.stream().map(urEntity -> urEntity.getVcName()).collect(Collectors.toList());
        checkedIsEmptyList(fieldNameList3, choiceBox3);


        ChoiceBox choiceBox4 =(ChoiceBox) scene.lookup("#mood");
        ObservableList<TbMood> moods = getMoodList();
        List<String> fieldNameList4 = moods.stream().map(urEntity -> urEntity.getVcName()).collect(Collectors.toList());
        checkedIsEmptyList(fieldNameList4, choiceBox4);

//        ListView userGroup = (ListView) scene.lookup("#userGroup");
//        ObservableList<TbUser> users = getUserList();
//        userGroup.setItems(users);
//
//        usersSelectionModel = userGroup.getSelectionModel();
//        usersSelectionModel.setSelectionMode(SelectionMode.MULTIPLE);
//
//        usersSelectionModel.selectedItemProperty().addListener(new ChangeListener(){
//            @Override
//            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
//
//            }
//        });


    }


    private Node checkedIsEmptyList(List list, ChoiceBox choiceBox){
        List<String> listEmpty= new ArrayList<>();
        listEmpty.add("отсутствуют значения");

        if (list.isEmpty()) {
            choiceBox.setItems(FXCollections.observableArrayList((listEmpty)));
            choiceBox.setValue(listEmpty.get(0));
        } else {
            choiceBox.setItems(FXCollections.observableArrayList((list)));
            choiceBox.setValue(list.get(0));
        }
        return choiceBox;
    }






    //navigation

    @HandleEvent(nodeName = "buttonMain")
    public EventPair transitionToMain(){
        EventPair pair = new EventPair();

        EventHandler eventHandler = (x) -> {
            Stage stage = stageHolder.getStage();


            stage.setScene(mainComponent.getScene());
            stage.show();
        };

        pair.setEventHandler(eventHandler);
        pair.setEventType(MouseEvent.MOUSE_CLICKED);

        return pair;
    }


    @HandleEvent(nodeName = "buttonListUsers")
    public EventPair transitionToUsers(){
        EventPair pair = new EventPair();
        EventHandler eventHandler = (x) -> {
            Stage stage = stageHolder.getStage();


            stage.setScene(listUserComponent.getScene());
            stage.show();
        };


        pair.setEventHandler(eventHandler);
        pair.setEventType(MouseEvent.MOUSE_CLICKED);

        return pair;
    }

    @HandleEvent(nodeName = "buttonListExperimentSubjects")
    public EventPair transitionToExperimentSubjects(){
        EventPair pair = new EventPair();

        EventHandler eventHandler = (x) -> {
            Stage stage = stageHolder.getStage();


            stage.setScene(listExperimentSubject.getScene());
            stage.show();
        };

        pair.setEventHandler(eventHandler);
        pair.setEventType(MouseEvent.MOUSE_CLICKED);

        return pair;
    }

    @HandleEvent(nodeName = "buttonListExperiments")
    public EventPair eventPair3(){
        EventPair pair = new EventPair();

        EventHandler eventHandler = (x) -> {
            Stage stage = stageHolder.getStage();
            if(securityContext.getUser().getVcRole() == Role.ADMIN){
                stage.setScene(listExperimentComponent.getScene());
                stage.show();
            } else {
                stage.setScene(listMyExperimentComponent.getScene());
                stage.show();
            }
        };

        pair.setEventHandler(eventHandler);
        pair.setEventType(MouseEvent.MOUSE_CLICKED);

        return pair;
    }


    @HandleEvent(nodeName = "buttonExperimentType")
    public EventPair eventPair4(){
        EventPair pair = new EventPair();

        EventHandler eventHandler = (x) -> {
            Stage stage = stageHolder.getStage();


            stage.setScene(listExperimentTypeComponent.getScene());
            stage.show();
        };

        pair.setEventHandler(eventHandler);
        pair.setEventType(MouseEvent.MOUSE_CLICKED);

        return pair;
    }

    @HandleEvent(nodeName = "buttonExperimentDesign")
    public EventPair eventPair5(){
        EventPair pair = new EventPair();

        EventHandler eventHandler = (x) -> {
            Stage stage = stageHolder.getStage();

            stage.setScene(listDesignComponent.getScene());
            stage.show();
        };

        pair.setEventHandler(eventHandler);
        pair.setEventType(MouseEvent.MOUSE_CLICKED);

        return pair;
    }


    @HandleEvent(nodeName = "buttonMyExperiment")
    public EventPair transitionToMyExperiment(){
        EventPair pair = new EventPair();

        EventHandler eventHandler = (x) -> {
            Stage stage = stageHolder.getStage();


            stage.setScene(listMyExperimentComponent.getScene());
            stage.show();
        };

        pair.setEventHandler(eventHandler);
        pair.setEventType(MouseEvent.MOUSE_CLICKED);

        return pair;
    }

    @HandleEvent(nodeName = "buttonFavoriteExperiment")
    public EventPair transitionToFavoriteExperiment(){
        EventPair pair = new EventPair();

        EventHandler eventHandler = (x) -> {
            Stage stage = stageHolder.getStage();


            stage.setScene(listFavoriteExperimentComponent.getScene());
            stage.show();
        };

        pair.setEventHandler(eventHandler);
        pair.setEventType(MouseEvent.MOUSE_CLICKED);

        return pair;
    }
}
