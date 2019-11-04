package com.sstu.practic.spring.components.arrangement;

import com.sstu.practic.spring.annotations.HandleEvent;
import com.sstu.practic.spring.annotations.JavaFxComponent;
import com.sstu.practic.spring.components.FxComponent;
import com.sstu.practic.spring.components.MainComponent;
import com.sstu.practic.spring.components.banner.BannerComponent;
import com.sstu.practic.spring.components.channel.ListChannelComponent;
import com.sstu.practic.spring.components.design.ListDesignComponent;
import com.sstu.practic.spring.components.equipment.ListEquipmentsComponent;
import com.sstu.practic.spring.components.experiment.ListExperimentComponent;
import com.sstu.practic.spring.components.experiment.ListMyExperimentComponent;
import com.sstu.practic.spring.components.experimentSubject.ListExperimentSubject;
import com.sstu.practic.spring.components.mood.ListMoodComponent;
import com.sstu.practic.spring.components.processingMethod.ListProcessingMethodsComponents;
import com.sstu.practic.spring.components.user.EditUserComponent;
import com.sstu.practic.spring.components.user.ListUserComponent;
import com.sstu.practic.spring.data.model.TbArrangements;
import com.sstu.practic.spring.data.model.TbChannels;
import com.sstu.practic.spring.data.model.TbUser;
import com.sstu.practic.spring.services.ArrangementService;
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

import javax.annotation.PostConstruct;
import java.util.List;

@JavaFxComponent(path = "/view/listArrangement.fxml")
public class ListArrangementComponent extends FxComponent {
    @Autowired
    private StageHolder stageHolder;
    @Autowired
    private ArrangementService arrangementService;
    @Autowired
    private MainComponent mainComponent;
    @Autowired
    private ListChannelComponent listChannelComponent;
    @Autowired
    private EditArrangementComponent editArrangementComponent;
    @Autowired
    private ListProcessingMethodsComponents listProcessingMethodsComponents;
    @Autowired
    private ListUserComponent listUserComponent;
    @Autowired
    private ListArrangementComponent listArrangementComponent;
    @Autowired
    private ListDesignComponent listDesignComponent;
    @Autowired
    private ListExperimentSubject listExperimentSubject;
    @Autowired
    private ListEquipmentsComponent listEquipmentsComponent;
    @Autowired
    private ListMoodComponent listMoodComponent;
    @Autowired
    private CreateArrangementComponent createArrangementComponent;
    @Autowired
    private ListExperimentComponent listExperimentComponent;
    @Autowired
    private SecurityContext securityContext;
    @Autowired
    private BannerComponent bannerComponent;
    @Autowired
    private ListMyExperimentComponent listMyExperimentComponent;
    @Autowired
    private EditUserComponent editUserComponent;

