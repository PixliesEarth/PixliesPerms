package net.pixlies.perms;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import lombok.Getter;
import net.pixlies.perms.data.InternalDatabase;
import org.bson.Document;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class PixliesPerms extends JavaPlugin {

    // Instance of Main class
    private static @Getter
    PixliesPerms instance = null;

    private @Getter MongoCollection<Document> profileCollection;
    private @Getter MongoCollection<Document> groupCollection;

    private @Getter InternalDatabase internalDatabase;

    // Bukkit methods
    @Override
    public void onEnable() {
        instance = this;
        init();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public void init() {
        // Load MongoDB stuff
        getLogger().info("Loading mongo-database...");
        String uri = getConfig().getString("mongo-uri");
        if (uri == null || uri.equalsIgnoreCase("uri-here")) {
            getLogger().warning("Invalid MongoDB URI");
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }
        MongoClient client = new MongoClient(uri);
        MongoDatabase database = client.getDatabase(getConfig().getString("mongo-database"));
        profileCollection = database.getCollection(getConfig().getString("profile-collection"));
        groupCollection = database.getCollection(getConfig().getString("group-collection"));
        getLogger().fine("Mongo-database loaded!");

        // Load Internal Database
        getLogger().info("Loading internal database...");
        internalDatabase = new InternalDatabase();
        getLogger().fine("Internal database loaded!");



    }

}
