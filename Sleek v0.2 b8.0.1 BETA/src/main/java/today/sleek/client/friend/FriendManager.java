package today.sleek.client.friend;

import java.util.concurrent.CopyOnWriteArrayList;

public class FriendManager {
    private final CopyOnWriteArrayList<Friend> friends;

    public FriendManager() {
        this.friends = new CopyOnWriteArrayList<>();
    }

    public void clearFriends() {
        this.friends.clear();
    }

    public void addFriend(Friend friend) {
        this.friends.add(friend);
    }

    public void removeFriend(String friend) {
        this.friends.removeIf(friend1 -> friend1.getName().equalsIgnoreCase(friend));
    }

    public boolean isFriend(String ign) {
        for (Friend friend : friends) {
            if (friend.getName().equals(ign)) {
                return true;
            }
        }
        return false;
    }

    public Friend getFriend(String name) {
        for (Friend friend : friends) {
            if (friend.getName().equals(name)) {
                return friend;
            }
        }
        return null;
    }

    @SuppressWarnings("all")
    public CopyOnWriteArrayList<Friend> getFriends() {
        return this.friends;
    }
}
