package egovframework.let.epr.service.impl;

import egovframework.com.cmm.service.impl.EgovComAbstractDAO;
import egovframework.let.epr.service.vo.ReqExcPerRepVO;
import egovframework.let.epr.service.vo.ResExcPerRepVO;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("eprExcPerRepMngtDAO")
public class ExcPerRepMngtDAO extends EgovComAbstractDAO {

    public int selectExcPerRepMngtListTotCnt(ReqExcPerRepVO reqExcPerRepVO)  throws Exception {
        return (Integer)selectOne("ExcPerRepMngtDAO.selectExcPerRepListTotCnt", reqExcPerRepVO);
    }

    public List<ResExcPerRepVO> selectExcPerRepMngtList(ReqExcPerRepVO reqExcPerRepVO) throws Exception {
        return selectList("ExcPerRepMngtDAO.selectExcPerRepList", reqExcPerRepVO);
    }

}
