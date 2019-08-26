package com.sstu.practic.spring.components.experimentSubject;

import com.sstu.practic.spring.annotations.HandleEvent;
import com.sstu.practic.spring.annotations.JavaFxComponent;
import com.sstu.practic.spring.components.FxComponent;
import com.sstu.practic.spring.components.MainComponent;
import com.sstu.practic.spring.components.arrangement.ListArrangementComponent;
import com.sstu.practic.spring.components.channel.ListChannelComponent;
import com.sstu.practic.spring.components.design.ListDesignComponent;
import com.sstu.practic.spring.components.equipment.ListEquipmentsComponent;
import com.sstu.practic.spring.components.experiment.ListFavoriteExperimentComponent;
import com.sstu.practic.spring.components.experiment.ListMyExperimentComponent;
import com.sstu.practic.spring.components.experimentType.ListExperimentTypeComponent;
import com.sstu.practic.spring.components.processingMethod.ListProcessingMethodsComponents;
import com.sstu.practic.spring.components.user.ListUserComponent;
import com.sstu.practic.spring.data.model.TbExperiment;
import com.sstu.practic.spring.data.model.TbExperimentSubject;
import com.sstu.practic.spring.data.repositories.UserRepository;
import com.sstu.practic.spring.services.ExperimentSubjectService;
import com.sstu.practic.spring.services.FavoriteExperimentService;
import com.sstu.practic.spring.services.security.SecurityContext;
import com.sstu.practic.spring.utils.StageHolder;
import com.sstu.practic.spring.utils.entities.EventPair;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@JavaFxComponent(path = "/view/editSubject.fxml")
public class EditExperimentSubjectComponent extends FxComponent {
    @Autowired
    private StageHolder stageHolder;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SecurityContext securityContext;
    @Autowired
    private MainComponent mainComponent;
    @Autowired
    private ExperimentSubjectService experimentSubjectService;
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
    private ListEquipmentsComponent listEquipmentsComponent;
    @Autowired
    private ListChannelComponent listChannelComponent;

    private TbExperimentSubject tbExperimentSubject;

    public Scene getScene(TbExperimentSubject tbExperimentSubject) {
        this.tbExperimentSubject = tbExperimentSubject;
        return scene;
    }

    public void setTextField(TbExperimentSubject tbExperimentSubject) {
        DatePicker subjectBirthday = (DatePicker) scene.lookup("#subjectBirthday");
        TextArea subjectComments = (TextArea) scene.lookup("#subjectComments");
        TextField subjectFirstName  = (TextField) scene.lookup("#subjectFirstName");
        TextField subjectSecondName  = (TextField) scene.lookup("#subjectSecondName");
        TextField subjectLastName  = (TextField) scene.lookup("#subjectLastName");


        LocalDate dateTime = tbExperimentSubject.getVcBirthday();
        subjectBirthday.setValue(dateTime);
        subjectFirstName.setText(tbExperimentSubject.getVcFirstName());
        subjectSecondName.setText(tbExperimentSubject.getVcSecondName());
        subjectLastName.setText(tbExperimentSubject.getVcLastName());
        subjectComments.setText(tbExperimentSubject.getVcComments());
    }

    @HandleEvent(nodeName = "buttonEditSubject")
    public EventPair eventHandler() {
        EventPair pair = new EventPair();

        EventHandler eventHandler = (x) -> {
            Stage stage = stageHolder.getStage();

            String subjectFirstName = ((TextField) scene.lookup("#subjectFirstName")).getText();
            String subjectSecondName = ((TextField) scene.lookup("#subjectSecondName")).getText();
            String subjectLastName = ((TextField) scene.lookup("#subjectLastName")).getText();
            LocalDate subjectBirthday = ((DatePicker) scene.lookup("#subjectBirthday")).getValue();
            String subjectComments = ((TextArea) scene.lookup("#subjectComments")).getText();


            String gender = (String)((ChoiceBox) scene.lookup("#gender")).getValue();
            String hand = (String)((ChoiceBox) scene.lookup("#hand")).getValue();
            String leg = (String)((ChoiceBox) scene.lookup("#leg")).getValue();
            String eye = (String)((ChoiceBox) scene.lookup("#eye")).getValue();

            tbExperimentSubject.setVcFirstName(subjectFirstName);
            tbExperimentSubject.setVcSecondName(subjectSecondName);
            tbExperimentSubject.setVcLastName(subjectLastName);
            tbExperimentSubject.setVcBirthday(subjectBirthday);
            tbExperimentSubject.setVcComments(subjectComments);

            tbExperimentSubject.setVcGender(gender);
            tbExperimentSubject.setVcLeadingHand(hand);
            tbExperimentSubject.setVcLeadingLeg(leg);
            tbExperimentSubject.setVcLeadingEye(eye);

            experimentSubjectService.updateSubject(tbExperimentSubject);

            stage.setScene(mainComponent.getScene());
            stage.show();
        };

        pair.setEventHandler(eventHandler);
        pair.setEventType(MouseEvent.MOUSE_CLICKED);

        return pair;
    }

