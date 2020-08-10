package com.saltfishsoft.springclouddemo.accserver.model.dto;

import com.saltfishsoft.springclouddemo.accserver.domain.User;
import com.saltfishsoft.springclouddemo.common.annotation.QueryCondition;
import com.saltfishsoft.springclouddemo.common.constant.MatchType;
import com.saltfishsoft.springclouddemo.common.model.base.BaseQuery;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.Specification;

/**
 * Created by Shihongbing on 2020/6/30.
 */
@Setter
@Getter
public class UserQueryDto extends BaseQuery<User> {

    @QueryCondition(func= MatchType.like)
    private String account;

    @QueryCondition(func= MatchType.like,column = "userName")
    private String userName;

    @Override
    public Specification<User> toSpec() {
        return super.toSpecWithAnd();
    }
}
