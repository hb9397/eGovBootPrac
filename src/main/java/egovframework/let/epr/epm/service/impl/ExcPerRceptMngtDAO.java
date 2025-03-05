package egovframework.let.epr.epm.service.impl;

import egovframework.com.cmm.service.impl.EgovComAbstractDAO;
import egovframework.let.epr.service.vo.ReqExcPerRepVO;
import org.springframework.stereotype.Repository;

@Repository("ExcPerRceptMngtDAO")
public class ExcPerRceptMngtDAO extends EgovComAbstractDAO {
    public int updateStatusWaitToApproval(ReqExcPerRepVO reqExcPerRepVO) throws Exception{
        return update("ExcPerRceptMngtDAO.updateStatusWaitToApproval", reqExcPerRepVO);
    }
}
