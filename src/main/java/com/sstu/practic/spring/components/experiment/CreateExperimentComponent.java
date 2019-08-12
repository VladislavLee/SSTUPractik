package com.sstu.practic.spring.components.experiment;

import com.sstu.practic.spring.annotations.HandleEvent;
import com.sstu.practic.spring.annotations.JavaFxComponent;
import com.sstu.practic.spring.components.FxComponent;
import com.sstu.practic.spring.components.MainComponent;
import com.sstu.practic.spring.data.model.*;
import com.sstu.practic.spring.data.repositories.UserRepository;
import com.sstu.practic.spring.services.*;
import com.sstu.practic.spring.services.security.SecurityContext;
import com.sstu.practic.spring.utils.StageHolder;
import com.sstu.practic.spring.utils.entities.EventPair;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileInputStream;
import java.util.List;
import java.util.stream.Collectors;

@JavaFxComponent(path = "/view/createExperiments.fxml")
public class CreateExperimentComponent extends FxComponent {
    @Autowired
    private StageHolder stageHolder;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SecurityContext securityContext;
    @Autowired
    private MainComponent mainComponent;
    @Autowired
    private ExperimentService experimentService;
    @Autowired
    private ExperimentSubjectService experimentSubjectService;
    @Autowired
    private ExperimentTypeService experimentTypeService;
    @Autowired
    private ExperimentDesignService experimentDesignService;
    @Autowired
    private MoodService moodService;


    private byte[] byteAgreement;

    private byte[] byteProtocol1;

    private byte[] byteProtocol2;

    private byte[] byteProtocol3;

    @HandleEvent(nodeName = "buttonCreateExperiment")
    public EventPair eventHandler() {
        EventPair pair = new EventPair();

        EventHandler eventHandler = (x) -> {
            Stage stage = stageHolder.getStage();

            String date = ((TextField) scene.lookup("#date")).getText();
            String experimentComments = ((TextArea) scene.lookup("#experimentComments")).getText();
            String subjectWeight = ( (TextField) scene.lookup("#subjectWeight")).getText();
            String recordDuration = ((TextField) scene.lookup("#recordDuration")).getText();

            String experimentType = (String)((ChoiceBox) scene.lookup("#experimentType")).getValue();
            String experimentSubject = (String)((ChoiceBox) scene.lookup("#experimentSubject")).getValue();
            String experimentDesign = (String)((ChoiceBox) scene.lookup("#experimentDesign")).getValue();
            String mood = (String)((ChoiceBox) scene.lookup("#mood")).getValue();

            experimentService.addExperiment(TbExperiment.builder()
                    .vcDate(date)
                    .vcSubjectWeight(subjectWeight)
                    .vcRecordDuration(recordDuration)
                    .vcDescription(experimentComments)
                    .vcExperimentType(experimentType)
                    .vcExperimentSubject(experimentSubject)
                    .vcExperimentDesign(experimentDesign)
                    .vcMood(mood)
                    .vcAgreement(byteAgreement)
                    .vcProtocol1(byteProtocol1)
                    .vcProtocol2(byteProtocol2)
                    .vcProtocol3(byteProtocol3)
                    .idUser(securityContext.getUser().getIdUser())
                    .build());


            stage.setScene(mainComponent.getScene());
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

                    System.out.println();
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

                    System.out.println();
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

                    System.out.println();
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

                    System.out.println();
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


    @PostConstruct
    public void init(){
        ChoiceBox choiceBox =(ChoiceBox) scene.lookup("#experimentType");
        ObservableList<TbExperimentType> experimentTypeList = getExperimentTypeList();
        List<String> fieldNameList = experimentTypeList.stream().map(urEntity -> urEntity.getVcName()).collect(Collectors.toList());
        choiceBox.setItems(FXCollections.observableArrayList((fieldNameList)));

        ChoiceBox choiceBox2 =(ChoiceBox) scene.lookup("#experimentSubject");
        ObservableList<TbExperimentSubject> experimentSubjects = getExperimentSubjectList();
        List<String> fieldNameList2 = experimentSubjects.stream().map(urEntity -> urEntity.getVcFirstName()).collect(Collectors.toList());
        choiceBox2.setItems(FXCollections.observableArrayList((fieldNameList2)));

        ChoiceBox choiceBox3 =(ChoiceBox) scene.lookup("#experimentDesign");
        ObservableList<TbExperimentDesign> experimentDesigns = getExperimentDesignList();
        List<String> fieldNameList3 = experimentDesigns.stream().map(urEntity -> urEntity.getVcName()).collect(Collectors.toList());
        choiceBox3.setItems(FXCollections.observableArrayList((fieldNameList3)));

        ChoiceBox choiceBox4 =(ChoiceBox) scene.lookup("#mood");
        ObservableList<TbMood> moods = getMoodList();
        List<String> fieldNameList4 = moods.stream().map(urEntity -> urEntity.getVcName()).collect(Collectors.toList());
        choiceBox4.setItems(FXCollections.observableArrayList((fieldNameList4)));
    }
}
