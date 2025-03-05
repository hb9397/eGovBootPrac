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
        int consequence = excPerRceptMngtDAO.updateStatusWaitToApproval(reqExcPerRepVO);

        if(reqExcPerRepVO.getCheckedExcPerReqSeqs().size() == consequence){
            return consequence;
        } else {
            throw new RuntimeException("Update failed : Only items with status 0002 (Waiting) should be selected.");
        }
    }

    @Override
    @Transactional
    public int updateStatusWaitToReject(ReqExcPerRepVO reqExcPerRepVO) throws Exception {

        int consequence = excPerRceptMngtDAO.updateStatusWaitToReject(reqExcPerRepVO);

        if (reqExcPerRepVO.getCheckedExcPerReqSeqs().size() == consequence){
            return consequence;
        } else {
            throw new RuntimeException("Update failed : Only items with status 0002 (Waiting) should be selected.");
        }
    }
}
