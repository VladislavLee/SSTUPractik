package com.sstu.practic.spring.components.equipment;

import com.sstu.practic.spring.annotations.HandleEvent;
import com.sstu.practic.spring.annotations.JavaFxComponent;
import com.sstu.practic.spring.components.FxComponent;
import com.sstu.practic.spring.components.MainComponent;
import com.sstu.practic.spring.components.arrangement.ListArrangementComponent;
import com.sstu.practic.spring.components.channel.ListChannelComponent;
import com.sstu.practic.spring.components.design.ListDesignComponent;
import com.sstu.practic.spring.components.experimentSubject.ListExperimentSubject;
import com.sstu.practic.spring.components.mood.ListMoodComponent;
import com.sstu.practic.spring.components.processingMethod.ListProcessingMethodsComponents;
import com.sstu.practic.spring.components.user.ListUserComponent;
import com.sstu.practic.spring.data.model.TbEquipment;
import com.sstu.practic.spring.services.EquipmentService;
import com.sstu.practic.spring.services.security.SecurityContext;
import com.sstu.practic.spring.services.security.entites.Role;
import com.sstu.practic.spring.utils.StageHolder;
import com.sstu.practic.spring.utils.entities.EventPair;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.List;

@JavaFxComponent(path = "/view/editEquipment.fxml")
public class EditEquipmentComponent extends FxComponent {
    @Autowired
    private MainComponent mainComponent;
    @Autowired
    private EquipmentService equipmentService;
    @Autowired
    private StageHolder stageHolder;
    @Autowired
    private SecurityContext securityContext;
    @Autowired
    private ListEquipmentsComponent listEquipmentsComponent;
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
    private ListChannelComponent listChannelComponent;
    @Autowired
    private ListMoodComponent listMoodComponent;
    @Autowired
    private CreateEquipmentComponent createEquipmentComponent;


    private TbEquipment tbEquipment;

    public Scene getScene(TbEquipment tbEquipment) {
        this.tbEquipment=tbEquipment;
        return scene;
    }

    @Override
    public List<Role> getRole(){
        return Arrays.asList(Role.ADMIN);
    }

    public void setTextField(TbEquipment tbEquipment) {
        TextField equipmentName = (TextField) scene.lookup("#equipmentName");
        TextArea equipmentSpecification = (TextArea) scene.lookup("#equipmentSpecification");
        TextField certificateName = (TextField) scene.lookup("#certificateName");
        TextField certificateOutput = (TextField) scene.lookup("#certificateOutput");

        equipmentName.setText(tbEquipment.getVcName());
        equipmentSpecification.setText(tbEquipment.getVcDescription());
        certificateName.setText(tbEquipment.getVcCertificateName());
        certificateOutput.setText(tbEquipment.getVcCertificateOutput());
    }



    @HandleEvent(nodeName = "buttonEditEquipment")
    public EventPair eventHandler() {
        EventPair pair = new EventPair();

        EventHandler eventHandler = (x) -> {
            Stage stage = stageHolder.getStage();

            String equipmentName = ((TextField) scene.lookup("#equipmentName")).getText();
            String equipmentSpecification = ((TextArea) scene.lookup("#equipmentSpecification")).getText();
            String certificateName = ((TextField) scene.lookup("#certificateName")).getText();
            String certificateOutput = ((TextField) scene.lookup("#certificateOutput")).getText();

            tbEquipment.setVcName(equipmentName);
            tbEquipment.setVcDescription(equipmentSpecification);
            tbEquipment.setVcCertificateName(certificateName);
            tbEquipment.setVcCertificateOutput(certificateOutput);

            equipmentService.updateEquipments(tbEquipment);

            stage.setScene(listEquipmentsComponent.getScene());
            stage.show();
        };

        pair.setEventHandler(eventHandler);
        pair.setEventType(MouseEvent.MOUSE_CLICKED);

        return pair;
    }


    //navigation

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


//    @HandleEvent(nodeName = "buttonExperimentSubjects")
//    public EventPair transition1(){
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
//
//        pair.setEventHandler(eventHandler);
//        pair.setEventType(MouseEvent.MOUSE_CLICKED);
//
//        return pair;
//    }

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


}
