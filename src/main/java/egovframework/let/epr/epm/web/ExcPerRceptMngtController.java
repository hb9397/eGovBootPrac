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
        Map<String, Object> resultMap = new HashMap<>();

        try {
            LoginVO user = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
            userUtil.setAuditorFieldsUpdate(user, updateData);

            resultMap.put("consequence", excPerRceptMngtService.updateStatusWaitToApproval(updateData));

            resultVO.setResultCode(ResponseCode.SUCCESS.getCode());
            resultVO.setResult(resultMap);
            resultVO.setResultMessage(ResponseCode.SUCCESS.getMessage());
        } catch (RuntimeException e){
            resultMap.put("consequence", -1);

            resultVO.setResultCode(ResponseCode.BAD_REQUEST.getCode());
            resultVO.setResult(resultMap);
            resultVO.setResultMessage(e.getMessage());
        } catch (Exception e){
            resultMap.put("consequence", -2);

            resultVO.setResultCode(ResponseCode.INTERNAL_SERVER_ERROR.getCode());
            resultVO.setResult(resultMap);
            resultVO.setResultMessage(e.getMessage());
        }

        return resultVO;
    }

    @ResponseBody
    @PostMapping("/updateStatusWaitToReject.do")
    public ResultVO updateStatusWaitToReject(@RequestBody ReqExcPerRepVO updateData) throws Exception{
        ResultVO resultVO = new ResultVO();
        Map<String, Object> resultMap = new HashMap<>();

        try {
            LoginVO user = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
            userUtil.setAuditorFieldsUpdate(user, updateData);

            int consequence = excPerRceptMngtService.updateStatusWaitToReject(updateData);

            resultMap.put("consequence", consequence);

            resultVO.setResultCode(ResponseCode.SUCCESS.getCode());
            resultVO.setResult(resultMap);
            resultVO.setResultMessage(ResponseCode.SUCCESS.getMessage());
        } catch (RuntimeException e) {
            // Service 예외 처리
            resultMap.put("error", e.getMessage());
            resultMap.put("consequence", -1);

            resultVO.setResultCode(ResponseCode.BAD_REQUEST.getCode());
            resultVO.setResult(resultMap);
            resultVO.setResultMessage(e.getMessage());
        } catch (Exception e) {
            // 기타 예외 처리
            resultMap.put("error", "Unexpected error occurred");
            resultMap.put("consequence", -2);

            resultVO.setResultCode(ResponseCode.INTERNAL_SERVER_ERROR.getCode());
            resultVO.setResult(resultMap);
            resultVO.setResultMessage(e.getMessage());
        }

        return resultVO;
    }
}
