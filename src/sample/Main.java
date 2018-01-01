package sample;

import com.company.Schedule;
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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
                client.sendObject(event.getRowValue());
            }
        });

        Button add = new Button("Add");

        add.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Schedule schedule = new Schedule();
                schedule.setId(list.get(list.size() - 1).getId() + 1);
                System.out.println("New element ID: " + schedule.getId());
                schedule.setName("Subject");
                schedule.setTime("00:00");

                tableView.getItems().clear();
                int size = list.size();
                client.sendObject(schedule);
                list = new ArrayList<>();
                client.receiveList(size + 1, list);
                tableView.getItems().setAll(list);
            }
        });

        Button remove = new Button("Remove");

        remove.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Schedule sc = (Schedule) tableView.getSelectionModel().getSelectedItem();
                sc.setDeleted(true);
                client.sendObject(sc);
                tableView.getItems().remove(sc);
            }
        });

        tableView.getItems().setAll(list);
        tableView.getColumns().addAll(column1, column2);

        GridPane pane = new GridPane();

        pane.add(tableView, 0, 0, 2, 4);
        pane.add(add, 2, 1);
        pane.add(remove, 2, 2);

        primaryStage.setScene(new Scene(pane, 300, 275));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
