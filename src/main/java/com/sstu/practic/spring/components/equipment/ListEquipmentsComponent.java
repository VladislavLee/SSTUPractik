package com.sstu.practic.spring.components.equipment;

import com.sstu.practic.spring.annotations.HandleEvent;
import com.sstu.practic.spring.annotations.JavaFxComponent;
import com.sstu.practic.spring.components.FxComponent;
import com.sstu.practic.spring.components.MainComponent;
import com.sstu.practic.spring.components.arrangement.ListArrangementComponent;
import com.sstu.practic.spring.components.channel.ListChannelComponent;
import com.sstu.practic.spring.components.design.ListDesignComponent;
import com.sstu.practic.spring.components.experimentSubject.ListExperimentSubject;
import com.sstu.practic.spring.components.mood.ListMoodComponent;
import com.sstu.practic.spring.components.processingMethod.EditProcessingMethodComponent;
import com.sstu.practic.spring.components.processingMethod.ListProcessingMethodsComponents;
import com.sstu.practic.spring.components.user.ListUserComponent;
import com.sstu.practic.spring.data.model.TbEquipment;
import com.sstu.practic.spring.data.model.TbExperiment;
import com.sstu.practic.spring.services.EquipmentService;
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
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

@JavaFxComponent(path = "/view/listEquipments.fxml")
public class ListEquipmentsComponent extends FxComponent {
    @Autowired
    private StageHolder stageHolder;
    @Autowired
    private EquipmentService equipmentService;
    @Autowired
    private MainComponent mainComponent;
    @Autowired
    private EditEquipmentComponent editEquipmentComponent;
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
    @Autowired
    private ListMoodComponent listMoodComponent;
    @Autowired
    private CreateEquipmentComponent createEquipmentComponent;


    @Override
    public Scene getScene() {
        TableView tableView =(TableView) scene.lookup("#listEquipment");

        TableColumn<TbEquipment, String> equipmentName
                = new TableColumn<TbEquipment, String>("Название");
        TableColumn< TbEquipment, String> equipmentSpecification
                = new TableColumn<TbEquipment, String>("Краткое описание");
        TableColumn< TbEquipment, String> certificatePath
                = new TableColumn<TbEquipment, String>("Сертификат");
        TableColumn< TbEquipment, String> certificateOutput
                = new TableColumn<TbEquipment, String>("Выходные данные сертификата");
        TableColumn actionUpdate
                = new TableColumn<>("Update");
        TableColumn actionDelete
                = new TableColumn("Delete");

        actionUpdate.setSortable(false);
        actionDelete.setSortable(false);


        equipmentName.setCellValueFactory(new PropertyValueFactory<>("vcName"));
        equipmentSpecification.setCellValueFactory(new PropertyValueFactory<>("vcDescription"));
        certificatePath.setCellValueFactory(new  PropertyValueFactory<>("dsf"));
        certificateOutput.setCellValueFactory(new PropertyValueFactory<>("vcCertificateOutput"));
        actionUpdate.setCellValueFactory(new PropertyValueFactory<>("DUMMY"));
        actionDelete.setCellValueFactory(new PropertyValueFactory<>("DUMMY"));


        ObservableList<TbEquipment> list = getList();
        tableView.setItems(list);


        Callback<TableColumn<TbEquipment, String>, TableCell<TbEquipment, String>> certificate
                =
                new Callback<TableColumn<TbEquipment, String>, TableCell<TbEquipment, String>>() {
                    @Override
                    public TableCell call(final TableColumn<TbEquipment, String> param) {
                        final TableCell<TbEquipment, String> cell = new TableCell<TbEquipment, String>() {


                            final Button btn = new Button("certificate");

                            @Override
                            public void updateItem(String item, boolean empty) {
                                super.updateItem(item, empty);
                                if (empty) {
                                    setGraphic(null);
                                    setText(null);
                                } else {
                                    btn.setId("Certificate");
                                    btn.setOnAction(event -> {
                                        TbEquipment tbEquipment = getTableView().getItems().get(getIndex());
                                        String vcCertificateName = getTableView().getItems().get(getIndex()).getVcCertificateName();
                                        byte[] certificate = getTableView().getItems().get(getIndex()).getVcCertificate();
                                        FileOutputStream fileOutputStream = null;
                                        try {
                                            fileOutputStream = new FileOutputStream("/Users/vlad/Documents/ProjectPractic/practic/download/".concat(vcCertificateName));

                                        } catch (FileNotFoundException e) {
                                            e.printStackTrace();
                                        }

                                        try {
                                            fileOutputStream.write(certificate);
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }

                                        try {
                                            fileOutputStream.close();
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }

                                        Stage stage = stageHolder.getStage();
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


        Callback<TableColumn<TbEquipment, String>, TableCell<TbEquipment, String>> cellFactory
                =
                new Callback<TableColumn<TbEquipment, String>, TableCell<TbEquipment, String>>() {
                    @Override
                    public TableCell call(final TableColumn<TbEquipment, String> param) {
                        final TableCell<TbEquipment, String> cell = new TableCell<TbEquipment, String>() {

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
                                        TbEquipment tbEquipment = getTableView().getItems().get(getIndex());
                                        Stage stage = stageHolder.getStage();
                                        editEquipmentComponent.setTextField(tbEquipment);
                                        stage.setScene(editEquipmentComponent.getScene(tbEquipment));
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

        Callback<TableColumn<TbEquipment, String>, TableCell<TbEquipment, String>> createButtonDelete
                =
                new Callback<TableColumn<TbEquipment, String>, TableCell<TbEquipment, String>>() {
                    @Override
                    public TableCell call(final TableColumn<TbEquipment, String> param) {
                        final TableCell<TbEquipment, String> cell = new TableCell<TbEquipment, String>() {

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
                                        TbEquipment tbEquipment = getTableView().getItems().get(getIndex());
                                        equipmentService.deleteEquipments(tbEquipment);
                                        list.removeAll(list);
                                        ObservableList<TbEquipment> lister = getList();
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
        certificatePath.setCellFactory(certificate);

        tableView.getColumns().addAll(equipmentName, equipmentSpecification, certificatePath,  actionUpdate, actionDelete );

        Pane root = (Pane) this.scene.getRoot();
        root.setPadding(new Insets(10));
        this.scene.setRoot(root);
        return this.scene;
    }

    private ObservableList<TbEquipment> getList() {
        List<TbEquipment> equipments = equipmentService.getAllEquipments();
        ObservableList<TbEquipment> list = FXCollections.observableArrayList(equipments);
        return list;
    }


    @HandleEvent(nodeName = "createEquipment")
    public EventPair createEquipment(){
        EventPair pair = new EventPair();
        EventHandler eventHandler = (x) -> {
            Stage stage = stageHolder.getStage();

            stage.setScene(createEquipmentComponent.getScene());
            stage.show();
        };

        pair.setEventHandler(eventHandler);
        pair.setEventType(MouseEvent.MOUSE_CLICKED);

        return pair;
    }


    //navigation

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


    @HandleEvent(nodeName = "buttonExperimentSubjects")
    public EventPair transition1(){
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


}
