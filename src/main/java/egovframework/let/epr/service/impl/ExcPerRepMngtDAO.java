package egovframework.let.epr.service.impl;

import egovframework.com.cmm.service.impl.EgovComAbstractDAO;
import egovframework.let.epr.service.vo.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("ExcPerRepMngtDAO")
public class ExcPerRepMngtDAO extends EgovComAbstractDAO {

    public int selectExcPerRepMngtListTotCnt(ReqExcPerRepVO reqExcPerRepVO)  throws Exception {
        return (Integer)selectOne("ExcPerRepMngtDAO.selectExcPerRepListTotCnt", reqExcPerRepVO);
    }

    public List<ResExcPerRepVO> selectExcPerRepMngtList(ReqExcPerRepVO reqExcPerRepVO) throws Exception {
        return selectList("ExcPerRepMngtDAO.selectExcPerRepList", reqExcPerRepVO);
    }

    public void insertExcPerRep(ReqExcPerRepVO reqExcPerRepVO) throws Exception{
        insert("ExcPerRepMngtDAO.insertExcPerRep", reqExcPerRepVO);
    }

    public int selectEqpmnRepListTotCnt() throws Exception {
        return (Integer)selectOne("ExcPerRepMngtDAO.selectEqpmnRepListTotCnt");
    }

    public List<ResEqpmnRepVO> selectEqpmnRepList(ReqExcPerRepDetailVO reqExcPerRepDetailVO) throws Exception{
        return selectList("ExcPerRepMngtDAO.selectEqpmnRepList", reqExcPerRepDetailVO);
    }

    public int selectPerRepListTotCnt() throws Exception{
        return (Integer)selectOne("ExcPerRepMngtDAO.selectPerRepListTotCnt");
    }

    public List<ResPerRepVO> selectPerRepList(ReqExcPerRepDetailVO reqExcPerRepDetailVO) throws Exception{
        return selectList("ExcPerRepMngtDAO.selectPerRepList", reqExcPerRepDetailVO);
    }
}
