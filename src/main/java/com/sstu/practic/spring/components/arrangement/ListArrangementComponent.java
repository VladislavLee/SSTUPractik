package com.sstu.practic.spring.components.arrangement;

import com.sstu.practic.spring.annotations.JavaFxComponent;
import com.sstu.practic.spring.components.FxComponent;
import com.sstu.practic.spring.components.MainComponent;
import com.sstu.practic.spring.components.channel.EditChannelComponent;
import com.sstu.practic.spring.components.channel.ListChannelComponent;
import com.sstu.practic.spring.data.model.TbArrangements;
import com.sstu.practic.spring.data.model.TbChannels;
import com.sstu.practic.spring.services.ArrangementService;
import com.sstu.practic.spring.services.ChannelService;
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

    @PostConstruct
    public void init() {
        TableView tableView =(TableView) scene.lookup("#listArrangement");
        TableColumn<TbArrangements, String> channelName
                = new TableColumn<TbArrangements, String>("Название");

        TableColumn< TbArrangements, String> channelDescription
                = new TableColumn<TbArrangements, String>("Краткое описание");

        TableColumn col_action = new TableColumn<>("Update");

        TableColumn actionDelete= new TableColumn("Delete");
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
                                        TbArrangements tbArrangements = getTableView().getItems().get(getIndex());
                                        Stage stage = stageHolder.getStage();
                                        stage.setScene(editArrangementComponent.getScene(tbArrangements));
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

        Callback<TableColumn<TbArrangements, String>, TableCell<TbArrangements, String>> createButtonDelete
                =
                new Callback<TableColumn<TbArrangements, String>, TableCell<TbArrangements, String>>() {
                    @Override
                    public TableCell call(final TableColumn<TbArrangements, String> param) {
                        final TableCell<TbArrangements, String> cell = new TableCell<TbArrangements, String>() {

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
                                        TbArrangements tbArrangements = getTableView().getItems().get(getIndex());
                                        arrangementService.deleteArrangement(tbArrangements);
                                        list.removeAll(list);
                                        ObservableList<TbArrangements> lister = getList();
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

    private ObservableList<TbArrangements> getList() {
        List<TbArrangements> arrangements = arrangementService.getAllArrangements();
        ObservableList<TbArrangements> list = FXCollections.observableArrayList(arrangements);
        return list;
    }
}
