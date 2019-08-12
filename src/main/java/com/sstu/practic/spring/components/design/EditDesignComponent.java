package com.sstu.practic.spring.components.design;

import com.sstu.practic.spring.annotations.HandleEvent;
import com.sstu.practic.spring.annotations.JavaFxComponent;
import com.sstu.practic.spring.components.FxComponent;
import com.sstu.practic.spring.components.MainComponent;
import com.sstu.practic.spring.data.model.TbArrangements;
import com.sstu.practic.spring.data.model.TbEquipment;
import com.sstu.practic.spring.data.model.TbExperimentDesign;
import com.sstu.practic.spring.services.ArrangementService;
import com.sstu.practic.spring.services.EquipmentService;
import com.sstu.practic.spring.services.ExperimentDesignService;
import com.sstu.practic.spring.services.security.SecurityContext;
import com.sstu.practic.spring.utils.StageHolder;
import com.sstu.practic.spring.utils.entities.EventPair;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Collectors;

@JavaFxComponent(path = "/view/editDesign.fxml")
public class EditDesignComponent extends FxComponent {
    @Autowired
    private MainComponent mainComponent;
    @Autowired
    private ExperimentDesignService experimentDesignService;
    @Autowired
    private StageHolder stageHolder;
    @Autowired
    private SecurityContext securityContext;
    @Autowired
    private ArrangementService arrangementService;

    private TbExperimentDesign tbExperimentDesign;


    public Scene getScene(TbExperimentDesign tbExperimentDesign) {
        this.tbExperimentDesign=tbExperimentDesign;
        return scene;
    }

    @HandleEvent(nodeName = "buttonEditDesign")
    public EventPair eventHandler() {
        EventPair pair = new EventPair();

        EventHandler eventHandler = (x) -> {
            Stage stage = stageHolder.getStage();

            String designName = ((TextField) scene.lookup("#designName")).getText();
            String designDescription = ((TextArea) scene.lookup("#designDescription")).getText();
            String designSampling = ((TextField) scene.lookup("#designSampling")).getText();
            String arrangement = (String)((ChoiceBox) scene.lookup("#designArrangement")).getValue();

            tbExperimentDesign.setVcName(designName);
            tbExperimentDesign.setVcDescription(designDescription);
            tbExperimentDesign.setVcSampling(designSampling);
            tbExperimentDesign.setVsArrangement(arrangement);

            experimentDesignService.updateExperimentDesign(tbExperimentDesign);

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
    }

}
