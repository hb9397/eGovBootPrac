package egovframework.let.epr.web;


import egovframework.com.cmm.LoginVO;
import egovframework.com.cmm.ResponseCode;
import egovframework.com.cmm.service.ResultVO;
import egovframework.com.cmm.util.EgovUserDetailsHelper;
import egovframework.let.epr.service.ExcPerRepMngtService;
import egovframework.let.epr.service.vo.ReqExcPerRepVO;
import egovframework.let.epr.service.vo.ResExcPerRepVO;
import egovframework.let.epr.util.pagination.PaginationUtil;
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
    public ResultVO insertExcPerRep(@RequestBody ReqExcPerRepVO reqExcPerRepVO) throws Exception{
        ResultVO resultVO = new ResultVO();

        LoginVO user = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();

        excPerRepMngtService.insertExcPerRep(reqExcPerRepVO);

        resultVO.setResultCode(ResponseCode.SUCCESS.getCode());
        resultVO.setResultMessage(ResponseCode.SUCCESS.getMessage());

        return resultVO;
    }
}
