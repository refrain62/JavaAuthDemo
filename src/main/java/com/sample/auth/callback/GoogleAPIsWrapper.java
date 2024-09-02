//package com.sample.auth.callback;
//
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.io.Reader;
//import java.security.GeneralSecurityException;
//import java.util.Arrays;
//import java.util.List;
//
//import com.google.api.client.auth.oauth2.BearerToken;
//import com.google.api.client.auth.oauth2.ClientParametersAuthentication;
//import com.google.api.client.auth.oauth2.Credential;
//import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
//import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
//import com.google.api.client.googleapis.auth.oauth2.GoogleOAuthConstants;
//import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
//import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
//import com.google.api.client.http.HttpTransport;
//import com.google.api.client.json.JsonFactory;
//import com.google.api.client.json.jackson2.JacksonFactory;
//import com.google.api.services.people.v1.PeopleService;
//import com.google.api.services.people.v1.PeopleServiceScopes;
//
///**
// * Google APIsラッパークラス.
// *
// * @author H.Aoshima
// * @version 1.0
// */
//public class GoogleAPIsWrapper {
//
//    /** アプリ名. */
//    private static final String       APPLICATION_NAME      = "CotoGoto";
//
//    /** JsonFactory. */
//    private static final JsonFactory  JSON_FACTORY          = JacksonFactory.getDefaultInstance();
//
//    /**
//     * Global instance of the scopes required by this quickstart.
//     * If modifying these scopes, delete your previously saved tokens/ folder.
//     */
//    public static final List <String> NORMAL_SCOPES         = Arrays.asList(
//            PeopleServiceScopes.USERINFO_EMAIL,
//            PeopleServiceScopes.USERINFO_PROFILE);
//
//    /** credentials.json. */
//    public static final String        CREDENTIALS_FILE_PATH = "/credentials.json";
//
//    /** HttpTransport. */
//    private static HttpTransport      httpTransport;
//
//    /** リダイレクトURL. */
//    private String                    redirectUrl;
//
//    /**
//     * コンストラクタ.
//     */
//    public GoogleAPIsWrapper() {
//
//    }
//
//
//    /**
//     * コンストラクタ.
//     * @param pRedirectUrl リダイレクトURL
//     */
//    public GoogleAPIsWrapper(final String pRedirectUrl) {
//
//        this.redirectUrl = pRedirectUrl;
//    }
//
//
//    /**
//     * JSONファイル取得.
//     * @return GoogleClientSecrets
//     * @throws IOException 例外
//     */
//    private GoogleClientSecrets getClientSecrets() throws IOException {
//
//        final Reader secretFile = new InputStreamReader(
//                GoogleAPIsWrapper.class.getResourceAsStream(GoogleAPIsWrapper.CREDENTIALS_FILE_PATH));
//        final var clientSecrets = GoogleClientSecrets.load(
//                GoogleAPIsWrapper.JSON_FACTORY, secretFile);
//
//        return clientSecrets;
//    }
//
//
//    /**
//     * google flow 認証のためのオブジェクト取得.
//     * @param scopes スコープ
//     * @return GoogleAuthorizationCodeFlow
//     * @throws IOException 例外
//     * @throws GeneralSecurityException 例外
//     */
//    private GoogleAuthorizationCodeFlow getFlow(final List <String> scopes) throws IOException,
//            GeneralSecurityException {
//
//        if (GoogleAPIsWrapper.httpTransport == null) {
//            GoogleAPIsWrapper.httpTransport = GoogleNetHttpTransport.newTrustedTransport();
//        }
//
//        final var clientSecrets = this.getClientSecrets();
//
//        final var flow = new GoogleAuthorizationCodeFlow.Builder(
//                GoogleAPIsWrapper.httpTransport, GoogleAPIsWrapper.JSON_FACTORY, clientSecrets, scopes)
//                .setAccessType("offline").setApprovalPrompt("force").build();
//
//        return flow;
//    }
//
//
//    /**
//     * 認証オブジェクト取得.
//     * @param refreshToken トークン
//     * @return GoogleCredential
//     * @throws GeneralSecurityException 例外
//     * @throws IOException 例外
//     */
//    public Credential getGoogleCredential(final String refreshToken)
//            throws GeneralSecurityException, IOException {
//
//        if (GoogleAPIsWrapper.httpTransport == null) {
//            GoogleAPIsWrapper.httpTransport = GoogleNetHttpTransport.newTrustedTransport();
//        }
//
//        final var secrets = this.getClientSecrets();
//        final var credential = new Credential.Builder(BearerToken.authorizationHeaderAccessMethod())
//                // .setClientSecrets(secrets.getDetails().getClientId(),
//                // secrets.getDetails().getClientSecret())
//                .setJsonFactory(GoogleAPIsWrapper.JSON_FACTORY)
//                .setTransport(GoogleAPIsWrapper.httpTransport)
//                .setClientAuthentication(
//                        new ClientParametersAuthentication(secrets.getDetails().getClientId(), secrets.getDetails().getClientSecret()))
//                .setTokenServerEncodedUrl(GoogleOAuthConstants.TOKEN_SERVER_URL)
//                .build();
//
//        credential.setRefreshToken(refreshToken);
//        credential.refreshToken();
//
//        return credential;
//    }
//
//
//    /**
//     * 認証URL取得.
//     * @param scopes スコープ
//     * @return String
//     * @throws IOException 例外
//     * @throws GeneralSecurityException 例外
//     */
//    public String getGoogleOAuthURL(final List <String> scopes) throws IOException,
//            GeneralSecurityException {
//
//        final var flow = this.getFlow(scopes);
//        return flow.newAuthorizationUrl().setRedirectUri(this.redirectUrl).build();
//    }
//
//
//    /**
//     * コールバック後、レスポンス取得.
//     * @param code コード
//     * @param scopes スコープ
//     * @return GoogleTokenResponse
//     * @throws IOException 例外
//     * @throws GeneralSecurityException 例外
//     */
//    public GoogleTokenResponse getGoogleResponse(final String code, final List <String> scopes)
//            throws IOException, GeneralSecurityException {
//
//        final var flow = this.getFlow(scopes);
//        return flow.newTokenRequest(code).setRedirectUri(this.redirectUrl).execute();
//    }
//
//
//    /**
//     * Peopleにアクセスするためのオブジェクト取得.
//     * @param credential Credential
//     * @return Calendar
//     * @throws GeneralSecurityException 例外
//     * @throws IOException 例外
//     */
//    public PeopleService getPeopleClient(
//            final Credential credential) throws GeneralSecurityException,
//            IOException {
//
//        if (GoogleAPIsWrapper.httpTransport == null) {
//            GoogleAPIsWrapper.httpTransport = GoogleNetHttpTransport.newTrustedTransport();
//        }
//
//        return new PeopleService.Builder(GoogleAPIsWrapper.httpTransport, GoogleAPIsWrapper.JSON_FACTORY, credential)
//                .setApplicationName(GoogleAPIsWrapper.APPLICATION_NAME)
//                .build();
//    }
//
//}
//
