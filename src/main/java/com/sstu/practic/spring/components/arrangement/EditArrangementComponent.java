package com.sstu.practic.spring.components.arrangement;

import com.sstu.practic.spring.annotations.HandleEvent;
import com.sstu.practic.spring.annotations.JavaFxComponent;
import com.sstu.practic.spring.components.FxComponent;
import com.sstu.practic.spring.components.MainComponent;
import com.sstu.practic.spring.data.model.TbArrangements;
import com.sstu.practic.spring.data.model.TbEquipment;
import com.sstu.practic.spring.services.ArrangementService;
import com.sstu.practic.spring.services.EquipmentService;
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


    private TbArrangements tbArrangements;

    public Scene getScene(TbArrangements tbArrangements) {
        this.tbArrangements=tbArrangements;
        return scene;
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

            stage.setScene(mainComponent.getScene());
            stage.show();
        };

        pair.setEventHandler(eventHandler);
        pair.setEventType(MouseEvent.MOUSE_CLICKED);

        return pair;
    }
}
