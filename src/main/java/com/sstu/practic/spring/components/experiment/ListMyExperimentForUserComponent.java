package com.sstu.practic.spring.components.experiment;

import com.sstu.practic.spring.annotations.JavaFxComponent;
import com.sstu.practic.spring.components.FxComponent;
import com.sstu.practic.spring.components.MainComponent;
import com.sstu.practic.spring.components.arrangement.ListArrangementComponent;
import com.sstu.practic.spring.components.design.ListDesignComponent;
import com.sstu.practic.spring.components.experimentSubject.ListExperimentSubject;
import com.sstu.practic.spring.components.experimentType.ListExperimentTypeComponent;
import com.sstu.practic.spring.components.processingMethod.EditProcessingMethodComponent;
import com.sstu.practic.spring.components.processingMethod.ListProcessingMethodsComponents;
import com.sstu.practic.spring.components.user.ListUserComponent;
import com.sstu.practic.spring.data.model.TbExperiment;
import com.sstu.practic.spring.services.EquipmentService;
import com.sstu.practic.spring.services.ExperimentService;
import com.sstu.practic.spring.services.FavoriteExperimentService;
import com.sstu.practic.spring.services.MyExperimentService;
import com.sstu.practic.spring.services.security.SecurityContext;
import com.sstu.practic.spring.utils.StageHolder;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@JavaFxComponent(path = "/view/oldPages/listMyExperiment.fxml")
public class ListMyExperimentForUserComponent extends FxComponent {
    @Autowired
    private MyExperimentService myExperimentService;
    @Autowired
    private StageHolder stageHolder;
    @Autowired
    private EquipmentService equipmentService;
    @Autowired
    private MainComponent mainComponent;
    @Autowired
    private EditExperimentComponent editExperimentComponent;
    @Autowired
    private EditProcessingMethodComponent editProcessingMethodComponent;
    @Autowired
    private ExperimentService experimentService;
    @Autowired
    private ListProcessingMethodsComponents listProcessingMethodsComponents;
    @Autowired
    private ListUserComponent listUserComponent;
    @Autowired
    private ListExperimentSubject listExperimentSubject;
    @Autowired
    private ListExperimentTypeComponent listExperimentTypeComponent;
    @Autowired
    private FavoriteExperimentService favoriteExperimentService;
    @Autowired
    private SecurityContext securityContext;
    @Autowired
    private ListFavoriteExperimentComponent listFavoriteExperimentComponent;
    @Autowired
    private ListArrangementComponent listArrangementComponent;
    @Autowired
    private ListDesignComponent listDesignComponent;
    @Autowired
    private ListMyExperimentComponent listMyExperimentComponent;
    @Autowired
    private ListExperimentComponent listExperimentComponent;
    @Autowired
    private CreateExperimentComponent createExperimentComponent;


    @Override
    public Scene getScene() {
        TableView tableView =(TableView) scene.lookup("#listMyExperiment");

        TableColumn<TbExperiment, String> experimentType
                = new TableColumn<TbExperiment, String>("Тип");
        TableColumn< TbExperiment, String> experimentSubject
                = new TableColumn<TbExperiment, String>("Испытуемый");
        TableColumn< TbExperiment, String> experimentDesign
                = new TableColumn<TbExperiment, String>("План");
        TableColumn< TbExperiment, String> mood
                = new TableColumn<TbExperiment, String>("Настроение");
        TableColumn< TbExperiment, String> date
                = new TableColumn<TbExperiment, String>("Дата");
        TableColumn< TbExperiment, String> recordDuration
                = new TableColumn<TbExperiment, String>("Длительность сессии");
        TableColumn< TbExperiment, String> subjectWeight
                = new TableColumn<TbExperiment, String>("Вес испытуемого");
        TableColumn< TbExperiment, String> experimentComments
                = new TableColumn<TbExperiment, String>("Коментарии");



        experimentType.setCellValueFactory(new PropertyValueFactory<>("vcExperimentType"));
        experimentSubject.setCellValueFactory(new PropertyValueFactory<>("vcExperimentSubject"));
        experimentDesign.setCellValueFactory(new  PropertyValueFactory<>("vcExperimentDesign"));
        mood.setCellValueFactory(new PropertyValueFactory<>("vcMood"));
        date.setCellValueFactory(new PropertyValueFactory<>("vcDate"));
        recordDuration.setCellValueFactory(new PropertyValueFactory<>("vcRecordDuration"));
        subjectWeight.setCellValueFactory(new PropertyValueFactory<>("vcSubjectWeight"));
        experimentComments.setCellValueFactory(new PropertyValueFactory<>("vcDescription"));



        List<TbExperiment> experiments = getList(securityContext.getUser().getIdUser());
        ObservableList<TbExperiment> list = FXCollections.observableArrayList(experiments);
        tableView.setItems(list);

        tableView.getColumns().addAll(experimentType, experimentSubject, experimentDesign, mood, date,
                recordDuration, subjectWeight, experimentComments);

        Pane root = (Pane) this.scene.getRoot();
        root.setPadding(new Insets(10));
        root.getChildren().add(tableView);
        this.scene.setRoot(root);
        return scene;
    }


    private ObservableList<TbExperiment> getList(Integer id) {
        List<TbExperiment> experiments = myExperimentService.getMyExperiment();
        ObservableList<TbExperiment> list = FXCollections.observableArrayList(experiments);
        return list;
    }

}
