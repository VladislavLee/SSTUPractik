package com.sstu.practic.spring.components.mood;

import com.sstu.practic.spring.annotations.HandleEvent;
import com.sstu.practic.spring.annotations.JavaFxComponent;
import com.sstu.practic.spring.components.FxComponent;
import com.sstu.practic.spring.components.MainComponent;
import com.sstu.practic.spring.components.arrangement.ListArrangementComponent;
import com.sstu.practic.spring.components.banner.BannerComponent;
import com.sstu.practic.spring.components.channel.ListChannelComponent;
import com.sstu.practic.spring.components.design.ListDesignComponent;
import com.sstu.practic.spring.components.equipment.ListEquipmentsComponent;
import com.sstu.practic.spring.components.experiment.ListExperimentComponent;
import com.sstu.practic.spring.components.experiment.ListMyExperimentComponent;
import com.sstu.practic.spring.components.experiment.ListMyExperimentForUserComponent;
import com.sstu.practic.spring.components.experimentSubject.ListExperimentSubject;
import com.sstu.practic.spring.components.processingMethod.ListProcessingMethodsComponents;
import com.sstu.practic.spring.components.user.ListUserComponent;
import com.sstu.practic.spring.data.model.TbMood;
import com.sstu.practic.spring.services.MoodService;
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

@JavaFxComponent(path = "/view/listMoods.fxml")
public class ListMoodComponent extends FxComponent {
    @Autowired
    private StageHolder stageHolder;
    @Autowired
    private MoodService moodService;
    @Autowired
    private MainComponent mainComponent;
    @Autowired
    private EditMoodComponent editMoodComponent;
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
    private ListExperimentComponent listExperimentComponent;
    @Autowired
    private CreateMoodComponent createMoodComponent;
    @Autowired
    private SecurityContext securityContext;
    @Autowired
    private BannerComponent bannerComponent;
    @Autowired
    private ListMyExperimentComponent listMyExperimentForUserComponent;

    @Override
    public Scene getScene() {
        TableView tableView =(TableView) scene.lookup("#listMood");

        TableColumn<TbMood, String> moodName
                = new TableColumn<TbMood, String>("Название");

        TableColumn<TbMood, String> moodDescription
                = new TableColumn<TbMood, String>("Описание");

        TableColumn actionUpdate = new TableColumn<>("Обновить");
        TableColumn actionDelete= new TableColumn("Удалить");

        actionUpdate.setSortable(false);
        actionDelete.setSortable(false);

        moodName.setCellValueFactory(new PropertyValueFactory<>("vcName"));
        moodDescription.setCellValueFactory(new PropertyValueFactory<>("vcDescription"));
        actionUpdate.setCellValueFactory(new PropertyValueFactory<>("DUMMY"));
        actionDelete.setCellValueFactory(new PropertyValueFactory<>("DUMMY"));

        ObservableList<TbMood> list = getList();
        tableView.setItems(list);


        Callback<TableColumn<TbMood, String>, TableCell<TbMood, String>> cellFactory
                =
                new Callback<TableColumn<TbMood, String>, TableCell<TbMood, String>>() {
                    @Override
                    public  TableCell call(final TableColumn<TbMood, String> param) {
                        final TableCell<TbMood, String> cell = new TableCell<TbMood, String>() {

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
                                        TbMood tbMood = getTableView().getItems().get(getIndex());
                                        Stage stage = stageHolder.getStage();

                                        if(securityContext.getUser().getVcRole() == Role.USER){
                                            stage.setScene(bannerComponent.getScene());
                                            stage.show();
                                        } else {
                                            editMoodComponent.setTextField(tbMood);
                                            stage.setScene(editMoodComponent.getScene(tbMood));
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

        Callback<TableColumn<TbMood, String>, TableCell<TbMood, String>> createButtonDelete
                =
                new Callback<TableColumn<TbMood, String>, TableCell<TbMood, String>>() {
                    @Override
                    public TableCell call(final TableColumn<TbMood, String> param) {
                        final TableCell<TbMood, String> cell = new TableCell<TbMood, String>() {

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
                                        TbMood tbMood = getTableView().getItems().get(getIndex());

                                        Stage stage = stageHolder.getStage();

                                        if(securityContext.getUser().getVcRole() == Role.USER){
                                            stage.setScene(bannerComponent.getScene());
                                            stage.show();
                                        } else {
                                            moodService.deleteMood(tbMood);
                                            list.removeAll(list);
                                            ObservableList<TbMood> lister = getList();
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



        actionUpdate.setCellFactory(cellFactory);
        actionDelete.setCellFactory(createButtonDelete);




        if (tableView.getColumns().isEmpty()) {
            tableView.getColumns().addAll(moodName, moodDescription, actionUpdate, actionDelete);
        }


        Pane root = (Pane) this.scene.getRoot();
        root.setPadding(new Insets(10));
        this.scene.setRoot(root);
        return this.scene;
    }

    private ObservableList<TbMood> getList() {
        List<TbMood> moods = moodService.getAllMoods();
        ObservableList<TbMood> list = FXCollections.observableArrayList(moods);
        return list;
    }

    @HandleEvent(nodeName = "createMood")
    public EventPair createMood(){
        EventPair pair = new EventPair();
        EventHandler eventHandler = (x) -> {
            Stage stage = stageHolder.getStage();
            stage.setScene(createMoodComponent.getScene());
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

    @HandleEvent(nodeName = "buttonExperimentSubject")
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
                stage.setScene(listMyExperimentForUserComponent.getScene());
                stage.show();
            }
        };

        pair.setEventHandler(eventHandler);
        pair.setEventType(MouseEvent.MOUSE_CLICKED);

        return pair;
    }

    @HandleEvent(nodeName = "buttonProcessingMethod")
    public EventPair transitionToProcess(){
        EventPair pairEquipment = new EventPair();

        EventHandler eventHandler = (x) -> {
            Stage stage = stageHolder.getStage();


            stage.setScene(listProcessingMethodsComponents.getScene());
            stage.show();
        };

        pairEquipment.setEventHandler(eventHandler);
        pairEquipment.setEventType(MouseEvent.MOUSE_CLICKED);

        return pairEquipment;
    }



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


}
