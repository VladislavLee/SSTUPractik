package com.sstu.practic.spring.components.auth;

import com.sstu.practic.spring.annotations.HandleEvent;
import com.sstu.practic.spring.annotations.JavaFxComponent;

import com.sstu.practic.spring.components.FxComponent;
import com.sstu.practic.spring.components.MainComponent;
import com.sstu.practic.spring.components.channel.CreateChannelComponent;
import com.sstu.practic.spring.components.user.CreateUserComponent;
import com.sstu.practic.spring.data.repositories.UserRepository;
import com.sstu.practic.spring.services.security.LoginService;
import com.sstu.practic.spring.utils.StageHolder;
import com.sstu.practic.spring.utils.entities.EventPair;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;



@JavaFxComponent(path = "/view/LoginForm.fxml")
public class LoginComponent extends FxComponent {

    @Autowired
    private LoginComponent loginComponent;
    @Autowired
    private StageHolder stageHolder;
    @Autowired
    private LoginService loginService;
    @Autowired
    private CreateUserComponent createUserComponent;
    @Autowired
    private CreateChannelComponent createChannelComponent;
    @Override
    public Scene getScene() {
        return scene;
    }



    @HandleEvent(nodeName = "saveUserEdit")
    public EventPair eventHandler() {
        EventPair pair = new EventPair();

        EventHandler eventHandler = (x) -> {
            Stage stage = stageHolder.getStage();

            TextField usernameTextField = (TextField) scene.lookup("#username");
            PasswordField passwordField = (PasswordField) scene.lookup("#password");

            if(loginService.login(usernameTextField.getText(),passwordField.getText())){
                stage.setScene(createChannelComponent.getScene());
                stage.show();
            }else {
                usernameTextField.setText("");
                passwordField.setText("");
            }

        };

        pair.setEventHandler(eventHandler);
        pair.setEventType(MouseEvent.MOUSE_CLICKED);

        return pair;
    }
}
