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
package org.ednovo.gooru.client.mvp.home;


import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ednovo.gooru.client.PlaceTokens;
import org.ednovo.gooru.client.SimpleAsyncCallback;
import org.ednovo.gooru.client.gin.AppClientFactory;
import org.ednovo.gooru.client.mvp.classpages.event.OpenClasspageListEvent;
import org.ednovo.gooru.client.mvp.classpages.event.OpenJoinClassPopupEvent;
import org.ednovo.gooru.client.mvp.classpages.studentView.StudentAssignmentView;
import org.ednovo.gooru.client.mvp.home.event.HeaderTabType;
import org.ednovo.gooru.client.mvp.home.event.HomeEvent;
import org.ednovo.gooru.client.mvp.home.event.SetUserDetailsInCollectionPlayEvent;
import org.ednovo.gooru.client.mvp.home.event.SetUserDetailsInPlayEvent;
import org.ednovo.gooru.client.mvp.home.library.events.SetLoginStatusEvent;
import org.ednovo.gooru.client.mvp.home.library.events.StandardPreferenceSettingEvent;
import org.ednovo.gooru.client.mvp.play.collection.event.SetPlayerLoginStatusEvent;
import org.ednovo.gooru.client.mvp.play.collection.event.ShowCollectionTabWidgetEvent;
import org.ednovo.gooru.client.mvp.play.collection.event.ShowPreviewTabWidgetEvent;
import org.ednovo.gooru.client.mvp.play.collection.event.ShowResourceTabWidgetEvent;
import org.ednovo.gooru.client.mvp.play.collection.preview.metadata.comment.events.SetCommentsOptionsEvent;
import org.ednovo.gooru.client.mvp.search.event.SetButtonEvent;
import org.ednovo.gooru.client.mvp.search.event.SetHeaderEvent;
import org.ednovo.gooru.client.mvp.search.event.SetHeaderZIndexEvent;
import org.ednovo.gooru.client.uc.AlertContentUc;
import org.ednovo.gooru.client.uc.AlertMessageUc;
import org.ednovo.gooru.client.uc.TextBoxWithPlaceholder;
import org.ednovo.gooru.client.ui.HTMLEventPanel;
import org.ednovo.gooru.client.util.MixpanelUtil;
import org.ednovo.gooru.shared.model.content.ClasspageListDo;
import org.ednovo.gooru.shared.model.content.CollectionDo;
import org.ednovo.gooru.shared.model.user.UserDo;
import org.ednovo.gooru.shared.util.DataLogEvents;
import org.ednovo.gooru.shared.util.GwtUUIDGenerator;
import org.ednovo.gooru.shared.util.MessageProperties;
import org.ednovo.gooru.shared.util.StringUtil;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.Widget;
import com.gwtplatform.mvp.client.proxy.PlaceRequest;


/**
 * @author BLR Team
 * 
 */
public class LoginPopupUc extends PopupPanel implements MessageProperties {
 
	@UiField
	TextBoxWithPlaceholder loginTxtBox;

	@UiField
	TextBoxWithPlaceholder passwordTxtBox;

	@UiField Anchor forgotPwd,ancRegisterHere;
	
	@UiField Label lblLoginHeading,lblDoYouHaveAccount,lblOr, lblPleaseWait, lblWelcomeBack,lblLoginWithGooru;

//	@UiField CheckBox lblKeepMeLogedIn;
	
	@UiField HTMLEventPanel cancelButton;
	
	@UiField Button  loginButton,gmailButton;

	
	@UiField(provided = true)
	LoginPopUpCBundle res;

	private HeaderUc headerUc;

	protected UserDo userDo;

	private SimpleAsyncCallback<UserDo> signedInDataAsyncCallback;

	private static final String LOGIN_ERROR = GL0347;

	private static final String LOGIN_COOKIE_DISABLE_MESSAGE = GL0348;

	private static final String OOPS = GL0061;
	
	private String nameToken = "";
	
	public String eventType = "";
	
	private String emailId = null;

	int age = 0;

	
	private String widgetMode=null;
	

