package org.gtugs.codelab.appengine.blog.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Widget;

public class UiBinderSample extends Composite {

	private static UiBinderSampleUiBinder uiBinder = GWT
			.create(UiBinderSampleUiBinder.class);

	interface UiBinderSampleUiBinder extends UiBinder<Widget, UiBinderSample> {
	}

	@UiField
	Button button;

	@UiField
	FlexTable flexTable = new FlexTable();

	public UiBinderSample(String firstName) {
		initWidget(uiBinder.createAndBindUi(this));
		button.setText(firstName);

		flexTable.setText(0, 0, "1");
		flexTable.setText(0, 1, "2");
		flexTable.setText(0, 2, "3");
	}

	@UiHandler("button")
	void onClick(ClickEvent e) {
		Window.alert("Hello!");
	}

}
