package com.sstu.practic.spring.components.processingMethod;

import com.sstu.practic.spring.annotations.HandleEvent;
import com.sstu.practic.spring.annotations.JavaFxComponent;
import com.sstu.practic.spring.components.FxComponent;
import com.sstu.practic.spring.components.MainComponent;
import com.sstu.practic.spring.components.arrangement.ListArrangementComponent;
import com.sstu.practic.spring.components.channel.ListChannelComponent;
import com.sstu.practic.spring.components.design.ListDesignComponent;
import com.sstu.practic.spring.components.equipment.ListEquipmentsComponent;
import com.sstu.practic.spring.components.experimentSubject.ListExperimentSubject;
import com.sstu.practic.spring.components.user.ListUserComponent;
import com.sstu.practic.spring.data.model.TbProcessingMethod;
import com.sstu.practic.spring.services.ProcessingMethodService;
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

@JavaFxComponent(path = "/view/listProcessingMethod.fxml")
public class ListProcessingMethodsComponents extends FxComponent {
    @Autowired
    private StageHolder stageHolder;
    @Autowired
    private ProcessingMethodService processingMethodService;
    @Autowired
    private MainComponent mainComponent;
    @Autowired
    private EditProcessingMethodComponent editProcessingMethodComponent;
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
    private ListChannelComponent listChannelComponent;

    @PostConstruct
    public void init() {
        TableView tableView =(TableView) scene.lookup("#listProcessingMethod");

        TableColumn<TbProcessingMethod, String> processingMethodName
                = new TableColumn<TbProcessingMethod, String>("Название");

        TableColumn<TbProcessingMethod, String> processingMethodDescription
                = new TableColumn<TbProcessingMethod, String>("Описание");

        TableColumn actionUpdate = new TableColumn<>("Update");
        TableColumn actionDelete= new TableColumn("Delete");

        actionUpdate.setSortable(false);
        actionDelete.setSortable(false);



        processingMethodName.setCellValueFactory(new PropertyValueFactory<>("vcName"));
        processingMethodDescription.setCellValueFactory(new PropertyValueFactory<>("vcDescription"));

        actionUpdate.setCellValueFactory(new PropertyValueFactory<>("DUMMY"));
        actionDelete.setCellValueFactory(new PropertyValueFactory<>("DUMMY"));

        ObservableList<TbProcessingMethod> list = getList();
        tableView.setItems(list);



        Callback<TableColumn<TbProcessingMethod, String>, TableCell<TbProcessingMethod, String>> cellFactory
                =
                new Callback<TableColumn<TbProcessingMethod, String>, TableCell<TbProcessingMethod, String>>() {
                    @Override
                    public TableCell call(final TableColumn<TbProcessingMethod, String> param) {
                        final TableCell<TbProcessingMethod, String> cell = new TableCell<TbProcessingMethod, String>() {

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
                                        TbProcessingMethod tbProcessingMethod = getTableView().getItems().get(getIndex());
                                        Stage stage = stageHolder.getStage();
                                        stage.setScene(editProcessingMethodComponent.getScene(tbProcessingMethod));
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

        Callback<TableColumn<TbProcessingMethod, String>, TableCell<TbProcessingMethod, String>> createButtonDelete
                =
                new Callback<TableColumn<TbProcessingMethod, String>, TableCell<TbProcessingMethod, String>>() {
                    @Override
                    public TableCell call(final TableColumn<TbProcessingMethod, String> param) {
                        final TableCell<TbProcessingMethod, String> cell = new TableCell<TbProcessingMethod, String>() {

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
                                        TbProcessingMethod tbProcessingMethod = getTableView().getItems().get(getIndex());
                                        processingMethodService.deleteProcessingMethod(tbProcessingMethod);
                                        list.removeAll(list);
                                        ObservableList<TbProcessingMethod> lister = getList();
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


        tableView.getColumns().addAll(processingMethodName, processingMethodDescription, actionUpdate, actionDelete );


        Pane root = (Pane) this.scene.getRoot();
        root.setPadding(new Insets(10));
        root.getChildren().add(tableView);
        this.scene.setRoot(root);

    }

    private ObservableList<TbProcessingMethod> getList() {
        List<TbProcessingMethod> processingMethods = processingMethodService.getAllProcessingMethods();
        ObservableList<TbProcessingMethod> list = FXCollections.observableArrayList(processingMethods);
        return list;
    }








    //navigation


    @HandleEvent(nodeName = "buttonEquipment")
    public EventPair transitionToEquipment(){
        EventPair pairEquipment = new EventPair();

        EventHandler eventHandler = (x) -> {
            Stage stage = stageHolder.getStage();


            stage.setScene(listEquipmentsComponent.getScene());
            stage.show();
        };

        pairEquipment.setEventHandler(eventHandler);
        pairEquipment.setEventType(MouseEvent.MOUSE_CLICKED);

        return pairEquipment;
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

    @HandleEvent(nodeName = "buttonArrangement")
    public EventPair transitionToArrangement(){
        EventPair pair = new EventPair();

        EventHandler eventHandler = (x) -> {
            Stage stage = stageHolder.getStage();

            stage.setScene(listArrangementComponent.getScene());
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



}
