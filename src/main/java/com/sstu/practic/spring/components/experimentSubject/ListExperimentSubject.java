package com.sstu.practic.spring.components.experimentSubject;

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
import com.sstu.practic.spring.components.mood.ListMoodComponent;
import com.sstu.practic.spring.components.processingMethod.EditProcessingMethodComponent;
import com.sstu.practic.spring.components.processingMethod.ListProcessingMethodsComponents;
import com.sstu.practic.spring.components.user.ListUserComponent;
import com.sstu.practic.spring.data.model.TbExperimentSubject;
import com.sstu.practic.spring.services.ExperimentSubjectService;
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

@JavaFxComponent(path = "/view/listSubject.fxml")
public class ListExperimentSubject extends FxComponent {
    @Autowired
    private StageHolder stageHolder;
    @Autowired
    private ExperimentSubjectService experimentSubjectService;
    @Autowired
    private MainComponent mainComponent;
    @Autowired
    private EditExperimentSubjectComponent editExperimentSubjectComponent;
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
    private CreateExperimentSubjectComponent createExperimentSubjectComponent;
    @Autowired
    private SecurityContext securityContext;
    @Autowired
    private BannerComponent bannerComponent;
    @Autowired
    private ListMyExperimentComponent listMyExperimentComponent;
    @Autowired
    private ListExperimentComponent listExperimentComponent;


    @Override
    public Scene getScene() {
        TableView tableView =(TableView) scene.lookup("#listSubject");

        TableColumn<TbExperimentSubject, String>  subjectFirstName
                = new TableColumn<TbExperimentSubject, String>("FirstName");
        TableColumn<TbExperimentSubject, String> subjectSecondName
                = new TableColumn<TbExperimentSubject, String>("SecondName");
        TableColumn<TbExperimentSubject, String> subjectLastName
                = new TableColumn<TbExperimentSubject, String>("LastName");
        TableColumn<TbExperimentSubject, String> subjectBirthday
                = new TableColumn<TbExperimentSubject, String>("Birthday ");
        TableColumn<TbExperimentSubject, String> gender
                = new TableColumn<TbExperimentSubject, String>("gender ");
        TableColumn<TbExperimentSubject, String> leadingHand
                = new TableColumn<TbExperimentSubject, String>("leadingHand ");
        TableColumn<TbExperimentSubject, String> leadingLeg
                = new TableColumn<TbExperimentSubject, String>("leadingLeg ");
        TableColumn<TbExperimentSubject, String> leadingEye
                = new TableColumn<TbExperimentSubject, String>("leadingEye ");
        TableColumn<TbExperimentSubject, String> subjectComments
                = new TableColumn<TbExperimentSubject, String>("subjectComments ");
        TableColumn actionUpdate
                = new TableColumn<>("Обновить");
        TableColumn actionDelete
                = new TableColumn("Удалить");

        actionUpdate.setSortable(false);
        actionDelete.setSortable(false);

        ObservableList<TbExperimentSubject> list = getList();
        tableView.setItems(list);


        Callback<TableColumn<TbExperimentSubject, String>, TableCell<TbExperimentSubject, String>> cellFactory
                =
                new Callback<TableColumn<TbExperimentSubject, String>, TableCell<TbExperimentSubject, String>>() {
                    @Override
                    public TableCell call(final TableColumn<TbExperimentSubject, String> param) {
                        final TableCell<TbExperimentSubject, String> cell = new TableCell<TbExperimentSubject, String>() {

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
                                        TbExperimentSubject tbExperimentSubject = getTableView().getItems().get(getIndex());


                                        Stage stage = stageHolder.getStage();

                                        if(securityContext.getUser().getVcRole() == Role.USER){
                                            stage.setScene(bannerComponent.getScene());
                                            stage.show();
                                        } else {
                                            editExperimentSubjectComponent.setTextField(tbExperimentSubject);
                                            stage.setScene(editExperimentSubjectComponent.getScene(tbExperimentSubject));
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

        Callback<TableColumn<TbExperimentSubject, String>, TableCell<TbExperimentSubject, String>> createButtonDelete
                =
                new Callback<TableColumn<TbExperimentSubject, String>, TableCell<TbExperimentSubject, String>>() {
                    @Override
                    public TableCell call(final TableColumn<TbExperimentSubject, String> param) {
                        final TableCell<TbExperimentSubject, String> cell = new TableCell<TbExperimentSubject, String>() {

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
                                        TbExperimentSubject tbExperimentSubject = getTableView().getItems().get(getIndex());

                                        Stage stage = stageHolder.getStage();

                                        if(securityContext.getUser().getVcRole() == Role.USER){
                                            stage.setScene(bannerComponent.getScene());
                                            stage.show();
                                        } else {
                                            experimentSubjectService.deleteSubject(tbExperimentSubject);
                                            list.removeAll(list);
                                            ObservableList<TbExperimentSubject> lister = getList();
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



        subjectFirstName.setCellValueFactory(new PropertyValueFactory<>("vcFirstName"));
        subjectSecondName.setCellValueFactory(new PropertyValueFactory<>("vcSecondName"));
        subjectLastName.setCellValueFactory(new PropertyValueFactory<>("vcLastName"));
        subjectBirthday.setCellValueFactory(new PropertyValueFactory<>("vcBirthday"));
        gender.setCellValueFactory(new PropertyValueFactory<>("vcGender"));
        leadingHand.setCellValueFactory(new PropertyValueFactory<>("vcLeadingHand"));
        leadingLeg.setCellValueFactory(new PropertyValueFactory<>("vcLeadingLeg"));
        leadingEye.setCellValueFactory(new PropertyValueFactory<>("vcLeadingEye"));
        subjectComments.setCellValueFactory(new PropertyValueFactory<>("vcComments"));
        actionUpdate.setCellValueFactory(new PropertyValueFactory<>("DUMMY"));
        actionDelete.setCellValueFactory(new PropertyValueFactory<>("DUMMY"));




        if (tableView.getColumns().isEmpty()) {
            tableView.getColumns().addAll(subjectFirstName, subjectSecondName,
                    subjectLastName, gender, leadingHand, leadingEye, leadingLeg, subjectComments, actionUpdate, actionDelete);
        }

        Pane root = (Pane) this.scene.getRoot();
        root.setPadding(new Insets(10));

        this.scene.setRoot(root);
        return this.scene;
    }

    private ObservableList<TbExperimentSubject> getList() {
        List<TbExperimentSubject> subjects = experimentSubjectService.getAllUSubject();
        ObservableList<TbExperimentSubject> list = FXCollections.observableArrayList(subjects);
        return list;
    }


    @HandleEvent(nodeName = "createSubject")
    public EventPair createEquipment(){
        EventPair pair = new EventPair();
        EventHandler eventHandler = (x) -> {
            Stage stage = stageHolder.getStage();

            stage.setScene(createExperimentSubjectComponent.getScene());
            stage.show();
        };

        pair.setEventHandler(eventHandler);
        pair.setEventType(MouseEvent.MOUSE_CLICKED);

        return pair;
    }


//    navigation

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
            if(securityContext.getUser().getVcRole() == Role.ADMIN){
                stage.setScene(listExperimentComponent.getScene());
                stage.show();
            } else {
                stage.setScene(listMyExperimentComponent.getScene());
                stage.show();
            }
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
