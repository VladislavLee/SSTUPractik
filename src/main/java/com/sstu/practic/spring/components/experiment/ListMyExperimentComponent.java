package com.sstu.practic.spring.components.experiment;

import com.sstu.practic.spring.annotations.HandleEvent;
import com.sstu.practic.spring.annotations.JavaFxComponent;
import com.sstu.practic.spring.components.FxComponent;
import com.sstu.practic.spring.components.MainComponent;
import com.sstu.practic.spring.components.arrangement.ListArrangementComponent;
import com.sstu.practic.spring.components.design.ListDesignComponent;
import com.sstu.practic.spring.components.experimentSubject.ListExperimentSubject;
import com.sstu.practic.spring.components.experimentType.ListExperimentTypeComponent;
import com.sstu.practic.spring.components.processingMethod.EditProcessingMethodComponent;
import com.sstu.practic.spring.components.processingMethod.ListProcessingMethodsComponents;
import com.sstu.practic.spring.components.user.ListUserComponent;
import com.sstu.practic.spring.data.model.TbExperiment;
import com.sstu.practic.spring.data.model.TbFavoriteExperiment;
import com.sstu.practic.spring.services.EquipmentService;
import com.sstu.practic.spring.services.ExperimentService;
import com.sstu.practic.spring.services.FavoriteExperimentService;
import com.sstu.practic.spring.services.MyExperimentService;
import com.sstu.practic.spring.services.security.SecurityContext;
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

@JavaFxComponent(path = "/view/oldPages/listMyExperiment.fxml")
public class ListMyExperimentComponent extends FxComponent {
    @Autowired
    private MyExperimentService myExperimentService;
    @Autowired
    private StageHolder stageHolder;
    @Autowired
    private EquipmentService equipmentService;
    @Autowired
    private MainComponent mainComponent;
    @Autowired
    private EditExperimentComponent editExperimentComponent;
    @Autowired
    private EditProcessingMethodComponent editProcessingMethodComponent;
    @Autowired
    private ExperimentService experimentService;
    @Autowired
    private ListProcessingMethodsComponents listProcessingMethodsComponents;
    @Autowired
    private ListUserComponent listUserComponent;
    @Autowired
    private ListExperimentSubject listExperimentSubject;
    @Autowired
    private ListExperimentTypeComponent listExperimentTypeComponent;
    @Autowired
    private FavoriteExperimentService favoriteExperimentService;
    @Autowired
    private SecurityContext securityContext;
    @Autowired
    private ListFavoriteExperimentComponent listFavoriteExperimentComponent;
    @Autowired
    private ListArrangementComponent listArrangementComponent;
    @Autowired
    private ListDesignComponent listDesignComponent;
    @Autowired
    private ListMyExperimentComponent listMyExperimentComponent;
    @Autowired
    private ListExperimentComponent listExperimentComponent;
    @Autowired
    private CreateExperimentComponent createExperimentComponent;


