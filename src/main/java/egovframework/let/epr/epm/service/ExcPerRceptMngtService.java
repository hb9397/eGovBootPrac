package egovframework.let.epr.epm.service;

import egovframework.let.epr.service.vo.ReqExcPerRepVO;

public interface ExcPerRceptMngtService {
    public int updateStatusWaitToApproval(ReqExcPerRepVO reqExcPerRepVO) throws Exception;
}
