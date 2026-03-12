import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class CalculatorController {

    @FXML private TextField number1Field;
    @FXML private TextField number2Field;
    @FXML private Label resultLabel;

    @FXML
    private void onCalculateClick() {
        try {
            double num1 = Double.parseDouble(number1Field.getText());
            double num2 = Double.parseDouble(number2Field.getText());

            double sum        = num1 + num2;
            double product    = num1 * num2;
            double difference = num1 - num2;
            Double division   = (num2 != 0) ? num1 / num2 : null;

            String divText = (division != null) ? String.valueOf(division) : "undefined (div by 0)";

            resultLabel.setText(
                "Sum: "      + sum        + "\n" +
                "Product: "  + product    + "\n" +
                "Subtract: " + difference + "\n" +
                "Division: " + divText
            );

            ResultService.saveResult(num1, num2, sum, product, difference, division);

        } catch (NumberFormatException e) {
            resultLabel.setText("Please enter valid numbers!");
        }
    }
}