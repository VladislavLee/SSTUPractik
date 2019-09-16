package com.sstu.practic.spring.components.user;

import com.sstu.practic.spring.annotations.HandleEvent;
import com.sstu.practic.spring.annotations.JavaFxComponent;
import com.sstu.practic.spring.components.FxComponent;
import com.sstu.practic.spring.components.MainComponent;
import com.sstu.practic.spring.components.experiment.ListExperimentComponent;
import com.sstu.practic.spring.components.experiment.ListMyExperimentComponent;
import com.sstu.practic.spring.components.experiment.ListMyExperimentForUserComponent;
import com.sstu.practic.spring.components.experimentSubject.ListExperimentSubject;
import com.sstu.practic.spring.data.model.TbProcessingMethod;
import com.sstu.practic.spring.data.model.TbUser;
import com.sstu.practic.spring.data.repositories.UserRepository;
import com.sstu.practic.spring.services.UserService;
import com.sstu.practic.spring.services.security.SecurityContext;
import com.sstu.practic.spring.services.security.entites.Role;
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

import java.util.Arrays;
import java.util.List;

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
    @Autowired
    private ListUserComponent listUserComponent;
    @Autowired
    private ListExperimentSubject listExperimentSubject;
    @Autowired
    private ListExperimentComponent listExperimentComponent;
    @Autowired
    private ListMyExperimentComponent listMyExperimentForUserComponent;

    private TbUser tbUser;


    public Scene getScene(TbUser tbUser) {
        this.tbUser = tbUser;
        return scene;
    }

    @Override
    public List<Role> getRole(){
        return Arrays.asList(Role.ADMIN);
    }


    public void setTextField(TbUser tbUser) {

        PasswordField password = (PasswordField) scene.lookup("#password");
        TextField firstName = (TextField) scene.lookup("#userFirstName");
        TextField secondName = (TextField) scene.lookup("#userSecondName");
        TextField lastName = (TextField) scene.lookup("#userLastName");
        TextArea comments = (TextArea) scene.lookup("#userComments");



        password.setText(tbUser.getVcPassword());
        firstName.setText(tbUser.getVcFirstName());
        secondName.setText(tbUser.getVcSecondName());
        lastName.setText(tbUser.getVcLastName());
        comments.setText(tbUser.getVcComments());
    }


    @HandleEvent(nodeName = "buttonEditUser")
    public EventPair eventHandler() {
        EventPair pair = new EventPair();

        EventHandler eventHandler = (x) -> {
            Stage stage = stageHolder.getStage();


            String password = ((PasswordField) scene.lookup("#password")).getText();
            String firstName = ((TextField) scene.lookup("#userFirstName")).getText();
            String secondName = ((TextField) scene.lookup("#userSecondName")).getText();
            String lastName = ((TextField) scene.lookup("#userLastName")).getText();
            String comments = ((TextArea) scene.lookup("#userComments")).getText();


            tbUser.setVcPassword(password);
            tbUser.setVcFirstName(firstName);
            tbUser.setVcSecondName(secondName);
            tbUser.setVcLastName(lastName);
            tbUser.setVcComments(comments);


            userService.updateUsers(tbUser);


            stage.setScene(listUserComponent.getScene());
            stage.show();
        };

        pair.setEventHandler(eventHandler);
        pair.setEventType(MouseEvent.MOUSE_CLICKED);

        return pair;
    }

    @HandleEvent(nodeName = "buttonListExperimentSubjects")
    public EventPair transitionToExperimentSubjects(){
        EventPair pair = new EventPair();

        EventHandler eventHandler = (x) -> {
            Stage stage = stageHolder.getStage();


            stage.setScene(listExperimentSubject.getScene());
            stage.show();
        };

        pair.setEventHandler(eventHandler);
        pair.setEventType(MouseEvent.MOUSE_CLICKED);

        return pair;
    }

    @HandleEvent(nodeName = "buttonListExperiments")
    public EventPair eventPair3(){
        EventPair pair = new EventPair();

        EventHandler eventHandler = (x) -> {
            Stage stage = stageHolder.getStage();
            if(securityContext.getUser().getVcRole() == Role.ADMIN){
                stage.setScene(listExperimentComponent.getScene());
                stage.show();
            } else {
                stage.setScene(listMyExperimentForUserComponent.getScene());
                stage.show();
            }
        };

        pair.setEventHandler(eventHandler);
        pair.setEventType(MouseEvent.MOUSE_CLICKED);

        return pair;
    }


    @HandleEvent(nodeName = "buttonMain")
    public EventPair transitionToMain(){
        EventPair pair = new EventPair();

        EventHandler eventHandler = (x) -> {
            Stage stage = stageHolder.getStage();


            stage.setScene(mainComponent.getScene());
            stage.show();
        };

        pair.setEventHandler(eventHandler);
        pair.setEventType(MouseEvent.MOUSE_CLICKED);

        return pair;
    }

    @HandleEvent(nodeName = "buttonListUsers")
    public EventPair transitionToUsers(){
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


}
