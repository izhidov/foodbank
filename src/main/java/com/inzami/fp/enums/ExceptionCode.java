package com.inzami.fp.enums;

public enum ExceptionCode {

    ApplicationException(4000),
    BadRequestException(4002),
    CreateNewEntityException(4003),
    DateISO8601FormatException(4004),
    EntityAlreadyExistsException(4006),
    EntityNotFoundInServiceException(4007),
    PropertyNotFoundException(4009),
    UserDeactivatedForActionException(4010),
    UserNotMatchException(4011),
    WrongUserRoleForActionException(4015),
    WrongEntityStatusForActionException(4016),
    CantPerformActionException(4017),
    CantChangeValueTwiceException(4019);

    private Integer code;

    public Integer getCode() {
        return code;
    }

    ExceptionCode(Integer code) {
        this.code = code;
    }
}
