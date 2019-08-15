package com.sstu.practic.spring.components.channel;

import com.sstu.practic.spring.annotations.HandleEvent;
import com.sstu.practic.spring.annotations.JavaFxComponent;
import com.sstu.practic.spring.components.FxComponent;
import com.sstu.practic.spring.components.MainComponent;
import com.sstu.practic.spring.components.arrangement.ListArrangementComponent;
import com.sstu.practic.spring.components.design.ListDesignComponent;
import com.sstu.practic.spring.components.equipment.ListEquipmentsComponent;
import com.sstu.practic.spring.components.experimentSubject.ListExperimentSubject;
import com.sstu.practic.spring.components.mood.ListMoodComponent;
import com.sstu.practic.spring.components.processingMethod.ListProcessingMethodsComponents;
import com.sstu.practic.spring.components.user.ListUserComponent;
import com.sstu.practic.spring.data.model.TbChannels;
import com.sstu.practic.spring.services.ChannelService;
import com.sstu.practic.spring.utils.StageHolder;
import com.sstu.practic.spring.utils.entities.EventPair;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.springframework.beans.factory.annotation.Autowired;
import javax.annotation.PostConstruct;
import java.util.List;

@JavaFxComponent(path = "/view/listChannel.fxml")
public class ListChannelComponent extends FxComponent {
    @Autowired
    private StageHolder stageHolder;
    @Autowired
    private ChannelService channelService;
    @Autowired
    private MainComponent mainComponent;
    @Autowired
    private ListChannelComponent listChannelComponent;
    @Autowired
    private EditChannelComponent editChannelComponent;
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

    @PostConstruct
    public void init() {
        TableView tableView =(TableView) scene.lookup("#listChannel");
        TableColumn<TbChannels, String> channelName
                = new TableColumn<TbChannels, String>("Название");

        TableColumn< TbChannels, String> channelDescription
                = new TableColumn<TbChannels, String>("Краткое описание");

        TableColumn col_action = new TableColumn<>("Update");

        TableColumn actionDelete= new TableColumn("Delete");
        col_action.setSortable(false);
        actionDelete.setSortable(false);

        channelName.setCellValueFactory(new PropertyValueFactory<>("vcName"));
        channelDescription.setCellValueFactory(new PropertyValueFactory<>("vcDescription"));


        col_action.setCellValueFactory(new PropertyValueFactory<>("DUMMY"));
        actionDelete.setCellValueFactory(new PropertyValueFactory<>("DUMMY"));
        ObservableList<TbChannels> list = getList();
        tableView.setItems(list);

        Callback<TableColumn<TbChannels, String>, TableCell<TbChannels, String>> cellFactory
                =
                new Callback<TableColumn<TbChannels, String>, TableCell<TbChannels, String>>() {
                    @Override
                    public TableCell call(final TableColumn<TbChannels, String> param) {
                        final TableCell<TbChannels, String> cell = new TableCell<TbChannels, String>() {

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
                                        TbChannels tbChannels = getTableView().getItems().get(getIndex());
                                        Stage stage = stageHolder.getStage();
                                        stage.setScene(editChannelComponent.getScene(tbChannels));
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

        Callback<TableColumn<TbChannels, String>, TableCell<TbChannels, String>> createButtonDelete
                =
                new Callback<TableColumn<TbChannels, String>, TableCell<TbChannels, String>>() {
                    @Override
                    public TableCell call(final TableColumn<TbChannels, String> param) {
                        final TableCell<TbChannels, String> cell = new TableCell<TbChannels, String>() {

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
                                        TbChannels tbChannels = getTableView().getItems().get(getIndex());
                                        channelService.deleteChannel(tbChannels);
                                        list.removeAll(list);
                                        ObservableList<TbChannels> lister = getList();
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




        col_action.setCellFactory(cellFactory);
        actionDelete.setCellFactory(createButtonDelete);


        tableView.getColumns().addAll(channelName, channelDescription, col_action, actionDelete);



        Pane root = (Pane) this.scene.getRoot();
        root.setPadding(new Insets(10));
        root.getChildren().add(tableView);
        this.scene.setRoot(root);
    }

    private ObservableList<TbChannels> getList() {
        List<TbChannels> channels = channelService.getAllChannels();
        ObservableList<TbChannels> list = FXCollections.observableArrayList(channels);
        return list;
    }




    //navigation


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



}
