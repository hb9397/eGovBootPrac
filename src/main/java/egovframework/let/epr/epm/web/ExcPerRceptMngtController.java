package egovframework.let.epr.epm.web;

import egovframework.com.cmm.LoginVO;
import egovframework.com.cmm.ResponseCode;
import egovframework.com.cmm.service.ResultVO;
import egovframework.com.cmm.util.EgovUserDetailsHelper;
import egovframework.let.epr.epm.service.ExcPerRceptMngtService;
import egovframework.let.epr.service.vo.ReqExcPerRepVO;
import egovframework.let.epr.util.user.UserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/epm")
public class ExcPerRceptMngtController {

    private final UserUtil userUtil;

    private final ExcPerRceptMngtService excPerRceptMngtService;

    @ResponseBody
    @PostMapping("/updateStatusWaitToApproval.do")
    public ResultVO updateStatusWaitToApproval(@RequestBody ReqExcPerRepVO updateData) throws Exception{

        ResultVO resultVO = new ResultVO();

        LoginVO user = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
        userUtil.setAuditorFieldsUpdate(user, updateData);

        Map<String, Object> resultMap = new HashMap<>();

        resultMap.put("consequence", excPerRceptMngtService.updateStatusWaitToApproval(updateData));

        resultVO.setResultCode(ResponseCode.SUCCESS.getCode());
        resultVO.setResult(resultMap);
        resultVO.setResultMessage(ResponseCode.SUCCESS.getMessage());

        return resultVO;
    }

    @ResponseBody
    @PostMapping("/updateStatusWaitToReject.do")
    public ResultVO updateStatusWaitToReject(@RequestBody ReqExcPerRepVO updateData) throws Exception{
        ResultVO resultVO = new ResultVO();

        LoginVO user = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
        userUtil.setAuditorFieldsUpdate(user, updateData);

        Map<String, Object> resultMap = new HashMap<>();

        resultMap.put("consequence", excPerRceptMngtService.updateStatusWaitToReject(updateData));

        resultVO.setResultCode(ResponseCode.SUCCESS.getCode());
        resultVO.setResult(resultMap);
        resultVO.setResultMessage(ResponseCode.SUCCESS.getMessage());

        return resultVO;
    }
}
