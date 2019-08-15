package com.sstu.practic.spring.components.equipment;

import com.sstu.practic.spring.annotations.HandleEvent;
import com.sstu.practic.spring.annotations.JavaFxComponent;
import com.sstu.practic.spring.components.FxComponent;
import com.sstu.practic.spring.components.MainComponent;
import com.sstu.practic.spring.data.model.TbEquipment;
import com.sstu.practic.spring.data.model.TbExperiment;
import com.sstu.practic.spring.data.model.TbMood;
import com.sstu.practic.spring.services.EquipmentService;
import com.sstu.practic.spring.services.MoodService;
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


    private TbEquipment tbEquipment;

    public Scene getScene(TbEquipment tbEquipment) {
        this.tbEquipment=tbEquipment;
        return scene;
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

            stage.setScene(mainComponent.getScene());
            stage.show();
        };

        pair.setEventHandler(eventHandler);
        pair.setEventType(MouseEvent.MOUSE_CLICKED);

        return pair;
    }
}
