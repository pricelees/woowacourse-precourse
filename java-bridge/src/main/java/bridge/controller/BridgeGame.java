package bridge.controller;

import bridge.constants.Constant;
import bridge.constants.GameResult;
import bridge.constants.MovingResult;
import bridge.domain.Bridge;
import bridge.domain.BridgeMap;
import bridge.dto.CurrentMapResponse;
import bridge.dto.GameResultResponse;
import bridge.exception.FellException;
import bridge.util.BridgeMaker;
import bridge.util.BridgeNumberGenerator;
import bridge.util.BridgeRandomNumberGenerator;
import bridge.view.input.InputView;
import bridge.view.output.OutputView;

/**
 * 다리 건너기 게임을 관리하는 클래스
 */
public class BridgeGame {
    private final InputView inputView;
    private final OutputView outputView;
    private Bridge bridge;
    private BridgeMap bridgeMap;
    private int playCount = 0;

    public BridgeGame(InputView inputView, OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void start() {
        createBridge();
        increasePlayCount();
        play();
    }

    private void increasePlayCount() {
        playCount++;
    }

    private void createBridge() {
        final int bridgeSize = inputView.readBridgeSize();
        final BridgeNumberGenerator bridgeNumberGenerator = new BridgeRandomNumberGenerator();
        final BridgeMaker bridgeMaker = new BridgeMaker(bridgeNumberGenerator);

        bridge = Bridge.from(bridgeMaker, bridgeSize);
    }

    private void play() {
        try {
            moveAll();
            gameEnd(GameResult.SUCCEED);
        } catch (FellException fellException) {
            retry();
        }
    }

    private void moveAll() throws FellException {
        initBridgeMap();
        for (int step = Constant.ZERO; step < bridge.getBridgeSize(); step++) {
            String location = inputView.readMoving();
            MovingResult result = move(location, step);
            bridgeMap.updateMap(location, result.getSymbol());
            printCurrentMap(bridgeMap);
            checkIfFell(result);
        }
    }

    private void checkIfFell(MovingResult result) {
        if (result == MovingResult.FALL) {
            throw new FellException();
        }
    }

    private void initBridgeMap() {
        bridgeMap = new BridgeMap();
    }

    private void printCurrentMap(BridgeMap bridgeMap) {
        CurrentMapResponse currentMapResponse = new CurrentMapResponse(
                bridgeMap.getSymbolsOfUPSide(),
                bridgeMap.getSymbolsOfDOWNSide()
        );
        outputView.printMap(currentMapResponse);
    }

    /**
     * 사용자가 칸을 이동할 때 사용하는 메서드
     * <p>
     * 이동을 위해 필요한 메서드의 반환 타입(return type), 인자(parameter)는 자유롭게 추가하거나 변경할 수 있다.
     */
    private MovingResult move(String location, int step) {
        if (bridge.canMove(location, step)) {
            return MovingResult.SUCCEED;
        }
        return MovingResult.FALL;
    }

    /**
     * 사용자가 게임을 다시 시도할 때 사용하는 메서드
     * <p>
     * 재시작을 위해 필요한 메서드의 반환 타입(return type), 인자(parameter)는 자유롭게 추가하거나 변경할 수 있다.
     */
    private void retry() {
        String command = inputView.readGameCommand();
        if (command.equals(Constant.GAME_QUIT_COMMAND)) {
            gameEnd(GameResult.FAIL);
        }
        if (command.equals(Constant.GAME_RESTART_COMMAND)) {
            increasePlayCount();
            play();
        }
    }

    private void gameEnd(GameResult gameResult) {
        CurrentMapResponse currentMapResponse = new CurrentMapResponse(
                bridgeMap.getSymbolsOfUPSide(),
                bridgeMap.getSymbolsOfDOWNSide()
        );
        GameResultResponse gameResultResponse = new GameResultResponse(
                gameResult.getResult(),
                playCount
        );
        outputView.printResult(currentMapResponse, gameResultResponse);
    }
}