	private static final String LOGINEVENT = "loginEvent";
	@UiTemplate("LoginPopupUc.ui.xml")
	interface Binder extends UiBinder<Widget, LoginPopupUc> {

	}

	private static final Binder binder = GWT.create(Binder.class);
	
	/**
	 * Class constructor , to create Login Popup. 
	 */
	
	public LoginPopupUc(){
		super(false);
        this.res = LoginPopUpCBundle.INSTANCE;
        res.css().ensureInjected();
        this.setGlassStyleName(LoginPopUpCBundle.INSTANCE.css().loginPopupGlassStyle());
        this.setGlassEnabled(true);
       	this.getElement().getStyle().setZIndex(99999);
       	
        add(binder.createAndBindUi(this));
        setTextAndIds();
        
		lblPleaseWait.setVisible(false);
		setHandlers();
        
		this.center();
	}
	
	public LoginPopupUc(String emailId){
		this();
		this.emailId = emailId;
	}

	/**
	 * Class constructor , assign {@link HeaderUc} instance
	 * 
	 * @param headerUc
	 *            instance of {@link HeaderUc}
	 */
	public LoginPopupUc(HeaderUc headerUc) {
		super(false);
		this.res = LoginPopUpCBundle.INSTANCE;
		res.css().ensureInjected();
		this.headerUc = headerUc;
		add(binder.createAndBindUi(this));
		this.setGlassEnabled(true);
		this.setGlassStyleName(LoginPopUpCBundle.INSTANCE.css().loginPopupGlassStyle());
		this.getElement().getStyle().setZIndex(99999);
		Window.enableScrolling(false);
        AppClientFactory.fireEvent(new SetHeaderZIndexEvent(99, false));
//        lblKeepMeLogedIn.getElement().setId("chkLogin");
		setTextAndIds();
		lblPleaseWait.setVisible(false);
		setHandlers();
		
		this.center();		
	}
	/**
	 * 
	 * @function setHandlers 
	 * 
	 * @created_date : 15-09-2013
	 * 
	 * @description
	 * 
	 * 
	 * @parm(s) : 
	 * 
	 * @return : void
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 * 
	 *
	 *
	 */
	private void setHandlers(){

		this.setSize("515px", "547px");
		
		loginTxtBox.addKeyUpHandler(new LoginKeyupHandler());
		passwordTxtBox.addKeyUpHandler(new LoginKeyupHandler());
	}
	
	
	/**
	 * 
	 * @function setText 
	 * 
	 * @created_date : Aug 25, 2013
	 * 
	 * @description
	 * 	To the labels for each controls.
	 * 
	 * @parm(s) : 
	 * 
	 * @return : void
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 * 
	 *
	 *
	 */
	public void setTextAndIds(){
		lblLoginHeading.setText(GL0187);
		gmailButton.setText(GL0203);
		gmailButton.getElement().setId("btnGmail");
		forgotPwd.getElement().setId("lnkForgotPwd");
        loginTxtBox.setPlaceholder(GL0202);
        loginTxtBox.getElement().setAttribute("placeholder", GL0202);
		loginTxtBox.setFocus(true);
        passwordTxtBox.setPlaceholder(GL0204);
        forgotPwd.setText(GL0205);
//        lblKeepMeLogedIn.setText(GL0206);
        loginButton.setText(GL0187);
        ancRegisterHere.setText(GL0207+GL_SPL_EXCLAMATION);
        ancRegisterHere.getElement().setId("lnkRegisterHere");
        lblDoYouHaveAccount.setText(GL0208);
        lblOr.setText(GL0209);
        lblPleaseWait.setText(GL0242);
        
        loginTxtBox.getElement().setId("tbLoginUsername");
        passwordTxtBox.getElement().setId("tbLoginPassword");
        loginButton.getElement().setId("btnLogin");
        
        lblPleaseWait.setVisible(false);
        
        lblWelcomeBack.setText(GL0345);
        lblLoginWithGooru.setText(GL0346);;
	}

	

