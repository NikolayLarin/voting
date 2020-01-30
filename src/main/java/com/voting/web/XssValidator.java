package com.voting.web;

import org.springframework.web.util.HtmlUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class XssValidator implements ConstraintValidator<XssEscape, String> {
    @Override
    public boolean isValid(String input, ConstraintValidatorContext context) {
        return HtmlUtils.htmlEscape(input).equals(input);
    }
}