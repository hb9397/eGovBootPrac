package egovframework.let.epr.service;

import egovframework.let.epr.service.vo.ReqExcPerRepVO;
import egovframework.let.epr.service.vo.ResExcPerRepVO;

import java.util.List;

public interface ExcPerRepMngtService {

    public int selectExcPerRepListTotCnt(ReqExcPerRepVO reqExcPerRepVO) throws Exception;

    public List<ResExcPerRepVO> selectExcPerRepList(ReqExcPerRepVO reqExcPerRepVO) throws Exception;

    public void insertExcPerRep(ReqExcPerRepVO reqExcPerRepVO) throws Exception;
}
