package com.sstu.practic.spring.components.design;

import com.sstu.practic.spring.annotations.HandleEvent;
import com.sstu.practic.spring.annotations.JavaFxComponent;
import com.sstu.practic.spring.components.FxComponent;
import com.sstu.practic.spring.components.MainComponent;
import com.sstu.practic.spring.data.model.TbArrangements;
import com.sstu.practic.spring.data.model.TbExperimentDesign;
import com.sstu.practic.spring.services.ArrangementService;
import com.sstu.practic.spring.services.ExperimentDesignService;
import com.sstu.practic.spring.services.security.SecurityContext;
import com.sstu.practic.spring.utils.StageHolder;
import com.sstu.practic.spring.utils.entities.EventPair;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Collectors;

@JavaFxComponent(path = "/view/createDesign.fxml")
public class CreateDesignComponent extends FxComponent {
    @Autowired
    private StageHolder stageHolder;
    @Autowired
    private MainComponent mainComponent;
    @Autowired
    private SecurityContext securityContext;
    @Autowired
    private ArrangementService arrangementService;
    @Autowired
    private ExperimentDesignService experimentDesignService;

    @HandleEvent(nodeName = "buttonCreateDesign")
    public EventPair eventHandler() {
        EventPair pair = new EventPair();

        EventHandler eventHandler = (x) -> {
            Stage stage = stageHolder.getStage();

            String designName = ((TextField) scene.lookup("#designName")).getText();
            String designDescription = ((TextArea) scene.lookup("#designDescription")).getText();
            String designSampling = ( (TextField) scene.lookup("#designSampling")).getText();
            String arrangement = (String)((ChoiceBox) scene.lookup("#designArrangement")).getValue();

            experimentDesignService.addExperimentDesign(TbExperimentDesign.builder()
                    .vcName(designName)
                    .vcDescription(designDescription)
                    .vcSampling(designSampling)
                    .vsArrangement(arrangement)
                    .idUser(securityContext.getUser().getIdUser())
                    .build());

            stage.setScene(mainComponent.getScene());
            stage.show();
        };


        pair.setEventHandler(eventHandler);
        pair.setEventType(MouseEvent.MOUSE_CLICKED);

        return pair;
    }


    private ObservableList<TbArrangements> getList() {
        List<TbArrangements> arrangements = arrangementService.getAllArrangements();
        ObservableList<TbArrangements> list = FXCollections.observableArrayList(arrangements);
        return list;
    }

    @PostConstruct
    public void init(){
        ChoiceBox choiceBox =(ChoiceBox) scene.lookup("#designArrangement");
        ObservableList<TbArrangements> lister = getList();
        List<String> fieldNameList = lister.stream().map(urEntity -> urEntity.getVcName()).collect(Collectors.toList());
        choiceBox.setItems(FXCollections.observableArrayList((fieldNameList)));
        choiceBox.setValue("Оборудование 1");
    }
}
