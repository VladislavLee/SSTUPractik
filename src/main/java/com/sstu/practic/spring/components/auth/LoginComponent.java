package com.sstu.practic.spring.components.auth;

import com.sstu.practic.spring.annotations.HandleEvent;
import com.sstu.practic.spring.annotations.JavaFxComponent;

import com.sstu.practic.spring.components.FxComponent;
import com.sstu.practic.spring.components.MainComponent;
import com.sstu.practic.spring.components.arrangement.CreateArrangementComponent;
import com.sstu.practic.spring.components.arrangement.ListArrangementComponent;
import com.sstu.practic.spring.components.channel.EditChannelComponent;
import com.sstu.practic.spring.components.channel.ListChannelComponent;
import com.sstu.practic.spring.components.design.CreateDesignComponent;
import com.sstu.practic.spring.components.design.ListDesignComponent;
import com.sstu.practic.spring.components.equipment.CreateEquipmentComponent;
import com.sstu.practic.spring.components.channel.CreateChannelComponent;
import com.sstu.practic.spring.components.equipment.ListEquipmentsComponent;
import com.sstu.practic.spring.components.experiment.CreateExperimentComponent;
import com.sstu.practic.spring.components.experiment.ListExperimentComponent;
import com.sstu.practic.spring.components.experiment.ListFavoriteExperimentComponent;
import com.sstu.practic.spring.components.experimentSubject.CreateExperimentSubjectComponent;
import com.sstu.practic.spring.components.experimentSubject.EditExperimentSubjectComponent;
import com.sstu.practic.spring.components.experimentSubject.ListExperimentSubject;
import com.sstu.practic.spring.components.experimentType.CreateExperimentTypeComponent;
import com.sstu.practic.spring.components.experimentType.ListExperimentTypeComponent;
import com.sstu.practic.spring.components.mood.CreateMoodComponent;
import com.sstu.practic.spring.components.mood.ListMoodComponent;
import com.sstu.practic.spring.components.processingMethod.CreateProcessingMethodComponent;
import com.sstu.practic.spring.components.processingMethod.ListProcessingMethodsComponents;
import com.sstu.practic.spring.components.user.CreateUserComponent;
import com.sstu.practic.spring.components.user.ListUserComponent;
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

@JavaFxComponent(path = "/view/login.fxml")
public class LoginComponent extends FxComponent {

    @Autowired
    private CreateProcessingMethodComponent createProcessingMethodComponent;
    @Autowired
    private MainComponent mainComponent;
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
    @Autowired
    private CreateEquipmentComponent createEquipmentComponent;
    @Autowired
    private ListUserComponent listUserComponent;
    @Autowired
    private CreateMoodComponent createMoodComponent;
    @Autowired
    private CreateExperimentTypeComponent createExperimentTypeComponent;
    @Autowired
    private CreateArrangementComponent createArrangementComponent;
    @Autowired
    private CreateExperimentSubjectComponent createExperimentSubjectComponent;
    @Autowired
    private ListExperimentSubject listExperimentSubject;
    @Autowired
    private ListMoodComponent listMoodComponent;
    @Autowired
    private ListExperimentTypeComponent listExperimentTypeComponent;
    @Autowired
    private ListEquipmentsComponent listEquipmentsComponent;
    @Autowired
    private EditChannelComponent editChannelComponent;
    @Autowired
    private ListChannelComponent listChannelComponent;
    @Autowired
    private ListProcessingMethodsComponents listProcessingMethodsComponents;
    @Autowired
    private ListArrangementComponent listArrangementComponent;
    @Autowired
    private CreateDesignComponent createDesignComponent;
    @Autowired
    private ListDesignComponent listDesignComponent;
    @Autowired
    private EditExperimentSubjectComponent editExperimentSubjectComponent;
    @Autowired
    private CreateExperimentComponent createExperimentComponent;
    @Autowired
    private ListExperimentComponent listExperimentComponent;
    @Autowired
    private ListFavoriteExperimentComponent listFavoriteExperimentComponent;
    @Override
    public Scene getScene() {
        return scene;
    }

    @HandleEvent(nodeName = "buttonLogin")
    public EventPair eventHandler() {
        EventPair pair = new EventPair();

        EventHandler eventHandler = (x) -> {
            Stage stage = stageHolder.getStage();

            TextField usernameTextField = (TextField) scene.lookup("#login");
            PasswordField passwordField = (PasswordField) scene.lookup("#password");

            if(loginService.login(usernameTextField.getText(),passwordField.getText())){
                stage.setScene(mainComponent.getScene());
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
