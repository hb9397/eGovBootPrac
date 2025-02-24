package egovframework.let.epr.service.impl;

import egovframework.let.epr.service.ExcPerRepMngtService;
import egovframework.let.epr.service.vo.*;
import lombok.RequiredArgsConstructor;
import org.egovframe.rte.fdl.cmmn.EgovAbstractServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service("ExcPerRepMngtService")
public class ExcPerRepMngtServiceImpl extends EgovAbstractServiceImpl implements ExcPerRepMngtService {

    private final ExcPerRepMngtDAO excPerRepMngtDAO;

    @Override
    public int selectExcPerRepListTotCnt(ReqExcPerRepVO reqExcPerRepVO) throws Exception {
        return excPerRepMngtDAO.selectExcPerRepMngtListTotCnt(reqExcPerRepVO);
    }

    @Override
    public List<ResExcPerRepVO> selectExcPerRepList(ReqExcPerRepVO reqExcPerRepVO) throws Exception {
        return excPerRepMngtDAO.selectExcPerRepMngtList(reqExcPerRepVO);
    }

    @Override
    public void insertExcPerRep(ReqExcPerRepVO reqExcPerRepVO) throws Exception {
        excPerRepMngtDAO.insertExcPerRep(reqExcPerRepVO);
    }

    @Override
    public int selectEqpmnRepListTotCnt() throws Exception {
        return excPerRepMngtDAO.selectEqpmnRepListTotCnt();
    }

    @Override
    public List<ResEqpmnRepVO> selectEqpmnRepList(ReqExcPerRepDetailVO reqExcPerRepDetailVO) throws Exception {
        return excPerRepMngtDAO.selectEqpmnRepList(reqExcPerRepDetailVO);
    }

    @Override
    public int selectPerRepListTotCnt() throws Exception {
        return excPerRepMngtDAO.selectPerRepListTotCnt();
    }

    @Override
    public List<ResPerRepVO> selectPerRepList(ReqExcPerRepDetailVO reqExcPerRepDetailVO) throws Exception {
        return excPerRepMngtDAO.selectPerRepList(reqExcPerRepDetailVO);
    }
}
