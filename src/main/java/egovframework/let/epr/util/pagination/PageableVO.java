package egovframework.let.epr.util.pagination;

public interface PageableVO {
    int getPageIndex();
    void setPageIndex(int pageIndex);

    int getPageUnit();
    void setPageUnit(int pageUnit);

    int getPageSize();
    void setPageSize(int pageSize);

    int getFirstIndex();
    void setFirstIndex(int firstIndex);

    int getLastIndex();
    void setLastIndex(int lastIndex);

    int getRecordCountPerPage();
    void setRecordCountPerPage(int recordCountPerPage);
}
