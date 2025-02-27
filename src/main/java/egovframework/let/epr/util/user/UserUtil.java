package egovframework.let.epr.util.user;

import egovframework.com.cmm.LoginVO;
import org.springframework.stereotype.Component;

@Component
public class UserUtil {
    public <T extends Auditable> void setAuditorFieldsCreate(LoginVO user, T auditable){
        if (user.getId() != null) {
            auditable.setRegId(user.getId());
            auditable.setCngId(user.getId());
        } else {
            auditable.setRegId("SYSTEM_TEST");
            auditable.setCngId("SYSTEM_TEST");
        }
    }

    public <T extends Auditable> void setAuditorFieldsUpdate(LoginVO user, T auditable){
        if (user.getId()  != null) {
            auditable.setCngId(user.getId());
        } else {
            auditable.setCngId("SYSTEM_TEST");
        }
    }

    public <T extends Auditable> void setAuditorFieldsDelete(LoginVO user, T auditable){
        if (user.getId()  != null) {
            auditable.setDelId(user.getId());
        } else {
            auditable.setDelId("SYSTEM_TEST");
        }
    }
}
