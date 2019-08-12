package com.sstu.practic.spring.components.experimentType;


import com.sstu.practic.spring.annotations.HandleEvent;
import com.sstu.practic.spring.annotations.JavaFxComponent;
import com.sstu.practic.spring.components.FxComponent;
import com.sstu.practic.spring.components.MainComponent;
import com.sstu.practic.spring.data.model.TbExperimentType;
import com.sstu.practic.spring.data.model.TbMood;
import com.sstu.practic.spring.services.ExperimentTypeService;
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

@JavaFxComponent(path = "/view/editExperimentType.fxml")
public class EditExperimentTypeComponent extends FxComponent {
    @Autowired
    private MainComponent mainComponent;
    @Autowired
    private ExperimentTypeService experimentTypeService;
    @Autowired
    private StageHolder stageHolder;
    @Autowired
    private SecurityContext securityContext;
    @Autowired
    private ListExperimentTypeComponent listExperimentTypeComponent;

    private TbExperimentType tbExperimentType;

    public Scene getScene(TbExperimentType tbExperimentType) {
        this.tbExperimentType = tbExperimentType;
        return scene;
    }

    @HandleEvent(nodeName = "buttonEditExperimentType")
    public EventPair eventHandler() {
        EventPair pair = new EventPair();

        EventHandler eventHandler = (x) -> {
            Stage stage = stageHolder.getStage();

            String experimentTypeName = ((TextField) scene.lookup("#experimentTypeName")).getText();
            String experimentTypeDescription = ((TextArea) scene.lookup("#experimentTypeDescription")).getText();

            tbExperimentType.setVcName(experimentTypeName);
            tbExperimentType.setVcDescription(experimentTypeDescription);

            experimentTypeService.updateExperimentType(tbExperimentType);

            stage.setScene(listExperimentTypeComponent.getScene());
            stage.show();
        };

        pair.setEventHandler(eventHandler);
        pair.setEventType(MouseEvent.MOUSE_CLICKED);

        return pair;
    }
}
