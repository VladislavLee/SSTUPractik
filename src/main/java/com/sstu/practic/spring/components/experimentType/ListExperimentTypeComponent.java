package com.sstu.practic.spring.components.experimentType;

import com.sstu.practic.spring.annotations.HandleEvent;
import com.sstu.practic.spring.annotations.JavaFxComponent;
import com.sstu.practic.spring.components.FxComponent;
import com.sstu.practic.spring.components.MainComponent;
import com.sstu.practic.spring.components.arrangement.ListArrangementComponent;
import com.sstu.practic.spring.components.design.ListDesignComponent;
import com.sstu.practic.spring.components.experiment.EditExperimentComponent;
import com.sstu.practic.spring.components.experiment.ListExperimentComponent;
import com.sstu.practic.spring.components.experimentSubject.ListExperimentSubject;
import com.sstu.practic.spring.components.processingMethod.EditProcessingMethodComponent;
import com.sstu.practic.spring.components.processingMethod.ListProcessingMethodsComponents;
import com.sstu.practic.spring.components.user.ListUserComponent;
import com.sstu.practic.spring.data.model.TbExperimentType;
import com.sstu.practic.spring.data.model.TbMood;
import com.sstu.practic.spring.services.ExperimentService;
import com.sstu.practic.spring.services.ExperimentTypeService;
import com.sstu.practic.spring.services.MoodService;
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

@JavaFxComponent(path = "/view/listExperimentType.fxml")
public class ListExperimentTypeComponent extends FxComponent {
    @Autowired
    private StageHolder stageHolder;
    @Autowired
    private ExperimentTypeService experimentTypeService;
    @Autowired
    private MainComponent mainComponent;
    @Autowired
    private EditExperimentTypeComponent editExperimentTypeComponent;
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
    private ListArrangementComponent listArrangementComponent;
    @Autowired
    private ListDesignComponent listDesignComponent;
    @Autowired
    private ListExperimentSubject listExperimentSubject;
    @Autowired
    private ListExperimentTypeComponent listExperimentTypeComponent;
    @Autowired
    private ListExperimentComponent listExperimentComponent;



    @PostConstruct
    public void init() {
        TableView tableView =(TableView) scene.lookup("#listExperimentType");

        TableColumn<TbExperimentType, String> experimentTypeName
                = new TableColumn<TbExperimentType, String>("Название");
        TableColumn<TbExperimentType, String> experimentTypeDescription
                = new TableColumn<TbExperimentType, String>("Описание");
        TableColumn actionUpdate
                = new TableColumn<>("Update");
        TableColumn actionDelete
                = new TableColumn("Delete");

        actionUpdate.setSortable(false);
        actionDelete.setSortable(false);

        experimentTypeName.setCellValueFactory(new PropertyValueFactory<>("vcName"));
        experimentTypeDescription.setCellValueFactory(new PropertyValueFactory<>("vcDescription"));
        actionUpdate.setCellValueFactory(new PropertyValueFactory<>("DUMMY"));
        actionDelete.setCellValueFactory(new PropertyValueFactory<>("DUMMY"));


        ObservableList<TbExperimentType> list = getList();
        tableView.setItems(list);

        Callback<TableColumn<TbExperimentType, String>, TableCell<TbExperimentType, String>> cellFactory
                =
                new Callback<TableColumn<TbExperimentType, String>, TableCell<TbExperimentType, String>>() {
                    @Override
                    public TableCell call(final TableColumn<TbExperimentType, String> param) {
                        final TableCell<TbExperimentType, String> cell = new TableCell<TbExperimentType, String>() {

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
                                        TbExperimentType tbExperimentType = getTableView().getItems().get(getIndex());
                                        Stage stage = stageHolder.getStage();
                                        stage.setScene(editExperimentTypeComponent.getScene(tbExperimentType));
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

        Callback<TableColumn<TbExperimentType, String>, TableCell<TbExperimentType, String>> createButtonDelete
                =
                new Callback<TableColumn<TbExperimentType, String>, TableCell<TbExperimentType, String>>() {
                    @Override
                    public TableCell call(final TableColumn<TbExperimentType, String> param) {
                        final TableCell<TbExperimentType, String> cell = new TableCell<TbExperimentType, String>() {

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
                                        TbExperimentType tbExperimentType = getTableView().getItems().get(getIndex());
                                        experimentTypeService.deleteExperimentType(tbExperimentType);
                                        list.removeAll(list);
                                        ObservableList<TbExperimentType> lister = getList();
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


        tableView.getColumns().addAll(experimentTypeName, experimentTypeDescription, actionUpdate, actionDelete );


        Pane root = (Pane) this.scene.getRoot();
        root.setPadding(new Insets(10));
        root.getChildren().add(tableView);
        this.scene.setRoot(root);

    }

    private ObservableList<TbExperimentType> getList() {
        List<TbExperimentType> experimentTypes = experimentTypeService.getExperimentType();
        ObservableList<TbExperimentType> list = FXCollections.observableArrayList(experimentTypes);
        return list;
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
    @HandleEvent(nodeName = "buttonExperiment")
    public EventPair eventPair6(){
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


}
