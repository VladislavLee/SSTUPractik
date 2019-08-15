package com.sstu.practic.spring.components.arrangement;

import com.sstu.practic.spring.annotations.HandleEvent;
import com.sstu.practic.spring.annotations.JavaFxComponent;
import com.sstu.practic.spring.components.FxComponent;
import com.sstu.practic.spring.components.MainComponent;
import com.sstu.practic.spring.components.user.CreateUserComponent;
import com.sstu.practic.spring.data.model.TbArrangements;
import com.sstu.practic.spring.data.model.TbEquipment;
import com.sstu.practic.spring.data.repositories.UserRepository;
import com.sstu.practic.spring.services.ArrangementService;
import com.sstu.practic.spring.services.EquipmentService;
import com.sstu.practic.spring.services.security.SecurityContext;
import com.sstu.practic.spring.utils.StageHolder;
import com.sstu.practic.spring.utils.entities.EventPair;
import javafx.event.EventHandler;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;

@JavaFxComponent(path = "/view/createArragement.fxml")
public class CreateArrangementComponent extends FxComponent {
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
    private ArrangementService arrangementService;


    @HandleEvent(nodeName = "buttonCreateArrangement")
    public EventPair eventPair(){
        EventPair pair = new EventPair();

        EventHandler eventHandler = (x) -> {
            Stage stage = stageHolder.getStage();

            String arrangementName = ((TextField) scene.lookup("#arrangementName")).getText();
            String aboutArrangement = ((TextArea) scene.lookup("#aboutArrangement")).getText();

            arrangementService.addArrangement(TbArrangements.builder()
                    .vcName(arrangementName)
                    .vcDescription(aboutArrangement)
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
