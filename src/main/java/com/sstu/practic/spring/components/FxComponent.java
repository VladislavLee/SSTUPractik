package com.sstu.practic.spring.components;

import com.sstu.practic.spring.annotations.Authenticated;
import com.sstu.practic.spring.annotations.HandleEvent;
import com.sstu.practic.spring.components.user.CreateUserComponent;
import com.sstu.practic.spring.components.user.ListUserComponent;
import com.sstu.practic.spring.services.security.SecurityContext;
import com.sstu.practic.spring.services.security.entites.Role;
import com.sstu.practic.spring.utils.StageHolder;
import com.sstu.practic.spring.utils.entities.EventPair;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.Arrays;
import java.util.List;


public abstract class FxComponent {

    @Autowired
    private MainComponent mainComponent;
    @Autowired
    private SecurityContext securityContext;
    @Autowired
    private StageHolder stageHolder;
    @Autowired
    private CreateUserComponent createUserComponent;
    @Autowired
    private ListUserComponent listUserComponent;


    protected Scene scene;

    protected List<Role> getRole(){
        return Arrays.asList(Role.ADMIN,Role.USER, Role.EXPERIMENTATOR);
    }

    @Authenticated
    public Scene getScene() {
        return getRole().contains(securityContext.getUser().getVcRole()) ? scene : mainComponent.getScene();
    }



    public void setScene(Scene scene) {
        this.scene = scene;
    }

    public void injectEventHandler(EventType eventType, String nodeName, EventHandler eventHandler) {
        Node node = scene.lookup("#"+nodeName);
        node.addEventHandler(eventType, eventHandler);
    }



    @HandleEvent(nodeName = "buttonCreate")
    public EventPair eventPair(){
        EventPair pair = new EventPair();

        EventHandler eventHandler = (x) -> {
            Stage stage = stageHolder.getStage();

            stage.setScene(listUserComponent.getScene());
            stage.show();
        };


        pair.setEventHandler(eventHandler);
        pair.setEventType(MouseEvent.MOUSE_CLICKED);

        return pair;
    }


    @HandleEvent(nodeName = "buttonListExperiments")
    public EventPair ReventPair(){
        EventPair rpair = new EventPair();

        EventHandler eventHandler = (x) -> {
            Stage stage = stageHolder.getStage();


            stage.setScene(createUserComponent.getScene());
            stage.show();
        };

        rpair.setEventHandler(eventHandler);
        rpair.setEventType(MouseEvent.MOUSE_CLICKED);

        return rpair;
    }

}
