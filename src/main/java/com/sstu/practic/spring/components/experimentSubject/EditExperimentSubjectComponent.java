package com.sstu.practic.spring.components.experimentSubject;

import com.sstu.practic.spring.annotations.HandleEvent;
import com.sstu.practic.spring.annotations.JavaFxComponent;
import com.sstu.practic.spring.components.FxComponent;
import com.sstu.practic.spring.components.MainComponent;
import com.sstu.practic.spring.data.model.TbExperimentSubject;
import com.sstu.practic.spring.data.repositories.UserRepository;
import com.sstu.practic.spring.services.ExperimentSubjectService;
import com.sstu.practic.spring.services.security.SecurityContext;
import com.sstu.practic.spring.utils.StageHolder;
import com.sstu.practic.spring.utils.entities.EventPair;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import javax.annotation.PostConstruct;


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

    private TbExperimentSubject tbExperimentSubject;

    public Scene getScene(TbExperimentSubject tbExperimentSubject) {
        this.tbExperimentSubject = tbExperimentSubject;
        return scene;
    }

    @HandleEvent(nodeName = "buttonEditSubject")
    public EventPair eventHandler() {
        EventPair pair = new EventPair();

        EventHandler eventHandler = (x) -> {
            Stage stage = stageHolder.getStage();

            String subjectFirstName = ((TextField) scene.lookup("#subjectFirstName")).getText();
            String subjectSecondName = ((TextField) scene.lookup("#subjectSecondName")).getText();
            String subjectLastName = ((TextField) scene.lookup("#subjectLastName")).getText();
            String subjectBirthday = ((TextField) scene.lookup("#subjectBirthday")).getText();
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
}