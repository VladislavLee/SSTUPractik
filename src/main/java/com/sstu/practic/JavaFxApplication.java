package com.sstu.practic;

import com.sstu.practic.spring.PracticApplication;
import com.sstu.practic.spring.components.FxComponent;
import com.sstu.practic.spring.utils.StageHolder;
import javafx.application.Application;
import javafx.stage.Stage;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

public class JavaFxApplication extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        ConfigurableApplicationContext context = SpringApplication.run(PracticApplication.class, new String[0]);

        FxComponent mainComponent = (FxComponent) context.getBean("createUserComponent");
        ((StageHolder)context.getBean("stageHolder")).setStage(primaryStage);

        primaryStage.setTitle("FXML Welcome");
        primaryStage.setScene(mainComponent.getScene());
        primaryStage.show();
    }
}
