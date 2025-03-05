package egovframework.let.epr.epm.service.impl;

import egovframework.let.epr.epm.service.ExcPerRceptMngtService;
import egovframework.let.epr.service.vo.ReqExcPerRepVO;
import lombok.RequiredArgsConstructor;
import org.egovframe.rte.fdl.cmmn.EgovAbstractServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service("ExcPerRceptMngtService")
public class ExcPerRceptMngtServiceImpl extends EgovAbstractServiceImpl implements ExcPerRceptMngtService {

    private final ExcPerRceptMngtDAO excPerRceptMngtDAO;

    @Override
    @Transactional
    public int updateStatusWaitToApproval(ReqExcPerRepVO reqExcPerRepVO) throws Exception {
        return excPerRceptMngtDAO.updateStatusWaitToApproval(reqExcPerRepVO);
    }
}
