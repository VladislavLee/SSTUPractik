package com.sstu.practic.spring.components.equipment;

import com.sstu.practic.spring.annotations.HandleEvent;
import com.sstu.practic.spring.annotations.JavaFxComponent;
import com.sstu.practic.spring.components.FxComponent;
import com.sstu.practic.spring.components.MainComponent;
import com.sstu.practic.spring.components.user.CreateUserComponent;
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
    private EquipmentService equipmentService;

    private byte[] byteCertificate;


    @HandleEvent(nodeName = "buttonCreateEquipment")
    public EventPair eventPair(){
        EventPair pair = new EventPair();

        EventHandler eventHandler = (x) -> {
            Stage stage = stageHolder.getStage();

            String equipmentName = ((TextField) scene.lookup("#equipmentName")).getText();
            String equipmentSpecification = ((TextArea) scene.lookup("#equipmentSpecification")).getText();
            String certificateOutput = ((TextField) scene.lookup("#certificateOutput")).getText();
            String nameCertificate = ((TextField) scene.lookup("#nameCertificate")).getText();

            equipmentService.addEquipment(TbEquipment.builder()
            .vcName(equipmentName)
            .vcDescription(equipmentSpecification)
            .vcCertificateName(nameCertificate)
            .vcCertificateOutput(certificateOutput)
            .idUser(securityContext.getUser().getIdUser())
            .vcCertificate(byteCertificate)
            .build());

            stage.setScene(createUserComponent.getScene());
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
}
