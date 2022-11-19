package bridge.domain;

import bridge.utils.enums.Moving;

public class RealTimeBridge {

    private final String BLANK = "";
    private final String SPACE = "   ";
    private final String DIVIDE = "|";

    String[][] realTimeBridge = {{"[", "", "]"}
            , {"[", "", "]"}};

    public String[][] getMap() {
        return realTimeBridge;
    }

    public void makeRealTimeBridge(String userMove, String bridgeText) {
        if (userMove.equals(Moving.UP.getValue())) {
            moveUpper(bridgeText);
            return;
        }
        moveDown(bridgeText);
    }

    private void moveDown(String bridgeText) {
        if (realTimeBridge[0][1].equals(BLANK)) {
            realTimeBridge[1][1] += bridgeText;
            realTimeBridge[0][1] += SPACE;
            return;
        }
        realTimeBridge[1][1] += DIVIDE + bridgeText;
        realTimeBridge[0][1] += DIVIDE + SPACE;
    }

    private void moveUpper(String bridgeText) {
        if (realTimeBridge[0][1].equals(BLANK)) {
            realTimeBridge[0][1] += bridgeText;
            realTimeBridge[1][1] += SPACE;
            return;
        }
        realTimeBridge[0][1] += DIVIDE + bridgeText;
        realTimeBridge[1][1] += DIVIDE + SPACE;
    }

    public void initialize() {
        realTimeBridge[0][1] = BLANK;
        realTimeBridge[1][1] = BLANK;
    }
}
