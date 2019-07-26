package com.sstu.practic.spring.components.user;


import com.sstu.practic.spring.annotations.HandleEvent;
import com.sstu.practic.spring.annotations.JavaFxComponent;
import com.sstu.practic.spring.components.FxComponent;
import com.sstu.practic.spring.components.MainComponent;
import com.sstu.practic.spring.data.model.TbUser;
import com.sstu.practic.spring.services.UserService;
import com.sstu.practic.spring.utils.StageHolder;
import com.sstu.practic.spring.utils.entities.EventPair;
import javafx.event.EventHandler;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;

@JavaFxComponent(path = "/view/createUser.fxml")
public class CreateUserComponent extends FxComponent {

    @Autowired
    private StageHolder stageHolder;
    @Autowired
    private UserService userService;
    @Autowired
    private MainComponent mainComponent;


    @HandleEvent(nodeName = "saveUserEdit")
    public EventPair eventHandler() {
        EventPair pair = new EventPair();

        EventHandler eventHandler = (x) -> {
            Stage stage = stageHolder.getStage();

            String login = ((TextField) scene.lookup("#login")).getText();
            String password = ((PasswordField) scene.lookup("#password")).getText();
            String firstName = ((TextField) scene.lookup("#userFirstName")).getText();
            String secondName = ((TextField) scene.lookup("#userSecondName")).getText();
            String lastName = ((TextField) scene.lookup("#userLastName")).getText();
            String comments = ((TextArea) scene.lookup("#aboutUser")).getText();

            userService.addUser(TbUser.builder()
                    .vcLogin(login)
                    .vcPassword(password)
                    .vcFirstName(firstName)
                    .vcSecondName(secondName)
                    .vcLastName(lastName)
                    .vcComments(comments)
                    .build());

            stage.setScene(mainComponent.getScene());
            stage.show();
        };

        pair.setEventHandler(eventHandler);
        pair.setEventType(MouseEvent.MOUSE_CLICKED);

        return pair;
    }
}
