package cc.polyfrost.example.hud;

import cc.polyfrost.oneconfig.hud.TextHud;

public class TestHud extends TextHud {
    public TestHud(boolean enabled, int x, int y) {
        super(enabled, x, y);
    }

    @Override
    public String getText() {
        return "Hello, world!";
    }
}
