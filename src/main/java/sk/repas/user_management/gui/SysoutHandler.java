package sk.repas.user_management.gui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class SysoutHandler implements EventHandler<ActionEvent>{

	@Override
	public void handle(ActionEvent event) {
		System.out.println("Klik!!");
	}
}
