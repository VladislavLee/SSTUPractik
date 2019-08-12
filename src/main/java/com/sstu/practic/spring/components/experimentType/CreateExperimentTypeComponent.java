package com.sstu.practic.spring.components.experimentType;

import com.sstu.practic.spring.annotations.HandleEvent;
import com.sstu.practic.spring.annotations.JavaFxComponent;
import com.sstu.practic.spring.components.FxComponent;
import com.sstu.practic.spring.components.MainComponent;
import com.sstu.practic.spring.data.model.TbExperimentType;
import com.sstu.practic.spring.data.model.TbMood;
import com.sstu.practic.spring.data.repositories.UserRepository;
import com.sstu.practic.spring.services.ExperimentTypeService;
import com.sstu.practic.spring.services.MoodService;
import com.sstu.practic.spring.services.security.SecurityContext;
import com.sstu.practic.spring.utils.StageHolder;
import com.sstu.practic.spring.utils.entities.EventPair;
import javafx.event.EventHandler;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;

@JavaFxComponent(path = "/view/createTypeExperiment.fxml")
public class CreateExperimentTypeComponent extends FxComponent {
    @Autowired
    private StageHolder stageHolder;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SecurityContext securityContext;
    @Autowired
    private MainComponent mainComponent;
    @Autowired
    private ExperimentTypeService experimentTypeService;



    @HandleEvent(nodeName = "buttonCreateExperimentType")
    public EventPair eventPair(){
        EventPair pair = new EventPair();

        EventHandler eventHandler = (x) -> {
            Stage stage = stageHolder.getStage();

            String experimentTypeName = ((TextField) scene.lookup("#experimentTypeName")).getText();
            String experimentTypeDescription = ((TextArea) scene.lookup("#experimentTypeDescription")).getText();


            experimentTypeService.addExperimentType(TbExperimentType.builder()
                    .vcName(experimentTypeName)
                    .vcDescription(experimentTypeDescription)
                    .idUser(securityContext.getUser().getIdUser())
                    .build());

            stage.setScene(mainComponent.getScene());
            stage.show();
        };


        pair.setEventHandler(eventHandler);
        pair.setEventType(MouseEvent.MOUSE_CLICKED);

        return pair;
    }
}
