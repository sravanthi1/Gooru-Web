/*******************************************************************************
 * Copyright 2013 Ednovo d/b/a Gooru. All rights reserved.
 *
 *  http://www.goorulearning.org/
 *
 *  Permission is hereby granted, free of charge, to any person obtaining
 *  a copy of this software and associated documentation files (the
 *  "Software"), to deal in the Software without restriction, including
 *  without limitation the rights to use, copy, modify, merge, publish,
 *  distribute, sublicense, and/or sell copies of the Software, and to
 *  permit persons to whom the Software is furnished to do so, subject to
 *  the following conditions:
 *
 *  The above copyright notice and this permission notice shall be
 *  included in all copies or substantial portions of the Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 *  EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 *  MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 *  NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 *  LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 *  OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
 *  WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 ******************************************************************************/
package org.ednovo.gooru.client.mvp.classpage.teach.edit.content.widget;

import org.ednovo.gooru.client.uc.H4Panel;
import org.ednovo.gooru.client.uc.PPanel;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Widget;


/**
 * @fileName : EditClassLessonView.java
 *
 * @description : 
 *
 *
 * @version : 1.0
 *
 * @date: 03-Jul-2015
 *
 * @Author tumbalam
 *
 * @Reviewer: 
 */
public class EditClassLessonView extends Composite {
	
	@UiField H4Panel lessonCountPanel;
	
	@UiField PPanel lessonPanel;
	
	@UiField HTMLPanel rowContainer;
	
	EditClassCollectionWidget editClassCollectionWidget;

	private static EditClassLessonViewUiBinder uiBinder = GWT.create(EditClassLessonViewUiBinder.class);

	interface EditClassLessonViewUiBinder extends	UiBinder<Widget, EditClassLessonView> {
	}

	public EditClassLessonView() {
		initWidget(uiBinder.createAndBindUi(this));
		lessonCountPanel.setText("Lesson 1");
		lessonPanel.setText("Intro to Fractions");
		for(int i=0;i<5;i++){
			editClassCollectionWidget=new EditClassCollectionWidget();
			rowContainer.add(editClassCollectionWidget);
		}
	}
}