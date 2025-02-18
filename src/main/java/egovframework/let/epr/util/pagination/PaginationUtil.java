package egovframework.let.epr.util.pagination;

import org.egovframe.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;
import org.springframework.stereotype.Component;

@Component
public class PaginationUtil {
    public <T extends PageableVO> void setPaginationInfo(PaginationInfo paginationInfo, int totCnt, T pageableVO){
        paginationInfo.setCurrentPageNo(pageableVO.getPageIndex());     // 현재 페이지 지정
        paginationInfo.setRecordCountPerPage(pageableVO.getPageUnit()); // 한 페이지에 출력할 데이터 수
        paginationInfo.setPageSize(pageableVO.getPageSize());           // 페이지 네비게이션에 표현할 수
        paginationInfo.setTotalRecordCount(totCnt);                     // 전체 데이터 개수

        pageableVO.setFirstIndex(paginationInfo.getFirstRecordIndex());
        pageableVO.setLastIndex(paginationInfo.getLastRecordIndex());
        pageableVO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());
    }
}
