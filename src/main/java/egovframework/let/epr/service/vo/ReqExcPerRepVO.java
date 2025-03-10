package egovframework.let.epr.service.vo;

import egovframework.let.epr.util.pagination.PageableVO;
import egovframework.let.epr.util.user.Auditable;
import lombok.*;
import java.util.List;

@Getter
@Setter
public class ReqExcPerRepVO extends ExcPerRep implements PageableVO, Auditable {
    /*** 검색 키워드 시작 ***/
    private String searchExcPerRepName;

    private String searchExcDate;
    /*** 검색 키워드 끝 ***/

    /*** 페이지 네이션 시작 ***/
    /** API 의 경우 아래와 같이 Server 에도 기본값을 두고, Front 도 기본값을 두어 테스트에 용이하도록 한다. **/

    /** 현재페이지 */
    private int pageIndex = 1;

    /** 페이지에 표시할 데이터 갯수 */
    private int pageUnit = 10;

    /** 페이지네이션 네비게이트 최대 개수 */
    private int pageSize = 10;

    /** 첫페이지 인덱스 */
    private int firstIndex = 1;

    /** 마지막페이지 인덱스 */
    private int lastIndex = 1;

    /** 페이지당 레코드 개수 */
    private int recordCountPerPage = 10;

    /** 레코드 번호 */
    private int rowNo = 0;

    /*** 페이지 네이션 끝 ***/

    /*** ExcPerReqSeq 체크 박스 ***/
    List<String> checkedExcPerReqSeqs;
}