    @PostConstruct
    public void init(){
        ChoiceBox choiceBox =(ChoiceBox) scene.lookup("#gender");
        ObservableList<TbExperimentSubject> experimentSubjects = getExperimentSubjectList();
        List<String> fieldNameList = experimentSubjects.stream().map(urEntity -> urEntity.getVcGender()).collect(Collectors.toList());
        checkedIsEmptyList(fieldNameList,choiceBox);


        ChoiceBox choiceBox2 =(ChoiceBox) scene.lookup("#hand");
        ObservableList<TbExperimentSubject> experimentSubjects2 = getExperimentSubjectList();
        List<String> fieldNameList2 = experimentSubjects2.stream().map(urEntity -> urEntity.getVcLeadingHand()).collect(Collectors.toList());
        checkedIsEmptyList(fieldNameList2,choiceBox2);


        ChoiceBox choiceBox3 =(ChoiceBox) scene.lookup("#leg");
        ObservableList<TbExperimentSubject> experimentSubjects3 = getExperimentSubjectList();
        List<String> fieldNameList3 = experimentSubjects3.stream().map(urEntity -> urEntity.getVcLeadingLeg()).collect(Collectors.toList());
        checkedIsEmptyList(fieldNameList3,choiceBox3);


        ChoiceBox choiceBox4 =(ChoiceBox) scene.lookup("#eye");
        ObservableList<TbExperimentSubject> experimentSubjects4 = getExperimentSubjectList();
        List<String> fieldNameList4 = experimentSubjects4.stream().map(urEntity -> urEntity.getVcLeadingEye()).collect(Collectors.toList());
        checkedIsEmptyList(fieldNameList4,choiceBox4);
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

    private ObservableList<TbExperimentSubject> getExperimentSubjectList() {
        List<TbExperimentSubject> subjects = experimentSubjectService.getAllUSubject();
        ObservableList<TbExperimentSubject> list = FXCollections.observableArrayList(subjects);
        return list;
    }


    //main Menu


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
    public EventPair transitionToListExperiments(){
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


    @HandleEvent(nodeName = "buttonExperimentSubject")
    public EventPair transitionToExperimentSubject(){
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

    @HandleEvent(nodeName = "buttonEquipment")
    public EventPair transitionToEquipment(){
        EventPair pair = new EventPair();
        EventHandler eventHandler = (x) -> {
            Stage stage = stageHolder.getStage();
            stage.setScene(listEquipmentsComponent.getScene());
            stage.show();
        };
        pair.setEventHandler(eventHandler);
        pair.setEventType(MouseEvent.MOUSE_CLICKED);
        return pair;
    }

    @HandleEvent(nodeName = "buttonChannel")
    public EventPair transitionToChannel(){
        EventPair pair = new EventPair();
        EventHandler eventHandler = (x) -> {
            Stage stage = stageHolder.getStage();
            stage.setScene(listChannelComponent.getScene());
            stage.show();
        };
        pair.setEventHandler(eventHandler);
        pair.setEventType(MouseEvent.MOUSE_CLICKED);
        return pair;
    }

    @HandleEvent(nodeName = "buttonProcessingMethod")
    public EventPair transitionToProcessingMethod(){
        EventPair pair = new EventPair();
        EventHandler eventHandler = (x) -> {
            Stage stage = stageHolder.getStage();
            stage.setScene(listProcessingMethodsComponents.getScene());
            stage.show();
        };
        pair.setEventHandler(eventHandler);
        pair.setEventType(MouseEvent.MOUSE_CLICKED);
        return pair;
    }

    @HandleEvent(nodeName = "buttonArrangement")
    public EventPair transitionToArrangement(){
        EventPair pair = new EventPair();
        EventHandler eventHandler = (x) -> {
            Stage stage = stageHolder.getStage();
            stage.setScene(listArrangementComponent.getScene());
            stage.show();
        };
        pair.setEventHandler(eventHandler);
        pair.setEventType(MouseEvent.MOUSE_CLICKED);
        return pair;
    }






}
