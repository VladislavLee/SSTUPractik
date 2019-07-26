package com.sstu.practic.spring.utils;

import javafx.stage.Stage;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class StageHolder {
    private Stage stage;
}
