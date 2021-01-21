// 承認する日報が承認済みかどうかチェック
package models.validators;

import javax.persistence.EntityManager;

import models.Approval;
import utils.DBUtil;

public class ApprovalValidator {
    public static boolean validate(Approval a,int r_id){
        EntityManager em = DBUtil.createEntityManager();
        boolean app_check=true;

        a = em.find(Approval.class,r_id);

        if(a == null){
            app_check = false;
        }

        return app_check;
    }
}
