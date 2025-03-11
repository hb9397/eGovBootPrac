package egovframework.let.epr.service.impl;

import egovframework.com.cmm.service.impl.EgovComAbstractDAO;
import egovframework.let.epr.service.vo.*;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public int selectEqpmnRepListTotCnt(ReqExcPerRepDetailVO reqExcPerRepDetailVO) throws Exception {
        return (Integer)selectOne("ExcPerRepMngtDAO.selectEqpmnRepListTotCnt", reqExcPerRepDetailVO);
    }

    public List<ResEqpmnRepVO> selectEqpmnRepList(ReqExcPerRepDetailVO reqExcPerRepDetailVO) throws Exception{
        return selectList("ExcPerRepMngtDAO.selectEqpmnRepList", reqExcPerRepDetailVO);
    }

    public int selectPerRepListTotCnt(ReqExcPerRepDetailVO reqExcPerRepDetailVO) throws Exception{
        return (Integer)selectOne("ExcPerRepMngtDAO.selectPerRepListTotCnt", reqExcPerRepDetailVO);
    }

    public List<ResPerRepVO> selectPerRepList(ReqExcPerRepDetailVO reqExcPerRepDetailVO) throws Exception{
        return selectList("ExcPerRepMngtDAO.selectPerRepList", reqExcPerRepDetailVO);
    }

    public void insertEqpmnRep(ReqEqpmnRepVO reqEqpmnRepVO) throws Exception{
        insert("ExcPerRepMngtDAO.insertEqpmnRep", reqEqpmnRepVO);
    }

    public void softDeleteEqpmnReps(ReqEqpmnRepVO reqEqpmnRepVO) throws Exception{
        update("ExcPerRepMngtDAO.softDeleteEqpmnReps", reqEqpmnRepVO);
    }

    public void insertPerRep(ReqPerRepVO reqPerRepVO) throws Exception{
        insert("ExcPerRepMngtDAO.insertPerRep", reqPerRepVO);
    }

    public void softDeletePerReps(ReqPerRepVO reqPerRepVO) throws Exception{
        update("ExcPerRepMngtDAO.softDeletePerReps", reqPerRepVO);
    }

    public void updateStatusWritingToWait(ReqExcPerRepVO reqExcPerRepVO) throws Exception{
        update("ExcPerRepMngtDAO.updateStatusWritingToWait", reqExcPerRepVO);
    }

    public List<String> selectExcPerRepDelIsNotNullList() throws Exception{
        return selectList("ExcPerRepMngtDAO.selectExcPerRepDelIsNotNullList");
    }

    public List<String> selectEQPMNREPDelIsNotNullList() throws Exception{
        return selectList("ExcPerRepMngtDAO.selectEQPMNREPDelIsNotNullList");
    }

    public List<String> selectPERREPDelIsNotNullList() throws Exception{
        return selectList("ExcPerRepMngtDAO.selectPERREPDelIsNotNullList");
    }

    public int deleteExcPerReps(List<String> delSeqs) throws Exception{
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("delSeqs", delSeqs);
        return delete("ExcPerRepMngtDAO.deleteExcPerReps", paramMap);
    }
}
