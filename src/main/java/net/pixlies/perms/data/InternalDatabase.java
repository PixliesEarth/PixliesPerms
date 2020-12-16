package net.pixlies.perms.data;

import lombok.Getter;
import net.pixlies.perms.profile.PermissionGroup;
import net.pixlies.perms.profile.PermissionProfile;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class InternalDatabase {

    private final @Getter Map<UUID, PermissionProfile> profiles = new HashMap<>();

    private final @Getter Map<String, PermissionGroup> groups = new HashMap<>();

}
