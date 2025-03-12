package egovframework.let.epr.batch.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import egovframework.com.cmm.service.impl.EgovComAbstractDAO;
import egovframework.let.epr.service.vo.ResExcPerRepVO;

/**
 * --- 사용안함 ---
 * 전자정부 프레임워크에서 DAO 를 구현할 때 사용하는 상위 클래스
 * EgovComAbstractDAO, EgovAbstractMapper 등에서 기본 DB로 설정된
 * SqlSessionFactory 를 @Resource 로 강제하고 있기 때문에 다중 DB로 설정을 변경
 * 하려면 해당 상위 개체 부터 커스터마이징해야한다.
 *
 * 그래서 지금은 단일 DB로 진행, 이거는 Spring Batch 다중 DB 를 이용할 때,
 * 참고하는 코드 정도로.,.,..,
 * 아니면 시간 나면 위의 상위객체까지 커스텀 해서 사용해보기
 * --- 사용안함 ---
 **/

/*@Repository("EprBatchDAO")
public class EprBatchDAO extends EgovComAbstractDAO {

	public EprBatchDAO(@Qualifier("batchSqlSession") SqlSessionFactory batchSqlSession) {
		super.setSqlSessionFactory(batchSqlSession);
	}

	public int insertGbExcPerRepList(List<ResExcPerRepVO> delData) throws Exception{
		return insert("EprBatchDAO.insertGbExcPerRepList", delData);
	}

	public int deleteExcPerReps(List<ResExcPerRepVO> delData) throws Exception{
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("delData", delData);
		return delete("EprBatchDAO.deleteExcPerReps", paramMap);
	}
}*/