	/**
	 * User login popup allows to sign in if correct credentials and set user
	 * info on header view
	 * 
	 * @param clickEvent
	 *            instance of {@link ClickEvent}
	 */
	@UiHandler("gmailButton")
	public void onGmailButtonClicked(ClickEvent clickEvent){
		DataLogEvents.signIn(GwtUUIDGenerator.uuid(),"login",System.currentTimeMillis(),System.currentTimeMillis(), "", AppClientFactory.getLoggedInUser().getToken());
		String callBack = Window.Location.getHref();
	
		AppClientFactory.getInjector().getSearchService().getGoogleSignin(callBack, new SimpleAsyncCallback<String>() {
		
			@Override
			public void onSuccess(String result) {
				MixpanelUtil.Click_Gmail_SignIn("LoginPopup");
				Window.Location.replace(result);
			
			}
		});
	}
	
	/**
	 * Added click handler to perform Login operation by taking entered user name and password from the user.
	 * @param clickEvent instance of {@link ClickEvent}
	 */
	
	@UiHandler("loginButton")
	public void onLoginClicked(ClickEvent clickEvent) {
	
		if (isCookieEnabled()) {
			
			final String username = loginTxtBox.getText().trim();
			String password = passwordTxtBox.getText().trim();
			
			JSONObject login = new JSONObject();
			login.put("username", new JSONString(username));
			login.put("password", new JSONString(password));
			
			if (username.length() > 1 && password.length() > 1) {
				
				loginButton.setVisible(false);
				lblPleaseWait.setVisible(true);
//				AppClientFactory.getInjector().getAppService().signin(username, password, new AsyncCallback<UserDo>() {
				AppClientFactory.getInjector().getAppService().v2Signin(login.toString(), new SimpleAsyncCallback<UserDo>() {
					@Override
					public void onSuccess(UserDo result) {
						if(result.getActive()==1){

							MixpanelUtil.Regular_User_Logged_In();
							if(result.getDateOfBirth()!=null && result.getAccountTypeId()==2){
							MixpanelUtil.Registration_turns13(); 
							com.google.gwt.i18n.client.DateTimeFormat dateFormat = com.google.gwt.i18n.client.DateTimeFormat
										.getFormat("yyyy-MM-dd hh:mm:ss.S");
							Date convertedCurrentDate = null;
							convertedCurrentDate = dateFormat.parse(result.getDateOfBirth());
							age = getAge(convertedCurrentDate);
							 if(age>=13){
								 Map<String, String> map = StringUtil.splitQuery(Window.Location
											.getHref());
									map.put("callback", "turn13");
									AppClientFactory.getPlaceManager().revealPlace(
											AppClientFactory.getCurrentPlaceToken(), map);
								  }
							}
							AppClientFactory.setLoggedInUser(result);
							AppClientFactory.fireEvent(new SetUserDetailsInPlayEvent(result.getToken()));
							AppClientFactory.fireEvent(new SetUserDetailsInCollectionPlayEvent(result.getToken(),result.getGooruUId()));
							//to Set the Options butts visibility in Player for comments.
							if (AppClientFactory.getPlaceManager().getCurrentPlaceRequest().getNameToken().equalsIgnoreCase(PlaceTokens.PREVIEW_PLAY)){
								AppClientFactory.fireEvent(new SetCommentsOptionsEvent());
							}
							if(getWidgetMode()!=null){
								//AppClientFactory.fireEvent(new ShowResourceTabWidgetEvent(getWidgetMode(), false));
							}
							hide();
							//headerUc.setLoggedInUser(result);
						    AppClientFactory.fireEvent(new SetHeaderEvent(result));
							//AppClientFactory.resetPlace();
						    
	/*					    if(result.getUsername().equalsIgnoreCase("TexasTeacher")) {
								AppClientFactory.fireEvent(new SetTexasAccountEvent("failure"));
								AppClientFactory.fireEvent(new SetTexasPlaceHolderEvent(true));
							}else{
								AppClientFactory.fireEvent(new SetTexasAccountEvent("success"));
								AppClientFactory.fireEvent(new SetTexasPlaceHolderEvent(false));
							}
	*/					    
						    AppClientFactory.setUserflag(true);
							AppClientFactory.fireEvent(new StandardPreferenceSettingEvent(result.getMeta().getTaxonomyPreference().getCode()));
						    if (AppClientFactory.getPlaceManager().getCurrentPlaceRequest().getNameToken().equals(PlaceTokens.COLLECTION_PLAY)){
						    	AppClientFactory.fireEvent(new ShowCollectionTabWidgetEvent(getWidgetMode(), false));
						    }
						    if (AppClientFactory.getPlaceManager().getCurrentPlaceRequest().getNameToken().equals(PlaceTokens.PREVIEW_PLAY)){
						    	AppClientFactory.fireEvent(new ShowPreviewTabWidgetEvent(getWidgetMode(), false));
						    }
						    
						    if (AppClientFactory.getPlaceManager().getCurrentPlaceRequest().getNameToken().equals(PlaceTokens.COLLECTION_PLAY) || AppClientFactory.getPlaceManager().getCurrentPlaceRequest().getNameToken().equals(PlaceTokens.PREVIEW_PLAY)){
						    	AppClientFactory.fireEvent(new SetPlayerLoginStatusEvent(true));
						    } 
						    else if(AppClientFactory.getPlaceManager().getCurrentPlaceRequest().getNameToken().equals(PlaceTokens.RESOURCE_PLAY)){
						    	AppClientFactory.fireEvent(new ShowResourceTabWidgetEvent(getWidgetMode(), false));
							}else if(AppClientFactory.getPlaceManager().getCurrentPlaceRequest().getNameToken().equals(PlaceTokens.SHELF)){
								AppClientFactory.resetPlace();
								Window.enableScrolling(true);
								AppClientFactory.fireEvent(new SetHeaderZIndexEvent(0, true));
								AppClientFactory.fireEvent(new HomeEvent(HeaderTabType.ORGANIZE));
								String id = AppClientFactory.getPlaceManager().getRequestParameter("id") !=null && !AppClientFactory.getPlaceManager().getRequestParameter("id").equalsIgnoreCase("") ? AppClientFactory.getPlaceManager().getRequestParameter("id") : null;
								if (id != null) {
									AppClientFactory.getPlaceManager().revealPlace(PlaceTokens.SHELF, new String[] { "id", id });
								}else{
									AppClientFactory.getPlaceManager().revealPlace(PlaceTokens.SHELF);
								}
								AppClientFactory.fireEvent(new SetButtonEvent());
								//Call shelf api to load the first collection.
							}else if(AppClientFactory.getPlaceManager().getCurrentPlaceRequest().getNameToken().equals(PlaceTokens.STUDY)){
								Window.enableScrolling(true);
								AppClientFactory.fireEvent(new SetHeaderZIndexEvent(0, true));
								AppClientFactory.fireEvent(new SetButtonEvent());
								openClasspage();
							}else{
								AppClientFactory.resetPlace();
								Window.enableScrolling(true);
								AppClientFactory.fireEvent(new SetHeaderZIndexEvent(0, true));
							}
						   
						    showNewGooruTryOut(result);
						    if(AppClientFactory.getPlaceManager().getCurrentPlaceRequest().getNameToken().equalsIgnoreCase(PlaceTokens.HOME)) {
						    	AppClientFactory.fireEvent(new SetLoginStatusEvent(true));
						    }
						    
						    if(nameToken.equals(PlaceTokens.TEACH)) {
//						    	AppClientFactory.fireEvent(new OpenClasspageListEvent());
						    }  else if(nameToken.equals(PlaceTokens.SHELF)){
								getCollectionFirstItem();
						    }
						    if(AppClientFactory.getCurrentPlaceToken().equals(PlaceTokens.STUDENT)){
						    	AppClientFactory.fireEvent(new OpenJoinClassPopupEvent());
						    }
						    if(result.getOrganizationName()!=null) {
							    if (result.getOrganizationName().contains("rusd")&&(AppClientFactory.getPlaceManager().getCurrentPlaceRequest().getNameToken().equals(PlaceTokens.HOME) || AppClientFactory.getPlaceManager().getCurrentPlaceRequest().getNameToken().equals(PlaceTokens.RUSD_LIBRARY))){
							    	AppClientFactory.getPlaceManager().revealPlace(PlaceTokens.RUSD_LIBRARY);
							    }
						    }
						    if(result.getOrganizationName()!=null) {
							    if (result.getOrganizationName().contains("sausd")&&(AppClientFactory.getPlaceManager().getCurrentPlaceRequest().getNameToken().equals(PlaceTokens.HOME) || AppClientFactory.getPlaceManager().getCurrentPlaceRequest().getNameToken().equals(PlaceTokens.SAUSD_LIBRARY))){
							    	AppClientFactory.getPlaceManager().revealPlace(PlaceTokens.SAUSD_LIBRARY);
							    }
						    }
						    
						    /*if(AppClientFactory.getCurrentPlaceToken().equals(PlaceTokens.SETTINGS)){
						    	String newMailId = AppClientFactory.getPlaceManager()
										.getRequestParameter("newMailId");
								String userId = AppClientFactory.getPlaceManager().getRequestParameter(
										"userId");
								String confirmStatus = AppClientFactory.getPlaceManager()
										.getRequestParameter("confirmStatus");
								if(newMailId!=null && userId!=null && confirmStatus!=null){
									Map<String, String> params = new HashMap<String, String>();
									params.put("confirmStatus", confirmStatus);
									params.put("newMailId", newMailId);
									params.put("userId", userId);
									PlaceRequest placeRequest=AppClientFactory.getPlaceManager().preparePlaceRequest(PlaceTokens.SETTINGS, params);
									AppClientFactory.getPlaceManager().revealPlace(true, placeRequest, false);
								}
						    }*/
						}else if(result.getConfirmStatus()==0){
							loginButton.setVisible(true);
							lblPleaseWait.setVisible(false);
							new AlertContentUc(GL1966, GL1938);
						}
					}

					

					@Override
					public void onFailure(Throwable caught) {
						loginButton.setVisible(true);
						lblPleaseWait.setVisible(false);
						new AlertContentUc(OOPS, LOGIN_ERROR);
					}
				});
			} else {
				loginButton.setVisible(true);
				lblPleaseWait.setVisible(false);
				new AlertContentUc(OOPS, LOGIN_ERROR);
			}
		} else  { 
			loginButton.setVisible(true);
			lblPleaseWait.setVisible(false);
			new AlertMessageUc(GL0738, new HTML(LOGIN_COOKIE_DISABLE_MESSAGE));
		}
	}

