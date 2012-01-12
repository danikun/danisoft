package org.danisoft.ui.base;

import javafx.scene.Node;

public interface Page {
	Node load();
	String getName();
}
