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
 * This class stores a permission group.
 */
@Data
@AllArgsConstructor
public class PermissionGroup implements PixliesPermissible {

    private static final PixliesPerms instance = PixliesPerms.getInstance();

    private String name;
    private Map<String, Boolean> permissions;
    private List<String> members;

    // Save into Internal Database
    public void save() {
        instance.getInternalDatabase().getGroups().put(name, this);
    }

    // Save into MongoDB
    public void backup() {
        Document group = new Document("name", this.name);
        Document find = instance.getGroupCollection().find(group).first();
        if (find != null)
            instance.getGroupCollection().deleteOne(find);
        group.append("permissions", permissions);
        group.append("members", members);
        instance.getProfileCollection().insertOne(group);
    }

    @Override
    public void addPermission(String node, boolean value) {
        this.permissions.put(node, value);
        save();
    }

}
