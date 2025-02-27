package egovframework.let.epr.comCode.web;

import egovframework.com.cmm.LoginVO;
import egovframework.com.cmm.ResponseCode;
import egovframework.com.cmm.service.ResultVO;
import egovframework.com.cmm.util.EgovUserDetailsHelper;
import egovframework.let.epr.comCode.service.ComCodeService;
import egovframework.let.epr.comCode.service.vo.SelectBoxComCodeVO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/epr/com/code")
public class ComCodeController {
    private final ComCodeService comCodeService;

    @GetMapping("/eqpmn/grade")
    public ResultVO selectEqpmnGradeCodeList() throws Exception {
        ResultVO resultVO = new ResultVO();

        LoginVO user = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();

        List<SelectBoxComCodeVO> list = comCodeService.selectEqpmnComCodeList();

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("user", user);
        resultMap.put("list", list);

        resultVO.setResultCode(ResponseCode.SUCCESS.getCode());
        resultVO.setResultMessage(resultVO.getResultMessage());
        resultVO.setResult(resultMap);

        return resultVO;
    }

    @GetMapping("/service/type")
    public ResultVO selectServiceTypeCodeList() throws Exception {
        ResultVO resultVO = new ResultVO();

        LoginVO user = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();

        List<SelectBoxComCodeVO> list = comCodeService.selectServiceTypeCodeList();

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("user", user);
        resultMap.put("list", list);

        resultVO.setResultCode(ResponseCode.SUCCESS.getCode());
        resultVO.setResultMessage(ResponseCode.SUCCESS.getMessage());
        resultVO.setResult(resultMap);

        return resultVO;
    }


}
