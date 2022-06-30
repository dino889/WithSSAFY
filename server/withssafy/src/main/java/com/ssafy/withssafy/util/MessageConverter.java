package com.ssafy.withssafy.util;

import com.ssafy.withssafy.errorcode.ErrorCode;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ResourceBundleMessageSource;

public class MessageConverter {

    public static String getMessage(ErrorCode code) {
        ResourceBundleMessageSource resourceBundleMessageSource = new ResourceBundleMessageSource();
        resourceBundleMessageSource.setBasenames("messages/error");
        resourceBundleMessageSource.setDefaultEncoding("UTF-8");

        return resourceBundleMessageSource.getMessage(code.getCode(), null, LocaleContextHolder.getLocale());
    }
}
