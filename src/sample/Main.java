package sample;

import com.company.Schedule;
import javafx.application.Application;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.util.ArrayList;

public class Main extends Application {
    private ArrayList<Schedule> list = new ArrayList<>();

    @Override
    public void start(Stage primaryStage) throws Exception{
        TcpClient client = new TcpClient(list);
        primaryStage.setTitle("Hello World");

        Schedule[] schedules = new Schedule[list.size()];
        list.toArray(schedules);

        TableView tableView = new TableView();
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tableView.setEditable(true);

        TableColumn<Schedule, String> column1 = new TableColumn<>("Time");
        column1.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getTime()));
        column1.setCellFactory(TextFieldTableCell.forTableColumn());

        column1.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Schedule, String>>() {
            public void handle(TableColumn.CellEditEvent<Schedule, String> event) {
                ((Schedule) event.getTableView().getItems().get(event.getTablePosition().getRow())).setTime(event.getNewValue());
                System.out.println(event.getNewValue());
                client.sendObject(event.getRowValue());
            }
        });

        TableColumn<Schedule, String> column2 = new TableColumn<>("Name");
        column2.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getName()));
        column2.setCellFactory(TextFieldTableCell.forTableColumn());

        column2.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Schedule, String>>() {
            public void handle(TableColumn.CellEditEvent<Schedule, String> event) {
                ((Schedule) event.getTableView().getItems().get(event.getTablePosition().getRow())).setTime(event.getNewValue());
                System.out.println(event.getNewValue());
            }
        });

        tableView.getItems().addAll(schedules);
        tableView.getColumns().addAll(column1, column2);

        GridPane pane = new GridPane();

        pane.add(tableView, 0, 0);

        primaryStage.setScene(new Scene(pane, 300, 275));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
