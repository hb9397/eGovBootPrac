package egovframework.let.epr.service.vo;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class ExcPerRep implements Serializable {
    /**
     *  serialVersion UID
     */
    private static final long serialVersionUID = -1L;

    private String excPerRepSeq;

    private String excPerRepName;

    private String progrsStatCode;

    private String progrsStatName;

    private String excDate;

    private String regDate;

    private String regID;

    private String cngDate;

    private String cngID;
}
