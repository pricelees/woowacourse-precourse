package pairmatching.controller;

import pairmatching.constant.ProgramOption;
import pairmatching.domain.courseinfo.CourseInfo;
import pairmatching.dto.CourseInfoRequest;
import pairmatching.dto.MatchResponse;
import pairmatching.dto.ProgramOptionRequest;
import pairmatching.exception.DuplicatePairException;
import pairmatching.exception.NotExistMatchingException;
import pairmatching.exception.OverMatchTryCountException;
import pairmatching.service.PairService;
import pairmatching.view.input.InputView;
import pairmatching.view.output.OutputView;

public class PairProgramController {
    private final PairService pairService;
    private final InputView inputView;
    private final OutputView outputView;
    private boolean isProgramOn;
    private int matchTryCount;

    public PairProgramController(PairService pairService, InputView inputView, OutputView outputView) {
        this.pairService = pairService;
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void run() {
        programOn();
        while (isProgramOn) {
            ProgramOptionRequest programOptionRequest = inputView.requestOption();
            processByOption(programOptionRequest);
        }
    }

    private void processByOption(ProgramOptionRequest programOptionRequest) {
        if (ProgramOption.MATCH.equals(programOptionRequest.option())) {
            outputView.printCourse();
            processMatching();
        }
        if (ProgramOption.SEARCH.equals(programOptionRequest.option())) {
            outputView.printCourse();
            processSearching();
        }
        if (ProgramOption.CLEAR.equals(programOptionRequest.option())) {
            pairService.clear();
            outputView.printResetMessage();
        }
        if (ProgramOption.QUIT.equals(programOptionRequest.option())) {
            programOff();
        }
    }

    private void processSearching() {
        CourseInfoRequest courseInfoRequest = inputView.requestCourseInfo();
        CourseInfo courseInfo = pairService.loadCourseInfoFrom(courseInfoRequest);
        try {
            MatchResponse matchResponse = pairService.search(courseInfo);
            outputView.printMatchInfo(matchResponse);
        } catch (NotExistMatchingException e) {
            outputView.printExceptionMessage(e.getMessage());
            processSearching();
        }
    }

    private void processMatching() {
        CourseInfoRequest courseInfoRequest = inputView.requestCourseInfo();
        CourseInfo courseInfo = pairService.loadCourseInfoFrom(courseInfoRequest);
        initMatchTryCount();
        if (pairService.checkIfPairsExist(courseInfo)) {
            requestIfRematch(courseInfo);
            return;
        }
        match(courseInfo);
    }

    private void requestIfRematch(CourseInfo courseInfo) {
        String rematchOption = inputView.requestRematchOption();
        if (rematchOption.equals(ProgramOption.REMATCH)) {
            match(courseInfo);
        }
        if (rematchOption.equals(ProgramOption.NOT_REMATCH)) {
            processMatching();
        }
    }

    private void match(CourseInfo courseInfo) {
        try {
            MatchResponse matchResponse = pairService.match(courseInfo);
            outputView.printMatchInfo(matchResponse);
        } catch (DuplicatePairException e) {
            outputView.printExceptionMessage(e.getMessage());
            rematch(courseInfo);
        }
    }

    private void rematch(CourseInfo courseInfo) {
        try {
            increaseMatchTryCount();
            match(courseInfo);
        } catch (OverMatchTryCountException e) {
            outputView.printExceptionMessage(e.getMessage());
            processMatching();
        }
    }

    private void increaseMatchTryCount() throws OverMatchTryCountException {
        matchTryCount++;
        if (matchTryCount >= ProgramOption.MAX_MATCH_TRY_COUNT) {
            throw new OverMatchTryCountException();
        }
    }

    private void initMatchTryCount() {
        matchTryCount = ProgramOption.INIT_MATCH_TRY_COUNT;
    }

    private void programOn() {
        isProgramOn = true;
    }

    private void programOff() {
        isProgramOn = false;
    }
}
