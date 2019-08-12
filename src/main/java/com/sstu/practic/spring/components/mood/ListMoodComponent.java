package com.sstu.practic.spring.components.mood;

import com.sstu.practic.spring.annotations.JavaFxComponent;
import com.sstu.practic.spring.components.FxComponent;
import com.sstu.practic.spring.components.MainComponent;
import com.sstu.practic.spring.components.arrangement.ListArrangementComponent;
import com.sstu.practic.spring.components.channel.ListChannelComponent;
import com.sstu.practic.spring.components.design.ListDesignComponent;
import com.sstu.practic.spring.components.equipment.ListEquipmentsComponent;
import com.sstu.practic.spring.components.experimentSubject.ListExperimentSubject;
import com.sstu.practic.spring.components.processingMethod.ListProcessingMethodsComponents;
import com.sstu.practic.spring.components.user.ListUserComponent;
import com.sstu.practic.spring.data.model.TbMood;
import com.sstu.practic.spring.services.MoodService;
import com.sstu.practic.spring.utils.StageHolder;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
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

    @PostConstruct
    public void init() {
        TableView tableView =(TableView) scene.lookup("#listMood");

        TableColumn<TbMood, String> moodName
                = new TableColumn<TbMood, String>("Название");

        TableColumn<TbMood, String> moodDescription
                = new TableColumn<TbMood, String>("Описание");

        TableColumn actionUpdate = new TableColumn<>("Update");
        TableColumn actionDelete= new TableColumn("Delete");

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
                                        TbMood tbMood = getTableView().getItems().get(getIndex());
                                        Stage stage = stageHolder.getStage();
                                        editMoodComponent.setTextField(tbMood);
                                        stage.setScene(editMoodComponent.getScene(tbMood));
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

        Callback<TableColumn<TbMood, String>, TableCell<TbMood, String>> createButtonDelete
                =
                new Callback<TableColumn<TbMood, String>, TableCell<TbMood, String>>() {
                    @Override
                    public TableCell call(final TableColumn<TbMood, String> param) {
                        final TableCell<TbMood, String> cell = new TableCell<TbMood, String>() {

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
                                        TbMood tbMood = getTableView().getItems().get(getIndex());
                                        moodService.deleteMood(tbMood);
                                        list.removeAll(list);
                                        ObservableList<TbMood> lister = getList();
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



        tableView.getColumns().addAll(moodName, moodDescription, actionUpdate, actionDelete );


        Pane root = (Pane) this.scene.getRoot();
        root.setPadding(new Insets(10));
        root.getChildren().add(tableView);
        this.scene.setRoot(root);

    }

    private ObservableList<TbMood> getList() {
        List<TbMood> moods = moodService.getAllMoods();
        ObservableList<TbMood> list = FXCollections.observableArrayList(moods);
        return list;
    }



}
