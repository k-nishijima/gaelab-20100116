package org.gtugs.codelab.appengine.blog.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;

public class EntryPointSample implements EntryPoint {

	@Override
	public void onModuleLoad() {
		RootPanel.get().add(new UiBinderSample("user name"));
	}

}
