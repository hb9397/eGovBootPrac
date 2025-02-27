package egovframework.let.epr.comCode.service;

import egovframework.let.epr.comCode.service.vo.SelectBoxComCodeVO;
import lombok.RequiredArgsConstructor;
import org.egovframe.rte.fdl.cmmn.EgovAbstractServiceImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service("ComCodeService")
public class ComCodeService extends EgovAbstractServiceImpl {
    private final ComCodeDAO comCodeDAO;

    public List<SelectBoxComCodeVO> selectComCodeList(String codeId) throws Exception {
        return comCodeDAO.selectComCodeList(codeId);
    }

}
