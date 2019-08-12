package com.sstu.practic.spring.components.mood;

import com.sstu.practic.spring.annotations.HandleEvent;
import com.sstu.practic.spring.annotations.JavaFxComponent;
import com.sstu.practic.spring.components.FxComponent;
import com.sstu.practic.spring.components.MainComponent;
import com.sstu.practic.spring.data.model.TbMood;
import com.sstu.practic.spring.services.MoodService;
import com.sstu.practic.spring.services.security.SecurityContext;
import com.sstu.practic.spring.utils.StageHolder;
import com.sstu.practic.spring.utils.entities.EventPair;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.Optional;

@JavaFxComponent(path = "/view/editMoods.fxml")
public class EditMoodComponent extends FxComponent {
    @Autowired
    private MainComponent mainComponent;
    @Autowired
    private MoodService moodService;
    @Autowired
    private StageHolder stageHolder;



    public void setTextField(TbMood tbMood) {
        TextField moodName = (TextField) scene.lookup("#moodName");
        TextArea moodDescription = (TextArea) scene.lookup("#moodDescription");

        moodName.setText(tbMood.getVcName());
        moodDescription.setText(tbMood.getVcDescription());
    }

    private TbMood tbMood;

    public Scene getScene(TbMood tbMood) {
        this.tbMood=tbMood;
        return scene;
    }


    @HandleEvent(nodeName = "buttonEditMood")
    public EventPair eventHandler() {
        EventPair pair = new EventPair();

        EventHandler eventHandler = (x) -> {
            Stage stage = stageHolder.getStage();


            String moodName = ((TextField) scene.lookup("#moodName")).getText();
            String moodDescription = ((TextArea) scene.lookup("#moodDescription")).getText();

            tbMood.setVcName(moodName);
            tbMood.setVcDescription(moodDescription);

            moodService.updateMoods(tbMood);

            stage.setScene(mainComponent.getScene());
            stage.show();
        };

        pair.setEventHandler(eventHandler);
        pair.setEventType(MouseEvent.MOUSE_CLICKED);

        return pair;
    }




}
