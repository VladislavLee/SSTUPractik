package com.sstu.practic.spring.components.experiment;

import com.sstu.practic.spring.annotations.HandleEvent;
import com.sstu.practic.spring.annotations.JavaFxComponent;
import com.sstu.practic.spring.components.FxComponent;
import com.sstu.practic.spring.components.MainComponent;
import com.sstu.practic.spring.data.model.TbExperiment;
import com.sstu.practic.spring.data.model.TbFavoriteExperiment;
import com.sstu.practic.spring.services.EquipmentService;
import com.sstu.practic.spring.services.ExperimentService;
import com.sstu.practic.spring.services.FavoriteExperimentService;
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
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@JavaFxComponent(path = "/view/listFavoriteExperiment.fxml")
public class ListFavoriteExperimentComponent extends FxComponent{
    @Autowired
    private StageHolder stageHolder;
    @Autowired
    private FavoriteExperimentService favoriteExperimentService;
    @Autowired
    private MainComponent mainComponent;
    @Autowired
    private ExperimentService experimentService;
    @Autowired
    private ListFavoriteExperimentComponent listFavoriteExperimentComponent;


    private void addExperiment(TbExperiment tbExperiment){
        favoriteExperimentService.addFavoriteExperiment(tbExperiment);
    }

//    @HandleEvent(nodeName = "createExperiment")
//    public EventPair eventHandler() {
//        EventPair pair = new EventPair();
//
//        EventHandler eventHandler = (x) -> {
//            Stage stage = stageHolder.getStage();
//
//            experimentService.getAllExperiment().stream().forEach(favoriteExperimentService::addFavoriteExperiment);
//
//            List<TbFavoriteExperiment> list = favoriteExperimentService.getFavoriteExperiment();
//
//
//        };
//
//        pair.setEventHandler(eventHandler);
//        pair.setEventType(MouseEvent.MOUSE_CLICKED);
//
//        return pair;
//    }


    @PostConstruct
    public void init() {
        TableView tableView =(TableView) scene.lookup("#listFavoriteExperiment");

        TableColumn<TbFavoriteExperiment, String> experimentType
                = new TableColumn<TbFavoriteExperiment, String>("Тип");
        TableColumn< TbFavoriteExperiment, String> experimentSubject
                = new TableColumn<TbFavoriteExperiment, String>("Испытуемый");
        TableColumn< TbFavoriteExperiment, String> experimentDesign
                = new TableColumn<TbFavoriteExperiment, String>("План");
        TableColumn< TbFavoriteExperiment, String> mood
                = new TableColumn<TbFavoriteExperiment, String>("Настроение");
        TableColumn< TbFavoriteExperiment, String> date
                = new TableColumn<TbFavoriteExperiment, String>("Дата");
        TableColumn< TbFavoriteExperiment, String> recordDuration
                = new TableColumn<TbFavoriteExperiment, String>("Длительность сессии");
        TableColumn< TbFavoriteExperiment, String> subjectWeight
                = new TableColumn<TbFavoriteExperiment, String>("Вес испытуемого");
        TableColumn< TbFavoriteExperiment, String> experimentComments
                = new TableColumn<TbFavoriteExperiment, String>("Коментарии");
        TableColumn actionDelete
                = new TableColumn("Delete");
        actionDelete.setSortable(false);





        experimentType.setCellValueFactory(new PropertyValueFactory<>("vcExperimentType"));
        experimentSubject.setCellValueFactory(new PropertyValueFactory<>("vcExperimentSubject"));
        experimentDesign.setCellValueFactory(new  PropertyValueFactory<>("vcExperimentDesign"));
        mood.setCellValueFactory(new PropertyValueFactory<>("vcMood"));
        date.setCellValueFactory(new PropertyValueFactory<>("vcDate"));
        recordDuration.setCellValueFactory(new PropertyValueFactory<>("vcRecordDuration"));
        subjectWeight.setCellValueFactory(new PropertyValueFactory<>("vcSubjectWeight"));
        experimentComments.setCellValueFactory(new PropertyValueFactory<>("vcDescription"));
        actionDelete.setCellValueFactory(new PropertyValueFactory<>("DUMMY"));
//        experimentService.getAllExperiment().stream().forEach(favoriteExperimentService::addFavoriteExperiment);

        List<TbFavoriteExperiment> list = favoriteExperimentService.getFavoriteExperiment();

        ObservableList<TbExperiment> observableList = getList();



        Callback<TableColumn<TbExperiment, String>, TableCell<TbExperiment, String>> createButtonDelete
                =
                new Callback<TableColumn<TbExperiment, String>, TableCell<TbExperiment, String>>() {
                    @Override
                    public TableCell call(final TableColumn<TbExperiment, String> param) {
                        final TableCell<TbExperiment, String> cell = new TableCell<TbExperiment, String>() {

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
                                        TbFavoriteExperiment tbFavoriteExperiment = list.get(getIndex());
                                        favoriteExperimentService.deleteFavoriteExperiment(tbFavoriteExperiment.getIdFavoriteExperiment());
                                        list.removeAll(list);
                                        ObservableList<TbExperiment> lister = getList();
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




        actionDelete.setCellFactory(createButtonDelete);

        tableView.setItems(observableList);

        tableView.getColumns().addAll(experimentType, experimentSubject, experimentDesign, mood, date, recordDuration, subjectWeight,
                experimentComments , actionDelete);

        Pane root = (Pane) this.scene.getRoot();
        root.setPadding(new Insets(10));
        root.getChildren().add(tableView);
        this.scene.setRoot(root);
    }


    private ObservableList<TbExperiment> getList() {
        List<TbFavoriteExperiment> list = favoriteExperimentService.getFavoriteExperiment();
        ObservableList<TbFavoriteExperiment> experimentObservableList = FXCollections.observableArrayList(list);
        ObservableList<TbExperiment> observableList = FXCollections.observableArrayList();

        for (int i = 0; i < experimentObservableList.size(); i++) {
            observableList.add(i, experimentObservableList.get(i).getTbExperiment());
        }

        return observableList;
    }


}
