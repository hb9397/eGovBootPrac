package egovframework.let.epr.service.vo;

import egovframework.let.epr.util.user.Auditable;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ReqPerRepVO extends PerRep implements Auditable {
    // 실적 목록 중 체크된 항목들
    private List<String> checkedPerNos;
}
