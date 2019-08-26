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
import com.sstu.practic.spring.components.user.CreateUserComponent;
import com.sstu.practic.spring.components.user.ListUserComponent;
import com.sstu.practic.spring.data.model.TbEquipment;
import com.sstu.practic.spring.data.repositories.UserRepository;
import com.sstu.practic.spring.services.EquipmentService;
import com.sstu.practic.spring.services.security.SecurityContext;
import com.sstu.practic.spring.utils.StageHolder;
import com.sstu.practic.spring.utils.entities.EventPair;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.*;

@JavaFxComponent(path = "/view/createEquipment.fxml")
public class CreateEquipmentComponent extends FxComponent {
    @Autowired
    private StageHolder stageHolder;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SecurityContext securityContext;
    @Autowired
    private MainComponent mainComponent;
    @Autowired
    private CreateUserComponent createUserComponent;
    @Autowired
    private ListEquipmentsComponent listEquipmentsComponent;
    @Autowired
    private EquipmentService equipmentService;
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
    private String nameCertificate;

    private byte[] byteCertificate;


    @HandleEvent(nodeName = "buttonCreateEquipment")
    public EventPair eventPair(){
        EventPair pair = new EventPair();

        EventHandler eventHandler = (x) -> {
            Stage stage = stageHolder.getStage();

            String equipmentName = ((TextField) scene.lookup("#equipmentName")).getText();
            String equipmentSpecification = ((TextArea) scene.lookup("#equipmentSpecification")).getText();
            String certificateOutput = ((TextField) scene.lookup("#certificateOutput")).getText();


            equipmentService.addEquipment(TbEquipment.builder()
            .vcName(equipmentName)
            .vcDescription(equipmentSpecification)
            .vcCertificateName(nameCertificate)
            .vcCertificateOutput(certificateOutput)
            .idUser(securityContext.getUser().getIdUser())
            .vcCertificate(byteCertificate)
            .build());

            stage.setScene(listEquipmentsComponent.getScene());
            stage.show();
        };


        pair.setEventHandler(eventHandler);
        pair.setEventType(MouseEvent.MOUSE_CLICKED);

        return pair;
    }


    @HandleEvent(nodeName = "certificate")
    public EventPair uploadCertificate(){
        FileChooser fileChooser = new FileChooser();

        EventHandler eventHandler = new EventHandler() {
            @Override
            public void handle(Event event) {
                File files = fileChooser.showOpenDialog(stageHolder.getStage());
                byte[] bytes;

                try(FileInputStream fileInputStream = new FileInputStream(files)) {
                    bytes = new byte[fileInputStream.available()];

                    nameCertificate = files.getName();
                    fileInputStream.read(bytes);
                    fileInputStream.close();

                    byteCertificate = bytes;

                    System.out.println();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        };

        return new EventPair(MouseEvent.MOUSE_CLICKED, eventHandler);
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
