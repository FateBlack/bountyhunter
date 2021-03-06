package com.example.converter;

import com.example.VO.AssignmentInfoVO;
import com.example.VO.AssignmentVO;
import com.example.VO.UserAccountVO;
import com.example.dataobject.AssignmentCategory;
import com.example.dataobject.AssignmentInfo;
import com.example.dataobject.UserAccount;
import com.example.dataobject.UserDetail;
import com.example.service.DetailService;
import com.example.service.UserAccountService;
import com.example.service.impl.DetailServiceImpl;
import com.example.service.impl.UserAccountServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/5/4.
 */
@Service
public class AssignmentInfoList2VOlistConverter {

    /**
     * 在配置文件中配置的图片保存路径
     */

    public  List<AssignmentInfoVO> converter(List<AssignmentInfo> assignmentInfoList,
                                         UserAccountService accountService,
                                         DetailService detailService
                                         ){

            List<AssignmentInfoVO> assignmentInfoVOList = new ArrayList<>();
            for (AssignmentInfo assignmentInfo:assignmentInfoList){
                    AssignmentInfoVO assignmentInfoVO = new AssignmentInfoVO();
                    UserAccount account =  accountService.findOne(assignmentInfo.getAssignmentOwner());
                    UserAccountVO accountVO = new UserAccountVO();
                    BeanUtils.copyProperties(account,accountVO);
                    if(assignmentInfo.getAssignmentReceive()!=null){
                        UserAccount receiver = accountService.findOne(assignmentInfo.getAssignmentReceive());
                        UserAccountVO userAccountVO = new UserAccountVO();
                        BeanUtils.copyProperties(receiver,userAccountVO);
                        assignmentInfoVO.setAssignmentReceive(userAccountVO);
                    }
                    UserDetail detail = detailService.findOne(account.getDetailId());
                    accountVO.setUserDetail(detail);

                    BeanUtils.copyProperties(assignmentInfo,assignmentInfoVO);
                    assignmentInfoVO.setAssignmentOwner(accountVO);
                    assignmentInfoVOList.add(assignmentInfoVO);
                }

        return assignmentInfoVOList;
    }
}
