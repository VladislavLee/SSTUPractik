package com.sstu.practic.spring.components.experiment;

import com.sstu.practic.spring.annotations.JavaFxComponent;
import com.sstu.practic.spring.components.FxComponent;
import com.sstu.practic.spring.components.MainComponent;
import com.sstu.practic.spring.data.model.TbExperiment;
import com.sstu.practic.spring.data.model.TbFavoriteExperiment;
import com.sstu.practic.spring.services.EquipmentService;
import com.sstu.practic.spring.services.FavoriteExperimentService;
import com.sstu.practic.spring.utils.StageHolder;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@JavaFxComponent(path = "/view/listFavoriteExperiment.fxml")
public class ListFavoriteExperimentComponent extends FxComponent{
    @Autowired
    private StageHolder stageHolder;
    @Autowired
    private FavoriteExperimentService favoriteExperimentService;
    @Autowired
    private MainComponent mainComponent;


    private ObservableList<TbFavoriteExperiment> getList() {
        List<TbFavoriteExperiment> favoriteExperiments = favoriteExperimentService.getFavoriteExperiment();
        ObservableList<TbFavoriteExperiment> list = FXCollections.observableArrayList(favoriteExperiments);
        return list;
    }
}
