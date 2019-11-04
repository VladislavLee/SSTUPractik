package com.sstu.practic.spring.components.group;

import com.sstu.practic.spring.annotations.HandleEvent;
import com.sstu.practic.spring.annotations.JavaFxComponent;
import com.sstu.practic.spring.components.FxComponent;
import com.sstu.practic.spring.components.MainComponent;
import com.sstu.practic.spring.components.banner.BannerComponent;
import com.sstu.practic.spring.components.experiment.EditExperimentComponent;
import com.sstu.practic.spring.data.model.TbExperiment;
import com.sstu.practic.spring.data.model.TbGroup;
import com.sstu.practic.spring.data.model.TbUser;
import com.sstu.practic.spring.services.EquipmentService;
import com.sstu.practic.spring.services.GroupService;
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
import java.util.List;

@JavaFxComponent(path = "/view/listGroup.fxml")
public class ListGroupComponent extends FxComponent{
    @Autowired
    private StageHolder stageHolder;
    @Autowired
    private GroupService groupService;
    @Autowired
    private MainComponent mainComponent;
    @Autowired
    private SecurityContext securityContext;
    @Autowired
    private BannerComponent bannerComponent;
    @Autowired
    private EditGroupComponent editGroupComponent;
    @Autowired
    private CreateGroupComponent createGroupComponent;
    @Override
    public Scene getScene() {
        TableView tableView =(TableView) scene.lookup("#listGroup");

        TableColumn<TbGroup, String> nameGroup
                = new TableColumn<TbGroup, String>("Название группы");

        TableColumn actionUpdate
                = new TableColumn<>("Обновить");
        TableColumn actionDelete
                = new TableColumn("Удалить");

        actionUpdate.setSortable(false);
        actionDelete.setSortable(false);


        nameGroup.setCellValueFactory(new PropertyValueFactory<>("vcName"));

        actionUpdate.setCellValueFactory(new PropertyValueFactory<>("DUMMY"));
        actionDelete.setCellValueFactory(new PropertyValueFactory<>("DUMMY"));


        ObservableList<TbGroup> list = getList(securityContext.getUser().getIdUser());
        tableView.setItems(list);


        Callback<TableColumn<TbGroup, String>, TableCell<TbGroup, String>> cellFactory
                =
                new Callback<TableColumn<TbGroup, String>, TableCell<TbGroup, String>>() {
                    @Override
                    public TableCell call(final TableColumn<TbGroup, String> param) {
                        final TableCell<TbGroup, String> cell = new TableCell<TbGroup, String>() {

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
                                        TbGroup tbGroup = getTableView().getItems().get(getIndex());
                                        Stage stage = stageHolder.getStage();


                                        if(securityContext.getUser().getVcRole() == Role.USER  ){
                                            stage.setScene(bannerComponent.getScene());
                                            stage.show();
                                        } else {
                                            editGroupComponent.setTextField(tbGroup);
                                            stage.setScene(editGroupComponent.getScene(tbGroup));
                                            stage.show();
                                            list.removeAll(list);
                                            ObservableList<TbGroup> lister = getList(securityContext.getUser().getIdUser());
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

        Callback<TableColumn<TbGroup, String>, TableCell<TbGroup, String>> createButtonDelete
                =
                new Callback<TableColumn<TbGroup, String>, TableCell<TbGroup, String>>() {
                    @Override
                    public TableCell call(final TableColumn<TbGroup, String> param) {
                        final TableCell<TbGroup, String> cell = new TableCell<TbGroup, String>() {

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
                                        TbGroup tbGroup = getTableView().getItems().get(getIndex());

                                        Stage stage = stageHolder.getStage();

                                        if(securityContext.getUser().getVcRole() == Role.USER){
                                            stage.setScene(mainComponent.getScene());
                                            stage.show();
                                        } else {
                                            groupService.deleteGroup(tbGroup);
                                            list.removeAll(list);
                                            ObservableList<TbGroup> lister = getList(securityContext.getUser().getIdUser());
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
            tableView.getColumns().addAll(nameGroup, actionUpdate, actionDelete);
        }


        Pane root = (Pane) this.scene.getRoot();
        root.setPadding(new Insets(10));
        this.scene.setRoot(root);
        return this.scene;
    }

    private ObservableList<TbGroup> getList(Integer id) {
        List<TbGroup> tbGroups = groupService.getMyGroup(id);
        ObservableList<TbGroup> list = FXCollections.observableArrayList(tbGroups);
        return list;
    }


    @HandleEvent(nodeName = "createGroup")
    public EventPair createGroup(){
        EventPair pair = new EventPair();

        EventHandler eventHandler = (x) -> {
            Stage stage = stageHolder.getStage();


            stage.setScene(createGroupComponent.getScene());
            stage.show();
        };

        pair.setEventHandler(eventHandler);
        pair.setEventType(MouseEvent.MOUSE_CLICKED);

        return pair;
    }



}
