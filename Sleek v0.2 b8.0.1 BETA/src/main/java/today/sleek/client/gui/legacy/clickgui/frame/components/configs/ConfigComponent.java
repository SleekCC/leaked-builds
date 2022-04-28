package today.sleek.client.gui.legacy.clickgui.frame.components.configs;

public abstract class ConfigComponent {
    private final FrameConfig owner;
    protected int x;
    protected int y;
    private boolean hidden;

    public ConfigComponent(int x, int y, FrameConfig owner) {
        this.owner = owner;
        this.x = x;
        this.y = y;
    }

    public abstract void initGui();

    public abstract void drawScreen(int mouseX, int mouseY);

    public abstract boolean mouseClicked(int mouseX, int mouseY, int mouseButton); // Return a boolean to know if we cancel the drag

    public abstract void onGuiClosed(int mouseX, int mouseY, int mouseButton);

    public abstract void keyTyped(char typedChar, int keyCode);

    public abstract int getOffset(); // Return offset

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    @SuppressWarnings("all")
    public boolean isHidden() {
        return this.hidden;
    }

    @SuppressWarnings("all")
    public void setHidden(final boolean hidden) {
        this.hidden = hidden;
    }
}
