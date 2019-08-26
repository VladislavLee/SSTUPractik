package com.sstu.practic.spring.components.arrangement;

import com.sstu.practic.spring.annotations.HandleEvent;
import com.sstu.practic.spring.annotations.JavaFxComponent;
import com.sstu.practic.spring.components.FxComponent;
import com.sstu.practic.spring.components.MainComponent;
import com.sstu.practic.spring.components.channel.ListChannelComponent;
import com.sstu.practic.spring.components.design.ListDesignComponent;
import com.sstu.practic.spring.components.equipment.ListEquipmentsComponent;
import com.sstu.practic.spring.components.experiment.ListExperimentComponent;
import com.sstu.practic.spring.components.experimentSubject.ListExperimentSubject;
import com.sstu.practic.spring.components.mood.ListMoodComponent;
import com.sstu.practic.spring.components.processingMethod.ListProcessingMethodsComponents;
import com.sstu.practic.spring.components.user.ListUserComponent;
import com.sstu.practic.spring.data.model.TbArrangements;
import com.sstu.practic.spring.data.model.TbChannels;
import com.sstu.practic.spring.services.ArrangementService;
import com.sstu.practic.spring.services.security.SecurityContext;
import com.sstu.practic.spring.utils.StageHolder;
import com.sstu.practic.spring.utils.entities.EventPair;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;

@JavaFxComponent(path = "/view/editArrangment.fxml")
public class EditArrangementComponent extends FxComponent {
    @Autowired
    private MainComponent mainComponent;
    @Autowired
    private ArrangementService arrangementService;
    @Autowired
    private StageHolder stageHolder;
    @Autowired
    private SecurityContext securityContext;
    @Autowired
    private ListArrangementComponent listArrangementComponent;
    @Autowired
    private ListChannelComponent listChannelComponent;
    @Autowired
    private EditArrangementComponent editArrangementComponent;
    @Autowired
    private ListProcessingMethodsComponents listProcessingMethodsComponents;
    @Autowired
    private ListUserComponent listUserComponent;
    @Autowired
    private ListExperimentComponent listExperimentComponent;
    @Autowired
    private ListExperimentSubject listExperimentSubject;
    @Autowired
    private ListEquipmentsComponent listEquipmentsComponent;
    @Autowired
    private ListMoodComponent listMoodComponent;
    private TbArrangements tbArrangements;

    public Scene getScene(TbArrangements tbArrangements) {
        this.tbArrangements=tbArrangements;
        return scene;
    }

    public void setTextField(TbArrangements tbArrangements) {
        TextField arrangementName = (TextField) scene.lookup("#arrangementName");
        TextArea arrangementDescription = (TextArea) scene.lookup("#arrangementDescription");

        arrangementName.setText(tbArrangements.getVcName());
        arrangementDescription.setText(tbArrangements.getVcDescription());
    }


    @HandleEvent(nodeName = "buttonEditArrangement")
    public EventPair eventHandler() {
        EventPair pair = new EventPair();

        EventHandler eventHandler = (x) -> {
            Stage stage = stageHolder.getStage();

            String arrangementName = ((TextField) scene.lookup("#arrangementName")).getText();
            String arrangementDescription = ((TextArea) scene.lookup("#arrangementDescription")).getText();


            tbArrangements.setVcName(arrangementName);
            tbArrangements.setVcDescription(arrangementDescription);


            arrangementService.updateArrangement(tbArrangements);

            stage.setScene(listArrangementComponent.getScene());
            stage.show();
        };

        pair.setEventHandler(eventHandler);
        pair.setEventType(MouseEvent.MOUSE_CLICKED);

        return pair;
    }





    //navigation


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


//    @HandleEvent(nodeName = "buttonExperimentSubjects")
//    public EventPair transitionToSubjects(){
//        EventPair pair = new EventPair();
//
//        EventHandler eventHandler = (x) -> {
//            Stage stage = stageHolder.getStage();
//
//
//            stage.setScene(listExperimentSubject.getScene());
//            stage.show();
//        };
//
//        pair.setEventHandler(eventHandler);
//        pair.setEventType(MouseEvent.MOUSE_CLICKED);
//
//        return pair;
//    }

    @HandleEvent(nodeName = "buttonListExperiments")
    public EventPair transitionToListExperiments(){
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

    @HandleEvent(nodeName = "buttonArrangement")
    public EventPair transitionToArrangement(){
        EventPair pair = new EventPair();

        EventHandler eventHandler = (x) -> {
            Stage stage = stageHolder.getStage();
            stage.setScene(listMoodComponent.getScene());
            stage.show();
        };

        pair.setEventHandler(eventHandler);
        pair.setEventType(MouseEvent.MOUSE_CLICKED);

        return pair;
    }



}