    @Override
    public Scene getScene() {
        TableView tableView =(TableView) scene.lookup("#listMyExperiment");

        TableColumn<TbExperiment, String> experimentType
                = new TableColumn<TbExperiment, String>("Тип");
        TableColumn< TbExperiment, String> experimentSubject
                = new TableColumn<TbExperiment, String>("Испытуемый");
        TableColumn< TbExperiment, String> experimentDesign
                = new TableColumn<TbExperiment, String>("План");
        TableColumn< TbExperiment, String> mood
                = new TableColumn<TbExperiment, String>("Настроение");
        TableColumn< TbExperiment, String> date
                = new TableColumn<TbExperiment, String>("Дата");
        TableColumn< TbExperiment, String> recordDuration
                = new TableColumn<TbExperiment, String>("Длительность сессии");
        TableColumn< TbExperiment, String> subjectWeight
                = new TableColumn<TbExperiment, String>("Вес испытуемого");
        TableColumn< TbExperiment, String> experimentComments
                = new TableColumn<TbExperiment, String>("Коментарии");
        TableColumn actionAddFavorite
                = new TableColumn("AddFavorite");
        TableColumn actionUpdate
                = new TableColumn<>("Update");
        TableColumn actionDelete
                = new TableColumn("Delete");

        actionUpdate.setSortable(false);
        actionDelete.setSortable(false);
        actionAddFavorite.setSortable(false);

        experimentType.setCellValueFactory(new PropertyValueFactory<>("vcExperimentType"));
        experimentSubject.setCellValueFactory(new PropertyValueFactory<>("vcExperimentSubject"));
        experimentDesign.setCellValueFactory(new  PropertyValueFactory<>("vcExperimentDesign"));
        mood.setCellValueFactory(new PropertyValueFactory<>("vcMood"));
        date.setCellValueFactory(new PropertyValueFactory<>("vcDate"));
        recordDuration.setCellValueFactory(new PropertyValueFactory<>("vcRecordDuration"));
        subjectWeight.setCellValueFactory(new PropertyValueFactory<>("vcSubjectWeight"));
        experimentComments.setCellValueFactory(new PropertyValueFactory<>("vcDescription"));
        actionUpdate.setCellValueFactory(new PropertyValueFactory<>("DUMMY"));
        actionDelete.setCellValueFactory(new PropertyValueFactory<>("DUMMY"));
        actionAddFavorite.setCellFactory(new PropertyValueFactory<>(""));


        List<TbExperiment> experiments = getList(securityContext.getUser().getIdUser());
        ObservableList<TbExperiment> list = FXCollections.observableArrayList(experiments);
        tableView.setItems(list);


        Callback<TableColumn<TbExperiment, String>, TableCell<TbExperiment, String>> cellFactory
                =
                new Callback<TableColumn<TbExperiment, String>, TableCell<TbExperiment, String>>() {
                    @Override
                    public TableCell call(final TableColumn<TbExperiment, String> param) {
                        final TableCell<TbExperiment, String> cell = new TableCell<TbExperiment, String>() {

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
                                        TbExperiment tbExperiment = getTableView().getItems().get(getIndex());
                                        Stage stage = stageHolder.getStage();
                                        editExperimentComponent.setTextField(tbExperiment);
                                        stage.setScene(editExperimentComponent.getScene(tbExperiment));
                                        stage.show();
                                        list.removeAll(list);

                                    });
                                    setGraphic(btn);
                                    setText(null);
                                }
                            }
                        };
                        return cell;
                    }
                };

        Callback<TableColumn<TbExperiment, String>, TableCell<TbExperiment, String>> createButtonDelete
                =
                new Callback<TableColumn<TbExperiment, String>, TableCell<TbExperiment, String>>() {
                    @Override
                    public TableCell call(final TableColumn<TbExperiment, String> param) {
                        final TableCell<TbExperiment, String> cell = new TableCell<TbExperiment, String>() {

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
                                        TbExperiment tbExperiment = getTableView().getItems().get(getIndex());
                                        favoriteExperimentService.deleteFavoriteByExperiment(tbExperiment);
                                        experimentService.deleteExperiment(tbExperiment);

                                        list.removeAll(list);
                                        ObservableList<TbExperiment> lister = getList(securityContext.getUser().getIdUser());
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

        Callback<TableColumn<TbExperiment, String>, TableCell<TbExperiment, String>> createButtonAddFavorite
                =
                new Callback<TableColumn<TbExperiment, String>, TableCell<TbExperiment, String>>() {
                    @Override
                    public TableCell call(final TableColumn<TbExperiment, String> param) {
                        final TableCell<TbExperiment, String> cell = new TableCell<TbExperiment, String>() {

                            final Button btn = new Button("addFavorite");

                            @Override
                            public void updateItem(String item, boolean empty) {
                                super.updateItem(item, empty);
                                if (empty) {
                                    setGraphic(null);
                                    setText(null);
                                } else {
                                    btn.setId("addFavorite");
                                    btn.setOnAction(event -> {
                                        TbExperiment tbExperiment = getTableView().getItems().get(getIndex());
                                        favoriteExperimentService.addFavoriteExperiment(tbExperiment);
                                    });
                                    setGraphic(btn);
                                    setText(null);
                                }
                            }
                        };
                        return cell;
                    }
                };

        actionAddFavorite.setCellFactory(createButtonAddFavorite);
        actionUpdate.setCellFactory(cellFactory);
        actionDelete.setCellFactory(createButtonDelete);


        tableView.getColumns().addAll(experimentType, experimentSubject, experimentDesign, mood, date, recordDuration, subjectWeight,
                experimentComments, actionAddFavorite, actionUpdate, actionDelete );

        Pane root = (Pane) this.scene.getRoot();
        root.setPadding(new Insets(10));
        root.getChildren().add(tableView);
        this.scene.setRoot(root);
        return scene;
    }

    private ObservableList<TbExperiment> getList(Integer id) {
        List<TbExperiment> experiments = myExperimentService.getMyExperiment(id);
        ObservableList<TbExperiment> list = FXCollections.observableArrayList(experiments);
        return list;
    }






//    navigation

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


            stage.setScene(listExperimentComponent.getScene());
            stage.show();
        };

        pair.setEventHandler(eventHandler);
        pair.setEventType(MouseEvent.MOUSE_CLICKED);

        return pair;
    }


    @HandleEvent(nodeName = "buttonExperimentType")
    public EventPair eventPair4(){
        EventPair pair = new EventPair();

        EventHandler eventHandler = (x) -> {
            Stage stage = stageHolder.getStage();


            stage.setScene(listExperimentTypeComponent.getScene());
            stage.show();
        };

        pair.setEventHandler(eventHandler);
        pair.setEventType(MouseEvent.MOUSE_CLICKED);

        return pair;
    }

    @HandleEvent(nodeName = "buttonExperimentDesign")
    public EventPair eventPair5(){
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


    @HandleEvent(nodeName = "buttonMyExperiment")
    public EventPair transitionToMyExperiment(){
        EventPair pair = new EventPair();

        EventHandler eventHandler = (x) -> {
            Stage stage = stageHolder.getStage();


            stage.setScene(listMyExperimentComponent.getScene());
            stage.show();
        };

        pair.setEventHandler(eventHandler);
        pair.setEventType(MouseEvent.MOUSE_CLICKED);

        return pair;
    }

    @HandleEvent(nodeName = "buttonFavoriteExperiment")
    public EventPair transitionToFavoriteExperiment(){
        EventPair pair = new EventPair();

        EventHandler eventHandler = (x) -> {
            Stage stage = stageHolder.getStage();


            stage.setScene(listFavoriteExperimentComponent.getScene());
            stage.show();
        };

        pair.setEventHandler(eventHandler);
        pair.setEventType(MouseEvent.MOUSE_CLICKED);

        return pair;
    }

}
