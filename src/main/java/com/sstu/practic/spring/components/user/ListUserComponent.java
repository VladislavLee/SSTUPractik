package com.sstu.practic.spring.components.user;

import com.sstu.practic.spring.annotations.HandleEvent;
import com.sstu.practic.spring.annotations.JavaFxComponent;
import com.sstu.practic.spring.annotations.PreAuthorize;
import com.sstu.practic.spring.components.FxComponent;
import com.sstu.practic.spring.components.MainComponent;
import com.sstu.practic.spring.components.arrangement.ListArrangementComponent;
import com.sstu.practic.spring.components.banner.BannerComponent;
import com.sstu.practic.spring.components.design.ListDesignComponent;
import com.sstu.practic.spring.components.experiment.ListExperimentComponent;
import com.sstu.practic.spring.components.experiment.ListMyExperimentComponent;
import com.sstu.practic.spring.components.experiment.ListMyExperimentForUserComponent;
import com.sstu.practic.spring.components.experimentSubject.ListExperimentSubject;
import com.sstu.practic.spring.components.experimentType.ListExperimentTypeComponent;
import com.sstu.practic.spring.data.model.TbUser;
import com.sstu.practic.spring.services.UserService;
import com.sstu.practic.spring.services.security.SecurityContext;
import com.sstu.practic.spring.services.security.entites.Role;
import com.sstu.practic.spring.utils.StageHolder;
import com.sstu.practic.spring.utils.entities.EventPair;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
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

import java.util.Arrays;
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
    @Autowired
    private CreateUserComponent createUserComponent;
    @Autowired
    private EditUserForAdminComponent editUserForAdminComponent;
    @Autowired
    private SecurityContext securityContext;
    @Autowired
    private BannerComponent bannerComponent;
    @Autowired
    private ListMyExperimentComponent listMyExperimentForUserComponent;


    private ObservableList<TbUser> userData = FXCollections.observableArrayList();


    @Override
    public Scene getScene() {
        TableView tableView = (TableView) scene.lookup("#tableUsers");

        TableColumn<TbUser, String> userFirstName
                = new TableColumn<TbUser, String>("Имя");
        TableColumn<TbUser, String> userSecondName
                = new TableColumn<TbUser, String>("Фамилия ");
        TableColumn<TbUser, String> userLastName
                = new TableColumn<TbUser, String>("Отчество ");
        TableColumn<TbUser, String> role
                = new TableColumn<TbUser, String>("Роль");
        TableColumn<TbUser, String> userComments
                = new TableColumn<TbUser, String>("Комментарий");
        TableColumn actionUpdate =
                new TableColumn<>("Обновить");
        TableColumn actionDelete =
                new TableColumn("Удалить");

        actionUpdate.setSortable(false);
        actionDelete.setSortable(false);


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

                            final Button btn = new Button("Обновить");

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

                                        if(securityContext.getUser().getVcRole() == Role.ADMIN){
                                            editUserForAdminComponent.setTextField(tbUser);
                                            stage.setScene(editUserForAdminComponent.getScene(tbUser));
                                            stage.show();
                                        } else {
                                            stage.setScene(bannerComponent.getScene());
                                            stage.show();
                                        }

                                        editUserForAdminComponent.setTextField(tbUser);
                                        stage.setScene(editUserForAdminComponent.getScene(tbUser));
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

                            final Button btn = new Button("Удалить");

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
                                        Stage stage = stageHolder.getStage();

                                        if(securityContext.getUser().getVcRole() == Role.ADMIN){
                                            editUserForAdminComponent.setTextField(tbUser);
                                            stage.setScene(editUserForAdminComponent.getScene(tbUser));
                                            stage.show();
                                        } else {
                                            stage.setScene(bannerComponent.getScene());
                                            stage.show();
                                        }

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


        if(tableView.getColumns().isEmpty()) {
            tableView.getColumns().addAll(userFirstName, userSecondName, userLastName,
                    role, userComments, actionUpdate, actionDelete);
        }

        Pane root = (Pane) this.scene.getRoot();
        root.setPadding(new Insets(10));

        this.scene.setRoot(root);
        return this.scene;
    }

    private ObservableList<TbUser> getList() {
        List<TbUser> users = userService.getAllUsers();
        ObservableList<TbUser> list = FXCollections.observableArrayList(users);
        return list;
    }

    @HandleEvent(nodeName = "editUserByUser")
    public EventPair editUserByUser() {
        EventPair pair = new EventPair();

        EventHandler eventHandler = (x) -> {
            Stage stage = stageHolder.getStage();

            TbUser tbUser = securityContext.getUser();

            editUserComponent.setTextField(tbUser);

            stage.setScene(editUserComponent.getScene(tbUser));
            stage.show();
        };

        pair.setEventHandler(eventHandler);
        pair.setEventType(MouseEvent.MOUSE_CLICKED);

        return pair;
    }


    @HandleEvent(nodeName = "createUser")
    public EventPair createUser() {
        EventPair pair = new EventPair();

        EventHandler eventHandler = (x) -> {
            Stage stage = stageHolder.getStage();

            if (securityContext.getUser().getVcRole() == Role.ADMIN) {

                stage.setScene(createUserComponent.getScene());
                stage.show();
            } else {
                stage.setScene(mainComponent.getScene());
                stage.show();
            }
            stage.show();
        };

        pair.setEventHandler(eventHandler);
        pair.setEventType(MouseEvent.MOUSE_CLICKED);

        return pair;
    }


    @HandleEvent(nodeName = "buttonListExperimentSubjects")
    public EventPair transitionToExperimentSubjects() {
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
    public EventPair transitionToMain() {
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
    public EventPair transitionToUsers() {
        EventPair pair = new EventPair();
        EventHandler eventHandler = (x) -> {
            Stage stage = stageHolder.getStage();

            if(securityContext.getUser().getVcRole() ==Role.ADMIN){
                stage.setScene(listUserComponent.getScene());
                stage.show();
            } else {
                stage.setScene(editUserComponent.getScene());
                stage.show();
            }

        };


        pair.setEventHandler(eventHandler);
        pair.setEventType(MouseEvent.MOUSE_CLICKED);

        return pair;
    }

}
