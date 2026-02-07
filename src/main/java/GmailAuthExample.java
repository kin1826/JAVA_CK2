//import com.google.api.client.auth.oauth2.Credential;
//import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
//import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
//import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
//import com.google.api.client.http.HttpTransport;
//import com.google.api.client.json.JsonGenerator;
//import com.google.api.client.json.JsonParser;
//import com.google.api.services.gmail.Gmail;
//import com.google.api.services.gmail.model.Profile;
//import com.google.api.client.json.JsonFactory;
//import com.google.api.client.json.JsonObjectParser;
//
//import java.io.*;
//import java.nio.charset.Charset;
//import java.util.Collections;
//
//public class GmailAuthExample {
//    private static final String CLIENT_SECRET_FILE = "path/to/client_secrets.json";
//    private static final JsonFactory JSON_FACTORY = new JsonFactory() {
//        @Override
//        public JsonParser createJsonParser(InputStream inputStream) throws IOException {
//            return null;
//        }
//
//        @Override
//        public JsonParser createJsonParser(InputStream inputStream, Charset charset) throws IOException {
//            return null;
//        }
//
//        @Override
//        public JsonParser createJsonParser(String s) throws IOException {
//            return null;
//        }
//
//        @Override
//        public JsonParser createJsonParser(Reader reader) throws IOException {
//            return null;
//        }
//
//        @Override
//        public JsonGenerator createJsonGenerator(OutputStream outputStream, Charset charset) throws IOException {
//            return null;
//        }
//
//        @Override
//        public JsonGenerator createJsonGenerator(Writer writer) throws IOException {
//            return null;
//        }
//    };
//    private static final String TOKENS_DIRECTORY_PATH = "tokens";
//    private static final HttpTransport HTTP_TRANSPORT = ;
//
//    public static void main(String[] args) throws Exception {
//        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(new FileInputStream(CLIENT_SECRET_FILE)));
//        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
//                HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, Collections.singleton(GmailScopes.GMAIL_READONLY))
//                .setDataStoreFactory(DATA_STORE_FACTORY)
//                .setAccessType("offline")
//                .build();
//
//        Credential credential = new AuthorizationCodeInstalledApp(flow, new LocalServerReceiver()).authorize("user");
//        Gmail gmail = new Gmail.Builder(HTTP_TRANSPORT, JSON_FACTORY, credential)
//                .setApplicationName(APPLICATION_NAME)
//                .build();
//
//        Profile profile = gmail.users().getProfile("me").execute();
//        System.out.println("Email: " + profile.getEmailAddress());
//    }
//}
