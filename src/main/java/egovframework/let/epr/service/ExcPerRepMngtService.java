package egovframework.let.epr.service;

import egovframework.let.epr.service.vo.*;

import java.util.List;

public interface ExcPerRepMngtService {

    public int selectExcPerRepListTotCnt(ReqExcPerRepVO reqExcPerRepVO) throws Exception;

    public List<ResExcPerRepVO> selectExcPerRepList(ReqExcPerRepVO reqExcPerRepVO) throws Exception;

    public void insertExcPerRep(ReqExcPerRepVO reqExcPerRepVO) throws Exception;

    public int selectEqpmnRepListTotCnt(ReqExcPerRepDetailVO reqExcPerRepDetailVO) throws Exception;

    public List<ResEqpmnRepVO> selectEqpmnRepList(ReqExcPerRepDetailVO reqExcPerRepDetailVO) throws Exception;

    public int selectPerRepListTotCnt(ReqExcPerRepDetailVO reqExcPerRepDetailVO) throws Exception;

    public List<ResPerRepVO> selectPerRepList(ReqExcPerRepDetailVO reqExcPerRepDetailVO) throws Exception;

    public void insertEqpmnRep(ReqEqpmnRepVO reqEqpmnRepVO) throws Exception;

    public void softDeleteEqpmnReps(ReqEqpmnRepVO reqEqpmnRepVO) throws Exception;

    public void insertPerRep(ReqPerRepVO reqPerRepVO) throws Exception;

    public void softDeletePerReps(ReqPerRepVO reqPerRepVO) throws Exception;
}
