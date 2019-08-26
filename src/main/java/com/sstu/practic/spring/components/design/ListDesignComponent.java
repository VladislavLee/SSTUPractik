package com.sstu.practic.spring.components.design;

import com.sstu.practic.spring.annotations.HandleEvent;
import com.sstu.practic.spring.annotations.JavaFxComponent;
import com.sstu.practic.spring.components.FxComponent;
import com.sstu.practic.spring.components.MainComponent;
import com.sstu.practic.spring.components.arrangement.ListArrangementComponent;
import com.sstu.practic.spring.components.equipment.ListEquipmentsComponent;
import com.sstu.practic.spring.components.experiment.ListExperimentComponent;
import com.sstu.practic.spring.components.experiment.ListFavoriteExperimentComponent;
import com.sstu.practic.spring.components.experiment.ListMyExperimentComponent;
import com.sstu.practic.spring.components.experimentSubject.ListExperimentSubject;
import com.sstu.practic.spring.components.experimentType.ListExperimentTypeComponent;
import com.sstu.practic.spring.components.mood.ListMoodComponent;
import com.sstu.practic.spring.components.user.ListUserComponent;
import com.sstu.practic.spring.data.model.TbEquipment;
import com.sstu.practic.spring.data.model.TbExperimentDesign;
import com.sstu.practic.spring.services.ExperimentDesignService;
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

@JavaFxComponent(path = "/view/listDesign.fxml")
public class ListDesignComponent extends FxComponent {
    @Autowired
    private StageHolder stageHolder;
    @Autowired
    private ExperimentDesignService experimentDesignService;
    @Autowired
    private MainComponent mainComponent;
    @Autowired
    private EditDesignComponent editDesignComponent;
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
    private ListEquipmentsComponent listEquipmentsComponent;
    @Autowired
    private CreateDesignComponent createDesignComponent;
    @Autowired
    private ListMyExperimentComponent listMyExperimentComponent;
    @Autowired
    private ListFavoriteExperimentComponent listFavoriteExperimentComponent;



    @Override
    public Scene getScene(){
        TableView tableView =(TableView) scene.lookup("#listExperimentDesign");

        TableColumn<TbEquipment, String> designName
                = new TableColumn<TbEquipment, String>("Название");
        TableColumn< TbEquipment, String> designDescription
                = new TableColumn<TbEquipment, String>("Краткое описание");
        TableColumn< TbEquipment, String> designSampling
                = new TableColumn<TbEquipment, String>("Частота ЭКГ");
        TableColumn< TbEquipment, String> arrangement
                = new TableColumn<TbEquipment, String>("Датчик");
        TableColumn actionUpdate
                = new TableColumn<>("Update");
        TableColumn actionDelete
                = new TableColumn("Delete");

        actionUpdate.setSortable(false);
        actionDelete.setSortable(false);


        designName.setCellValueFactory(new PropertyValueFactory<>("vcName"));
        designDescription.setCellValueFactory(new PropertyValueFactory<>("vcDescription"));
        designSampling.setCellValueFactory(new  PropertyValueFactory<>("vcSampling"));
        arrangement.setCellValueFactory(new  PropertyValueFactory<>("vsArrangement"));
        actionUpdate.setCellValueFactory(new PropertyValueFactory<>("DUMMY"));
        actionDelete.setCellValueFactory(new PropertyValueFactory<>("DUMMY"));


        ObservableList<TbExperimentDesign> list = getList();
        tableView.setItems(list);


        Callback<TableColumn<TbExperimentDesign, String>, TableCell<TbExperimentDesign, String>> cellFactory
                =
                new Callback<TableColumn<TbExperimentDesign, String>, TableCell<TbExperimentDesign, String>>() {
                    @Override
                    public TableCell call(final TableColumn<TbExperimentDesign, String> param) {
                        final TableCell<TbExperimentDesign, String> cell = new TableCell<TbExperimentDesign, String>() {

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
                                        TbExperimentDesign tbExperimentDesign = getTableView().getItems().get(getIndex());
                                        Stage stage = stageHolder.getStage();
                                        editDesignComponent.setTextField(tbExperimentDesign);
                                        stage.setScene(editDesignComponent.getScene(tbExperimentDesign));
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

        Callback<TableColumn<TbExperimentDesign, String>, TableCell<TbExperimentDesign, String>> createButtonDelete
                =
                new Callback<TableColumn<TbExperimentDesign, String>, TableCell<TbExperimentDesign, String>>() {
                    @Override
                    public TableCell call(final TableColumn<TbExperimentDesign, String> param) {
                        final TableCell<TbExperimentDesign, String> cell = new TableCell<TbExperimentDesign, String>() {

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
                                        TbExperimentDesign tbExperimentDesign = getTableView().getItems().get(getIndex());
                                        experimentDesignService.deleteExperimentDesign(tbExperimentDesign);
                                        list.removeAll(list);
                                        ObservableList<TbExperimentDesign> lister = getList();
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




        tableView.getColumns().addAll(designName, designDescription, designSampling, arrangement , actionUpdate, actionDelete );



        Pane root = (Pane) this.scene.getRoot();
        this.scene.setRoot(root);
        return this.scene;
    }

    private ObservableList<TbExperimentDesign> getList() {
        List<TbExperimentDesign> equipments = experimentDesignService.getAllExperimentDesign();
        ObservableList<TbExperimentDesign> list = FXCollections.observableArrayList(equipments);
        return list;
    }

    @HandleEvent(nodeName = "createDesign")
    public EventPair createDesign(){
        EventPair pair = new EventPair();

        EventHandler eventHandler = (x) -> {
            Stage stage = stageHolder.getStage();


            stage.setScene(createDesignComponent.getScene());
            stage.show();
        };

        pair.setEventHandler(eventHandler);
        pair.setEventType(MouseEvent.MOUSE_CLICKED);

        return pair;
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

    @HandleEvent(nodeName = "buttonExperiment")
    public EventPair transitionToSubjects(){
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

    @HandleEvent(nodeName = "buttonExperimentType")
    public EventPair transitionToExperimentType(){
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


}