	private void getCollectionFirstItem() {
		AppClientFactory.getInjector().getResourceService().getUserCollection(new SimpleAsyncCallback<List<CollectionDo>>() {
            public void onSuccess(List<CollectionDo> result) {				                	
            	for (CollectionDo collection : result) {
        			if (!collection.getCollectionType().toString().trim().equalsIgnoreCase("folder")){
        				AppClientFactory.getPlaceManager().revealPlace(PlaceTokens.SHELF, new String[] { "id", collection.getGooruOid() });
        				break;
        			}
        		}
            }
        });
	}
	/**
	 * Added click handler to hide the login popup.
	 * 
	 * @param clickEvent
	 *            instance of {@link ClickEvent}
	 */
	@UiHandler("cancelButton")
	public void onCancelClicked(ClickEvent clickEvent) {
		
		StudentAssignmentView.islogin = false;
		
		/* * Checks for parameter value if it is true then it will remove the parameter, so that it will avoid 
		 * invoking login popup multiple times on refresh.*/
		 
		if(AppClientFactory.getPlaceManager().getRequestParameter(LOGINEVENT) != null && AppClientFactory.getPlaceManager().getRequestParameter(LOGINEVENT).equalsIgnoreCase("true")){
			hide();
			AppClientFactory.getPlaceManager().revealPlace(PlaceTokens.HOME);
		}
		if( AppClientFactory.getPlaceManager().getCurrentPlaceRequest().getNameToken().equals(PlaceTokens.PREVIEW_PLAY)){
			AppClientFactory.fireEvent(new ShowPreviewTabWidgetEvent(getWidgetMode(), true));
		}
		if (AppClientFactory.getPlaceManager().getCurrentPlaceRequest().getNameToken().equals(PlaceTokens.COLLECTION_PLAY)){
	    	AppClientFactory.fireEvent(new ShowCollectionTabWidgetEvent(getWidgetMode(), true));
	    } 
	    else if(AppClientFactory.getPlaceManager().getCurrentPlaceRequest().getNameToken().equals(PlaceTokens.RESOURCE_PLAY)){
	    	AppClientFactory.fireEvent(new ShowResourceTabWidgetEvent(getWidgetMode(), true));
		}
	    else if(AppClientFactory.getPlaceManager().getCurrentPlaceRequest().getNameToken().equals(PlaceTokens.STUDENT))
	    {
	    	if(!StudentAssignmentView.getMainContainerStatus())
	    	{
	    		AppClientFactory.getPlaceManager().revealPlace(PlaceTokens.HOME);
	    	}
	    }/*else if(AppClientFactory.getPlaceManager().getCurrentPlaceRequest().getNameToken().equals(PlaceTokens.SETTINGS)){
	    	if(AppClientFactory.getPlaceManager().getRequestParameter("newMailId")!=null){
	    		AppClientFactory.getPlaceManager().revealPlace(PlaceTokens.HOME);
	    	}
	    }*/
	    else{
			Window.enableScrolling(true);
			AppClientFactory.fireEvent(new SetHeaderZIndexEvent(0, true));
			if (AppClientFactory.getPlaceManager().getCurrentPlaceRequest().getNameToken().equalsIgnoreCase(PlaceTokens.SHELF) && AppClientFactory.getPlaceManager().getRequestParameter("id") != null && !AppClientFactory.getPlaceManager().getRequestParameter("id").equalsIgnoreCase("")){
				hide();
				AppClientFactory.getPlaceManager().revealPlace(PlaceTokens.HOME);
			}
		}
        hide();
	}

