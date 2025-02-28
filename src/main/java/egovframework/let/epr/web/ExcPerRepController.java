package egovframework.let.epr.web;


import egovframework.com.cmm.LoginVO;
import egovframework.com.cmm.ResponseCode;
import egovframework.com.cmm.service.ResultVO;
import egovframework.com.cmm.util.EgovUserDetailsHelper;
import egovframework.let.epr.service.ExcPerRepMngtService;
import egovframework.let.epr.service.vo.*;
import egovframework.let.epr.util.pagination.PaginationUtil;
import egovframework.let.epr.util.user.UserUtil;
import lombok.RequiredArgsConstructor;
import org.egovframe.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;
import org.springframework.web.bind.annotation.*;
import org.springmodules.validation.commons.DefaultBeanValidator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/v1/epr")
public class ExcPerRepController {
    private final PaginationUtil paginationUtil;

    private final UserUtil userUtil;

    private final DefaultBeanValidator defaultBeanValidator;

    private final ExcPerRepMngtService excPerRepMngtService;


    @PostMapping("/excPerRepList.do")
    @ResponseBody
    public ResultVO selectExcPerRepList(@RequestBody ReqExcPerRepVO searchCondition) throws Exception{
        ResultVO resultVO = new ResultVO();

        LoginVO user = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();

        PaginationInfo paginationInfo = new PaginationInfo();
        int resExcPerVOListTotCnt = excPerRepMngtService.selectExcPerRepListTotCnt(searchCondition);
        paginationUtil.setPaginationInfo(paginationInfo, resExcPerVOListTotCnt, searchCondition);

        List<ResExcPerRepVO> resExcPerRepVOList = excPerRepMngtService.selectExcPerRepList(searchCondition);

        Map<String, Object> resultMap = new HashMap<>();

        resultMap.put("list", resExcPerRepVOList);
        resultMap.put("paginationInfo", paginationInfo);
        resultMap.put("user", user);

        resultVO.setResultCode(ResponseCode.SUCCESS.getCode());
        resultVO.setResultMessage(ResponseCode.SUCCESS.getMessage());
        resultVO.setResult(resultMap);

        return resultVO;
    }

    @PostMapping("/insertExcPerRep.do")
    @ResponseBody
    public ResultVO insertExcPerRep(@RequestBody ReqExcPerRepVO createData) throws Exception{
        ResultVO resultVO = new ResultVO();

        LoginVO user = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();

        userUtil.setAuditorFieldsCreate(user, createData);

        excPerRepMngtService.insertExcPerRep(createData);

        resultVO.setResultCode(ResponseCode.SUCCESS.getCode());
        resultVO.setResultMessage(ResponseCode.SUCCESS.getMessage());

        return resultVO;
    }

    @PostMapping("/eqpmnRepList.do")
    @ResponseBody
    public ResultVO selectEqpmnList(@RequestBody ReqExcPerRepDetailVO inquiryCondition) throws Exception{
        ResultVO resultVO = new ResultVO();

        LoginVO user = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();

        PaginationInfo paginationInfo = new PaginationInfo();
        int resEqpmnRepListCnt = excPerRepMngtService.selectEqpmnRepListTotCnt(inquiryCondition);
        paginationUtil.setPaginationInfo(paginationInfo, resEqpmnRepListCnt, inquiryCondition);

        List<ResEqpmnRepVO> eqpmnRepList = excPerRepMngtService.selectEqpmnRepList(inquiryCondition);

        Map<String, Object> resultMap = new HashMap<>();

        resultMap.put("list", eqpmnRepList);
        resultMap.put("paginationInfo", paginationInfo);
        resultMap.put("user", user);

        resultVO.setResultCode(ResponseCode.SUCCESS.getCode());
        resultVO.setResultMessage(ResponseCode.SUCCESS.getMessage());
        resultVO.setResult(resultMap);

        return resultVO;
    }

    @PostMapping("/perRepList.do")
    @ResponseBody
    public ResultVO selectPerRepList(@RequestBody ReqExcPerRepDetailVO inquiryCondition) throws Exception{
        ResultVO resultVO = new ResultVO();

        LoginVO user = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();

        PaginationInfo paginationInfo = new PaginationInfo();
        int resPerRepListCnt = excPerRepMngtService.selectPerRepListTotCnt(inquiryCondition);
        paginationUtil.setPaginationInfo(paginationInfo, resPerRepListCnt, inquiryCondition);

        List<ResPerRepVO> perRepList = excPerRepMngtService.selectPerRepList(inquiryCondition);

        Map<String, Object> resultMap = new HashMap<>();

        resultMap.put("list", perRepList);
        resultMap.put("paginationInfo", paginationInfo);
        resultMap.put("user", user);

        resultVO.setResultCode(ResponseCode.SUCCESS.getCode());
        resultVO.setResult(resultMap);
        resultVO.setResultMessage(ResponseCode.SUCCESS.getMessage());

        return resultVO;
    }

    @PostMapping("/insertEqpmnRep.do")
    @ResponseBody
    public ResultVO insertEqpmnRep(@RequestBody ReqEqpmnRepVO createData) throws Exception{
        ResultVO resultVO = new ResultVO();

        LoginVO user = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
        userUtil.setAuditorFieldsCreate(user, createData);

        excPerRepMngtService.insertEqpmnRep(createData);

        resultVO.setResultCode(ResponseCode.SUCCESS.getCode());
        resultVO.setResultMessage(ResponseCode.SUCCESS.getMessage());

        return resultVO;
    }

    @PostMapping("softDelEqpmnReps.do")
    @ResponseBody
    public ResultVO softDeleteEqpmnReps(@RequestBody ReqEqpmnRepVO softDelData) throws Exception{
        ResultVO resultVO = new ResultVO();

        LoginVO user = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
        userUtil.setAuditorFieldsDelete(user, softDelData);

        excPerRepMngtService.softDeleteEqpmnReps(softDelData);

        resultVO.setResultCode(ResponseCode.SUCCESS.getCode());
        resultVO.setResultMessage(ResponseCode.SUCCESS.getMessage());

        return resultVO;
    }

    @PostMapping("/insertPerRep.do")
    @ResponseBody
    public ResultVO insertPerRep(@RequestBody ReqPerRepVO createData) throws Exception{
        ResultVO resultVO = new ResultVO();

        LoginVO user = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
        userUtil.setAuditorFieldsCreate(user, createData);

        excPerRepMngtService.insertPerRep(createData);

        resultVO.setResultCode(ResponseCode.SUCCESS.getCode());
        resultVO.setResultMessage(resultVO.getResultMessage());

        return resultVO;
    }

    @PostMapping("softDeletePerReps.do")
    @ResponseBody
    public ResultVO softDeletePerReps(ReqPerRepVO softDelData) throws Exception{
        ResultVO resultVO = new ResultVO();

        LoginVO user = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
        userUtil.setAuditorFieldsDelete(user, softDelData);

        excPerRepMngtService.softDeletePerReps(softDelData);

        resultVO.setResultCode(ResponseCode.SUCCESS.getCode());
        resultVO.setResultMessage(ResponseCode.SUCCESS.getMessage());

        return resultVO;
    }
}
