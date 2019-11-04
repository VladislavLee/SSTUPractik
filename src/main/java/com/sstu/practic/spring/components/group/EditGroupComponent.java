package com.sstu.practic.spring.components.group;

import com.sstu.practic.spring.annotations.HandleEvent;
import com.sstu.practic.spring.annotations.JavaFxComponent;
import com.sstu.practic.spring.components.FxComponent;
import com.sstu.practic.spring.components.experiment.ListExperimentComponent;
import com.sstu.practic.spring.data.model.TbExperiment;
import com.sstu.practic.spring.data.model.TbGroup;
import com.sstu.practic.spring.data.model.TbUser;
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
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.util.List;

@JavaFxComponent(path = "/view/editGroup.fxml")
public class EditGroupComponent extends FxComponent {
    @Autowired
    private StageHolder stageHolder;
    @Autowired
    private UserService userService;
    @Autowired
    private GroupService groupService;
    @Autowired
    private SecurityContext securityContext;
    @Autowired
    private ListExperimentComponent listExperimentComponent;
    @Autowired
    private ListGroupComponent listGroupComponent;

    private TbGroup tbGroup;
    private MultipleSelectionModel<TbUser> userSelectionModel;

    public Scene getScene(TbGroup tbGroup) {
        this.tbGroup = tbGroup;
        return scene;
    }

    public void setTextField(TbGroup tbGroup) {
        TextField nameGroup = (TextField) scene.lookup("#nameGroup");
        nameGroup.setText(tbGroup.getVcName());
    }


    @HandleEvent(nodeName = "buttonEditGroup")
    public EventPair eventHandler() {
        EventPair pair = new EventPair();

        EventHandler eventHandler = (x) -> {
            Stage stage = stageHolder.getStage();


            String nameGroup = ( (TextField) scene.lookup("#nameGroup")).getText();


            tbGroup.setUserList(userSelectionModel.getSelectedItems());


//            groupService.addUserList(tbGroup, userSelectionModel.getSelectedItems());

            tbGroup.setVcName(nameGroup);


            groupService.updateGroup(tbGroup);


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
