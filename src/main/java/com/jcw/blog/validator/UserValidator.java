package com.jcw.blog.validator;


import com.jcw.blog.model.Board;
import com.jcw.blog.model.User;
import org.hibernate.annotations.Comment;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class UserValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        User user = (User) target;
        if(StringUtils.isEmpty(((User) target).getUsername())){
            errors.rejectValue("username", "key", "username 입력하세요");
        }

    }
}
