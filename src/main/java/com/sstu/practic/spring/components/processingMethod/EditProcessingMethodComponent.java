package com.sstu.practic.spring.components.processingMethod;

import com.sstu.practic.spring.annotations.HandleEvent;
import com.sstu.practic.spring.annotations.JavaFxComponent;
import com.sstu.practic.spring.components.FxComponent;
import com.sstu.practic.spring.components.MainComponent;
import com.sstu.practic.spring.components.arrangement.ListArrangementComponent;
import com.sstu.practic.spring.components.channel.ListChannelComponent;
import com.sstu.practic.spring.components.design.ListDesignComponent;
import com.sstu.practic.spring.components.equipment.ListEquipmentsComponent;
import com.sstu.practic.spring.components.experimentSubject.ListExperimentSubject;
import com.sstu.practic.spring.components.mood.ListMoodComponent;
import com.sstu.practic.spring.components.user.ListUserComponent;
import com.sstu.practic.spring.data.model.TbMood;
import com.sstu.practic.spring.data.model.TbProcessingMethod;
import com.sstu.practic.spring.services.ProcessingMethodService;
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

@JavaFxComponent(path = "/view/editProcessingMethods.fxml")
public class EditProcessingMethodComponent extends FxComponent {
    @Autowired
    private MainComponent mainComponent;
    @Autowired
    private ProcessingMethodService processingMethodService;
    @Autowired
    private StageHolder stageHolder;
    @Autowired
    private SecurityContext securityContext;
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
    private ListEquipmentsComponent listEquipmentsComponent;
    @Autowired
    private ListChannelComponent listChannelComponent;
    @Autowired
    private ListMoodComponent listMoodComponent;

    private TbProcessingMethod tbProcessingMethod;

    public Scene getScene(TbProcessingMethod tbProcessingMethod) {
        this.tbProcessingMethod=tbProcessingMethod;
        return scene;
    }

    public void setTextField(TbProcessingMethod tbProcessingMethod) {
        TextField processingMethodName = (TextField) scene.lookup("#processingMethodName");
        TextArea processingMethodDescription = (TextArea) scene.lookup("#processingMethodDescription");

        processingMethodName.setText(tbProcessingMethod.getVcName());
        processingMethodDescription.setText(tbProcessingMethod.getVcDescription());
    }

    @HandleEvent(nodeName = "buttonEditProcessingMethod")
    public EventPair eventHandler() {
        EventPair pair = new EventPair();

        EventHandler eventHandler = (x) -> {
            Stage stage = stageHolder.getStage();

            String processingMethodName = ((TextField) scene.lookup("#processingMethodName")).getText();
            String processingMethodDescription = ((TextArea) scene.lookup("#processingMethodDescription")).getText();

            tbProcessingMethod.setVcName(processingMethodName);
            tbProcessingMethod.setVcDescription(processingMethodDescription);

            processingMethodService.updateProcessingMethod(tbProcessingMethod);

            stage.setScene(listProcessingMethodsComponents.getScene());
            stage.show();
        };

        pair.setEventHandler(eventHandler);
        pair.setEventType(MouseEvent.MOUSE_CLICKED);

        return pair;
    }





    //navigation
    @HandleEvent(nodeName = "buttonMain")
    public EventPair transitionToMain(){
        EventPair pairEquipment = new EventPair();

        EventHandler eventHandler = (x) -> {
            Stage stage = stageHolder.getStage();


            stage.setScene(mainComponent.getScene());
            stage.show();
        };

        pairEquipment.setEventHandler(eventHandler);
        pairEquipment.setEventType(MouseEvent.MOUSE_CLICKED);

        return pairEquipment;
    }

    @HandleEvent(nodeName = "buttonProcessingMethod")
    public EventPair transitionToProcessingMethod(){
        EventPair pairEquipment = new EventPair();

        EventHandler eventHandler = (x) -> {
            Stage stage = stageHolder.getStage();


            stage.setScene(listProcessingMethodsComponents.getScene());
            stage.show();
        };

        pairEquipment.setEventHandler(eventHandler);
        pairEquipment.setEventType(MouseEvent.MOUSE_CLICKED);

        return pairEquipment;
    }

    @HandleEvent(nodeName = "buttonEquipment")
    public EventPair transitionToEquipment(){
        EventPair pairEquipment = new EventPair();

        EventHandler eventHandler = (x) -> {
            Stage stage = stageHolder.getStage();


            stage.setScene(listEquipmentsComponent.getScene());
            stage.show();
        };

        pairEquipment.setEventHandler(eventHandler);
        pairEquipment.setEventType(MouseEvent.MOUSE_CLICKED);

        return pairEquipment;
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

            stage.setScene(listArrangementComponent.getScene());
            stage.show();
        };

        pair.setEventHandler(eventHandler);
        pair.setEventType(MouseEvent.MOUSE_CLICKED);

        return pair;
    }

    @HandleEvent(nodeName = "ExperimentSubject")
    public EventPair transitionToSubjects(){
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


            stage.setScene(listDesignComponent.getScene());
            stage.show();
        };

        pair.setEventHandler(eventHandler);
        pair.setEventType(MouseEvent.MOUSE_CLICKED);

        return pair;
    }
}
