package today.sleek.base.modules;

public enum ModuleCategory {

    COMBAT("Combat"),
    EXPLOIT("Exploit"),
    MOVEMENT("Movement"),
    VISUALS("Visuals"),
    PLAYER("Player"),
    WORLD("World"),
    SETTINGS("Settings"),
    SCRIPT("Script"),
    HIDDEN("Hidden");

    public String name;

    ModuleCategory(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
