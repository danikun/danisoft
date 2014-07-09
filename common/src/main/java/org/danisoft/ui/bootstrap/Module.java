package org.danisoft.ui.bootstrap;

import javafx.scene.control.TabPane;

import org.controlsfx.control.action.ActionGroup;

public interface Module {
	ActionGroup loadModule(TabPane tabPane);
}
