package egovframework.let.epr.comCode.service;

import egovframework.com.cmm.service.impl.EgovComAbstractDAO;
import egovframework.let.epr.comCode.service.vo.SelectBoxComCodeVO;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("ComCodeDAO")
public class ComCodeDAO extends EgovComAbstractDAO {
    public List<SelectBoxComCodeVO> selectComCodeList(String codeId) throws Exception{
        return selectList("selectComCodeList", codeId);
    }
}
