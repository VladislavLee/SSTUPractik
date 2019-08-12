package com.sstu.practic.spring.components.design;

import com.sstu.practic.spring.annotations.JavaFxComponent;
import com.sstu.practic.spring.components.FxComponent;
import com.sstu.practic.spring.components.MainComponent;
import com.sstu.practic.spring.components.equipment.EditEquipmentComponent;
import com.sstu.practic.spring.data.model.TbEquipment;
import com.sstu.practic.spring.data.model.TbExperimentDesign;
import com.sstu.practic.spring.services.EquipmentService;
import com.sstu.practic.spring.services.ExperimentDesignService;
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

    @PostConstruct
    public void init() {
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
        root.setPadding(new Insets(10));
        root.getChildren().add(tableView);
        this.scene.setRoot(root);
    }

    private ObservableList<TbExperimentDesign> getList() {
        List<TbExperimentDesign> equipments = experimentDesignService.getAllExperimentDesign();
        ObservableList<TbExperimentDesign> list = FXCollections.observableArrayList(equipments);
        return list;
    }
}
