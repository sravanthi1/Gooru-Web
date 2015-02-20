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

package org.ednovo.gooru.shared.util;


public interface ClientConstants {
	
	String TRUE = "true";
	
	String FALSE = "false";
	
	String GOORU_ACTIVE_USER = "gooru-active-user";
	
	String GOORU_ANONYMOUS = "ANONYMOUS";
	
	/* ****************** Base service Impl Constants Start ******************** */
	

	String REST_ENDPOINT = "rest.endpoint";

	String SEARCH_ENDPOINT = "search.endpoint";

	String REST_USERNAME = "rest.username";

	String REST_PASSWORD = "rest.password";

	String SEARCH_USERNAME = "search.username";

	String SEARCH_PASSWORD = "search.password";

	String API_KEY = "api.key";

	String HOME_ENDPOINT = "home.endpoint";
	
	String ANALYTICS_ENDPOINT = "analytics.endpoint";

	String DOMAIN_NAME = "domain.name";
	
	String DOCVIEWER_HOME = "docViewer.home";
	
	String DOCVIEWER_CACHE = "docViewer.cache";

	String GOOGLE_ANALTICS_ADDITIONAL_ACCOUNTS = "google.analtics.additional.accounts";

	String CLASSIC_ENDPOINT = "classic.endpoint";

	String GOORU_SESSION_TOKEN = "gooru-session-token";
	
	String GOORU_ACCESS_TOKEN = "google-access-token";
	
	String SIGNED_USER_UID = "signed-user-uid";

	String SIGNED_USER_EMAILID = "signed-user-emailid";

	String COOKIE_PATH = "/";

	String TOKEN = "token";

	String USER_INFO_FAILED_ON_TOKEN = "Get User info failed on token : ";

	int COOKIE_AGE = 86400;//24 hrs
	
	String PRODUCTION_SWITCH = "production.switch";
	
	String GOOGLE_SIGNIN = "google.signin";
	
	String ENABLE_CLIENT_LOGGERS = "clientside.loggers";
	
	String GOOGLE_DRIVE = "drive.api";
	
	String PROFILE_IMAGE_RESPOSITORY_URL="profile.image.url";
	
	String CDN_ENDPOINT = "cdn.endpoint";
	
	String DATA_LOG_API_KEY = "log.api.key";
	
	String WHATS_NEW_MOS_LINK = "whats.new.mos.link";
	
	String WHATS_NEW_FIB_LINK = "whats.new.fib.link";
	
	String MOS_LINK = "mos.link";

	String GOORU_RELEASE_VERSION = "gooru.release.version";
	
	String FACEBOOK_APP_ID="facebook.app_id";
	
	String FACEBOOK_FEED_URL="facebook.dialogfeedurl";
	
	String TAXONOMY_PREFERENCES = "taxonomy.preferences";
	
	String SIGNED_USER_DOB = "signed-user-dob";
	
	String SERVER_REDIRECT_URL="redirect.url";
	
	String GOOGLE_RESTENDPOINT="google.restendpoint";
	
	String STORIES_URL = "stories.url";
	
	String SHOW_STORIES = "show.stories";
	
	String COMMUNITY_LIBRARY_ID = "community.library.gooruOid";
	
	String HTTPS = "https";
	
	String HTTP = "http";
	
	/* ****************** Base service Impl Constants End ******************** */
	
	/*************** Analytics start **************************************************/
	
	String CLEARSUMMARY="ClearSummary";
	
	String CLEARPROGRESS="ClearProgress";
	
	String SUMMARY="Summary";
	
	String PROGRESS="Progress";
	
	String BELOWSCORE="BelowScore";
	
	String ABOVESCORE="AboveScore";
	
	String ONE = "1";

	String TWO = "2";
	
	String THREE = "3";
	
	String REPORTS = "reports";
	
	String GREEN = "#BCD1B9 !important";
	String RED = "#EAB4B3 !important";
	String ORANGE = "#FFE7C2 !important";
	String WHITE = "#FFF";
	String VIEWRESPONSE = "View Response";
	String QUESTION = "question";
	String RESOURCE="resource";
	String OE = "OE";
	String MA = "MA";
	String FIB = "FIB";
	String TF = "TF";
	String ANS_A = "A";
	String ANS_B = "B";
	String FIRST_SESSION = "1st Session";
	String ALL_STUDENTS ="All Students";
	String SORT_BY = "Sort BY:";
	String INCORRECT="#db0f0f";
	String CORRECT="#4d9645";
	String ONMULTIPULEATTEMPTS="#FBB03B";
	String SCORED="scoredTab";
	String OPENENDED="openendedTab";
	String BREAKDOWN="breakdownTab";
	String PRINT="print";
	String EMAIL="email";
	String WEBSITE ="website";
	String WEBPAGE = "webpage";		
	String SLIDE = "slide";
	String IMAGE ="image";
	String HANDOUT = "handout";
	String LESSON ="lesson";
	String TEXTBOOK = "textbook";
	String TEXT = "text";
	String EXAM = "exam";
	String VIDEO = "video";
	String INTERACTIVE ="interactive";
	String AUDIO = "audio";
	String CHALLENGE ="Challenge";
	String VIDEOS ="Videos";
	String OTHER ="Other";
	
	/*************** Analytics End **************************************************/
	
	/***** Resource Player Related **************/
	String SAVING = "Saving..";
	String POSTING = "Posting..";
	String SUBMIT ="Submit";
	String NOTLODING ="not-loading";
	
}