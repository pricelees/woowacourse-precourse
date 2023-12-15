# 우아한테크코스 프리코스 최종 코딩테스트


---

### 1. 우선 테스트코드를 보고 개요를 파악하자.
    

    -> 페어매칭 미션에선, 테스트 코드에서 크루의 컬렉션은 List같이 순서가 보존되는 것을 사용하는 것을 암시한다.

### 2. 구현 방법이 떠올랐는데, 그 상황에서 더 좋은 방법을 고민하지 말고 일단은 그대로 구현하자.

### 3. 컬렉션의 순서가 안 맞는다면 Set을 쓴 것이 아닌지 의심하자.


    -> 가급적 LinkedHashset을 사용하자.
    -> 스트림의 Collectors.toCollection(LinkedHashSet::new)를 이용하자.


### 4. 예외가 여라 단계로 처리될 경우, 각각의 기능을 분리하자.


    private void match(CourseInfo courseInfo) {
        try {
            MatchResponse matchResponse = pairService.match(courseInfo);
            outputView.printMatchInfo(matchResponse);
        } catch (DuplicatePairException e) {
            System.out.println(e.getMessage());
            rematch(courseInfo);
        }
    }
    
    private void rematch(CourseInfo courseInfo) {
        try {
            increaseMatchTryCount();
            match(courseInfo);
        } catch (OverMatchTryCountException e) {
            System.out.println(e.getMessage());
            processMatching();
        }
    }
    
    private void increaseMatchTryCount() throws OverMatchTryCountException {
        matchTryCount++;
        if (matchTryCount >= ProgramOption.MAX_MATCH_TRY_COUNT) {
            throw new OverMatchTryCountException();
        }
    }


### 5. 상속을 써야겠다는 생각이 들어 구현한 클래스 이외엔 모두 처음부터 final로 선언하자.


### 6. 상수 클래스 혹은 static으로만 이루어진 클래스는, private 생성자를 지정했는지 확인하자.


### 7. 랜덤값을 뽑아야 하는 경우엔, 테스트를 고려하자. 
    - 의존성 주입으로 테스트를 할 것인가, mockStatic으로 테스트를 할 것인가?
    - 의존성 주입으로 하는 것도 좋지만, 테스트 코드를 위한 생성자를 오버로딩 해야하는 비효율성을 무시할 순 없다.


### 8. 다리 건너기 게임과 같이 비교적 복잡한 UI인 경우, 파트를 분리하자.
    - 한번에 모든 다리를 생성할 수 없다.
    - 다리를 위, 아래로 나누고 각각을 합치면 된다.(String.join, collectors.joining() 등으로!)
    - 이 부분도, 테스트 코드를 바로 읽었으면 충분히 아이디어를 얻을 수 있었다 !!!


### 9. Request DTO를 사용하면 비교적 단계적으로 예외 처리를 할 수 있다. 
    - InputView에서는 정규식을 이용하여 문자열 형태만 검증하고 바로 파싱한다.
    - Request DTO 객체를 생성할 때 파싱된 값에 대한 예외 검증을 진행한다.
    - enum과 같은 클래스의 값을 불러와야 한다면, Optional로 지정한 뒤 리턴한다.


    public static Optional<Level> fromName(String name) {
        return Arrays.stream(Level.values())
                .filter(level -> name.equals(level.getName()))
                .findFirst();
    }

    public static CourseInfoRequest from(String courseInfo) {
        String[] allInfo = courseInfo.split(COURSE_LEVEL_MISSION_DELIMITER);
        Course course = Course.fromName(allInfo[COURSE_INDEX])
                .orElseThrow(InvalidCourseInfoException::new);

        Level level = Level.fromName(allInfo[LEVEL_INDEX])
                .orElseThrow(InvalidCourseInfoException::new);

        Mission mission = Mission.fromName(allInfo[MISSION_INDEX])
                .orElseThrow(InvalidCourseInfoException::new);

        return new CourseInfoRequest(course, level, mission);
    }
    
### 10. 사용자 정의 예외는 반드시 고려해보자. (다리 건너기, 페어 프로그래밍)
    - 예외 발생은, 특수한 상황에서 반복을 멈추는 기능으로 활용할 수도 있다. 


### 11. toList()의 불변성에 주의하자.

### 12. 객체의 속성을 변경할 경우, equals 메서드를 다시 한번 확인하자.(점심 메뉴 추천)