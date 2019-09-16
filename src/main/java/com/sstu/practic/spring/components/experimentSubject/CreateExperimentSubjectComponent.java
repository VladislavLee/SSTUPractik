package com.sstu.practic.spring.components.experimentSubject;

import com.sstu.practic.spring.annotations.HandleEvent;
import com.sstu.practic.spring.annotations.JavaFxComponent;
import com.sstu.practic.spring.components.FxComponent;
import com.sstu.practic.spring.components.MainComponent;
import com.sstu.practic.spring.components.arrangement.CreateArrangementComponent;
import com.sstu.practic.spring.components.arrangement.ListArrangementComponent;
import com.sstu.practic.spring.components.channel.CreateChannelComponent;
import com.sstu.practic.spring.components.channel.EditChannelComponent;
import com.sstu.practic.spring.components.channel.ListChannelComponent;
import com.sstu.practic.spring.components.design.CreateDesignComponent;
import com.sstu.practic.spring.components.design.ListDesignComponent;
import com.sstu.practic.spring.components.equipment.CreateEquipmentComponent;
import com.sstu.practic.spring.components.equipment.ListEquipmentsComponent;
import com.sstu.practic.spring.components.experiment.CreateExperimentComponent;
import com.sstu.practic.spring.components.experiment.ListExperimentComponent;
import com.sstu.practic.spring.components.experiment.ListFavoriteExperimentComponent;
import com.sstu.practic.spring.components.experiment.ListMyExperimentComponent;
import com.sstu.practic.spring.components.experimentType.CreateExperimentTypeComponent;
import com.sstu.practic.spring.components.experimentType.ListExperimentTypeComponent;
import com.sstu.practic.spring.components.mood.CreateMoodComponent;
import com.sstu.practic.spring.components.mood.ListMoodComponent;
import com.sstu.practic.spring.components.processingMethod.ListProcessingMethodsComponents;
import com.sstu.practic.spring.components.user.CreateUserComponent;
import com.sstu.practic.spring.components.user.ListUserComponent;
import com.sstu.practic.spring.data.model.TbExperimentSubject;
import com.sstu.practic.spring.data.repositories.UserRepository;
import com.sstu.practic.spring.services.ExperimentSubjectService;
import com.sstu.practic.spring.services.security.SecurityContext;
import com.sstu.practic.spring.services.security.entites.Role;
import com.sstu.practic.spring.utils.StageHolder;
import com.sstu.practic.spring.utils.entities.EventPair;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.time.LocalDate;


@JavaFxComponent(path = "/view/createSubject.fxml")
public class CreateExperimentSubjectComponent extends FxComponent {

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
    private CreateUserComponent createUserComponent;
    @Autowired
    private CreateChannelComponent createChannelComponent;
    @Autowired
    private CreateEquipmentComponent createEquipmentComponent;
    @Autowired
    private ListUserComponent listUserComponent;
    @Autowired
    private CreateMoodComponent createMoodComponent;
    @Autowired
    private CreateExperimentTypeComponent createExperimentTypeComponent;
    @Autowired
    private CreateArrangementComponent createArrangementComponent;
    @Autowired
    private CreateExperimentSubjectComponent createExperimentSubjectComponent;
    @Autowired
    private ListExperimentSubject listExperimentSubject;
    @Autowired
    private ListMoodComponent listMoodComponent;
    @Autowired
    private ListExperimentTypeComponent listExperimentTypeComponent;
    @Autowired
    private ListEquipmentsComponent listEquipmentsComponent;
    @Autowired
    private EditChannelComponent editChannelComponent;
    @Autowired
    private ListChannelComponent listChannelComponent;
    @Autowired
    private ListProcessingMethodsComponents listProcessingMethodsComponents;
    @Autowired
    private ListArrangementComponent listArrangementComponent;
    @Autowired
    private CreateDesignComponent createDesignComponent;
    @Autowired
    private ListDesignComponent listDesignComponent;
    @Autowired
    private EditExperimentSubjectComponent editExperimentSubjectComponent;
    @Autowired
    private CreateExperimentComponent createExperimentComponent;
    @Autowired
    private ListExperimentComponent listExperimentComponent;
    @Autowired
    private ListFavoriteExperimentComponent listFavoriteExperimentComponent;
    @Autowired
    private ListMyExperimentComponent listMyExperimentComponent;

    @HandleEvent(nodeName = "buttonCreateSubject")
    public EventPair eventPair(){
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

            experimentSubjectService.addExperimentSubject(TbExperimentSubject.builder()
                    .vcFirstName(subjectFirstName)
                    .vcSecondName(subjectSecondName)
                    .vcLastName(subjectLastName)
                    .vcBirthday(subjectBirthday)
                    .vcComments(subjectComments)
                    .vcGender(gender)
                    .vcLeadingHand(hand)
                    .vcLeadingLeg(leg)
                    .vcLeadingEye(eye)
                    .idUser(securityContext.getUser().getIdUser())
                    .build());

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
        ObservableList<String> listGender = FXCollections.observableArrayList("мужской", "женский", "не указан");
        choiceBox.setItems(FXCollections.observableArrayList((listGender)));
        choiceBox.setValue("не указан");


        ChoiceBox choiceBox2 =(ChoiceBox) scene.lookup("#hand");
        ObservableList<String> listHand= FXCollections.observableArrayList("правая", "левая");
        choiceBox2.setItems(FXCollections.observableArrayList((listHand)));
        choiceBox2.setValue("правая");


        ChoiceBox choiceBox3 =(ChoiceBox) scene.lookup("#leg");
        ObservableList<String> listLeg = FXCollections.observableArrayList("правая", "левая");
        choiceBox3.setItems(FXCollections.observableArrayList((listLeg)));
        choiceBox3.setValue("правая");


        ChoiceBox choiceBox4 =(ChoiceBox) scene.lookup("#eye");
        ObservableList<String> listEye= FXCollections.observableArrayList("правый", "левый");
        choiceBox4.setItems(FXCollections.observableArrayList((listEye)));
        choiceBox4.setValue("правый");
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
