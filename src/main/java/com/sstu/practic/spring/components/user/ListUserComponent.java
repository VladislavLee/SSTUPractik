package com.sstu.practic.spring.components.user;

import com.sstu.practic.spring.annotations.HandleEvent;
import com.sstu.practic.spring.annotations.JavaFxComponent;
import com.sstu.practic.spring.components.FxComponent;
import com.sstu.practic.spring.components.MainComponent;
import com.sstu.practic.spring.components.arrangement.ListArrangementComponent;
import com.sstu.practic.spring.components.design.ListDesignComponent;
import com.sstu.practic.spring.components.experiment.ListExperimentComponent;
import com.sstu.practic.spring.components.experimentSubject.ListExperimentSubject;
import com.sstu.practic.spring.components.experimentType.ListExperimentTypeComponent;
import com.sstu.practic.spring.data.model.TbUser;
import com.sstu.practic.spring.services.UserService;
import com.sstu.practic.spring.utils.StageHolder;
import com.sstu.practic.spring.utils.entities.EventPair;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.springframework.beans.factory.annotation.Autowired;
import javax.annotation.PostConstruct;
import java.util.List;

@JavaFxComponent(path = "/view/listUser.fxml")
public class ListUserComponent extends FxComponent {
    @Autowired
    private StageHolder stageHolder;
    @Autowired
    private UserService userService;
    @Autowired
    private MainComponent mainComponent;
    @Autowired
    private EditUserComponent editUserComponent;
    @Autowired
    private ListUserComponent listUserComponent;
    @Autowired
    private ListArrangementComponent listArrangementComponent;
    @Autowired
    private ListDesignComponent listDesignComponent;
    @Autowired
    private ListExperimentSubject listExperimentSubject;
    @Autowired
    private ListExperimentTypeComponent listExperimentTypeComponent;
    @Autowired
    private ListExperimentComponent listExperimentComponent;
    private ObservableList<TbUser> userData = FXCollections.observableArrayList();

    @PostConstruct
    public void init() {
        TableView tableView =(TableView) scene.lookup("#tableUsers");

        TableColumn<TbUser, String> login
                = new TableColumn<TbUser, String>("Login");
        TableColumn<TbUser, String> password
                = new TableColumn<TbUser, String>("Password");
        TableColumn<TbUser, String> userFirstName
                = new TableColumn<TbUser, String>("FirstName");
        TableColumn<TbUser, String> userSecondName
                = new TableColumn<TbUser, String>("SecondName ");
        TableColumn<TbUser, String> userLastName
                = new TableColumn<TbUser, String>("LastName ");
        TableColumn<TbUser, String> role
                = new TableColumn<TbUser, String>("role ");
        TableColumn<TbUser, String> userComments
                = new TableColumn<TbUser, String>("Comments ");
        TableColumn actionUpdate =
                new TableColumn<>("Update");
        TableColumn actionDelete=
                new TableColumn("Delete");

        actionUpdate.setSortable(false);
        actionDelete.setSortable(false);

        login.setCellValueFactory(new PropertyValueFactory<>("vcLogin"));
        password.setCellValueFactory(new PropertyValueFactory<>("vcPassword"));
        userFirstName.setCellValueFactory(new PropertyValueFactory<>("vcFirstName"));
        userSecondName.setCellValueFactory(new PropertyValueFactory<>("vcSecondName"));
        userLastName.setCellValueFactory(new PropertyValueFactory<>("vcLastName"));
        role.setCellValueFactory(new PropertyValueFactory<>("vcRole"));
        userComments.setCellValueFactory(new PropertyValueFactory<>("vcComments"));
        actionUpdate.setCellValueFactory(new PropertyValueFactory<>("DUMMY"));
        actionDelete.setCellValueFactory(new PropertyValueFactory<>("DUMMY"));

        ObservableList<TbUser> list = getList();
        tableView.setItems(list);

        Callback<TableColumn<TbUser, String>, TableCell<TbUser, String>> cellFactory
                =
                new Callback<TableColumn<TbUser, String>, TableCell<TbUser, String>>() {
                    @Override
                    public TableCell call(final TableColumn<TbUser, String> param) {
                        final TableCell<TbUser, String> cell = new TableCell<TbUser, String>() {

                            final Button btn = new Button("UPDATE");

                            @Override
                            public void updateItem(String item, boolean empty) {
                                super.updateItem(item, empty);
                                if (empty) {
                                    setGraphic(null);
                                    setText(null);
                                } else {
                                    btn.setId("Update");
                                    btn.setOnAction(event -> {
                                        TbUser tbUser = getTableView().getItems().get(getIndex());
                                        Stage stage = stageHolder.getStage();
                                        stage.setScene(editUserComponent.getScene(tbUser));
                                        stage.show();
                                    });
                                    setGraphic(btn);
                                    setText(null);
                                }
                            }
                        };
                        return cell;
                    }
                };

        Callback<TableColumn<TbUser, String>, TableCell<TbUser, String>> createButtonDelete
                =
                new Callback<TableColumn<TbUser, String>, TableCell<TbUser, String>>() {
                    @Override
                    public TableCell call(final TableColumn<TbUser, String> param) {
                        final TableCell<TbUser, String> cell = new TableCell<TbUser, String>() {

                            final Button btn = new Button("DELETE");

                            @Override
                            public void updateItem(String item, boolean empty) {
                                super.updateItem(item, empty);
                                if (empty) {
                                    setGraphic(null);
                                    setText(null);
                                } else {
                                    btn.setId("Delete");
                                    btn.setOnAction(event -> {
                                        TbUser tbUser = getTableView().getItems().get(getIndex());
                                        userService.deleteUsers(tbUser);
                                        list.removeAll(list);
                                        ObservableList<TbUser> lister = getList();
                                        tableView.setItems(lister);
                                    });
                                    setGraphic(btn);
                                    setText(null);
                                }
                            }
                        };
                        return cell;
                    }
                };

        actionUpdate.setCellFactory(cellFactory);
        actionDelete.setCellFactory(createButtonDelete);

        tableView.getColumns().addAll(login, password, userFirstName, userSecondName, userLastName,
                                        role, userComments , actionUpdate, actionDelete);



        Pane root = (Pane) this.scene.getRoot();
        root.setPadding(new Insets(10));
        root.getChildren().add(tableView);
        this.scene.setRoot(root);

    }

    private ObservableList<TbUser> getList() {
        List<TbUser> users = userService.getAllUsers();
        ObservableList<TbUser> list = FXCollections.observableArrayList(users);
        return list;
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


            stage.setScene(listDesignComponent.getScene());
            stage.show();
        };

        pair.setEventHandler(eventHandler);
        pair.setEventType(MouseEvent.MOUSE_CLICKED);

        return pair;
    }


    @HandleEvent(nodeName = "buttonMain")
    public EventPair eventPair5(){
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