    @Override
    public Scene getScene() {
        TableView tableView =(TableView) scene.lookup("#listArrangement");
        TableColumn<TbArrangements, String> channelName
                = new TableColumn<TbArrangements, String>("Название");

        TableColumn< TbArrangements, String> channelDescription
                = new TableColumn<TbArrangements, String>("Краткое описание");

        TableColumn col_action = new TableColumn<>("Обновить");

        TableColumn actionDelete= new TableColumn("Удалить");
        col_action.setSortable(false);
        actionDelete.setSortable(false);

        channelName.setCellValueFactory(new PropertyValueFactory<>("vcName"));
        channelDescription.setCellValueFactory(new PropertyValueFactory<>("vcDescription"));


        col_action.setCellValueFactory(new PropertyValueFactory<>("DUMMY"));
        actionDelete.setCellValueFactory(new PropertyValueFactory<>("DUMMY"));

        ObservableList<TbArrangements> list = getList();
        tableView.setItems(list);

        Callback<TableColumn<TbArrangements, String>, TableCell<TbArrangements, String>> cellFactory
                =
                new Callback<TableColumn<TbArrangements, String>, TableCell<TbArrangements, String>>() {
                    @Override
                    public TableCell call(final TableColumn<TbArrangements, String> param) {
                        final TableCell<TbArrangements, String> cell = new TableCell<TbArrangements, String>() {

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
                                        TbArrangements tbArrangements = getTableView().getItems().get(getIndex());

                                        Stage stage = stageHolder.getStage();

                                        if(securityContext.getUser().getVcRole() == Role.USER){
                                            stage.setScene(bannerComponent.getScene());
                                            stage.show();
                                        } else {
                                            editArrangementComponent.setTextField(tbArrangements);
                                            stage.setScene(editArrangementComponent.getScene(tbArrangements));
                                            stage.show();
                                        }


                                    });
                                    setGraphic(btn);
                                    setText(null);
                                }
                            }
                        };
                        return cell;
                    }
                };

        Callback<TableColumn<TbArrangements, String>, TableCell<TbArrangements, String>> createButtonDelete
                =
                new Callback<TableColumn<TbArrangements, String>, TableCell<TbArrangements, String>>() {
                    @Override
                    public TableCell call(final TableColumn<TbArrangements, String> param) {
                        final TableCell<TbArrangements, String> cell = new TableCell<TbArrangements, String>() {

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
                                        TbArrangements tbArrangements = getTableView().getItems().get(getIndex());

                                        Stage stage = stageHolder.getStage();

                                        if(securityContext.getUser().getVcRole() == Role.USER){
                                            stage.setScene(bannerComponent.getScene());
                                            stage.show();
                                        } else {
                                            arrangementService.deleteArrangement(tbArrangements);
                                            list.removeAll(list);
                                            ObservableList<TbArrangements> lister = getList();
                                            tableView.setItems(lister);
                                        }
                                    });
                                    setGraphic(btn);
                                    setText(null);
                                }
                            }
                        };
                        return cell;
                    }
                };





        col_action.setCellFactory(cellFactory);
        actionDelete.setCellFactory(createButtonDelete);



        if (tableView.getColumns().isEmpty()) {
            tableView.getColumns().addAll(channelName, channelDescription, col_action, actionDelete);
        }


        Pane root = (Pane) this.scene.getRoot();
        root.setPadding(new Insets(10));
        this.scene.setRoot(root);
        return this.scene;
    }


    private ObservableList<TbArrangements> getList() {
        List<TbArrangements> arrangements = arrangementService.getAllArrangements();
        ObservableList<TbArrangements> list = FXCollections.observableArrayList(arrangements);
        return list;
    }


    @HandleEvent(nodeName = "createArrangement")
    public EventPair createArrangement(){
        EventPair pair = new EventPair();

        EventHandler eventHandler = (x) -> {
            Stage stage = stageHolder.getStage();

            stage.setScene(createArrangementComponent.getScene());
            stage.show();
        };
        pair.setEventHandler(eventHandler);
        pair.setEventType(MouseEvent.MOUSE_CLICKED);

        return pair;
    }



//    navigation


    @HandleEvent(nodeName = "buttonListUsers")
    public EventPair transitionToUsers(){
        EventPair pair = new EventPair();

        EventHandler eventHandler = (x) -> {
            Stage stage = stageHolder.getStage();

            if(securityContext.getUser().getVcRole()==Role.ADMIN){
                stage.setScene(listUserComponent.getScene());
                stage.show();
            } else {
                TbUser tbUser = securityContext.getUser();
                editUserComponent.setTextField(tbUser);
                stage.setScene(editUserComponent.getScene(tbUser));
                stage.show();
            }
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



    @HandleEvent(nodeName = "buttonExperimentSubjects")
    public EventPair transitionToSubjects(){
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
                stage.setScene(listMyExperimentComponent.getScene());
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


    @HandleEvent(nodeName = "buttonProcessingMethod")
    public EventPair transitionToProcessingMethod(){
        EventPair pair = new EventPair();

        EventHandler eventHandler = (x) -> {
            Stage stage = stageHolder.getStage();

            stage.setScene(listProcessingMethodsComponents.getScene());
            stage.show();
        };

        pair.setEventHandler(eventHandler);
        pair.setEventType(MouseEvent.MOUSE_CLICKED);

        return pair;
    }


    @HandleEvent(nodeName = "buttonEquipment")
    public EventPair transitionToEquipment(){
        EventPair pair = new EventPair();

        EventHandler eventHandler = (x) -> {
            Stage stage = stageHolder.getStage();

            stage.setScene(listEquipmentsComponent.getScene());
            stage.show();
        };

        pair.setEventHandler(eventHandler);
        pair.setEventType(MouseEvent.MOUSE_CLICKED);

        return pair;
    }

    @HandleEvent(nodeName = "buttonChannel")
    public EventPair transitionToChannel(){
        EventPair pair = new EventPair();
        EventHandler eventHandler = (x) -> {
            Stage stage = stageHolder.getStage();
            stage.setScene(listChannelComponent.getScene());
            stage.show();
        };
        pair.setEventHandler(eventHandler);
        pair.setEventType(MouseEvent.MOUSE_CLICKED);
        return pair;
    }

    @HandleEvent(nodeName = "buttonMood")
    public EventPair transitionToMood(){
        EventPair pair = new EventPair();

        EventHandler eventHandler = (x) -> {
            Stage stage = stageHolder.getStage();
            stage.setScene(listMoodComponent.getScene());
            stage.show();
        };

        pair.setEventHandler(eventHandler);
        pair.setEventType(MouseEvent.MOUSE_CLICKED);

        return pair;
    }


}
