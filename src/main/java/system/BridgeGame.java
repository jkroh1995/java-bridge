package system;

import bridge.BridgeMaker;
import bridge.BridgeNumberGenerator;
import bridge.BridgeRandomNumberGenerator;
import utils.EnumStrings;
import controller.InputController;
import view.OutputView;
import bridge.RealTimeBridge;
import java.util.List;

/**
 * 다리 건너기 게임을 관리하는 클래스
 */
public class BridgeGame {

    RealTimeBridge realTimeBridge = new RealTimeBridge();
    OutputView outputView = new OutputView();
    InputController inputController = new InputController();

    int count = 1;
    int size;
    List<String> bridge;
    boolean flag = true;
    public String[][] realTimeMap = realTimeBridge.getMap();

    public BridgeGame() {

    }

    /**
     * 사용자가 칸을 이동할 때 사용하는 메서드
     * <p>
     * 이동을 위해 필요한 메서드의 반환 타입(return type), 인자(parameter)는 자유롭게 추가하거나 변경할 수 있다.
     */
    public String move(String bridgeText, String userMove) {
        if (!bridgeText.equals(userMove)) {
            return " X ";
        }
        return " O ";
    }

    /**
     * 사용자가 게임을 다시 시도할 때 사용하는 메서드
     * <p>
     * 재시작을 위해 필요한 메서드의 반환 타입(return type), 인자(parameter)는 자유롭게 추가하거나 변경할 수 있다.
     */

    private void retry() {
        String gameCommand = inputController.getGameCommand();
        if (gameCommand.equals("Q")) {
            outputView.printResult(flag, count, realTimeMap);
            return;
        }
        initializeVariables();
        play(size, bridge);
    }

    private void initializeVariables() {
        flag = true;
        count++;
        realTimeBridge.initialize();
    }

    public void start() {
        this.size = inputController.getBridgeSize();
        BridgeNumberGenerator bridgeNumberGenerator = new BridgeRandomNumberGenerator();
        BridgeMaker bridgeMaker = new BridgeMaker(bridgeNumberGenerator);
        this.bridge = bridgeMaker.makeBridge(size);
        play(size, bridge);
    }

    private void play(int size, List<String> bridge) {
        for (int i = 0; i < size; i++) {
            if (compareInputActual(bridge, i)) {
                break;
            }
        }
        checkResult();
        outputView.printResult(flag, count, realTimeMap);
    }

    private boolean compareInputActual(List<String> bridge, int index) {
        String userMove = inputController.getMoving();
        String moveResult = move(userMove, bridge.get(index));
        return isWrong(realTimeMap, userMove, moveResult);
    }

    private void checkResult() {
        if (!flag) {
            retry();
        }
    }

    private boolean isWrong(String[][] realTimeMap, String userMove, String moveResult) {
        if (moveResult.equals(" X ")) {
            printRealTimeMap(realTimeMap, userMove, moveResult);
            flag = false;
            return true;
        }
        printRealTimeMap(realTimeMap, userMove, moveResult);
        return false;
    }

    private void printRealTimeMap(String[][] realTimeMap, String userMove, String moveResult) {
        realTimeBridge.makeRealTimeBridge(userMove, moveResult);
        outputView.printMap(realTimeMap);
    }

    public String isSuccess(boolean flag) {
        if (flag) {
            return EnumStrings.SUCCESS.getValue();
        }
        return EnumStrings.FAIL.getValue();
    }
}