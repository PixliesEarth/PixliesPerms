package net.pixlies.perms.profile;

import lombok.AllArgsConstructor;
import lombok.Data;
import net.pixlies.perms.PixliesPerms;
import net.pixlies.perms.interfaces.PixliesPermissible;
import org.bson.Document;

import java.util.List;
import java.util.Map;

/**
 * @author MickMMars
 * This class stores a players permission-profile.
 */
@Data
@AllArgsConstructor
public class PermissionProfile implements PixliesPermissible {

    private static final PixliesPerms instance = PixliesPerms.getInstance();

    private String UUID;
    private Map<String, Boolean> individualPermissions;
    private List<String> permissionGroups;

    // Save into Internal Database
    public void save() {
        instance.getInternalDatabase().getProfiles().put(java.util.UUID.fromString(this.getUUID()), this);
    }

    // Save into MongoDB
    public void backup() {
        Document profile = new Document("UUID", this.UUID);
        Document find = instance.getProfileCollection().find(profile).first();
        if (find != null)
            instance.getProfileCollection().deleteOne(find);
        profile.append("individualPermissions", individualPermissions);
        profile.append("permissionGroups", permissionGroups);
        instance.getProfileCollection().insertOne(profile);
    }

    @Override
    public void addPermission(String node, boolean value) {
        this.individualPermissions.put(node, value);
        save();
    }

}
