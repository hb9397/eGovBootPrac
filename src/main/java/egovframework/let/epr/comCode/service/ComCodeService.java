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

    @Value("${code-id.eqpmnGrade}")
    private String eqpmnGrade;

    @Value("${code-id.serviceType}")
    private String serviceType;

    public List<SelectBoxComCodeVO> selectEqpmnComCodeList() throws Exception {
        return comCodeDAO.selectComCodeList(eqpmnGrade);
    }

    public List<SelectBoxComCodeVO> selectServiceTypeCodeList() throws Exception {
        String codeId = "PMS003";
        return comCodeDAO.selectComCodeList(serviceType);
    }
}