	/**
	 * Get register popup and set popup location
	 * 
	 * @param clickEvent
	 *            instance of {@link ClickEvent}
	 */
	@UiHandler("ancRegisterHere")
	public void onRegisterClicked(ClickEvent clickEvent) {
		MixpanelUtil.Arrive_Register_popup();
		DataLogEvents.signUp(GwtUUIDGenerator.uuid(),"login",System.currentTimeMillis(),System.currentTimeMillis(), "");
		
//		Map<String, String> params = new HashMap<String, String>();
//		params.put("callback", "signup");
//		params.put("type", "1");
//		AppClientFactory.getPlaceManager().revealPlace(PlaceTokens.HOME, params );
		 PlaceRequest checkTabvalue = AppClientFactory.getPlaceManager().getCurrentPlaceRequest();
         String tabValue = checkTabvalue.getParameter("tab", null);
		Map<String, String> params = StringUtil.splitQuery(Window.Location.getHref());
		if(params.containsKey("query"))
		{
			String queryVal = AppClientFactory.getPlaceManager().getRequestParameter("query");
			params.put("query", queryVal);
		}
		if(params.containsKey("flt.subjectName"))
		{
			String subjectNameVal = AppClientFactory.getPlaceManager().getRequestParameter("flt.subjectName");
			params.put("flt.subjectName", subjectNameVal);
		}
		params.put("callback", "signup");
		params.put("type", "1");
		if(tabValue!=null){
		params.remove("tab");
		}
		this.hide();
		
		AppClientFactory.getPlaceManager().revealPlace(AppClientFactory.getCurrentPlaceToken(), params );
		
		/*RegisterVc registerVc = new RegisterVc();
		registerVc.center();
		registerVc.show();*/
		
	}

