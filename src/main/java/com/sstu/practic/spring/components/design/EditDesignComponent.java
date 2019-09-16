package com.sstu.practic.spring.components.design;

import com.sstu.practic.spring.annotations.HandleEvent;
import com.sstu.practic.spring.annotations.JavaFxComponent;
import com.sstu.practic.spring.components.FxComponent;
import com.sstu.practic.spring.components.MainComponent;
import com.sstu.practic.spring.components.arrangement.ListArrangementComponent;
import com.sstu.practic.spring.components.equipment.ListEquipmentsComponent;
import com.sstu.practic.spring.components.experiment.ListExperimentComponent;
import com.sstu.practic.spring.components.experiment.ListFavoriteExperimentComponent;
import com.sstu.practic.spring.components.experiment.ListMyExperimentComponent;
import com.sstu.practic.spring.components.experimentSubject.ListExperimentSubject;
import com.sstu.practic.spring.components.experimentType.ListExperimentTypeComponent;
import com.sstu.practic.spring.components.mood.ListMoodComponent;
import com.sstu.practic.spring.components.user.ListUserComponent;
import com.sstu.practic.spring.data.model.TbArrangements;
import com.sstu.practic.spring.data.model.TbExperimentDesign;
import com.sstu.practic.spring.services.ArrangementService;
import com.sstu.practic.spring.services.ExperimentDesignService;
import com.sstu.practic.spring.services.security.SecurityContext;
import com.sstu.practic.spring.services.security.entites.Role;
import com.sstu.practic.spring.utils.StageHolder;
import com.sstu.practic.spring.utils.entities.EventPair;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@JavaFxComponent(path = "/view/editDesign.fxml")
public class EditDesignComponent extends FxComponent {
    @Autowired
    private ExperimentDesignService experimentDesignService;
    @Autowired
    private StageHolder stageHolder;
    @Autowired
    private SecurityContext securityContext;
    @Autowired
    private ArrangementService arrangementService;
    @Autowired
    private MainComponent mainComponent;
    @Autowired
    private EditDesignComponent editDesignComponent;
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
    private ListExperimentComponent listExperimentComponent;
    @Autowired
    private ListEquipmentsComponent listEquipmentsComponent;
    @Autowired
    private ListMoodComponent listMoodComponent;
    @Autowired
    private ListMyExperimentComponent listMyExperimentComponent;
    @Autowired
    private ListFavoriteExperimentComponent listFavoriteExperimentComponent;

    private TbExperimentDesign tbExperimentDesign;


    public Scene getScene(TbExperimentDesign tbExperimentDesign) {
        this.tbExperimentDesign=tbExperimentDesign;
        return scene;
    }


    public void setTextField(TbExperimentDesign tbExperimentDesign) {
        TextField designName = (TextField) scene.lookup("#designName");
        TextArea designDescription = (TextArea) scene.lookup("#designDescription");
        TextField designSampling = (TextField) scene.lookup("#designSampling");



        designName.setText(tbExperimentDesign.getVcName());
        designDescription.setText(tbExperimentDesign.getVcDescription());
        designSampling.setText(tbExperimentDesign.getVcSampling());

    }

    @HandleEvent(nodeName = "buttonEditDesign")
    public EventPair eventHandler() {
        EventPair pair = new EventPair();

        EventHandler eventHandler = (x) -> {
            Stage stage = stageHolder.getStage();

            String designName = ((TextField) scene.lookup("#designName")).getText();
            String designDescription = ((TextArea) scene.lookup("#designDescription")).getText();
            String designSampling = ((TextField) scene.lookup("#designSampling")).getText();
            String arrangement = (String)((ChoiceBox) scene.lookup("#designArrangement")).getValue();

            tbExperimentDesign.setVcName(designName);
            tbExperimentDesign.setVcDescription(designDescription);
            tbExperimentDesign.setVcSampling(designSampling);
            tbExperimentDesign.setVsArrangement(arrangement);

            experimentDesignService.updateExperimentDesign(tbExperimentDesign);

            stage.setScene(listDesignComponent.getScene());
            stage.show();
        };

        pair.setEventHandler(eventHandler);
        pair.setEventType(MouseEvent.MOUSE_CLICKED);

        return pair;
    }


    private ObservableList<TbArrangements> getList() {
        List<TbArrangements> arrangements = arrangementService.getAllArrangements();
        ObservableList<TbArrangements> list = FXCollections.observableArrayList(arrangements);
        return list;
    }



    @PostConstruct
    public void init(){
        ChoiceBox choiceBox =(ChoiceBox) scene.lookup("#designArrangement");
        ObservableList<TbArrangements> lister = getList();
        List<String> fieldNameList = lister.stream().map(urEntity -> urEntity.getVcName()).collect(Collectors.toList());
        checkedIsEmptyList(fieldNameList,choiceBox);
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







    //    navigation

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

    @HandleEvent(nodeName = "buttonExperiment")
    public EventPair transitionToSubjects(){
        EventPair pair = new EventPair();

        EventHandler eventHandler = (x) -> {
            Stage stage = stageHolder.getStage();


            stage.setScene(listExperimentComponent.getScene());
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

    @HandleEvent(nodeName = "buttonExperimentType")
    public EventPair transitionToExperimentType(){
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


}
