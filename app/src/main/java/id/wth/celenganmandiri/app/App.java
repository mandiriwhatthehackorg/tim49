package id.wth.celenganmandiri.app;

import android.app.Application;
import android.content.Context;

import id.wth.celenganmandiri.utils.PreferenceManager;
import io.realm.Realm;
import io.realm.RealmConfiguration;

public class App extends Application {
    private static Context context;
    public static final String TAG = App.class
            .getSimpleName();
    private static App mInstance;
    private PreferenceManager pref;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this.getApplicationContext();
        mInstance = this;
        Realm.init(this);
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder()
                .name(Realm.DEFAULT_REALM_NAME)
                .schemaVersion(0)
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(realmConfiguration);

    }

    public static synchronized App getInstance() {
        return mInstance;
    }

    //*********** Returns Application Context ********//
    public static Context getContext() {
        return context;
    }

    public PreferenceManager getPrefManager() {
        if (pref == null) {
            pref = new PreferenceManager(this);
        }

        return pref;
    }

    public void logout() {
        pref.clear();
        /*Intent intent = new Intent(this, LoginPage.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);*/
    }
}
