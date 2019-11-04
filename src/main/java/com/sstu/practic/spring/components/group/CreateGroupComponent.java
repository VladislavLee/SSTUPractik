package com.sstu.practic.spring.components.group;

import com.sstu.practic.spring.annotations.HandleEvent;
import com.sstu.practic.spring.annotations.JavaFxComponent;
import com.sstu.practic.spring.components.FxComponent;
import com.sstu.practic.spring.components.experiment.ListExperimentComponent;
import com.sstu.practic.spring.data.model.*;
import com.sstu.practic.spring.services.GroupService;
import com.sstu.practic.spring.services.UserService;
import com.sstu.practic.spring.services.security.SecurityContext;
import com.sstu.practic.spring.utils.StageHolder;
import com.sstu.practic.spring.utils.entities.EventPair;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import javax.annotation.PostConstruct;
import java.util.List;


@JavaFxComponent(path = "/view/createGroup.fxml")
public class CreateGroupComponent extends FxComponent {
    @Autowired
    private StageHolder stageHolder;
    @Autowired
    private UserService userService;
    @Autowired
    private GroupService groupService;
    @Autowired
    private SecurityContext securityContext;
    @Autowired
    private ListGroupComponent listGroupComponent;

    private TbGroup tbGroup;
    private MultipleSelectionModel<TbUser> userSelectionModel;

    @HandleEvent(nodeName = "buttonCreateGroup")
    public EventPair eventHandler() {
        EventPair pair = new EventPair();

        EventHandler eventHandler = (x) -> {
            Stage stage = stageHolder.getStage();

            String nameGroup = ( (TextField) scene.lookup("#nameGroup")).getText();

            groupService.addGroup(TbGroup.builder()
            .idCreator(securityContext.getUser().getIdUser())
            .vcName(nameGroup)
            .userList(userSelectionModel.getSelectedItems())
            .build());

            stage.setScene(listGroupComponent.getScene());
            stage.show();
        };

        pair.setEventHandler(eventHandler);
        pair.setEventType(MouseEvent.MOUSE_CLICKED);

        return pair;
    }

    private ObservableList<TbUser> getUserList() {
        List<TbUser> users = userService.getAllUsers();
        ObservableList<TbUser> list = FXCollections.observableArrayList(users);
        return list;
    }


    @PostConstruct
    public void init(){
        ListView userGroup = (ListView) scene.lookup("#userGroup");
        ObservableList<TbUser> users = getUserList();
        userGroup.setItems(users);

        userSelectionModel = userGroup.getSelectionModel();
        userSelectionModel.setSelectionMode(SelectionMode.MULTIPLE);

        userSelectionModel.selectedItemProperty().addListener(new ChangeListener(){
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {

            }
        });
    }

}