	/**
	 * Get forgot password popup while clicking on forgot password link on login
	 * popup
	 * 
	 * @param clickEvent
	 *            instance of {@link ClickEvent}
	 */
	@UiHandler("forgotPwd")
	public void onForgotPwdClicked(ClickEvent clickEvent) {
		ForgotPasswordVc forgotPasswordVc=new ForgotPasswordVc();
		forgotPasswordVc.setGlassEnabled(true);
		forgotPasswordVc.show();
		forgotPasswordVc.center();
		this.hide();
				
	}

	/**
	 * creating inner class implementing KeyUpHandler interface.
	 * 
	 */
	public class LoginKeyupHandler implements KeyUpHandler {

		@Override
		public void onKeyUp(KeyUpEvent event) {
			if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
				onLoginClicked(null);
			}
		}

	}

	/**
	 * Update user view flag
	 * 
	 * @param user
	 *            instance of {@link UserDo}
	 */
	private void showNewGooruTryOut(UserDo user) { 
		if (user != null) {
			int flag = user.getViewFlag() != null ? user.getViewFlag() : 0;
			if (flag == 0 && !AppClientFactory.isAnonymous()) {
				/*ImprovedGooruPopUpView popupview=new ImprovedGooruPopUpView();
				popupview.show();
				popupview.center();*/
				
				//new TryItOutVc();
				
				AppClientFactory.getInjector().getUserService().updateUserViewFlag(user.getGooruUId(), 1, new SimpleAsyncCallback<UserDo>() {
					@Override
					public void onSuccess(UserDo newUser) {
						UserDo user = AppClientFactory.getLoggedInUser();
						user.setViewFlag(newUser.getViewFlag());
						AppClientFactory.setLoggedInUser(user);
					}
				});
			} else if((flag == 2||flag == 6||flag==1) && !AppClientFactory.isAnonymous()){
//				new ImprovedGooruPopUpView();				
			}
		}
	}
	
	private static native boolean isCookieEnabled() /*-{
		return navigator.cookieEnabled;
	 }-*/;

	/**
	 * 
	 * @return instance of UserDo.
	 */
	
	public SimpleAsyncCallback<UserDo> getSignedInDataAsyncCallback() {
		return signedInDataAsyncCallback;
	}

	/**
	 * 
	 * @param signedInDataAsyncCallback {@link SimpleAsyncCallback<UserDo>}
	 */
	public void setSignedInDataAsyncCallback(SimpleAsyncCallback<UserDo> signedInDataAsyncCallback) {
		this.signedInDataAsyncCallback = signedInDataAsyncCallback;
	}
	
	public void setNameToken(String nameToken) { 
		this.nameToken=nameToken;
	}

	public void setMixPanelEvent(String eventType) {
		this.eventType = eventType;
	}

	public String getMixPanelEvent() {
		return eventType;
		
	}
	
	public void onLoad(){
		super.onLoad();
		Scheduler.get().scheduleDeferred(new  ScheduledCommand(){
			public void execute() {
				if(emailId!=null) {
					loginTxtBox.setText(emailId);
					loginTxtBox.setReadOnly(true);
				}
			}
		});
	}

	private int getAge(Date birthDate) {
		if (birthDate != null) {
			long ageInMillis = new Date().getTime() - birthDate.getTime();
			Date age = new Date(ageInMillis);
			return age.getYear() - 70;
		} else {
			return 0;
		}
	}

	public String getWidgetMode() {
		return widgetMode;
	}

	public void setWidgetMode(String widgetMode) {
		this.widgetMode = widgetMode;

	}
	public void openClasspage() {
		AppClientFactory
				.getInjector()
				.getClasspageService()
				.v2GetAllClass("2", "0",
						new SimpleAsyncCallback<ClasspageListDo>() {
							@Override
							public void onSuccess(ClasspageListDo result) {
								if (result.getSearchResults().size() > 0){
									OpenClasspageEdit(result.getSearchResults().get(0).getGooruOid());
								}else{
									
								}
							}
						});
	}
	private void OpenClasspageEdit(String gooruOId) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("classpageid", gooruOId);
		params.put("pageNum", "0");
		params.put("pageSize", "10");
		params.put("pos", "1");
		AppClientFactory.getPlaceManager().revealPlace(
				PlaceTokens.EDIT_CLASSPAGE, params);
	}

}

