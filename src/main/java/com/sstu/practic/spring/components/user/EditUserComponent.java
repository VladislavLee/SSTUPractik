package com.sstu.practic.spring.components.user;

import com.sstu.practic.spring.annotations.HandleEvent;
import com.sstu.practic.spring.annotations.JavaFxComponent;
import com.sstu.practic.spring.components.FxComponent;
import com.sstu.practic.spring.components.MainComponent;
import com.sstu.practic.spring.data.model.TbChannels;
import com.sstu.practic.spring.data.model.TbMood;
import com.sstu.practic.spring.data.model.TbUser;
import com.sstu.practic.spring.services.ChannelService;
import com.sstu.practic.spring.services.MoodService;
import com.sstu.practic.spring.services.UserService;
import com.sstu.practic.spring.services.security.SecurityContext;
import com.sstu.practic.spring.utils.StageHolder;
import com.sstu.practic.spring.utils.entities.EventPair;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;

@JavaFxComponent(path = "/view/editUserForUser.fxml")
public class EditUserComponent extends FxComponent {
    @Autowired
    private MainComponent mainComponent;
    @Autowired
    private UserService userService;
    @Autowired
    private StageHolder stageHolder;
    @Autowired
    private SecurityContext securityContext;

    private TbUser tbUser;

    public Scene getScene(TbUser tbUser) {
        this.tbUser=tbUser;
        return scene;
    }

    @HandleEvent(nodeName = "buttonEditUser")
    public EventPair eventHandler() {
        EventPair pair = new EventPair();

        EventHandler eventHandler = (x) -> {
            Stage stage = stageHolder.getStage();

            String login = ((TextField) scene.lookup("#login")).getText();
            String password = ((PasswordField) scene.lookup("#password")).getText();
            String firstName = ((TextField) scene.lookup("#userFirstName")).getText();
            String secondName = ((TextField) scene.lookup("#userSecondName")).getText();
            String lastName = ((TextField) scene.lookup("#userLastName")).getText();
            String comments = ((TextArea) scene.lookup("#userComments")).getText();

            tbUser.setVcLogin(login);
            tbUser.setVcPassword(password);
            tbUser.setVcFirstName(firstName);
            tbUser.setVcSecondName(secondName);
            tbUser.setVcLastName(lastName);
            tbUser.setVcComments(comments);

            userService.updateUsers(tbUser);

            stage.setScene(mainComponent.getScene());
            stage.show();
        };

        pair.setEventHandler(eventHandler);
        pair.setEventType(MouseEvent.MOUSE_CLICKED);

        return pair;
    }



}
