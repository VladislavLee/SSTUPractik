package com.sstu.practic.spring.components.processingMethod;

import com.sstu.practic.spring.annotations.HandleEvent;
import com.sstu.practic.spring.annotations.JavaFxComponent;
import com.sstu.practic.spring.components.FxComponent;
import com.sstu.practic.spring.components.MainComponent;
import com.sstu.practic.spring.data.model.TbMood;
import com.sstu.practic.spring.data.model.TbProcessingMethod;
import com.sstu.practic.spring.services.MoodService;
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


    private TbProcessingMethod tbProcessingMethod;

    public Scene getScene(TbProcessingMethod tbProcessingMethod) {
        this.tbProcessingMethod=tbProcessingMethod;
        return scene;
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

            stage.setScene(mainComponent.getScene());
            stage.show();
        };

        pair.setEventHandler(eventHandler);
        pair.setEventType(MouseEvent.MOUSE_CLICKED);

        return pair;
    }
}