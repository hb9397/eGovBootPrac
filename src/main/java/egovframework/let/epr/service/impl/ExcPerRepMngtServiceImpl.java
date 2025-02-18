package egovframework.let.epr.service.impl;

import egovframework.let.epr.service.ExcPerRepMngtService;
import egovframework.let.epr.service.vo.ReqExcPerRepVO;
import egovframework.let.epr.service.vo.ResExcPerRepVO;
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
}
