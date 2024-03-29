package com.sstu.practic.spring.components.channel;


import com.sstu.practic.spring.annotations.HandleEvent;
import com.sstu.practic.spring.annotations.JavaFxComponent;
import com.sstu.practic.spring.components.FxComponent;
import com.sstu.practic.spring.components.MainComponent;
import com.sstu.practic.spring.components.experiment.ListMyExperimentComponent;
import com.sstu.practic.spring.components.processingMethod.CreateProcessingMethodComponent;
import com.sstu.practic.spring.components.user.CreateUserComponent;
import com.sstu.practic.spring.data.model.TbChannels;
import com.sstu.practic.spring.services.ChannelService;
import com.sstu.practic.spring.services.security.SecurityContext;
import com.sstu.practic.spring.utils.StageHolder;
import com.sstu.practic.spring.utils.entities.EventPair;
import javafx.event.EventHandler;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;

@JavaFxComponent(path = "/view/createChannel.fxml")
public class CreateChannelComponent extends FxComponent {

    @Autowired
    private CreateUserComponent createUserComponent;

    @Autowired
    private MainComponent mainComponent;
    @Autowired
    private ListChannelComponent listChannelComponent;
    @Autowired
    private CreateProcessingMethodComponent createProcessingMethodComponent;
    @Autowired
    ChannelService channelService;
    @Autowired
    private StageHolder stageHolder;
    @Autowired
    private SecurityContext securityContext;
    @Autowired
    private ListMyExperimentComponent listMyExperimentComponent;
    

    @HandleEvent(nodeName = "buttonCreateChannel")
    public EventPair eventHandler() {
        EventPair pair = new EventPair();

        EventHandler eventHandler = (x) -> {
            Stage stage = stageHolder.getStage();

            String channelName = ((TextField) scene.lookup("#channelName")).getText();
            String channelDescription = ((TextArea) scene.lookup("#channelDescription")).getText();

            channelService.addChannel(TbChannels.builder()
            .vcName(channelName)
            .vcDescription(channelDescription)
            .idUser(securityContext.getUser().getIdUser())
            .build());

            stage.setScene(listChannelComponent.getScene());
            stage.show();
        };

        pair.setEventHandler(eventHandler);
        pair.setEventType(MouseEvent.MOUSE_CLICKED);

        return pair;
    }




}
