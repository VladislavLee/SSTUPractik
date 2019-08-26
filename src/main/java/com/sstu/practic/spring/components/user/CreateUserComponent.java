package com.sstu.practic.spring.components.user;

import com.sstu.practic.spring.annotations.HandleEvent;
import com.sstu.practic.spring.annotations.JavaFxComponent;
import com.sstu.practic.spring.components.FxComponent;
import com.sstu.practic.spring.components.MainComponent;
import com.sstu.practic.spring.components.experiment.ListExperimentComponent;
import com.sstu.practic.spring.components.experimentSubject.ListExperimentSubject;
import com.sstu.practic.spring.data.model.TbUser;
import com.sstu.practic.spring.data.repositories.UserRepository;
import com.sstu.practic.spring.services.UserService;
import com.sstu.practic.spring.services.security.SecurityContext;
import com.sstu.practic.spring.services.security.entites.Role;
import com.sstu.practic.spring.utils.StageHolder;
import com.sstu.practic.spring.utils.entities.EventPair;
import javafx.collections.FXCollections;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileInputStream;
import java.util.Arrays;
import java.util.List;

@JavaFxComponent(path = "/view/createUser.fxml")
public class CreateUserComponent extends FxComponent {
    @Autowired
    private StageHolder stageHolder;
    @Autowired
    private UserService userService;
    @Autowired
    private MainComponent mainComponent;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SecurityContext securityContext;
    @Autowired
    private ListUserComponent listUserComponent;
    @Autowired
    private ListExperimentSubject listExperimentSubject;
    @Autowired
    private ListExperimentComponent listExperimentComponent;

    private byte[] bytePhoto;

    @Override
    public List<Role> getRole(){
        return Arrays.asList(Role.ADMIN);
    }

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

            Role role = (Role)((ChoiceBox) scene.lookup("#chooseRole")).getValue();

            userService.addUser(TbUser.builder()
                    .vcLogin(login)
                    .vcPassword(password)
                    .vcFirstName(firstName)
                    .vcSecondName(secondName)
                    .vcLastName(lastName)
                    .vcComments(comments)
                    .vcRole(role)
                    .iPhoto(bytePhoto)
                    .build());

            stage.setScene(mainComponent.getScene());
            stage.show();
        };


        pair.setEventHandler(eventHandler);
        pair.setEventType(MouseEvent.MOUSE_CLICKED);

        return pair;
    }

    @HandleEvent(nodeName = "certificatePath")
    public EventPair uploadPhoto(){
        FileChooser fileChooser = new FileChooser();

        EventHandler eventHandler = new EventHandler() {
            @Override
            public void handle(Event event) {
                File file = fileChooser.showOpenDialog(stageHolder.getStage());
                byte[] bytes;


                try(FileInputStream fileInputStream = new FileInputStream(file)) {
                    bytes = new byte[fileInputStream.available()];

                    fileInputStream.read(bytes);
                    fileInputStream.close();


                    bytePhoto = bytes;





//                    запись файлы из бд
//                    FileOutputStream fileOutputStream = new FileOutputStream("./2.jpg");
//                    fileOutputStream.write(userRepository.findByVcLoginAndVcPassword("user","111").get().getIPhoto());
//                    fileOutputStream.close();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };

        return new EventPair(MouseEvent.MOUSE_CLICKED, eventHandler);
    }

    @PostConstruct
    public void init(){
        ChoiceBox choiceBox =(ChoiceBox) scene.lookup("#chooseRole");
        choiceBox.setItems(FXCollections.observableArrayList(Arrays.asList(Role.ADMIN,Role.EXPERIMENTATOR, Role.USER)));
        choiceBox.setValue(Role.ADMIN);
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
    public EventPair transitionToListExperiments(){
        EventPair pair = new EventPair();

        EventHandler eventHandler = (x) -> {
            Stage stage = stageHolder.getStage();


            stage.setScene(listExperimentComponent.getScene());
            stage.show();
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
